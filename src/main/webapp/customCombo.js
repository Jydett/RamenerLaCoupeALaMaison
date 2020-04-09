for (const dropdown of document.querySelectorAll(".custom-select-wrapper")) {
    let selected = dropdown.querySelector(".selected");
    if (selected !== null) {
        dropdown.querySelector('.form-input').value = selected.dataset["value"];
        dropdown.querySelector('.custom-select__trigger span').innerHTML = selected.innerHTML;
    }
    dropdown.addEventListener('click', function() {
        this.querySelector('.custom-select').classList.toggle('open');
    })
}
for (const option of document.querySelectorAll(".custom-option")) {
    option.addEventListener('click', function() {
        if (! this.classList.contains('selected')) {
            let selected = this.parentNode.querySelector('.custom-option.selected');
            if (selected !== null) {
                selected.classList.remove('selected');
            }
            this.classList.add('selected');
            let customSelect = this.closest('.custom-select');
            customSelect.querySelector('.form-input').value = this.dataset["value"];
            customSelect.querySelector('.custom-select__trigger span').innerHTML = this.innerHTML;
        }
    })
}
window.addEventListener('click', function(e) {
    for (const select of document.querySelectorAll('.custom-select')) {
        if (!select.contains(e.target)) {
            select.classList.remove('open');
        }
    }
});