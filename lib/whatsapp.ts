import twilio from 'twilio';
import { prisma } from './prisma';

const client = twilio(
  process.env.TWILIO_ACCOUNT_SID,
  process.env.TWILIO_AUTH_TOKEN
);

const TWILIO_WHATSAPP_NUMBER = process.env.TWILIO_WHATSAPP_NUMBER || 'whatsapp:+14155552671';

export interface WhatsAppMessageParams {
  userId: number;
  phoneNumber: string;
  messageType: 'otp' | 'booking_confirmation' | 'payment_receipt' | 'reminder' | 'cancellation';
  bookingId?: number;
  data?: Record<string, any>;
}

// OTP message template
export async function generateOTPMessage(data: any): Promise<string> {
  const otp = data.otp || '000000';
  return `🔐 *Your Login OTP*\n\n` +
    `Your one-time password is:\n\n` +
    `*${otp}*\n\n` +
    `This code will expire in 10 minutes.\n` +
    `Do not share this code with anyone.\n\n` +
    `If you didn't request this, please ignore this message.`;
}

export async function generateBookingConfirmationMessage(data: any): Promise<string> {
  return `🎉 *Travel Booking Confirmation*\n\n` +
    `Booking Reference: ${data.bookingReference}\n` +
    `Destination: ${data.destination || 'Your selected package'}\n` +
    `Check-in: ${data.startDate}\n` +
    `Check-out: ${data.endDate}\n` +
    `Travelers: ${data.numberOfPeople}\n` +
    `Total Amount: ₹${data.totalAmount}\n\n` +
    `Status: ${data.status || 'Pending'}\n` +
    `Thank you for booking with us! 🙏`;
}

export async function generatePaymentReceiptMessage(data: any): Promise<string> {
  return `💳 *Payment Receipt*\n\n` +
    `Booking Reference: ${data.bookingReference}\n` +
    `Amount: ₹${data.amount}\n` +
    `Payment Method: ${data.method?.toUpperCase()}\n` +
    `Transaction ID: ${data.transactionId || 'N/A'}\n` +
    `Date: ${new Date().toLocaleDateString()}\n\n` +
    `Status: ✅ Payment Successful\n` +
    `Your booking is now confirmed!`;
}

export async function generateReminderMessage(data: any): Promise<string> {
  return `📅 *Booking Reminder*\n\n` +
    `Your trip to ${data.destination} is coming up!\n` +
    `Check-in Date: ${data.startDate}\n` +
    `Booking Reference: ${data.bookingReference}\n\n` +
    `Please ensure you have all required documents ready.\n` +
    `For assistance, contact our support team.`;
}

export async function generateCancellationMessage(data: any): Promise<string> {
  return `❌ *Booking Cancellation*\n\n` +
    `Your booking has been cancelled.\n` +
    `Booking Reference: ${data.bookingReference}\n` +
    `Refund Amount: ₹${data.refundAmount || data.totalAmount}\n\n` +
    `The refund will be processed within 5-7 business days.\n` +
    `For more details, contact support.`;
}

async function generateMessage(
  messageType: string,
  data: Record<string, any>
): Promise<string> {
  switch (messageType) {
    case 'otp':
      return generateOTPMessage(data);
    case 'booking_confirmation':
      return generateBookingConfirmationMessage(data);
    case 'payment_receipt':
      return generatePaymentReceiptMessage(data);
    case 'reminder':
      return generateReminderMessage(data);
    case 'cancellation':
      return generateCancellationMessage(data);
    default:
      return 'Hello from Travel Agency Pro!';
  }
}

export async function sendWhatsAppMessage(params: WhatsAppMessageParams): Promise<{
  success: boolean;
  messageId?: string;
  error?: string;
}> {
  try {
    // Validate environment variables
    if (!process.env.TWILIO_ACCOUNT_SID || !process.env.TWILIO_AUTH_TOKEN) {
      console.error('[v0] Missing Twilio credentials in environment variables');
      return {
        success: false,
        error: 'Twilio credentials not configured',
      };
    }

    // Format phone number for Twilio
    const toNumber = params.phoneNumber.startsWith('+') 
      ? `whatsapp:${params.phoneNumber}` 
      : `whatsapp:+91${params.phoneNumber}`;

    const messageBody = await generateMessage(params.messageType, params.data || {});

    console.log('[v0] Sending WhatsApp message');
    console.log('[v0] To:', toNumber);
    console.log('[v0] From:', TWILIO_WHATSAPP_NUMBER);
    console.log('[v0] Message type:', params.messageType);

    const message = await client.messages.create({
      from: TWILIO_WHATSAPP_NUMBER,
      to: toNumber,
      body: messageBody,
    });

    console.log('[v0] Message sent successfully with SID:', message.sid);

    // Log the WhatsApp message in database
    try {
      await prisma.whatsAppLog.create({
        data: {
          userId: params.userId,
          bookingId: params.bookingId,
          phoneNumber: params.phoneNumber,
          messageType: params.messageType,
          messageBody,
          status: 'sent',
          twilioSid: message.sid,
        },
      });
    } catch (dbError) {
      console.error('[v0] Failed to log WhatsApp message:', dbError);
    }

    return {
      success: true,
      messageId: message.sid,
    };
  } catch (error) {
    const errorMessage = error instanceof Error ? error.message : 'Unknown error';
    console.error('[v0] WhatsApp message sending error:', errorMessage);

    // Log failed message attempt
    try {
      await prisma.whatsAppLog.create({
        data: {
          userId: params.userId,
          bookingId: params.bookingId,
          phoneNumber: params.phoneNumber,
          messageType: params.messageType,
          messageBody: JSON.stringify(params.data),
          status: 'failed',
          errorDetails: errorMessage,
        },
      });
    } catch (dbError) {
      console.error('[v0] Failed to log WhatsApp error:', dbError);
    }

    return {
      success: false,
      error: errorMessage,
    };
  }
}

export async function sendBulkWhatsAppMessages(
  messages: WhatsAppMessageParams[]
): Promise<Array<{ phoneNumber: string; success: boolean; messageId?: string }>> {
  const results = await Promise.all(
    messages.map(msg => sendWhatsAppMessage(msg))
  );

  return messages.map((msg, index) => ({
    phoneNumber: msg.phoneNumber,
    success: results[index].success,
    messageId: results[index].messageId,
  }));
}
