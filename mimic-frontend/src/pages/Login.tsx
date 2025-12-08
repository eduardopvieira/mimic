import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();
  
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    if (!email || !senha) {
      setError('Por favor, preencha todos os campos.');
      setIsLoading(false);
      return;
    }

    try {
    
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, senha }),
      });

      if (!response.ok) {
        throw new Error('Credenciais inválidas.');
      }

    
      const data = await response.json();

      if (!data.token) {
         throw new Error("Token não recebido do servidor.");
      }

    
      localStorage.setItem('token', data.token);
      localStorage.setItem('usuarioId', data.id);
      localStorage.setItem('usuarioEmail', data.email);

      console.log("Login realizado com sucesso!", data);
      navigate('/gerenciar-magias');

    } catch (err: any) {
      console.error(err);
      setError('Email ou senha incorretos. Tente novamente.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans flex items-center justify-center p-4">
      <div className="max-w-md w-full bg-[#2D2D2D] p-8 sm:p-10 rounded-lg shadow-2xl">
        
        <div className="h-24 w-24 rounded-full bg-white p-2 mx-auto mb-6">
          <img src="/mimic.png" alt="Logo Mimic" className="h-full w-full object-contain" />
        </div>

        <h2 className="text-3xl font-bold text-white text-center mb-2 font-medieval">Login</h2>
        <hr className="border-t-2 border-red-600 mb-8" />

        <form onSubmit={handleSubmit}>
          <div className="space-y-6">
            <div>
              <label className="block text-lg font-medium text-gray-300 mb-2">Email</label>
              <input 
                type="email" 
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg"
                placeholder="seu@email.com" 
                required 
                disabled={isLoading}
              />
            </div>
            
            <div>
              <label className="block text-lg font-medium text-gray-300 mb-2">Senha</label>
              <input 
                type="password" 
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg"
                placeholder="********" 
                required 
                disabled={isLoading}
              />
            </div>
          </div>

          {error && (
            <p className="text-red-400 text-center mt-4 animate-pulse">{error}</p>
          )}

          <div className="mt-8">
            <button 
                type="submit"
                disabled={isLoading}
                className={`w-full p-3 rounded text-white font-semibold transition duration-200 text-lg shadow-lg shadow-red-900/50 ${
                  isLoading ? 'bg-gray-600 cursor-not-allowed' : 'bg-red-600 hover:bg-red-500'
                }`}
            >
                {isLoading ? 'Entrando...' : 'Entrar'}
            </button>
          </div>
        </form>

        <div className="mt-6 text-center">
          <Link to="/cadastrar" className="text-gray-400 hover:text-red-500 hover:underline transition-colors">
            Não tem uma conta? Cadastre-se
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Login;