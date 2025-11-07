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
});