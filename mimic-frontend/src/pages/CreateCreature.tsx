import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Header from '../components/layout/Header';
import Stepper from '../components/ui/Stepper';
import InputField from '../components/form/InputField';
import AttributeCard from '../components/form/AttributeCard';
import DynamicSection from '../components/form/DynamicSection'; 
import SelectField from '../components/form/SelectField';


// --- INTERFACES ---

interface RecursoCreaturaDTO {
    id: number;
    nome: string;
    descricao: string;
}

interface DynamicItem {
  id: number;
  value: string | number;
  description: string;
}

interface CreatureData {
  nome: string; tamanho: string; tipo: string; tag: string; alinhamento: string;
  ca: string; pv: string; deslBase: string; deslVoo: string; deslNatacao: string;
  str: number; dex: number; con: number; int: number; wis: number; cha: number;
  saves: string; skills: string; resistDano: string; imunidDano: string; imunidCond: string; sentidos: string; idiomas: string; nd: string;
  
  specialAbilities: DynamicItem[];
  actions: DynamicItem[];
  
  legendaryActions: string; lairActions: string;
}

// --- FUNÇÃO DE RECONSTRUÇÃO (CORE DA EDIÇÃO) ---
// Pega [1, 2] e transforma em [{id:..., value:1, desc:...}, ...]
const reconstruirLista = (ids: number[], listaCompleta: RecursoCreaturaDTO[]): DynamicItem[] => {
    if (!ids || ids.length === 0) return [];
    
    return ids.map((idDoBanco, index) => {
        // Encontra o objeto completo na lista de opções
        const itemOriginal = listaCompleta.find(i => i.id === idDoBanco);
        return {
            id: Date.now() + index, // ID único para o React (key)
            value: idDoBanco,       // ID real para o select
            description: itemOriginal ? itemOriginal.descricao : ''
        };
    });
};

const CreateCreature = () => {
  const navigate = useNavigate();
  const { id } = useParams(); // ID da URL
  const isEditMode = !!id;    // True se for edição

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

  // Listas de Opções (Vêm do Banco)
  const [listas, setListas] = useState({
      habilidades: [] as RecursoCreaturaDTO[],
      acoes: [] as RecursoCreaturaDTO[]
  });

  const [formData, setFormData] = useState<CreatureData>({
    nome: '', tamanho: 'MEDIO', tipo: '', tag: '', alinhamento: 'SEM_ALINHAMENTO',
    ca: '', pv: '', deslBase: '', deslVoo: '', deslNatacao: '',
    str: 10, dex: 10, con: 10, int: 10, wis: 10, cha: 10,
    saves: '', skills: '', resistDano: '', imunidDano: '', imunidCond: '', sentidos: '', idiomas: '', nd: '',
    specialAbilities: [], 
    actions: [],
    legendaryActions: '', lairActions: ''
  });

  // --- 1. BUSCAR LISTAS DE REFERÊNCIA (Ao abrir) ---
  useEffect(() => {
      const fetchListas = async () => {
          const token = localStorage.getItem('token');
          if (!token) return;
          const tokenLimpo = token.replace("Bearer ", "").trim();
          const headers = { 'Authorization': `Bearer ${tokenLimpo}` };

          try {
              const [resHabilidades, resAcoes] = await Promise.all([
                  fetch(`http://localhost:8080/api/habilidades_criatura`, { headers }),
                  fetch(`http://localhost:8080/api/acoes_criatura`, { headers })
              ]);

              const habilidadesData = await resHabilidades.json();
              const acoesData = await resAcoes.json();

              setListas({ habilidades: habilidadesData, acoes: acoesData });
          } catch (error) {
              console.error("Erro ao carregar recursos:", error);
          } finally {
              setLoading(false);
          }
      };
      fetchListas();
  }, []);

  // --- 2. CARREGAR DADOS DA CRIATURA (SE FOR EDIÇÃO) ---
  useEffect(() => {
      // Só roda se for edição E se as listas já tiverem carregado (senão reconstruirLista falha)
      if (!isEditMode || loading) return;

      const fetchCriatura = async () => {
          const token = localStorage.getItem('token');
          const usuarioId = localStorage.getItem('usuarioId');
          const tokenLimpo = token?.replace("Bearer ", "").trim();
          
          try {
              const res = await fetch(`http://localhost:8080/api/criaturas/${id}?usuarioId=${usuarioId}`, {
                  headers: { 'Authorization': `Bearer ${tokenLimpo}` }
              });

              if (res.ok) {
                  const data = await res.json();
                  
                  // Tratamento de campos opcionais/nulos
                  const deslBase = data.deslocamentoTotal || ""; 

                  setFormData(prev => ({
                      ...prev,
                      nome: data.nome,
                      tamanho: data.tamanho || 'MEDIO',
                      tipo: data.tipo,
                      tag: data.tag,
                      alinhamento: data.alinhamento || 'SEM_ALINHAMENTO',
                      
                      ca: data.ca, pv: data.pv,
                      deslBase: deslBase, deslVoo: '', deslNatacao: '', // Ajuste se tiver lógica de split

                      str: data.str, dex: data.dex, con: data.con,
                      int: data.intelligence, wis: data.wis, cha: data.cha,

                      saves: data.saves, skills: data.skills,
                      resistDano: data.resistDano, imunidDano: data.imunidDano,
                      imunidCond: data.imunidCond, sentidos: data.sentidos,
                      idiomas: data.idiomas, nd: data.nd,

                      legendaryActions: data.legendaryActions || '',
                      lairActions: data.lairActions || '',

                      // AQUI ACONTECE A MÁGICA: Converte IDs [1, 2] -> Objetos visuais
                      specialAbilities: reconstruirLista(data.habilidadesIds, listas.habilidades),
                      actions: reconstruirLista(data.acoesIds, listas.acoes)
                  }));
              }
          } catch (err) {
              console.error("Erro ao carregar criatura para edição:", err);
          }
      };

      fetchCriatura();
  }, [id, isEditMode, loading]); // Roda quando ID muda ou quando loading termina

  // --- HANDLERS (Com Correção de Estado 'prev') ---

  const updateData = (field: string, value: any) => setFormData(prev => ({ ...prev, [field]: value }));
  
  const addDynamicItem = (field: keyof CreatureData) => {
      setFormData(prev => {
          const currentList = Array.isArray(prev[field]) ? (prev[field] as DynamicItem[]) : [];
          const newItem: DynamicItem = { id: Date.now(), value: '', description: '' };
          return { ...prev, [field]: [...currentList, newItem] };
      });
  };

  const removeDynamicItem = (field: keyof CreatureData, id: number) => {
      setFormData(prev => {
          const currentList = Array.isArray(prev[field]) ? (prev[field] as DynamicItem[]) : [];
          return { ...prev, [field]: currentList.filter(item => item.id !== id) };
      });
  };

  const updateDynamicItem = (field: keyof CreatureData, id: number, newValueStr: string, sourceOptions: any[]) => {
      const newValue = Number(newValueStr);
      const selectedOption = sourceOptions.find(opt => opt.value === newValue);
      const newDesc = selectedOption ? selectedOption.desc : '';

      setFormData(prev => {
          const currentList = Array.isArray(prev[field]) ? (prev[field] as DynamicItem[]) : [];
          const updatedList = currentList.map(item => 
              item.id === id ? { ...item, value: newValue, description: newDesc } : item
          );
          return { ...prev, [field]: updatedList };
      });
  };

  // --- PREPARAÇÃO DE OPÇÕES ---
  const opcoesHabilidades = listas.habilidades.map(h => ({ value: h.id, label: h.nome, desc: h.descricao }));
  const opcoesAcoes = listas.acoes.map(a => ({ value: a.id, label: a.nome, desc: a.descricao }));

  // --- SUBMIT ---
  const handleSubmit = async () => {
      const token = localStorage.getItem('token');
      const usuarioId = localStorage.getItem('usuarioId');
      const tokenLimpo = token?.replace("Bearer ", "").trim();
      
      const payload = {
          ...formData,
          // Mapeamento para DTO Java
          intelligence: formData.int, 
          // Extrai apenas os IDs válidos das listas visuais
          habilidadesIds: formData.specialAbilities.map(i => Number(i.value)).filter(v => v > 0),
          acoesIds: formData.actions.map(i => Number(i.value)).filter(v => v > 0),
      };

      try {
          const urlBase = `http://localhost:8080/api/criaturas`;
          // Se for edição, PUT na URL com ID. Se novo, POST.
          const url = isEditMode ? `${urlBase}/${id}?usuarioId=${usuarioId}` : `${urlBase}?usuarioId=${usuarioId}`;
          const method = isEditMode ? 'PUT' : 'POST';

          const res = await fetch(url, {
              method: method,
              headers: { 
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${tokenLimpo}` 
              },
              body: JSON.stringify(payload)
          });

          if (res.ok) {
              alert(isEditMode ? "Criatura atualizada!" : "Criatura criada!");
              navigate('/gerenciar-criaturas');
          } else {
              const txt = await res.text();
              alert("Erro ao salvar: " + txt);
          }
      } catch (err) {
          console.error(err);
          alert("Erro de conexão.");
      }
  };

  const handleStepClick = (id: number) => setCurrentStep(id);
  const nextStep = () => setCurrentStep(prev => Math.min(prev + 1, steps.length));
  const prevStep = () => setCurrentStep(prev => Math.max(prev - 1, 1));

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />
      <main className="container mx-auto p-8">
        <div className="max-w-5xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl min-h-[700px] flex flex-col">
          
          <div className="flex justify-between items-center mb-6">
             <h1 className="text-2xl font-bold text-gray-400">
                {isEditMode ? `Editando: ${formData.nome}` : "Nova Criatura"}
             </h1>
          </div>

          <Stepper steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />

          <form onSubmit={(e) => e.preventDefault()} className="flex-1 flex flex-col justify-between mt-8">
            
            {/* ETAPA 1: DADOS BÁSICOS (Com Selects Corrigidos) */}
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
                        
                        {/* SELECT TAMANHO */}
                        <SelectField 
                            label="Tamanho" 
                            value={formData.tamanho} 
                            onChange={(e: any) => updateData('tamanho', e.target.value)} 
                            options={[
                                { id: "MINUSCULO", nome: "Minúsculo" }, 
                                { id: "PEQUENO", nome: "Pequeno" }, 
                                { id: "MEDIO", nome: "Médio" }, 
                                { id: "GRANDE", nome: "Grande" }, 
                                { id: "ENORME", nome: "Enorme" }, 
                                { id: "COLOSSAL", nome: "Colossal" }
                            ]} 
                        />

                        <InputField label="Tipo" value={formData.tipo} onChange={(e: any) => updateData('tipo', e.target.value)} placeholder="Ex: Dragão" />
                        <InputField label="Tag (Opcional)" value={formData.tag} onChange={(e: any) => updateData('tag', e.target.value)} placeholder="Ex: Metamorfo" />
                        
                        {/* SELECT ALINHAMENTO */}
                        <SelectField 
                            label="Alinhamento" 
                            value={formData.alinhamento} 
                            onChange={(e: any) => updateData('alinhamento', e.target.value)} 
                            options={[
                                { id: "LEAL_BOM", nome: "Leal e Bom" }, 
                                { id: "NEUTRO_BOM", nome: "Neutro e Bom" }, 
                                { id: "CAOTICO_BOM", nome: "Caótico e Bom" },
                                { id: "LEAL_NEUTRO", nome: "Leal e Neutro" }, 
                                { id: "VERDADEIRO_NEUTRO", nome: "Neutro" }, 
                                { id: "CAOTICO_NEUTRO", nome: "Caótico e Neutro" },
                                { id: "LEAL_MAU", nome: "Leal e Mau" }, 
                                { id: "NEUTRO_MAU", nome: "Neutro e Mau" }, 
                                { id: "CAOTICO_MAU", nome: "Caótico e Mau" },
                                { id: "SEM_ALINHAMENTO", nome: "Sem Alinhamento"}
                            ]} 
                        />
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
                        <InputField label="Nível de Desafio (ND)" value={formData.nd} onChange={(e: any) => updateData('nd', e.target.value)} placeholder="Ex: 5" />
                    </div>
                </div>
            )}

            {/* ETAPA 5: HABILIDADES */}
            {currentStep === 5 && (
                <DynamicSection 
                    title="Habilidades Especiais" 
                    itemName="Habilidade"
                    items={formData.specialAbilities} 
                    options={opcoesHabilidades} 
                    onAdd={() => addDynamicItem('specialAbilities')} 
                    onRemove={(id: number) => removeDynamicItem('specialAbilities', id)} 
                    onUpdate={(id, val) => updateDynamicItem('specialAbilities', id, val, opcoesHabilidades)} 
                />
            )}

            {/* ETAPA 6: AÇÕES */}
            {currentStep === 6 && (
                <DynamicSection 
                    title="Ações da Criatura" 
                    itemName="Ação"
                    items={formData.actions} 
                    options={opcoesAcoes} 
                    onAdd={() => addDynamicItem('actions')} 
                    onRemove={(id: number) => removeDynamicItem('actions', id)} 
                    onUpdate={(id, val) => updateDynamicItem('actions', id, val, opcoesAcoes)} 
                />
            )}

            {/* ETAPA 7: LENDÁRIAS */}
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

            {/* BOTÕES */}
            <div className="mt-10 flex justify-between pb-8 pt-6 border-t border-gray-700">
                {currentStep > 1 ? (
                    <button type="button" onClick={prevStep} className="px-6 py-3 rounded-lg bg-gray-600 hover:bg-gray-500 text-white font-semibold transition text-lg flex items-center gap-2">← Voltar</button>
                ) : <button type="button" onClick={() => navigate('/gerenciar-criaturas')} className="px-6 py-3 rounded-lg bg-red-800 hover:bg-red-700 text-white font-semibold">Cancelar</button>}

                {currentStep < steps.length ? (
                    <button type="button" onClick={nextStep} className="px-8 py-3 rounded-lg bg-red-600 hover:bg-red-500 text-white font-semibold transition text-lg shadow-lg shadow-red-900/50 flex items-center gap-2">Próximo →</button>
                ) : (
                    <button type="button" onClick={handleSubmit} className="px-8 py-3 rounded-lg bg-green-600 hover:bg-green-500 text-white font-semibold transition text-lg shadow-lg shadow-green-900/50 flex items-center gap-2">
                        {isEditMode ? 'Atualizar Criatura ✓' : 'Criar Criatura ✓'}
                    </button>
                )}
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateCreature;