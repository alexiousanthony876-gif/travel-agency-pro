'use client';

import { useState } from 'react';
import toast from 'react-hot-toast';
import { CreditCard, Smartphone, Zap } from 'lucide-react';

declare global {
  interface Window {
    Razorpay: any;
  }
}

interface PaymentCheckoutProps {
  bookingId: number;
  amount: number;
  bookingReference: string;
  onPaymentSuccess?: () => void;
}

export default function PaymentCheckout({
  bookingId,
  amount,
  bookingReference,
  onPaymentSuccess,
}: PaymentCheckoutProps) {
  const [loading, setLoading] = useState(false);
  const [selectedMethod, setSelectedMethod] = useState<'card' | 'upi' | 'netbanking'>('card');

  const handlePayment = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      // Step 1: Create order
      const orderResponse = await fetch('/api/payments/create-order', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ bookingId }),
      });

      const orderData = await orderResponse.json();

      if (!orderResponse.ok) {
        toast.error(orderData.error || 'Failed to create order');
        setLoading(false);
        return;
      }

      // Step 2: Initialize Razorpay
      const script = document.createElement('script');
      script.src = 'https://checkout.razorpay.com/v1/checkout.js';
      script.async = true;
      document.body.appendChild(script);

      script.onload = () => {
        const options = {
          key: orderData.keyId,
          amount: orderData.amount,
          currency: orderData.currency,
          order_id: orderData.orderId,
          name: 'Travel Agency Pro',
          description: `Booking: ${bookingReference}`,
          image: '/logo.png',
          prefill: {
            name: orderData.customer.name,
            email: orderData.customer.email,
            contact: orderData.customer.contact,
          },
          notes: {
            bookingId: bookingId.toString(),
            bookingReference,
          },
          theme: {
            color: '#1e40af',
          },
          method: {
            upi: true,
            card: true,
            netbanking: true,
          },
          handler: async (response: any) => {
            try {
              // Step 3: Verify payment
              const verifyResponse = await fetch('/api/payments/verify', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                  razorpayOrderId: orderData.orderId,
                  razorpayPaymentId: response.razorpay_payment_id,
                  razorpaySignature: response.razorpay_signature,
                  paymentMethod: selectedMethod,
                }),
              });

              const verifyData = await verifyResponse.json();

              if (!verifyResponse.ok) {
                toast.error(verifyData.error || 'Payment verification failed');
                setLoading(false);
                return;
              }

              toast.success('Payment successful! Your booking is confirmed.');
              
              if (onPaymentSuccess) {
                onPaymentSuccess();
              }
            } catch (error) {
              console.error('Verification error:', error);
              toast.error('An error occurred during payment verification');
            } finally {
              setLoading(false);
            }
          },
          modal: {
            ondismiss: () => {
              toast.error('Payment cancelled');
              setLoading(false);
            },
          },
        };

        const rzp = new window.Razorpay(options);
        rzp.open();
      };
    } catch (error) {
      console.error('Payment error:', error);
      toast.error('An error occurred. Please try again.');
      setLoading(false);
    }
  };

  const paymentMethods = [
    {
      id: 'upi',
      name: 'UPI',
      icon: Smartphone,
      description: 'Google Pay, PhonePe, PayTM',
      label: 'upi',
    },
    {
      id: 'card',
      name: 'Debit / Credit Card',
      icon: CreditCard,
      description: 'Visa, Mastercard, RuPay',
      label: 'card',
    },
    {
      id: 'netbanking',
      name: 'Net Banking',
      icon: Zap,
      description: 'All major Indian banks',
      label: 'netbanking',
    },
  ];

  return (
    <div className="w-full max-w-2xl mx-auto bg-white rounded-2xl shadow-lg p-8">
      {/* Header */}
      <div className="mb-8">
        <h2 className="text-2xl font-bold text-gray-900 mb-2">Complete Payment</h2>
        <p className="text-gray-600">Select your preferred payment method</p>
      </div>

      {/* Amount Summary */}
      <div className="mb-8 p-4 bg-blue-50 rounded-lg border border-blue-200">
        <div className="flex justify-between items-center">
          <span className="text-gray-700 font-medium">Total Amount</span>
          <span className="text-3xl font-bold text-blue-600">₹{amount.toLocaleString('en-IN')}</span>
        </div>
        <p className="text-xs text-gray-600 mt-2">Booking Reference: {bookingReference}</p>
      </div>

      {/* Payment Methods */}
      <form onSubmit={handlePayment} className="space-y-4">
        <div className="space-y-3">
          {paymentMethods.map((method) => {
            const Icon = method.icon;
            return (
              <label
                key={method.id}
                className={`flex items-center p-4 border-2 rounded-lg cursor-pointer transition-all ${
                  selectedMethod === method.label
                    ? 'border-blue-600 bg-blue-50'
                    : 'border-gray-200 hover:border-gray-300'
                }`}
              >
                <input
                  type="radio"
                  name="paymentMethod"
                  value={method.label}
                  checked={selectedMethod === method.label}
                  onChange={() => setSelectedMethod(method.label as any)}
                  className="w-4 h-4 text-blue-600"
                />
                <Icon className="w-6 h-6 text-gray-600 ml-4 mr-4" />
                <div className="flex-1">
                  <p className="font-semibold text-gray-900">{method.name}</p>
                  <p className="text-sm text-gray-600">{method.description}</p>
                </div>
              </label>
            );
          })}
        </div>

        {/* Security Notice */}
        <div className="p-4 bg-green-50 rounded-lg border border-green-200 mt-6">
          <p className="text-xs text-green-800">
            🔒 All payments are processed securely using Razorpay encryption. Your card details are never stored on our servers.
          </p>
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-gradient-to-r from-blue-600 to-indigo-600 text-white font-semibold py-3 rounded-lg hover:from-blue-700 hover:to-indigo-700 disabled:opacity-50 transition-all duration-200 mt-8"
        >
          {loading ? 'Processing...' : `Pay ₹${amount.toLocaleString('en-IN')}`}
        </button>

        {/* Terms */}
        <p className="text-xs text-gray-600 text-center">
          By proceeding, you agree to our Terms & Conditions and Privacy Policy
        </p>
      </form>
    </div>
  );
}
