import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();
  
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    // AQUI VEM O SPRING QND FUNCIONAR
    if (!email || !password) {
      setError('Por favor, preencha todos os campos.');
      return;
    }

    console.log("Logando com:", email);
    navigate('/gerenciar-personagens');
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
              <label htmlFor="email" className="block text-lg font-medium text-gray-300 mb-2">Email</label>
              <input 
                type="email" 
                id="email" 
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg"
                placeholder="seu@email.com" 
                required 
              />
            </div>
            
            <div>
              <label htmlFor="senha" className="block text-lg font-medium text-gray-300 mb-2">Senha</label>
              <input 
                type="password" 
                id="senha" 
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg"
                placeholder="********" 
                required 
              />
            </div>
          </div>

          {error && (
            <p className="text-red-400 text-center mt-4 animate-pulse">{error}</p>
          )}

          <div className="mt-8">
            <button 
                type="submit"
                className="w-full p-3 rounded bg-red-600 hover:bg-red-500 text-white font-semibold transition duration-200 text-lg shadow-lg shadow-red-900/50"
            >
                Entrar
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