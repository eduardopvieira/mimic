document.addEventListener('DOMContentLoaded', function() {
    
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
                    materialDesc.value = '';
                }
            }
        });
    }
    
    const form = document.getElementById('magia-form');
    const errorMsg = document.getElementById('magia-error-message');
    const errorHighlightClasses = ['border-red-500', 'ring-2', 'ring-red-500'];

    function clearErrorStyles() {
        if (errorMsg) errorMsg.classList.add('hidden');
        
        form.querySelectorAll('input, select, textarea, label').forEach(el => {
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
            
            let errorsFound = false;

            const camposTexto = ['magia-nome', 'magia-alcance', 'magia-conjuracao', 'magia-duracao', 'magia-descricao'];
            camposTexto.forEach(id => {
                const input = document.getElementById(id);
                if (input.value.trim() === '') {
                    addErrorHighlight(input);
                    errorsFound = true;
                }
            });

            const nivel = document.getElementById('magia-nivel');
            if (nivel.value === '') {
                addErrorHighlight(nivel);
                errorsFound = true;
            }

            const componentesMarcados = document.querySelectorAll('input[name="componentes[]"]:checked').length;
            if (componentesMarcados === 0) {
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

            if (errorsFound) {
                if (errorMsg) errorMsg.classList.remove('hidden');
            } else {
                console.log("Formulário de magia válido. Enviando...");
                window.location.href = 'tela-principal.html'; 
            }
        });
    }
});