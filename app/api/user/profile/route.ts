import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@/lib/prisma';
import { getAuthCookie, verifyToken } from '@/lib/auth';

export async function GET(request: NextRequest) {
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

    // Get user
    const user = await prisma.user.findUnique({
      where: { id: decoded.userId },
      select: {
        id: true,
        phoneNumber: true,
        firstName: true,
        lastName: true,
        email: true,
        role: true,
      },
    });

    if (!user) {
      return NextResponse.json(
        { error: 'User not found' },
        { status: 404 }
      );
    }

    // Get user's bookings
    const bookings = await prisma.booking.findMany({
      where: { userId: decoded.userId },
      select: {
        id: true,
        bookingReference: true,
        startDate: true,
        endDate: true,
        numberOfPeople: true,
        totalAmount: true,
        status: true,
        package: {
          select: { destination: true },
        },
        hotel: {
          select: { location: true },
        },
      },
      orderBy: { createdAt: 'desc' },
      take: 10,
    });

    const formattedBookings = bookings.map(booking => ({
      ...booking,
      destination: booking.package?.destination || booking.hotel?.location || 'Travel Package',
    }));

    return NextResponse.json(
      {
        user,
        bookings: formattedBookings,
      },
      { status: 200 }
    );
  } catch (error) {
    console.error('Profile fetch error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}
