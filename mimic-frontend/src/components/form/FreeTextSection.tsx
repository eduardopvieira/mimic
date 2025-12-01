export interface FreeTextItem {
  id: number;
  name: string;
  description: string;
}

interface FreeTextSectionProps {
  title: string;
  items: FreeTextItem[];
  onAdd: () => void;
  onRemove: (id: number) => void;
  onUpdate: (id: number, key: 'name' | 'description', value: string) => void;
}

const FreeTextSection = ({ title, items, onAdd, onRemove, onUpdate }: FreeTextSectionProps) => {
  return (
    <div className="animate-fade-in">
      <div className="flex justify-between items-center pt-4 mb-6 border-b border-gray-700 pb-2">
        <h2 className="text-3xl font-semibold text-white font-medieval">{title}</h2>
        <button 
          type="button" 
          onClick={onAdd} 
          className="flex items-center px-4 py-2 rounded bg-red-600 hover:bg-red-500 text-white font-bold transition shadow-lg shadow-red-900/40"
        >
          <span className="text-2xl mr-2 leading-none">+</span> Adicionar
        </button>
      </div>

      <div className="space-y-6">
        {items.length === 0 && (
            <p className="text-gray-500 italic text-center py-4">Nenhum item adicionado.</p>
        )}
        
        {items.map((item) => (
          <div key={item.id} className="bg-[#3a3a3a] p-4 rounded-lg relative border border-gray-600 shadow-md">
             <button 
                type="button" 
                onClick={() => onRemove(item.id)} 
                className="absolute top-3 right-3 text-gray-400 hover:text-red-500 transition-colors"
                title="Remover"
             >
               <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12"></path></svg>
             </button>

             <div className="mb-4 pr-8">
                <label className="block text-gray-400 mb-1 text-sm font-bold uppercase">Nome</label>
                <input 
                    type="text" 
                    value={item.name} 
                    onChange={(e) => onUpdate(item.id, 'name', e.target.value)} 
                    className="w-full p-2 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 outline-none" 
                    placeholder="Ex: Mordida" 
                />
             </div>
             
             <div>
                <label className="block text-gray-400 mb-1 text-sm font-bold uppercase">Descrição</label>
                <textarea 
                    rows={3} 
                    value={item.description} 
                    onChange={(e) => onUpdate(item.id, 'description', e.target.value)} 
                    className="w-full p-2 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 outline-none resize-none" 
                    placeholder="Descrição do efeito..." 
                />
             </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FreeTextSection;