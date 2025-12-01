import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <header className="bg-[#444444] p-4 shadow-lg sticky top-0 z-50 h-20">
      <div className="w-full px-6 flex items-center space-x-4 h-full">
        <Link to="/home-page" className="flex items-center gap-4">
            <div className="h-12 w-12 rounded-full bg-white p-1">
                <img src="/mimic.png" alt="Logo" className="h-full w-full object-contain" />
            </div>
            <h1 className="text-3xl font-bold text-white hover:text-red-500 transition">Mimic</h1>
        </Link>
      </div>
    </header>
    
  );
};

export default Header;