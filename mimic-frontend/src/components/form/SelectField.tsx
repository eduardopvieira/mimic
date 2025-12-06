const SelectField = ({ label, value, onChange, options, disabled = false }: any) => {
  return (
    <div>
      <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">
        {label}
      </label>
      <div className="relative">
        <select 
          value={value} 
          onChange={onChange}
          disabled={disabled}
          className={`w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 focus:ring-1 focus:ring-red-500 outline-none transition appearance-none ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`}
        >
          <option value="">Selecione...</option>
          
          {options.map((opt: any) => {
            const valor = typeof opt === 'object' ? opt.id : opt;
            const texto = typeof opt === 'object' ? opt.nome : opt;
            
            return (
              <option key={valor} value={valor}>
                {texto}
              </option>
            );
          })}
        </select>
        
        <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-400">
             <svg className="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
        </div>
      </div>
    </div>
  );
};

export default SelectField;