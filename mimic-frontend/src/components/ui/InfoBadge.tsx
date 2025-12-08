interface InfoBadgeProps {
  label: string;
  value: string | number;
  highlight?: boolean;
}

const InfoBadge = ({ label, value, highlight = false }: InfoBadgeProps) => {
  return (
    <div className={`flex flex-col p-3 rounded bg-[#363636] border border-gray-600 ${highlight ? 'border-red-500/50' : ''}`}>
      <span className={`text-[10px] uppercase font-bold tracking-wider mb-1 ${highlight ? 'text-red-400' : 'text-gray-500'}`}>
        {label}
      </span>
      <span className="text-white font-medium truncate text-sm">
        {value}
      </span>
    </div>
  );
};

export default InfoBadge;