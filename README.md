# Web App de Reservas con Spring Boot

## Requerimientos

Aplicación web que permita a los clientes del Restaurante "Sabor Gourmet" hacer y gestionar reservas en línea.

Esta aplicación debe:

1. Mostrar la disponibilidad de mesas
2. Permitir la reserva en tiempo real
3. Ofrecer la posibilidad de cancelar o modificar reservas existentes.
4. Además, el sistema debe proporcionar una interfaz de administración para que el personal del restaurante pueda actualizar la configuración de las mesas y ver las reservas actuales.
5. La aplicación debe ser responsiva, asegurando una experiencia de usuario óptima tanto en dispositivos móviles como de escritorio.

Problemáticas:

1. Integración de Spring MVC: Implementar controladores y servicios para gestionar solicitudes de reserva, visualización y cancelación.
2. Uso de Spring Data JPA: Modelar y manejar la persistencia de datos
   relacionados con clientes, reservas y mesas.
3. Desarrollo de Interfaces con Thymeleaf: Crear vistas dinámicas que permitan
   a los usuarios interactuar con el sistema de reservas.

## Tech Stack

Java 21 | Spring Boot 3.5.7 | Maven | Jar

### Componentes

- Spring MVC para la interfaz de usuario.
- Actuator
- Spring Data JPA para la gestión de la base de datos.
- PostgreSQL Driver
- Thymeleaf para las vistas con thymeleaf-layout-dialect

### Otras Dependencias

- Spring Web
- Dev Tools

## Tareas Completadas

- [x] Creación del entorno
- [x] Configuración Inicial

## Tareas Pendientes

### 1. Base de datos

- [x] Configurar la conexión a base de datos PostgreSQL en application.properties

### 2. Controladores

- [ ] 1. HomeController (Público)
- [ ] 2. ReservaController (Cliente)
- [ ] 3. AdminController (Personal del Restaurante)
- [ ] 4. MesaController (Administración)
- [ ] 5. ClienteController (Feature - Administración)
- [ ] 6. API REST Controller (Feature - Developer)

### 3. Modelos

- [ ] 1. Mesa.java
- [ ] 2. Cliente.java
- [ ] 3. Reserva.java

### 4. Repositorios

- [ ] ReservaRepository.java

### 5. Servicios

- [ ] ReservaService.java

### 6. Vista Thymeleaf

- [ ] templates/reservas/formulario.html

### 7. Seguridad

- [ ] Implementar seguridad con Spring Security

### 8. Integración con Fudo API (Feature Futura)

- [ ] Crear un FudoIntegrationService que sincronice el estado de las mesas
