import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';
import { Shield, Heart, Zap, Skull, Activity, Brain, Sword, Star, Wind, Waves } from 'lucide-react';
import InfoBadge from '../components/ui/InfoBadge';
import StatBox from '../components/ui/StatBox';
import AttributeRow from '../components/ui/AttributeRow';


interface RecursoCreaturaDTO { id: number; nome: string; descricao: string; }

interface CreatureData {
  id: number;
  nome: string; tamanho: string; tipo: string; tag: string; alinhamento: string;
  ca: string; pv: string;
  
  deslBase: string; 
  deslVoo: string; 
  deslNatacao: string;
  str: number; 
  dex: number; 
  con: number; 
  intelligence: number;
  wis: number; 
  cha: number;

  saves: string; skills: string; resistDano: string; imunidDano: string; imunidCond: string; 
  sentidos: string; idiomas: string; nd: string;
  
  habilidadesIds: number[];
  acoesIds: number[];
  
  legendaryActions: string; 
  lairActions: string;
  imagem?: string;
}

const CreatureView = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [creature, setCreature] = useState<CreatureData | null>(null);
  const [loading, setLoading] = useState(true);

  const [listas, setListas] = useState({
      habilidades: [] as RecursoCreaturaDTO[],
      acoes: [] as RecursoCreaturaDTO[]
  });

  const getMod = (valor: number) => Math.floor((valor - 10) / 2);
  const formatMod = (val: number) => (val >= 0 ? `+${val}` : `${val}`);
  const formatText = (text: string) => text ? text.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase()) : '-';

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token');
      if (!token) return;
      const tokenLimpo = token.replace("Bearer ", "").trim();
      const headers = { 'Authorization': `Bearer ${tokenLimpo}` };

      try {
        const [resHab, resAcoes] = await Promise.all([
            fetch(`http://localhost:8080/api/habilidades_criatura`, { headers }),
            fetch(`http://localhost:8080/api/acoes_criatura`, { headers })
        ]);

        const usuarioId = localStorage.getItem('usuarioId');
        const resCreature = await fetch(`http://localhost:8080/api/criaturas/${id}?usuarioId=${usuarioId}`, { headers });
        
        if (resCreature.ok) {
            setCreature(await resCreature.json());
            setListas({
                habilidades: await resHab.json(),
                acoes: await resAcoes.json()
            });
        }
      } catch (error) {
        console.error("Erro ao carregar criatura:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  if (loading || !creature) return <div className="min-h-screen bg-[#1A1A1A] flex items-center justify-center text-white">Carregando Bestiário...</div>;

  const initiativeMod = getMod(creature.dex);

  return (
    <div className="bg-[#1A1A1A] min-h-screen text-gray-200 font-sans pb-10">
      <Header />
      
      <main className="container mx-auto px-4 py-8 max-w-7xl">
        
        <div className="bg-[#2D2D2D] rounded-xl shadow-2xl overflow-hidden border border-gray-700 mb-8 flex flex-col-reverse md:flex-row">
            
            <div className="md:w-3/4 p-6 sm:p-8 flex flex-col justify-between">
                <div>
                    <h1 className="text-4xl font-bold text-white mb-2 font-medieval">{creature.nome}</h1>
                    <p className="text-xl text-red-500 font-semibold mb-6 flex items-center gap-2">
                        {formatText(creature.tamanho)} {creature.tipo} {creature.tag ? `(${creature.tag})` : ''}
                    </p>

                    <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 mb-8">
                        <InfoBadge label="Desafio (ND)" value={creature.nd} highlight />
                        <InfoBadge label="Alinhamento" value={formatText(creature.alinhamento)} />
                        <InfoBadge label="Sentidos" value={creature.sentidos || "-"} />
                        <InfoBadge label="Idiomas" value={creature.idiomas || "-"} />
                    </div>

                    <div className="grid grid-cols-3 sm:grid-cols-6 gap-4">
                        <StatBox icon={<Shield size={24} />} label="CA" value={creature.ca} color="text-red-500" />
                        <StatBox icon={<Heart size={24} />} label="PV Médio" value={creature.pv ? creature.pv.split(' ')[0] : '0'} color="text-red-600" />
                        <StatBox icon={<Zap size={24} />} label="Iniciativa" value={formatMod(initiativeMod)} color="text-yellow-500" />
                        <StatBox icon={<Activity size={24} />} label="Chão" value={creature.deslBase || "0m"} color="text-gray-300" />
                        <StatBox icon={<Wind size={24} />} label="Voo" value={creature.deslVoo || "-"} color="text-cyan-400" />
                        <StatBox icon={<Waves size={24} />} label="Natação" value={creature.deslNatacao || "-"} color="text-blue-500" />
                    </div>
                </div>

                <div className="mt-8 flex gap-4">
                    <button onClick={() => navigate(`/editar-criatura/${id}`)} className="bg-red-700 hover:bg-red-600 text-white px-6 py-2 rounded text-sm font-bold transition-colors uppercase tracking-wider shadow-lg shadow-red-900/20">
                        Editar Criatura
                    </button>
                </div>
            </div>

            <div className="md:w-1/4 bg-[#222] relative group min-h-[250px] border-l border-gray-700">
                {creature.imagem ? (
                    <img src={`data:image/jpeg;base64,${creature.imagem}`} alt={creature.nome} className="absolute inset-0 w-full h-full object-cover object-top opacity-90 group-hover:opacity-100 transition-opacity" />
                ) : (
                    <div className="w-full h-full flex flex-col items-center justify-center bg-gray-800 text-gray-600 p-8 text-center">
                        <span className="text-4xl mb-2">?</span>
                        <span className="text-xs uppercase font-bold">Sem Imagem</span>
                    </div>
                )}
                <div className="absolute bottom-0 left-0 right-0 h-24 bg-gradient-to-t from-[#2D2D2D] to-transparent md:hidden"></div>
            </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
            
            <div className="lg:col-span-1 space-y-6">
                
                <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                    <h3 className="text-xl font-bold text-gray-100 mb-6 border-b border-gray-600 pb-3 flex items-center gap-2">
                        <Activity className="text-red-500" size={20} /> Atributos
                    </h3>
                    <div className="space-y-4">
                        <AttributeRow label="Força" score={creature.str} />
                        <AttributeRow label="Destreza" score={creature.dex} />
                        <AttributeRow label="Constituição" score={creature.con} />
                        
                        <AttributeRow label="Inteligência" score={creature.intelligence} />
                        
                        <AttributeRow label="Sabedoria" score={creature.wis} />
                        <AttributeRow label="Carisma" score={creature.cha} />
                    </div>
                </div>

                <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                    <h3 className="text-xl font-bold text-gray-100 mb-4 border-b border-gray-600 pb-3 flex justify-between items-center">
                         <span className="flex items-center gap-2">
                            <Brain className="text-red-500" size={20} /> Detalhes
                        </span>
                    </h3>
                    <div className="space-y-4">
                        {creature.saves && (
                             <DetailItem label="Testes de Resistência" value={creature.saves} icon={<Shield size={16} className="text-red-400" />} />
                        )}
                        {creature.skills && (
                             <DetailItem label="Perícias" value={creature.skills} icon={<Activity size={16} className="text-red-400" />} />
                        )}
                    </div>
                </div>

                {(creature.resistDano || creature.imunidDano || creature.imunidCond) && (
                    <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                        <h3 className="text-xl font-bold text-gray-100 mb-4 border-b border-gray-600 pb-3 flex justify-between items-center">
                            <span className="flex items-center gap-2">
                                <Shield className="text-red-500" size={20} /> Defesas
                            </span>
                        </h3>
                        <div className="space-y-4">
                            {creature.resistDano && <DetailItem label="Resistência a Dano" value={creature.resistDano} />}
                            {creature.imunidDano && <DetailItem label="Imunidade a Dano" value={creature.imunidDano} />}
                            {creature.imunidCond && <DetailItem label="Imunidade a Condição" value={creature.imunidCond} />}
                        </div>
                    </div>
                )}
            </div>

            <div className="lg:col-span-2 space-y-6">
                
                {creature.habilidadesIds.length > 0 && (
                    <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                        <h3 className="text-xl font-bold text-gray-100 mb-6 flex items-center gap-2 border-b border-gray-600 pb-3">
                            <Zap className="text-red-500" size={20} /> Habilidades Especiais
                        </h3>
                        <div className="space-y-4">
                            {creature.habilidadesIds.map(hId => {
                                const habilidade = listas.habilidades.find(h => h.id === hId);
                                return (
                                    <div key={hId} className="bg-[#363636] p-4 rounded border-l-4 border-red-600 shadow-sm">
                                        <p className="font-bold text-gray-100 mb-1 text-lg">{habilidade?.nome || "Carregando..."}</p>
                                        <p className="text-sm text-gray-400 leading-relaxed text-justify">{habilidade?.descricao}</p>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                )}

                {creature.acoesIds.length > 0 && (
                    <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                        <h3 className="text-xl font-bold text-gray-100 mb-6 flex items-center gap-2 border-b border-gray-600 pb-3">
                            <Sword className="text-red-500" size={20} /> Ações
                        </h3>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                            {creature.acoesIds.map(aId => {
                                const acao = listas.acoes.find(a => a.id === aId);
                                return (
                                    <div key={aId} className="bg-[#363636] border border-gray-600 rounded p-4 shadow-sm hover:border-red-500/50 transition-colors">
                                        <div className="flex justify-between items-start mb-2">
                                            <h5 className="font-bold text-white text-lg">{acao?.nome}</h5>
                                            <span className="text-[10px] uppercase font-bold text-red-200 bg-red-900/40 px-2 py-0.5 rounded">Ação</span>
                                        </div>
                                        <p className="text-sm text-gray-400 leading-relaxed text-justify">{acao?.descricao}</p>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                )}

                {creature.legendaryActions && (
                     <div className="bg-[#2D2D2D] p-6 rounded-lg border border-red-900/50 shadow-lg">
                        <h3 className="text-xl font-bold text-red-400 mb-4 flex items-center gap-2 border-b border-red-900/50 pb-3">
                            <Star className="text-yellow-500" size={20} /> Ações Lendárias
                        </h3>
                        <div className="bg-[#363636] p-4 rounded text-sm text-gray-300 leading-relaxed whitespace-pre-wrap border border-red-900/30">
                            {creature.legendaryActions}
                        </div>
                    </div>
                )}

                {creature.lairActions && (
                     <div className="bg-[#2D2D2D] p-6 rounded-lg border border-gray-700 shadow-lg">
                        <h3 className="text-xl font-bold text-gray-100 mb-4 flex items-center gap-2 border-b border-gray-600 pb-3">
                            <Skull className="text-gray-400" size={20} /> Ações de Covil
                        </h3>
                        <div className="bg-[#363636] p-4 rounded text-sm text-gray-300 leading-relaxed whitespace-pre-wrap">
                            {creature.lairActions}
                        </div>
                    </div>
                )}

            </div>
        </div>
      </main>
    </div>
  );
};

const DetailItem = ({ label, value, icon }: { label: string, value: string, icon?: React.ReactNode }) => (
    <div className="flex flex-col border-b border-gray-700 pb-2 last:border-0">
        <span className="text-xs uppercase font-bold text-gray-500 mb-1 flex items-center gap-2">
            {icon} {label}
        </span>
        <span className="text-gray-200 text-sm leading-relaxed">{value}</span>
    </div>
);

export default CreatureView;