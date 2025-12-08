interface AttributeRowProps {
  label: string;
  score: number;
}

const AttributeRow = ({ label, score }: AttributeRowProps) => {
  const mod = Math.floor((score - 10) / 2);
  const modString = mod >= 0 ? `+${mod}` : `${mod}`;
  
  return (
    <div className="flex items-center justify-between bg-[#363636] p-3 px-4 rounded border border-gray-600 shadow-sm">
      <span className="font-bold text-gray-300 w-1/3 uppercase text-xs tracking-wider">
        {label}
      </span>
      <div className="flex items-center gap-3">
        <span className="text-xs text-gray-500 bg-black/20 px-2 py-1 rounded font-mono">
          {score}
        </span>
        <span className={`text-xl font-bold w-12 text-center ${mod > 0 ? 'text-green-500' : mod < 0 ? 'text-red-500' : 'text-gray-500'}`}>
          {modString}
        </span>
      </div>
    </div>
  );
};

export default AttributeRow;