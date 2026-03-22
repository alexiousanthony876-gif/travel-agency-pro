'use client';

import { useRouter } from 'next/navigation';
import AdminSidebar from '@/components/admin/Sidebar';
import AdminHeader from '@/components/admin/Header';
import { MessageSquare } from 'lucide-react';

export default function MessagesPage() {
  const router = useRouter();

  const handleLogout = () => {
    document.cookie = 'auth_token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC;';
    router.push('/login');
  };

  return (
    <div className="flex h-screen bg-gray-50">
      <AdminSidebar onLogout={handleLogout} />
      <div className="flex-1 flex flex-col overflow-hidden">
        <AdminHeader adminName="Admin" />
        <main className="flex-1 overflow-y-auto p-6">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900">WhatsApp Messages</h1>
            <p className="text-gray-600 mt-1">Track all WhatsApp communications</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <StatCard icon={MessageSquare} label="Total Sent" value={0} />
            <StatCard icon={MessageSquare} label="Delivery Rate" value="0%" />
            <StatCard icon={MessageSquare} label="Failed Messages" value={0} />
          </div>

          <div className="bg-white rounded-lg shadow p-8 text-center">
            <p className="text-gray-600">Message logs coming soon</p>
          </div>
        </main>
      </div>
    </div>
  );
}

function StatCard({ icon: Icon, label, value }: any) {
  return (
    <div className="bg-white rounded-lg shadow p-6">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-gray-600 text-sm">{label}</p>
          <p className="text-2xl font-bold text-gray-900 mt-2">{value}</p>
        </div>
        <Icon className="w-8 h-8 text-blue-600" />
      </div>
    </div>
  );
}
