import { Link, useLocation, useNavigate } from 'react-router-dom';

const Sidebar = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const menuItems = [
    { label: "Gerenciar Personagens", path: "/gerenciar-personagens" },
    { label: "Gerenciar Criaturas", path: "/gerenciar-criaturas" },
    { label: "Gerenciar Magias", path: "/gerenciar-magias" },
    { label: "Gerenciar Origens", path: "/gerenciar-origens" },
  ];

  const handleLogout = () => {
    localStorage.removeItem('token'); 

    navigate('/login');
  };

  return (
    <aside className="w-64 bg-[#2D2D2D] h-full p-6 hidden md:flex flex-col shadow-xl border-r border-gray-700">
      <nav className="space-y-4 flex-1">
        
        <div className="mb-6 pb-6 border-b border-gray-700">
            <Link to="/gerenciar-personagem" className="flex items-center space-x-2 text-gray-300 hover:text-white transition">
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

      <div className="mt-auto pt-6 border-t border-gray-700">
        <button 
          onClick={handleLogout}
          className="w-full flex items-center justify-center space-x-2 px-4 py-3 rounded-lg transition duration-200 
                     border border-transparent text-gray-400 hover:text-white hover:bg-red-900/20 hover:border-red-500/50 group"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 group-hover:text-red-500 transition-colors" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
          </svg>
          <span className="font-semibold">Sair</span>
        </button>
      </div>

    </aside>
  );
};

export default Sidebar;