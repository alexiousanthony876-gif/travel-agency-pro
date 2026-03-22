# Twilio WhatsApp - Quick Reference

## 5-Minute Checklist (If you already have Twilio Account)

```
[ ] 1. Go to: https://console.twilio.com/us1/develop/sms/try-it-out/whatsapp-sandbox
[ ] 2. Copy TWILIO_ACCOUNT_SID from dashboard
[ ] 3. Copy TWILIO_AUTH_TOKEN from dashboard  
[ ] 4. Copy TWILIO_WHATSAPP_NUMBER from sandbox page (format: whatsapp:+14155552671)
[ ] 5. Open WhatsApp, send "join [CODE]" to Twilio number
[ ] 6. Create .env.local file in project root
[ ] 7. Add: TWILIO_ACCOUNT_SID=ACxxxxx
[ ] 8. Add: TWILIO_AUTH_TOKEN=xxxxx
[ ] 9. Add: TWILIO_WHATSAPP_NUMBER=whatsapp:+14155552671
[ ] 10. Run: npm run dev
[ ] 11. Go to: http://localhost:3000/login
[ ] 12. Send OTP using your phone number
[ ] 13. Check WhatsApp for OTP message
```

---

## Environment Variables Template

Copy this and replace XXX values:

```env
TWILIO_ACCOUNT_SID=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
TWILIO_AUTH_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
TWILIO_WHATSAPP_NUMBER=whatsapp:+14155552671
DATABASE_URL=mysql://user:password@localhost:3306/travel_agency_pro
JWT_SECRET=your-super-secret-32-character-minimum
NEXT_PUBLIC_RAZORPAY_KEY_ID=your_razorpay_key
RAZORPAY_KEY_SECRET=your_razorpay_secret
NEXT_PUBLIC_APP_URL=http://localhost:3000
NODE_ENV=development
```

---

## Key Values Location

| Value | Where to Find | Example |
|-------|---------------|---------|
| Account SID | Twilio Console → Dashboard | ACxxxxxxxxxxxxxxxx |
| Auth Token | Twilio Console → Dashboard (hidden, click eye icon) | xxxxxxxxxxxxxxxxxxxx |
| WhatsApp Number | Twilio Console → Messaging → WhatsApp Sandbox | whatsapp:+14155552671 |

---

## Phone Number Format

```
✅ Correct Formats:
  +919876543210      (with country code)
  9876543210         (will auto-convert to +91)

❌ Wrong Formats:
  91-9876543210      (hyphens)
  +91 98765 43210    (spaces)
  919876543210       (missing +)
  09876543210        (leading zero)
```

---

## Sandbox Joining Steps

```
1. Go to WhatsApp Sandbox: 
   https://console.twilio.com/us1/develop/sms/try-it-out/whatsapp-sandbox

2. Find "Join this sandbox" section

3. Copy the code shown (e.g., "join brave-lion")

4. Open WhatsApp on your phone

5. Start chat with Twilio number shown (e.g., +14155552671)

6. Send the "join [CODE]" message

7. Wait for confirmation message from Twilio

8. Your phone is now added to sandbox ✅
```

---

## Debug Checklist

When OTP doesn't arrive, check:

```
[ ] .env.local file exists in project root
[ ] TWILIO_ACCOUNT_SID is correct (starts with AC)
[ ] TWILIO_AUTH_TOKEN is correct (32+ characters)
[ ] TWILIO_WHATSAPP_NUMBER includes "whatsapp:" prefix
[ ] Phone number joined sandbox (check "Joined Numbers" section)
[ ] Phone number format is correct (+91XXXXXXXXXX)
[ ] Dev server restarted after .env.local changes
[ ] Check terminal logs for [v0] messages
[ ] Check Twilio Console Logs for errors
[ ] Account email verified
```

---

## Terminal Test

Check these messages in your dev server logs:

```
[v0] Sending WhatsApp message ✅
[v0] To: whatsapp:+919876543210 ✅
[v0] From: whatsapp:+14155552671 ✅
[v0] Message type: otp ✅
[v0] Message sent successfully with SID: SMxxxx ✅
```

If you see errors instead, check the error message in logs.

---

## Twilio Console Navigation

1. **Account Info**: Dashboard (top-left)
2. **WhatsApp Sandbox**: Messaging → Try it out → Send a WhatsApp message
3. **Message Logs**: Messaging → Logs
4. **Account Settings**: Account (bottom-left) → Settings

---

## Common Quick Fixes

| Problem | Solution |
|---------|----------|
| OTP not arriving | Rejoin sandbox (send `join [CODE]` again) |
| "Credentials not configured" | Restart dev server with `npm run dev` |
| "Invalid phone number" | Add +91 prefix, no spaces/dashes |
| Got test message but not OTP | Code issue, check server logs |
| Account not verified | Check email, verify phone number |

---

## File Locations in Project

```
/vercel/share/v0-project/
├── .env.local                          ← Your config (create this)
├── lib/whatsapp.ts                     ← WhatsApp service
├── app/api/auth/login/route.ts         ← Login with OTP
├── components/auth/LoginForm.tsx       ← Login UI
└── TWILIO_SETUP.md                     ← Full guide
```

---

## Next After OTP Works

1. Test payment: `/checkout` page
2. Test admin: `/admin` page
3. Setup database migrations
4. Deploy to Vercel

---

## Emergency Contact

If stuck, verify these in order:

1. Check TWILIO_SETUP.md (full guide with 20+ solutions)
2. Check Twilio Console Logs for specific error
3. Rejoin WhatsApp sandbox from Step 2.3
4. Restart dev server completely
5. Create fresh .env.local from template above

