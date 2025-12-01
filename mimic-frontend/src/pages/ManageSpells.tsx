import { useState } from 'react';
import Header from '../components/layout/Header';
import Sidebar from '../components/layout/Sidebar';
import SpellCard from '../components/ui/SpellCard';
import { Link } from 'react-router-dom';

interface Spell {
  id: number;
  name: string;
  level: number;
  school: string;
  description: string;
}

const ManageSpells = () => {
  // dados mockados por enqnt
  const [spells, setSpells] = useState<Spell[]>([
    { id: 1, name: "Bola de Fogo", level: 3, school: "Evocação", description: "Uma explosão de chamas ruge em um ponto à sua escolha. Cada criatura num raio de 6m deve fazer um teste de Destreza." },
    { id: 2, name: "Mãos Mágicas", level: 0, school: "Conjuração", description: "Uma mão espectral flutuante aparece num ponto à sua escolha. Você pode usar a mão para manipular objetos, abrir portas ou causar distrações." },
    { id: 3, name: "Escudo", level: 1, school: "Abjuração", description: "Uma barreira invisível de força mágica aparece e protege você. Até o início do seu próximo turno, você tem +5 na CA, inclusive contra o ataque que ativou a magia." },
    { id: 4, name: "Detectar Magia", level: 1, school: "Adivinhação", description: "Pela duração, você sente a presença de magia a até 9 metros de você. Se você sentir magia dessa forma, você pode usar sua ação para ver uma aura fraca em volta de qualquer criatura ou objeto visível." },
  ]);

  // handlers
  const handleEdit = (id: number) => {
    alert(`Editar magia ID: ${id} (Implementar Modal)`);
  };

  const handleDelete = (id: number) => {
    if (confirm("Tem certeza que deseja excluir esta magia?")) {
      setSpells(prev => prev.filter(spell => spell.id !== id));
    }
  };

  return (
    <div className="bg-[#1A1A1A] w-full h-full flex flex-col font-sans text-gray-200">
      <Header />
      
      <div className="flex flex-1 overflow-hidden">
        <Sidebar />

        <main className="flex-1 p-8 overflow-y-auto">
          
          {/* Cabeçalho da Página */}
          <div className="flex justify-between items-center mb-8 border-b border-gray-700 pb-4">
            <div>
                <h2 className="text-4xl font-bold text-white font-medieval">Grimório de Magias</h2>
                <p className="text-gray-400 mt-2">Gerencie as magias disponíveis no sistema.</p>
            </div>
            
            {/* Botão Nova Magia */}
            <Link to='/criar-magia' className="flex items-center gap-2 bg-red-600 hover:bg-red-500 text-white font-bold py-2 px-6 rounded shadow-lg shadow-red-900/50 transition transform hover:scale-105">
                <span className="text-2xl leading-none mb-1">+</span>
                <span>Nova Magia</span>
            </Link>
          </div>

          {/* Grid de Cards */}
          {spells.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
                {spells.map((spell) => (
                <SpellCard
                    key={spell.id}
                    {...spell}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                />
                ))}
            </div>
          ) : (
             <div className="flex flex-col items-center justify-center h-64 border-2 border-dashed border-gray-700 rounded-lg text-gray-500">
                <p className="text-xl font-medieval">O Grimório está vazio.</p>
                <p className="text-sm">Clique em "Nova Magia" para começar.</p>
             </div>
          )}

        </main>
      </div>
    </div>
  );
};

export default ManageSpells;