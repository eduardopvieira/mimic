import { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import Header from '../components/layout/Header';
import Sidebar from '../components/layout/Sidebar';
import OriginCard from '../components/ui/OriginCard';

// Interface deve bater com o retorno do OrigemDTO do Java
interface Origin {
  id: number;
  nome: string;
  pericias: string[];     // Java: Set<String> pericias
  equipamentoInicial: string; // Java: equipamentoInicial
}

const ManageOrigins = () => {
  const navigate = useNavigate();
  const [origins, setOrigins] = useState<Origin[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchOrigins();
  }, []);

  const fetchOrigins = async () => {
    const token = localStorage.getItem('token');
    const usuarioId = localStorage.getItem('usuarioId');

    if (!token || !usuarioId) return;

    try {
        const response = await fetch(`http://localhost:8080/api/origens?usuarioId=${usuarioId}`, {
            headers: { 'Authorization': token }
        });
        if (response.ok) {
            const data = await response.json();
            // Adaptador caso o Java retorne nomes diferentes
            const adaptedOrigins = data.map((o: any) => ({
                id: o.id,
                nome: o.nome,
                pericias: o.pericias || [],
                equipamentoInicial: o.equipamentoInicial
            }));
            setOrigins(adaptedOrigins);
        }
    } catch (error) {
        console.error("Erro ao buscar origens", error);
    } finally {
        setLoading(false);
    }
  };

  const handleEdit = (id: number) => {
    navigate(`/editar-origem/${id}`);
  };

  const handleDelete = async (id: number) => {
    if (!confirm("Tem certeza que deseja excluir esta origem?")) return;

    const token = localStorage.getItem('token');
    const usuarioId = localStorage.getItem('usuarioId');

    try {
        const response = await fetch(`http://localhost:8080/api/origens/${id}?usuarioId=${usuarioId}`, {
            method: 'DELETE',
            headers: { 'Authorization': token || '' }
        });

        if (response.ok) {
            setOrigins(prev => prev.filter(origin => origin.id !== id));
        } else {
            alert("Erro ao excluir. Verifique se a origem é do sistema.");
        }
    } catch (error) {
        console.error(error);
    }
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
            <Link 
                to='/criar-origem'
                className="flex items-center gap-2 bg-red-600 hover:bg-red-500 text-white font-bold py-2 px-6 rounded shadow-lg shadow-red-900/50 transition transform hover:scale-105"
            >
                <span className="text-2xl leading-none mb-1">+</span>
                <span>Nova Origem</span>
            </Link>
          </div>

          {loading ? (
             <div className="text-center text-gray-400 mt-20">Carregando grimório de histórias...</div>
          ) : origins.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
              {origins.map((origin) => (
                <OriginCard 
                    key={origin.id} 
                    id={origin.id}
                    name={origin.nome}
                    skills={origin.pericias}
                    equipment={origin.equipamentoInicial}
                    onEdit={handleEdit} 
                    onDelete={handleDelete} 
                />
              ))}
            </div>
          ) : (
             <div className="flex flex-col items-center justify-center h-64 border-2 border-dashed border-gray-700 rounded-lg text-gray-500">
                <p className="text-xl font-medieval">Nenhuma origem encontrada.</p>
             </div>
          )}

        </main>
      </div>
    </div>
  );
};

export default ManageOrigins;