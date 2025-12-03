import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/layout/Header';

import type { MagiaDTO } from '../types/Magia';
import { EscolaDeMagia } from '../types/Magia';
import { MagiaService } from '../services/MagiaService';

const CreateSpell = () => {
  const navigate = useNavigate();
  const [error, setError] = useState('');

  // Estado inicial do formulário
  const [formData, setFormData] = useState({
    nome: '',
    alcance: '',
    conjuracao: '', // Mapeia para 'tempoConjuracao'
    nivel: 0,       // Mapeia para 'circulo'
    escola: '',     // Armazena a string selecionada no dropdown
    duracao: '',
    components: [] as string[], // Array temporário para controlar os checkboxes [V, S, M]
    materialDesc: '',
    isRitual: false,
    isConcentration: false,
    descricao: ''
  });

  // Atualiza inputs de Texto e o Select de Escola
  // Note que adicionei HTMLSelectElement ao tipo do evento
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // Atualiza checkboxes simples (Ritual e Concentração)
  const handleCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, checked } = e.target;
    setFormData(prev => ({ ...prev, [name]: checked }));
  };

  // Lógica para adicionar/remover V, S, M do array
  const handleComponentToggle = (val: string) => {
    setFormData(prev => {
      const hasComponent = prev.components.includes(val);
      const newComponents = hasComponent
        ? prev.components.filter(c => c !== val) // Remove se já existe
        : [...prev.components, val]; // Adiciona se não existe

      return { ...prev, components: newComponents };
    });
  };

  // Função de Envio
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    // Validação Básica
    if (!formData.nome || !formData.alcance || !formData.conjuracao || !formData.duracao || !formData.descricao) {
      setError('Por favor, preencha todos os campos obrigatórios.');
      return;
    }

    // 1. Formatar a String de Componentes para o padrão do D&D
    // Exemplo final esperado: "V, S, M (pó de diamante)"
    let componentesString = '';
    
    // Filtra componentes que não são M para o início da string
    const basicComps = formData.components.filter(c => c !== 'M').join(', ');
    
    if (basicComps) {
        componentesString += basicComps;
    }

    // Se tiver componente Material (M), adiciona ele com a descrição
    if (formData.components.includes('M')) {
        if (componentesString) componentesString += ', '; // Vírgula se já tiver V ou S antes
        
        // Se o usuário escreveu descrição, coloca entre parênteses. Se não, só põe "M".
        if (formData.materialDesc) {
            componentesString += `M (${formData.materialDesc})`;
        } else {
            componentesString += 'M';
        }
    }

    // 2. Montar o Objeto DTO Tipado
    const novaMagia: MagiaDTO = {
        nome: formData.nome,
        circulo: formData.nivel,
        // Converte a string vazia ou valor selecionado para o Enum ou null
        escola: formData.escola ? (formData.escola as EscolaDeMagia) : null,
        tempoConjuracao: formData.conjuracao,
        alcance: formData.alcance,
        componentes: componentesString, // A string formatada acima
        duracao: formData.duracao,
        isConcentracao: formData.isConcentration,
        isRitual: formData.isRitual,
        formulaDano: null, // Futuramente você pode adicionar inputs para isso
        tipoDano: null,    // Futuramente você pode adicionar inputs para isso
        descricao: formData.descricao,
        usuarioId: 1 // TODO: Alterar para pegar o ID do usuário logado (Contexto/Session)
    };

    try {
        console.log("Enviando DTO:", novaMagia); // Debug
        await MagiaService.create(novaMagia);
        
        alert("Magia criada com sucesso!");
        navigate('/gerenciar-magias');
    } catch (err) {
        console.error(err);
        setError("Erro ao salvar a magia. Verifique se o backend está rodando.");
    }
  };

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans">
      <Header />

      <main className="container mx-auto p-8">
        <div className="max-w-4xl mx-auto bg-[#2D2D2D] p-6 sm:p-8 rounded-lg shadow-2xl">

          <h2 className="text-3xl font-semibold text-white mb-2 font-medieval">Criar Magia</h2>
          <hr className="border-t-2 border-red-600 mb-8" />

          <form onSubmit={handleSubmit}>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-6">

              {/* COLUNA DA ESQUERDA */}
              <div className="grid grid-cols-[auto_1fr] gap-6 items-center">
                
                <label className="text-lg font-medium text-gray-300">Nome</label>
                <input 
                  type="text" name="nome" value={formData.nome} onChange={handleChange}
                  className="p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg w-full"
                  placeholder="Ex: Bola de Fogo" 
                />

                <label className="text-lg font-medium text-gray-300">Alcance</label>
                <input 
                  type="text" name="alcance" value={formData.alcance} onChange={handleChange}
                  className="p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg w-full"
                  placeholder="Ex: 30m / Toque" 
                />

                <label className="text-lg font-medium text-gray-300">Conjuração</label>
                <input 
                  type="text" name="conjuracao" value={formData.conjuracao} onChange={handleChange}
                  className="p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg w-full"
                  placeholder="Ex: 1 Ação" 
                />
              </div>

              {/* COLUNA DA DIREITA */}
              <div className="grid grid-cols-[auto_1fr] gap-6 items-center">
                
                <label className="text-lg font-medium text-gray-300">Nível</label>
                <input 
                  type="number" name="nivel" min="0" max="9" 
                  value={formData.nivel} 
                  onChange={(e) => setFormData({...formData, nivel: parseInt(e.target.value)})}
                  className="p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg w-full no-spinner"
                  placeholder="0 a 9" 
                />

                <label className="text-lg font-medium text-gray-300">Duração</label>
                <input 
                  type="text" name="duracao" value={formData.duracao} onChange={handleChange}
                  className="p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg w-full"
                  placeholder="Ex: Instantânea / 1 min" 
                />

                <label className="text-lg font-medium text-gray-300">Componentes</label>
                <div className="flex items-center space-x-6">
                  {['V', 'S', 'M'].map((comp) => (
                    <div key={comp} className="flex items-center cursor-pointer">
                      <input 
                        id={`comp-${comp}`} 
                        type="checkbox" 
                        checked={formData.components.includes(comp)}
                        onChange={() => handleComponentToggle(comp)}
                        className="h-5 w-5 rounded bg-[#444444] text-red-600 border-gray-600 focus:ring-2 focus:ring-red-500 focus:ring-offset-2 focus:ring-offset-[#2D2D2D] cursor-pointer" 
                      />
                      <label htmlFor={`comp-${comp}`} className="ml-2 text-lg text-gray-200 cursor-pointer">{comp}</label>
                    </div>
                  ))}
                </div>

                <label className="text-lg font-medium text-gray-300">Tags</label>
                <div className="flex items-center space-x-6">
                  <div className="flex items-center">
                    <input 
                      id="ritual" name="isRitual" type="checkbox" 
                      checked={formData.isRitual} onChange={handleCheck}
                      className="h-5 w-5 rounded bg-[#444444] text-red-600 border-gray-600 focus:ring-2 focus:ring-red-500 focus:ring-offset-2 focus:ring-offset-[#2D2D2D] cursor-pointer" 
                    />
                    <label htmlFor="ritual" className="ml-2 text-lg text-gray-200 cursor-pointer">Ritual</label>
                  </div>
                  <div className="flex items-center">
                    <input 
                      id="concentracao" name="isConcentration" type="checkbox" 
                      checked={formData.isConcentration} onChange={handleCheck}
                      className="h-5 w-5 rounded bg-[#444444] text-red-600 border-gray-600 focus:ring-2 focus:ring-red-500 focus:ring-offset-2 focus:ring-offset-[#2D2D2D] cursor-pointer" 
                    />
                    <label htmlFor="concentracao" className="ml-2 text-lg text-gray-200 cursor-pointer">Concentração</label>
                  </div>
                </div>
              </div>

            </div>

            {/* CONDICIONAL: DESCRIÇÃO DO MATERIAL */}
            {/* Só aparece se 'M' estiver incluso no array de components */}
            {formData.components.includes('M') && (
              <div className="mt-6 animate-fade-in">
                <label className="block text-lg font-medium text-gray-300 mb-2">
                    Descrição do Componente Material
                </label>
                <input 
                  type="text" name="materialDesc" value={formData.materialDesc} onChange={handleChange}
                  className="w-full p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg"
                  placeholder="Ex: um galho de visco / pó de diamante (50 PO)" 
                />
              </div>
            )}

            {/* DESCRIÇÃO DA MAGIA */}
            <div className="mt-6">
              <label className="block text-lg font-medium text-gray-300 mb-2">Descrição da Magia</label>
              <textarea 
                name="descricao" rows={8} value={formData.descricao} onChange={handleChange}
                className="w-full p-2 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg resize-y"
                placeholder="Inclua o efeito da magia aqui..." 
              />
            </div>

            {/* MENSAGEM DE ERRO */}
            {error && (
              <div className="mt-6 p-3 rounded bg-red-900/30 border border-red-500/50 text-red-200 text-lg text-center animate-pulse">
                {error}
              </div>
            )}

            <div className="mt-10 flex justify-between">
              <button 
                type="button"
                onClick={() => navigate('/gerenciar-magias')}
                className="px-6 py-2 rounded bg-red-600 hover:bg-red-500 text-white font-semibold transition duration-200 text-lg"
              >
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

export default CreateSpell;