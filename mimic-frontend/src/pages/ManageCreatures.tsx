import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';
import Sidebar from '../components/layout/Sidebar';
import CreatureCard from '../components/ui/CreatureCard';

interface CriaturaListagem {
  id: number;
  nome: string;
  tipo: string;
  tamanho: string; // Precisamos garantir que isso venha do DTO
  // imagem: string; // Se um dia criaturas tiverem imagem
}

const ManageCreatures = () => {
  const navigate = useNavigate();
  const [criaturas, setCriaturas] = useState<CriaturaListagem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  // --- FETCH ---
  useEffect(() => {
    const fetchCriaturas = async () => {
      const token = localStorage.getItem('token');
      const usuarioId = localStorage.getItem('usuarioId');

      if (!usuarioId || !token) {
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(`http://localhost:8080/api/criaturas?usuarioId=${usuarioId}`, {
          headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
          const data = await response.json();
          setCriaturas(data);
        } else {
          setError("Erro ao buscar criaturas.");
        }
      } catch (err) {
        console.error(err);
        setError("Erro de conexão.");
      } finally {
        setLoading(false);
      }
    };

    fetchCriaturas();
  }, []);

  // --- ACTIONS ---
  const handleDelete = async (id: number) => {
    if (!window.confirm("Tem certeza que deseja excluir esta criatura?")) return;
    
    const token = localStorage.getItem('token');
    const usuarioId = localStorage.getItem('usuarioId');

    try {
        const res = await fetch(`http://localhost:8080/api/criaturas/${id}?usuarioId=${usuarioId}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (res.ok) {
            setCriaturas(prev => prev.filter(c => c.id !== id));
        } else {
            alert("Erro ao excluir.");
        }
    } catch (error) {
        console.error(error);
        alert("Erro de conexão.");
    }
  };

  const handleEdit = (id: number) => {
      navigate(`/editar-criatura/${id}`);
  };

  return (
    <div className="bg-[#1A1A1A] w-full h-full flex flex-col font-sans text-gray-200">
      <Header />
      <div className="flex flex-1 overflow-hidden">
        <Sidebar />
        <main className="flex-1 p-8 overflow-y-auto">
          
          <div className="flex justify-between items-center mb-8 border-b border-gray-700 pb-4">
            <div>
                <h2 className="text-4xl font-bold text-white font-medieval">Bestiário Pessoal</h2>
                <p className="text-gray-400 mt-2">Gerencie suas criaturas e monstros homebrew.</p>
            </div>
            <Link to='/criar-criatura' className="flex items-center gap-2 bg-red-600 hover:bg-red-500 text-white font-bold py-2 px-6 rounded shadow-lg shadow-red-900/50 transition transform hover:scale-105">
                <span className="text-2xl leading-none mb-1">+</span><span>Nova Criatura</span>
            </Link>
          </div>

          {loading && (
            <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-red-500"></div>
            </div>
          )}

          {!loading && !error && criaturas.length === 0 && (
             <div className="text-center py-20 bg-[#2D2D2D] rounded-lg border border-dashed border-gray-600">
                <h3 className="text-2xl text-gray-300">Nenhuma criatura encontrada.</h3>
             </div>
          )}

          <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            {criaturas.map((criatura) => (
              <CreatureCard
                key={criatura.id}
                id={criatura.id}
                title={criatura.nome}
                
                // Mapeamento correto dos campos
                race={criatura.tipo || "Tipo Desconhecido"} 
                size={criatura.tamanho || "Médio"} 
                
                image={null} // Passar imagem se tiver no futuro
                
                onEdit={handleEdit}
                onDelete={handleDelete}
                onView={(id) => navigate(`/criatura/${id}`)}
              />
            ))}
          </div>
        </main>
      </div>
    </div>
  );
};

export default ManageCreatures;