# 🍽️ Sistema de Reservas - Restaurante "Sabor Gourmet"

Aplicación web full-stack para gestión de reservas de restaurante con Spring Boot y Bootstrap 5.

## 📋 Descripción

Sistema completo de reservas que permite a los clientes realizar reservas en línea y al personal del restaurante gestionar mesas y reservas de forma eficiente.

### ✨ Características Implementadas

- ✅ Sistema de reservas en tiempo real
- ✅ Gestión completa de mesas (CRUD)
- ✅ Panel de administración intuitivo
- ✅ API REST completa (32 endpoints)
- ✅ Interfaz responsive con Bootstrap 5
- ✅ Validación de datos con Bean Validation
- ✅ Asignación automática de mesas
- ✅ Generación de códigos únicos de reserva
- ✅ Filtrado y búsqueda avanzada

## 🚀 Tech Stack

### Backend

- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.7** - Framework principal
- **Spring MVC** - Controladores web y REST
- **Spring Data JPA** - Persistencia de datos
- **H2 Database** - Base de datos en memoria (desarrollo)
- **Maven** - Gestión de dependencias

### Frontend

- **Thymeleaf** - Motor de plantillas
- **Bootstrap 5.3.2** - Framework CSS
- **Bootstrap Icons 1.11.2** - Iconografía
- **Vanilla JavaScript** - Interactividad

### Herramientas

- **Spring Boot DevTools** - Hot reload
- **Lombok** - Reducción de boilerplate
- **Jackson** - Serialización JSON

## 📁 Estructura del Proyecto

```
src/main/java/ipss/cl/reservas/
├── config/
│   ├── DataLoader.java          # Carga de datos iniciales
│   └── WebConfig.java            # Configuración MVC y CORS
├── controllers/
│   ├── api/                      # REST Controllers
│   │   ├── DisponibilidadRestController.java
│   │   ├── MesaRestController.java
│   │   └── ReservaRestController.java
│   └── web/                      # View Controllers
│       ├── AdminViewController.java
│       ├── HomeViewController.java
│       └── ReservaViewController.java
├── exceptions/
│   ├── GlobalExceptionHandler.java
│   ├── MesaNoDisponibleException.java
│   └── ReservaNoEncontradaException.java
├── models/
│   ├── dto/
│   │   ├── request/              # DTOs de entrada
│   │   ├── response/             # DTOs de salida
│   │   └── view/                 # DTOs para vistas
│   ├── entities/
│   │   ├── Mesa.java
│   │   └── Reserva.java
│   └── enums/
│       ├── EstadoMesa.java
│       ├── EstadoReserva.java
│       └── TipoMesa.java
├── repositories/
│   ├── MesaRepository.java
│   └── ReservaRepository.java
├── services/
│   ├── DisponibilidadService.java
│   ├── MesaService.java
│   └── ReservaService.java
└── utils/
    ├── DateUtils.java
    └── ReservaCodeGenerator.java

src/main/resources/
├── templates/
│   ├── home.html                 # Página principal
│   ├── admin/
│   │   └── dashboard.html        # Panel de administración
│   ├── reserva/
│   │   └── nueva.html            # Formulario de reserva
│   ├── mesas/
│   │   └── lista.html            # Listado de mesas
│   └── reservas/
│       └── lista.html            # Listado de reservas
└── application.properties
```

## 🎯 API REST Endpoints

### Mesas

- `GET /api/mesas` - Listar todas las mesas
- `GET /api/mesas/{id}` - Obtener mesa por ID
- `GET /api/mesas/numero/{numero}` - Buscar por número
- `GET /api/mesas/tipo/{tipo}` - Filtrar por tipo
- `GET /api/mesas/estado/{estado}` - Filtrar por estado
- `GET /api/mesas/activas` - Mesas activas
- `POST /api/mesas` - Crear mesa
- `PUT /api/mesas/{id}` - Actualizar mesa
- `DELETE /api/mesas/{id}` - Eliminar mesa

### Reservas

- `GET /api/reservas` - Listar todas las reservas
- `GET /api/reservas/{id}` - Obtener reserva por ID
- `GET /api/reservas/codigo/{codigo}` - Buscar por código
- `GET /api/reservas/estado/{estado}` - Filtrar por estado
- `GET /api/reservas/fecha/{fecha}` - Reservas por fecha
- `GET /api/reservas/cliente/{nombre}` - Buscar por cliente
- `POST /api/reservas` - Crear reserva
- `PUT /api/reservas/{id}` - Actualizar reserva
- `PUT /api/reservas/{id}/estado` - Cambiar estado
- `DELETE /api/reservas/{id}` - Cancelar reserva

### Disponibilidad

- `GET /api/disponibilidad/fecha/{fecha}` - Mesas disponibles por fecha
- `GET /api/disponibilidad/fecha/{fecha}/hora/{hora}` - Disponibilidad específica
- `POST /api/disponibilidad/verificar` - Verificar disponibilidad

## 🖥️ Vistas Web

### Públicas

- `/` - Página de inicio
- `/reserva/nueva` - Formulario de nueva reserva

### Administración

- `/admin` - Panel de control
- `/mesas` - Gestión de mesas
- `/reservas` - Gestión de reservas

### Desarrollo

- `/h2-console` - Consola H2 Database

## ⚙️ Configuración

### Base de Datos H2 (Desarrollo)

```properties
spring.datasource.url=jdbc:h2:mem:reservasdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
```

### Datos de Prueba

El sistema carga automáticamente:

- 6 mesas de diferentes tipos
- 5 reservas de ejemplo

## 🚀 Ejecución

### Prerrequisitos

- Java 21 o superior
- Maven 3.6+

### Comandos

```bash
# Compilar
./mvnw clean compile

# Ejecutar
./mvnw spring-boot:run

# Ejecutar tests
./mvnw test
```

### Acceso

- **Aplicación**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
- **API REST**: http://localhost:8080/api/\*

## 📊 Estado del Proyecto

### ✅ Completado

#### Backend

- [x] Configuración inicial Spring Boot
- [x] Base de datos H2 configurada
- [x] Modelos de entidades (Mesa, Reserva)
- [x] Enumeraciones (Estados, Tipos)
- [x] Repositorios JPA con queries personalizadas
- [x] Servicios de negocio completos
- [x] DTOs de Request/Response
- [x] Validaciones con Bean Validation
- [x] Manejo global de excepciones
- [x] 32 endpoints REST funcionales
- [x] CORS configurado
- [x] Utilidades (generadores, formateadores)

#### Frontend

- [x] 5 vistas Thymeleaf
- [x] Integración Bootstrap 5
- [x] Diseño responsive
- [x] Formularios con validación
- [x] Consumo de API con Fetch
- [x] Filtros y búsquedas
- [x] Estados de carga y errores
- [x] Iconografía Bootstrap Icons

### 🔄 En Progreso

- [ ] Tests unitarios completos
- [ ] Tests de integración

### 📋 Pendiente

#### Funcionalidades

- [ ] Sistema de autenticación (Spring Security)
- [ ] Roles de usuario (Cliente, Admin)
- [ ] Modificación de reservas
- [ ] Cancelación por código
- [ ] Notificaciones por email
- [ ] Recordatorios automáticos
- [ ] Historial de reservas

#### Mejoras Técnicas

- [ ] Migración a PostgreSQL (producción)
- [ ] Paginación en listados
- [ ] Cache con Redis
- [ ] Documentación API (Swagger/OpenAPI)
- [ ] Internacionalización (i18n)
- [ ] Logs estructurados
- [ ] Métricas con Actuator

#### Integraciones

- [ ] Integración con Fudo API
- [ ] Pasarela de pagos
- [ ] SMS notifications
- [ ] Google Calendar sync

## 🤝 Contribución

Este es un proyecto académico desarrollado para el curso de Desarrollo Web con Spring Boot.

## 📝 Licencia

Proyecto educativo - IPSS Instituto Profesional

## 👨‍💻 Autor

Desarrollado como proyecto del curso de Spring Boot 2024-2025
