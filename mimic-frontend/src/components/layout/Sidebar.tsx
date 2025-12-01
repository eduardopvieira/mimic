import { Link, useLocation } from 'react-router-dom'; 

const Sidebar = () => {
  const location = useLocation(); 

  const menuItems = [
    { label: "Criar Personagem", path: "/formulario-personagem" },
    { label: "Criar Criatura", path: "/formulario-criatura" },
    { label: "Gerenciar Magias", path: "/gerenciar-magias" },
    { label: "Gerenciar Origens", path: "/gerenciar-origens" },
  ];

  return (
    <aside className="w-64 bg-[#2D2D2D] h-full p-6 hidden md:block shadow-xl border-r border-gray-700">
      <nav className="space-y-4">
        
        <div className="mb-6 pb-6 border-b border-gray-700">
            <Link to="/home-page" className="flex items-center space-x-2 text-gray-300 hover:text-white transition">
                <span className="font-bold">Página Principal</span>
            </Link>
        </div>

        <h3 className="text-gray-400 uppercase text-sm font-bold tracking-wider mb-4">Ações</h3>
        
        {menuItems.map((item) => {
          const isActive = location.pathname === item.path;
          
          return (
            <Link
              key={item.label}
              to={item.path}
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg transition duration-200 group border
                ${isActive 
                  ? 'bg-red-600 text-white border-red-500 shadow-lg shadow-red-900/50' 
                  : 'bg-red-700/20 text-red-100 hover:bg-red-600 hover:text-white border-red-900/30 hover:border-red-500'
                }`}
            >
              <span className="font-semibold">{item.label}</span>
            </Link>
          );
        })}
      </nav>
    </aside>
  );
};

export default Sidebar;