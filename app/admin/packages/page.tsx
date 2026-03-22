'use client';

import { useRouter } from 'next/navigation';
import AdminSidebar from '@/components/admin/Sidebar';
import AdminHeader from '@/components/admin/Header';
import { Plus } from 'lucide-react';

export default function PackagesPage() {
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
          <div className="mb-8 flex justify-between items-center">
            <div>
              <h1 className="text-3xl font-bold text-gray-900">Packages</h1>
              <p className="text-gray-600 mt-1">Manage travel packages</p>
            </div>
            <button className="flex items-center gap-2 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
              <Plus className="w-5 h-5" />
              Add Package
            </button>
          </div>

          <div className="bg-white rounded-lg shadow p-8 text-center">
            <p className="text-gray-600">Package management coming soon</p>
          </div>
        </main>
      </div>
    </div>
  );
}
