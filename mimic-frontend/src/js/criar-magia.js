document.addEventListener('DOMContentLoaded', function() {
    
    // --- Sua lógica original para o componente Material ---
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

    // --- Nova Lógica de Validação ---
    const form = document.getElementById('magia-form');

    if (form) {
        form.addEventListener('submit', function(event) {
            // Impede o envio padrão do formulário para validarmos primeiro
            event.preventDefault(); 

            let errors = []; // Array para armazenar mensagens de erro

            // 1. Campos de texto simples
            const nome = document.getElementById('magia-nome').value.trim();
            const alcance = document.getElementById('magia-alcance').value.trim();
            const conjuracao = document.getElementById('magia-conjuracao').value.trim();
            const duracao = document.getElementById('magia-duracao').value.trim();
            const descricao = document.getElementById('magia-descricao').value.trim();
            
            if (nome === '') errors.push('O campo "Nome" é obrigatório.');
            if (alcance === '') errors.push('O campo "Alcance" é obrigatório.');
            if (conjuracao === '') errors.push('O campo "Conjuração" é obrigatório.');
            if (duracao === '') errors.push('O campo "Duração" é obrigatório.');
            if (descricao === '') errors.push('O campo "Descrição da Magia" é obrigatório.');

            // 2. Campo de Nível (Number)
            const nivel = document.getElementById('magia-nivel').value;
            if (nivel === '') { // Input number retorna string vazia se não preenchido
                errors.push('O campo "Nível" é obrigatório.');
            }

            // 3. Componentes (Pelo menos um)
            const componentesMarcados = document.querySelectorAll('input[name="componentes[]"]:checked').length;
            if (componentesMarcados === 0) {
                errors.push('A magia deve ter pelo menos um componente (V, S ou M).');
            }

            // 4. Componente Material (Condicional)
            const compM_estaMarcado = document.getElementById('comp-m').checked;
            const materialDesc = document.getElementById('magia-material-descricao').value.trim();

            if (compM_estaMarcado && materialDesc === '') {
                errors.push('A "Descrição do Componente Material" é obrigatória pois "M" está marcado.');
            }

            // --- Checagem Final ---
            if (errors.length > 0) {
                // Se houver erros, mostra um alerta com todos eles
                alert("Por favor, corrija os seguintes erros:\n\n" + errors.join("\n"));
            } else {
                console.log("Formulário válido. Enviando...");
                window.location.href = '../html/tela-principal.html'; 
            }
        });
    }
});