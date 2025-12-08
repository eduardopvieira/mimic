import React from 'react';

interface StatBoxProps {
  icon: React.ReactNode;
  label: string;
  value: string | number;
  color: string;
}

const StatBox = ({ icon, label, value, color }: StatBoxProps) => {
  return (
    <div className="bg-[#363636] p-3 rounded-lg border border-gray-600 flex flex-col items-center justify-center text-center shadow-inner h-24 group hover:border-gray-500 transition-colors">
      <div className={`mb-2 ${color}`}>
        {icon}
      </div>
      <span className="text-2xl font-bold text-white leading-none mb-1">
        {value}
      </span>
      <span className="text-[10px] text-gray-500 uppercase tracking-widest font-bold">
        {label}
      </span>
    </div>
  );
};

export default StatBox;