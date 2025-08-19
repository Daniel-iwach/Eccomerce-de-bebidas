// Inicializar totales al cargar la página
document.addEventListener('DOMContentLoaded', async function(event){
    
    const btnClearCart = document.getElementById('clear-cart');
    const cartList = document.getElementById('cart-list');
    const btnCheckout = document.getElementById('checkout-btn');

    // Productos en el carrito
    let cartItems = [];
    let cartId = localStorage.getItem('cartId');
    let userId = localStorage.getItem('userId');
    let cart = 0;

    btnClearCart.addEventListener('click', clearCart);

    cartItems = await getItemsByCartId(cartId);
    await updateTotals();
    await renderItems(cartItems);

    cartList.addEventListener('click', async function(event) {
        if (event.target.matches('[data-inc-id]')) {
            const itemId = event.target.dataset.incId;

            await increaseQuantity(itemId);
            let cartItem= await getItemById(itemId);
            document.getElementById(`qty-${itemId}`).value = cartItem.quantity;
            document.getElementById(`subtotal-${itemId}`).innerText = cartItem.subTotal;
            await updateTotals();
            
        }

        if (event.target.matches('[data-dec-id]')) {
            const itemId = event.target.dataset.decId;
            let currentQty = parseInt(document.getElementById(`qty-${itemId}`).value, 10);

            if (currentQty > 1) {
                await decreaseQuantity(itemId);
                let cartItem= await getItemById(itemId);
                document.getElementById(`qty-${itemId}`).value = cartItem.quantity;
                document.getElementById(`subtotal-${itemId}`).innerText = cartItem.subTotal;
                await updateTotals();
            }

        }

        if (event.target.matches('[data-remove-id]')) {
            const itemId = event.target.dataset.removeId;
            await removeItem(itemId);
            cartItems = await getItemsByCartId(cartId);
            await updateTotals();
            await renderItems(cartItems);
        }

    });

    cartList.addEventListener('input', async function(event) {
        if (event.target.matches('input[id^="qty-"]')) {
            const itemId = event.target.id.replace('qty-', '');
            const value = event.target.value;

            // Si está vacío, no validar aún
            if (value.trim() === '') return;

            let newQuantity = parseInt(value, 10);

            if (isNaN(newQuantity) || newQuantity < 1) {
                newQuantity = 1; // Evitar valores menores a 1
                event.target.value = 1;
            }
            console.log(newQuantity);
            await updateQuantity(itemId, newQuantity); 
            let cartItem = await getItemById(itemId);
            document.getElementById(`subtotal-${itemId}`).innerText = cartItem.subTotal;
            await updateTotals();
        }
    });

    btnCheckout.addEventListener('click', async function(event) {
        console.log(document.getElementById('cart-total').textContent);
        if (confirm('¿Estás seguro de que deseas finalizar la compra?')) {
            await createSale();
            cartItems = await getItemsByCartId(cartId);
            await updateTotals();
            await renderItems(cartItems);
        }
    });

    //funcion para finalizar compra
    async function createSale() {
        try {
            let total = document.getElementById('cart-total').textContent.replace('$', '').replace('.', '');
            const response = await fetch(`http://localhost:8080/sale/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                        userId: userId,
                        payId: 1234,
                        total: parseInt(total)
                })
            });
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error al crear la venta:', error);
        }
    }

    //funcion para obtener datos del carrito
    async function getCart() {
        try {
            const response = await fetch(`http://localhost:8080/cart/get-cart/${cartId}`);
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error al obtener el carrito:', error);
        }
    }

    //funcion para obtener un item por ID
    async function getItemById(itemId) {
        try {
            const response = await fetch(`http://localhost:8080/item-cart/get-by-id/${itemId}`);
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error al obtener el item:', error);
        }
    }
    
    // Función para obtener los items del carrito
    async function getItemsByCartId(cartId){
        try {
            const response = await fetch(`http://localhost:8080/item-cart/list-item-with-product/${cartId}`);
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error al obtener los items:', error);
        }
    }

    //funcion para mostrar los items
    async function renderItems() {
        cartList.innerHTML = '';

        cartItems.forEach(item => {
            const cartItem = document.createElement('div');
            cartItem.classList.add('cart-item');
            cartItem.innerHTML = `
                <div class="item-image">
                    <img src="http://localhost:8080${item.productImage}" alt="${item.productName}">
                </div>
                <div class="item-info">
                    <h4 class="product-name">${item.productName}</h4>
                </div>
                    <div class="item-price">${item.productPrice}</div>
                <div class="quantity-controls">
                    <button class="quantity-btn" data-dec-id="${item.id}">-</button>

                    <input type="number" class="quantity-input" id="qty-${item.id}" value="${item.quantity}" min="1">

                    <button class="quantity-btn" data-inc-id="${item.id}">+</button>
                </div>
                <div class="item-subtotal" id="subtotal-${item.id}">${item.subTotal}</div>

                <button class="remove-item" data-remove-id="${item.id}">
                    <i class="fas fa-trash"></i>
                </button>
            `;
            cartList.appendChild(cartItem);
        });
        
    }
        

    // Función para incrementar cantidad
    async function increaseQuantity(itemId) {
        try {
            const response = await fetch(`http://localhost:8080/item-cart/inc-item`,{
                method: 'POST', 
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "itemCartId": itemId,
                    "cartId": cartId
                })
            });
            const data = await response.json();
        } catch (error) {
            console.error('Error al obtener los items:', error);
        }
    }

    // Función para decrementar cantidad
    async function decreaseQuantity(itemId) {
        try {
            const response = await fetch(`http://localhost:8080/item-cart/dec-item`,{
                method: 'POST', 
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "itemCartId": itemId,
                    "cartId": cartId
                })
            });
            const data = await response.json();
        } catch (error) {
            console.error('Error al obtener los items:', error);
        }
    }

    // Función para actualizar cantidad manualmente
    async function updateQuantity(itemId, quantity) {
        try {
            const response =await fetch(`http://localhost:8080/item-cart/update`,{
                method: 'PUT', 
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "itemId": itemId,
                    "quantity": quantity
                })
            });
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Error al obtener los items:', error);
        }
    }

    
    // Función para remover item
    async function removeItem(itemId) {
        try {
            const response = await fetch(`http://localhost:8080/item-cart/delete/${itemId}`, {
                method: 'DELETE'
            });
            const data = await response.text();
            console.log(data);
        } catch (error) {
            console.log ('Error al obtener los items:', error);
        }
        
    }

    //Funcion para resetear el carrito
    async function resetCart() {
        try {
            const response = await fetch(`http://localhost:8080/item-cart/clean-cart/${cartId}`, {
                method: 'DELETE'
            });
            const data = await response.text();
            console.log(data);
        } catch (error) {
            console.error('Error al obtener los items:', error);
        }
    }


    // Función para actualizar totales
    async function updateTotals() {
        cart = await getCart();
        document.getElementById('cart-total').innerText = `$${cart.total.toLocaleString()}`;
    }

    // Función para vaciar carrito
    async function clearCart() {
        if (confirm('¿Estás seguro de que deseas vaciar todo el carrito?')) {
            await resetCart();
            showEmptyCart();
        }
    }

    // Función para mostrar carrito vacío
    function showEmptyCart() {
        cartList.innerHTML = '';
        document.getElementById('cart-total').textContent = '$0';
    }

});
