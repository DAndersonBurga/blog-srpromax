let paginaActual = 1;
let limit = 6;
let list = document.querySelectorAll('.list .blog');

function cargarItem() {
    let inicio = limit * (paginaActual - 1);
    let fin = limit * paginaActual - 1;
    list.forEach((item, index) => {
        if (index >= inicio && index <= fin) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    })
    listPage()
}
cargarItem();

function listPage() {
    let count = Math.ceil(list.length / limit); // Redondear hacia arriba
    document.querySelector(".listPage").innerHTML = "";

    if (paginaActual !== 1) {
        let anterior = document.createElement("li");
        anterior.textContent = "Anterior";
        anterior.setAttribute("onclick", "cambiarPag(" + (paginaActual - 1) + ")");
        document.querySelector(".listPage").appendChild(anterior);
    }

    for (let i = 1; i < count+1; i++) { // Para no mostrar indice 0 el count + 1
        let nuevaPagina = document.createElement("li");
        nuevaPagina.textContent = i;
        if (i === paginaActual) {
            nuevaPagina.classList.add("active");
        }
        nuevaPagina.setAttribute("onclick", "cambiarPag(" + i + ")");
        document.querySelector(".listPage").appendChild(nuevaPagina);
    }

    if (paginaActual !== count) { // Si no estoy en la pagina final
        let siguiente = document.createElement("li");
        siguiente.textContent = "Siguiente"
        siguiente.setAttribute("onclick", "cambiarPag(" + (paginaActual + 1) + ")");
        document.querySelector(".listPage").appendChild(siguiente);
    }
}

function cambiarPag(index) {
    paginaActual = index;
    cargarItem();
}