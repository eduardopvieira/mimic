interface InputFieldProps {
  label: string;
  value: string | number;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  className?: string;
}

const InputField = ({ label, value, onChange, placeholder, className = "" }: InputFieldProps) => {
  return (
    <div className={`mb-4 ${className}`}>
      <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">
        {label}
      </label>
      <input 
        type="text" 
        value={value} 
        onChange={onChange} 
        placeholder={placeholder}
        className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 focus:ring-1 focus:ring-red-500 outline-none transition placeholder-gray-500" 
      />
    </div>
  );
};

export default InputField;