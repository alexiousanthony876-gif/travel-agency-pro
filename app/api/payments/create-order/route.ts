import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@/lib/prisma';
import { createRazorpayOrder } from '@/lib/razorpay';
import { getAuthCookie, verifyToken } from '@/lib/auth';
import { z } from 'zod';

const createOrderSchema = z.object({
  bookingId: z.number(),
});

export async function POST(request: NextRequest) {
  try {
    // Verify authentication
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
    const { bookingId } = createOrderSchema.parse(body);

    // Get booking
    const booking = await prisma.booking.findUnique({
      where: { id: bookingId },
      include: {
        user: true,
        package: true,
        hotel: true,
      },
    });

    if (!booking) {
      return NextResponse.json(
        { error: 'Booking not found' },
        { status: 404 }
      );
    }

    // Check if user owns this booking
    if (booking.userId !== decoded.userId) {
      return NextResponse.json(
        { error: 'Forbidden' },
        { status: 403 }
      );
    }

    // Check if payment already exists
    const existingPayment = await prisma.payment.findUnique({
      where: { bookingId },
    });

    if (existingPayment && existingPayment.status === 'completed') {
      return NextResponse.json(
        { error: 'Payment already completed' },
        { status: 400 }
      );
    }

    // Create Razorpay order
    const razorpayResult = await createRazorpayOrder({
      amount: Math.round(booking.totalAmount * 100), // Convert to paise
      receipt: booking.bookingReference,
      description: `Booking for ${booking.package?.name || booking.hotel?.name || 'Travel Package'}`,
      customer: {
        name: `${booking.user.firstName} ${booking.user.lastName}`,
        contact: booking.user.phoneNumber,
        email: booking.user.email || undefined,
      },
      notes: {
        bookingId: booking.id,
        bookingReference: booking.bookingReference,
      },
    });

    if (!razorpayResult.success) {
      return NextResponse.json(
        { error: razorpayResult.error },
        { status: 500 }
      );
    }

    // Create or update payment record
    let payment = await prisma.payment.findUnique({
      where: { bookingId },
    });

    if (payment) {
      payment = await prisma.payment.update({
        where: { id: payment.id },
        data: {
          razorpayOrderId: razorpayResult.orderId,
          status: 'pending',
          metadata: JSON.stringify({
            retryCount: (JSON.parse(payment.metadata || '{}').retryCount || 0) + 1,
          }),
        },
      });
    } else {
      payment = await prisma.payment.create({
        data: {
          userId: booking.userId,
          bookingId,
          amount: booking.totalAmount,
          method: 'pending',
          razorpayOrderId: razorpayResult.orderId,
          status: 'pending',
          metadata: JSON.stringify({}),
        },
      });
    }

    return NextResponse.json(
      {
        success: true,
        orderId: razorpayResult.orderId,
        amount: razorpayResult.amount,
        currency: razorpayResult.currency,
        paymentId: payment.id,
        keyId: process.env.NEXT_PUBLIC_RAZORPAY_KEY_ID,
        customer: {
          name: `${booking.user.firstName} ${booking.user.lastName}`,
          email: booking.user.email,
          contact: booking.user.phoneNumber,
        },
      },
      { status: 200 }
    );
  } catch (error) {
    console.error('Payment creation error:', error);

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
