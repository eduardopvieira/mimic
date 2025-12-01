interface CharacterCardProps {
  title: string;
  category: string;
  race: string;
  charClass: string;
  image: string | null; 
}

const CharacterCard = ({ title, category, race, charClass, image }: CharacterCardProps) => {
  return (
    <a href="#" className="block bg-[#2D2D2D] rounded-lg shadow-2xl overflow-hidden group transition-all duration-300 hover:shadow-red-500/30 border border-transparent hover:border-red-500/50">
      
      {/* HEADER DO CARD */}
      <div className="p-4 bg-[#3a3a3a] border-b-2 border-red-600">
        <h3 className="text-2xl font-bold text-white truncate">{title}</h3>
      </div>

      {/* CORPO DO CARD */}
      <div className="p-5 flex space-x-6 items-start">
        
        <div className="bg-[#444444] rounded-lg flex-shrink-0 ring-2 ring-gray-600 group-hover:ring-red-500 transition-all self-stretch w-32 h-32 overflow-hidden flex items-center justify-center text-gray-500">
          {image ? (
            <img src={image} alt={title} className="w-full h-full object-cover object-top" />
          ) : (
            <svg className="w-16 h-16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
            </svg>
          )}
        </div>

        <div className="flex flex-col justify-start space-y-3 flex-grow">
          <div>
            <span className="text-xs font-medium text-gray-400 uppercase tracking-wider">Categoria</span>
            <p className="text-base font-semibold text-white">{category}</p>
          </div>
          <div>
            <span className="text-xs font-medium text-gray-400 uppercase tracking-wider">Raça</span>
            <p className="text-base font-semibold text-white">{race}</p>
          </div>
          <div>
            <span className="text-xs font-medium text-gray-400 uppercase tracking-wider">Classe</span>
            <p className="text-base font-semibold text-white">{charClass}</p>
          </div>
        </div>
      </div>
    </a>
  );
};

export default CharacterCard;