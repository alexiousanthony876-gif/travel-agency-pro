'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import {
  LayoutDashboard,
  Users,
  BookOpen,
  CreditCard,
  MessageSquare,
  Hotel,
  Package,
  BarChart3,
  Settings,
  LogOut,
} from 'lucide-react';

interface SidebarProps {
  onLogout: () => void;
}

export default function AdminSidebar({ onLogout }: SidebarProps) {
  const pathname = usePathname();

  const menuItems = [
    { icon: LayoutDashboard, label: 'Dashboard', href: '/admin' },
    { icon: Users, label: 'Customers', href: '/admin/customers' },
    { icon: BookOpen, label: 'Bookings', href: '/admin/bookings' },
    { icon: CreditCard, label: 'Payments', href: '/admin/payments' },
    { icon: Hotel, label: 'Hotels', href: '/admin/hotels' },
    { icon: Package, label: 'Packages', href: '/admin/packages' },
    { icon: MessageSquare, label: 'Messages', href: '/admin/messages' },
    { icon: BarChart3, label: 'Analytics', href: '/admin/analytics' },
    { icon: Settings, label: 'Settings', href: '/admin/settings' },
  ];

  return (
    <aside className="w-64 bg-gradient-to-b from-blue-900 to-blue-800 text-white h-screen overflow-y-auto">
      {/* Logo */}
      <div className="p-6 border-b border-blue-700">
        <h2 className="text-2xl font-bold">Travel Admin</h2>
        <p className="text-blue-200 text-sm mt-1">Management Panel</p>
      </div>

      {/* Navigation */}
      <nav className="p-4 space-y-2">
        {menuItems.map((item) => {
          const Icon = item.icon;
          const isActive = pathname === item.href;

          return (
            <Link
              key={item.href}
              href={item.href}
              className={`flex items-center gap-3 px-4 py-3 rounded-lg transition-all ${
                isActive
                  ? 'bg-blue-600 text-white'
                  : 'text-blue-100 hover:bg-blue-700'
              }`}
            >
              <Icon className="w-5 h-5" />
              <span className="font-medium">{item.label}</span>
            </Link>
          );
        })}
      </nav>

      {/* Footer */}
      <div className="absolute bottom-0 left-0 right-0 p-4 border-t border-blue-700 bg-blue-900">
        <button
          onClick={onLogout}
          className="flex items-center gap-3 w-full px-4 py-3 rounded-lg text-blue-100 hover:bg-blue-700 transition-all"
        >
          <LogOut className="w-5 h-5" />
          <span className="font-medium">Logout</span>
        </button>
      </div>
    </aside>
  );
}
