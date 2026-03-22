# 🚀 Quick Start - Travel Agency Pro

## 5-Minute Setup

### Step 1: Install Dependencies
```bash
npm install
```

### Step 2: Create Environment File
```bash
cp .env.example .env.local
```

### Step 3: Get Your API Keys

**Razorpay** (for payments):
1. Go to https://razorpay.com
2. Sign up and verify your account
3. Get your API keys from Dashboard → Settings → API Keys
4. Copy `Key ID` and `Key Secret` to `.env.local`

**Twilio WhatsApp** (for notifications):
1. Go to https://www.twilio.com
2. Sign up for a free account
3. Get WhatsApp Sandbox from Console
4. Copy `Account SID`, `Auth Token`, and WhatsApp number to `.env.local`

**Database**:
- Use MySQL locally or get a free tier from:
  - [PlanetScale](https://planetscale.com) (MySQL)
  - [Neon](https://neon.tech) (PostgreSQL)
  - [Aiven](https://aiven.io)

Update `DATABASE_URL` in `.env.local`

### Step 4: Setup Database
```bash
npm run db:generate
npm run db:push
```

### Step 5: Start Development Server
```bash
npm run dev
```

### Step 6: Access Your App
- **Homepage**: http://localhost:3000
- **Login**: http://localhost:3000/login
- **Admin**: http://localhost:3000/admin

---

## 📝 Test Credentials

### Test User Account
- **Phone**: +911234567890 (any number works)
- **Password**: Test@123

### Admin Account
Once the app is running, manually create an admin user by:
1. Signing up as a normal user
2. Updating the user role to 'admin' in database:
```sql
UPDATE user SET role = 'admin' WHERE phoneNumber = '+911234567890';
```

---

## 🔑 Key Features

| Feature | Status |
|---------|--------|
| Phone-based OTP Login | ✅ Ready |
| WhatsApp Notifications | ✅ Ready |
| Payment Processing (UPI, Card, Net Banking) | ✅ Ready |
| Admin Dashboard | ✅ Ready |
| Customer Management | ✅ Ready |
| Booking Management | ✅ Ready |
| Payment Analytics | ✅ Ready |

---

## 📂 Important Files

| File | Purpose |
|------|---------|
| `package.json` | Dependencies and scripts |
| `.env.example` | Environment variable template |
| `prisma/schema.prisma` | Database schema |
| `app/api/auth/` | Authentication routes |
| `app/api/payments/` | Payment processing |
| `components/auth/` | Login & OTP components |
| `components/payments/` | Payment checkout |
| `app/admin/` | Admin dashboard pages |

---

## 🆘 Troubleshooting

**OTP not sending?**
```
Check Twilio WhatsApp sandbox is active and phone number is added
```

**Payment page blank?**
```
Ensure NEXT_PUBLIC_RAZORPAY_KEY_ID is set and Razorpay script loads
```

**Database connection error?**
```
Verify DATABASE_URL format and MySQL server is running
```

**Port 3000 already in use?**
```
npm run dev -- -p 3001
```

---

## 📚 Next Steps

1. **Customize Branding**: Update logo, colors, and brand name
2. **Add Hotel/Package Data**: Populate database with your offerings
3. **Configure Email**: Add email notifications (optional)
4. **Deploy**: Push to GitHub and deploy to Vercel

---

## 🎯 Useful Commands

```bash
# Start dev server
npm run dev

# Build for production
npm run build

# Start production server
npm start

# Database commands
npm run db:push      # Push schema changes
npm run db:generate  # Regenerate Prisma client
npm run db:migrate   # Run migrations

# Linting
npm run lint
```

---

## 📞 Support

For issues:
1. Check SETUP.md for detailed setup guide
2. Review environment variables
3. Check console logs for errors
4. Verify API key formats

---

**Ready to go? Start with `npm install` and follow the 5 steps above!** 🎉
