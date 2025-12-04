import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Register = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: '',
    password: '',
    confirmPassword: ''
  });
  
  const [message, setMessage] = useState({ text: '', type: '' }); // type: 'error' | 'success'

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setMessage({ text: '', type: '' });

    if (formData.password !== formData.confirmPassword) {
      setMessage({ text: 'As senhas não coincidem.', type: 'error' });
      return;
    }

    if (formData.password.length < 6) {
        setMessage({ text: 'A senha deve ter pelo menos 6 caracteres.', type: 'error' });
        return;
    }

    console.log("Cadastrando:", formData.email);
    setMessage({ text: 'Cadastro realizado com sucesso! Redirecionando...', type: 'success' });
    
    setTimeout(() => {
        navigate('/');
    }, 1500);
  };

  return (
    <div className="bg-[#1A1A1A] text-gray-200 min-h-screen font-sans flex items-center justify-center p-4">
      <div className="max-w-md w-full bg-[#2D2D2D] p-8 sm:p-10 rounded-lg shadow-2xl">
        
        <div className="h-24 w-24 rounded-full bg-white p-2 mx-auto mb-6">
          <img src="/mimic.png" alt="Logo Mimic" className="h-full w-full object-contain" />
        </div>

        <h2 className="text-3xl font-bold text-white text-center mb-2 font-medieval">Cadastrar</h2>
        <hr className="border-t-2 border-red-600 mb-8" />

        <form onSubmit={handleSubmit}>
          <div className="space-y-6">
            <div>
              <label className="block text-lg font-medium text-gray-300 mb-2">Email</label>
              <input 
                type="email" name="email" value={formData.email} onChange={handleChange}
                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg"
                placeholder="seu@email.com" required 
              />
            </div>
            
            <div>
              <label className="block text-lg font-medium text-gray-300 mb-2">Senha</label>
              <input 
                type="password" name="password" value={formData.password} onChange={handleChange}
                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg"
                placeholder="********" required 
              />
            </div>

            <div>
              <label className="block text-lg font-medium text-gray-300 mb-2">Confirmar Senha</label>
              <input 
                type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange}
                className="w-full p-3 rounded bg-[#444444] border border-gray-600 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500 text-lg"
                placeholder="********" required 
              />
            </div>
          </div>

          {message.text && (
            <p className={`text-center mt-4 text-lg ${message.type === 'error' ? 'text-red-400' : 'text-green-400'}`}>
                {message.text}
            </p>
          )}

          <div className="mt-8">
            <button 
                type="submit"
                className="w-full p-3 rounded bg-red-600 hover:bg-red-500 text-white font-semibold transition duration-200 text-lg shadow-lg shadow-red-900/50"
            >
                Finalizar Cadastro
            </button>
          </div>
        </form>

        <div className="mt-6 text-center">
          <Link to="/" className="text-gray-400 hover:text-red-500 hover:underline transition-colors">
            Já tem uma conta? Faça o login
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Register;