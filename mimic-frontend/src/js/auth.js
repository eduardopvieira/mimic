document.addEventListener('DOMContentLoaded', () => {

    const loginForm = document.getElementById('login-form');
    const cadastroForm = document.getElementById('cadastro-form');

    // --- LOGIN ---
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const senha = document.getElementById('senha').value;
            const errorElement = document.getElementById('login-error');

            const users = JSON.parse(localStorage.getItem('mimicUsers')) || {};

            if (users[email] && users[email] === senha) {
                errorElement.classList.add('hidden');
                
                window.location.href = '../html/tela-principal.html';
            } else {
                errorElement.textContent = 'Email ou senha inválidos.';
                errorElement.classList.remove('hidden');
            }
        });
    }

    // --- LÓGICA DE CADASTRO ---
    if (cadastroForm) {
        cadastroForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const email = document.getElementById('email').value;
            const senha = document.getElementById('senha').value;
            const confirmarSenha = document.getElementById('confirmar-senha').value;
            const messageElement = document.getElementById('cadastro-message');

            if (senha !== confirmarSenha) {
                messageElement.textContent = 'As senhas não conferem.';
                messageElement.classList.add('text-red-400');
                messageElement.classList.remove('text-green-400', 'hidden');
                return;
            }

            const users = JSON.parse(localStorage.getItem('mimicUsers')) || {};

            if (users[email]) {
                messageElement.textContent = 'Este email já está cadastrado.';
                messageElement.classList.add('text-red-400');
                messageElement.classList.remove('text-green-400', 'hidden');
                return;
            }

            users[email] = senha;
            localStorage.setItem('mimicUsers', JSON.stringify(users));

            messageElement.textContent = 'Cadastro realizado com sucesso! Pode realizar o login.';
            messageElement.classList.add('text-green-400');
            messageElement.classList.remove('text-red-400', 'hidden');

        });
    }

});