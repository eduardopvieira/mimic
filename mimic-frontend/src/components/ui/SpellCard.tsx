import React from 'react';

interface SpellCardProps {
  id: number;
  name: string;
  level: number;
  description: string;
  school: string; // ex: "Evocação", "Necromancia"
  onEdit: (id: number) => void;
  onDelete: (id: number) => void;
}

const SpellCard = ({ id, name, level, description, school, onEdit, onDelete }: SpellCardProps) => {
  
  // Formata o nível (0 = Truque)
  const levelLabel = level === 0 ? "Truque" : `${level}º Círculo`;

  // Define cores baseadas na escola (opcional, apenas um charme visual)
  const schoolColor = school === "Evocação" ? "text-red-400" 
                    : school === "Necromancia" ? "text-purple-400"
                    : school === "Adivinhação" ? "text-blue-400"
                    : "text-gray-400";

  return (
    <div className="bg-[#2D2D2D] rounded-lg shadow-lg border border-gray-700 p-6 flex flex-col justify-between h-full hover:border-red-500 transition-colors group relative">
      
      {/* Cabeçalho do Card */}
      <div className="mb-4">
        <div className="flex justify-between items-start mb-2">
            <span className="text-xs font-bold uppercase tracking-widest bg-gray-800 text-gray-300 px-2 py-1 rounded">
                {levelLabel}
            </span>
            <span className={`text-xs font-bold uppercase ${schoolColor}`}>
                {school}
            </span>
        </div>
        <h3 className="text-2xl font-bold text-white font-medieval truncate" title={name}>
            {name}
        </h3>
      </div>

      {/* Descrição (com limite de linhas para ficarem iguais) */}
      <p className="text-gray-400 text-sm mb-6 line-clamp-4 flex-grow">
        {description}
      </p>

      {/* Ações (Editar/Excluir) */}
      <div className="flex justify-end gap-3 pt-4 border-t border-gray-700 opacity-100 md:opacity-0 group-hover:opacity-100 transition-opacity">
        <button 
            onClick={() => onEdit(id)}
            className="p-2 text-gray-400 hover:text-white hover:bg-gray-700 rounded transition"
            title="Editar"
        >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path></svg>
        </button>
        <button 
            onClick={() => onDelete(id)}
            className="p-2 text-gray-400 hover:text-red-500 hover:bg-red-900/20 rounded transition"
            title="Excluir"
        >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>
        </button>
      </div>
    </div>
  );
};

export default SpellCard;