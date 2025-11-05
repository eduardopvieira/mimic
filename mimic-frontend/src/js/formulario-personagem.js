// Espera o HTML ser carregado antes de rodar o script
document.addEventListener('DOMContentLoaded', function() {

    // --- Seleciona os elementos (DIVs de cada Etapa) ---
    const etapa1 = document.getElementById('etapa-1');
    const etapa2 = document.getElementById('etapa-2');
    const etapa3 = document.getElementById('etapa-3');
    const etapa4 = document.getElementById('etapa-4');
    const etapa5 = document.getElementById('etapa-5');
    const etapa6 = document.getElementById('etapa-6');

    // --- Seleciona os botões ---
    const btnProximo1 = document.getElementById('btn-proximo-1');
    const btnProximo2 = document.getElementById('btn-proximo-2');
    const btnProximo3 = document.getElementById('btn-proximo-3');
    const btnProximo4 = document.getElementById('btn-proximo-4');
    const btnProximo5 = document.getElementById('btn-proximo-5');

    const btnVoltar2 = document.getElementById('btn-voltar-2');
    const btnVoltar3 = document.getElementById('btn-voltar-3');
    const btnVoltar4 = document.getElementById('btn-voltar-4');
    const btnVoltar5 = document.getElementById('btn-voltar-5');
    const btnVoltar6 = document.getElementById('btn-voltar-6');

    // --- Seleciona os elementos visuais do Stepper ---
    const stepper = {
        circles: [
            document.getElementById('stepper-circle-1'),
            document.getElementById('stepper-circle-2'),
            document.getElementById('stepper-circle-3'),
            document.getElementById('stepper-circle-4'),
            document.getElementById('stepper-circle-5'),
            document.getElementById('stepper-circle-6')
        ],
        texts: [
            document.getElementById('stepper-text-1'),
            document.getElementById('stepper-text-2'),
            document.getElementById('stepper-text-3'),
            document.getElementById('stepper-text-4'),
            document.getElementById('stepper-text-5'),
            document.getElementById('stepper-text-6')
        ],
        lines: [
            document.getElementById('stepper-line-1'),
            document.getElementById('stepper-line-2'),
            document.getElementById('stepper-line-3'),
            document.getElementById('stepper-line-4'),
            document.getElementById('stepper-line-5')
        ]
    };

    // --- Lógica de Navegação (Seu código original) ---

    // Etapa 1 -> 2
    btnProximo1.addEventListener('click', function() {
        etapa1.classList.add('hidden');
        etapa2.classList.remove('hidden');
        
        // Stepper: Marca 1 como concluído
        stepper.circles[0].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[0].classList.add('bg-red-600');
        stepper.texts[0].classList.remove('text-white');
        stepper.texts[0].classList.add('text-red-400');
        stepper.lines[0].classList.remove('border-gray-600');
        stepper.lines[0].classList.add('border-red-600');
        
        // Stepper: Marca 2 como ativo
        stepper.circles[1].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[1].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[1].classList.remove('text-gray-500');
        stepper.texts[1].classList.add('text-white');
    });

    // Etapa 2 -> 1
    btnVoltar2.addEventListener('click', function() {
        etapa2.classList.add('hidden');
        etapa1.classList.remove('hidden');

        // Stepper: Reseta 1 para ativo
        stepper.circles[0].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[0].classList.remove('bg-red-600');
        stepper.texts[0].classList.add('text-white');
        stepper.texts[0].classList.remove('text-red-400');
        stepper.lines[0].classList.add('border-gray-600');
        stepper.lines[0].classList.remove('border-red-600');
        
        // Stepper: Reseta 2 para pendente
        stepper.circles[1].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[1].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[1].classList.add('text-gray-500');
        stepper.texts[1].classList.remove('text-white');
    });

    // Etapa 2 -> 3
    btnProximo2.addEventListener('click', function() {
        etapa2.classList.add('hidden');
        etapa3.classList.remove('hidden');

        // Stepper: Marca 2 como concluído
        stepper.circles[1].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[1].classList.add('bg-red-600');
        stepper.texts[1].classList.remove('text-white');
        stepper.texts[1].classList.add('text-red-400');
        stepper.lines[1].classList.remove('border-gray-600');
        stepper.lines[1].classList.add('border-red-600');

        // Stepper: Marca 3 como ativo
        stepper.circles[2].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[2].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[2].classList.remove('text-gray-500');
        stepper.texts[2].classList.add('text-white');
    });

    // Etapa 3 -> 2
    btnVoltar3.addEventListener('click', function() {
        etapa3.classList.add('hidden');
        etapa2.classList.remove('hidden');

        // Stepper: Reseta 2 para ativo
        stepper.circles[1].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[1].classList.remove('bg-red-600');
        stepper.texts[1].classList.add('text-white');
        stepper.texts[1].classList.remove('text-red-400');
        stepper.lines[1].classList.add('border-gray-600');
        stepper.lines[1].classList.remove('border-red-600');

        // Stepper: Reseta 3 para pendente
        stepper.circles[2].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[2].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[2].classList.add('text-gray-500');
        stepper.texts[2].classList.remove('text-white');
    });

    // Etapa 3 -> 4
    btnProximo3.addEventListener('click', function() {
        etapa3.classList.add('hidden');
        etapa4.classList.remove('hidden');
        
        // Stepper: Marca 3 como concluído
        stepper.circles[2].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[2].classList.add('bg-red-600');
        stepper.texts[2].classList.remove('text-white');
        stepper.texts[2].classList.add('text-red-400');
        stepper.lines[2].classList.remove('border-gray-600');
        stepper.lines[2].classList.add('border-red-600');

        // Stepper: Marca 4 como ativo
        stepper.circles[3].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[3].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[3].classList.remove('text-gray-500');
        stepper.texts[3].classList.add('text-white');
    });

    // Etapa 4 -> 3
    btnVoltar4.addEventListener('click', function() {
        etapa4.classList.add('hidden');
        etapa3.classList.remove('hidden');

        // Stepper: Reseta 3 para ativo
        stepper.circles[2].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[2].classList.remove('bg-red-600');
        stepper.texts[2].classList.add('text-white');
        stepper.texts[2].classList.remove('text-red-400');
        stepper.lines[2].classList.add('border-gray-600');
        stepper.lines[2].classList.remove('border-red-600');
        
        // Stepper: Reseta 4 para pendente
        stepper.circles[3].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[3].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[3].classList.add('text-gray-500');
        stepper.texts[3].classList.remove('text-white');
    });


    // Etapa 4 -> 5
    btnProximo4.addEventListener('click', function() {
        etapa4.classList.add('hidden');
        etapa5.classList.remove('hidden');
        
        // Stepper: Marca 4 como concluído
        stepper.circles[3].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[3].classList.add('bg-red-600');
        stepper.texts[3].classList.remove('text-white');
        stepper.texts[3].classList.add('text-red-400');
        stepper.lines[3].classList.remove('border-gray-600');
        stepper.lines[3].classList.add('border-red-600');

        // Stepper: Marca 5 como ativo
        stepper.circles[4].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[4].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[4].classList.remove('text-gray-500');
        stepper.texts[4].classList.add('text-white');
    });

    // Etapa 5 -> 4
    btnVoltar5.addEventListener('click', function() {
        etapa5.classList.add('hidden');
        etapa4.classList.remove('hidden');

        // Stepper: Reseta 4 para ativo
        stepper.circles[3].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[3].classList.remove('bg-red-600');
        stepper.texts[3].classList.add('text-white');
        stepper.texts[3].classList.remove('text-red-400');
        stepper.lines[3].classList.add('border-gray-600');
        stepper.lines[3].classList.remove('border-red-600');
        
        // Stepper: Reseta 5 para pendente
        stepper.circles[4].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[4].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[4].classList.add('text-gray-500');
        stepper.texts[4].classList.remove('text-white');
    });

    // Etapa 5 -> 6
    btnProximo5.addEventListener('click', function() {
        etapa5.classList.add('hidden');
        etapa6.classList.remove('hidden');
        
        // Stepper: Marca 5 como concluído
        stepper.circles[4].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[4].classList.add('bg-red-600');
        stepper.texts[4].classList.remove('text-white');
        stepper.texts[4].classList.add('text-red-400');
        stepper.lines[4].classList.remove('border-gray-600');
        stepper.lines[4].classList.add('border-red-600');

        // Stepper: Marca 6 como ativo
        stepper.circles[5].classList.remove('bg-gray-800', 'text-gray-400');
        stepper.circles[5].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[5].classList.remove('text-gray-500');
        stepper.texts[5].classList.add('text-white');
    });

    // Etapa 6 -> 5
    btnVoltar6.addEventListener('click', function() {
        etapa6.classList.add('hidden');
        etapa5.classList.remove('hidden');

        // Stepper: Reseta 5 para ativo
        stepper.circles[4].classList.add('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.circles[4].classList.remove('bg-red-600');
        stepper.texts[4].classList.add('text-white');
        stepper.texts[4].classList.remove('text-red-400');
        stepper.lines[4].classList.add('border-gray-600');
        stepper.lines[4].classList.remove('border-red-600');
        
        // Stepper: Reseta 6 para pendente
        stepper.circles[5].classList.add('bg-gray-800', 'text-gray-400');
        stepper.circles[5].classList.remove('bg-gray-700', 'ring-4', 'ring-red-500');
        stepper.texts[5].classList.add('text-gray-500');
        stepper.texts[5].classList.remove('text-white');
    });

    // --------------------------------------------------
    // --- NOVO: LÓGICA DO REPETIDOR DE CAMPOS ---
    // --------------------------------------------------

    function inicializarRepetidor(addBtnId, containerId, entryClass, prefix) {
        
        const addBtn = document.getElementById(addBtnId);
        const container = document.getElementById(containerId);
        
        // Conta quantos 'entries' já existem (começamos com 1)
        let entryCounter = container.getElementsByClassName(entryClass).length;

        addBtn.addEventListener('click', function() {
            // Encontra o primeiro 'molde' (entry) dentro do container
            const entryMolde = container.querySelector(`.${entryClass}`);
            if (!entryMolde) {
                console.error(`Molde com classe ${entryClass} não encontrado.`);
                return;
            }

            // Clona o molde
            const newEntry = entryMolde.cloneNode(true);

            // Incrementa o contador
            entryCounter++;

            // --- Atualiza os IDs e 'for' dos novos elementos ---
            const newSelect = newEntry.querySelector(`.${prefix}-select`);
            const newDesc = newEntry.querySelector(`.${prefix}-desc`);
            const newSelectLabel = newEntry.querySelector(`label[for^="${prefix}-nome"]`);
            const newDescLabel = newEntry.querySelector(`label[for^="${prefix}-desc"]`);

            if (newSelect) newSelect.id = `${prefix}-nome-${entryCounter}`;
            if (newDesc) newDesc.id = `${prefix}-desc-${entryCounter}`;
            if (newSelectLabel) newSelectLabel.setAttribute('for', `${prefix}-nome-${entryCounter}`);
            if (newDescLabel) newDescLabel.setAttribute('for', `${prefix}-desc-${entryCounter}`);

            // --- Limpa os valores do clone ---
            if (newSelect) newSelect.selectedIndex = 0;
            if (newDesc) newDesc.value = 'A descrição...'; // Reseta o placeholder

            // --- Adiciona o botão "Remover" ---
            const removeBtn = document.createElement('button');
            removeBtn.type = 'button';
            removeBtn.innerHTML = '&times;'; // Símbolo de 'X'
            removeBtn.className = 'absolute top-3 right-4 text-gray-400 hover:text-white font-bold text-2xl leading-none';
            
            removeBtn.addEventListener('click', function() {
                newEntry.remove();
                // Nota: Não decrementamos o contador, pois ele só serve para criar IDs únicos, não para contar o total.
            });

            newEntry.appendChild(removeBtn);

            // Adiciona o novo bloco clonado ao container
            container.appendChild(newEntry);
        });
    }

    // --- Inicializa os repetidores para cada etapa ---
    inicializarRepetidor('add-talento', 'talentos-container', 'talento-entry', 'talento');
    inicializarRepetidor('add-truque', 'truques-container', 'truque-entry', 'truque');
    inicializarRepetidor('add-magia', 'magias-container', 'magia-entry', 'magia');
    inicializarRepetidor('add-pericia', 'pericias-container', 'pericia-entry', 'pericia');

});