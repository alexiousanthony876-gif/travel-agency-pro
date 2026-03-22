import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@/lib/prisma';
import { verifyPaymentSignature, fetchPaymentDetails } from '@/lib/razorpay';
import { sendWhatsAppMessage } from '@/lib/whatsapp';
import { getAuthCookie, verifyToken } from '@/lib/auth';
import { z } from 'zod';

const verifyPaymentSchema = z.object({
  razorpayOrderId: z.string(),
  razorpayPaymentId: z.string(),
  razorpaySignature: z.string(),
  paymentMethod: z.enum(['upi', 'card', 'netbanking']),
});

export async function POST(request: NextRequest) {
  try {
    const token = await getAuthCookie();
    if (!token) {
      return NextResponse.json(
        { error: 'Unauthorized' },
        { status: 401 }
      );
    }

    const decoded = verifyToken(token);
    if (!decoded) {
      return NextResponse.json(
        { error: 'Invalid token' },
        { status: 401 }
      );
    }

    const body = await request.json();
    const {
      razorpayOrderId,
      razorpayPaymentId,
      razorpaySignature,
      paymentMethod,
    } = verifyPaymentSchema.parse(body);

    // Verify signature
    const isSignatureValid = await verifyPaymentSignature(
      razorpayOrderId,
      razorpayPaymentId,
      razorpaySignature
    );

    if (!isSignatureValid) {
      return NextResponse.json(
        { error: 'Invalid payment signature' },
        { status: 400 }
      );
    }

    // Fetch payment details from Razorpay
    const paymentDetails = await fetchPaymentDetails(razorpayPaymentId);

    if (!paymentDetails.success) {
      return NextResponse.json(
        { error: 'Failed to fetch payment details' },
        { status: 500 }
      );
    }

    // Get payment record
    const payment = await prisma.payment.findFirst({
      where: { razorpayOrderId },
      include: {
        booking: {
          include: {
            user: true,
            package: true,
            hotel: true,
          },
        },
      },
    });

    if (!payment) {
      return NextResponse.json(
        { error: 'Payment record not found' },
        { status: 404 }
      );
    }

    // Check authorization
    if (payment.userId !== decoded.userId) {
      return NextResponse.json(
        { error: 'Forbidden' },
        { status: 403 }
      );
    }

    // Update payment record
    const updatedPayment = await prisma.payment.update({
      where: { id: payment.id },
      data: {
        razorpayPaymentId,
        method: paymentMethod,
        status: paymentDetails.payment.status === 'captured' ? 'completed' : 'failed',
        transactionId: razorpayPaymentId,
        metadata: JSON.stringify(paymentDetails.payment),
      },
    });

    // If payment successful, update booking
    if (updatedPayment.status === 'completed' && payment.booking) {
      await prisma.booking.update({
        where: { id: payment.bookingId },
        data: { status: 'confirmed' },
      });

      // Send WhatsApp confirmation
      await sendWhatsAppMessage({
        userId: payment.userId,
        phoneNumber: payment.booking.user.phoneNumber,
        messageType: 'payment_receipt',
        bookingId: payment.bookingId,
        data: {
          bookingReference: payment.booking.bookingReference,
          amount: payment.amount,
          method: paymentMethod,
          transactionId: razorpayPaymentId,
          destination: payment.booking.package?.name || payment.booking.hotel?.name,
          startDate: payment.booking.startDate.toLocaleDateString(),
          endDate: payment.booking.endDate.toLocaleDateString(),
        },
      });
    }

    return NextResponse.json(
      {
        success: true,
        message: 'Payment verified successfully',
        payment: {
          id: updatedPayment.id,
          status: updatedPayment.status,
          amount: updatedPayment.amount,
          method: updatedPayment.method,
          transactionId: updatedPayment.transactionId,
        },
      },
      { status: 200 }
    );
  } catch (error) {
    console.error('Payment verification error:', error);

    if (error instanceof z.ZodError) {
      return NextResponse.json(
        { error: 'Validation error', details: error.errors },
        { status: 400 }
      );
    }

    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}
