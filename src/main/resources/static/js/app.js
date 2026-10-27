document.addEventListener("DOMContentLoaded", () => {
    const phone = document.querySelector("#telefone");
    if (phone) {
        phone.addEventListener("input", () => {
            let value = phone.value.replace(/\D/g, "").slice(0, 11);
            if (value.length > 10) {
                value = value.replace(/^(\d{2})(\d{5})(\d{0,4}).*/, "($1) $2-$3");
            } else if (value.length > 6) {
                value = value.replace(/^(\d{2})(\d{4})(\d{0,4}).*/, "($1) $2-$3");
            } else if (value.length > 2) {
                value = value.replace(/^(\d{2})(\d*)/, "($1) $2");
            }
            phone.value = value;
        });
    }

    document.querySelectorAll(".alert").forEach((alert) => {
        window.setTimeout(() => {
            alert.classList.add("alert-hide");
        }, 6000);
    });
});
