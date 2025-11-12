document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('origem-form');
    const errorMsg = document.getElementById('origem-error-message');
    const errorHighlightClasses = ['border-red-500', 'ring-2', 'ring-red-500'];

    function clearErrorStyles() {
        if (errorMsg) errorMsg.classList.add('hidden');
        
        form.querySelectorAll('input, select, textarea').forEach(el => {
            el.classList.remove(...errorHighlightClasses);
        });
    }

    function addErrorHighlight(element) {
        if (element) {
            element.classList.add(...errorHighlightClasses);
        }
    }

    if (form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); 
            clearErrorStyles();
            
            let isValid = true;
            let errorsFound = false;

            const nome = document.getElementById('origem-nome');
            if (nome.value.trim() === '') {
                addErrorHighlight(nome);
                errorsFound = true;
            }

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

            if (pericia1.value !== '' && pericia1.value === pericia2.value) {
                addErrorHighlight(pericia1);
                addErrorHighlight(pericia2);
                errorsFound = true; 
            }

            const equipamento = document.getElementById('origem-equipamento');
            if (equipamento.value.trim() === '') {
                addErrorHighlight(equipamento);
                errorsFound = true;
            }

            const featureNome = document.getElementById('origem-feature-nome');
            const featureDesc = document.getElementById('origem-feature-descricao');

            const nomeVal = featureNome.value.trim();
            const descVal = featureDesc.value.trim();

            if ((nomeVal !== '' && descVal === '') || (nomeVal === '' && descVal !== '')) {
                addErrorHighlight(featureNome);
                addErrorHighlight(featureDesc);
                errorsFound = true;
            }

            if (errorsFound) {
                if (errorMsg) errorMsg.classList.remove('hidden');
                isValid = false;
            }

            if (isValid) {
                console.log("Formulário de origem válido. Enviando...");
                window.location.href = 'tela-principal.html';
            }
        });
    }
});