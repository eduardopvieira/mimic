import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Header from '../components/layout/Header';

// --- CONSTANTES ---

const skillOptions = [
    { val: "Acrobacia", label: "Acrobacia (Des)" },
    { val: "Arcanismo", label: "Arcanismo (Int)" },
    { val: "Atletismo", label: "Atletismo (For)" },
    { val: "Atuacao", label: "Atuação (Car)" },
    { val: "Enganacao", label: "Enganação (Car)" },
    { val: "Furtividade", label: "Furtividade (Des)" },
    { val: "Historia", label: "História (Int)" },
    { val: "Intimidacao", label: "Intimidação (Car)" },
    { val: "Intuicao", label: "Intuição (Sab)" },
    { val: "Investigacao", label: "Investigação (Int)" },
    { val: "Lidar com Animais", label: "Lidar com Animais (Sab)" },
    { val: "Medicina", label: "Medicina (Sab)" },
    { val: "Natureza", label: "Natureza (Int)" },
    { val: "Percepcao", label: "Percepção (Sab)" },
    { val: "Persuasao", label: "Persuasão (Car)" },
    { val: "Prestidigitacao", label: "Prestidigitação (Des)" },
    { val: "Religiao", label: "Religião (Int)" },
    { val: "Sobrevivencia", label: "Sobrevivência (Sab)" },
];

// Valores devem bater com o Enum 'Atributo' do Java
const atributosOptions = [
    { val: "FORCA", label: "Força" },
    { val: "DESTREZA", label: "Destreza" },
    { val: "CONSTITUICAO", label: "Constituição" },
    { val: "INTELIGENCIA", label: "Inteligência" },
    { val: "SABEDORIA", label: "Sabedoria" },
    { val: "CARISMA", label: "Carisma" }
];

interface TalentoSimple {
    id: number;
    nome: string;
}

const CreateOrigin = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEditMode = !!id;

  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  
  // Lista de talentos carregada do backend para o select
  const [talentosDisponiveis, setTalentosDisponiveis] = useState<TalentoSimple[]>([]);

  // State do Formulário
  const [formData, setFormData] = useState({
    nome: '',
    descricao: '',
    equipamentoA: '',
    equipamentoB: '',
    pericia1: '',
    pericia2: '',
    ferramenta: '',
    atributosPermitidos: [] as string[],
    talentoInicialId: '' // String para o select, converte p/ number no submit
  });

  // --- CARGA INICIAL (Talentos e Dados se for Edição) ---
  useEffect(() => {
    const carregarDados = async () => {
        setLoading(true);
        const token = localStorage.getItem('token');
        const usuarioId = localStorage.getItem('usuarioId');

        if (!token || !usuarioId) {
            setError("Usuário não autenticado.");
            setLoading(false);
            return;
        }

        try {
            // 1. Busca Lista de Talentos (Endpoint: GET /api/talentos)
            // Lembre-se de criar/liberar este endpoint no Backend (BibliotecaController)
            const resTalentos = await fetch(`http://localhost:8080/api/talentos`, {
                headers: { 'Authorization': token }
            });
            
            if (resTalentos.ok) {
                const dataTalentos = await resTalentos.json();
                setTalentosDisponiveis(dataTalentos);
            } else {
                console.error("Erro ao carregar talentos.");
            }

            // 2. Se for edição, busca os dados da Origem
            if (isEditMode) {
                const resOrigem = await fetch(`http://localhost:8080/api/origens/${id}?usuarioId=${usuarioId}`, {
                    headers: { 'Authorization': token }
                });
                
                if (!resOrigem.ok) throw new Error("Erro ao carregar origem para edição.");
                
                const data = await resOrigem.json();
                
                // Mapeia do DTO Java para o Form React
                setFormData({
                    nome: data.nome,
                    descricao: data.descricao || '',
                    equipamentoA: data.equipamentoA || '',
                    equipamentoB: data.equipamentoB || '',
                    pericia1: data.pericias && data.pericias.length > 0 ? data.pericias[0] : '',
                    pericia2: data.pericias && data.pericias.length > 1 ? data.pericias[1] : '',
                    ferramenta: data.ferramenta || '',
                    atributosPermitidos: data.atributosPermitidos || [],
                    talentoInicialId: data.talentoInicialId ? data.talentoInicialId.toString() : ''
                });
            }
        } catch (err) {
            console.error(err);
            setError("Erro ao carregar dados do servidor.");
        } finally {
            setLoading(false);
        }
    };

    carregarDados();
  }, [id, isEditMode]);

  // --- HANDLERS ---

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Lógica para marcar/desmarcar os atributos (Checkboxes visuais)
  const handleAtributoToggle = (val: string) => {
    setFormData(prev => {
        const jaTem = prev.atributosPermitidos.includes(val);
        let novos;
        
        if (jaTem) {
            novos = prev.atributosPermitidos.filter(a => a !== val);
        } else {
            // Regra opcional: Limitar a 3 escolhas (padrão D&D)
            if (prev.atributosPermitidos.length >= 3) return prev; 
            novos = [...prev.atributosPermitidos, val];
        }
        
        return { ...prev, atributosPermitidos: novos };
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    // Validações Básicas
    if (!formData.nome || !formData.pericia1 || !formData.pericia2 || !formData.equipamentoA || !formData.equipamentoB || !formData.talentoInicialId) {
        setError('Por favor, preencha todos os campos obrigatórios (*).');
        return;
    }
    
    // Validação de Atributos (Geralmente Origens dão 3 opções)
    if (formData.atributosPermitidos.length < 3) {
        setError('Por favor, selecione 3 atributos para potencializar (Regra D&D 2024).');
        return;
    }

    const token = localStorage.getItem('token');
    const usuarioId = localStorage.getItem('usuarioId');

    const payload = {
        nome: formData.nome,
        descricao: formData.descricao,
        equipamentoA: formData.equipamentoA,
        equipamentoB: formData.equipamentoB,
        pericias: [formData.pericia1, formData.pericia2], 
        ferramenta: formData.ferramenta,
        atributosPermitidos: formData.atributosPermitidos,
        talentoInicialId: parseInt(formData.talentoInicialId)
    };

    // alert(JSON.stringify(payload, null, 2));

    try {
        const url = isEditMode 
            ? `http://localhost:8080/api/origens/${id}?usuarioId=${usuarioId}`
            : `http://localhost:8080/api/origens?usuarioId=${usuarioId}`;
        
        const method = isEditMode ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token ? `Bearer ${token}` : ''
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const txt = await response.text();
            throw new Error(txt || 'Erro ao salvar origem.');
        }

        console.log("Origem salva com sucesso!");
        navigate('/gerenciar-origens');

    } catch (err: any) {
        console.error(err);
        setError(err.message || "Erro de conexão.");
    }
  };

  if (loading) {
    return (
        <div className="bg-[#1A1A1A] h-screen flex items-center justify-center text-white">
            <p className="text-xl animate-pulse">Carregando dados...</p>
        </div>
    );
  }

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />

      <main className="container mx-auto p-8">
        <div className="max-w-4xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl">
            
            <h2 className="text-3xl font-semibold text-white mb-2 font-medieval">
                {isEditMode ? 'Editar Origem' : 'Criar Origem'}
            </h2>
            <hr className="border-t-2 border-red-600 mb-8" />

            <form onSubmit={handleSubmit}>
            
                {/* LINHA 1: NOME E TALENTO */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-6">
                    <div>
                        <label className="text-lg font-medium text-gray-300">Nome da Origem *</label>
                        <input type="text" name="nome" value={formData.nome} onChange={handleChange}
                                placeholder="Ex: Acólito, Criminoso..."
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500" />
                    </div>

                    <div>
                        <label className="text-lg font-medium text-gray-300">Talento Inicial *</label>
                        <div className="relative w-full">
                            <select name="talentoInicialId" value={formData.talentoInicialId} onChange={handleChange}
                                    className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 appearance-none cursor-pointer">
                                <option value="" disabled>Selecione um talento...</option>
                                {talentosDisponiveis.map(t => (
                                    <option key={t.id} value={t.id}>{t.nome}</option>
                                ))}
                            </select>
                            <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-400">
                                <svg className="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
                            </div>
                        </div>
                        <p className="text-xs text-gray-500 mt-1">D&D 2024: Toda origem concede um talento de Nível 1.</p>
                    </div>
                </div> 

                {/* ATRIBUTOS PERMITIDOS (CHECKBOXES VISUAIS) */}
                <div className="mt-6">
                    <label className="text-lg font-medium text-gray-300 mb-2 block">
                        Atributos Permitidos (Escolha 3) *
                    </label>
                    <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
                        {atributosOptions.map(attr => (
                            <div key={attr.val} 
                                 onClick={() => handleAtributoToggle(attr.val)}
                                 className={`p-3 rounded cursor-pointer border transition-colors flex items-center gap-3 select-none ${
                                    formData.atributosPermitidos.includes(attr.val)
                                    ? 'bg-red-900/40 border-red-500 text-white'
                                    : 'bg-[#444444] border-gray-600 text-gray-400 hover:bg-[#505050]'
                                 }`}>
                                {/* Bolinha indicadora */}
                                <div className={`w-5 h-5 rounded-full border flex items-center justify-center ${
                                    formData.atributosPermitidos.includes(attr.val) ? 'bg-red-500 border-red-500' : 'border-gray-400'
                                }`}>
                                    {formData.atributosPermitidos.includes(attr.val) && (
                                        <div className="w-2 h-2 bg-white rounded-full"></div>
                                    )}
                                </div>
                                <span className="font-medium">{attr.label}</span>
                            </div>
                        ))}
                    </div>
                    <p className="text-xs text-gray-500 mt-1">
                        Selecionados: {formData.atributosPermitidos.length}/3
                    </p>
                </div>

                {/* PERÍCIAS */}
                <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label className="block text-lg font-medium text-gray-300 mb-2">Perícia 1 *</label>
                        <div className="relative">
                            <select name="pericia1" value={formData.pericia1} onChange={handleChange}
                                    className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 appearance-none cursor-pointer">
                                <option value="">Selecione...</option>
                                {skillOptions.map(opt => <option key={opt.val + '1'} value={opt.val}>{opt.label}</option>)}
                            </select>
                        </div>
                    </div>
                    <div>
                        <label className="block text-lg font-medium text-gray-300 mb-2">Perícia 2 *</label>
                        <div className="relative">
                            <select name="pericia2" value={formData.pericia2} onChange={handleChange}
                                    className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 appearance-none cursor-pointer">
                                <option value="">Selecione...</option>
                                {skillOptions.map(opt => <option key={opt.val + '2'} value={opt.val}>{opt.label}</option>)}
                            </select>
                        </div>
                    </div>
                </div>
                
                {/* FERRAMENTA */}
                <div className="mt-6">
                    <label className="block text-lg font-medium text-gray-300 mb-2">Ferramenta (Opcional)</label>
                    <input type="text" name="ferramenta" value={formData.ferramenta} onChange={handleChange}
                            className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 placeholder-gray-400"
                            placeholder="Ex: Kit de Disfarces" />
                </div>

                {/* EQUIPAMENTO */}
                <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label className="block text-lg font-medium text-gray-300 mb-2">Equipamento Inicial A *</label>
                        <textarea name="equipamentoA" rows={4} value={formData.equipamentoA} onChange={handleChange}
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 resize-y placeholder-gray-400"
                                placeholder="Opção A. Ex: Cota de malha, Escudo, Maça, Símbolo Sagrado..."></textarea>
                    </div>
                    
                    <div>
                        <label className="block text-lg font-medium text-gray-300 mb-2">Equipamento Inicial B *</label>
                        <textarea name="equipamentoB" rows={4} value={formData.equipamentoB} onChange={handleChange}
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 resize-y placeholder-gray-400"
                                placeholder="Opção B. Ex: 50 PO (Moedas de Ouro)"></textarea>
                    </div>
                </div>

                {/* DESCRIÇÃO */}
                <div className="mt-6">
                    <label className="block text-lg font-medium text-gray-300 mb-2">Descrição (Flavor Text)</label>
                    <textarea name="descricao" rows={3} value={formData.descricao} onChange={handleChange}
                            className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 resize-y placeholder-gray-400"
                            placeholder="Descreva o passado do personagem..."></textarea>
                </div>

                {error && (
                   <div className="mt-6 p-3 rounded bg-red-900/30 border border-red-500/50 text-red-200 text-lg text-center animate-pulse">
                      {error}
                   </div>
                )}

                <div className="mt-10 flex justify-between">
                    <button type="button" 
                            className="px-6 py-2 rounded bg-red-600 hover:bg-red-500 text-white font-semibold transition"
                            onClick={() => navigate('/gerenciar-origens')}>
                        Cancelar
                    </button>
                    <button type="submit" className="px-6 py-2 rounded bg-green-600 hover:bg-green-500 text-white font-semibold transition shadow-lg shadow-green-900/40">
                        {isEditMode ? 'Salvar Alterações' : 'Criar Origem'}
                    </button>
                </div>
            </form>
        </div>
      </main>
    </div>
  );
};

export default CreateOrigin;