# 🍹 E-commerce de Bebidas

![Estado del Proyecto](https://img.shields.io/badge/Estado-En%20Desarrollo-orange) ![Tecnologías](https://img.shields.io/badge/Tecnologías-Spring%20Boot%20%7C%20MongoDB%20%7C%20JWT%20%7C%20OAuth2%20%7C%20HTML%2FCSS%2FJS-blue)

**Descripción:**
Proyecto de e-commerce especializado en la venta de bebidas, pensado para brindar una experiencia completa y segura tanto a usuarios como a administradores. Incluye gestión de productos, stock, ventas, pagos y reportes financieros.

---

## 🛠 Tecnologías Utilizadas

**Backend:**

* Java + Spring Boot
* MongoDB (almacenamiento de datos NoSQL)
* JWT y OAuth2 para seguridad y autenticación

**Frontend:**

* HTML5, CSS3 y JavaScript moderno
* Panel de usuario y administrador responsive

**Integraciones:**

* Pasarelas de pago online
* Generación de tickets/facturas
* Manejo de stock y balances de ganancias

---

## 📌 Funcionalidades Principales

### Panel de Usuario

* Registro y login seguro (JWT/OAuth2)
* Visualización de catálogo de productos
* Carrito de compras y finalización de pedidos
* Historial de compras y descargas de tickets

### Panel de Administrador

* Gestión completa de productos y stock
* Seguimiento de ventas, compras y balances
* Control de usuarios y roles diferenciados
* Generación de tickets y reportes de ventas

### Seguridad

* Autenticación y autorización robusta mediante JWT
* Integración con OAuth2 para login externo
* Roles claramente diferenciados (usuario/admin)

---

## 💻 Arquitectura del Proyecto

* Arquitectura **RESTful API** con Spring Boot
* Base de datos **MongoDB** para escalabilidad y flexibilidad
* Separación de capas: **Controlador → Servicio → Repositorio → Modelo**
* Frontend interactúa con la API usando **fetch / Axios**

---

## 📂 Estructura del Proyecto

```

backend/
 ├─ src/main/java/com/ecommerce/
 │    ├─ coonfig/      # Configuracion del proyecto
 │    ├─ controller/   # Endpoints de la API
 │    ├─ exception/    # Excepciones propias
 │    ├─ mappers/      # Mapeadores
 │    ├─ security/     # Seguridad
 │    ├─ service/      # Lógica de negocio
 │    ├─ repository/   # Acceso a MongoDB
 │    └─ model/        # Entidades del proyecto
 ├─ resources/
      ├─ html/
      ├─ css/                # Estilos
      └─ js/                 # Scripts
      └─ application.properties
```

---

## 🚀 Próximos Pasos

* Integración completa de pasarelas de pago 💳
* Mejora de la UI/UX del panel de usuario y admin 🎨
* Automatización de tickets y facturas 🧾
* Reportes avanzados de stock, ventas y balances 📊
* Optimización de seguridad y pruebas de penetración 🔐

---

## 🤝 Cómo Contribuir

Se aceptan contribuciones de cualquier tipo:

1. Crear un fork del repositorio
2. Abrir issues para sugerencias o errores
3. Enviar pull requests con mejoras de código, diseño o documentación

---

## 📧 Contacto

**Desarrollador Principal:** Daniel Iwach

* [LinkedIn](https://www.linkedin.com/in/daniel-iwach)
