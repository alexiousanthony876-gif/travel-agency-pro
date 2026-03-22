import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@/lib/prisma';
import { hashPassword, generateOTP, getOTPExpiry, setAuthCookie, generateToken } from '@/lib/auth';
import { sendWhatsAppMessage } from '@/lib/whatsapp';
import { z } from 'zod';

const loginSchema = z.object({
  phoneNumber: z.string().min(10).max(13),
  password: z.string().optional(),
  firstName: z.string().optional(),
  lastName: z.string().optional(),
  isSignUp: z.boolean().optional(),
});

export async function POST(request: NextRequest) {
  try {
    const body = await request.json();
    const { phoneNumber, password, firstName, lastName, isSignUp } = loginSchema.parse(body);

    // Normalize phone number
    const normalizedPhone = phoneNumber.startsWith('+') 
      ? phoneNumber 
      : `+91${phoneNumber}`;

    // Check if user exists
    let user = await prisma.user.findUnique({
      where: { phoneNumber: normalizedPhone },
    });

    if (isSignUp) {
      // Sign up flow
      if (user) {
        return NextResponse.json(
          { error: 'User already exists' },
          { status: 400 }
        );
      }

      if (!firstName || !lastName || !password) {
        return NextResponse.json(
          { error: 'Missing required fields for signup' },
          { status: 400 }
        );
      }

      // Create new user
      user = await prisma.user.create({
        data: {
          phoneNumber: normalizedPhone,
          firstName,
          lastName,
          passwordHash: await hashPassword(password),
          role: 'user',
        },
      });
    } else {
      // Login flow
      if (!user) {
        return NextResponse.json(
          { error: 'User not found' },
          { status: 404 }
        );
      }

      if (user.passwordHash && password) {
        const crypto = await import('crypto');
        const bcrypt = await import('bcryptjs');
        const isValidPassword = await bcrypt.compare(password, user.passwordHash);
        
        if (!isValidPassword) {
          return NextResponse.json(
            { error: 'Invalid password' },
            { status: 401 }
          );
        }
      }
    }

    // Generate OTP
    const otp = generateOTP();
    const expiresAt = getOTPExpiry();

    // Store OTP in database
    await prisma.oTP.create({
      data: {
        userId: user.id,
        code: otp,
        expiresAt,
      },
    });

    // Send OTP via WhatsApp
    const otpResult = await sendWhatsAppMessage({
      userId: user.id,
      phoneNumber: normalizedPhone,
      messageType: 'otp',
      data: {
        otp,
        purpose: 'login',
      },
    });

    console.log('[v0] OTP send result:', otpResult);

    return NextResponse.json(
      {
        message: 'OTP sent to WhatsApp',
        userId: user.id,
        phoneNumber: normalizedPhone,
        otpSent: true,
      },
      { status: 200 }
    );
  } catch (error) {
    console.error('Login error:', error);
    
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
