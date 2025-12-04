import { Link } from 'react-router-dom';
import Header from '../components/layout/Header';
import Sidebar from '../components/layout/Sidebar';
import CreatureCard from '../components/ui/CreatureCard';

const ManageCreatures = () => {
  // MOCKADOS ENQNT O BACK N FUNCIONA
  const mySheets = [
    {
      id: 2,
      title: "Lobo Grande",
      race: "Lobo",
      size: "Grande",
      image: "/wolf.jpg"
    },
    {
      id: 4,
      title: "Beholder Xy'lor",
      race: "Aberração",
      size: "Médio",
      image: null
    }

  ];

  return (
    <div className="bg-[#1A1A1A] w-full h-full flex flex-col font-sans text-gray-200">
      <Header />
      <div className="flex flex-1 overflow-hidden">
        <Sidebar />
        <main className="flex-1 p-8 overflow-y-auto">
          <div className="flex justify-between items-center mb-8 border-b border-gray-700 pb-4">
            <div>
                <h2 className="text-4xl font-bold text-white font-medieval">Minhas Fichas - Criatura</h2>
                <p className="text-gray-400 mt-2">Crie criaturas para enfrentar seus jogadores.</p>
            </div>
            <Link to='/criar-criatura' className="flex items-center gap-2 bg-red-600 hover:bg-red-500 text-white font-bold py-2 px-6 rounded shadow-lg shadow-red-900/50 transition transform hover:scale-105">
                <span className="text-2xl leading-none mb-1">+</span>
                <span>Nova Criatura</span>
            </Link>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            {mySheets.map((sheet) => (
              <CreatureCard
                key={sheet.id}
                title={sheet.title}
                race={sheet.race}
                size={sheet.size}
                image={sheet.image}
              />
            ))}
          </div>
        </main>
      </div>
    </div>
  );
};

export default ManageCreatures;