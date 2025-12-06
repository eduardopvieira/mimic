import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';
import Stepper from '../components/ui/Stepper';
import DynamicSection from '../components/form/DynamicSection';
import AttributeCard from '../components/form/AttributeCard';
import SelectField from '../components/form/SelectField';
import { TALENTOS, TRUQUES, MAGIAS, PERICIAS } from '../data/mockData';

// --- INTERFACES ---
interface ItemLista { id: number; nome: string; }
interface Subclasse extends ItemLista { classePaiId?: number; }
// Subraca segue a mesma estrutura de ItemLista por enquanto
interface Subraca extends ItemLista {} 

interface DynamicItem {
  id: number;
  value: string; 
  description: string;
}

// Interface necessária para tipar o updateData corretamente
interface IFormData {
  nome: string;
  tamanho: string;
  alinhamento: string;
  
  // Aceita string (do select HTML) ou number (id real)
  racaId: string | number;
  subracaId: string | number;
  classeId: string | number;
  subclasseId: string | number;
  origemId: string | number;
  
  foto: File | null;
  fotoPreview: string | null;
  
  str: number; dex: number; con: number; 
  int: number; wis: number; cha: number;
  
  equipamento: string;

  talentos: DynamicItem[];
  truques: DynamicItem[];
  magias: DynamicItem[];
  pericias: DynamicItem[];
}

const CreateCharacter = () => {
  const navigate = useNavigate();
  
  const steps = [
    { id: 1, label: "Dados Básicos" },
    { id: 2, label: "Talentos" },
    { id: 3, label: "Truques" },
    { id: 4, label: "Magias" },
    { id: 5, label: "Perícias" },
    { id: 6, label: "Atributos" },
    { id: 7, label: "Equipamento" },
  ];

  const [currentStep, setCurrentStep] = useState(1);
  const [loading, setLoading] = useState(true);

  // --- ESTADO DAS LISTAS (Vêm do Banco de Dados) ---
  const [listas, setListas] = useState({
     racas: [] as ItemLista[],
     subracas: [] as Subraca[], // Adicionado
     classes: [] as ItemLista[],
     subclasses: [] as Subclasse[],
     origens: [] as ItemLista[]
  });

  // --- ESTADO DO FORMULÁRIO ---
  const [formData, setFormData] = useState<IFormData>({
    nome: '', 
    tamanho: 'Médio', 
    alinhamento: '', 
    racaId: '', 
    subracaId: '',
    classeId: '', 
    subclasseId: '', 
    origemId: '', 
    
    foto: null, 
    fotoPreview: null,
    
    str: 10, dex: 10, con: 10, int: 10, wis: 10, cha: 10,
    
    equipamento: '',

    talentos: [],
    truques: [],
    magias: [],
    pericias: []
  });

  // --- 1. CARREGAR DADOS DO BACKEND (Ao iniciar) ---
  useEffect(() => {
    const fetchListas = async () => {
        const token = localStorage.getItem('token');
        const usuarioId = localStorage.getItem('usuarioId'); 

        if (!usuarioId) {
            console.error("Usuário não encontrado no localStorage");
            return;
        }

        const headers = { 'Authorization': `Bearer ${token}` };

        try {
            const [resRacas, resClasses, resOrigens, resMagias] = await Promise.all([
                fetch(`http://localhost:8080/api/racas`, { headers }), 
                fetch(`http://localhost:8080/api/classes`, { headers }),
                fetch(`http://localhost:8080/api/origens?usuarioId=${usuarioId}`, { headers }),
                fetch(`http://localhost:8080/api/magias?usuarioId=${usuarioId}`, { headers }) 
            ]);

            const racas = await resRacas.json();
            const classes = await resClasses.json();
            const origens = await resOrigens.json();
            // magias podem ser carregadas aqui ou na etapa específica

            setListas(prev => ({ ...prev, racas, classes, origens }));
        } catch (error) {
            console.error("Erro ao carregar listas:", error);
        } finally {
            setLoading(false);
        }
    };

    fetchListas();
  }, []);

  // --- 2. HANDLERS GERAIS ---

  // TIPAGEM CORRIGIDA AQUI:
  // "K" é uma chave válida de IFormData (ex: 'nome', 'racaId')
  // "value" assume automaticamente o tipo daquela chave
  // TIPAGEM CORRIGIDA
  const updateData = <K extends keyof IFormData>(field: K, value: IFormData[K]) => {
    
    if (field === 'racaId') {
        fetchSubracas(value as string); 
        setFormData(prev => ({ 
            ...prev, 
            racaId: value as string | number, 
            subracaId: '' 
        }));
        return; // Retornamos para não rodar o setFormData genérico lá embaixo
    }
    
    // CASO 2: Se for Classe (Lógica Específica)
    if (field === 'classeId') {
        fetchSubclasses(value as string);
        setFormData(prev => ({ 
            ...prev, 
            // O 'as' abaixo corrige o erro de tipagem
            classeId: value as string | number, 
            subclasseId: '' 
        })); 
        return;
    }

    // CASO 3: Genérico (Para todos os outros campos)
    setFormData(prev => ({ ...prev, [field]: value }));
  };
  
  const fetchSubclasses = async (classeId: string) => {
      if (!classeId) return;
      const token = localStorage.getItem('token');
      try {
          const res = await fetch(`http://localhost:8080/api/classes/${classeId}/subclasses`, {
              headers: { 'Authorization': `Bearer ${token}` }
          });
          if (res.ok) {
              const data = await res.json();
              setListas(prev => ({ ...prev, subclasses: data }));
          }
      } catch (err) {
          console.error(err);
      }
  };

  const fetchSubracas = async (racaId: string | number) => {
      if (!racaId) {
          setListas(prev => ({ ...prev, subracas: [] }));
          return;
      }

      const token = localStorage.getItem('token');
      
      const url = `http://localhost:8080/api/racas/${racaId}/subracas`;

      const res = await fetch(url, {
          headers: { 'Authorization': `Bearer ${token}` }
      });

      console.log(`📡 [DEBUG Subraca] Status da Resposta: ${res.status}`);

      if (res.ok) {
          const data = await res.json();
          setListas(prev => ({ ...prev, subracas: data }));
      }
  };

  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setFormData(prev => ({ ...prev, foto: file, fotoPreview: URL.createObjectURL(file) }));
    }
  };

  const addDynamicItem = (field: keyof IFormData) => {
    if (!Array.isArray(formData[field])) return;

    const list = formData[field] as DynamicItem[];
    const newItem: DynamicItem = { id: Date.now(), value: '', description: '' };
    setFormData(prev => ({ ...prev, [field]: [...list, newItem] }));
  };

  const removeDynamicItem = (field: keyof IFormData, id: number) => {
    if (!Array.isArray(formData[field])) return;
    const list = formData[field] as DynamicItem[];
    setFormData(prev => ({ ...prev, [field]: list.filter(item => item.id !== id) }));
  };

  const updateDynamicItem = (field: keyof IFormData, id: number, newValue: string, sourceOptions: any[]) => {
    if (!Array.isArray(formData[field])) return;
    
    const selectedOption = sourceOptions.find(opt => opt.value === newValue);
    const newDesc = selectedOption ? selectedOption.desc : '';
    
    const list = formData[field] as DynamicItem[];
    setFormData(prev => ({
      ...prev,
      [field]: list.map(item => item.id === id ? { ...item, value: newValue, description: newDesc } : item)
    }));
  };


  // Navegação do Stepper
  const handleStepClick = (stepId: number) => setCurrentStep(stepId);
  const nextStep = () => setCurrentStep(prev => Math.min(prev + 1, steps.length));
  const prevStep = () => setCurrentStep(prev => Math.max(prev - 1, 1));

  // --- RENDER ---
  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />

      <main className="container mx-auto p-8">
        <div className="max-w-6xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl min-h-[700px] flex flex-col">
          
          <Stepper steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />

          <form onSubmit={(e) => e.preventDefault()} className="flex-1 flex flex-col justify-between mt-8">
            
            {/* ETAPA 1: DADOS BÁSICOS */}
            {currentStep === 1 && (
              <div className="animate-fade-in space-y-8">
                <div className="flex justify-between items-center border-b border-gray-700 pb-4">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4">Dados Básicos</h2>
                    {loading && <span className="text-yellow-500 text-sm animate-pulse">Carregando listas...</span>}
                </div>
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                  <div className="lg:col-span-2 grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div className="md:col-span-2">
                      <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">Nome do Personagem</label>
                      <input type="text" value={formData.nome} onChange={e => updateData('nome', e.target.value)} placeholder="Ex: Aragorn" className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 outline-none" />
                    </div>
                    
                    {/* SELECTS CONECTADOS AO BANCO */}
                    <SelectField 
                        label="Raça" 
                        value={formData.racaId} 
                        onChange={(e: any) => updateData('racaId', e.target.value)} 
                        options={listas.racas} // Passando objetos {id, nome}
                    />
                    
                    <SelectField 
                        label="Sub-Raça" 
                        value={formData.subracaId} 
                        onChange={(e: any) => updateData('subracaId', e.target.value)} 
                        options={listas.subracas}
                        disabled={!formData.racaId} 
                    />
                    
                    <SelectField 
                        label="Classe" 
                        value={formData.classeId} 
                        onChange={(e: any) => updateData('classeId', e.target.value)} 
                        options={listas.classes} 
                    />
                    
                    <SelectField 
                        label="Sub-Classe" 
                        value={formData.subclasseId} 
                        onChange={(e: any) => updateData('subclasseId', e.target.value)} 
                        options={listas.subclasses}
                        disabled={!formData.classeId || listas.subclasses.length === 0} 
                    />
                    
                    <SelectField 
                        label="Origem" 
                        value={formData.origemId} 
                        onChange={(e: any) => updateData('origemId', e.target.value)} 
                        options={listas.origens} 
                    />
                    
                    {/* ENUMS (Valores fixos devem bater com o Java) */}
                    <SelectField 
                        label="Alinhamento" 
                        value={formData.alinhamento} 
                        onChange={(e: any) => updateData('alinhamento', e.target.value)} 
                        options={[
                            {id: "LEAL_BOM", nome: "Leal e Bom"},
                            {id: "NEUTRO_BOM", nome: "Neutro e Bom"},
                            {id: "CAOTICO_BOM", nome: "Caótico e Bom"},
                            {id: "LEAL_NEUTRO", nome: "Leal e Neutro"},
                            {id: "NEUTRO", nome: "Neutro"},
                            {id: "CAOTICO_NEUTRO", nome: "Caótico e Neutro"},
                            {id: "LEAL_MAU", nome: "Leal e Mau"},
                            {id: "NEUTRO_MAU", nome: "Neutro e Mau"},
                            {id: "CAOTICO_MAU", nome: "Caótico e Mau"},
                        ]} 
                    />
                    
                    <SelectField 
                        label="Tamanho" 
                        value={formData.tamanho} 
                        onChange={(e: any) => updateData('tamanho', e.target.value)} 
                        options={["Miúdo", "Pequeno", "Médio", "Grande"]} 
                    />
                  </div>
                  
                  {/* FOTO */}
                  <div className="lg:col-span-1 flex flex-col">
                    <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">Foto</label>
                    <div className="flex-1 bg-[#3a3a3a] rounded-lg border-2 border-dashed border-gray-600 flex flex-col items-center justify-center relative overflow-hidden group hover:border-red-500 transition-colors min-h-[300px]">
                        {formData.fotoPreview ? <img src={formData.fotoPreview} alt="Preview" className="absolute inset-0 w-full h-full object-cover" /> : <p className="text-gray-400">Adicionar Foto</p>}
                        <input type="file" accept="image/*" onChange={handleImageUpload} className="absolute inset-0 w-full h-full opacity-0 cursor-pointer" />
                    </div>
                  </div>
                </div>
              </div>
            )}

            {/* ETAPAS 2 A 5 - DINÂMICAS */}
            {currentStep === 2 && <DynamicSection title="Talentos" itemName="Talento" items={formData.talentos} options={TALENTOS} onAdd={() => addDynamicItem('talentos')} onRemove={(id) => removeDynamicItem('talentos', id)} onUpdate={(id, val) => updateDynamicItem('talentos', id, val, TALENTOS)} />}
            {currentStep === 3 && <DynamicSection title="Truques" itemName="Truque" items={formData.truques} options={TRUQUES} onAdd={() => addDynamicItem('truques')} onRemove={(id) => removeDynamicItem('truques', id)} onUpdate={(id, val) => updateDynamicItem('truques', id, val, TRUQUES)} />}
            {currentStep === 4 && <DynamicSection title="Magias" itemName="Magia" items={formData.magias} options={MAGIAS} onAdd={() => addDynamicItem('magias')} onRemove={(id) => removeDynamicItem('magias', id)} onUpdate={(id, val) => updateDynamicItem('magias', id, val, MAGIAS)} />}
            {currentStep === 5 && <DynamicSection title="Perícias" itemName="Perícia" items={formData.pericias} options={PERICIAS} onAdd={() => addDynamicItem('pericias')} onRemove={(id) => removeDynamicItem('pericias', id)} onUpdate={(id, val) => updateDynamicItem('pericias', id, val, PERICIAS)} />}

            {/* ETAPA 6: MODIFICADORES/ATRIBUTOS */}
            {currentStep === 6 && (
               <div className="animate-fade-in">
                  <div className="flex justify-between items-center border-b border-gray-700 pb-4 mb-8">
                      <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4">Habilidades (Atributos)</h2>
                      <span className="text-sm text-gray-500">Distribua seus pontos</span>
                  </div>
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
                           {opt === 'A' ? "Itens sugeridos da Classe e Origem." : "Ouro Inicial para comprar itens (100 PO)."}
                         </div>
                      </label>
                    ))}
                  </div>
               </div>
            )}

            {/* NAVEGAÇÃO */}
            <div className="mt-10 flex justify-between pb-8 pt-6 border-t border-gray-700">
              {currentStep > 1 ? (
                <button type="button" onClick={prevStep} className="px-6 py-3 rounded-lg bg-gray-600 hover:bg-gray-500 text-white font-semibold flex items-center gap-2">← Voltar</button>
              ) : <div />}

              {currentStep < steps.length ? (
                <button type="button" onClick={nextStep} className="px-8 py-3 rounded-lg bg-red-600 hover:bg-red-500 text-white font-semibold flex items-center gap-2 shadow-lg shadow-red-900/50">Próximo →</button>
              ) : (
                <button type="button" onClick={handleFinish} className="px-8 py-3 rounded-lg bg-green-600 hover:bg-green-500 text-white font-semibold flex items-center gap-2 shadow-lg shadow-green-900/50">Finalizar Ficha ✓</button>
              )}
            </div>

          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateCharacter;
