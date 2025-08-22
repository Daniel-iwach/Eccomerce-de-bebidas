// Datos de productos
document.addEventListener('DOMContentLoaded', async function() {

    // Variables globales
    let cart = [];
    let cartId = localStorage.getItem('cartId');
    let products = [];
    let currentFilter = 'all';
    let searchTerm = '';

    // Elementos del DOM
    const productsGrid = document.getElementById('productsGrid');
    const cartIcon = document.getElementById('cartIcon');
    const cartCount = document.getElementById('cartCount');
    const cartItems = document.getElementById('cartItems');
    const cartTotal = document.getElementById('cartTotal');


    const searchInput = document.getElementById('searchInput');
    const filterButtons = document.querySelectorAll('.filter-btn');
    const categoryCards = document.querySelectorAll('.category-card');


    // Inicializar todo después de que los elementos DOM estén disponibles
    products = await getAllProducts();
    console.log(products);
    await renderProducts();
    if(localStorage.getItem('cartId') !== null){
        await updateCartUI(cartId);
    }

    await initializeEventListeners();
    animateOnScroll();

    async function getAllProducts() {
        try {
            const response = await fetch("http://localhost:8080/products/get-all");
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error al obtener los productos:', error);
            return [];
        }
    }

    // Event Listeners
    async function initializeEventListeners() {

        //Botones de añadir al carrito
        document.querySelectorAll('.add-to-cart').forEach(button => {
            button.addEventListener('click', async function(event) {
                if(localStorage.getItem('cartId') === null) {
                    window.location.href = '/html/login.html';
                }else{
                    const productId = this.dataset.productId;
                    await addToCart(cartId, productId,event);
                }
            });
        });

        // Filtros de productos
        filterButtons.forEach(btn => {
            btn.addEventListener('click', async function() {
                filterButtons.forEach(b => b.classList.remove('active'));
                this.classList.add('active');
                currentFilter = this.dataset.filter;
                await renderProducts();
            });
        });

        // Categorías
        categoryCards.forEach(card => {
            card.addEventListener('click', async function() {
                const category = this.dataset.category;
                filterButtons.forEach(b => b.classList.remove('active'));
                const targetBtn = document.querySelector(`[data-filter="${category}"]`);
                if (targetBtn) {
                    targetBtn.classList.add('active');
                    currentFilter = category;
                    await renderProducts();
                    scrollToProducts();
                }
            });
        });

        // Búsqueda
        searchInput.addEventListener('input', function() {
            searchTerm = this.value.toLowerCase();
            renderProducts();
        });

        // Abrir carrito
        cartIcon.addEventListener('click', openCart);

        // Navegación suave
        document.querySelectorAll('.nav-link').forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                const target = this.getAttribute('href');
                if (target.startsWith('#')) {
                    const element = document.querySelector(target);
                    if (element) {
                        const headerHeight = 80;
                        const elementPosition = element.offsetTop - headerHeight;
                        window.scrollTo({
                            top: elementPosition,
                            behavior: 'smooth'
                        });
                    }
                }
            });
        });

        // Efecto scroll del header - arreglado
        let ticking = false;
        function updateHeader() {
            const header = document.querySelector('.header');
            if (window.scrollY > 50) {

            } else {

            }
            ticking = false;
        }

        window.addEventListener('scroll', function() {
            if (!ticking) {
                requestAnimationFrame(updateHeader);
                ticking = true;
            }
        });
    }

    // Renderizar productos
    async function renderProducts() {
        let filteredProducts = products;
        // Aplicar filtros
        if (currentFilter !== 'all') {
            filteredProducts = products.filter(product =>
                product.category.toLowerCase() === currentFilter.toLowerCase()
            );
        }

        // Aplicar búsqueda
        if (searchTerm) {
            filteredProducts = filteredProducts.filter(product =>
                product.name.toLowerCase().includes(searchTerm) ||
                product.description.toLowerCase().includes(searchTerm)
            );
        }

        // Renderizar
        productsGrid.innerHTML = filteredProducts.map(product => `
            <div class="product-card" data-category="${product.category}">
                <div class="product-image-container">
                    <img class="product-image" src="http://localhost:8080${product.urlImagen}" alt="${product.name}"></i>
                    <div class="product-badge">${product.category}</div>
                </div>
                <div class="product-info">
                    <div class="product-category">${product.brand}</div>
                    <h4 class="product-name">${product.name}</h4>
                    <p class="product-description">${product.description}</p>
                    <div class="product-footer">
                    <div class="product-price">$${product.price}</div>

                        <button class="add-to-cart" data-product-id="${product.id}">
                            <i class="fas fa-cart-plus"></i> Agregar
                        </button>

                    </div>
                </div>
            </div>
        `).join('');

        // Reiniciar animaciones
        setTimeout(() => {
            document.querySelectorAll('.product-card').forEach((card, index) => {
                card.style.animationDelay = `${index * 0.1}s`;
            });
        }, 100);
    }


    // Funciones del carrito
    async function addToCart(cartId, productId,event) {
        await addItemToCart(cartId, productId);

        await updateCartUI(cartId);
        showAddToCartAnimation();

        // Feedback visual
        const addButton = event.target;
        const originalText = addButton.innerHTML;
        addButton.innerHTML = '<i class="fas fa-check"></i> ¡Agregado!';
        addButton.style.background = '#28a745';

        setTimeout(() => {
            addButton.innerHTML = originalText;
            addButton.style.background = '';
        }, 1500);
    }

    async function addItemToCart(cartId, productId) {
        try {
            const response = await fetch('http://localhost:8080/item-cart/add-item', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "cartId": cartId,
                    "productId": productId,
                    quantity: 1
                })
            });
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error al agregar el producto al carrito:', error);
            return null;
        }
    }

    async function getTotalItemsByCartId(cartId) {
        try {
            const response = await fetch(`http://localhost:8080/item-cart/get-total-quantity/${cartId}`);
             if (response.status === 401 || response.status === 403) {
                // Token inválido o expirado → limpiar y redirigir
                localStorage.clear();
                window.location.href = "/html/login.html";
                return null;
            }else{
                const data = await response.json();
                return data;
            }
        } catch (error) {
            console.error('Error al obtener el total de productos en el carrito:', error);
            return null;
        }
    }

    async function updateCartUI(cartId) {
        let totalItems = await getTotalItemsByCartId(cartId);
        cartCount.innerText = totalItems;
    }

    function openCart() {
        if(localStorage.getItem('cartId') === null) {
            localStorage.clear();
            window.location.href = '/html/login.html';
        }else{
            window.location.href = '/html/cart.html';
        }
    }



    function renderCartItems() {
        if (cart.length === 0) {
            cartItems.innerHTML = '<p class="empty-cart">Tu carrito está vacío</p>';
            cartTotal.textContent = '0';
            return;
        }

        cartItems.innerHTML = cart.map(item => `
            <div class="product-card">
                <div class="cart-item-info">
                    <div class="cart-item-name">${item.name}</div>

                </div>
                <div class="quantity-controls">
                    <button class="quantity-btn" onclick="updateQuantity(${item.id}, -1)">
                        <i class="fas fa-minus"></i>
                    </button>
                    <span class="quantity">${item.stock}</span>
                    <button class="quantity-btn" onclick="updateQuantity(${item.id}, 1)">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
                <button class="remove-item" onclick="removeFromCart(${item.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        `).join('');

        const total = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
        cartTotal.textContent = total.toLocaleString();
    }

    // Funciones auxiliares
    function scrollToProducts() {
        const productsSection = document.getElementById('products');
        const headerHeight = 80;
        const elementPosition = productsSection.offsetTop - headerHeight;
        window.scrollTo({
            top: elementPosition,
            behavior: 'smooth'
        });
    }

    function showAddToCartAnimation() {
        const cartIcon = document.getElementById('cartIcon');
        cartIcon.style.transform = 'scale(1.2)';
        cartIcon.style.transition = 'transform 0.3s ease';

        setTimeout(() => {
            cartIcon.style.transform = 'scale(1)';
        }, 300);
    }

    function animateOnScroll() {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.style.opacity = '1';
                    entry.target.style.transform = 'translateY(0)';
                }
            });
        }, {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        });

        // Observar elementos que queremos animar
        document.querySelectorAll('.category-card, .product-card').forEach(el => {
            el.style.opacity = '0';
            el.style.transform = 'translateY(30px)';
            el.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
            observer.observe(el);
        });
    }

    // Efectos adicionales - arreglados
    let mouseX = 0;
    let mouseY = 0;
    let isMoving = false;

    document.addEventListener('mousemove', function(e) {
        mouseX = e.clientX;
        mouseY = e.clientY;

        if (!isMoving) {
            isMoving = true;
            requestAnimationFrame(updateBottleEffect);
        }
    });

    function updateBottleEffect() {
        const bottles = document.querySelectorAll('.bottle-showcase');
        bottles.forEach(bottle => {
            const rect = bottle.getBoundingClientRect();
            const x = mouseX - rect.left - rect.width / 2;
            const y = mouseY - rect.top - rect.height / 2;

            const rotateX = Math.max(-15, Math.min(15, (y / rect.height) * 8));
            const rotateY = Math.max(-15, Math.min(15, (x / rect.width) * 8));

            bottle.style.transform = `perspective(1000px) rotateX(${-rotateX}deg) rotateY(${rotateY}deg)`;
        });
        isMoving = false;
    }


    // Preloader simple (opcional)
    window.addEventListener('load', function() {
        document.body.classList.add('loaded');

        // Agregar clase para animaciones iniciales
        setTimeout(() => {
            document.querySelectorAll('.category-card').forEach((card, index) => {
                setTimeout(() => {
                    card.style.animation = `fadeInScale 0.6s ease-out forwards`;
                }, index * 100);
            });
        }, 500);
    });

    console.log('🍷cargado correctamente!');

});