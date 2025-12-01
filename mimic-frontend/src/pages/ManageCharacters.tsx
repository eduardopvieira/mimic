import { Link } from 'react-router-dom';
import Header from '../components/layout/Header';
import Sidebar from '../components/layout/Sidebar';
import CharacterCard from '../components/ui/CharacterCard';

const ManageCharacters = () => {

    //MOCKADOS ENQUANTO O BACK N FUNCIONA
  const mySheets = [
    {
      id: 1,
      title: "Eldrin Luthien",
      category: "Personagem",
      race: "Alto Elfo",
      charClass: "Mago",
      image: "/mage-highelf.jpg" 
    },
    {
      id: 2,
      title: "Lyra Swiftwind",
      category: "Personagem",
      race: "Halfling",
      charClass: "Ladina",
      image: null 
    },

  ];

  return (
    <div className="bg-[#1A1A1A] w-full h-full flex flex-col font-sans text-gray-200">
      
      <Header />
      <div className="flex flex-1 overflow-hidden">
        <Sidebar />
        <main className="flex-1 p-8 overflow-y-auto">
          <div className="flex justify-between items-center mb-8 border-b border-gray-700 pb-4">
            <div>
                <h2 className="text-4xl font-bold text-white font-medieval">Minhas Fichas - Personagem</h2>
                <p className="text-gray-400 mt-2">Crie personagens para jogar.</p>
            </div>

            <Link to='/criar-personagem' className="flex items-center gap-2 bg-red-600 hover:bg-red-500 text-white font-bold py-2 px-6 rounded shadow-lg shadow-red-900/50 transition transform hover:scale-105">
                <span className="text-2xl leading-none mb-1">+</span>
                <span>Novo Personagem</span>
            </Link>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            {mySheets.map((sheet) => (
              <CharacterCard
                key={sheet.id}
                title={sheet.title}
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

export default ManageCharacters;