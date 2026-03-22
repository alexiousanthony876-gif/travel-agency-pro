# Setup Verification Checklist

Use this file to verify your Travel Agency Pro setup before going live.

---

## Phase 1: Twilio WhatsApp Configuration (OTP)

### Pre-Setup
- [ ] You have a Twilio account created
- [ ] Your Twilio account email is verified
- [ ] Your Twilio account phone is verified

### Sandbox Configuration
- [ ] WhatsApp Sandbox accessed at: https://console.twilio.com/us1/develop/sms/try-it-out/whatsapp-sandbox
- [ ] Joined the sandbox with message: `join [CODE]` sent from your phone
- [ ] Your phone number appears in "Joined Numbers" section
- [ ] You have the Twilio WhatsApp number (format: whatsapp:+14155552671)

### Environment Setup
- [ ] `.env.local` file created in project root
- [ ] TWILIO_ACCOUNT_SID is set and correct (starts with AC)
- [ ] TWILIO_AUTH_TOKEN is set and correct (32+ characters)
- [ ] TWILIO_WHATSAPP_NUMBER is set in format: whatsapp:+14155552671
- [ ] All other required variables are set:
  - [ ] DATABASE_URL
  - [ ] JWT_SECRET
  - [ ] NEXT_PUBLIC_RAZORPAY_KEY_ID
  - [ ] RAZORPAY_KEY_SECRET

### Code Setup
- [ ] `/lib/whatsapp.ts` has OTP message generator
- [ ] `/app/api/auth/login/route.ts` uses messageType: 'otp'
- [ ] LoginForm component is present at `/components/auth/LoginForm.tsx`
- [ ] OTPInput component is present at `/components/auth/OTPInput.tsx`

### Testing
- [ ] Dev server started: `npm run dev`
- [ ] Navigate to: http://localhost:3000/login
- [ ] Enter phone number (the one that joined sandbox)
- [ ] Click "Send OTP"
- [ ] Check WhatsApp - OTP message received within 10 seconds
- [ ] Copy OTP and verify on login page
- [ ] Successfully logged in

---

## Phase 2: Database Configuration

### Database Setup
- [ ] MySQL database created
- [ ] Database URL added to .env.local
- [ ] Database schema exists (or ready to run migrations)

### Prisma Setup
- [ ] `/prisma/schema.prisma` file exists
- [ ] All models defined: User, OTP, Payment, WhatsAppLog, etc.
- [ ] Run: `npm run db:generate` (generates Prisma client)
- [ ] Run: `npm run db:push` (creates tables)

### Testing
- [ ] Tables created in database
- [ ] Can view tables in database UI
- [ ] Sample data can be inserted and retrieved

---

## Phase 3: Payment Integration

### Razorpay Setup
- [ ] Razorpay account created
- [ ] API keys obtained from Razorpay dashboard
- [ ] NEXT_PUBLIC_RAZORPAY_KEY_ID added to .env.local
- [ ] RAZORPAY_KEY_SECRET added to .env.local

### Payment Code
- [ ] `/lib/razorpay.ts` exists and is properly configured
- [ ] `/app/api/payments/create-order/route.ts` exists
- [ ] `/app/api/payments/verify/route.ts` exists
- [ ] Payment checkout component at `/components/payments/PaymentCheckout.tsx`

### Testing
- [ ] Navigate to: http://localhost:3000/dashboard
- [ ] Find a booking and click "Pay Now"
- [ ] Payment form appears with UPI/Card options
- [ ] Payment creation works (order ID generated)
- [ ] Payment verification works after successful payment

---

## Phase 4: User Authentication & Dashboard

### User Features
- [ ] User can sign up at /login
- [ ] User can login with OTP
- [ ] User dashboard at /dashboard shows:
  - [ ] User profile information
  - [ ] Booking history
  - [ ] Payment status
- [ ] User can update profile
- [ ] User can logout

### Session Management
- [ ] JWT tokens working
- [ ] Authentication middleware protecting routes
- [ ] Logout clears session
- [ ] Protected routes redirect to login when not authenticated

---

## Phase 5: Admin Panel

### Admin Access
- [ ] Admin user created in database (role = 'admin')
- [ ] Can access /admin page when logged in as admin
- [ ] Cannot access /admin when logged in as regular user
- [ ] Cannot access /admin when not logged in

### Admin Dashboard
- [ ] Main dashboard shows:
  - [ ] Total users count
  - [ ] Total bookings count
  - [ ] Total revenue
  - [ ] Pending payments count
- [ ] All stats are accurate

### Admin Sub-Pages
- [ ] /admin/customers - View all customers
- [ ] /admin/bookings - View all bookings
- [ ] /admin/payments - View all payments with status
- [ ] /admin/hotels - Manage hotels
- [ ] /admin/packages - Manage packages
- [ ] /admin/messages - View WhatsApp message logs
- [ ] /admin/analytics - View charts and analytics
- [ ] /admin/settings - Configure system settings

### Admin Features
- [ ] Can search/filter customers
- [ ] Can view booking details
- [ ] Can view payment details
- [ ] Can mark payments as verified
- [ ] Can view all WhatsApp messages sent

---

## Phase 6: WhatsApp Notifications

### Integration
- [ ] WhatsAppLog table exists in database
- [ ] Messages logged correctly when sent
- [ ] Failed messages logged with error details

### Booking Confirmation
- [ ] After booking, WhatsApp message sent
- [ ] Message includes booking reference
- [ ] Message includes trip details
- [ ] Message includes total amount

### Payment Receipt
- [ ] After payment, WhatsApp receipt sent
- [ ] Receipt includes amount
- [ ] Receipt includes payment method
- [ ] Receipt includes transaction ID

### Other Messages
- [ ] Reminder messages can be sent
- [ ] Cancellation messages can be sent
- [ ] All messages logged in admin panel

---

## Phase 7: Frontend UI/UX

### Responsive Design
- [ ] Page works on mobile (375px width)
- [ ] Page works on tablet (768px width)
- [ ] Page works on desktop (1920px width)
- [ ] No horizontal scrolling on any device

### Navigation
- [ ] Main navigation works on all pages
- [ ] Sidebar works on mobile (collapsible)
- [ ] Breadcrumbs show current location
- [ ] All links are working

### Forms
- [ ] All forms have proper validation
- [ ] Error messages display clearly
- [ ] Success messages display clearly
- [ ] Loading states show during processing

### Accessibility
- [ ] Can tab through all interactive elements
- [ ] Form labels associated with inputs
- [ ] Images have alt text
- [ ] Color contrast is sufficient

---

## Phase 8: Security Verification

### Authentication Security
- [ ] Passwords are hashed (bcryptjs)
- [ ] OTP codes are 6 digits and random
- [ ] OTP expires after 10 minutes
- [ ] OTP verified before creating session
- [ ] JWT tokens have expiration

### Payment Security
- [ ] Razorpay signatures verified
- [ ] Payment verification required before marking paid
- [ ] Admin can only view their own sensitive data
- [ ] User cannot access other user's data

### Data Protection
- [ ] Sensitive keys in .env.local (not in code)
- [ ] .env.local in .gitignore
- [ ] Database passwords not exposed
- [ ] API endpoints validate user permissions
- [ ] SQL injection prevention (Prisma ORM)

### HTTPS/SSL
- [ ] (For production) HTTPS configured
- [ ] (For production) SSL certificate installed
- [ ] (For production) Redirects from HTTP to HTTPS

---

## Phase 9: Performance Optimization

### Frontend Performance
- [ ] Images optimized (using Next.js Image)
- [ ] CSS minified (Tailwind)
- [ ] JavaScript minified (production build)
- [ ] Lazy loading on components
- [ ] Page loads in < 3 seconds

### Backend Performance
- [ ] Database queries optimized
- [ ] No N+1 query problems
- [ ] API responses < 1 second
- [ ] Caching implemented where needed

---

## Phase 10: Error Handling

### API Error Handling
- [ ] 400 - Bad request returns meaningful error
- [ ] 401 - Unauthorized redirects to login
- [ ] 404 - Not found shows proper error page
- [ ] 500 - Server error logged to console
- [ ] All errors have user-friendly messages

### Frontend Error Handling
- [ ] Network errors show message to user
- [ ] Form validation shows specific field errors
- [ ] Loading failures show retry option
- [ ] Session expiry handled gracefully

---

## Phase 11: Testing

### User Journey Testing
- [ ] New user signup complete
- [ ] Login with OTP complete
- [ ] Browse packages complete
- [ ] Make booking complete
- [ ] Process payment complete
- [ ] Receive WhatsApp notifications complete
- [ ] View booking in dashboard complete
- [ ] Logout and re-login complete

### Admin Journey Testing
- [ ] Admin login complete
- [ ] View dashboard stats complete
- [ ] Search customers complete
- [ ] View booking details complete
- [ ] View payment details complete
- [ ] View WhatsApp logs complete
- [ ] Update settings complete

### Edge Cases
- [ ] Invalid OTP (wrong code)
- [ ] Expired OTP (after 10 min)
- [ ] Wrong password
- [ ] Duplicate booking attempt
- [ ] Partial payment
- [ ] Network timeout during payment

---

## Phase 12: Deployment Preparation

### Code Quality
- [ ] No console.log statements left (except [v0] debug logs)
- [ ] No TypeScript errors: `npm run build`
- [ ] No ESLint errors: `npm run lint` (if configured)
- [ ] Code is clean and commented

### Documentation
- [ ] README.md updated
- [ ] SETUP.md instructions work
- [ ] QUICKSTART.md steps verified
- [ ] API documentation complete

### Environment
- [ ] .env.example file has all required variables
- [ ] .gitignore includes .env.local
- [ ] No secrets in version control
- [ ] node_modules not committed

### Testing Build
- [ ] Production build succeeds: `npm run build`
- [ ] Production server starts: `npm run start`
- [ ] All features work in production build
- [ ] No errors in production logs

---

## Phase 13: Deployment

### Vercel Deployment
- [ ] GitHub repo connected
- [ ] GitHub branch is latest code
- [ ] Vercel project created
- [ ] Environment variables set in Vercel
- [ ] Build succeeds
- [ ] Site is accessible online
- [ ] All features work online

### Alternative Hosting
- [ ] (If self-hosted) Server has Node.js 18+
- [ ] (If self-hosted) SSL certificate installed
- [ ] (If self-hosted) Database accessible from server
- [ ] (If self-hosted) Environment variables configured

---

## Final Checklist

Before marking as "Live":

- [ ] All 13 phases completed
- [ ] No known bugs
- [ ] Users can sign up
- [ ] Users can login with OTP
- [ ] Payments work
- [ ] WhatsApp notifications sent
- [ ] Admin panel functional
- [ ] No sensitive data exposed
- [ ] Acceptable performance
- [ ] Ready for users

---

## Quick Status Summary

```
Phase 1 - Twilio WhatsApp: [ ] Completed
Phase 2 - Database:        [ ] Completed
Phase 3 - Payments:        [ ] Completed
Phase 4 - Auth & User:     [ ] Completed
Phase 5 - Admin Panel:     [ ] Completed
Phase 6 - WhatsApp Msgs:   [ ] Completed
Phase 7 - Frontend UI:     [ ] Completed
Phase 8 - Security:        [ ] Completed
Phase 9 - Performance:     [ ] Completed
Phase 10 - Error Handle:   [ ] Completed
Phase 11 - Testing:        [ ] Completed
Phase 12 - Deployment:     [ ] Completed
Phase 13 - Go Live:        [ ] Completed

Overall Status: ___________% Complete
Date Completed: ___________
```

---

## Troubleshooting Resources

If you encounter issues:

1. Check TWILIO_SETUP.md for OTP issues
2. Check TWILIO_QUICK_REFERENCE.md for quick fixes
3. Check IMPLEMENTATION_GUIDE.md for step-by-step help
4. Check SETUP.md for complete setup guide
5. Check server logs for [v0] debug messages
6. Check Twilio Console Logs for message delivery issues

---

## Support Contact

For questions about specific integrations:

- **Twilio**: https://www.twilio.com/docs
- **Razorpay**: https://razorpay.com/docs
- **Next.js**: https://nextjs.org/docs
- **Prisma**: https://www.prisma.io/docs
- **Vercel**: https://vercel.com/docs

