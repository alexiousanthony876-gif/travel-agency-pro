# Travel Agency Pro - Setup Guide

## 🚀 Quick Start

### Prerequisites
- Node.js 18+ and npm/yarn/pnpm
- MySQL 8.0+ or compatible database
- Twilio Account (for WhatsApp)
- Razorpay Account (for Payments)

### Installation Steps

1. **Clone and Install Dependencies**
```bash
npm install
# or
pnpm install
```

2. **Environment Configuration**
```bash
cp .env.example .env.local
```

Edit `.env.local` with your credentials:
```env
# Database
DATABASE_URL=mysql://user:password@localhost:3306/travel_agency_pro

# JWT
JWT_SECRET=your-super-secret-key-here

# Razorpay (Get from https://razorpay.com)
NEXT_PUBLIC_RAZORPAY_KEY_ID=your_key_id
RAZORPAY_KEY_SECRET=your_key_secret

# Twilio WhatsApp (Get from https://www.twilio.com)
TWILIO_ACCOUNT_SID=your_account_sid
TWILIO_AUTH_TOKEN=your_auth_token
TWILIO_WHATSAPP_NUMBER=whatsapp:+14155552671

# OTP Settings
OTP_EXPIRY_MINUTES=10
OTP_MAX_ATTEMPTS=3
```

3. **Setup Database**
```bash
# Generate Prisma client
npm run db:generate

# Push schema to database
npm run db:push
```

4. **Create Admin User** (Run this once)
```bash
# Add to your database directly or use admin panel
# Or create a seed script
```

5. **Start Development Server**
```bash
npm run dev
```

Visit `http://localhost:3000`

---

## 📱 Features

### 🔐 Authentication
- **Phone-based OTP Login**: Users receive 6-digit OTP via WhatsApp
- **Password-less Authentication**: Secure JWT token management
- **Automatic Session Management**: HTTP-only cookies for token storage

### 💳 Payment Integration
- **Razorpay Integration**: Multiple payment methods
  - UPI (Google Pay, PhonePe, Paytm)
  - Credit/Debit Cards (Visa, Mastercard, RuPay)
  - Net Banking (All major Indian banks)
- **Payment Verification**: Signature verification for security
- **Refund Management**: Built-in refund processing

### 💬 WhatsApp Integration
- **Booking Confirmations**: Instant WhatsApp updates
- **Payment Receipts**: Transaction confirmations via WhatsApp
- **Reminders**: Pre-trip notifications
- **Cancellation Notices**: Refund information

### 👨‍💼 Admin Dashboard
- **Customer Management**: View and manage all customers
- **Booking Tracking**: Monitor all bookings with status
- **Payment Analytics**: Revenue tracking and payment method statistics
- **WhatsApp Message Logs**: Track all communications
- **Real-time Statistics**: Dashboard with key metrics

### 🛫 User Features
- **Book Packages/Hotels**: Browse and book travel options
- **Payment Processing**: Seamless checkout with multiple methods
- **Booking History**: View all past and upcoming bookings
- **Profile Management**: Update personal information

---

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/login` - Send OTP
- `POST /api/auth/verify-otp` - Verify and create session
- `POST /api/auth/logout` - Logout user

### Payments
- `POST /api/payments/create-order` - Create Razorpay order
- `POST /api/payments/verify` - Verify payment signature

### User
- `GET /api/user/profile` - Get user profile and bookings

### Admin
- `GET /api/admin/dashboard-stats` - Dashboard statistics
- `GET /api/admin/customers` - List all customers
- `GET /api/admin/bookings` - List all bookings
- `GET /api/admin/payments` - List all payments

---

## 🏗️ Project Structure

```
travel-agency-pro/
├── app/
│   ├── page.tsx                 # Homepage
│   ├── login/page.tsx           # Login page
│   ├── dashboard/page.tsx       # User dashboard
│   ├── admin/
│   │   ├── page.tsx            # Admin dashboard
│   │   ├── customers/
│   │   ├── bookings/
│   │   ├── payments/
│   │   ├── hotels/
│   │   ├── packages/
│   │   ├── messages/
│   │   ├── analytics/
│   │   └── settings/
│   ├── api/
│   │   ├── auth/
│   │   ├── payments/
│   │   ├── user/
│   │   └── admin/
│   └── layout.tsx
├── components/
│   ├── auth/
│   │   ├── LoginForm.tsx
│   │   └── OTPInput.tsx
│   ├── payments/
│   │   └── PaymentCheckout.tsx
│   ├── admin/
│   │   ├── Sidebar.tsx
│   │   └── Header.tsx
├── lib/
│   ├── auth.ts                  # JWT & password utilities
│   ├── razorpay.ts             # Razorpay integration
│   ├── whatsapp.ts             # WhatsApp/Twilio integration
│   └── prisma.ts               # Database client
├── prisma/
│   └── schema.prisma           # Database schema
├── package.json
├── tsconfig.json
├── tailwind.config.ts
├── next.config.js
└── .env.example
```

---

## 🔒 Security Features

- **Password Hashing**: bcryptjs for secure password storage
- **JWT Authentication**: Signed tokens with expiration
- **OTP Verification**: Time-limited one-time passwords
- **Razorpay Signature Verification**: Payment verification
- **HTTP-only Cookies**: Secure token storage
- **Environment Variables**: Sensitive data in .env only
- **SQL Injection Prevention**: Prisma ORM parameterized queries
- **CORS Ready**: Configured for multi-domain support

---

## 📊 Database Schema

### Core Tables
- **Users**: Customer and admin accounts
- **OTP**: One-time passwords for login
- **Bookings**: Travel and hotel bookings
- **Payments**: Payment transactions with Razorpay data
- **Packages**: Travel packages
- **Hotels**: Hotel listings
- **WhatsAppLog**: Message delivery tracking
- **AdminLog**: Admin action audit trail

---

## 🚀 Deployment

### Deploy to Vercel
```bash
# Connect GitHub repository
# Push to main branch
# Vercel auto-deploys

# Set environment variables in Vercel dashboard
```

### Deploy to Other Platforms
1. Build the project:
```bash
npm run build
```

2. Set environment variables on your hosting platform

3. Deploy the build output

---

## 🛠️ Development

### Run Tests
```bash
npm test
```

### Build for Production
```bash
npm run build
npm start
```

### Database Migrations
```bash
npm run db:migrate
```

---

## 📞 Support & Troubleshooting

### Common Issues

**1. OTP not receiving?**
- Check Twilio credentials in .env
- Verify WhatsApp number format (+country code)
- Check Twilio account balance

**2. Payment not working?**
- Verify Razorpay keys in .env
- Check if amount is in paise (multiply rupees by 100)
- Ensure test vs production keys match environment

**3. Database connection issues?**
- Verify DATABASE_URL format
- Check MySQL server is running
- Confirm user permissions

---

## 📚 Additional Resources

- [Next.js Documentation](https://nextjs.org/docs)
- [Prisma Documentation](https://www.prisma.io/docs)
- [Razorpay API Docs](https://razorpay.com/docs/)
- [Twilio WhatsApp API](https://www.twilio.com/docs/whatsapp)
- [Tailwind CSS](https://tailwindcss.com/docs)

---

## 📄 License

This project is licensed under the MIT License.

---

**Version**: 1.0.0  
**Last Updated**: 2024  
**Maintained By**: Travel Agency Pro Team
