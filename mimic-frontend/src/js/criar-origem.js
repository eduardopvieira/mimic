document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('origem-form');

    if (form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); 
            let errors = [];

            const nome = document.getElementById('origem-nome').value.trim();
            if (nome === '') {
                errors.push('O campo "Nome da Origem" é obrigatório.');
            }

            const pericia1 = document.getElementById('origem-pericia-1').value;
            const pericia2 = document.getElementById('origem-pericia-2').value;

            if (pericia1 === '') {
                errors.push('O campo "Proficiência em Perícia 1" é obrigatório.');
            }
            if (pericia2 === '') {
                errors.push('O campo "Proficiência em Perícia 2" é obrigatório.');
            }

            if (pericia1 !== '' && pericia1 === pericia2) {
                errors.push('As Perícias 1 e 2 não podem ser iguais.');
            }

            const equipamento = document.getElementById('origem-equipamento').value.trim();
            if (equipamento === '') {
                errors.push('O campo "Equipamento Inicial" é obrigatório.');
            }

            const featureNome = document.getElementById('origem-feature-nome').value.trim();
            const featureDesc = document.getElementById('origem-feature-descricao').value.trim();

            if (featureNome !== '' && featureDesc === '') {
                errors.push('A "Descrição da Característica" é obrigatória, já que um nome foi preenchido.');
            }
            if (featureNome === '' && featureDesc !== '') {
                errors.push('O "Nome da Característica" é obrigatório, já que uma descrição foi preenchida.');
            }

            if (errors.length > 0) {
                alert("Por favor, corrija os seguintes erros:\n\n" + errors.join("\n"));
            } else {
                console.log("Formulário de origem válido. Enviando...");
                
                window.location.href = 'tela-principal.html';
            }
        });
    }
});