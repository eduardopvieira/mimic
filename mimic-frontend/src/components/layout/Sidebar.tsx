import React from 'react';

const Sidebar = () => {
  // Lista de menus para facilitar manutenção futura
  const menuItems = [
    { label: "Criar Personagem", href: "/formulario-personagem", icon: "👤" },
    { label: "Criar Criatura", href: "/formulario-criatura", icon: "🐉" },
    { label: "Criar Magia", href: "/criar-magia", icon: "✨" },
    { label: "Criar Origem", href: "/criar-origem", icon: "📜" },
  ];

  return (
    <aside className="w-64 bg-[#2D2D2D] min-h-[calc(100vh-80px)] p-6 hidden md:block shadow-xl border-r border-gray-700">
      <nav className="space-y-4">
        <h3 className="text-gray-400 uppercase text-sm font-bold tracking-wider mb-4">Ações</h3>
        {menuItems.map((item) => (
          <a
            key={item.label}
            href={item.href}
            className="flex items-center space-x-3 px-4 py-3 rounded-lg bg-red-700/20 text-red-100 hover:bg-red-600 hover:text-white transition duration-200 group border border-red-900/30 hover:border-red-500"
          >
            <span className="text-xl group-hover:scale-110 transition-transform">{item.icon}</span>
            <span className="font-semibold">{item.label}</span>
          </a>
        ))}
      </nav>
    </aside>
  );
};

export default Sidebar;