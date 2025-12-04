import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';
import Stepper from '../components/ui/Stepper';
import InputField from '../components/form/InputField';
import AttributeCard from '../components/form/AttributeCard';
import FreeTextSection, { type FreeTextItem } from '../components/form/FreeTextSection';

interface CreatureData {
  // ETAPA 1
  nome: string; tamanho: string; tipo: string; tag: string; alinhamento: string;
  // ETAPA 2
  ca: string; pv: string; deslBase: string; deslVoo: string; deslNatacao: string;
  // ETAPA 3
  str: number; dex: number; con: number; int: number; wis: number; cha: number;
  // ETAPA 4
  saves: string; skills: string; resistDano: string; imunidDano: string; imunidCond: string; sentidos: string; idiomas: string; nd: string;
  // LISTAS
  specialAbilities: FreeTextItem[];
  actions: FreeTextItem[];
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
  const [formData, setFormData] = useState<CreatureData>({
    nome: '', tamanho: '', tipo: '', tag: '', alinhamento: '',
    ca: '', pv: '', deslBase: '', deslVoo: '', deslNatacao: '',
    str: 10, dex: 10, con: 10, int: 10, wis: 10, cha: 10,
    saves: '', skills: '', resistDano: '', imunidDano: '', imunidCond: '', sentidos: '', idiomas: '', nd: '',
    specialAbilities: [], actions: [],
    legendaryActions: '', lairActions: ''
  });

  const updateData = (field: string, value: any) => setFormData(prev => ({ ...prev, [field]: value }));
  const handleStepClick = (id: number) => setCurrentStep(id);
  const nextStep = () => setCurrentStep(prev => Math.min(prev + 1, steps.length));
  const prevStep = () => setCurrentStep(prev => Math.max(prev - 1, 1));
  
  const addItem = (field: 'specialAbilities' | 'actions') => {
      const newItem: FreeTextItem = { id: Date.now(), name: '', description: '' };
      setFormData(prev => ({ ...prev, [field]: [...prev[field], newItem] }));
  };
  const removeItem = (field: 'specialAbilities' | 'actions', id: number) => {
      setFormData(prev => ({ ...prev, [field]: prev[field].filter(i => i.id !== id) }));
  };
  const updateItem = (field: 'specialAbilities' | 'actions', id: number, key: 'name' | 'description', val: string) => {
      setFormData(prev => ({
          ...prev,
          [field]: prev[field].map(i => i.id === id ? { ...i, [key]: val } : i)
      }));
  };
  
  const handleSubmit = () => {
      console.log("Criatura Salva:", formData);
      navigate('/home-page');
  };

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
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4 mb-6 font-medieval">Identidade da Criatura</h2>
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

            {/* ETAPA 5: HABILIDADES */}
            {currentStep === 5 && (
                <FreeTextSection 
                    title="Habilidades Especiais" 
                    items={formData.specialAbilities} 
                    onAdd={() => addItem('specialAbilities')} 
                    onRemove={(id: number) => removeItem('specialAbilities', id)} 
                    onUpdate={(id: number, key: 'name' | 'description', val: string) => updateItem('specialAbilities', id, key, val)} 
                />
            )}

            {/* ETAPA 6: AÇÕES */}
            {currentStep === 6 && (
                <FreeTextSection 
                    title="Ações da Criatura" 
                    items={formData.actions} 
                    onAdd={() => addItem('actions')} 
                    onRemove={(id: number) => removeItem('actions', id)} 
                    onUpdate={(id: number, key: 'name' | 'description', val: string) => updateItem('actions', id, key, val)} 
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