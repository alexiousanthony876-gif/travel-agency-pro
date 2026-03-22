# 🌍 Travel Agency Pro - Complete Booking Platform

<div align="center">

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)
![Status](https://img.shields.io/badge/status-Production%20Ready-brightgreen.svg)

A full-featured travel agency booking platform with phone-based OTP authentication, integrated payment processing, WhatsApp notifications, and comprehensive admin dashboard.

[Features](#-features) • [Quick Start](#-quick-start) • [Documentation](#-documentation) • [Tech Stack](#-tech-stack)

</div>

---

## ✨ Features

### 🔐 **Authentication**
- Phone-based OTP login via WhatsApp
- Secure JWT token management
- Password hashing with bcryptjs
- Automatic session management with HTTP-only cookies
- OTP resend with cooldown timer

### 💳 **Payment Integration**
- **Razorpay Integration** with multiple payment methods:
  - 💳 Credit/Debit Cards (Visa, Mastercard, RuPay)
  - 📱 UPI (Google Pay, PhonePe, Paytm)
  - 🏦 Net Banking (All major Indian banks)
- Payment verification with signature validation
- Refund processing and tracking
- Complete payment history

### 💬 **WhatsApp Integration**
- Real-time booking confirmations via WhatsApp
- Payment receipts and transaction notifications
- Pre-trip reminders
- Cancellation notices with refund information
- Message delivery tracking and logging

### 👨‍💼 **Admin Dashboard**
- **Dashboard**: Key metrics and analytics
- **Customer Management**: View, search, filter customers
- **Booking Management**: Track all bookings with status
- **Payment Analytics**: Revenue tracking and payment method breakdown
- **WhatsApp Logs**: Monitor all communications
- **Analytics & Charts**: Business insights and trends
- **System Settings**: Configure platform settings

### 👥 **User Features**
- Browse and book travel packages
- Hotel booking options
- Secure checkout with multiple payment methods
- Booking history and details
- Profile management
- Real-time payment status

### 🎨 **Design**
- Responsive mobile-first design
- Modern UI with Tailwind CSS
- Accessible components
- Professional branding
- Smooth animations and transitions

---

## 🚀 Quick Start

### Prerequisites
- Node.js 18+
- MySQL 8.0+ (or PlanetScale, Neon, Aiven)
- Razorpay Account
- Twilio Account

### Installation (5 minutes)

```bash
# 1. Install dependencies
npm install

# 2. Copy environment template
cp .env.example .env.local

# 3. Add your API keys to .env.local
# Get keys from:
# - Razorpay: https://razorpay.com
# - Twilio: https://www.twilio.com

# 4. Setup database
npm run db:generate
npm run db:push

# 5. Start development server
npm run dev
```

Visit `http://localhost:3000`

### First Login
1. Go to http://localhost:3000/login
2. Enter any phone number
3. Create a password
4. Get OTP from WhatsApp
5. Enter OTP to confirm

### Admin Access
Update user role in database:
```sql
UPDATE User SET role = 'admin' WHERE phoneNumber = '+911234567890';
```

Then access http://localhost:3000/admin

---

## 📚 Documentation

| Document | Purpose |
|----------|---------|
| **[QUICKSTART.md](QUICKSTART.md)** | 5-minute setup guide |
| **[SETUP.md](SETUP.md)** | Complete setup instructions |
| **[IMPLEMENTATION_GUIDE.md](IMPLEMENTATION_GUIDE.md)** | Step-by-step implementation |
| **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** | Feature overview |
| **[FILES_CREATED.md](FILES_CREATED.md)** | Complete file listing |

---

## 🛠️ Tech Stack

### Frontend
- **React 19** - UI library
- **Next.js 16** - Framework with App Router
- **TypeScript** - Type safety
- **Tailwind CSS** - Styling
- **Recharts** - Data visualization
- **React Hook Form** - Form management
- **Zod** - Schema validation

### Backend
- **Next.js API Routes** - Serverless functions
- **Prisma ORM** - Database access
- **JWT** - Authentication
- **bcryptjs** - Password hashing

### Integrations
- **Razorpay** - Payment processing
- **Twilio** - WhatsApp messaging

### Database
- **MySQL** - Primary database
- **Prisma** - ORM with migrations

### Deployment
- **Vercel** - Hosting and deployment

---

## 📊 Project Structure

```
travel-agency-pro/
├── app/                    # Next.js app directory
│   ├── page.tsx           # Homepage
│   ├── login/             # Login page
│   ├── dashboard/         # User dashboard
│   ├── admin/             # Admin pages
│   ├── api/               # API routes
│   └── layout.tsx         # Root layout
├── components/            # React components
│   ├── auth/             # Authentication UI
│   ├── payments/         # Payment UI
│   └── admin/            # Admin components
├── lib/                  # Utilities
│   ├── auth.ts          # Auth utilities
│   ├── razorpay.ts      # Payment service
│   ├── whatsapp.ts      # Messaging service
│   └── prisma.ts        # Database client
├── prisma/              # Database
│   └── schema.prisma    # Schema definition
└── package.json         # Dependencies
```

---

## 🔐 Security Features

✅ Password hashing with bcryptjs  
✅ JWT token authentication  
✅ OTP-based login verification  
✅ Razorpay signature verification  
✅ HTTP-only cookie storage  
✅ Environment variable protection  
✅ Parameterized queries (Prisma ORM)  
✅ Input validation with Zod  
✅ Admin role-based access control  
✅ HTTPS ready  

---

## 📱 API Endpoints

### Authentication
- `POST /api/auth/login` - Send OTP
- `POST /api/auth/verify-otp` - Verify OTP and create session
- `POST /api/auth/resend-otp` - Resend OTP
- `POST /api/auth/logout` - Logout

### Payments
- `POST /api/payments/create-order` - Create payment order
- `POST /api/payments/verify` - Verify payment

### User
- `GET /api/user/profile` - Get user profile and bookings

### Admin
- `GET /api/admin/dashboard-stats` - Dashboard statistics
- `GET /api/admin/customers` - List customers
- `GET /api/admin/bookings` - List bookings
- `GET /api/admin/payments` - List payments

---

## 🎯 Key Workflows

### User Login
```
User → Phone/Password → OTP via WhatsApp → Verify → Dashboard
```

### Booking & Payment
```
Browse → Book → Payment → Razorpay → Verify → Receipt → Dashboard
```

### WhatsApp Notifications
```
Booking Created → WhatsApp Confirmation
Payment Done → WhatsApp Receipt
Trip Reminder → WhatsApp Alert
Cancellation → WhatsApp Refund Info
```

### Admin Operations
```
Admin Login → Dashboard → View Stats → Manage Data → Send Messages
```

---

## 🚀 Deployment

### Deploy to Vercel

1. **Push to GitHub**
```bash
git add .
git commit -m "Initial commit"
git push origin main
```

2. **Import to Vercel**
   - Go to https://vercel.com
   - Click "New Project"
   - Select your GitHub repo
   - Add environment variables
   - Click "Deploy"

3. **Set Environment Variables**
   - Database URL
   - Razorpay keys
   - Twilio keys
   - JWT secret

### Other Hosting
See [IMPLEMENTATION_GUIDE.md](IMPLEMENTATION_GUIDE.md) for alternatives.

---

## 📈 Performance

- ✅ Optimized images with Next.js Image component
- ✅ CSS minification with Tailwind
- ✅ Code splitting and lazy loading
- ✅ Database query optimization
- ✅ SWR caching for data fetching
- ✅ Production-ready build with Turbopack

---

## 🐛 Troubleshooting

**OTP not sending?**
- Check Twilio WhatsApp sandbox configuration
- Verify phone number format (+91XXXXXXXXXX)
- Ensure Twilio account has balance

**Payment failing?**
- Verify Razorpay keys are correct
- Check if amount is in paise (rupees × 100)
- Use test mode for testing

**Database issues?**
- Verify DATABASE_URL is correct
- Check MySQL service is running
- Confirm user permissions

See [SETUP.md](SETUP.md) for more troubleshooting.

---

## 📄 Database Schema

- **User** - Customer and admin accounts
- **OTP** - One-time password tracking
- **Package** - Travel packages
- **Hotel** - Hotel listings
- **Booking** - Trip bookings
- **Payment** - Payment transactions
- **WhatsAppLog** - Message delivery logs
- **AdminLog** - Admin action audit trail

---

## 🤝 Contributing

This is a complete production-ready application. Feel free to:
- Customize branding
- Add new features
- Modify payment methods
- Extend database schema
- Deploy to your servers

---

## 📞 Support

**Documentation:**
- [QUICKSTART.md](QUICKSTART.md) - 5-minute setup
- [SETUP.md](SETUP.md) - Detailed setup
- [IMPLEMENTATION_GUIDE.md](IMPLEMENTATION_GUIDE.md) - Implementation steps
- [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) - Feature overview
- [FILES_CREATED.md](FILES_CREATED.md) - File listing

**External Resources:**
- [Razorpay Docs](https://razorpay.com/docs/)
- [Twilio Docs](https://www.twilio.com/docs/)
- [Prisma Docs](https://www.prisma.io/docs/)
- [Next.js Docs](https://nextjs.org/docs)

---

## 📄 License

MIT License - See LICENSE file for details

---

## 🎉 Ready to Launch?

1. **Read**: [QUICKSTART.md](QUICKSTART.md)
2. **Setup**: Follow the 5-minute guide
3. **Configure**: Add your API keys
4. **Deploy**: Push to Vercel
5. **Launch**: Go live!

---

<div align="center">

**Built with ❤️ for travel agencies**

Version 1.0.0 | Production Ready | MIT License

</div>
