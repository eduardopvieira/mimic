// --- COMPONENTE AUXILIAR (SelectField) ---
const SelectField = ({ label, value, onChange, options, disabled = false }: any) => {
  return (
  <div>
    <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">{label}</label>
    <select 
      value={value} 
      onChange={onChange}
      disabled={disabled}
      className={`w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 focus:ring-1 focus:ring-red-500 outline-none transition appearance-none ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`}
    >
      <option value="">Selecione...</option>
      {options.map((opt: string) => (
        <option key={opt} value={opt}>{opt}</option>
      ))}
    </select>
  </div>)
};

export default SelectField;