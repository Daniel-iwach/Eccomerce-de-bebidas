🍹 E-commerce de Bebidas

Descripción:
Proyecto de e-commerce especializado en la venta de bebidas, pensado para brindar una experiencia completa y segura tanto a usuarios como a administradores. Incluye gestión de productos, stock, ventas, pagos y reportes financieros.

🛠 Tecnologías Utilizadas
Backend:

Java + Spring Boot

MongoDB (almacenamiento de datos NoSQL)

JWT y OAuth2 para seguridad y autenticación

Frontend:

HTML5, CSS3 y JavaScript 


Integraciones:

Pasarelas de pago online

Generación de tickets/facturas


📌 Funcionalidades Principales
Panel de Usuario
Registro y login seguro (JWT/OAuth2)

Visualización de catálogo de productos

Carrito de compras y finalización de pedidos

Historial de compras y descargas de tickets

Panel de Administrador
Gestión completa de productos y stock

Seguimiento de ventas, compras y balances

Control de usuarios y roles diferenciados

Generación de tickets y reportes de ventas

Seguridad
Autenticación y autorización robusta mediante JWT

Integración con OAuth2 para login externo

Roles claramente diferenciados (usuario/admin)

💻 Arquitectura del Proyecto
Arquitectura RESTful API con Spring Boot

Base de datos MongoDB para escalabilidad y flexibilidad

Separación de capas: Controlador → Servicio → Repositorio → Modelo

Frontend interactúa con la API usando fetch / Axios

📂 Estructura del Proyecto
bash
Copiar
Editar
backend/
 ├─ src/main/java/com/ecommerce/
 │    ├─ controller/     # Endpoints de la API
 │    ├─ service/        # Lógica de negocio
 │    ├─ repository/     # Acceso a MongoDB
 │    └─ model/          # Entidades del proyecto
 ├─ resources/
 │    └─ application.properties
frontend/
 ├─ index.html
 ├─ css/                # Estilos
 └─ js/                 # Scripts
🚀 Próximos Pasos
Integración completa de pasarelas de pago 💳

Mejora de la UI/UX del panel de usuario y admin 🎨

Automatización de tickets y facturas 🧾

Reportes avanzados de stock, ventas y balances 📊

Optimización de seguridad y pruebas de penetración 🔐

🤝 Cómo Contribuir
Se aceptan contribuciones de cualquier tipo:

Crear un fork del repositorio

Abrir issues para sugerencias o errores

Enviar pull requests con mejoras de código, diseño o documentación

📧 Contacto
Desarrollador Principal: Daniel Iwach

LinkedIn
