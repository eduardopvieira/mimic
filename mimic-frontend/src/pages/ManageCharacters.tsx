import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';
import Sidebar from '../components/layout/Sidebar';
import CharacterCard from '../components/ui/CharacterCard';

interface PersonagemListagem {
  id: number;
  nomePersonagem: string;
  nivel: number;
  racaNome?: string; 
  classeNome?: string;
  imagem?: string;
}

const ManageCharacters = () => {
  const navigate = useNavigate();
  const [personagens, setPersonagens] = useState<PersonagemListagem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');


  useEffect(() => {
    const fetchMeusPersonagens = async () => {
      const token = localStorage.getItem('token');
      const usuarioId = localStorage.getItem('usuarioId');

      if (!usuarioId || !token) {
        setError("Usuário não autenticado.");
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(`http://localhost:8080/api/personagens?usuarioId=${usuarioId}`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        if (response.ok) {
          const data = await response.json();
          setPersonagens(data);
        } else {
          setError("Erro ao buscar personagens.");
        }
      } catch (err) {
        console.error(err);
        setError("Erro de conexão com o servidor.");
      } finally {
        setLoading(false);
      }
    };

    fetchMeusPersonagens();
  }, []);


  const handleView = (id: number) => {
    navigate(`/personagem/${id}`);
  };


  const handleDelete = async (id: number) => {
    if (!window.confirm("Tem certeza que deseja excluir este personagem? Essa ação não pode ser desfeita.")) {
        return;
    }

    const token = localStorage.getItem('token');
    const usuarioId = localStorage.getItem('usuarioId');

    try {
        const response = await fetch(`http://localhost:8080/api/personagens/${id}?usuarioId=${usuarioId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
          
            setPersonagens(prev => prev.filter(p => p.id !== id));
        } else {
            alert("Erro ao excluir personagem. Tente novamente.");
        }
    } catch (error) {
        console.error(error);
        alert("Erro de conexão ao excluir.");
    }
  };


  const handleEdit = (id: number) => {
      navigate(`/editar-personagem/${id}`);
  };


  const getImagemSrc = (imgData?: string) => {
    if (!imgData) return undefined; 
  
    if (imgData.startsWith('data:image')) return imgData;
    return `data:image/jpeg;base64,${imgData}`;
  };


  return (
    <div className="bg-[#1A1A1A] w-full h-full flex flex-col font-sans text-gray-200 min-h-screen">
      
      <Header />
      
      <div className="flex flex-1 overflow-hidden">
        <Sidebar />
        
        <main className="flex-1 p-8 overflow-y-auto">
          
          <div className="flex justify-between items-center mb-8 border-b border-gray-700 pb-4">
            <div>
                <h2 className="text-4xl font-bold text-white font-medieval">Minhas Fichas</h2>
                <p className="text-gray-400 mt-2">Gerencie seus personagens do D&D 5.5.</p>
            </div>

            <Link to='/criar-personagem' className="flex items-center gap-2 bg-red-600 hover:bg-red-500 text-white font-bold py-2 px-6 rounded shadow-lg shadow-red-900/50 transition transform hover:scale-105">
                <span className="text-2xl leading-none mb-1">+</span>
                <span>Novo Personagem</span>
            </Link>
          </div>

          {loading && (
            <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-red-500"></div>
            </div>
          )}

          {error && (
            <div className="bg-red-900/50 border border-red-500 p-4 rounded text-center text-white mb-6">
                <p>{error}</p>
            </div>
          )}

          {!loading && !error && personagens.length === 0 && (
            <div className="text-center py-20 bg-[#2D2D2D] rounded-lg border border-dashed border-gray-600">
                <h3 className="text-2xl text-gray-300 font-semibold mb-2">Você ainda não tem personagens.</h3>
            </div>
          )}

          <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            {personagens.map((personagem) => (
              <CharacterCard
                key={personagem.id}
                id={personagem.id}
                title={personagem.nomePersonagem}
                race={personagem.racaNome || "Desconhecido"}
                charClass={personagem.classeNome || "Desconhecido"}
                image={getImagemSrc(personagem.imagem)}
                
                onView={handleView}
                onEdit={handleEdit}
                onDelete={handleDelete}
              />
            ))}
          </div>

        </main>
      </div>
    </div>
  );
};

export default ManageCharacters;