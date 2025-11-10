document.addEventListener('DOMContentLoaded', function() {

    // --- VARIÁVEIS DAS ETAPAS ---
    const etapa1 = document.getElementById('etapa-1');
    const etapa2 = document.getElementById('etapa-2');
    const etapa3 = document.getElementById('etapa-3');
    const etapa4 = document.getElementById('etapa-4');
    const etapa5 = document.getElementById('etapa-5');
    const etapa6 = document.getElementById('etapa-6');
    const etapa7 = document.getElementById('etapa-7');

    // --- BOTÕES PRÓXIMO ---
    const btnProximo1 = document.getElementById('btn-proximo-1');
    const btnProximo2 = document.getElementById('btn-proximo-2');
    const btnProximo3 = document.getElementById('btn-proximo-3');
    const btnProximo4 = document.getElementById('btn-proximo-4');
    const btnProximo5 = document.getElementById('btn-proximo-5');
    const btnProximo6 = document.getElementById('btn-proximo-6');

    // --- BOTÕES VOLTAR ---
    const btnVoltar2 = document.getElementById('btn-voltar-2');
    const btnVoltar3 = document.getElementById('btn-voltar-3');
    const btnVoltar4 = document.getElementById('btn-voltar-4');
    const btnVoltar5 = document.getElementById('btn-voltar-5');
    const btnVoltar6 = document.getElementById('btn-voltar-6');
    const btnVoltar7 = document.getElementById('btn-voltar-7');

    // --- FORMULÁRIO ---
    const form = document.getElementById('criatura-form');

    // --- OBJETOS DO STEPPER ---
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

    // --- LÓGICA DE NAVEGAÇÃO DO STEPPER (COM VALIDAÇÃO) ---

    // ----- ETAPA 1 -> 2 -----
    btnProximo1.addEventListener('click', function() {
        // Validação da Etapa 1
        const errors = validarEtapa1();
        if (errors.length > 0) {
            alert("Corrija os seguintes erros na Etapa 1:\n\n" + errors.join("\n"));
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

    btnVoltar2.addEventListener('click', function() {
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

    // ----- ETAPA 2 -> 3 -----
    btnProximo2.addEventListener('click', function() {
        // Validação da Etapa 2
        const errors = validarEtapa2();
        if (errors.length > 0) {
            alert("Corrija os seguintes erros na Etapa 2:\n\n" + errors.join("\n"));
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

    btnVoltar3.addEventListener('click', function() {
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

    // ----- ETAPA 3 -> 4 -----
    btnProximo3.addEventListener('click', function() {
        // Validação da Etapa 3
        const errors = validarEtapa3();
        if (errors.length > 0) {
            alert("Corrija os seguintes erros na Etapa 3:\n\n" + errors.join("\n"));
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

    btnVoltar4.addEventListener('click', function() {
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

    // ----- ETAPA 4 -> 5 -----
    btnProximo4.addEventListener('click', function() {
        // Validação da Etapa 4
        const errors = validarEtapa4();
        if (errors.length > 0) {
            alert("Corrija os seguintes erros na Etapa 4:\n\n" + errors.join("\n"));
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

    btnVoltar5.addEventListener('click', function() {
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

    // ----- ETAPA 5 -> 6 -----
    btnProximo5.addEventListener('click', function() {
        // Validação da Etapa 5
        const errors = validarEtapa5();
        if (errors.length > 0) {
            alert("Corrija os seguintes erros na Etapa 5:\n\n" + errors.join("\n"));
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

    btnVoltar6.addEventListener('click', function() {
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
    
    // ----- ETAPA 6 -> 7 -----
    btnProximo6.addEventListener('click', function() {
        // Validação da Etapa 6
        const errors = validarEtapa6();
        if (errors.length > 0) {
            alert("Corrija os seguintes erros na Etapa 6:\n\n" + errors.join("\n"));
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

    btnVoltar7.addEventListener('click', function() {
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

    // --- CÁLCULO DE MODIFICADOR --- (Sua lógica original)
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

    // --- LÓGICA DO REPETIDOR --- (Sua lógica original)

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

        addBtn.addEventListener('click', function() {
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
                removeBtn.addEventListener('click', function() {
                    newEntry.remove();
                });
            }

            container.appendChild(newEntry);
        });

        const firstRemoveBtn = container.querySelector(`.${entryClass} .remover-btn`);
        if (firstRemoveBtn) {
            firstRemoveBtn.addEventListener('click', function() {
                if (container.getElementsByClassName(entryClass).length > 1) {
                    firstRemoveBtn.closest(`.${entryClass}`).remove();
                } else {
                    const firstInput = container.querySelector(`.${entryClass} input[type="text"]`);
                    const firstTextarea = container.querySelector(`.${entryClass} textarea`);
                    if(firstInput) firstInput.value = '';
                    if(firstTextarea) firstTextarea.value = '';
                }
            });
        }
    }

    inicializarRepetidorCriatura('add-habilidade', 'habilidades-container', 'habilidade-entry', 'habilidade');
    inicializarRepetidorCriatura('add-acao', 'acoes-container', 'acao-entry', 'acao');


    // --- FUNÇÕES DE VALIDAÇÃO (NOVAS) ---
    
    function validarEtapa1() {
        let errors = [];
        if (document.getElementById('nome').value.trim() === '') errors.push('O campo "Nome da Criatura" é obrigatório.');
        if (document.getElementById('tamanho').value === '') errors.push('O campo "Tamanho" é obrigatório.');
        if (document.getElementById('tipo').value.trim() === '') errors.push('O campo "Tipo" é obrigatório.');
        if (document.getElementById('alinhamento').value === '') errors.push('O campo "Alinhamento" é obrigatório.');
        return errors;
    }

    function validarEtapa2() {
        let errors = [];
        if (document.getElementById('ca').value.trim() === '') errors.push('O campo "Classe de Armadura (CA)" é obrigatório.');
        if (document.getElementById('pv').value.trim() === '') errors.push('O campo "Pontos de Vida (PV)" é obrigatório.');
        if (document.getElementById('desl-base').value.trim() === '') errors.push('O campo "Deslocamento Base" é obrigatório.');
        return errors;
    }

    function validarEtapa3() {
        let errors = [];
        const atributos = ['for', 'des', 'con', 'int', 'sab', 'car'];
        for (const atr of atributos) {
            if (document.getElementById(`atr-${atr}`).value.trim() === '') {
                errors.push(`O atributo "${atr.toUpperCase()}" não pode estar vazio.`);
            }
        }
        return errors;
    }

    function validarEtapa4() {
        let errors = [];
        if (document.getElementById('sentidos').value.trim() === '') errors.push('O campo "Sentidos" é obrigatório (ex: Percepção passiva 10).');
        if (document.getElementById('idiomas').value.trim() === '') errors.push('O campo "Idiomas" é obrigatório (use "—" se for o caso).');
        if (document.getElementById('nd').value.trim() === '') errors.push('O campo "Nível de Desafio (ND)" é obrigatório.');
        return errors;
    }

    function validarEtapa5() {
        let errors = [];
        const habilidades = document.querySelectorAll('.habilidade-entry');
        habilidades.forEach((habilidade, index) => {
            const nome = habilidade.querySelector('input[name="habilidade-nome[]"]').value.trim();
            const desc = habilidade.querySelector('textarea[name="habilidade-desc[]"]').value.trim();
            
            if (nome !== '' && desc === '') {
                errors.push(`A Habilidade "${nome}" está sem descrição.`);
            } else if (nome === '' && desc !== '') {
                errors.push(`A Habilidade #${index + 1} tem uma descrição mas está sem nome.`);
            }
        });
        return errors;
    }

    function validarEtapa6() {
        let errors = [];
        const acoes = document.querySelectorAll('.acao-entry');
        
        let acoesPreenchidas = 0;
        acoes.forEach((acao, index) => {
            const nome = acao.querySelector('input[name="acao-nome[]"]').value.trim();
            const desc = acao.querySelector('textarea[name="acao-desc[]"]').value.trim();
            
            if (nome !== '' && desc === '') {
                errors.push(`A Ação "${nome}" está sem descrição.`);
            } else if (nome === '' && desc !== '') {
                errors.push(`A Ação #${index + 1} tem uma descrição mas está sem nome.`);
            } else if (nome !== '' && desc !== '') {
                acoesPreenchidas++;
            }
        });

        if (acoesPreenchidas === 0) {
            errors.push('A criatura deve ter pelo menos uma Ação (Nome e Descrição) preenchida.');
        }
        return errors;
    }

    // --- VALIDAÇÃO FINAL (SUBMIT) ---
    if (form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); // Impede o envio

            // Roda todas as validações de todas as etapas
            let allErrors = [
                ...validarEtapa1(),
                ...validarEtapa2(),
                ...validarEtapa3(),
                ...validarEtapa4(),
                ...validarEtapa5(),
                ...validarEtapa6()
                // Etapa 7 é totalmente opcional, não precisa validar.
            ];

            if (allErrors.length > 0) {
                alert("Erro ao finalizar! O formulário está incompleto. Verifique os erros:\n\n" + allErrors.join("\n"));
            } else {
                // Se tudo estiver OK
                console.log("Formulário de criatura válido. Enviando...");
                // Redireciona para a tela principal
                window.location.href = 'tela-principal.html';
            }
        });
    }
});