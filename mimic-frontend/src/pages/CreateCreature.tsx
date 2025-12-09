import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Header from '../components/layout/Header';
import Stepper from '../components/ui/Stepper';
import InputField from '../components/form/InputField';
import AttributeCard from '../components/form/AttributeCard';
import DynamicSection from '../components/form/DynamicSection'; 
import SelectField from '../components/form/SelectField';

interface RecursoCreaturaDTO { id: number; nome: string; descricao: string; }
interface DynamicItem { id: number; value: string | number; description: string; }

interface CreatureData {
  nome: string; tamanho: string; tipo: string; tag: string; alinhamento: string;
  ca: string; pv: string; deslBase: string; deslVoo: string; deslNatacao: string;
  str: number; dex: number; con: number; int: number; wis: number; cha: number;
  saves: string; skills: string; resistDano: string; imunidDano: string; imunidCond: string; sentidos: string; idiomas: string; nd: string;
  
  specialAbilities: DynamicItem[];
  actions: DynamicItem[];
  
  legendaryActions: string; lairActions: string;

  foto: File | null;
  fotoPreview: string | null;
}

const reconstruirLista = (ids: number[], listaCompleta: RecursoCreaturaDTO[]): DynamicItem[] => {
    if (!ids || ids.length === 0) return [];
    return ids.map((idDoBanco, index) => {
        const itemOriginal = listaCompleta.find(i => i.id === idDoBanco);
        return {
            id: Date.now() + index,
            value: idDoBanco,
            description: itemOriginal ? itemOriginal.descricao : ''
        };
    });
};

const CreateCreature = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEditMode = !!id;

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
    legendaryActions: '', lairActions: '',
    foto: null, fotoPreview: null
  });

  
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
              setListas({ habilidades: await resHabilidades.json(), acoes: await resAcoes.json() });
          } catch (error) { console.error(error); } 
          finally { setLoading(false); }
      };
      fetchListas();
  }, []);

  
  useEffect(() => {
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
                  
                  
                  const deslTotal = data.deslocamentoTotal || "";
                  const deslBase = deslTotal.split(',')[0] || ""; 
                  const deslVoo = deslTotal.includes('Voo') ? deslTotal.match(/Voo\s([0-9]+m)/)?.[1] || "" : "";
                  const deslNatacao = deslTotal.includes('Natação') ? deslTotal.match(/Natação\s([0-9]+m)/)?.[1] || "" : "";

                  setFormData(prev => ({
                      ...prev,
                      nome: data.nome,
                      tamanho: data.tamanho || 'MEDIO',
                      tipo: data.tipo,
                      tag: data.tag,
                      alinhamento: data.alinhamento || 'SEM_ALINHAMENTO',
                      ca: data.ca, pv: data.pv,
                      
                      deslBase: deslBase.replace("Voo", "").replace("Natação", "").trim(), 
                      deslVoo: deslVoo, 
                      deslNatacao: deslNatacao,

                      str: data.str, dex: data.dex, con: data.con,
                      int: data.intelligence, wis: data.wis, cha: data.cha,
                      saves: data.saves, skills: data.skills,
                      resistDano: data.resistDano, imunidDano: data.imunidDano,
                      imunidCond: data.imunidCond, sentidos: data.sentidos,
                      idiomas: data.idiomas, nd: data.nd,
                      legendaryActions: data.legendaryActions || '',
                      lairActions: data.lairActions || '',
                      specialAbilities: reconstruirLista(data.habilidadesIds, listas.habilidades),
                      actions: reconstruirLista(data.acoesIds, listas.acoes),
                      fotoPreview: data.imagem ? `data:image/jpeg;base64,${data.imagem}` : null,
                      foto: null
                  }));
              }
          } catch (err) { console.error(err); }
      };
      fetchCriatura();
  }, [id, isEditMode, loading]); 

  

  const updateData = (field: string, value: any) => setFormData(prev => ({ ...prev, [field]: value }));
  
  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setFormData(prev => ({ ...prev, foto: file, fotoPreview: URL.createObjectURL(file) }));
    }
  };

  const addDynamicItem = (field: keyof CreatureData) => {
      setFormData(prev => {
          const currentList = Array.isArray(prev[field]) ? (prev[field] as DynamicItem[]) : [];
          return { ...prev, [field]: [...currentList, { id: Date.now(), value: '', description: '' }] };
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
      setFormData(prev => {
          const currentList = Array.isArray(prev[field]) ? (prev[field] as DynamicItem[]) : [];
          return { ...prev, [field]: currentList.map(item => item.id === id ? { ...item, value: newValue, description: selectedOption?.desc || '' } : item) };
      });
  };

  const opcoesHabilidades = listas.habilidades.map(h => ({ value: h.id, label: h.nome, desc: h.descricao }));
  const opcoesAcoes = listas.acoes.map(a => ({ value: a.id, label: a.nome, desc: a.descricao }));

  
  const validateStep = (step: number) => {
      if (step === 1) { 
          if (!formData.nome.trim()) { alert("Nome é obrigatório."); return false; }
          if (!formData.tamanho) { alert("Tamanho é obrigatório."); return false; }
          if (!formData.tipo.trim()) { alert("Tipo é obrigatório."); return false; }
          if (!formData.alinhamento) { alert("Alinhamento é obrigatório."); return false; }
      }
      if (step === 2) { 
          if (!formData.ca.trim()) { alert("Classe de Armadura (CA) é obrigatória."); return false; }
          if (!formData.pv.trim()) { alert("Pontos de Vida (PV) é obrigatório."); return false; }
          if (!formData.deslBase.trim()) { alert("Deslocamento Base é obrigatório."); return false; }
      }
      if (step === 4) { 
          if (!formData.nd.trim()) { alert("Nível de Desafio (ND) é obrigatório."); return false; }
      }
      return true;
  };

  const handleNextStep = () => {
      if (validateStep(currentStep)) {
          setCurrentStep(prev => Math.min(prev + 1, steps.length));
      }
  };

  
  const handleSubmit = async () => {
      if (!validateStep(1) || !validateStep(2) || !validateStep(4)) return;

      const token = localStorage.getItem('token');
      const usuarioId = localStorage.getItem('usuarioId');
      const tokenLimpo = token?.replace("Bearer ", "").trim();
      
      
      const partesDeslocamento = [];
      if (formData.deslBase) partesDeslocamento.push(formData.deslBase);
      if (formData.deslVoo) partesDeslocamento.push(`Voo ${formData.deslVoo}`);
      if (formData.deslNatacao) partesDeslocamento.push(`Natação ${formData.deslNatacao}`);
      const deslocamentoFinal = partesDeslocamento.join(", ");

      const payload = {
          ...formData,
          intelligence: formData.int, 
          deslocamentoTotal: deslocamentoFinal, 
          habilidadesIds: formData.specialAbilities.map(i => Number(i.value)).filter(v => v > 0),
          acoesIds: formData.actions.map(i => Number(i.value)).filter(v => v > 0),
      };

      try {
          const urlBase = `http://localhost:8080/api/criaturas`;
          const url = isEditMode ? `${urlBase}/${id}?usuarioId=${usuarioId}` : `${urlBase}?usuarioId=${usuarioId}`;
          const method = isEditMode ? 'PUT' : 'POST';

          const res = await fetch(url, {
              method: method,
              headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${tokenLimpo}` },
              body: JSON.stringify(payload)
          });

          if (!res.ok) throw new Error(await res.text());

          const savedCreature = await res.json();
          const creatureId = savedCreature.id;

          if (formData.foto && creatureId) {
              const imgData = new FormData();
              imgData.append('file', formData.foto);
              await fetch(`${urlBase}/${creatureId}/imagem?usuarioId=${usuarioId}`, {
                  method: 'POST',
                  headers: { 'Authorization': `Bearer ${tokenLimpo}` }, 
                  body: imgData
              });
          }

          alert(isEditMode ? "Criatura atualizada!" : "Criatura criada!");
          navigate('/gerenciar-criaturas');
      } catch (err: any) {
          alert("Erro: " + err.message);
      }
  };

  const handleStepClick = (id: number) => {
      
      if (id > currentStep && !validateStep(currentStep)) return;
      setCurrentStep(id);
  };
  
  const prevStep = () => setCurrentStep(prev => Math.max(prev - 1, 1));

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />
      <main className="container mx-auto p-8">
        <div className="max-w-6xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl min-h-[700px] flex flex-col">
          
          <div className="flex justify-between items-center mb-6">
             <h1 className="text-2xl font-bold text-gray-400">{isEditMode ? `Editando: ${formData.nome}` : "Nova Criatura"}</h1>
          </div>

          <Stepper steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />

          <form onSubmit={(e) => e.preventDefault()} className="flex-1 flex flex-col justify-between mt-8">
            
            {currentStep === 1 && (
                <div className="animate-fade-in space-y-6">
                    <div className="flex justify-between items-center border-b border-gray-700 pb-4 mb-6">
                        <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 font-medieval">Identidade da Criatura</h2>
                        {loading && <span className="text-yellow-500 animate-pulse text-sm">Carregando...</span>}
                    </div>
                    
                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div className="md:col-span-2">
                                <label className="block text-gray-400 mb-1 text-sm font-bold uppercase">Nome <span className="text-red-500">*</span></label>
                                <input type="text" value={formData.nome} onChange={e => updateData('nome', e.target.value)} placeholder="Ex: Dragão Vermelho Jovem" className="w-full p-3 rounded bg-[#444] border border-gray-600 text-white outline-none focus:border-red-500" />
                            </div>
                            
                            <SelectField 
                                required
                                label="Tamanho" 
                                value={formData.tamanho} 
                                onChange={(e: any) => updateData('tamanho', e.target.value)} 
                                options={[
                                    { id: "MINUSCULO", nome: "Minúsculo" }, { id: "PEQUENO", nome: "Pequeno" }, 
                                    { id: "MEDIO", nome: "Médio" }, { id: "GRANDE", nome: "Grande" }, 
                                    { id: "ENORME", nome: "Enorme" }, { id: "COLOSSAL", nome: "Colossal" }
                                ]} 
                            />

                            <div className="md:col-span-1">
                                <label className="block text-gray-400 mb-1 text-sm font-bold uppercase">Tipo <span className="text-red-500">*</span></label>
                                <input type="text" value={formData.tipo} onChange={e => updateData('tipo', e.target.value)} placeholder="Ex: Dragão" className="w-full p-3 rounded bg-[#444] border border-gray-600 text-white outline-none focus:border-red-500" />
                            </div>

                            <InputField label="Tag (Opcional)" value={formData.tag} onChange={(e: any) => updateData('tag', e.target.value)} placeholder="Ex: Metamorfo" />
                            
                            <SelectField 
                                required
                                label="Alinhamento" 
                                value={formData.alinhamento} 
                                onChange={(e: any) => updateData('alinhamento', e.target.value)} 
                                options={[
                                    { id: "LEAL_BOM", nome: "Leal e Bom" }, { id: "NEUTRO_BOM", nome: "Neutro e Bom" }, 
                                    { id: "CAOTICO_BOM", nome: "Caótico e Bom" }, { id: "LEAL_NEUTRO", nome: "Leal e Neutro" }, 
                                    { id: "VERDADEIRO_NEUTRO", nome: "Neutro" }, { id: "CAOTICO_NEUTRO", nome: "Caótico e Neutro" },
                                    { id: "LEAL_MAU", nome: "Leal e Mau" }, { id: "NEUTRO_MAU", nome: "Neutro e Mau" }, 
                                    { id: "CAOTICO_MAU", nome: "Caótico e Mau" }, { id: "SEM_ALINHAMENTO", nome: "Sem Alinhamento"}
                                ]} 
                            />
                        </div>

                        <div className="lg:col-span-1 flex flex-col">
                            <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">Imagem</label>
                            <div className="flex-1 bg-[#3a3a3a] rounded-lg border-2 border-dashed border-gray-600 flex flex-col items-center justify-center relative overflow-hidden group hover:border-red-500 transition-colors min-h-[300px]">
                                {formData.fotoPreview ? (
                                    <img src={formData.fotoPreview} alt="Preview" className="absolute inset-0 w-full h-full object-cover" />
                                ) : (
                                    <div className="text-center p-4">
                                        <svg className="mx-auto h-12 w-12 text-gray-500" stroke="currentColor" fill="none" viewBox="0 0 48 48"><path d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" /></svg>
                                        <p className="mt-1 text-sm text-gray-400">Clique para adicionar foto</p>
                                    </div>
                                )}
                                <input type="file" accept="image/*" onChange={handleImageUpload} className="absolute inset-0 w-full h-full opacity-0 cursor-pointer" />
                            </div>
                        </div>
                    </div>
                </div>
            )}

            {currentStep === 2 && (
                <div className="animate-fade-in space-y-6">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 mb-6 font-medieval">Estatísticas de Combate</h2>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label className="block text-gray-400 mb-1 text-sm font-bold uppercase">Classe de Armadura (CA) <span className="text-red-500">*</span></label>
                            <input type="text" value={formData.ca} onChange={e => updateData('ca', e.target.value)} placeholder="Ex: 17" className="w-full p-3 rounded bg-[#444] border border-gray-600 text-white outline-none focus:border-red-500" />
                        </div>
                        <div>
                            <label className="block text-gray-400 mb-1 text-sm font-bold uppercase">Pontos de Vida (PV) <span className="text-red-500">*</span></label>
                            <input type="text" value={formData.pv} onChange={e => updateData('pv', e.target.value)} placeholder="Ex: 136" className="w-full p-3 rounded bg-[#444] border border-gray-600 text-white outline-none focus:border-red-500" />
                        </div>
                        
                        <div className="md:col-span-2 bg-[#333] p-4 rounded-lg border border-gray-600">
                            <label className="block text-white font-bold mb-4 border-b border-gray-600 pb-2">Deslocamento <span className="text-red-500">*</span></label>
                            <div className="grid grid-cols-3 gap-4">
                                <div>
                                    <label className="text-xs text-gray-400 block mb-1">Base <span className="text-red-500">*</span></label>
                                    <input type="text" value={formData.deslBase} onChange={e => updateData('deslBase', e.target.value)} placeholder="9m" className="w-full p-2 rounded bg-[#444] border border-gray-600 text-white" />
                                </div>
                                <div>
                                    <label className="text-xs text-gray-400 block mb-1">Voo</label>
                                    <input type="text" value={formData.deslVoo} onChange={e => updateData('deslVoo', e.target.value)} placeholder="6m" className="w-full p-2 rounded bg-[#444] border border-gray-600 text-white" />
                                </div>
                                <div>
                                    <label className="text-xs text-gray-400 block mb-1">Natação</label>
                                    <input type="text" value={formData.deslNatacao} onChange={e => updateData('deslNatacao', e.target.value)} placeholder="3m" className="w-full p-2 rounded bg-[#444] border border-gray-600 text-white" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}

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

            {currentStep === 4 && (
                <div className="animate-fade-in space-y-6">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 mb-6 font-medieval">Proficiências e Sentidos</h2>
                    <div className="space-y-4">
                        <div>
                            <label className="block text-gray-400 mb-1 text-sm font-bold uppercase">Nível de Desafio (ND) <span className="text-red-500">*</span></label>
                            <input type="text" value={formData.nd} onChange={e => updateData('nd', e.target.value)} placeholder="Ex: 5" className="w-full p-3 rounded bg-[#444] border border-gray-600 text-white outline-none focus:border-red-500" />
                        </div>
                        <InputField label="Testes de Resistência" value={formData.saves} onChange={(e: any) => updateData('saves', e.target.value)} placeholder="Ex: Des +4, Sab +2" />
                        <InputField label="Perícias" value={formData.skills} onChange={(e: any) => updateData('skills', e.target.value)} placeholder="Ex: Furtividade +6" />
                        <InputField label="Imunidade a Dano" value={formData.imunidDano} onChange={(e: any) => updateData('imunidDano', e.target.value)} placeholder="Ex: Fogo" />
                        <InputField label="Imunidade a Condição" value={formData.imunidCond} onChange={(e: any) => updateData('imunidCond', e.target.value)} placeholder="Ex: Enfeitiçado" />
                        <InputField label="Sentidos" value={formData.sentidos} onChange={(e: any) => updateData('sentidos', e.target.value)} placeholder="Ex: Visão no Escuro 18m" />
                        <InputField label="Idiomas" value={formData.idiomas} onChange={(e: any) => updateData('idiomas', e.target.value)} placeholder="Ex: Comum, Dracônico" />
                        
                    </div>
                </div>
            )}

            {currentStep === 5 && <DynamicSection title="Habilidades Especiais" itemName="Habilidade" items={formData.specialAbilities} options={opcoesHabilidades} onAdd={() => addDynamicItem('specialAbilities')} onRemove={(id) => removeDynamicItem('specialAbilities', id)} onUpdate={(id, val) => updateDynamicItem('specialAbilities', id, val, opcoesHabilidades)} />}
            {currentStep === 6 && <DynamicSection title="Ações da Criatura" itemName="Ação" items={formData.actions} options={opcoesAcoes} onAdd={() => addDynamicItem('actions')} onRemove={(id) => removeDynamicItem('actions', id)} onUpdate={(id, val) => updateDynamicItem('actions', id, val, opcoesAcoes)} />}

            {currentStep === 7 && (
                <div className="animate-fade-in space-y-6">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 mb-6 font-medieval">Ações Lendárias e de Covil</h2>
                    <div><label className="block text-gray-400 mb-2 text-sm font-bold uppercase">Ações Lendárias</label><textarea rows={5} value={formData.legendaryActions} onChange={(e) => updateData('legendaryActions', e.target.value)} className="w-full p-3 rounded bg-[#444] border border-gray-600 text-white outline-none focus:border-red-500" /></div>
                    <div><label className="block text-gray-400 mb-2 text-sm font-bold uppercase">Ações de Covil</label><textarea rows={5} value={formData.lairActions} onChange={(e) => updateData('lairActions', e.target.value)} className="w-full p-3 rounded bg-[#444] border border-gray-600 text-white outline-none focus:border-red-500" /></div>
                </div>
            )}

            <div className="mt-10 flex justify-between pb-8 pt-6 border-t border-gray-700">
                {currentStep > 1 ? <button type="button" onClick={prevStep} className="px-6 py-3 rounded-lg bg-gray-600 hover:bg-gray-500 text-white font-semibold">← Voltar</button> : <button type="button" onClick={() => navigate('/gerenciar-criaturas')} className="px-6 py-3 rounded-lg bg-red-800 hover:bg-red-700 text-white font-semibold">Cancelar</button>}
                {currentStep < steps.length ? <button type="button" onClick={handleNextStep} className="px-8 py-3 rounded-lg bg-red-600 hover:bg-red-500 text-white font-semibold">Próximo →</button> : <button type="button" onClick={handleSubmit} className="px-8 py-3 rounded-lg bg-green-600 hover:bg-green-500 text-white font-semibold">{isEditMode ? 'Atualizar Criatura' : 'Criar Criatura'}</button>}
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateCreature;