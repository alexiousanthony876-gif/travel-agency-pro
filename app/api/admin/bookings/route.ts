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

    const bookings = await prisma.booking.findMany({
      include: {
        user: {
          select: { firstName: true, lastName: true },
        },
        package: {
          select: { destination: true },
        },
        hotel: {
          select: { location: true },
        },
      },
      orderBy: { createdAt: 'desc' },
    });

    const formattedBookings = bookings.map(booking => ({
      id: booking.id,
      bookingReference: booking.bookingReference,
      userName: `${booking.user.firstName} ${booking.user.lastName}`,
      destination: booking.package?.destination || booking.hotel?.location || 'Travel Package',
      startDate: booking.startDate,
      endDate: booking.endDate,
      numberOfPeople: booking.numberOfPeople,
      totalAmount: booking.totalAmount,
      status: booking.status,
      createdAt: booking.createdAt,
    }));

    return NextResponse.json(
      {
        bookings: formattedBookings,
        adminName: admin ? `${admin.firstName} ${admin.lastName}` : 'Admin',
      },
      { status: 200 }
    );
  } catch (error) {
    console.error('Bookings API error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}
