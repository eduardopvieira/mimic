import React, { useState } from "react";

const AttributeCard = ({ label, value, onChange }: { label: string, value: number, onChange: (val: number) => void }) => {
  const [localValue, setLocalValue] = useState<string>(value.toString());

  React.useEffect(() => {
    
    if (parseInt(localValue) !== value) {
       setLocalValue(value.toString());
    }
  }, [value]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newVal = e.target.value;
    setLocalValue(newVal);

    
    const parsed = parseInt(newVal);
    if (!isNaN(parsed)) {
      onChange(parsed);
    }
  };

  const handleBlur = () => {
    
    
    
    let finalValue = 0;
    
    if (localValue !== '' && !isNaN(parseInt(localValue))) {
      finalValue = parseInt(localValue);
    }

    setLocalValue(finalValue.toString());
    onChange(finalValue);
  };

  const mod = Math.floor((value - 10) / 2);
  const modDisplay = mod >= 0 ? `+${mod}` : `${mod}`;

  return (
    <div className="bg-gray-200 text-gray-900 rounded-lg shadow-xl border-2 border-gray-400 p-4 flex flex-col justify-between items-center min-h-[220px] transition-transform hover:-translate-y-1 duration-300">
      
      <span className="text-xl font-bold uppercase tracking-widest border-b-2 border-gray-400 pb-1 w-full text-center font-medieval">
        {label}
      </span>

      <div className="flex-1 flex items-center justify-center w-full">
        <input 
          type="number" 
          value={localValue} 
          onChange={handleChange}
          onBlur={handleBlur} 
          className="w-full text-center bg-transparent border-none p-0 focus:ring-0 text-7xl font-black text-gray-800 outline-none no-spinner font-medieval"
        />
      </div>

      <div className="bg-white rounded-full px-6 py-1 border-2 border-gray-900 shadow-inner mt-2">
        <span className="text-2xl font-bold text-gray-900 select-none font-medieval">
          {modDisplay}
        </span>
      </div>
    </div>
  );
};

export default AttributeCard;