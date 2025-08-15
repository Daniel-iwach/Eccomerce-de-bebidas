// Real-time validation for better UX
document.addEventListener('DOMContentLoaded', async function() {

    let= email = document.getElementById('loginEmail');
    let= password = document.getElementById('loginPassword');
    const loginForm = document.getElementById('loginFormElement');

    const forgotPasswor= document.getElementById('forgotPassword');
    const registerLink = document.getElementById('registerLink');
    const loginLink = document.getElementById('loginLink');
    const loginLink2 = document.getElementById('loginLink2');

    const togglePasswordIcon = document.getElementById('togglePasswordIcon');

    forgotPasswor.addEventListener('click', () => showForm('forgotForm'));
    registerLink.addEventListener('click', () => showForm('registerForm'));
    loginLink.addEventListener('click', () => showForm('loginForm'));
    loginLink2.addEventListener('click', () => showForm('loginForm'));

    
    togglePasswordIcon.addEventListener('click', ()=> togglePassword('loginPassword'));


    // Add real-time validation listeners
    const inputs = document.querySelectorAll('input');
    inputs.forEach(input => {
        input.addEventListener('blur', function() {
            validateField(this);
        });
        
        input.addEventListener('input', function() {
            // Clear error on input if field was previously invalid
            if (this.classList.contains('error')) {
                validateField(this);
            }
        });
    });


    // Mostrar formulario correspondiente
    function showForm(formId) {
        // Hide all forms
        document.querySelectorAll('.auth-form').forEach(form => {
            form.classList.remove('active');
        });
        
        // Show selected form
        document.getElementById(formId).classList.add('active');
        
        // Clear all forms and errors
        clearAllForms();
    }

    // Mostrar u ocultar contraseña
    function togglePassword(inputId) {
        const input = document.getElementById(inputId);
        const icon = input.nextElementSibling;
        
        if (input.type === 'password') {
            input.type = 'text';
            icon.classList.replace('fa-eye', 'fa-eye-slash');
        } else {
            input.type = 'password';
            icon.classList.replace('fa-eye-slash', 'fa-eye');
        }
    }

    // Show error
    function showError(inputId, message) {
        const input = document.getElementById(inputId);
        const errorElement = document.getElementById(inputId + 'Error');
        
        input.classList.add('error');
        input.classList.remove('success');
        errorElement.textContent = message;
        errorElement.classList.add('show');
    }

    // Show success
    function showSuccess(inputId) {
        const input = document.getElementById(inputId);
        const errorElement = document.getElementById(inputId + 'Error');
        
        input.classList.remove('error');
        input.classList.add('success');
        errorElement.classList.remove('show');
    }

    // Clear errors
    function clearError(inputId) {
        const input = document.getElementById(inputId);
        const errorElement = document.getElementById(inputId + 'Error');
        
        input.classList.remove('error', 'success');
        errorElement.classList.remove('show');
    }

    // Clear all forms
    function clearAllForms() {
        document.querySelectorAll('form').forEach(form => {
            form.reset();
        });
        document.querySelectorAll('.error-message').forEach(error => {
            error.classList.remove('show');
        });
        document.querySelectorAll('input').forEach(input => {
            input.classList.remove('error', 'success');
        });
        document.querySelectorAll('.success-message').forEach(success => {
            success.classList.remove('show');
        });
    }

    // Show loading state
    function showLoading(formType) {
        const button = document.getElementById(formType + 'ButtonText');
        const loading = document.getElementById(formType + 'Loading');
        const submitButton = button.closest('.auth-button');
        
        button.style.display = 'none';
        loading.style.display = 'block';
        submitButton.disabled = true;
    }

    // Hide loading state
    function hideLoading(formType) {
        const button = document.getElementById(formType + 'ButtonText');
        const loading = document.getElementById(formType + 'Loading');
        const submitButton = button.closest('.auth-button');
        
        button.style.display = 'inline';
        loading.style.display = 'none';
        submitButton.disabled = false;
    }

    // Login form handler
    document.getElementById('loginFormElement').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const email = document.getElementById('loginEmail').value;
        const password = document.getElementById('loginPassword').value;
        let isValid = true;

        // Clear previous errors
        clearError('loginEmail');
        clearError('loginPassword');

        // Validate email
        if (!email) {
            showError('loginEmail', 'El email es requerido');
            isValid = false;
        } else if (!validateEmail(email)) {
            showError('loginEmail', 'Por favor ingresa un email válido');
            isValid = false;
        } else {
            showSuccess('loginEmail');
        }

        // Validate password
        if (!password) {
            showError('loginPassword', 'La contraseña es requerida');
            isValid = false;
        } else {
            showSuccess('loginPassword');
        }

        if (isValid) {
            showLoading('login');
            
            setTimeout(async() => {
                hideLoading('login');
                await login(e);
            }, 2000);
        }
    });

    // Register form handler
    document.getElementById('registerFormElement').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const formData = {
            name: document.getElementById('registerName').value,
            lastname: document.getElementById('registerLastname').value,
            email: document.getElementById('registerEmail').value,
            password: document.getElementById('registerPassword').value,
            address: document.getElementById('registerAddress').value,
            numberPhone: document.getElementById('registerPhone').value
        };
        
        let isValid = true;

        // Clear previous errors
        Object.keys(formData).forEach(key => {
            clearError('register' + key.charAt(0).toUpperCase() + key.slice(1));
        });
        clearError('registerLastname');

        // Validate name
        if (!formData.name) {
            showError('registerName', 'El nombre es requerido');
            isValid = false;
        } else if (!validateName(formData.name)) {
            showError('registerName', 'El nombre debe tener al menos 2 caracteres');
            isValid = false;
        } else {
            showSuccess('registerName');
        }

        // Validate lastname
        if (!formData.lastname) {
            showError('registerLastname', 'El apellido es requerido');
            isValid = false;
        } else if (!validateName(formData.lastname)) {
            showError('registerLastname', 'El apellido debe tener al menos 2 caracteres');
            isValid = false;
        } else {
            showSuccess('registerLastname');
        }

        // Validate email
        if (!formData.email) {
            showError('registerEmail', 'El email es requerido');
            isValid = false;
        } else if (!validateEmail(formData.email)) {
            showError('registerEmail', 'Por favor ingresa un email válido');
            isValid = false;
        } else {
            showSuccess('registerEmail');
        }

        // Validate password
        if (!formData.password) {
            showError('registerPassword', 'La contraseña es requerida');
            isValid = false;
        } else if (!validatePassword(formData.password)) {
            showError('registerPassword', 'La contraseña debe tener al menos 6 caracteres');
            isValid = false;
        } else {
            showSuccess('registerPassword');
        }

        // Validate address
        if (!formData.address) {
            showError('registerAddress', 'La dirección es requerida');
            isValid = false;
        } else if (!validateAddress(formData.address)) {
            showError('registerAddress', 'La dirección debe ser más específica');
            isValid = false;
        } else {
            showSuccess('registerAddress');
        }

        // Validate phone
        if (!formData.numberPhone) {
            showError('registerPhone', 'El teléfono es requerido');
            isValid = false;
        } else if (!validatePhone(formData.numberPhone)) {
            showError('registerPhone', 'Por favor ingresa un número de teléfono válido');
            isValid = false;
        } else {
            showSuccess('registerPhone');
        }

        if (isValid) {
            showLoading('register');
            
            // Simulate API call
            setTimeout(() => {
                hideLoading('register');
                document.getElementById('registerSuccess').classList.add('show');
                
                // Simulate redirect after success
                setTimeout(() => {
                    console.log('Register data:', formData);
                    alert('¡Registro exitoso! En una aplicación real, serías redirigido al dashboard.');
                    // Auto switch to login form after successful registration
                    setTimeout(() => {
                        showForm('loginForm');
                    }, 2000);
                }, 1500);
            }, 2000);
        }
    });

    // Forgot password form handler
    document.getElementById('forgotFormElement').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const email = document.getElementById('forgotEmail').value;
        let isValid = true;

        // Clear previous errors
        clearError('forgotEmail');

        // Validate email
        if (!email) {
            showError('forgotEmail', 'El email es requerido');
            isValid = false;
        } else if (!validateEmail(email)) {
            showError('forgotEmail', 'Por favor ingresa un email válido');
            isValid = false;
        } else {
            showSuccess('forgotEmail');
        }

        if (isValid) {
            showLoading('forgot');
            
            // Simulate API call
            setTimeout(() => {
                hideLoading('forgot');
                document.getElementById('forgotSuccess').classList.add('show');
                
                setTimeout(() => {
                    console.log('Forgot password for:', email);
                    alert('Se ha enviado un enlace de recuperación a tu email. Revisa tu bandeja de entrada.');
                }, 1500);
            }, 2000);
        }
    });

    // Format phone number as user types
    document.getElementById('registerPhone').addEventListener('input', function() {
        let value = this.value.replace(/\D/g, '');
        if (value.length > 0) {
            if (value.startsWith('54')) {
                // Argentina format: +54 9 11 1234-5678
                if (value.length <= 2) {
                    value = '+54';
                } else if (value.length <= 3) {
                    value = '+54 ' + value.slice(2);
                } else if (value.length <= 5) {
                    value = '+54 ' + value.slice(2, 3) + ' ' + value.slice(3);
                } else if (value.length <= 9) {
                    value = '+54 ' + value.slice(2, 3) + ' ' + value.slice(3, 5) + ' ' + value.slice(5);
                } else {
                    value = '+54 ' + value.slice(2, 3) + ' ' + value.slice(3, 5) + ' ' + value.slice(5, 9) + '-' + value.slice(9, 13);
                }
            } else {
                // Auto-add Argentina country code if not present
                if (value.length === 10 && value.startsWith('9')) {
                    value = '+54 ' + value.slice(0, 1) + ' ' + value.slice(1, 3) + ' ' + value.slice(3, 7) + '-' + value.slice(7);
                } else if (value.length >= 8) {
                    value = '+54 9 ' + value.slice(0, 2) + ' ' + value.slice(2, 6) + '-' + value.slice(6, 10);
                }
            }
        }
        this.value = value;
    });

    async function login(event){
        event.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/auth/log-in', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: email.value,
                    password: password.value
                }),
                credentials: 'include'
            });
            const data = await response.json();
            if (data.error) {
                document.getElementById('loginSuccess').classList.remove('show');
                document.getElementById('loginError').classList.add('show');
            }else{
                document.getElementById('loginError').classList.remove('show');
                document.getElementById('loginSuccess').classList.add('show');
                setTimeout(async() => {
                    let user = await getUserByEmail();
                    let cart = await getCartByUserId(user.id);
                    localStorage.setItem('userId', user.id);
                    localStorage.setItem('cartId', cart.id);
                    window.location.href = '../html/index.html';
                }, 2000);
            }
            console.log(data);
            
        } catch (error) {
            console.error('Error al iniciar sesión:', error);
        }
    }

    async function getUserByEmail() {
        try {
            let email = document.getElementById('loginEmail').value;
            const response = await fetch('http://localhost:8080/users/get-by-email/' + email, {
                method: 'GET',
                credentials: 'include'
            });
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error al obtener el usuario:', error);
        }
    }

    async function getCartByUserId(userId) {
        try {
            const response = await fetch('http://localhost:8080/cart/get-cart-by-user/' + userId , {
                method: 'GET',
                credentials: 'include'
            });
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error al obtener el carrito:', error);
        }
    }

    //--------------------------------------- Validaciones----------------------------------------

    //valida el input correspondiente
    function validateField(input) {
        const value = input.value;
        const id = input.id;
        
        switch(id) {
            case 'loginEmail':
            case 'registerEmail':
            case 'forgotEmail':
                if (value && !validateEmail(value)) {
                    showError(id, 'Por favor ingresa un email válido');
                } else if (value && validateEmail(value)) {
                    showSuccess(id);
                } else if (!value) {
                    clearError(id);
                }
                break;
                
            case 'loginPassword':
            case 'registerPassword':
                if (value && !validatePassword(value)) {
                    showError(id, 'La contraseña debe tener al menos 6 caracteres');
                } else if (value && validatePassword(value)) {
                    showSuccess(id);
                } else if (!value) {
                    clearError(id);
                }
                break;
                
            case 'registerName':
            case 'registerLastname':
                if (value && !validateName(value)) {
                    const fieldName = id.includes('Name') ? 'nombre' : 'apellido';
                    showError(id, `El ${fieldName} debe tener al menos 2 caracteres`);
                } else if (value && validateName(value)) {
                    showSuccess(id);
                } else if (!value) {
                    clearError(id);
                }
                break;
                
            case 'registerPhone':
                if (value && !validatePhone(value)) {
                    showError(id, 'Por favor ingresa un número de teléfono válido');
                } else if (value && validatePhone(value)) {
                    showSuccess(id);
                } else if (!value) {
                    clearError(id);
                }
                break;
                
            case 'registerAddress':
                if (value && !validateAddress(value)) {
                    showError(id, 'La dirección debe ser más específica');
                } else if (value && validateAddress(value)) {
                    showSuccess(id);
                } else if (!value) {
                    clearError(id);
                }
                break;
        }
    }

    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

    function validatePassword(password) {
        return password.length >= 6;
    }

    function validateName(name) {
        return name.trim().length >= 2;
    }

    function validatePhone(phone) {
        const re = /^[\+]?[\d\s\-\(\)]{8,}$/;
        return re.test(phone.replace(/\s/g, ''));
    }

    function validateAddress(address) {
        return address.trim().length >= 5;
    }

});
