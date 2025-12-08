import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Header from '../components/layout/Header';

const escolasDeMagia = [
    { value: "ABJURACAO", label: "Abjuração" },
    { value: "ADIVINHACAO", label: "Adivinhação" },
    { value: "ENCANTAMENTO", label: "Encantamento" },
    { value: "EVOCACAO", label: "Evocação" },
    { value: "ILUSAO", label: "Ilusão" },
    { value: "INVOCACAO", label: "Invocação" },
    { value: "NECROMANCIA", label: "Necromancia" },
    { value: "TRANSMUTACAO", label: "Transmutação" }
];

const CreateSpell = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEditMode = !!id;   

  const [error, setError] = useState('');
  const [loadingData, setLoadingData] = useState(false);

  // Estado inicial do formulário
  const [formData, setFormData] = useState({
    nome: '',
    alcance: '',
    conjuracao: '', // Mapeia para 'tempoConjuracao'
    nivel: 0,       // Mapeia para 'circulo'
    escola: '',     // Armazena a string selecionada no dropdown
    duracao: '',
    components: [] as string[],
    materialDesc: '',
    isRitual: false,
    isConcentration: false,
    descricao: '',
    escola: ''
  });


  useEffect(() => {
    if (isEditMode) {
      fetchSpellData();
    }
  }, [id]);

  const fetchSpellData = async () => {
    setLoadingData(true);
    const token = localStorage.getItem('token');
    const usuarioId = localStorage.getItem('usuarioId');

    try {
      const response = await fetch(`http://localhost:8080/api/magias/${id}?usuarioId=${usuarioId}`, {
        headers: { 'Authorization': token || '' }
      });

      if (!response.ok) throw new Error("Erro ao carregar magia.");

      const data = await response.json();
    
      const compsArray = [];
      if (data.componentes.includes('V')) compsArray.push('V');
      if (data.componentes.includes('S')) compsArray.push('S');
      if (data.componentes.includes('M')) compsArray.push('M');

    
      const materialMatch = data.componentes.match(/\(([^)]+)\)/);
      const materialText = materialMatch ? materialMatch[1] : '';

    
      setFormData({
        nome: data.nome,
        alcance: data.alcance,
        conjuracao: data.tempoConjuracao,
        nivel: data.circulo,             
        duracao: data.duracao,
        components: compsArray,
        materialDesc: materialText,
        isRitual: data.eRitual,          
        isConcentration: data.eConcentracao,
        descricao: data.descricao,
        escola: data.escola || ''
      });

    } catch (err) {
      console.error(err);
      setError("Não foi possível carregar os dados da magia.");
    } finally {
      setLoadingData(false);
    }
  };


  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // Atualiza checkboxes simples (Ritual e Concentração)
  const handleCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, checked } = e.target;
    setFormData(prev => ({ ...prev, [name]: checked }));
  };

  // Lógica para adicionar/remover V, S, M do array
  const handleComponentToggle = (val: string) => {
    setFormData(prev => {
      const hasComponent = prev.components.includes(val);
      const newComponents = hasComponent
        ? prev.components.filter(c => c !== val)
        : [...prev.components, val];
      return { ...prev, components: newComponents };
    });
  };


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    // Validação Básica
    if (!formData.nome || !formData.alcance || !formData.conjuracao || !formData.duracao || !formData.descricao) {
      setError('Por favor, preencha todos os campos obrigatórios.');
      return;
    }

    const token = localStorage.getItem('token');
    const usuarioId = localStorage.getItem('usuarioId');

    if (!token || !usuarioId) {
        setError('Sessão inválida. Faça login novamente.');
        return;
    }

  
    let componentesString = formData.components.join(', ');
    if (formData.components.includes('M') && formData.materialDesc) {
        componentesString += ` (${formData.materialDesc})`;
    }

    const payload = {
        nome: formData.nome,
        alcance: formData.alcance,
        tempoConjuracao: formData.conjuracao,
        circulo: formData.nivel,
        duracao: formData.duracao,
        componentes: componentesString,
        eRitual: formData.isRitual,         
        eConcentracao: formData.isConcentration, 
        descricao: formData.descricao,
        escola: formData.escola || null
    };

    try {
        const url = isEditMode 
            ? `http://localhost:8080/api/magias/${id}?usuarioId=${usuarioId}`
            : `http://localhost:8080/api/magias?usuarioId=${usuarioId}`;
        
        const method = isEditMode ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) throw new Error('Erro ao salvar magia.');

        console.log(isEditMode ? "Editado com sucesso!" : "Criado com sucesso!");
        navigate('/gerenciar-magias');

    } catch (err) {
        console.error(err);
        setError('Falha na conexão com o servidor.');
    }
  };

  if (loadingData) {
    return (
        <div className="bg-[#1A1A1A] h-screen flex items-center justify-center text-white">
            <p className="text-xl animate-pulse">Carregando dados do grimório...</p>
        </div>
    );
  }

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />

      <main className="container mx-auto p-8">
        <div className="max-w-4xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl">

          <h2 className="text-3xl font-semibold text-white mb-2 font-medieval">
            {isEditMode ? 'Editar Magia' : 'Criar Magia'}
          </h2>
          <hr className="border-t-2 border-red-600 mb-8" />

          <form onSubmit={handleSubmit}>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-6">

              <div className="grid grid-cols-[auto_1fr] gap-6 items-center">
                <label className="text-lg font-medium text-gray-300">Nome</label>
                <input type="text" name="nome" value={formData.nome} onChange={handleChange} className="p-2 rounded bg-[#444444] border border-gray-600 text-white w-full focus:outline-none focus:ring-2 focus:ring-red-500" placeholder="Ex: Bola de Fogo" />

                <label className="text-lg font-medium text-gray-300">Alcance</label>
                <input type="text" name="alcance" value={formData.alcance} onChange={handleChange} className="p-2 rounded bg-[#444444] border border-gray-600 text-white w-full focus:outline-none focus:ring-2 focus:ring-red-500" placeholder="Ex: 30m / Toque" />

                <label className="text-lg font-medium text-gray-300">Conjuração</label>
                <input type="text" name="conjuracao" value={formData.conjuracao} onChange={handleChange} className="p-2 rounded bg-[#444444] border border-gray-600 text-white w-full focus:outline-none focus:ring-2 focus:ring-red-500" placeholder="Ex: 1 Ação" />
                
                <label className="text-lg font-medium text-gray-300">Escola</label>
                <div className="relative w-full">
                    <select name="escola" value={formData.escola} onChange={handleChange} className="p-2 rounded bg-[#444444] border border-gray-600 text-white w-full appearance-none cursor-pointer focus:outline-none focus:ring-2 focus:ring-red-500">
                        <option value="" disabled>Selecione...</option>
                        {escolasDeMagia.map((e) => (
                            <option key={e.value} value={e.value}>{e.label}</option>
                        ))}
                    </select>
                </div>
              </div>

              <div className="grid grid-cols-[auto_1fr] gap-6 items-center">
                <label className="text-lg font-medium text-gray-300">Nível</label>
                <input type="number" name="nivel" min="0" max="9" value={formData.nivel} onChange={(e) => setFormData({...formData, nivel: parseInt(e.target.value)})} className="p-2 rounded bg-[#444444] border border-gray-600 text-white w-full focus:outline-none focus:ring-2 focus:ring-red-500" />

                <label className="text-lg font-medium text-gray-300">Duração</label>
                <input type="text" name="duracao" value={formData.duracao} onChange={handleChange} className="p-2 rounded bg-[#444444] border border-gray-600 text-white w-full focus:outline-none focus:ring-2 focus:ring-red-500" placeholder="Ex: Instantânea" />

                <label className="text-lg font-medium text-gray-300">Componentes</label>
                <div className="flex items-center space-x-6">
                  {['V', 'S', 'M'].map((comp) => (
                    <div key={comp} className="flex items-center">
                      <input id={`comp-${comp}`} type="checkbox" checked={formData.components.includes(comp)} onChange={() => handleComponentToggle(comp)} className="h-5 w-5 rounded bg-[#444444] text-red-600 focus:ring-red-500 cursor-pointer" />
                      <label htmlFor={`comp-${comp}`} className="ml-2 text-lg text-gray-200 cursor-pointer">{comp}</label>
                    </div>
                  ))}
                </div>

                <label className="text-lg font-medium text-gray-300">Tags</label>
                <div className="flex items-center space-x-6">
                  <div className="flex items-center">
                    <input id="ritual" name="isRitual" type="checkbox" checked={formData.isRitual} onChange={handleCheck} className="h-5 w-5 rounded bg-[#444444] text-red-600 focus:ring-red-500 cursor-pointer" />
                    <label htmlFor="ritual" className="ml-2 text-lg text-gray-200 cursor-pointer">Ritual</label>
                  </div>
                  <div className="flex items-center">
                    <input id="concentracao" name="isConcentration" type="checkbox" checked={formData.isConcentration} onChange={handleCheck} className="h-5 w-5 rounded bg-[#444444] text-red-600 focus:ring-red-500 cursor-pointer" />
                    <label htmlFor="concentracao" className="ml-2 text-lg text-gray-200 cursor-pointer">Concentração</label>
                  </div>
                </div>
              </div>
            </div>

            {formData.components.includes('M') && (
              <div className="mt-6">
                <label className="block text-lg font-medium text-gray-300 mb-2">Descrição do Material</label>
                <input type="text" name="materialDesc" value={formData.materialDesc} onChange={handleChange} className="w-full p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500" placeholder="Ex: diamante de 50 PO" />
              </div>
            )}

            <div className="mt-6">
              <label className="block text-lg font-medium text-gray-300 mb-2">Descrição da Magia</label>
              <textarea name="descricao" rows={8} value={formData.descricao} onChange={handleChange} className="w-full p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 resize-y" placeholder="Efeito..." />
            </div>

            {error && <div className="mt-6 p-3 rounded bg-red-900/30 border border-red-500/50 text-red-200 text-center animate-pulse">{error}</div>}

            <div className="mt-10 flex justify-between">
              <button type="button" onClick={() => navigate('/gerenciar-magias')} className="px-6 py-2 rounded bg-red-600 hover:bg-red-500 text-white font-semibold">Cancelar</button>
              <button type="submit" className="px-6 py-2 rounded bg-green-600 hover:bg-green-500 text-white font-semibold shadow-lg">
                {isEditMode ? 'Salvar Alterações' : 'Criar Magia'}
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateSpell;