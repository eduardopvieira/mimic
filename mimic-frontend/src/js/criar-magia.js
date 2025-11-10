document.addEventListener('DOMContentLoaded', function() {
    
    // --- Lógica do Componente Material (Sua lógica original) ---
    const checkboxM = document.getElementById('comp-m');
    const container = document.getElementById('material-container');
    
    if (checkboxM && container) {
        checkboxM.addEventListener('change', function() {
            if (this.checked) {
                container.style.display = 'block';
            } else {
                container.style.display = 'none';
                
                const materialDesc = document.getElementById('magia-material-descricao');
                if (materialDesc) {
                    materialDesc.value = ''; // Limpa o campo ao desmarcar
                }
            }
        });
    }

    // --- Nova Lógica de Validação com Destaque ---
    
    const form = document.getElementById('magia-form');
    const errorMsg = document.getElementById('magia-error-message');
    // Classes de destaque do Tailwind
    const errorHighlightClasses = ['border-red-500', 'ring-2', 'ring-red-500'];

    // Função para limpar os destaques de erro
    function clearErrorStyles() {
        if (errorMsg) errorMsg.classList.add('hidden');
        
        form.querySelectorAll('input, select, textarea, label').forEach(el => {
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
            
            let errorsFound = false;

            // --- CAMPOS OBRIGATÓRIOS ---

            // 1. Campos de texto
            const camposTexto = ['magia-nome', 'magia-alcance', 'magia-conjuracao', 'magia-duracao', 'magia-descricao'];
            camposTexto.forEach(id => {
                const input = document.getElementById(id);
                if (input.value.trim() === '') {
                    addErrorHighlight(input);
                    errorsFound = true;
                }
            });

            const nivel = document.getElementById('magia-nivel');
            if (nivel.value === '') { // Input number retorna string vazia se não preenchido
                addErrorHighlight(nivel);
                errorsFound = true;
            }

            // 3. Componentes (Pelo menos um)
            const componentesMarcados = document.querySelectorAll('input[name="componentes[]"]:checked').length;
            if (componentesMarcados === 0) {
                // Destaca a label "Componentes" que adicionamos o ID
                const componentesLabel = document.getElementById('componentes-label');
                addErrorHighlight(componentesLabel);
                errorsFound = true;
            }

            const compM_estaMarcado = document.getElementById('comp-m').checked;
            const materialDescInput = document.getElementById('magia-material-descricao');
            
            if (compM_estaMarcado && materialDescInput.value.trim() === '') {
                addErrorHighlight(materialDescInput);
                errorsFound = true;
            }

            // --- CHECAGEM FINAL ---
            if (errorsFound) {
                // Se houver erros, mostra a mensagem de erro genérica
                if (errorMsg) errorMsg.classList.remove('hidden');
            } else {
                // Se não houver erros, envia o formulário (ou redireciona)
                console.log("Formulário de magia válido. Enviando...");
                // Redireciona para a tela principal
                window.location.href = 'tela-principal.html'; 
            }
        });
    }
});