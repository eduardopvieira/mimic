import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';

const CreateOrigin = () => {
  const navigate = useNavigate();
  const [error, setError] = useState('');

  const [formData, setFormData] = useState({
    nome: '',
    moedas: '',
    pericia1: '',
    pericia2: '',
    ferramenta: '',
    idioma: '',
    equipamento: '',
    featureNome: '',
    featureDesc: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.nome || !formData.pericia1 || !formData.pericia2 || !formData.equipamento) {
        setError('Por favor, preencha todos os campos obrigatórios.');
        return;
    }
    console.log("Origem Salva:", formData);
    navigate('/gerenciar-origens');
  };

  // MOCKADO ENQNT O BACK N TA CONECTADO
  const skillOptions = [
    { val: "acrobacia", label: "Acrobacia (Des)" },
    { val: "arcanismo", label: "Arcanismo (Int)" },
    { val: "atletismo", label: "Atletismo (For)" },
    { val: "atuacao", label: "Atuação (Car)" },
    { val: "enganacao", label: "Enganação (Car)" },
    { val: "furtividade", label: "Furtividade (Des)" },
    { val: "historia", label: "História (Int)" },
    { val: "intimidacao", label: "Intimidação (Car)" },
    { val: "intuicao", label: "Intuição (Sab)" },
    { val: "investigacao", label: "Investigação (Int)" },
    { val: "lidar_animais", label: "Lidar com Animais (Sab)" },
    { val: "medicina", label: "Medicina (Sab)" },
    { val: "natureza", label: "Natureza (Int)" },
    { val: "percepcao", label: "Percepção (Sab)" },
    { val: "persuasao", label: "Persuasão (Car)" },
    { val: "prestidigitacao", label: "Prestidigitação (Des)" },
    { val: "religiao", label: "Religião (Int)" },
    { val: "sobrevivencia", label: "Sobrevivência (Sab)" },
  ];

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />

      <main className="container mx-auto p-8">
        <div className="max-w-4xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl">
            
            <h2 className="text-3xl font-semibold text-white mb-2 font-medieval">Criar Origem</h2>
            <hr className="border-t-2 border-red-600 mb-8" />

            <form onSubmit={handleSubmit}>
            
                <div className="grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-6">
    
                    <div className="grid grid-cols-1 gap-2">
                        <label className="text-lg font-medium text-gray-300">Nome da Origem *</label>
                        <input type="text" name="nome" value={formData.nome} onChange={handleChange}
                                placeholder="Ex: Acólito, Criminoso..."
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg placeholder-gray-400" />
                    </div>

                    <div className="grid grid-cols-1 gap-2">
                        <label className="text-lg font-medium text-gray-300">Dinheiro Inicial (PO)</label>
                        <input type="text" name="moedas" value={formData.moedas} onChange={handleChange}
                                placeholder="Ex: 15"
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg placeholder-gray-400" />
                    </div>

                </div> 

                <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label className="block text-lg font-medium text-gray-300 mb-2">Proficiência em Perícia 1 *</label>
                        <select name="pericia1" value={formData.pericia1} onChange={handleChange}
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg appearance-none">
                            <option value="">Selecione...</option>
                            {skillOptions.map(opt => <option key={opt.val + '1'} value={opt.val}>{opt.label}</option>)}
                        </select>
                    </div>
                    <div>
                        <label className="block text-lg font-medium text-gray-300 mb-2">Proficiência em Perícia 2 *</label>
                        <select name="pericia2" value={formData.pericia2} onChange={handleChange}
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg appearance-none">
                            <option value="">Selecione...</option>
                            {skillOptions.map(opt => <option key={opt.val + '2'} value={opt.val}>{opt.label}</option>)}
                        </select>
                    </div>
                </div>
                
                <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label className="block text-lg font-medium text-gray-300 mb-2">Prof. em Ferramenta</label>
                        <select name="ferramenta" value={formData.ferramenta} onChange={handleChange}
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg appearance-none">
                            <option value="">Nenhuma</option>
                            <option value="escolha_artesao">Um tipo de ferramenta de artesão</option>
                            <option value="escolha_instrumento">Um tipo de instrumento musical</option>
                            <option value="escolha_jogo">Um tipo de kit de jogo</option>
                            <option value="kit_disfarce">Kit de Disfarce</option>
                            <option value="kit_falsificacao">Kit de Falsificação</option>
                            <option value="kit_herbalismo">Kit de Herbalismo</option>
                            <option value="kit_veneficio">Kit de Venefício (Envenenador)</option>
                            <option value="ferramentas_ladrao">Ferramentas de Ladrão</option>
                            <option value="ferramentas_navegacao">Ferramentas de Navegador</option>
                        </select>
                    </div>
                    <div>
                        <label className="block text-lg font-medium text-gray-300 mb-2">Prof. em Idioma</label>
                        <select name="idioma" value={formData.idioma} onChange={handleChange}
                                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg appearance-none">
                            <option value="">Nenhum</option>
                            <option value="escolha_um">Um idioma à sua escolha</option>
                            <option value="escolha_dois">Dois idiomas à sua escolha</option>
                            <option value="comum">Comum</option>
                            <option value="anao">Anão</option>
                            <option value="elfico">Élfico</option>
                            <option value="gigante">Gigante</option>
                            <option value="gnomico">Gnômico</option>
                            <option value="goblin">Goblin</option>
                            <option value="halfling">Halfling</option>
                            <option value="orc">Orc</option>
                            <option value="abissal">Abissal</option>
                            <option value="celestial">Celestial</option>
                            <option value="draconico">Dracônico</option>
                            <option value="infernal">Infernal</option>
                            <option value="primordial">Primordial</option>
                            <option value="silvestre">Silvestre</option>
                            <option value="subterraneo">Subterrâneo</option>
                        </select>
                    </div>
                </div>

                <div className="mt-6">
                    <label className="block text-lg font-medium text-gray-300 mb-2">Equipamento Inicial *</label>
                    <textarea name="equipamento" rows={3} value={formData.equipamento} onChange={handleChange}
                            className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg placeholder-gray-400"
                            placeholder="Liste os itens. Ex: Um símbolo sagrado, um livro de preces..."></textarea>
                </div>

                <hr className="border-t-2 border-gray-700 my-8" />
                
                <h3 className="text-2xl font-semibold text-white mb-4">Característica da Origem (Opcional)</h3>
                
                <div className="mb-6">
                    <label className="block text-lg font-medium text-gray-300 mb-2">Nome da Característica</label>
                    <input type="text" name="featureNome" value={formData.featureNome} onChange={handleChange}
                            placeholder="Ex: Refúgio do Fiel"
                            className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg placeholder-gray-400" />
                </div>

                <div>
                    <label className="block text-lg font-medium text-gray-300 mb-2">Descrição da Característica</label>
                    <textarea name="featureDesc" rows={5} value={formData.featureDesc} onChange={handleChange}
                            className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 text-lg placeholder-gray-400"
                            placeholder="Descreva o que a característica faz..."></textarea>
                </div>

                {error && (
                   <div className="mt-6 p-3 rounded bg-red-900/30 border border-red-500/50 text-red-200 text-lg text-center animate-pulse">
                      {error}
                   </div>
                )}

                <div className="mt-10 flex justify-between">
                    <button type="button" 
                            className="px-6 py-2 rounded bg-red-600 hover:bg-red-500 text-white font-semibold transition duration-200 text-lg"
                            onClick={() => navigate('/gerenciar-origens')}>
                        Cancelar
                    </button>
                    <button type="submit" className="px-6 py-2 rounded bg-green-600 hover:bg-green-500 text-white font-semibold transition duration-200 text-lg shadow-lg shadow-green-900/40">
                        Finalizar
                    </button>
                </div>
            </form>
        </div>
      </main>
    </div>
  );
};

export default CreateOrigin;