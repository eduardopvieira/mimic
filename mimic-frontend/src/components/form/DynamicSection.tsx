
interface Item {
  id: number;
  value: string | number;
  description: string;
}

interface DynamicSectionProps {
  title: string;
  items: Item[];
  options: any[];
  onAdd: () => void;
  onRemove: (id: number) => void;
  onUpdate: (id: number, newValue: string) => void; 
  itemName: string;
}

const DynamicSection = ({ title, items, options, onAdd, onRemove, onUpdate, itemName }: DynamicSectionProps) => {
  return (
    <div className="animate-fade-in">
      
      <div className="flex justify-between items-center border-b border-gray-700 pb-4 mb-6 pt-4">
        <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4">{title}</h2>
        <button 
          type="button" 
          onClick={onAdd}
          className="flex items-center justify-center px-4 py-2 rounded bg-red-600 hover:bg-red-500 text-white font-semibold transition duration-200 text-lg shadow-lg shadow-red-900/40"
        >
          <svg className="w-6 h-6 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"></path></svg>
          Adicionar {itemName}
        </button>
      </div>

      <div className="space-y-6">
        {items.length === 0 && (
            <p className="text-gray-500 italic text-center py-8">Nenhum {itemName.toLowerCase()} adicionado ainda.</p>
        )}

        {items.map((item, index) => (
          <div key={item.id} className="bg-[#3a3a3a] p-6 rounded-lg relative border border-gray-600 shadow-md group hover:border-gray-500 transition-colors">
            
            <button 
                type="button" 
                onClick={() => onRemove(item.id)}
                className="absolute top-4 right-4 text-gray-400 hover:text-red-500 transition-colors"
                title="Remover item"
            >
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12"></path></svg>
            </button>

            <div className="grid grid-cols-1 gap-4">
                <div>
                    <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">
                        {itemName} #{index + 1}
                    </label>
                    <select 
                        value={item.value} 
                        onChange={(e) => onUpdate(item.id, e.target.value)}
                        className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 focus:ring-1 focus:ring-red-500 outline-none transition appearance-none"
                    >
                        <option value="">Selecione...</option>
                        {options.map(opt => (
                            <option key={opt.value} value={opt.value}>{opt.label}</option>
                        ))}
                    </select>
                </div>

                
                <div>
                    <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">Descrição</label>
                    <textarea 
                        rows={3} 
                        readOnly 
                        value={item.description || "Selecione uma opção para ver a descrição..."}
                        className="w-full p-3 rounded bg-[#333333] border border-gray-600 text-gray-300 text-lg placeholder-gray-500 cursor-not-allowed resize-none"
                    />
                </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DynamicSection;