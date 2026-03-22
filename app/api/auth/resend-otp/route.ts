import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@/lib/prisma';
import { generateOTP, getOTPExpiry } from '@/lib/auth';
import { sendWhatsAppMessage } from '@/lib/whatsapp';
import { z } from 'zod';

const resendOTPSchema = z.object({
  userId: z.number(),
});

export async function POST(request: NextRequest) {
  try {
    const body = await request.json();
    const { userId } = resendOTPSchema.parse(body);

    // Get user
    const user = await prisma.user.findUnique({
      where: { id: userId },
    });

    if (!user) {
      return NextResponse.json(
        { error: 'User not found' },
        { status: 404 }
      );
    }

    // Generate new OTP
    const otp = generateOTP();
    const expiresAt = getOTPExpiry();

    // Store OTP in database
    await prisma.oTP.create({
      data: {
        userId,
        code: otp,
        expiresAt,
      },
    });

    // Send OTP via WhatsApp
    const result = await sendWhatsAppMessage({
      userId,
      phoneNumber: user.phoneNumber,
      messageType: 'booking_confirmation',
      data: {
        otp,
        purpose: 'login',
      },
    });

    if (!result.success) {
      return NextResponse.json(
        { error: 'Failed to send OTP via WhatsApp' },
        { status: 500 }
      );
    }

    return NextResponse.json(
      {
        message: 'OTP resent successfully',
        otpSent: true,
      },
      { status: 200 }
    );
  } catch (error) {
    console.error('Resend OTP error:', error);

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
