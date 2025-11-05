document.addEventListener('DOMContentLoaded', function() {

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

    btnProximo1.addEventListener('click', function() {
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

    btnProximo2.addEventListener('click', function() {
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

    btnProximo3.addEventListener('click', function() {
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


    btnProximo4.addEventListener('click', function() {
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

    btnProximo5.addEventListener('click', function() {
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
    
    btnProximo6.addEventListener('click', function() {
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


    function inicializarRepetidor(addBtnId, containerId, entryClass, prefix) {
        
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

            const newSelect = newEntry.querySelector(`.${prefix}-select`);
            const newDesc = newEntry.querySelector(`.${prefix}-desc`);
            const newSelectLabel = newEntry.querySelector(`label[for^="${prefix}-nome"]`);
            const newDescLabel = newEntry.querySelector(`label[for^="${prefix}-desc"]`);

            if (newSelect) newSelect.id = `${prefix}-nome-${entryCounter}`;
            if (newDesc) newDesc.id = `${prefix}-desc-${entryCounter}`;
            if (newSelectLabel) newSelectLabel.setAttribute('for', `${prefix}-nome-${entryCounter}`);
            if (newDescLabel) newDescLabel.setAttribute('for', `${prefix}-desc-${entryCounter}`);

            if (newSelect) newSelect.selectedIndex = 0;
            if (newDesc) newDesc.value = 'A descrição...';

            const removeBtn = document.createElement('button');
            removeBtn.type = 'button';
            removeBtn.innerHTML = '&times;';
            removeBtn.className = 'absolute top-3 right-4 text-gray-400 hover:text-white font-bold text-2xl leading-none';
            
            removeBtn.addEventListener('click', function() {
                newEntry.remove();
            });

            newEntry.appendChild(removeBtn);

            container.appendChild(newEntry);
        });
    }

    inicializarRepetidor('add-talento', 'talentos-container', 'talento-entry', 'talento');
    inicializarRepetidor('add-truque', 'truques-container', 'truque-entry', 'truque');
    inicializarRepetidor('add-magia', 'magias-container', 'magia-entry', 'magia');
    inicializarRepetidor('add-pericia', 'pericias-container', 'pericia-entry', 'pericia');

});