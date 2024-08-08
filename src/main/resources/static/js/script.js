document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("customPrompt");
    const btn = document.getElementById("showDialog");
    const okBtn = document.getElementById("okBtn");
    const nameInput = document.getElementById("nameInput");

    // When the user clicks the button, open the modal
    btn.onclick = function() {
        modal.style.display = "flex";
        nameInput.focus(); // Focus the input field when modal opens
    }

    // When the user clicks the OK button
    okBtn.onclick = function() {
        const name = nameInput.value;
        console.log("Entered Name: ", name);
        modal.style.display = "none";
        nameInput.value = ""; // Clear the input field
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
            nameInput.value = ""; // Clear the input field
        }
    }
});
