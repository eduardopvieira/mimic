import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';
import Stepper from '../components/ui/Stepper';
import InputField from '../components/form/InputField';
import AttributeCard from '../components/form/AttributeCard';
// Trocamos o FreeTextSection pelo DynamicSection para usar Selects vindos do banco
import DynamicSection from '../components/form/DynamicSection'; 

// --- INTERFACES ---

// O que vem do Banco de Dados
interface RecursoCreaturaDTO {
    id: number;
    nome: string;
    descricao: string;
}

// O item visual na lista do formulário
interface DynamicItem {
  id: number;
  value: string | number;
  description: string;
}

interface CreatureData {
  // ETAPA 1
  nome: string; tamanho: string; tipo: string; tag: string; alinhamento: string;
  // ETAPA 2
  ca: string; pv: string; deslBase: string; deslVoo: string; deslNatacao: string;
  // ETAPA 3
  str: number; dex: number; con: number; int: number; wis: number; cha: number;
  // ETAPA 4
  saves: string; skills: string; resistDano: string; imunidDano: string; imunidCond: string; sentidos: string; idiomas: string; nd: string;
  
  // LISTAS DINÂMICAS (Conectadas ao Banco)
  specialAbilities: DynamicItem[];
  actions: DynamicItem[];
  
  // ETAPA 7
  legendaryActions: string; lairActions: string;
}

const CreateCreature = () => {
  const navigate = useNavigate();
  
  const steps = [
    { id: 1, label: "Dados Básicos" },
    { id: 2, label: "Combate" },
    { id: 3, label: "Atributos" },
    { id: 4, label: "Proficiências" },
    { id: 5, label: "Habilidades" },
    { id: 6, label: "Ações" },
    { id: 7, label: "Lendárias" },
  ];

  const [currentStep, setCurrentStep] = useState(1);
  const [loading, setLoading] = useState(true);

  // --- ESTADO DAS LISTAS DO BANCO ---
  const [listas, setListas] = useState({
      habilidades: [] as RecursoCreaturaDTO[],
      acoes: [] as RecursoCreaturaDTO[]
  });

  const [formData, setFormData] = useState<CreatureData>({
    nome: '', tamanho: '', tipo: '', tag: '', alinhamento: '',
    ca: '', pv: '', deslBase: '', deslVoo: '', deslNatacao: '',
    str: 10, dex: 10, con: 10, int: 10, wis: 10, cha: 10,
    saves: '', skills: '', resistDano: '', imunidDano: '', imunidCond: '', sentidos: '', idiomas: '', nd: '',
    
    specialAbilities: [], 
    actions: [],
    
    legendaryActions: '', lairActions: ''
  });

  // --- 1. BUSCAR DADOS DO BACKEND ---
  // --- 1. BUSCAR DADOS DO BACKEND (COM DEBUG) ---
  // --- 1. BUSCAR DADOS DO BACKEND (COM DEBUG DE TOKEN) ---
  // --- 1. BUSCAR DADOS DO BACKEND (Habilidades e Ações) ---
  useEffect(() => {
    const fetchListas = async () => {
        const token = localStorage.getItem('token');
        const usuarioId = localStorage.getItem('usuarioId');

        if (!usuarioId || !token) {
            setLoading(false);
            return;
        }

        // Garante que o token esteja no formato correto sem duplicar "Bearer"
        const tokenLimpo = token.replace("Bearer ", "").trim();
        const headers = { 'Authorization': `Bearer ${tokenLimpo}` };

        try {
            // Promise.all para carregar as duas listas simultaneamente
            const [resHabilidades, resAcoes] = await Promise.all([
                // Se no futuro você quiser filtrar por usuário, adicione ?usuarioId=${usuarioId}
                fetch(`http://localhost:8080/api/habilidades_criatura`, { headers }),
                fetch(`http://localhost:8080/api/acoes_criatura`, { headers })
            ]);

            if (!resHabilidades.ok || !resAcoes.ok) {
                throw new Error("Falha na resposta do servidor (403/404/500)");
            }

            const habilidades = await resHabilidades.json();
            const acoes = await resAcoes.json();

            setListas({
                habilidades,
                acoes
            });

        } catch (error) {
            console.error("Erro ao carregar dados de criatura:", error);
            // alert("Erro ao conectar com o servidor."); 
        } finally {
            setLoading(false);
        }
    };

    fetchListas();
  }, []);

  // --- HANDLERS ---

  const updateData = (field: string, value: any) => setFormData(prev => ({ ...prev, [field]: value }));
  
  // Handlers para Itens Dinâmicos (Genéricos)
  const addDynamicItem = (field: keyof CreatureData) => {
      if (!Array.isArray(formData[field])) return;
      const list = formData[field] as DynamicItem[];
      const newItem: DynamicItem = { id: Date.now(), value: '', description: '' };
      setFormData(prev => ({ ...prev, [field]: [...list, newItem] }));
  };

  const removeDynamicItem = (field: keyof CreatureData, id: number) => {
      if (!Array.isArray(formData[field])) return;
      const list = formData[field] as DynamicItem[];
      setFormData(prev => ({ ...prev, [field]: list.filter(item => item.id !== id) }));
  };

  const updateDynamicItem = (field: keyof CreatureData, id: number, newValueStr: string, sourceOptions: any[]) => {
      if (!Array.isArray(formData[field])) return;
      
      const newValue = Number(newValueStr); // Converte ID para número
      const selectedOption = sourceOptions.find(opt => opt.value === newValue);
      const newDesc = selectedOption ? selectedOption.desc : '';

      const list = formData[field] as DynamicItem[];
      setFormData(prev => ({
          ...prev,
          [field]: list.map(item => item.id === id ? { ...item, value: newValue, description: newDesc } : item)
      }));
  };

  // --- PREPARAÇÃO DE OPÇÕES PARA O SELECT ---
  const opcoesHabilidades = listas.habilidades.map(h => ({
      value: h.id,
      label: h.nome,
      desc: h.descricao
  }));

  const opcoesAcoes = listas.acoes.map(a => ({
      value: a.id,
      label: a.nome,
      desc: a.descricao
  }));

  // --- SUBMIT ---
  const handleSubmit = async () => {
      // Montar Payload para o Backend
      // Nota: O backend provavelmente espera IDs para habilidades e ações
      const payload = {
          ...formData,
          habilidadesIds: formData.specialAbilities.map(i => Number(i.value)).filter(v => v > 0),
          acoesIds: formData.actions.map(i => Number(i.value)).filter(v => v > 0),
          // Remova os arrays de objetos se o backend só quiser IDs
      };

      console.log("Enviando Criatura:", payload);
      // Adicione aqui o fetch POST para /api/criaturas
      // await fetch(...)
      
      navigate('/home-page'); // Ou dashboard
  };

  // Navegação
  const handleStepClick = (id: number) => setCurrentStep(id);
  const nextStep = () => setCurrentStep(prev => Math.min(prev + 1, steps.length));
  const prevStep = () => setCurrentStep(prev => Math.max(prev - 1, 1));

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />
      <main className="container mx-auto p-8">
        <div className="max-w-5xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl min-h-[700px] flex flex-col">
          
          <Stepper steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />

          <form onSubmit={(e) => e.preventDefault()} className="flex-1 flex flex-col justify-between mt-8">
            
            {/* ETAPA 1: DADOS BÁSICOS */}
            {currentStep === 1 && (
                <div className="animate-fade-in space-y-6">
                    <div className="flex justify-between items-center border-b border-gray-700 pb-4 mb-6">
                        <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 font-medieval">Identidade da Criatura</h2>
                        {loading && <span className="text-yellow-500 animate-pulse text-sm">Carregando dados...</span>}
                    </div>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div className="md:col-span-2">
                             <InputField label="Nome da Criatura" value={formData.nome} onChange={(e: any) => updateData('nome', e.target.value)} placeholder="Ex: Dragão Vermelho Jovem" />
                        </div>
                        <div>
                            <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">Tamanho</label>
                            <select value={formData.tamanho} onChange={(e) => updateData('tamanho', e.target.value)} className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white outline-none">
                                <option value="">Selecione...</option>
                                {['Miúdo', 'Pequeno', 'Médio', 'Grande', 'Enorme', 'Imenso'].map(t => <option key={t} value={t}>{t}</option>)}
                            </select>
                        </div>
                        <InputField label="Tipo" value={formData.tipo} onChange={(e: any) => updateData('tipo', e.target.value)} placeholder="Ex: Dragão, Monstruosidade" />
                        <InputField label="Tag (Opcional)" value={formData.tag} onChange={(e: any) => updateData('tag', e.target.value)} placeholder="Ex: Metamorfo" />
                        <div>
                            <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">Alinhamento</label>
                            <select value={formData.alinhamento} onChange={(e) => updateData('alinhamento', e.target.value)} className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white outline-none">
                                <option value="">Selecione...</option>
                                {['Leal e Bom', 'Neutro e Bom', 'Caótico e Bom', 'Leal e Neutro', 'Neutro', 'Caótico e Neutro', 'Leal e Mau', 'Neutro e Mau', 'Caótico e Mau', 'Sem Alinhamento'].map(a => <option key={a} value={a}>{a}</option>)}
                            </select>
                        </div>
                    </div>
                </div>
            )}

            {/* ETAPA 2: COMBATE */}
            {currentStep === 2 && (
                <div className="animate-fade-in space-y-6">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 mb-6 font-medieval">Estatísticas de Combate</h2>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <InputField label="Classe de Armadura (CA)" value={formData.ca} onChange={(e: any) => updateData('ca', e.target.value)} placeholder="Ex: 17 (Natural)" />
                        <InputField label="Pontos de Vida (PV)" value={formData.pv} onChange={(e: any) => updateData('pv', e.target.value)} placeholder="Ex: 136 (16d10 + 48)" />
                        <div className="md:col-span-2 bg-[#333] p-4 rounded-lg border border-gray-600">
                            <label className="block text-white font-bold mb-4 border-b border-gray-600 pb-2">Deslocamento</label>
                            <div className="grid grid-cols-3 gap-4">
                                <InputField label="Base" value={formData.deslBase} onChange={(e: any) => updateData('deslBase', e.target.value)} placeholder="9m" />
                                <InputField label="Voo" value={formData.deslVoo} onChange={(e: any) => updateData('deslVoo', e.target.value)} placeholder="-" />
                                <InputField label="Natação" value={formData.deslNatacao} onChange={(e: any) => updateData('deslNatacao', e.target.value)} placeholder="-" />
                            </div>
                        </div>
                    </div>
                </div>
            )}

            {/* ETAPA 3: ATRIBUTOS */}
            {currentStep === 3 && (
                <div className="animate-fade-in">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 mb-8 font-medieval">Atributos</h2>
                    <div className="grid grid-cols-2 md:grid-cols-3 gap-6">
                        <AttributeCard label="Força" value={formData.str} onChange={(v: number) => updateData('str', v)} />
                        <AttributeCard label="Destreza" value={formData.dex} onChange={(v: number) => updateData('dex', v)} />
                        <AttributeCard label="Constituição" value={formData.con} onChange={(v: number) => updateData('con', v)} />
                        <AttributeCard label="Inteligência" value={formData.int} onChange={(v: number) => updateData('int', v)} />
                        <AttributeCard label="Sabedoria" value={formData.wis} onChange={(v: number) => updateData('wis', v)} />
                        <AttributeCard label="Carisma" value={formData.cha} onChange={(v: number) => updateData('cha', v)} />
                    </div>
                </div>
            )}

            {/* ETAPA 4: PROFICIÊNCIAS */}
            {currentStep === 4 && (
                <div className="animate-fade-in space-y-6">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 mb-6 font-medieval">Proficiências e Sentidos</h2>
                    <div className="space-y-4">
                        <InputField label="Testes de Resistência" value={formData.saves} onChange={(e: any) => updateData('saves', e.target.value)} placeholder="Ex: Des +4, Sab +2" />
                        <InputField label="Perícias" value={formData.skills} onChange={(e: any) => updateData('skills', e.target.value)} placeholder="Ex: Furtividade +6" />
                        <InputField label="Imunidade a Dano" value={formData.imunidDano} onChange={(e: any) => updateData('imunidDano', e.target.value)} placeholder="Ex: Fogo" />
                        <InputField label="Imunidade a Condição" value={formData.imunidCond} onChange={(e: any) => updateData('imunidCond', e.target.value)} placeholder="Ex: Enfeitiçado" />
                        <InputField label="Sentidos" value={formData.sentidos} onChange={(e: any) => updateData('sentidos', e.target.value)} placeholder="Ex: Visão no Escuro 18m" />
                        <InputField label="Idiomas" value={formData.idiomas} onChange={(e: any) => updateData('idiomas', e.target.value)} placeholder="Ex: Comum, Dracônico" />
                        <InputField label="Nível de Desafio (ND)" value={formData.nd} onChange={(e: any) => updateData('nd', e.target.value)} placeholder="Ex: 5 (1.800 XP)" />
                    </div>
                </div>
            )}

            {/* ETAPA 5: HABILIDADES (USANDO DYNAMIC SECTION COM SELECT) */}
            {currentStep === 5 && (
                <DynamicSection 
                    title="Habilidades Especiais" 
                    itemName="Habilidade"
                    items={formData.specialAbilities} 
                    options={opcoesHabilidades} // Passa a lista vinda do banco
                    onAdd={() => addDynamicItem('specialAbilities')} 
                    onRemove={(id: number) => removeDynamicItem('specialAbilities', id)} 
                    onUpdate={(id, val) => updateDynamicItem('specialAbilities', id, val, opcoesHabilidades)} 
                />
            )}

            {/* ETAPA 6: AÇÕES (USANDO DYNAMIC SECTION COM SELECT) */}
            {currentStep === 6 && (
                <DynamicSection 
                    title="Ações da Criatura" 
                    itemName="Ação"
                    items={formData.actions} 
                    options={opcoesAcoes} // Passa a lista vinda do banco
                    onAdd={() => addDynamicItem('actions')} 
                    onRemove={(id: number) => removeDynamicItem('actions', id)} 
                    onUpdate={(id, val) => updateDynamicItem('actions', id, val, opcoesAcoes)} 
                />
            )}

            {/* ETAPA 7: LENDÁRIAS (TEXTO LIVRE) */}
            {currentStep === 7 && (
                <div className="animate-fade-in space-y-6">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 mb-6 font-medieval">Ações Lendárias e de Covil</h2>
                    <div>
                        <label className="block text-gray-400 mb-2 text-sm font-bold uppercase">Ações Lendárias</label>
                        <textarea rows={5} value={formData.legendaryActions} onChange={(e) => updateData('legendaryActions', e.target.value)} className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 outline-none" placeholder="A criatura pode realizar 3 ações lendárias..." />
                    </div>
                    <div>
                        <label className="block text-gray-400 mb-2 text-sm font-bold uppercase">Ações de Covil</label>
                        <textarea rows={5} value={formData.lairActions} onChange={(e) => updateData('lairActions', e.target.value)} className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 outline-none" placeholder="Na contagem de iniciativa 20..." />
                    </div>
                </div>
            )}

            {/* BOTÕES DE NAVEGAÇÃO */}
            <div className="mt-10 flex justify-between pb-8 pt-6 border-t border-gray-700">
                {currentStep > 1 ? (
                    <button type="button" onClick={prevStep} className="px-6 py-3 rounded-lg bg-gray-600 hover:bg-gray-500 text-white font-semibold transition text-lg flex items-center gap-2">← Voltar</button>
                ) : <button type="button" onClick={() => navigate('/home-page')} className="px-6 py-3 rounded-lg bg-red-800 hover:bg-red-700 text-white font-semibold">Cancelar</button>}

                {currentStep < steps.length ? (
                    <button type="button" onClick={nextStep} className="px-8 py-3 rounded-lg bg-red-600 hover:bg-red-500 text-white font-semibold transition text-lg shadow-lg shadow-red-900/50 flex items-center gap-2">Próximo →</button>
                ) : (
                    <button type="button" onClick={handleSubmit} className="px-8 py-3 rounded-lg bg-green-600 hover:bg-green-500 text-white font-semibold transition text-lg shadow-lg shadow-green-900/50 flex items-center gap-2">Criar Criatura ✓</button>
                )}
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateCreature;