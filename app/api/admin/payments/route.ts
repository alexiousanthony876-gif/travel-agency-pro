import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@/lib/prisma';
import { getAuthCookie, verifyToken } from '@/lib/auth';

export async function GET(request: NextRequest) {
  try {
    const token = await getAuthCookie();
    if (!token) {
      return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
    }

    const decoded = verifyToken(token);
    if (!decoded || decoded.role !== 'admin') {
      return NextResponse.json({ error: 'Forbidden' }, { status: 403 });
    }

    const admin = await prisma.user.findUnique({
      where: { id: decoded.userId },
    });

    // Get all payments
    const payments = await prisma.payment.findMany({
      include: {
        user: {
          select: { firstName: true, lastName: true },
        },
        booking: {
          select: { bookingReference: true },
        },
      },
      orderBy: { createdAt: 'desc' },
    });

    // Calculate total revenue
    const totalRevenue = await prisma.payment.aggregate({
      _sum: { amount: true },
      where: { status: 'completed' },
    });

    const formattedPayments = payments.map(payment => ({
      id: payment.id,
      transactionId: payment.razorpayPaymentId || payment.transactionId || 'N/A',
      bookingReference: payment.booking?.bookingReference || 'N/A',
      customerName: `${payment.user.firstName} ${payment.user.lastName}`,
      amount: payment.amount,
      method: payment.method,
      status: payment.status,
      createdAt: payment.createdAt,
    }));

    return NextResponse.json(
      {
        payments: formattedPayments,
        totalRevenue: totalRevenue._sum.amount || 0,
        adminName: admin ? `${admin.firstName} ${admin.lastName}` : 'Admin',
      },
      { status: 200 }
    );
  } catch (error) {
    console.error('Payments API error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}
