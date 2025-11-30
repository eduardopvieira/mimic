import React from 'react';

const Header = () => {
  return (
    <header className="bg-[#444444] p-4 shadow-lg sticky top-0 z-50 h-20">
      <div className="w-full px-6 flex items-center space-x-4 h-full">
        <div className="h-12 w-12 rounded-full bg-white p-1">
          <img 
            src="/mimic.png" 
            alt="Logo Mimic" 
            className="h-full w-full object-contain" 
          />
        </div>
        <h1 className="text-3xl font-bold text-white">Mimic</h1>
      </div>
    </header>
  );
};

export default Header;