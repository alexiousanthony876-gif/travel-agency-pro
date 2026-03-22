'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import AdminSidebar from '@/components/admin/Sidebar';
import AdminHeader from '@/components/admin/Header';
import { TrendingUp, Users, BookOpen, CreditCard } from 'lucide-react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

interface DashboardStats {
  totalUsers: number;
  totalBookings: number;
  totalRevenue: number;
  pendingPayments: number;
  recentBookings: any[];
  chartData: any[];
}

export default function AdminDashboard() {
  const router = useRouter();
  const [stats, setStats] = useState<DashboardStats | null>(null);
  const [loading, setLoading] = useState(true);
  const [adminName, setAdminName] = useState('Admin');

  useEffect(() => {
    // Check authentication
    const token = document.cookie
      .split('; ')
      .find(row => row.startsWith('auth_token='))
      ?.split('=')[1];

    if (!token) {
      router.push('/auth/login');
      return;
    }

    // Fetch dashboard stats
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      const response = await fetch('/api/admin/dashboard-stats');
      const data = await response.json();
      
      if (response.ok) {
        setStats(data);
        setAdminName(data.adminName || 'Admin');
      } else {
        router.push('/auth/login');
      }
    } catch (error) {
      console.error('Failed to fetch stats:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = async () => {
    document.cookie = 'auth_token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC;';
    router.push('/auth/login');
  };

  if (loading) {
    return (
      <div className="flex h-screen items-center justify-center bg-gray-50">
        <div className="text-center">
          <div className="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <div className="w-12 h-12 border-4 border-blue-200 border-t-blue-600 rounded-full animate-spin"></div>
          </div>
          <p className="text-gray-600">Loading dashboard...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="flex h-screen bg-gray-50">
      {/* Sidebar */}
      <AdminSidebar onLogout={handleLogout} />

      {/* Main Content */}
      <div className="flex-1 flex flex-col overflow-hidden">
        <AdminHeader adminName={adminName} />

        <main className="flex-1 overflow-y-auto p-6">
          {/* Page Title */}
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900">Dashboard</h1>
            <p className="text-gray-600 mt-1">Welcome back! Here's your business overview.</p>
          </div>

          {/* Stats Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            <StatCard
              icon={Users}
              label="Total Customers"
              value={stats?.totalUsers || 0}
              change={12}
            />
            <StatCard
              icon={BookOpen}
              label="Total Bookings"
              value={stats?.totalBookings || 0}
              change={8}
            />
            <StatCard
              icon={CreditCard}
              label="Total Revenue"
              value={`₹${(stats?.totalRevenue || 0).toLocaleString('en-IN')}`}
              change={15}
            />
            <StatCard
              icon={TrendingUp}
              label="Pending Payments"
              value={stats?.pendingPayments || 0}
              change={-5}
              negative
            />
          </div>

          {/* Charts and Tables */}
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {/* Revenue Chart */}
            <div className="lg:col-span-2 bg-white rounded-lg shadow p-6">
              <h2 className="text-lg font-bold text-gray-900 mb-4">Revenue Trend</h2>
              {stats?.chartData && stats.chartData.length > 0 ? (
                <ResponsiveContainer width="100%" height={300}>
                  <LineChart data={stats.chartData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Line
                      type="monotone"
                      dataKey="revenue"
                      stroke="#1e40af"
                      strokeWidth={2}
                    />
                  </LineChart>
                </ResponsiveContainer>
              ) : (
                <p className="text-gray-600">No data available</p>
              )}
            </div>

            {/* Recent Activity */}
            <div className="bg-white rounded-lg shadow p-6">
              <h2 className="text-lg font-bold text-gray-900 mb-4">Recent Bookings</h2>
              <div className="space-y-3">
                {stats?.recentBookings && stats.recentBookings.length > 0 ? (
                  stats.recentBookings.slice(0, 5).map((booking, idx) => (
                    <div
                      key={idx}
                      className="p-3 bg-gray-50 rounded-lg border border-gray-200"
                    >
                      <p className="text-sm font-medium text-gray-900">
                        {booking.bookingReference}
                      </p>
                      <p className="text-xs text-gray-600 mt-1">
                        {booking.status}
                      </p>
                    </div>
                  ))
                ) : (
                  <p className="text-gray-600">No recent bookings</p>
                )}
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}

interface StatCardProps {
  icon: any;
  label: string;
  value: string | number;
  change?: number;
  negative?: boolean;
}

function StatCard({
  icon: Icon,
  label,
  value,
  change,
  negative,
}: StatCardProps) {
  return (
    <div className="bg-white rounded-lg shadow p-6">
      <div className="flex items-start justify-between mb-4">
        <div className="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
          <Icon className="w-6 h-6 text-blue-600" />
        </div>
        {change !== undefined && (
          <div
            className={`text-sm font-semibold ${
              negative ? 'text-red-600' : 'text-green-600'
            }`}
          >
            {negative ? '-' : '+'}
            {Math.abs(change)}%
          </div>
        )}
      </div>
      <p className="text-gray-600 text-sm">{label}</p>
      <p className="text-2xl font-bold text-gray-900 mt-2">{value}</p>
    </div>
  );
}
