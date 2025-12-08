import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Header from '../components/layout/Header';
import Stepper from '../components/ui/Stepper';
import DynamicSection from '../components/form/DynamicSection';
import AttributeCard from '../components/form/AttributeCard';
import SelectField from '../components/form/SelectField';


interface ItemLista { id: number; nome: string; }

interface Classe extends ItemLista { 
    periciasDeClasse?: string[]; 
    equipamentoA?: string; 
    equipamentoB?: string;
}

interface Origem extends ItemLista {
    equipamentoA?: string;
    equipamentoB?: string;
}

interface Subclasse extends ItemLista { classePaiId?: number; }
interface Subraca extends ItemLista {} 

interface DynamicItem {
  id: number;
  value: string | number; 
  description: string;
}

interface TalentoDTO {
  id: number;
  nome: string;
  descricao: string;
}

interface MagiaDTO {
  id: number;
  nome: string;
  descricao: string;
  circulo: number; 
}

interface IFormData {
  nome: string;
  tamanho: string;
  alinhamento: string;
  nivel: number;
  

  racaId: string | number;
  subracaId: string | number;
  classeId: string | number;
  subclasseId: string | number;
  origemId: string | number;
  

  periciaClasse1: string;
  periciaClasse2: string;
  

  foto: File | null;
  fotoPreview: string | null;
  

  str: number; dex: number; con: number; 
  inte: number; wis: number; cha: number;
  

  equipamentoClasse: string;
  equipamentoOrigem: string;


  talentos: DynamicItem[];
  truques: DynamicItem[];
  magias: DynamicItem[];
}

const reconstruirLista = (ids: number[], listaCompleta: any[], chaveDescricao = 'descricao'): DynamicItem[] => {
    if (!ids || ids.length === 0) return [];
    
    return ids.map((id, index) => {
        const itemOriginal = listaCompleta.find(i => i.id === id);
        return {
            id: Date.now() + index,
            value: id, 
            description: itemOriginal ? itemOriginal[chaveDescricao] : ''
        };
    });
};

const CreateCharacter = () => {
  const navigate = useNavigate();
  const { id } = useParams(); 
  const isEditMode = !!id;

  const steps = [
    { id: 1, label: "Dados Básicos" },
    { id: 2, label: "Talentos" },
    { id: 3, label: "Truques" },
    { id: 4, label: "Magias" },
    { id: 5, label: "Atributos" },
    { id: 6, label: "Equipamento" },
  ];

  const OPCOES_TAMANHO = [
      { id: "MINUSCULO", nome: "Minúsculo" },
      { id: "PEQUENO", nome: "Pequeno" },
      { id: "MEDIO", nome: "Médio" },
      { id: "GRANDE", nome: "Grande" },
      { id: "ENORME", nome: "Enorme" },
      { id: "COLOSSAL", nome: "Colossal" }
  ];

  const [currentStep, setCurrentStep] = useState(1);
  const [loading, setLoading] = useState(true);


  const [listas, setListas] = useState({
     racas: [] as ItemLista[],
     subracas: [] as Subraca[], 
     classes: [] as Classe[],
     subclasses: [] as Subclasse[],
     origens: [] as Origem[],
     talentos: [] as TalentoDTO[],
     magias: [] as MagiaDTO[]
  });


  const [formData, setFormData] = useState<IFormData>({
    nivel: 1,
    nome: '', 
    tamanho: 'MEDIO', 
    alinhamento: '', 
    racaId: '', 
    subracaId: '',
    classeId: '', 
    subclasseId: '', 
    origemId: '', 
    periciaClasse1: '',
    periciaClasse2: '',

    foto: null, 
    fotoPreview: null,
    
    str: 10, dex: 10, con: 10, inte: 10, wis: 10, cha: 10,
    
    equipamentoClasse: 'A',
    equipamentoOrigem: 'A',

    talentos: [],
    truques: [],
    magias: [],
  });


  useEffect(() => {
    const fetchListas = async () => {
        const token = localStorage.getItem('token');
        const usuarioId = localStorage.getItem('usuarioId');

        if (!usuarioId || !token) {
            setLoading(false);
            return;
        }

        const headers = { 'Authorization': `Bearer ${token}` };

        try {
            const [resRacas, resClasses, resOrigens, resTalentos, resMagias] = await Promise.all([
                fetch(`http://localhost:8080/api/racas`, { headers }),
                fetch(`http://localhost:8080/api/classes`, { headers }),
                fetch(`http://localhost:8080/api/origens?usuarioId=${usuarioId}`, { headers }),
                fetch(`http://localhost:8080/api/talentos`, { headers }),
                fetch(`http://localhost:8080/api/magias?usuarioId=${usuarioId}`, { headers })
            ]);

            const racas = await resRacas.json();
            const classes = await resClasses.json();
            const origens = await resOrigens.json();
            const talentos = await resTalentos.json();
            const magias = await resMagias.json();

            setListas(prev => ({ 
                ...prev, 
                racas, 
                classes, 
                origens, 
                talentos,
                magias 
            }));

        } catch (error) {
            console.error("Erro ao carregar dados:", error);
        } finally {
            setLoading(false);
        }
    };

    fetchListas();
  }, []);


  useEffect(() => {
    if (!isEditMode || loading) return;

    const fetchPersonagem = async () => {
        const token = localStorage.getItem('token');
        const usuarioId = localStorage.getItem('usuarioId');
        
        try {
            const res = await fetch(`http://localhost:8080/api/personagens/${id}?usuarioId=${usuarioId}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });

            if (res.ok) {
                const data = await res.json();
                
              
                const talentosRecuperados = reconstruirLista(data.talentosIds || [], listas.talentos);

                const todasMagiasIds: number[] = data.magiasPreparadasIds || [];
                const truquesIds: number[] = [];
                const magiasIds: number[] = [];

                todasMagiasIds.forEach(mId => {
                    const magiaInfo = listas.magias.find(m => m.id === mId);
                    if (magiaInfo) {
                        if (magiaInfo.circulo === 0) truquesIds.push(mId);
                        else magiasIds.push(mId);
                    }
                });

                const truquesRecuperados = reconstruirLista(truquesIds, listas.magias);
                const magiasRecuperadas = reconstruirLista(magiasIds, listas.magias);

              
                setFormData(prev => ({
                    ...prev,
                    nome: data.nomePersonagem,
                    nivel: data.nivel,
                    alinhamento: data.alinhamento,
                    
                    racaId: data.racaId,
                    subracaId: data.subracaId || '',
                    classeId: data.classeId,
                    subclasseId: data.subclasseId || '',
                    origemId: data.origemId,
                    
                  
                    tamanho: data.tamanho || 'MEDIO',

                    fotoPreview: data.imagem ? `data:image/jpeg;base64,${data.imagem}` : null,
                    foto: null,

                    str: data.forca, dex: data.destreza, con: data.constituicao,
                    inte: data.inteligencia, wis: data.sabedoria, cha: data.carisma,

                    periciaClasse1: data.pericias && data.pericias[0] ? data.pericias[0] : '',
                    periciaClasse2: data.pericias && data.pericias[1] ? data.pericias[1] : '',

                    equipamentoClasse: data.escolhaEquipamentoClasse || 'A', 
                    equipamentoOrigem: data.escolhaEquipamentoOrigem || 'A',
                    
                    talentos: talentosRecuperados,
                    truques: truquesRecuperados,
                    magias: magiasRecuperadas
                }));

                if (data.racaId) fetchSubracas(data.racaId);
                if (data.classeId) fetchSubclasses(data.classeId);
            }
        } catch (err) {
            console.error("Erro ao carregar personagem", err);
        }
    };
    
    fetchPersonagem();
  }, [id, isEditMode, loading]); 




  const fetchSubclasses = async (classeId: string | number) => {
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
      } catch (err) { console.error(err); }
  };

  const fetchSubracas = async (racaId: string | number) => {
      if (!racaId) { setListas(prev => ({ ...prev, subracas: [] })); return; }
      const token = localStorage.getItem('token');
      try {
          const res = await fetch(`http://localhost:8080/api/racas/${racaId}/subracas`, {
              headers: { 'Authorization': `Bearer ${token}` }
          });
          if (res.ok) {
              const data = await res.json();
              setListas(prev => ({ ...prev, subracas: data }));
          }
      } catch (error) { console.error(error); }
  };



  const updateData = <K extends keyof IFormData>(field: K, value: IFormData[K]) => {
  
    if (field === 'racaId') {
        fetchSubracas(value as string); 
        setFormData(prev => ({ ...prev, racaId: value as string | number, subracaId: '' }));
        return; 
    }
    if (field === 'classeId') {
        fetchSubclasses(value as string);
        setFormData(prev => ({ 
            ...prev, 
            classeId: value as string | number, 
            subclasseId: '',
            periciaClasse1: '',
            periciaClasse2: ''
        })); 
        return;
    }
  
    setFormData(prev => ({ ...prev, [field]: value }));
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

  const updateDynamicItem = (field: keyof IFormData, id: number, newValueStr: string, sourceOptions: any[]) => {
    if (!Array.isArray(formData[field])) return;
    const newValue = Number(newValueStr);
    const selectedOption = sourceOptions.find(opt => opt.value === newValue);
    const newDesc = selectedOption ? selectedOption.desc : '';

    const list = formData[field] as any[];
    setFormData(prev => ({
      ...prev,
      [field]: list.map(item => item.id === id ? { ...item, value: newValue, description: newDesc } : item)
    }));
  };

  const opcoesTalentos = listas.talentos.map(t => ({ value: t.id, label: t.nome, desc: t.descricao }));
  const opcoesTruques = listas.magias.filter(m => m.circulo === 0).map(m => ({ value: m.id, label: m.nome, desc: m.descricao }));
  const opcoesMagias = listas.magias.filter(m => m.circulo > 0).map(m => ({ value: m.id, label: `${m.nome} (Nvl ${m.circulo})`, desc: m.descricao }));


  const validateStep = (step: number): boolean => {
      if (step === 1) {
          if (!formData.nome.trim()) { alert("O Nome é obrigatório."); return false; }
          if (!formData.nivel || formData.nivel < 1) { alert("O Nível é obrigatório."); return false; }
          if (!formData.racaId) { alert("A Raça é obrigatória."); return false; }
        
          if (listas.subracas.length > 0 && !formData.subracaId) { alert("Esta raça exige uma Sub-raça."); return false; }
          
          if (!formData.classeId) { alert("A Classe é obrigatória."); return false; }
          if (!formData.periciaClasse1) { alert("Selecione a primeira Perícia."); return false; }
          if (!formData.periciaClasse2) { alert("Selecione a segunda Perícia."); return false; }
          
          if (!formData.origemId) { alert("A Origem é obrigatória."); return false; }
          if (!formData.alinhamento) { alert("O Alinhamento é obrigatório."); return false; }
          if (!formData.tamanho) { alert("O Tamanho é obrigatório."); return false; }
      }
      return true;
  };

  const handleNextStep = () => {
      if (validateStep(currentStep)) {
          setCurrentStep(prev => Math.min(prev + 1, steps.length));
      }
  };



  const handleFinish = async () => {
    if (!validateStep(1)) return;

    const token = localStorage.getItem('token');
    const usuarioId = localStorage.getItem('usuarioId');
    if (!usuarioId) { alert("Erro: Usuário não identificado."); return; }

    const getMod = (score: number) => Math.floor((score - 10) / 2);
    const modCon = getMod(formData.con);
    const modDex = getMod(formData.dex);
    
    const periciasFinais = [];
    if (formData.periciaClasse1) periciasFinais.push(formData.periciaClasse1);
    if (formData.periciaClasse2) periciasFinais.push(formData.periciaClasse2);

    let ouroInicial = 0;
    if (formData.equipamentoClasse === 'B') ouroInicial += 100; 
    if (formData.equipamentoOrigem === 'B') ouroInicial += 50;
    if (formData.equipamentoClasse === 'A' && formData.equipamentoOrigem === 'A') ouroInicial = 15;

    const talentosIds = formData.talentos.map(t => Number(t.value)).filter(v => v > 0);
    const magiasIds = [...formData.truques.map(t => Number(t.value)), ...formData.magias.map(t => Number(t.value))].filter(v => v > 0);

    const payload = {
        nomePersonagem: formData.nome,
        nivel: formData.nivel,
        tamanho: formData.tamanho,
        alinhamento: formData.alinhamento,
        pontosDeExperiencia: 0,
        
        classeId: Number(formData.classeId),
        subclasseId: formData.subclasseId ? Number(formData.subclasseId) : null,
        racaId: Number(formData.racaId),
        subracaId: formData.subracaId ? Number(formData.subracaId) : null,
        origemId: Number(formData.origemId),
        
        forca: formData.str, destreza: formData.dex, constituicao: formData.con,
        inteligencia: formData.inte, sabedoria: formData.wis, carisma: formData.cha,
        
        pontosDeVidaMaximos: 10 + modCon, pontosDeVidaAtuais: 10 + modCon, pontosDeVidaTemporarios: 0,
        classeDeArmadura: 10 + modDex, iniciativa: modDex, deslocamento: 9, percepcaoPassiva: 10 + getMod(formData.wis),
        
        pericias: periciasFinais,
        talentosIds: talentosIds, magiasPreparadasIds: magiasIds,
        
        escolhaEquipamentoClasse: formData.equipamentoClasse,
        escolhaEquipamentoOrigem: formData.equipamentoOrigem,
        pc: 0, pp: 0, po: ouroInicial, pl: 0,
        
        aparencia: "Foto enviada separadamente", historia: ""
    };

    try {
        const urlBase = `http://localhost:8080/api/personagens`;
        const url = isEditMode ? `${urlBase}/${id}?usuarioId=${usuarioId}` : `${urlBase}?usuarioId=${usuarioId}`;
        const method = isEditMode ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const txt = await response.text();
            throw new Error(txt || "Erro ao salvar ficha.");
        }

        const savedChar = await response.json();
        const charId = savedChar.id;

        if (formData.foto && charId) {
            const formDataImg = new FormData();
            formDataImg.append('file', formData.foto);
            
            const uploadRes = await fetch(`${urlBase}/${charId}/imagem?usuarioId=${usuarioId}`, {
                method: 'POST',
                headers: { 'Authorization': `Bearer ${token}` }, 
                body: formDataImg
            });

            if (!uploadRes.ok) console.warn("Erro ao subir imagem.");
        }

        alert(isEditMode ? "Personagem atualizado!" : "Personagem criado com sucesso!");
        navigate('/gerenciar-personagens'); 

    } catch (error: any) {
        console.error(error);
        alert(`Erro: ${error.message}`);
    }
  };


  const handleStepClick = (stepId: number) => {
    
      if (stepId > currentStep && !validateStep(currentStep)) return;
      setCurrentStep(stepId);
  };
  
  const prevStep = () => setCurrentStep(prev => Math.max(prev - 1, 1));

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />

      <main className="container mx-auto p-8">
        <div className="max-w-6xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl min-h-[700px] flex flex-col">
          
          <div className="flex justify-between items-center mb-6">
             <h1 className="text-2xl font-bold text-gray-400">
                {isEditMode ? `Editando: ${formData.nome}` : "Novo Personagem"}
             </h1>
          </div>

          <Stepper steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />

          <form onSubmit={(e) => e.preventDefault()} className="flex-1 flex flex-col justify-between mt-8">
            
            {currentStep === 1 && (
              <div className="animate-fade-in space-y-8">
                <div className="flex justify-between items-center border-b border-gray-700 pb-4">
                    <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4">Dados Básicos</h2>
                    {loading && <span className="text-yellow-500 text-sm animate-pulse">Carregando dados...</span>}
                </div>
                
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                  <div className="lg:col-span-2 grid grid-cols-1 md:grid-cols-2 gap-6">
                    
                    <div className="md:col-span-1">
                      <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">
                          Nome <span className="text-red-500">*</span>
                      </label>
                      <input type="text" value={formData.nome} onChange={e => updateData('nome', e.target.value)} className={`w-full p-3 rounded bg-[#444444] border ${!formData.nome && 'border-red-900/50'} border-gray-600 text-white focus:border-red-500 outline-none`} />
                    </div>
                    <div className="md:col-span-1">
                      <label className="block text-gray-400 mb-1 text-sm font-bold uppercase tracking-wider">
                          Nível <span className="text-red-500">*</span>
                      </label>
                      <input type="number" min="1" max="20" value={formData.nivel} onChange={e => updateData('nivel', parseInt(e.target.value))} className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:border-red-500 outline-none" />
                    </div>
                    
                    <SelectField required label="Raça" value={formData.racaId} onChange={(e: any) => updateData('racaId', e.target.value)} options={listas.racas} />
                    
                    <SelectField 
                        label="Sub-Raça" 
                        value={formData.subracaId} 
                        onChange={(e: any) => updateData('subracaId', e.target.value)} 
                        options={listas.subracas} 
                        disabled={!formData.racaId || listas.subracas.length === 0}
                        required={listas.subracas.length > 0} 
                    />
                    
                    <SelectField required label="Classe" value={formData.classeId} onChange={(e: any) => updateData('classeId', e.target.value)} options={listas.classes} />
                    <SelectField label="Sub-Classe" value={formData.subclasseId} onChange={(e: any) => updateData('subclasseId', e.target.value)} options={listas.subclasses} disabled={!formData.classeId || formData.nivel < 3} />

                    {(() => {
                        const classeSelecionada = listas.classes.find(c => c.id == formData.classeId);
                        const periciasDisponiveis = classeSelecionada?.periciasDeClasse || [];
                        const optionsP1 = periciasDisponiveis.filter(p => p !== formData.periciaClasse2);
                        const optionsP2 = periciasDisponiveis.filter(p => p !== formData.periciaClasse1);
                        return (
                            <>
                                <SelectField required label="Perícia (1)" value={formData.periciaClasse1} onChange={(e: any) => updateData('periciaClasse1', e.target.value)} options={optionsP1} disabled={!formData.classeId || periciasDisponiveis.length === 0} />
                                <SelectField required label="Perícia (2)" value={formData.periciaClasse2} onChange={(e: any) => updateData('periciaClasse2', e.target.value)} options={optionsP2} disabled={!formData.classeId || !formData.periciaClasse1} />
                            </>
                        );
                    })()}

                    <SelectField required label="Origem" value={formData.origemId} onChange={(e: any) => updateData('origemId', e.target.value)} options={listas.origens} />
                    
                    <SelectField required label="Alinhamento" value={formData.alinhamento} onChange={(e: any) => updateData('alinhamento', e.target.value)} options={[
                        { id: "LEAL_BOM", nome: "Leal e Bom" }, { id: "NEUTRO_BOM", nome: "Neutro e Bom" }, { id: "CAOTICO_BOM", nome: "Caótico e Bom" },
                        { id: "LEAL_NEUTRO", nome: "Leal e Neutro" }, { id: "VERDADEIRO_NEUTRO", nome: "Neutro" }, { id: "CAOTICO_NEUTRO", nome: "Caótico e Neutro" },
                        { id: "LEAL_MAU", nome: "Leal e Mau" }, { id: "NEUTRO_MAU", nome: "Neutro e Mau" }, { id: "CAOTICO_MAU", nome: "Caótico e Mau" }
                    ]} />
                    
                    <SelectField required label="Tamanho" value={formData.tamanho} onChange={(e: any) => updateData('tamanho', e.target.value)} options={OPCOES_TAMANHO} />
                  </div>
                  
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

            {currentStep === 2 && <DynamicSection title="Talentos" itemName="Talento" items={formData.talentos} options={opcoesTalentos} onAdd={() => addDynamicItem('talentos')} onRemove={(id) => removeDynamicItem('talentos', id)} onUpdate={(id, val) => updateDynamicItem('talentos', id, val, opcoesTalentos)} />}
            {currentStep === 3 && <DynamicSection title="Truques" itemName="Truque" items={formData.truques} options={opcoesTruques} onAdd={() => addDynamicItem('truques')} onRemove={(id) => removeDynamicItem('truques', id)} onUpdate={(id, val) => updateDynamicItem('truques', id, val, opcoesTruques)} />}
            {currentStep === 4 && <DynamicSection title="Magias" itemName="Magia" items={formData.magias} options={opcoesMagias} onAdd={() => addDynamicItem('magias')} onRemove={(id) => removeDynamicItem('magias', id)} onUpdate={(id, val) => updateDynamicItem('magias', id, val, opcoesMagias)} />}
            
            {currentStep === 5 && (
               <div className="animate-fade-in">
                  <div className="flex justify-between items-center border-b border-gray-700 pb-4 mb-8">
                      <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4">Habilidades (Atributos)</h2>
                      <span className="text-sm text-gray-500">Distribua seus pontos</span>
                  </div>
                  <div className="grid grid-cols-2 md:grid-cols-3 gap-8">
                    <AttributeCard label="Força" value={formData.str} onChange={(v: number) => updateData('str', v)} />
                    <AttributeCard label="Destreza" value={formData.dex} onChange={(v: number) => updateData('dex', v)} />
                    <AttributeCard label="Constituição" value={formData.con} onChange={(v: number) => updateData('con', v)} />
                    <AttributeCard label="Inteligência" value={formData.inte} onChange={(v: number) => updateData('inte', v)} />
                    <AttributeCard label="Sabedoria" value={formData.wis} onChange={(v: number) => updateData('wis', v)} />
                    <AttributeCard label="Carisma" value={formData.cha} onChange={(v: number) => updateData('cha', v)} />
                  </div>
               </div>
            )}

            {currentStep === 6 && (
               <div className="animate-fade-in space-y-10">
                  <div className="flex justify-between items-center border-b border-gray-700 pb-4 mb-8 pt-4">
                      <h2 className="text-3xl font-semibold text-white border-l-4 border-red-500 pl-4">Equipamento Inicial</h2>
                  </div>
                  <div>
                      <h3 className="text-xl font-bold text-gray-300 mb-4">Equipamento da Classe</h3>
                      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        {['A', 'B'].map(opt => {
                            const classeSel = listas.classes.find(c => c.id == formData.classeId);
                            let texto = "Selecione uma classe...";
                            if (classeSel) {
                                if (opt === 'A') texto = classeSel.equipamentoA || "Itens padrão.";
                                if (opt === 'B') texto = classeSel.equipamentoB || "Ouro inicial.";
                            }
                            return (
                              <label key={opt} className={`cursor-pointer border-2 rounded-xl p-6 transition-all hover:bg-[#3a3a3a] ${formData.equipamentoClasse === opt ? 'border-red-500 bg-[#3a3a3a] ring-1 ring-red-500' : 'border-gray-600 bg-[#444]'}`}>
                                 <div className="flex justify-between items-start mb-4">
                                     <span className="text-2xl font-bold text-white">Opção {opt}</span>
                                     <input type="radio" name="equipClasse" value={opt} checked={formData.equipamentoClasse === opt} onChange={() => updateData('equipamentoClasse', opt)} className="w-6 h-6 text-red-600 focus:ring-red-500 bg-gray-700 border-gray-600" />
                                 </div>
                                 <p className="text-gray-300 text-sm leading-relaxed">{texto}</p>
                              </label>
                            );
                        })}
                      </div>
                  </div>
                  <div>
                      <h3 className="text-xl font-bold text-gray-300 mb-4">Equipamento da Origem</h3>
                      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        {['A', 'B'].map(opt => {
                            const origemSel = listas.origens.find(o => o.id == formData.origemId);
                            let texto = "Selecione uma origem...";
                            if (origemSel) {
                                if (opt === 'A') texto = origemSel.equipamentoA || "Itens padrão.";
                                if (opt === 'B') texto = origemSel.equipamentoB || "50 PO.";
                            }
                            return (
                              <label key={opt} className={`cursor-pointer border-2 rounded-xl p-6 transition-all hover:bg-[#3a3a3a] ${formData.equipamentoOrigem === opt ? 'border-red-500 bg-[#3a3a3a] ring-1 ring-red-500' : 'border-gray-600 bg-[#444]'}`}>
                                 <div className="flex justify-between items-start mb-4">
                                     <span className="text-2xl font-bold text-white">Opção {opt}</span>
                                     <input type="radio" name="equipOrigem" value={opt} checked={formData.equipamentoOrigem === opt} onChange={() => updateData('equipamentoOrigem', opt)} className="w-6 h-6 text-red-600 focus:ring-red-500 bg-gray-700 border-gray-600" />
                                 </div>
                                 <p className="text-gray-300 text-sm leading-relaxed">{texto}</p>
                              </label>
                            );
                        })}
                      </div>
                  </div>
               </div>
            )}

            <div className="mt-10 flex justify-between pb-8 pt-6 border-t border-gray-700">
              {currentStep > 1 ? (
                <button type="button" onClick={prevStep} className="px-6 py-3 rounded-lg bg-gray-600 hover:bg-gray-500 text-white font-semibold flex items-center gap-2">← Voltar</button>
              ) : <div />}

              {currentStep < steps.length ? (
                <button type="button" onClick={handleNextStep} className="px-8 py-3 rounded-lg bg-red-600 hover:bg-red-500 text-white font-semibold flex items-center gap-2 shadow-lg shadow-red-900/50">Próximo →</button>
              ) : (
                <button type="button" onClick={handleFinish} className="px-8 py-3 rounded-lg bg-green-600 hover:bg-green-500 text-white font-semibold flex items-center gap-2 shadow-lg shadow-green-900/50">
                    {isEditMode ? 'Atualizar Ficha ✓' : 'Finalizar Ficha ✓'}
                </button>
              )}
            </div>

          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateCharacter;