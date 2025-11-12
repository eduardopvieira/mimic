document.addEventListener('DOMContentLoaded', function () {

    const etapa1 = document.getElementById('etapa-1');
    const etapa2 = document.getElementById('etapa-2');
    const etapa3 = document.getElementById('etapa-3');
    const etapa4 = document.getElementById('etapa-4');
    const etapa5 = document.getElementById('etapa-5');
    const etapa6 = document.getElementById('etapa-6');
    const etapa7 = document.getElementById('etapa-7');

    const btnProximo1 = document.getElementById('btn-proximo-1');
    const btnProximo2 = document.getElementById('btn-proximo-2');
    const btnProximo3 = document.getElementById('btn-proximo-3');
    const btnProximo4 = document.getElementById('btn-proximo-4');
    const btnProximo5 = document.getElementById('btn-proximo-5');
    const btnProximo6 = document.getElementById('btn-proximo-6');

    const btnVoltar2 = document.getElementById('btn-voltar-2');
    const btnVoltar3 = document.getElementById('btn-voltar-3');
    const btnVoltar4 = document.getElementById('btn-voltar-4');
    const btnVoltar5 = document.getElementById('btn-voltar-5');
    const btnVoltar6 = document.getElementById('btn-voltar-6');
    const btnVoltar7 = document.getElementById('btn-voltar-7');

    const form = document.getElementById('criatura-form');

    const errorEtapa1 = document.getElementById('error-etapa-1');
    const errorEtapa2 = document.getElementById('error-etapa-2');
    const errorEtapa3 = document.getElementById('error-etapa-3');
    const errorEtapa4 = document.getElementById('error-etapa-4');
    const errorEtapa5 = document.getElementById('error-etapa-5');
    const errorEtapa6 = document.getElementById('error-etapa-6');
    const errorFinal = document.getElementById('error-final');

    const errorHighlightClasses = ['border-red-500', 'ring-2', 'ring-red-500'];

    function clearErrorStyles(containerElement) {
        if (!containerElement) return;

        const errorMsg = containerElement.querySelector('.bg-red-800');
        if (errorMsg && !errorMsg.id.includes('final')) errorMsg.classList.add('hidden');
        if (errorFinal) errorFinal.classList.add('hidden'); 

        containerElement.querySelectorAll('input, select, textarea').forEach(el => {
            el.classList.remove(...errorHighlightClasses);
        });
    }

    
    function addErrorHighlight(element) {
        if (element) {
            element.classList.add(...errorHighlightClasses);
        }
    }

    
    const stepper = {
        circles: [
            document.getElementById('stepper-circle-1'),
            document.getElementById('stepper-circle-2'),
            document.getElementById('stepper-circle-3'),
            document.getElementById('stepper-circle-4'),
            document.getElementById('stepper-circle-5'),
            document.getElementById('stepper-circle-6'),
            document.getElementById('stepper-circle-7')
        ],
        texts: [
            document.getElementById('stepper-text-1'),
            document.getElementById('stepper-text-2'),
            document.getElementById('stepper-text-3'),
            document.getElementById('stepper-text-4'),
            document.getElementById('stepper-text-5'),
            document.getElementById('stepper-text-6'),
            document.getElementById('stepper-text-7')
        ],
        lines: [
            document.getElementById('stepper-line-1'),
            document.getElementById('stepper-line-2'),
            document.getElementById('stepper-line-3'),
            document.getElementById('stepper-line-4'),
            document.getElementById('stepper-line-5'),
            document.getElementById('stepper-line-6')
        ]
    };

    

    
    btnProximo1.addEventListener('click', function () {
        clearErrorStyles(etapa1);
        const isValid = validarEtapa1();
        if (!isValid) {
            if (errorEtapa1) errorEtapa1.classList.remove('hidden');
            return;
        }

        etapa1.classList.add('hidden');
        etapa2.classList.remove('hidden');

        stepper.circles[0].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[0].classList.add('bg-red-600');
        stepper.texts[0].classList.remove('text-white');
        stepper.texts[0].classList.add('text-red-400');
        stepper.lines[0].classList.remove('border-gray-600');
        stepper.lines[0].classList.add('border-red-600');

        stepper.circles[1].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[1].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[1].classList.remove('text-gray-500');
        stepper.texts[1].classList.add('text-white');
    });

    btnVoltar2.addEventListener('click', function () {
        etapa2.classList.add('hidden');
        etapa1.classList.remove('hidden');

        stepper.circles[0].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[0].classList.remove('bg-red-600');
        stepper.texts[0].classList.add('text-white');
        stepper.texts[0].classList.remove('text-red-400');
        stepper.lines[0].classList.add('border-gray-600');
        stepper.lines[0].classList.remove('border-red-600');

        stepper.circles[1].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[1].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[1].classList.add('text-gray-500');
        stepper.texts[1].classList.remove('text-white');
    });

    
    btnProximo2.addEventListener('click', function () {
        clearErrorStyles(etapa2);
        const isValid = validarEtapa2();
        if (!isValid) {
            if (errorEtapa2) errorEtapa2.classList.remove('hidden');
            return;
        }

        etapa2.classList.add('hidden');
        etapa3.classList.remove('hidden');

        stepper.circles[1].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[1].classList.add('bg-red-600');
        stepper.texts[1].classList.remove('text-white');
        stepper.texts[1].classList.add('text-red-400');
        stepper.lines[1].classList.remove('border-gray-600');
        stepper.lines[1].classList.add('border-red-600');

        stepper.circles[2].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[2].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[2].classList.remove('text-gray-500');
        stepper.texts[2].classList.add('text-white');
    });

    btnVoltar3.addEventListener('click', function () {
        etapa3.classList.add('hidden');
        etapa2.classList.remove('hidden');

        stepper.circles[1].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[1].classList.remove('bg-red-600');
        stepper.texts[1].classList.add('text-white');
        stepper.texts[1].classList.remove('text-red-400');
        stepper.lines[1].classList.add('border-gray-600');
        stepper.lines[1].classList.remove('border-red-600');

        stepper.circles[2].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[2].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[2].classList.add('text-gray-500');
        stepper.texts[2].classList.remove('text-white');
    });

    
    btnProximo3.addEventListener('click', function () {
        clearErrorStyles(etapa3);
        const isValid = validarEtapa3();
        if (!isValid) {
            if (errorEtapa3) errorEtapa3.classList.remove('hidden');
            return;
        }

        etapa3.classList.add('hidden');
        etapa4.classList.remove('hidden');

        stepper.circles[2].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[2].classList.add('bg-red-600');
        stepper.texts[2].classList.remove('text-white');
        stepper.texts[2].classList.add('text-red-400');
        stepper.lines[2].classList.remove('border-gray-600');
        stepper.lines[2].classList.add('border-red-600');

        stepper.circles[3].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[3].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[3].classList.remove('text-gray-500');
        stepper.texts[3].classList.add('text-white');
    });

    btnVoltar4.addEventListener('click', function () {
        etapa4.classList.add('hidden');
        etapa3.classList.remove('hidden');

        stepper.circles[2].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[2].classList.remove('bg-red-600');
        stepper.texts[2].classList.add('text-white');
        stepper.texts[2].classList.remove('text-red-400');
        stepper.lines[2].classList.add('border-gray-600');
        stepper.lines[2].classList.remove('border-red-600');

        stepper.circles[3].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[3].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[3].classList.add('text-gray-500');
        stepper.texts[3].classList.remove('text-white');
    });

    
    btnProximo4.addEventListener('click', function () {
        clearErrorStyles(etapa4);
        const isValid = validarEtapa4();
        if (!isValid) {
            if (errorEtapa4) errorEtapa4.classList.remove('hidden');
            return;
        }

        etapa4.classList.add('hidden');
        etapa5.classList.remove('hidden');

        stepper.circles[3].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[3].classList.add('bg-red-600');
        stepper.texts[3].classList.remove('text-white');
        stepper.texts[3].classList.add('text-red-400');
        stepper.lines[3].classList.remove('border-gray-600');
        stepper.lines[3].classList.add('border-red-600');

        stepper.circles[4].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[4].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[4].classList.remove('text-gray-500');
        stepper.texts[4].classList.add('text-white');
    });

    btnVoltar5.addEventListener('click', function () {
        etapa5.classList.add('hidden');
        etapa4.classList.remove('hidden');

        stepper.circles[3].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[3].classList.remove('bg-red-600');
        stepper.texts[3].classList.add('text-white');
        stepper.texts[3].classList.remove('text-red-400');
        stepper.lines[3].classList.add('border-gray-600');
        stepper.lines[3].classList.remove('border-red-600');

        stepper.circles[4].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[4].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[4].classList.add('text-gray-500');
        stepper.texts[4].classList.remove('text-white');
    });

    
    btnProximo5.addEventListener('click', function () {
        clearErrorStyles(etapa5);
        const isValid = validarEtapa5();
        if (!isValid) {
            if (errorEtapa5) errorEtapa5.classList.remove('hidden');
            return;
        }

        etapa5.classList.add('hidden');
        etapa6.classList.remove('hidden');

        stepper.circles[4].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[4].classList.add('bg-red-600');
        stepper.texts[4].classList.remove('text-white');
        stepper.texts[4].classList.add('text-red-400');
        stepper.lines[4].classList.remove('border-gray-600');
        stepper.lines[4].classList.add('border-red-600');

        stepper.circles[5].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[5].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[5].classList.remove('text-gray-500');
        stepper.texts[5].classList.add('text-white');
    });

    btnVoltar6.addEventListener('click', function () {
        etapa6.classList.add('hidden');
        etapa5.classList.remove('hidden');

        stepper.circles[4].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[4].classList.remove('bg-red-600');
        stepper.texts[4].classList.add('text-white');
        stepper.texts[4].classList.remove('text-red-400');
        stepper.lines[4].classList.add('border-gray-600');
        stepper.lines[4].classList.remove('border-red-600');

        stepper.circles[5].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[5].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[5].classList.add('text-gray-500');
        stepper.texts[5].classList.remove('text-white');
    });

    
    btnProximo6.addEventListener('click', function () {
        clearErrorStyles(etapa6);
        const isValid = validarEtapa6();
        if (!isValid) {
            if (errorEtapa6) errorEtapa6.classList.remove('hidden');
            return;
        }

        etapa6.classList.add('hidden');
        etapa7.classList.remove('hidden');

        stepper.circles[5].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[5].classList.add('bg-red-600');
        stepper.texts[5].classList.remove('text-white');
        stepper.texts[5].classList.add('text-red-400');
        stepper.lines[5].classList.remove('border-gray-600');
        stepper.lines[5].classList.add('border-red-600');

        stepper.circles[6].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[6].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[6].classList.remove('text-gray-500');
        stepper.texts[6].classList.add('text-white');
    });

    btnVoltar7.addEventListener('click', function () {
        etapa7.classList.add('hidden');
        etapa6.classList.remove('hidden');

        stepper.circles[5].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[5].classList.remove('bg-red-600');
        stepper.texts[5].classList.add('text-white');
        stepper.texts[5].classList.remove('text-red-400');
        stepper.lines[5].classList.add('border-gray-600');
        stepper.lines[5].classList.remove('border-red-600');

        stepper.circles[6].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[6].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[6].classList.add('text-gray-500');
        stepper.texts[6].classList.remove('text-white');
    });

    
    function calcularModificador(valor) {
        return Math.floor((valor - 10) / 2);
    }

    function atualizarModificadorUI(inputElement, spanElement) {
        const valor = parseInt(inputElement.value, 10) || 10;
        const mod = calcularModificador(valor);

        if (spanElement) {
            spanElement.textContent = `Modificador: ${mod >= 0 ? '+' : ''}${mod}`;
        }
    }

    const atributos = ['for', 'des', 'con', 'int', 'sab', 'car'];
    atributos.forEach(sigla => {
        const input = document.getElementById(`atr-${sigla}`);
        const span = input ? input.nextElementSibling : null;

        if (input && span) {
            input.addEventListener('input', () => {
                atualizarModificadorUI(input, span);
            });
            input.addEventListener('change', () => {
                atualizarModificadorUI(input, span);
            });
            atualizarModificadorUI(input, span);
        }
    });

    
    function inicializarRepetidorCriatura(addBtnId, containerId, entryClass, prefix) {

        const addBtn = document.getElementById(addBtnId);
        const container = document.getElementById(containerId);

        if (!addBtn) {
            console.error(`Botão de Adicionar não encontrado: ${addBtnId}`);
            return;
        }
        if (!container) {
            console.error(`Container não encontrado: ${containerId}`);
            return;
        }

        let entryCounter = container.getElementsByClassName(entryClass).length;

        addBtn.addEventListener('click', function () {
            const entryMolde = container.querySelector(`.${entryClass}`);
            if (!entryMolde) {
                console.error(`Molde com classe ${entryClass} não encontrado.`);
                return;
            }

            const newEntry = entryMolde.cloneNode(true);
            entryCounter++;

            const newNameInput = newEntry.querySelector('input[type="text"]');
            const newDescTextarea = newEntry.querySelector('textarea');
            const newNameLabel = newEntry.querySelector(`label[for^="${prefix}-nome"]`);
            const newDescLabel = newEntry.querySelector(`label[for^="${prefix}-desc"]`);

            if (newNameInput) newNameInput.id = `${prefix}-nome-${entryCounter}`;
            if (newDescTextarea) newDescTextarea.id = `${prefix}-desc-${entryCounter}`;
            if (newNameLabel) newNameLabel.setAttribute('for', `${prefix}-nome-${entryCounter}`);
            if (newDescLabel) newDescLabel.setAttribute('for', `${prefix}-desc-${entryCounter}`);

            if (newNameInput) newNameInput.value = '';
            if (newDescTextarea) newDescTextarea.value = '';

            const removeBtn = newEntry.querySelector('.remover-btn');
            if (removeBtn) {
                removeBtn.addEventListener('click', function () {
                    newEntry.remove();
                });
            }

            container.appendChild(newEntry);
        });

        const firstRemoveBtn = container.querySelector(`.${entryClass} .remover-btn`);
        if (firstRemoveBtn) {
            firstRemoveBtn.addEventListener('click', function () {
                if (container.getElementsByClassName(entryClass).length > 1) {
                    firstRemoveBtn.closest(`.${entryClass}`).remove();
                } else {
                    const firstInput = container.querySelector(`.${entryClass} input[type="text"]`);
                    const firstTextarea = container.querySelector(`.${entryClass} textarea`);
                    if (firstInput) firstInput.value = '';
                    if (firstTextarea) firstTextarea.value = '';
                }
            });
        }
    }

    inicializarRepetidorCriatura('add-habilidade', 'habilidades-container', 'habilidade-entry', 'habilidade');
    inicializarRepetidorCriatura('add-acao', 'acoes-container', 'acao-entry', 'acao');


    

    function validarEtapa1() {
        let isValid = true;
        const nome = document.getElementById('nome');
        const tamanho = document.getElementById('tamanho');
        const tipo = document.getElementById('tipo');
        const alinhamento = document.getElementById('alinhamento');

        if (nome.value.trim() === '') { addErrorHighlight(nome); isValid = false; }
        if (tamanho.value === '') { addErrorHighlight(tamanho); isValid = false; }
        if (tipo.value.trim() === '') { addErrorHighlight(tipo); isValid = false; }
        if (alinhamento.value === '') { addErrorHighlight(alinhamento); isValid = false; }

        return isValid;
    }

    function validarEtapa2() {
        let isValid = true;
        const ca = document.getElementById('ca');
        const pv = document.getElementById('pv');
        const deslBase = document.getElementById('desl-base');

        if (ca.value.trim() === '') { addErrorHighlight(ca); isValid = false; }
        if (pv.value.trim() === '') { addErrorHighlight(pv); isValid = false; }
        if (deslBase.value.trim() === '') { addErrorHighlight(deslBase); isValid = false; }

        return isValid;
    }

    function validarEtapa3() {
        let isValid = true;
        const atributos = ['for', 'des', 'con', 'int', 'sab', 'car'];
        for (const atr of atributos) {
            const input = document.getElementById(`atr-${atr}`);
            if (input.value.trim() === '') {
                addErrorHighlight(input);
                isValid = false;
            }
        }
        return isValid;
    }

    function validarEtapa4() {
        let isValid = true;
        const sentidos = document.getElementById('sentidos');
        const idiomas = document.getElementById('idiomas');
        const nd = document.getElementById('nd');

        if (sentidos.value.trim() === '') { addErrorHighlight(sentidos); isValid = false; }
        if (idiomas.value.trim() === '') { addErrorHighlight(idiomas); isValid = false; }
        if (nd.value.trim() === '') { addErrorHighlight(nd); isValid = false; }

        return isValid;
    }

    function validarEtapa5() {
        let isValid = true;
        const habilidades = document.querySelectorAll('.habilidade-entry');

        habilidades.forEach((habilidade) => {
            const nomeInput = habilidade.querySelector('input[name="habilidade-nome[]"]');
            const descInput = habilidade.querySelector('textarea[name="habilidade-desc[]"]');
            const nome = nomeInput.value.trim();
            const desc = descInput.value.trim();

            
            if ((nome !== '' && desc === '') || (nome === '' && desc !== '')) {
                addErrorHighlight(nomeInput);
                addErrorHighlight(descInput);
                isValid = false;
            }
        });
        return isValid;
    }

    function validarEtapa6() {
        let isValid = true;
        const acoes = document.querySelectorAll('.acao-entry');

        let filledActions = 0; 

        acoes.forEach((acao) => {
            const nomeInput = acao.querySelector('input[name="acao-nome[]"]');
            const descInput = acao.querySelector('textarea[name="acao-desc[]"]');
            const nome = nomeInput.value.trim();
            const desc = descInput.value.trim();

            if (nome === '' && desc === '') {
                
            } else if ((nome !== '' && desc === '') || (nome === '' && desc !== '')) {
                
                addErrorHighlight(nomeInput);
                addErrorHighlight(descInput);
                isValid = false;
            } else if (nome !== '' && desc !== '') {
                
                filledActions++;
            }
        });

        
        if (filledActions === 0) {
            isValid = false;
            
            if (acoes.length > 0) {
                addErrorHighlight(acoes[0].querySelector('input[name="acao-nome[]"]'));
                addErrorHighlight(acoes[0].querySelector('textarea[name="acao-desc[]"]'));
            }
        }

        return isValid;
    }

    

    if (form) {
        form.addEventListener('submit', function (event) {
            event.preventDefault(); 

            
            clearErrorStyles(etapa1);
            clearErrorStyles(etapa2);
            clearErrorStyles(etapa3);
            clearErrorStyles(etapa4);
            clearErrorStyles(etapa5);
            clearErrorStyles(etapa6);
            clearErrorStyles(etapa7); 

            
            const v1 = validarEtapa1();
            const v2 = validarEtapa2();
            const v3 = validarEtapa3();
            const v4 = validarEtapa4();
            const v5 = validarEtapa5();
            const v6 = validarEtapa6();

            if (v1 && v2 && v3 && v4 && v5 && v6) {
                console.log("Formulário de criatura válido. Enviando...");
                window.location.href = 'tela-principal.html';
            } else {
                if (errorFinal) errorFinal.classList.remove('hidden');

                if (!v1) validarEtapa1();
                if (!v2) validarEtapa2();
                if (!v3) validarEtapa3();
                if (!v4) validarEtapa4();
                if (!v5) validarEtapa5();
                if (!v6) validarEtapa6();
            }
        });
    }

});