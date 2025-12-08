import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';
import { Shield, Heart, Zap, Scroll, Backpack, Brain, Activity, Dna, Ruler } from 'lucide-react';
import InfoBadge from '../components/ui/InfoBadge';
import StatBox from '../components/ui/StatBox';
import AttributeRow from '../components/ui/AttributeRow';

// --- INTERFACES ---
interface ItemLista { id: number; nome: string; descricao?: string; }
interface Classe extends ItemLista { equipamentoA?: string; equipamentoB?: string; }
interface Origem extends ItemLista { equipamentoA?: string; equipamentoB?: string; }
interface MagiaDTO { id: number; nome: string; descricao: string; circulo: number; }
interface TalentoDTO { id: number; nome: string; descricao: string; }

interface CharacterData {
  id: number;
  nomePersonagem: string;
  nivel: number;
  alinhamento: string;
  tamanho: string;
  imagem?: string;
  
  racaId: number;
  subracaId?: number;
  classeId: number;
  subclasseId?: number;
  origemId: number;

  // Atributos
  forca: number;
  destreza: number;
  constituicao: number;
  inteligencia: number;
  sabedoria: number;
  carisma: number;

  pontosDeVidaMaximos: number;
  classeDeArmadura: number;
  iniciativa: number;
  deslocamento: number;
  percepcaoPassiva: number;

  pericias: string[];
  
  // --- CAMPO NOVO QUE VEM DO BACKEND ---
  salvaguardas: string[]; 
  // -------------------------------------

  talentosIds: number[];
  magiasPreparadasIds: number[];
  
  escolhaEquipamentoClasse: string;
  escolhaEquipamentoOrigem: string;
  po: number;
}

// --- MAPEAMENTOS ---
const SKILL_MAP = [
  { name: 'Acrobacia', attr: 'destreza' },
  { name: 'Arcanismo', attr: 'inteligencia' },
  { name: 'Atletismo', attr: 'forca' },
  { name: 'Atuação', attr: 'carisma' },
  { name: 'Enganação', attr: 'carisma' },
  { name: 'Furtividade', attr: 'destreza' },
  { name: 'História', attr: 'inteligencia' },
  { name: 'Intimidação', attr: 'carisma' },
  { name: 'Intuição', attr: 'sabedoria' },
  { name: 'Investigação', attr: 'inteligencia' },
  { name: 'Lidar com Animais', attr: 'sabedoria' },
  { name: 'Medicina', attr: 'sabedoria' },
  { name: 'Natureza', attr: 'inteligencia' },
  { name: 'Percepção', attr: 'sabedoria' },
  { name: 'Persuasão', attr: 'carisma' },
  { name: 'Prestidigitação', attr: 'destreza' },
  { name: 'Religião', attr: 'inteligencia' },
  { name: 'Sobrevivência', attr: 'sabedoria' },
] as const;

// Mapeamento para Salvaguardas (Label deve ser igual ao que vem do Java)
const SAVING_THROWS = [
    { attr: 'forca', label: 'Força' },
    { attr: 'destreza', label: 'Destreza' },
    { attr: 'constituicao', label: 'Constituição' },
    { attr: 'inteligencia', label: 'Inteligência' },
    { attr: 'sabedoria', label: 'Sabedoria' },
    { attr: 'carisma', label: 'Carisma' },
] as const;

const CharacterView = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [character, setCharacter] = useState<CharacterData | null>(null);
  const [loading, setLoading] = useState(true);

  // Estados para lookup
  const [listas, setListas] = useState({
    racas: [] as ItemLista[],
    subracas: [] as ItemLista[],
    classes: [] as Classe[],
    subclasses: [] as ItemLista[],
    origens: [] as Origem[],
    talentos: [] as TalentoDTO[],
    magias: [] as MagiaDTO[]
  });

  const getMod = (valor: number) => Math.floor((valor - 10) / 2);
  const formatMod = (val: number) => (val >= 0 ? `+${val}` : `${val}`);
  const getProficiencyBonus = (level: number) => Math.ceil(level / 4) + 1;
  const formatText = (text: string) => text ? text.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase()) : '-';

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token');
      const usuarioId = localStorage.getItem('usuarioId');
      const headers = { 'Authorization': `Bearer ${token}` };

      try {
        const [resRacas, resClasses, resOrigens, resTalentos, resMagias] = await Promise.all([
            fetch(`http://localhost:8080/api/racas`, { headers }),
            fetch(`http://localhost:8080/api/classes`, { headers }),
            fetch(`http://localhost:8080/api/origens?usuarioId=${usuarioId}`, { headers }),
            fetch(`http://localhost:8080/api/talentos`, { headers }),
            fetch(`http://localhost:8080/api/magias?usuarioId=${usuarioId}`, { headers })
        ]);

        const charRes = await fetch(`http://localhost:8080/api/personagens/${id}?usuarioId=${usuarioId}`, { headers });
        const charData = await charRes.json();

        let subracasData = [], subclassesData = [];
        if (charData.racaId) {
            const srRes = await fetch(`http://localhost:8080/api/racas/${charData.racaId}/subracas`, { headers });
            if (srRes.ok) subracasData = await srRes.json();
        }
        if (charData.classeId) {
            const scRes = await fetch(`http://localhost:8080/api/classes/${charData.classeId}/subclasses`, { headers });
            if (scRes.ok) subclassesData = await scRes.json();
        }

        setListas({
            racas: await resRacas.json(),
            subracas: subracasData,
            classes: await resClasses.json(),
            subclasses: subclassesData,
            origens: await resOrigens.json(),
            talentos: await resTalentos.json(),
            magias: await resMagias.json()
        });

        setCharacter(charData);
      } catch (error) {
        console.error("Erro ao carregar ficha:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  if (loading || !character) return <div className="min-h-screen bg-[#1A1A1A] flex items-center justify-center text-white">Carregando Grimório...</div>;

  const raca = listas.racas.find(r => r.id === character.racaId)?.nome || "Desconhecido";
  const subracaObj = listas.subracas.find(s => s.id === character.subracaId);
  const subracaNome = subracaObj ? subracaObj.nome : null;

  const classeObj = listas.classes.find(c => c.id === character.classeId);
  const classe = classeObj?.nome || "Desconhecido";
  const subclasse = listas.subclasses.find(s => s.id === character.subclasseId)?.nome;
  const origem = listas.origens.find(o => o.id === character.origemId)?.nome || "Desconhecido";

  const equipClasseTexto = character.escolhaEquipamentoClasse === 'A' ? classeObj?.equipamentoA : classeObj?.equipamentoB;
  const equipOrigemTexto = character.escolhaEquipamentoOrigem === 'A' ? listas.origens.find(o => o.id === character.origemId)?.equipamentoA : listas.origens.find(o => o.id === character.origemId)?.equipamentoB;

  const proficiencyBonus = getProficiencyBonus(character.nivel);

  return (
    <div className="bg-[#1A1A1A] min-h-screen text-gray-200 font-sans pb-10">
      <Header />
      
      <main className="container mx-auto px-4 py-8 max-w-7xl">
        
        {/* --- CABEÇALHO --- */}
        <div className="bg-[#2D2D2D] rounded-xl shadow-2xl overflow-hidden border border-gray-700 mb-8 flex flex-col-reverse md:flex-row">
            <div className="md:w-3/4 p-6 sm:p-8 flex flex-col justify-between">
                <div>
                    <h1 className="text-4xl font-bold text-white mb-2">{character.nomePersonagem}</h1>
                    <p className="text-xl text-red-500 font-semibold mb-6 flex items-center gap-2">
                        {raca} {subracaNome ? `(${subracaNome})` : ''} 
                        <span className="text-gray-600">•</span> 
                        {classe} {subclasse ? `- ${subclasse}` : ''}
                    </p>

                    <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 mb-8">
                        <InfoBadge label="Nível" value={character.nivel} />
                        <InfoBadge label="Origem" value={origem} />
                        <InfoBadge label="Alinhamento" value={formatText(character.alinhamento)} />
                        <InfoBadge label="Tamanho" value={formatText(character.tamanho || "Médio")} />
                    </div>

                    <div className="grid grid-cols-3 sm:grid-cols-6 gap-4">
                        <StatBox icon={<Heart size={24} />} label="PV Máx" value={character.pontosDeVidaMaximos} color="text-red-600" />
                        <StatBox icon={<Shield size={24} />} label="CA" value={character.classeDeArmadura} color="text-red-500" />
                        <StatBox icon={<Zap size={24} />} label="Iniciativa" value={formatMod(character.iniciativa)} color="text-gray-300" />
                        <StatBox icon={<Ruler size={24} />} label="Desloc." value={`${character.deslocamento}m`} color="text-gray-300" />
                        <StatBox icon={<Brain size={24} />} label="Profic." value={`+${proficiencyBonus}`} color="text-red-400" />
                        <StatBox icon={<Dna size={24} />} label="Percepção" value={character.percepcaoPassiva} color="text-gray-400" />
                    </div>
                </div>

                <div className="mt-8 flex gap-4">
                    <button onClick={() => navigate(`/editar-personagem/${id}`)} className="bg-red-700 hover:bg-red-600 text-white px-6 py-2 rounded text-sm font-bold transition-colors uppercase tracking-wider shadow-lg shadow-red-900/20">
                        Editar Ficha
                    </button>
                </div>
            </div>

            <div className="md:w-1/4 bg-[#222] relative group min-h-[250px] border-l border-gray-700">
                {character.imagem ? (
                    <img src={`data:image/jpeg;base64,${character.imagem}`} alt={character.nomePersonagem} className="absolute inset-0 w-full h-full object-cover object-top opacity-90 group-hover:opacity-100 transition-opacity" />
                ) : (
                    <div className="w-full h-full flex flex-col items-center justify-center bg-gray-800 text-gray-600 p-8 text-center">
                        <span className="text-4xl mb-2">?</span>
                        <span className="text-xs uppercase font-bold">Sem Imagem</span>
                    </div>
                )}
                <div className="absolute bottom-0 left-0 right-0 h-24 bg-gradient-to-t from-[#2D2D2D] to-transparent md:hidden"></div>
            </div>
        </div>

        {/* --- CONTEÚDO PRINCIPAL --- */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
            
            {/* COLUNA 1: ESTATÍSTICAS */}
            <div className="lg:col-span-1 space-y-6">
                
                {/* 1. ATRIBUTOS */}
                <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                    <h3 className="text-xl font-bold text-gray-100 mb-6 border-b border-gray-600 pb-3 flex items-center gap-2">
                        Atributos
                    </h3>
                    <div className="space-y-4">
                        <AttributeRow label="Força" score={character.forca} />
                        <AttributeRow label="Destreza" score={character.destreza} />
                        <AttributeRow label="Constituição" score={character.constituicao} />
                        <AttributeRow label="Inteligência" score={character.inteligencia} />
                        <AttributeRow label="Sabedoria" score={character.sabedoria} />
                        <AttributeRow label="Carisma" score={character.carisma} />
                    </div>
                </div>

                <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                    <h3 className="text-xl font-bold text-gray-100 mb-4 border-b border-gray-600 pb-3 flex justify-between items-center">
                        <span className="flex items-center gap-2">
                           Salvaguardas
                        </span>
                    </h3>
                    <div className="grid grid-cols-2 gap-x-4 gap-y-3">
                        {SAVING_THROWS.map((save) => {
                            const attrScore = character[save.attr as keyof CharacterData] as number;
                            const attrMod = getMod(attrScore);
                            const isProficient = character.salvaguardas && character.salvaguardas.includes(save.label);
                            const totalSave = attrMod + (isProficient ? proficiencyBonus : 0);

                            return (
                                <div key={save.attr} className="flex justify-between items-center group p-1 rounded hover:bg-[#363636] transition-colors">
                                    <div className="flex items-center gap-2">
                                        <div className={`w-2.5 h-2.5 rounded-full border ${isProficient ? 'bg-red-500 border-red-500 shadow-[0_0_6px_rgba(239,68,68,0.8)]' : 'bg-transparent border-gray-600'}`}></div>
                                        <span className={`text-sm ${isProficient ? 'text-white font-bold' : 'text-gray-400'}`}>
                                            {save.label.substring(0,3)}
                                        </span>
                                    </div>
                                    <span className={`font-mono font-bold ${isProficient ? 'text-red-400' : 'text-gray-500'}`}>
                                        {formatMod(totalSave)}
                                    </span>
                                </div>
                            );
                        })}
                    </div>
                </div>

                {/* 3. LISTA COMPLETA DE PERÍCIAS */}
                <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                    <h3 className="text-xl font-bold text-gray-100 mb-4 border-b border-gray-600 pb-3 flex justify-between items-center">
                        <span>Perícias</span>
                        <span className="text-xs font-normal text-gray-500 uppercase">Modificador</span>
                    </h3>
                    <div className="space-y-1">
                        {SKILL_MAP.map((skill) => {
                            const attrScore = character[skill.attr as keyof CharacterData] as number;
                            const attrMod = getMod(attrScore);
                            const isProficient = character.pericias.includes(skill.name);
                            const totalMod = attrMod + (isProficient ? proficiencyBonus : 0);

                            return (
                                <div key={skill.name} className={`flex justify-between items-center p-2 rounded transition-colors ${isProficient ? 'bg-red-900/10 border border-red-900/30' : 'hover:bg-[#363636]'}`}>
                                    <div className="flex items-center gap-3">
                                        <div className={`w-2 h-2 rounded-full ${isProficient ? 'bg-red-500 shadow-[0_0_8px_rgba(239,68,68,0.8)]' : 'bg-gray-700'}`}></div>
                                        <span className={`text-sm ${isProficient ? 'text-gray-100 font-bold' : 'text-gray-400'}`}>
                                            {skill.name} <span className="text-[10px] text-gray-600 uppercase ml-1">({skill.attr.substring(0,3)})</span>
                                        </span>
                                    </div>
                                    <span className={`font-mono font-bold ${isProficient ? 'text-red-400' : 'text-gray-500'}`}>
                                        {formatMod(totalMod)}
                                    </span>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>

            {/* COLUNA 2: CONTEÚDO EXPANDIDO */}
            <div className="lg:col-span-2 space-y-6">
                
                {/* TALENTOS */}
                <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                    <h3 className="text-xl font-bold text-gray-100 mb-6 flex items-center gap-2 border-b border-gray-600 pb-3">
                        Talentos
                    </h3>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        {character.talentosIds.length > 0 ? character.talentosIds.map(tId => {
                            const talento = listas.talentos.find(t => t.id === tId);
                            return (
                                <div key={tId} className="bg-[#363636] p-4 rounded border-l-4 border-red-600 shadow-sm">
                                    <p className="font-bold text-gray-100 mb-1">{talento?.nome || "Carregando..."}</p>
                                    <p className="text-sm text-gray-400 leading-snug">{talento?.descricao}</p>
                                </div>
                            )
                        }) : <p className="text-gray-500 italic">Nenhum talento selecionado.</p>}
                    </div>
                </div>

                {/* GRIMÓRIO */}
                <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                    <h3 className="text-xl font-bold text-gray-100 mb-8 flex items-center gap-2 border-b border-gray-600 pb-3">
                        Truques e Magias
                    </h3>
                    
                    {/* Truques */}
                    <div className="mb-8">
                        <h4 className="text-xs font-bold text-gray-400 uppercase tracking-widest mb-4 border-l-2 border-gray-500 pl-3">
                            Truques (Nível 0)
                        </h4>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                            {character.magiasPreparadasIds
                                .map(mId => listas.magias.find(m => m.id === mId))
                                .filter((m): m is MagiaDTO => !!m && m.circulo === 0)
                                .map((magia) => (
                                    <div key={magia.id} className="bg-[#363636] border border-gray-600 rounded p-4 shadow-sm hover:border-gray-500 transition-colors">
                                        <div className="flex justify-between items-start mb-2">
                                            <h5 className="font-bold text-white">{magia.nome}</h5>
                                            <span className="text-[10px] uppercase font-bold text-gray-400 bg-black/30 px-2 py-0.5 rounded">Truque</span>
                                        </div>
                                        <p className="text-sm text-gray-400 leading-relaxed">{magia.descricao}</p>
                                    </div>
                                ))
                            }
                            {character.magiasPreparadasIds.filter(mId => listas.magias.find(m => m.id === mId)?.circulo === 0).length === 0 && <p className="text-gray-500 italic text-sm">Nenhum truque aprendido.</p>}
                        </div>
                    </div>

                    {/* Magias */}
                    <div>
                        <h4 className="text-xs font-bold text-red-400 uppercase tracking-widest mb-4 border-l-2 border-red-500 pl-3">
                            Magias Preparadas
                        </h4>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                             {character.magiasPreparadasIds
                                .map(mId => listas.magias.find(m => m.id === mId))
                                .filter((m): m is MagiaDTO => !!m && m.circulo > 0)
                                .sort((a, b) => a.circulo - b.circulo)
                                .map((magia) => (
                                    <div key={magia.id} className="bg-[#363636] border-l-4 border-red-900/60 rounded p-4 shadow-sm hover:bg-[#3a3a3a] transition-colors">
                                        <div className="flex justify-between items-start mb-2">
                                            <h5 className="font-bold text-white">{magia.nome}</h5>
                                            <span className="text-[10px] uppercase font-bold text-red-200 bg-red-900/40 px-2 py-0.5 rounded">Nível {magia.circulo}</span>
                                        </div>
                                        <p className="text-sm text-gray-400 leading-relaxed">{magia.descricao}</p>
                                    </div>
                                ))
                            }
                            {character.magiasPreparadasIds.filter(mId => listas.magias.find(m => m.id === mId)?.circulo !== 0).length === 0 && <p className="text-gray-500 italic text-sm">Nenhuma magia preparada.</p>}
                        </div>
                    </div>
                </div>

                {/* EQUIPAMENTO */}
                <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                    <div className="flex justify-between items-center border-b border-gray-600 pb-3 mb-6">
                        <h3 className="text-xl font-bold text-gray-100 flex items-center gap-2">
                            Inventário
                        </h3>
                        <div className="bg-black/30 px-4 py-1 rounded border border-yellow-600/30 text-yellow-500 text-sm font-bold font-mono">
                            {character.po} PO
                        </div>
                    </div>
                    
                    <div className="space-y-4">
                        <div className="bg-[#363636] p-4 rounded border-l-2 border-gray-500">
                            <span className="text-[10px] text-gray-500 uppercase font-bold block mb-1 tracking-wider">Do Pacote de Classe</span>
                            <p className="text-gray-300 text-sm">{equipClasseTexto || "Nenhum equipamento selecionado."}</p>
                        </div>
                        <div className="bg-[#363636] p-4 rounded border-l-2 border-gray-500">
                            <span className="text-[10px] text-gray-500 uppercase font-bold block mb-1 tracking-wider">Do Pacote de Origem</span>
                            <p className="text-gray-300 text-sm">{equipOrigemTexto || "Nenhum equipamento selecionado."}</p>
                        </div>
                    </div>
                </div>

            </div>
        </div>
      </main>
    </div>
  );
};

export default CharacterView;