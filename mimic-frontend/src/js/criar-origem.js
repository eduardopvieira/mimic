document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('origem-form');
    // Pega a nova mensagem de erro
    const errorMsg = document.getElementById('origem-error-message');
    // Classes de destaque do Tailwind
    const errorHighlightClasses = ['border-red-500', 'ring-2', 'ring-red-500'];

    // Função para limpar os destaques de erro
    function clearErrorStyles() {
        if (errorMsg) errorMsg.classList.add('hidden');
        
        form.querySelectorAll('input, select, textarea').forEach(el => {
            el.classList.remove(...errorHighlightClasses);
        });
    }

    // Função para adicionar destaque de erro
    function addErrorHighlight(element) {
        if (element) {
            element.classList.add(...errorHighlightClasses);
        }
    }

    if (form) {
        form.addEventListener('submit', function(event) {
            // Impede o envio padrão do formulário
            event.preventDefault(); 
            // Limpa erros antigos
            clearErrorStyles();
            
            let isValid = true; // Assume que é válido até provar o contrário
            let errorsFound = false;

            // --- CAMPOS OBRIGATÓRIOS ---

            // 1. Nome da Origem
            const nome = document.getElementById('origem-nome');
            if (nome.value.trim() === '') {
                addErrorHighlight(nome);
                errorsFound = true;
            }

            // 2. Perícias (devem ser selecionadas)
            const pericia1 = document.getElementById('origem-pericia-1');
            const pericia2 = document.getElementById('origem-pericia-2');

            if (pericia1.value === '') {
                addErrorHighlight(pericia1);
                errorsFound = true;
            }
            if (pericia2.value === '') {
                addErrorHighlight(pericia2);
                errorsFound = true;
            }

            // 2.1. Validação extra: Perícias não podem ser iguais
            if (pericia1.value !== '' && pericia1.value === pericia2.value) {
                addErrorHighlight(pericia1);
                addErrorHighlight(pericia2);
                errorsFound = true; 
                // Nota: A mensagem genérica não dirá "são iguais",
                // mas o destaque em ambos ajuda o usuário.
            }

            // 3. Equipamento Inicial
            const equipamento = document.getElementById('origem-equipamento');
            if (equipamento.value.trim() === '') {
                addErrorHighlight(equipamento);
                errorsFound = true;
            }

            // --- BLOCO OPCIONAL (validação condicional) ---

            // 4. Característica da Origem (Opcional, mas deve ser preenchida em bloco)
            const featureNome = document.getElementById('origem-feature-nome');
            const featureDesc = document.getElementById('origem-feature-descricao');

            const nomeVal = featureNome.value.trim();
            const descVal = featureDesc.value.trim();

            if ((nomeVal !== '' && descVal === '') || (nomeVal === '' && descVal !== '')) {
                addErrorHighlight(featureNome);
                addErrorHighlight(featureDesc);
                errorsFound = true;
            }

            // --- CHECAGEM FINAL ---
            if (errorsFound) {
                // Se houver erros, mostra a mensagem de erro genérica
                if (errorMsg) errorMsg.classList.remove('hidden');
                isValid = false;
            }

            // Se tudo estiver OK, redireciona
            if (isValid) {
                console.log("Formulário de origem válido. Enviando...");
                window.location.href = 'tela-principal.html';
            }
        });
    }
});