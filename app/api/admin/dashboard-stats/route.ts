import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@/lib/prisma';
import { getAuthCookie, verifyToken } from '@/lib/auth';

export async function GET(request: NextRequest) {
  try {
    // Verify authentication and admin role
    const token = await getAuthCookie();
    if (!token) {
      return NextResponse.json(
        { error: 'Unauthorized' },
        { status: 401 }
      );
    }

    const decoded = verifyToken(token);
    if (!decoded || decoded.role !== 'admin') {
      return NextResponse.json(
        { error: 'Forbidden - Admin access required' },
        { status: 403 }
      );
    }

    // Get user for admin name
    const admin = await prisma.user.findUnique({
      where: { id: decoded.userId },
    });

    if (!admin) {
      return NextResponse.json(
        { error: 'Admin not found' },
        { status: 404 }
      );
    }

    // Get stats
    const totalUsers = await prisma.user.count({
      where: { role: 'user' },
    });

    const totalBookings = await prisma.booking.count();

    const totalRevenue = await prisma.payment.aggregate({
      _sum: { amount: true },
      where: { status: 'completed' },
    });

    const pendingPayments = await prisma.payment.count({
      where: { status: 'pending' },
    });

    // Get recent bookings
    const recentBookings = await prisma.booking.findMany({
      take: 10,
      orderBy: { createdAt: 'desc' },
      select: {
        id: true,
        bookingReference: true,
        status: true,
        totalAmount: true,
        startDate: true,
        user: {
          select: {
            firstName: true,
            lastName: true,
          },
        },
      },
    });

    // Generate chart data (last 7 days)
    const chartData = generateChartData(totalRevenue._sum.amount || 0, 7);

    return NextResponse.json(
      {
        totalUsers,
        totalBookings,
        totalRevenue: totalRevenue._sum.amount || 0,
        pendingPayments,
        recentBookings,
        chartData,
        adminName: `${admin.firstName} ${admin.lastName}`,
      },
      { status: 200 }
    );
  } catch (error) {
    console.error('Dashboard stats error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}

function generateChartData(totalRevenue: number, days: number) {
  const data = [];
  const now = new Date();

  for (let i = days - 1; i >= 0; i--) {
    const date = new Date(now);
    date.setDate(date.getDate() - i);

    data.push({
      name: date.toLocaleDateString('en-IN', { month: 'short', day: 'numeric' }),
      revenue: Math.floor(Math.random() * (totalRevenue / days) + totalRevenue / (days * 2)),
    });
  }

  return data;
}
