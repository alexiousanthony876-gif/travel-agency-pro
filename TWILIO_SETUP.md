# Twilio WhatsApp OTP Setup Guide

## Problem: OTP Not Coming on WhatsApp

This guide will help you configure Twilio WhatsApp messaging to send OTP codes. Follow each step carefully.

---

## ⏱️ Time Required: 20-30 minutes

---

## Step 1: Create Twilio Account (5 minutes)

### 1.1 Sign Up for Twilio
1. Go to https://www.twilio.com/try-twilio
2. Sign up with:
   - Email address
   - Password
   - Full name
3. Verify your email
4. You'll be asked for a phone number - **provide a real phone number**

### 1.2 Get Your Twilio Credentials
1. After signup, go to https://console.twilio.com
2. On the dashboard, look for your **Account SID** and **Auth Token**
3. Click "Copy" next to each and save them somewhere safe
   - Account SID looks like: `ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`
   - Auth Token looks like: `xxxxxxxxxxxxxxxxxxxxxxxxxxxx`

---

## Step 2: Set Up WhatsApp Sandbox (10 minutes)

### 2.1 Access WhatsApp Sandbox
1. In Twilio Console, click **Messaging** in the left menu
2. Click **Try it out** → **Send a WhatsApp message**
3. Or directly go to: https://console.twilio.com/us1/develop/sms/try-it-out/whatsapp-sandbox

### 2.2 Get Your Sandbox Phone Number
1. You'll see a section with instructions
2. Look for a phone number like: **`whatsapp:+14155552671`**
3. **Copy this exact number** - this is your `TWILIO_WHATSAPP_NUMBER`
4. Note: The format must include `whatsapp:` prefix

### 2.3 Join the Sandbox (CRITICAL STEP)
This is the most important part - you MUST complete this to receive messages.

1. Find the "Join this sandbox" section
2. It will show you a message like:
   ```
   join brave-lion
   ```
   (The code changes, so yours will be different)
3. **Using WhatsApp on your phone**:
   - Open WhatsApp
   - Open a chat with the Twilio number shown (e.g., +14155552671)
   - Type the message exactly as shown (e.g., `join brave-lion`)
   - Send it
4. Within 5 seconds, you'll get a confirmation message from Twilio WhatsApp
5. **Now your phone is added to the sandbox!**

### 2.4 Verify Sandbox Membership
1. Go back to the WhatsApp Sandbox page
2. Scroll down to see "Joined Numbers"
3. Your phone number should now be listed there
4. **If it's not listed, redo step 2.3**

---

## Step 3: Get Your Environment Variables Ready

Before updating your code, gather these values:

| Variable | Value | Where to Get |
|----------|-------|--------------|
| `TWILIO_ACCOUNT_SID` | `ACxxxxx...` | Twilio Console → Dashboard |
| `TWILIO_AUTH_TOKEN` | `xxxxx...` | Twilio Console → Dashboard |
| `TWILIO_WHATSAPP_NUMBER` | `whatsapp:+14155552671` | WhatsApp Sandbox page |
| `DATABASE_URL` | Your DB URL | Your database provider |
| `JWT_SECRET` | Any random string | Create any 32+ char string |
| `RAZORPAY_KEY_ID` | From Razorpay | Razorpay Dashboard |
| `RAZORPAY_KEY_SECRET` | From Razorpay | Razorpay Dashboard |

---

## Step 4: Create .env.local File

### 4.1 Create the File
1. In your project root directory, create a file named `.env.local`
2. Copy and paste all the variables below:

```env
# =======================
# TWILIO WHATSAPP CONFIG
# =======================
TWILIO_ACCOUNT_SID=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
TWILIO_AUTH_TOKEN=your_auth_token_here
TWILIO_WHATSAPP_NUMBER=whatsapp:+14155552671

# =======================
# DATABASE CONFIG
# =======================
DATABASE_URL=mysql://user:password@localhost:3306/travel_agency_pro

# =======================
# AUTHENTICATION CONFIG
# =======================
JWT_SECRET=your-super-secret-jwt-key-min-32-characters-long
OTP_EXPIRY_MINUTES=10
OTP_MAX_ATTEMPTS=3

# =======================
# RAZORPAY CONFIG
# =======================
NEXT_PUBLIC_RAZORPAY_KEY_ID=your-razorpay-key-id
RAZORPAY_KEY_SECRET=your-razorpay-key-secret

# =======================
# APP CONFIG
# =======================
NEXT_PUBLIC_APP_URL=http://localhost:3000
NODE_ENV=development
```

### 4.2 Replace Placeholder Values
1. Replace `ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx` with your actual Account SID
2. Replace `your_auth_token_here` with your actual Auth Token
3. Replace `whatsapp:+14155552671` with your actual Twilio WhatsApp number
4. Replace database URL with your actual MySQL connection string
5. Keep other values as-is for development

### 4.3 Save the File
- Make sure the file is named exactly `.env.local`
- It should be in your project root (same level as `package.json`)
- **Do NOT commit this to GitHub** - it's already in `.gitignore`

---

## Step 5: Restart Development Server

### 5.1 Stop Current Server
If your dev server is running, press `Ctrl+C` to stop it.

### 5.2 Start Fresh
```bash
npm run dev
```

The server will load the new environment variables from `.env.local`.

---

## Step 6: Test OTP Flow

### 6.1 Open Login Page
1. Go to http://localhost:3000/login
2. You should see a login form

### 6.2 Send OTP
1. Enter the phone number you used to join the sandbox
   - Format: `+919876543210` (with +91)
   - Or: `9876543210` (without +91, it will be added automatically)
2. Create a password (any password)
3. Check the box to agree to terms
4. Click "Send OTP"

### 6.3 Check WhatsApp
1. Open WhatsApp on your phone
2. Look for a message from the Twilio WhatsApp number
3. **You should see your OTP code within 5-10 seconds**

### 6.4 If OTP Arrives ✅
Congratulations! Twilio is working. Now:
1. Copy the OTP code
2. Go back to the login page
3. Paste the OTP code in the verification field
4. Click "Verify OTP"
5. You should be logged in!

### 6.5 If OTP Doesn't Arrive ❌
Follow the troubleshooting steps below.

---

## Troubleshooting: OTP Not Arriving

Follow these steps in order:

### A. Check Environment Variables Are Loaded

1. Add this temporary debug code to your login page
2. In `/components/auth/LoginForm.tsx`, add at the top of the component:
```typescript
useEffect(() => {
  console.log('[v0] TWILIO_ACCOUNT_SID:', process.env.NEXT_PUBLIC_APP_URL);
  // Note: TWILIO_ACCOUNT_SID is secret and won't be logged
}, []);
```

3. Check browser console for any errors
4. Check your terminal (dev server) for messages starting with `[v0]`

### B. Verify Phone Number Format

- ✅ Correct: `+919876543210` (with country code)
- ✅ Correct: `9876543210` (will be converted automatically)
- ❌ Wrong: `91-9876543210`
- ❌ Wrong: `0-9876543210` (leading zero)
- ❌ Wrong: `9876543210` if from a different country

**Check:** The phone number must be the SAME one you used to join the sandbox in Step 2.3

### C. Check Sandbox Membership

1. Go to: https://console.twilio.com/us1/develop/sms/try-it-out/whatsapp-sandbox
2. Scroll to "Joined Numbers"
3. **Is your phone number listed?**
   - YES → Proceed to step D
   - NO → **You need to rejoin the sandbox:**
     ```
     1. Open WhatsApp
     2. Find the "join [CODE]" message at the top of Twilio WhatsApp
     3. Copy the join code
     4. Send it to Twilio number again
     5. Wait for confirmation
     ```

### D. Check Twilio Console Logs

1. Go to: https://console.twilio.com
2. Click **Messaging** → **Logs**
3. Look for recent failed messages (red X icon)
4. Click on failed messages to see error details
5. **Common errors:**
   - "Recipient number is not part of this conversation" = Sandbox membership issue
   - "Account not active" = Account needs to be verified
   - "Invalid credentials" = Wrong Twilio keys in .env.local

### E. Verify Twilio Account is Active

1. Go to https://console.twilio.com
2. Look for any warning banners at the top
3. **Common issues:**
   - Account is in trial mode - OK for development
   - Email not verified - Check your email
   - Phone not verified - Check your phone for Twilio SMS

### F. Check Server Logs

1. Open your terminal running the dev server
2. Look for messages starting with `[v0]`
3. These will show:
   - Whether WhatsApp message was sent
   - What phone number was used
   - Any error messages
4. **Example logs you should see:**
   ```
   [v0] Sending WhatsApp message
   [v0] To: whatsapp:+919876543210
   [v0] From: whatsapp:+14155552671
   [v0] Message type: otp
   [v0] Message sent successfully with SID: SMxxxxxxxxxxxxxxxxxxxx
   ```

### G. Test Twilio Directly

To verify Twilio is working independently:

1. Go to: https://console.twilio.com/us1/develop/sms/try-it-out/whatsapp-sandbox
2. Find "Send a test message" section
3. Type any message
4. Click "Send"
5. **Check your WhatsApp** - did the test message arrive?
   - YES → Twilio is working, issue is in our code
   - NO → Twilio sandbox issue, redo Step 2.3

---

## Common Issues & Solutions

### Issue: "Twilio credentials not configured"

**Solution:**
- Check `.env.local` file exists in project root
- Verify `TWILIO_ACCOUNT_SID` and `TWILIO_AUTH_TOKEN` are present
- Restart dev server: `npm run dev`

### Issue: "Recipient number is not part of this conversation"

**Solution:**
- You haven't joined the WhatsApp sandbox yet
- Follow Step 2.3 again
- Make sure you send the `join [CODE]` message to Twilio WhatsApp

### Issue: Message shows in logs but doesn't arrive on phone

**Solution:**
- Your phone may have blocked Twilio WhatsApp
- Go to WhatsApp → Settings → Blocked contacts
- Unblock the Twilio number if blocked
- Try again

### Issue: "Invalid phone number"

**Solution:**
- Format must be: `+91XXXXXXXXXX` (10 digits after +91)
- No spaces, dashes, or parentheses
- Example: `+919876543210` ✅
- Example: `+91 98765 43210` ❌

---

## Success Checklist

Before moving forward, verify:

- [ ] Twilio account created and verified
- [ ] WhatsApp sandbox joined (message sent from phone)
- [ ] Phone number appears in "Joined Numbers" on sandbox page
- [ ] `.env.local` file created with all variables
- [ ] Dev server restarted after adding `.env.local`
- [ ] OTP message received on WhatsApp
- [ ] OTP verification successful
- [ ] Successfully logged into application

---

## Next Steps

Once OTP is working:

1. **Setup Database**: Follow database setup instructions
2. **Test Payments**: Configure Razorpay and test payments
3. **Setup Admin Panel**: Create admin account and test admin features
4. **Deploy**: Push to Vercel or your hosting platform

---

## Support

If you're still having issues:

1. Check the server logs (look for `[v0]` messages)
2. Verify `.env.local` has correct values
3. Check Twilio console logs for error details
4. Re-read the troubleshooting section above

**Additional Resources:**
- Twilio WhatsApp Docs: https://www.twilio.com/docs/whatsapp
- Twilio Console: https://console.twilio.com

