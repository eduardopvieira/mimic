import React from 'react';
import Header from '../components/layout/Header';
import Sidebar from '../components/layout/Sidebar';
import CharacterCard from '../components/ui/CharacterCard';

const Dashboard = () => {
  // Simulação dos dados que virão do Back-end Spring Boot futuramente
  const mySheets = [
    {
      id: 1,
      title: "Eldrin Luthien",
      category: "Personagem",
      race: "Alto Elfo",
      charClass: "Mago",
      image: "/mage-highelf.jpg" // Certifique-se que esta imagem existe em /public
    },
    {
      id: 2,
      title: "Lobo Grande",
      category: "Criatura",
      race: "Lobo",
      charClass: "N/A",
      image: "/wolf.jpg"
    },
    {
      id: 3,
      title: "Lyra Swiftwind",
      category: "Personagem",
      race: "Halfling",
      charClass: "Ladina",
      image: null // Sem imagem renderiza o ícone padrão
    },
    {
      id: 4,
      title: "Beholder Xy'lor",
      category: "Criatura",
      race: "Aberração",
      charClass: "N/A",
      image: null
    }
  ];

  return (
    // Container Principal: Ocupa toda a largura/altura e organiza Header e Corpo em coluna
    <div className="bg-[#1A1A1A] w-full h-full flex flex-col font-sans text-gray-200">
      
      {/* 1. O Header fica fixo no topo naturalmente pelo fluxo flex-col */}
      <Header />
      
      {/* 2. Área de Conteúdo (Sidebar + Main) */}
      {/* flex-1: Ocupa todo o espaço restante abaixo do header */}
      {/* overflow-hidden: Impede a rolagem na página inteira (para rolar só o main) */}
      <div className="flex flex-1 overflow-hidden">
        
        {/* A Sidebar fica fixa à esquerda */}
        <Sidebar />

        {/* 3. Área Principal (Onde ficam os cards) */}
        {/* flex-1: Ocupa a largura restante ao lado da sidebar */}
        {/* overflow-y-auto: A barra de rolagem aparece SÓ AQUI se tiver muitos cards */}
        <main className="flex-1 p-8 overflow-y-auto">
          
          <div className="mb-8 border-b border-gray-700 pb-4">
            <h2 className="text-4xl font-bold text-white">Minhas Fichas</h2>
            <p className="text-gray-400 mt-2">Gerencie seus personagens e criaturas do D&D 5.5</p>
          </div>

          {/* Grid Responsivo: 1 coluna no celular, 2 no tablet, 3 em telas grandes */}
          <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            {mySheets.map((sheet) => (
              <CharacterCard
                key={sheet.id}
                title={sheet.title}
                category={sheet.category}
                race={sheet.race}
                charClass={sheet.charClass}
                image={sheet.image}
              />
            ))}
          </div>

        </main>
      </div>
    </div>
  );
};

export default Dashboard;