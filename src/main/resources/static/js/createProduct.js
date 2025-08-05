document.addEventListener('DOMContentLoaded', function() {
    formProduct=document.getElementById('formProduct');
    btnCreateProduct=document.getElementById('btnCreateProduct');
    btnUpdateProduct=document.getElementById('btnUpdateProduct');

    nameInput=document.getElementById('name');
    descriptionInput=document.getElementById('description');
    brandInput=document.getElementById('brand');
    priceInput=document.getElementById('price');
    categoryInput=document.getElementById('category');
    const previewImg = document.getElementById("preview");
    const placeholder = document.getElementById("preview-placeholder");
    fileInput=document.getElementById('img');

    //btnCreateProduct.addEventListener('click', createProduct);
    btnCreateProduct.addEventListener('click', guardarImagen);
    btnUpdateProduct.addEventListener('click', findProductById)

    async function createProduct(event) {
        event.preventDefault();
        const data= await fetch('http://localhost:8080/product/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: nameInput.value,
                description: descriptionInput.value,
                brand: brandInput.value,
                price: priceInput.value,
                urlImagen: "nada",
                category: categoryInput.value
            })
        });
        const response = await data.json();
        console.log(response);
    }

    async function findProductById(event){
        try{
            const data= await fetch('http://localhost:8080/product/getById/688f6f382e046ac807d2a5d3' ,{
            method: 'GET'});
            const response= await data.json();
            console.log(response);
            previewImg.src= "http://localhost:8080"+ response.urlImagen
            previewImg.classList.remove("hidden");
            // Ocultar el placeholder
            placeholder.style.display = "none";
            return response;
        }catch(error){
            console.log(error);
        }
    }

    async function guardarImagen(event){
        try {
            const formData = new FormData();
            formData.append("name", nameInput.value);
            formData.append("description", descriptionInput.value);
            formData.append("brand", brandInput.value);
            formData.append("price", priceInput.value);
            formData.append("category", categoryInput.value);
            formData.append("file", fileInput.files[0]);
            const response = await fetch("http://localhost:8080/product/completo", {
                method: "POST",
                body: formData,
            });

            if (response.ok) {
                alert("Producto creado exitosamente");
            } else {
                const error = await response.text();
                alert("Error al crear producto: " + error);
            }
        } catch (error) {
            console.error("Error en el fetch:", error);
            alert("Error de red o servidor");
        }
    }

    // Preview de imagen
    document.getElementById('img').addEventListener('change', function(event) {
        const file = event.target.files[0];
        const preview = document.getElementById('preview');
        const placeholder = document.getElementById('preview-placeholder');

        if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
            placeholder.style.display = 'none';
        };
        reader.readAsDataURL(file);
        } else {
            preview.style.display = 'none';
            placeholder.style.display = 'block';
        }
    });

    // Efectos de botones
    document.querySelectorAll('.btn-custom').forEach(button => {
        button.addEventListener('click', function() {
            this.style.transform = 'scale(0.95)';
            setTimeout(() => {
                this.style.transform = 'translateY(-2px)';
            }, 100);
        });
    });


});