import React, { useState } from 'react';
import Header from '../components/layout/Header';
import Stepper from '../components/ui/Stepper';
import DynamicSection from '../components/form/DynamicSection';
import { TALENTOS, TRUQUES, MAGIAS, PERICIAS } from '../data/mockData';
import AttributeCard from '../components/form/AttributeCard';

// --- TIPAGEM ---
interface DynamicItem {
  id: number;
  value: string;
  description: string;
}

interface FormData {
  // Dados Básicos
  nome: string; tamanho: string; alinhamento: string; raca: string; subraca: string;
  classe: string; subclasse: string; origem: string; foto: File | null; fotoPreview: string | null;
  
  // Atributos (Estes são os valores BASE, ex: 10, 15, 20)
  str: number; dex: number; con: number; int: number; wis: number; cha: number;
  
  // Equipamento
  equipamento: string;

  // Arrays Dinâmicos
  talentos: DynamicItem[];
  truques: DynamicItem[];
  magias: DynamicItem[];
  pericias: DynamicItem[];
}

// --- COMPONENTE AUXILIAR: SELECT ---
const SelectField = ({ label, value, onChange, options, disabled = false }: any) => (
  <div>
    <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">{label}</label>
    <select 
      value={value} 
      onChange={onChange}
      disabled={disabled}
      className={`w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 focus:ring-1 focus:ring-red-500 outline-none transition appearance-none ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`}
    >
      <option value="">Selecione...</option>
      {options.map((opt: string) => (
        <option key={opt} value={opt}>{opt}</option>
      ))}
    </select>
  </div>
);


// --- COMPONENTE PRINCIPAL ---
const CreateCharacter = () => {
  const steps = [
    { id: 1, label: "Dados Básicos" },
    { id: 2, label: "Talentos" },
    { id: 3, label: "Truques" },
    { id: 4, label: "Magias" },
    { id: 5, label: "Perícias" },
    { id: 6, label: "Mods" },
    { id: 7, label: "Equipamento" },
  ];

  const [currentStep, setCurrentStep] = useState(1);
  const [formData, setFormData] = useState<FormData>({
    nome: '', tamanho: '', alinhamento: '', raca: '', subraca: '',
    classe: '', subclasse: '', origem: '', foto: null, fotoPreview: null,
    str: 10, dex: 10, con: 10, int: 10, wis: 10, cha: 10,
    equipamento: '',
    talentos: [], truques: [], magias: [], pericias: []
  });

  // --- Handlers ---
  const updateData = (field: string, value: any) => setFormData(prev => ({ ...prev, [field]: value }));
  
  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setFormData(prev => ({ ...prev, foto: file, fotoPreview: URL.createObjectURL(file) }));
    }
  };

  const handleStepClick = (stepId: number) => setCurrentStep(stepId);
  const nextStep = () => setCurrentStep(prev => Math.min(prev + 1, steps.length));
  const prevStep = () => setCurrentStep(prev => Math.max(prev - 1, 1));

  // Funções Dinâmicas (Adicionar/Remover/Atualizar Listas)
  const addDynamicItem = (field: keyof FormData) => {
    const newItem: DynamicItem = { id: Date.now(), value: '', description: '' };
    setFormData(prev => ({ ...prev, [field]: [...(prev[field] as DynamicItem[]), newItem] }));
  };

  const removeDynamicItem = (field: keyof FormData, id: number) => {
    setFormData(prev => ({ ...prev, [field]: (prev[field] as DynamicItem[]).filter(item => item.id !== id) }));
  };

  const updateDynamicItem = (field: keyof FormData, id: number, newValue: string, sourceOptions: any[]) => {
    const selectedOption = sourceOptions.find(opt => opt.value === newValue);
    const newDesc = selectedOption ? selectedOption.desc : '';
    setFormData(prev => ({
      ...prev,
      [field]: (prev[field] as DynamicItem[]).map(item => 
        item.id === id ? { ...item, value: newValue, description: newDesc } : item
      )
    }));
  };

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />

      <main className="container mx-auto p-8">
        <div className="max-w-6xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl min-h-[700px] flex flex-col">
          
          <Stepper steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />

          <form onSubmit={(e) => e.preventDefault()} className="flex-1 flex flex-col justify-between mt-8">
            
            {/* ETAPA 1 */}
            {currentStep === 1 && (
              <div className="animate-fade-in space-y-8">
                <div className="flex justify-between items-center border-b border-gray-700 pb-4">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4">Dados Básicos</h2>
                    <span className="text-sm text-gray-500 uppercase font-bold">D&D 5.5 Edition</span>
                </div>
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                  <div className="lg:col-span-2 grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div className="md:col-span-2">
                      <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">Nome do Personagem</label>
                      <input type="text" value={formData.nome} onChange={e => updateData('nome', e.target.value)} placeholder="Ex: Aragorn" className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 outline-none" />
                    </div>
                    <SelectField label="Raça" value={formData.raca} onChange={(e: any) => updateData('raca', e.target.value)} options={["Humano", "Elfo", "Anão", "Halfling", "Draconato", "Tiefling", "Gnomo", "Orc"]} />
                    <SelectField label="Sub-Raça" value={formData.subraca} onChange={(e: any) => updateData('subraca', e.target.value)} options={["Alto Elfo", "Elfo da Floresta", "Anão da Colina", "Anão da Montanha"]} disabled={!formData.raca} />
                    <SelectField label="Classe" value={formData.classe} onChange={(e: any) => updateData('classe', e.target.value)} options={["Bárbaro", "Bardo", "Bruxo", "Clérigo", "Druida", "Feiticeiro", "Guerreiro", "Ladino", "Mago", "Monge", "Paladino", "Patrulheiro"]} />
                    <SelectField label="Sub-Classe" value={formData.subclasse} onChange={(e: any) => updateData('subclasse', e.target.value)} options={["Campeão", "Mestre de Batalha", "Evocação", "Vida"]} disabled={!formData.classe} />
                    <SelectField label="Origem" value={formData.origem} onChange={(e: any) => updateData('origem', e.target.value)} options={["Acólito", "Artesão", "Charlatão", "Criminoso", "Eremita", "Forasteiro", "Herói do Povo", "Nobre", "Sábio", "Soldado"]} />
                    <SelectField label="Alinhamento" value={formData.alinhamento} onChange={(e: any) => updateData('alinhamento', e.target.value)} options={["Leal e Bom", "Neutro e Bom", "Caótico e Bom", "Leal e Neutro", "Neutro", "Caótico e Neutro", "Leal e Mau", "Neutro e Mau", "Caótico e Mau"]} />
                    <SelectField label="Tamanho" value={formData.tamanho} onChange={(e: any) => updateData('tamanho', e.target.value)} options={["Miúdo", "Pequeno", "Médio", "Grande"]} />
                  </div>
                  <div className="lg:col-span-1 flex flex-col">
                    <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">Foto</label>
                    <div className="flex-1 bg-[#3a3a3a] rounded-lg border-2 border-dashed border-gray-600 flex flex-col items-center justify-center relative overflow-hidden group hover:border-red-500 transition-colors min-h-[300px]">
                        {formData.fotoPreview ? <img src={formData.fotoPreview} className="absolute inset-0 w-full h-full object-cover" /> : <p className="text-gray-400">Adicionar Foto</p>}
                        <input type="file" accept="image/*" onChange={handleImageUpload} className="absolute inset-0 w-full h-full opacity-0 cursor-pointer" />
                    </div>
                  </div>
                </div>
              </div>
            )}

            {/* ETAPAS 2 a 5 (Dinâmicas) */}
            {currentStep === 2 && <DynamicSection title="Talentos" itemName="Talento" items={formData.talentos} options={TALENTOS} onAdd={() => addDynamicItem('talentos')} onRemove={(id) => removeDynamicItem('talentos', id)} onUpdate={(id, val) => updateDynamicItem('talentos', id, val, TALENTOS)} />}
            {currentStep === 3 && <DynamicSection title="Truques" itemName="Truque" items={formData.truques} options={TRUQUES} onAdd={() => addDynamicItem('truques')} onRemove={(id) => removeDynamicItem('truques', id)} onUpdate={(id, val) => updateDynamicItem('truques', id, val, TRUQUES)} />}
            {currentStep === 4 && <DynamicSection title="Magias" itemName="Magia" items={formData.magias} options={MAGIAS} onAdd={() => addDynamicItem('magias')} onRemove={(id) => removeDynamicItem('magias', id)} onUpdate={(id, val) => updateDynamicItem('magias', id, val, MAGIAS)} />}
            {currentStep === 5 && <DynamicSection title="Perícias" itemName="Perícia" items={formData.pericias} options={PERICIAS} onAdd={() => addDynamicItem('pericias')} onRemove={(id) => removeDynamicItem('pericias', id)} onUpdate={(id, val) => updateDynamicItem('pericias', id, val, PERICIAS)} />}

            {/* ETAPA 6: MODIFICADORES (ATRIBUTOS) - NOVO DESIGN */}
            {currentStep === 6 && (
               <div className="animate-fade-in">
                  <div className="flex justify-between items-center border-b border-gray-700 pb-4 mb-8">
                      <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4">Habilidades (Atributos)</h2>
                      <span className="text-sm text-gray-500">Distribua seus pontos</span>
                  </div>
                  
                  {/* Grid de Cards baseados no HTML original */}
                  <div className="grid grid-cols-2 md:grid-cols-3 gap-8">
                    <AttributeCard label="Força" value={formData.str} onChange={(v: number) => updateData('str', v)} />
                    <AttributeCard label="Destreza" value={formData.dex} onChange={(v: number) => updateData('dex', v)} />
                    <AttributeCard label="Constituição" value={formData.con} onChange={(v: number) => updateData('con', v)} />
                    <AttributeCard label="Inteligência" value={formData.int} onChange={(v: number) => updateData('int', v)} />
                    <AttributeCard label="Sabedoria" value={formData.wis} onChange={(v: number) => updateData('wis', v)} />
                    <AttributeCard label="Carisma" value={formData.cha} onChange={(v: number) => updateData('cha', v)} />
                  </div>
               </div>
            )}

            {/* ETAPA 7: EQUIPAMENTO */}
            {currentStep === 7 && (
               <div className="animate-fade-in">
                  <h2 className="text-3xl font-semibold text-white mb-6 pt-4 border-l-4 border-red-500 pl-4">Equipamento Inicial</h2>
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {['A', 'B'].map(opt => (
                      <label key={opt} className={`cursor-pointer border-2 rounded-xl p-8 transition-all hover:bg-[#3a3a3a] ${formData.equipamento === opt ? 'border-red-500 bg-[#3a3a3a] ring-1 ring-red-500' : 'border-gray-600 bg-[#444]'}`}>
                         <div className="flex justify-between items-start mb-4">
                             <span className="text-3xl font-bold text-white">Opção {opt}</span>
                             <input type="radio" name="equip" value={opt} checked={formData.equipamento === opt} onChange={() => updateData('equipamento', opt)} className="w-6 h-6 text-red-600 focus:ring-red-500 bg-gray-700 border-gray-600" />
                         </div>
                         <div className="text-gray-300 space-y-2">
                           {opt === 'A' ? "Itens da Classe" : "Ouro Inicial (100 PO)"}
                         </div>
                      </label>
                    ))}
                  </div>
               </div>
            )}

            {/* Ações de Navegação */}
            <div className="mt-10 flex justify-between pb-8 pt-6 border-t border-gray-700">
              {currentStep > 1 ? (
                <button type="button" onClick={prevStep} className="px-6 py-3 rounded-lg bg-gray-600 hover:bg-gray-500 text-white font-semibold flex items-center gap-2">← Voltar</button>
              ) : <div />}

              {currentStep < steps.length ? (
                <button type="button" onClick={nextStep} className="px-8 py-3 rounded-lg bg-red-600 hover:bg-red-500 text-white font-semibold flex items-center gap-2 shadow-lg shadow-red-900/50">Próximo →</button>
              ) : (
                <button type="submit" className="px-8 py-3 rounded-lg bg-green-600 hover:bg-green-500 text-white font-semibold flex items-center gap-2 shadow-lg shadow-green-900/50">Finalizar Ficha ✓</button>
              )}
            </div>

          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateCharacter;