import React from 'react';

interface CreatureCardProps {
  id: number;
  title: string;
  race: string; // Mapear para 'tipo'
  size: string; // Mapear para 'tamanho'
  image: string | null; 
  onView: (id: number) => void;   // <--- Nova prop para visualizar
  onEdit: (id: number) => void;
  onDelete: (id: number) => void;
}

const CreatureCard = ({ id, title, race, size, image, onView, onEdit, onDelete }: CreatureCardProps) => {
  return (
    <div className="relative block bg-[#2D2D2D] rounded-lg shadow-2xl overflow-hidden group transition-all duration-300 hover:shadow-red-500/30 border border-transparent hover:border-red-500/50">
      
      {/* HEADER DO CARD */}
      <div className="p-4 bg-[#3a3a3a] border-b-2 border-red-600">
        <h3 className="text-2xl font-bold text-white truncate">{title}</h3>
      </div>

      {/* CORPO DO CARD */}
      <div className="p-5 flex space-x-6 items-start pb-16">
        
        <div className="bg-[#444444] rounded-lg flex-shrink-0 ring-2 ring-gray-600 group-hover:ring-red-500 transition-all self-stretch w-32 h-32 overflow-hidden flex items-center justify-center text-gray-500">
          {image ? (
            <img src={image} alt={title} className="w-full h-full object-cover object-top" />
          ) : (
            // Ícone Genérico
            <svg className="w-16 h-16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M14.828 14.828a4 4 0 01-5.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
          )}
        </div>

        <div className="flex flex-col justify-start space-y-3 flex-grow">
          <div>
            <span className="text-xs font-medium text-gray-400 uppercase tracking-wider">Tipo</span>
            <p className="text-base font-semibold text-white">{race}</p>
          </div>
          <div>
            <span className="text-xs font-medium text-gray-400 uppercase tracking-wider">Tamanho</span>
            <p className="text-base font-semibold text-white">{size}</p>
          </div>
        </div>
      </div>

      {/* AÇÕES */}
      <div className="absolute bottom-4 right-4 flex gap-3 z-10">
        
        {/* --- Botão Visualizar (Novo) --- */}
        <button 
            onClick={() => onView(id)}
            className="p-2 rounded-full bg-gray-700 text-gray-200 hover:bg-green-600 hover:text-white transition-colors shadow-lg"
            title="Visualizar Criatura"
        >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
            </svg>
        </button>

        {/* Botão Editar */}
        <button 
            onClick={() => onEdit(id)}
            className="p-2 rounded-full bg-gray-700 text-gray-200 hover:bg-blue-600 hover:text-white transition-colors shadow-lg"
            title="Editar Criatura"
        >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"></path></svg>
        </button>

        {/* Botão Deletar */}
        <button 
            onClick={() => onDelete(id)}
            className="p-2 rounded-full bg-gray-700 text-gray-200 hover:bg-red-600 hover:text-white transition-colors shadow-lg"
            title="Excluir Criatura"
        >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>
        </button>
      </div>

    </div>
  );
};

export default CreatureCard;