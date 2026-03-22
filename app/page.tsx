'use client';

import Link from 'next/link';
import { Plane, MapPin, Users, Award } from 'lucide-react';

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      {/* Navigation */}
      <nav className="bg-white shadow-sm sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex justify-between items-center">
          <h1 className="text-2xl font-bold text-blue-600">Travel Agency Pro</h1>
          <div className="space-x-4">
            <Link
              href="/login"
              className="px-4 py-2 text-blue-600 hover:text-blue-700 font-medium"
            >
              Login
            </Link>
            <Link
              href="/login"
              className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              Sign Up
            </Link>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20 text-center">
        <h2 className="text-5xl font-bold text-gray-900 mb-6">Explore the World with Ease</h2>
        <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
          Book your dream vacation with seamless payments, WhatsApp support, and exclusive travel packages
        </p>
        <Link
          href="/login"
          className="inline-block px-8 py-3 bg-gradient-to-r from-blue-600 to-indigo-600 text-white font-semibold rounded-lg hover:from-blue-700 hover:to-indigo-700 transition-all"
        >
          Start Booking Now
        </Link>
      </section>

      {/* Features Section */}
      <section className="bg-white py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <h3 className="text-3xl font-bold text-gray-900 mb-12 text-center">Why Choose Us?</h3>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
            <Feature
              icon={Plane}
              title="Wide Selection"
              description="Choose from thousands of travel packages and hotels worldwide"
            />
            <Feature
              icon={MapPin}
              title="Best Prices"
              description="Get the best deals and exclusive offers on your bookings"
            />
            <Feature
              icon={Users}
              title="24/7 WhatsApp Support"
              description="Get instant support anytime via WhatsApp"
            />
            <Feature
              icon={Award}
              title="Secure Payments"
              description="Multiple payment options with bank-level security"
            />
          </div>
        </div>
      </section>

      {/* Packages Section */}
      <section className="py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <h3 className="text-3xl font-bold text-gray-900 mb-12 text-center">Popular Packages</h3>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <PackageCard
              destination="Maldives"
              days={5}
              price={45000}
              image="🏝️"
            />
            <PackageCard
              destination="Bali"
              days={7}
              price={55000}
              image="🌴"
            />
            <PackageCard
              destination="Switzerland"
              days={10}
              price={85000}
              image="⛰️"
            />
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="bg-gradient-to-r from-blue-600 to-indigo-600 text-white py-16">
        <div className="max-w-4xl mx-auto text-center px-4 sm:px-6 lg:px-8">
          <h3 className="text-3xl font-bold mb-4">Ready to Start Your Adventure?</h3>
          <p className="text-lg mb-8">Book now and get 10% off on your first booking</p>
          <Link
            href="/login"
            className="inline-block px-8 py-3 bg-white text-blue-600 font-semibold rounded-lg hover:bg-gray-100 transition-all"
          >
            Book Your Trip
          </Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-gray-400 py-8">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <p>&copy; 2024 Travel Agency Pro. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
}

function Feature({ icon: Icon, title, description }: any) {
  return (
    <div className="text-center">
      <div className="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
        <Icon className="w-8 h-8 text-blue-600" />
      </div>
      <h4 className="text-xl font-semibold text-gray-900 mb-2">{title}</h4>
      <p className="text-gray-600">{description}</p>
    </div>
  );
}

function PackageCard({ destination, days, price, image }: any) {
  return (
    <div className="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow">
      <div className="h-40 bg-gradient-to-br from-blue-400 to-blue-600 flex items-center justify-center text-6xl">
        {image}
      </div>
      <div className="p-6">
        <h4 className="text-xl font-bold text-gray-900 mb-2">{destination}</h4>
        <p className="text-gray-600 mb-4">{days} days package</p>
        <div className="flex justify-between items-center">
          <span className="text-2xl font-bold text-blue-600">₹{price.toLocaleString('en-IN')}</span>
          <button className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
            Explore
          </button>
        </div>
      </div>
    </div>
  );
}
