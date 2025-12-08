import React from 'react';

interface OptionType {
  id: number;
  nome: string;
}

interface SelectBackendProps {
  label: string;
  name: string;
  value: string | number;
  onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
  options: OptionType[];
  disabled?: boolean;
  loading?: boolean;
  placeholder?: string;
}

const SelectBackend: React.FC<SelectBackendProps> = ({ 
  label, name, value, onChange, options, disabled = false, loading = false, placeholder = "Selecione..." 
}) => {
  return (
    <div>
      <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">
        {label}
      </label>
      <div className="relative w-full">
        <select
          name={name}
          value={value}
          onChange={onChange}
          disabled={disabled || loading}
          className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 appearance-none cursor-pointer disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <option value="" disabled>
            {loading ? "Carregando..." : placeholder}
          </option>
          {options.map((opt) => (
            <option key={opt.id} value={opt.id}>
              {opt.nome}
            </option>
          ))}
        </select>
        
        <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-400">
            <svg className="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                <path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/>
            </svg>
        </div>
      </div>
    </div>
  );
};

export default SelectBackend;