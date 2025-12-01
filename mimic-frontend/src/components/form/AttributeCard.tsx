import React, { useState } from "react";

// --- ATRIBUTE CARD CORRIGIDO ---
const AttributeCard = ({ label, value, onChange }: { label: string, value: number, onChange: (val: number) => void }) => {
  // Estado local para permitir string vazia enquanto digita
  const [localValue, setLocalValue] = useState<string>(value.toString());

  // Sincroniza se o valor mudar externamente (ex: carregamento inicial)
  React.useEffect(() => {
    // Só atualiza se o número real for diferente do que está escrito (evita conflito de digitação)
    if (parseInt(localValue) !== value) {
       setLocalValue(value.toString());
    }
  }, [value]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newVal = e.target.value;
    setLocalValue(newVal); // Permite ficar vazio visualmente

    // Atualiza o modificador pai em tempo real (se for número válido)
    const parsed = parseInt(newVal);
    if (!isNaN(parsed)) {
      onChange(parsed);
    }
  };

  const handleBlur = () => {
    // Ao perder o foco:
    // 1. Se estiver vazio ou inválido -> vira 0
    // 2. Se for "010" -> vira 10
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
          value={localValue} // Usa o estado local (string)
          onChange={handleChange}
          onBlur={handleBlur} // <--- A mágica acontece aqui
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