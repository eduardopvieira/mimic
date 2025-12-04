import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';
import Sidebar from '../components/layout/Sidebar';
import OriginCard from '../components/ui/OriginCard';

interface Origin {
  id: number;
  name: string;
  skills: string[];
  equipment: string;
}

const ManageOrigins = () => {
  const navigate = useNavigate();
  
  // Mock Data
  const [origins, setOrigins] = useState<Origin[]>([
    { id: 1, name: "Acólito", skills: ["Intuição", "Religião"], equipment: "Símbolo sagrado, livro de preces, 5 bastões de incenso, vestes, roupas comuns, 15 PO." },
    { id: 2, name: "Criminoso", skills: ["Enganação", "Furtividade"], equipment: "Pé de cabra, roupas comuns escuras com capuz, 15 PO." },
    { id: 3, name: "Sábio", skills: ["Arcanismo", "História"], equipment: "Vidro de tinta, pena, faca pequena, carta de um colega morto, roupas comuns, 10 PO." },
  ]);

  const handleEdit = (id: number) => alert(`Editar ${id}`);
  const handleDelete = (id: number) => {
      if(confirm("Deseja excluir esta origem?")) setOrigins(prev => prev.filter(o => o.id !== id));
  };

  return (
    <div className="bg-[#1A1A1A] w-full h-full flex flex-col font-sans text-gray-200">
      <Header />
      <div className="flex flex-1 overflow-hidden">
        <Sidebar />
        <main className="flex-1 p-8 overflow-y-auto">
          
          <div className="flex justify-between items-center mb-8 border-b border-gray-700 pb-4">
            <div>
                <h2 className="text-4xl font-bold text-white font-medieval">Origens (Antecedentes)</h2>
                <p className="text-gray-400 mt-2">Defina o passado dos personagens.</p>
            </div>
            <button 
                onClick={() => navigate('/criar-origem')}
                className="flex items-center gap-2 bg-red-600 hover:bg-red-500 text-white font-bold py-2 px-6 rounded shadow-lg shadow-red-900/50 transition transform hover:scale-105"
            >
                <span className="text-2xl leading-none mb-1">+</span>
                <span>Nova Origem</span>
            </button>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            {origins.map((origin) => (
              <OriginCard key={origin.id} {...origin} onEdit={handleEdit} onDelete={handleDelete} />
            ))}
          </div>

        </main>
      </div>
    </div>
  );
};

export default ManageOrigins;