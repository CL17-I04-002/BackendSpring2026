# Gestión de Reservas API

Sistema REST desarrollado con Spring Boot para la gestión de reservas de espacios. El proyecto implementa autenticación basada en JWT, autorización basada en roles y permisos, resiliencia mediante Circuit Breaker, documentación con Swagger/OpenAPI y despliegue mediante Docker Compose.

---

# Tecnologías utilizadas

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Docker
- Docker Compose
- JWT
- Spring Validation
- Spring Actuator
- Resilience4j
- OpenAPI / Swagger
- Maven

---

# Arquitectura

La aplicación sigue una arquitectura por capas:

```
Controller
    │
Service
    │
Repository
    │
PostgreSQL
```

Separando claramente:

- Controladores REST
- Lógica de negocio
- Acceso a datos
- DTOs
- Entidades
- Configuración
- Seguridad
- Manejo global de excepciones

---

# Funcionalidades

## Autenticación

La autenticación se realiza mediante JWT.

El sistema permite:

- Registro de usuarios
- Inicio de sesión
- Generación de Token JWT
- Protección de endpoints

---

## Autorización

Se implementó un sistema de autorización basado en:

- Roles
- Permisos

Cada endpoint requiere permisos específicos para poder ser consumido.

Ejemplo:

```
ADMIN

    CREATE_SPACE
    UPDATE_SPACE
    DELETE_SPACE
    GET_ALL_SPACES
    CREATE_RESERVATION
```

---

## Gestión de Espacios

Permite:

- Crear espacios
- Actualizar espacios
- Eliminar espacios
- Consultar espacios
- Consultar espacio por ID

---

## Gestión de Reservas

Permite:

- Crear reservas
- Consultar reservas
- Cancelar reservas

Antes de crear una reserva el sistema valida:

- existencia del usuario
- existencia del espacio
- disponibilidad del espacio

---

# Integración con Servicio de Pago

La reserva realiza una llamada HTTP hacia un servicio externo que simula la validación del pago.

Este servicio se encuentra en un proyecto independiente.

Cuando el servicio responde correctamente, la reserva continúa.

Si el servicio falla, entra en funcionamiento el Circuit Breaker.

---

# Circuit Breaker (Resilience4j)

Se implementó el patrón Circuit Breaker utilizando Resilience4j.

Objetivo:

Evitar llamadas continuas hacia un servicio externo cuando éste se encuentra caído.

Comportamiento:

```
Reserva

        │

Servicio Pago

        │

¿Disponible?

      Sí

      ↓

Reserva Exitosa

----------------------------

¿No disponible?

↓

Circuit Breaker

↓

Método Fallback

↓

Respuesta controlada
```

El proyecto de simulación del servicio de pago puede responder de dos maneras:

```
HTTP 200
```

o lanzar una excepción para simular indisponibilidad del servicio.

---

# Patrón de Diseño

## Observer

Se implementó el patrón Observer para desacoplar el proceso principal de las acciones posteriores a una reserva.

Cuando una reserva es creada exitosamente:

```
Reserva creada

        │

Notifica Observer

        │

Acciones ejecutadas

- Registro de eventos
- Notificaciones
- Procesos posteriores
```

Esto permite agregar nuevos observadores sin modificar la lógica principal de reservas.

---

# Documentación API

Swagger se encuentra disponible en:

```
http://localhost:8080/api/v1/swagger-ui/index.html
```

OpenAPI:

```
http://localhost:8080/api/v1/api-docs
```

---

# Base de Datos

Motor utilizado:

```
PostgreSQL
```

La base de datos es creada automáticamente mediante Docker Compose.

---

# Requisitos

- Java 17
- Maven 3.9+
- Docker Desktop
- Docker Compose

En Windows es recomendable utilizar Docker Desktop con WSL2 habilitado.

---

# Ejecución con Docker

## 1. Clonar el proyecto

```bash
git clone <url-del-repositorio>

cd gestion-reserva-api
```

---

## 2. Construir el proyecto

```bash
mvn clean package
```

---

## 3. Construir el proyecto

Crear una base de datos llamada GestionReserva.
Abrir pgAdmin.
Ir a Tools → Query Tool.
Abrir el archivo GestionReserva.sql incluido en el proyecto.
Ejecutar el script.
Verificar que las tablas y los datos se hayan creado correctamente.
Levantar la aplicación con Docker Compose.

---

## 4. Levantar los contenedores

```bash
docker compose up --build
```

o

```bash
docker-compose up --build
```

---

## 5. Verificar los contenedores

```bash
docker ps
```

Deberían aparecer:

- PostgreSQL
- Gestión Reserva API

---

## 6. Detener contenedores

```bash
docker compose down
```

---

# Proyecto Externo

Para probar completamente la aplicación es necesario ejecutar el proyecto:

```
Mock Payment Service
```

Este proyecto expone el endpoint:

```
POST

/mock-payment/validate
```

La API de reservas consume este endpoint para validar el pago antes de confirmar la reserva.

Para probar el Circuit Breaker basta con modificar dicho proyecto para lanzar una excepción:

```java
throw new RuntimeException("Payment service unavailable");
```

Al hacerlo, Resilience4j abrirá el circuito luego del número configurado de fallos y ejecutará el método fallback.

---

# Seguridad

La API utiliza:

- JWT Authentication
- Spring Security
- Filtro JWT personalizado
- Autorización basada en Roles
- Autorización basada en Permisos

Los endpoints protegidos requieren enviar:

```
Authorization

Bearer <token>
```

---

# Endpoints principales

## Autenticación

```
POST /authenticate
```

```
POST /users
```

---

## Espacios

```
GET /spaces

GET /spaces/{id}

POST /spaces

PUT /spaces/{id}

DELETE /spaces/{id}
```

---

## Reservas

```
POST /reservations

GET /reservations

DELETE /reservations/{id}
```

---

# Actuator

La aplicación expone endpoints de monitoreo mediante Spring Boot Actuator.

Ejemplos:

```
/actuator/health

/actuator/info

/actuator/metrics
```

---

# Estructura del proyecto

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── mapper
 ├── security
 ├── config
 ├── exception
 ├── observer
 ├── util
 └── resources
```

---

# Consideraciones

- Las contraseñas se almacenan cifradas mediante BCrypt.
- Se utiliza validación mediante Bean Validation.
- Se implementa manejo global de excepciones.
- Se utiliza DTO para separar la capa de presentación de las entidades.
- Se emplea MapStruct para el mapeo entre entidades y DTOs.
- La persistencia se realiza mediante Spring Data JPA y Hibernate.

---

# Autor

Daniel Larín

Prueba Técnica - Gestión de Reservas API