# 📚 Booky

API Rest de reseñas de libros analizadas con IA que permite gestionar un catálogo de libros y sus reseñas con análisis inteligente del contenido.

## 🚀 Características

- **Gestión de Libros**: CRUD completo para manejar catálogo de libros
- **Sistema de Reseñas**: Los usuarios pueden dejar reseñas sobre los libros
- **Análisis con IA**: Integración con OpenAI para analizar el el contenido de las reseñas (filtrar por lenguaje ofensivo)
- **API RESTful**: Endpoints bien estructurados con documentación Swagger/OpenAPI
- **Base de Datos**: PostgreSQL con JPA/Hibernate
- **Paginación**: Soporte para paginación en listados
- **Búsqueda**: Búsqueda de libros por palabra clave, autor y nombre
- **Tests**: Suite completa de tests unitarios e integración
- **Docker**: Contenerización completa con Docker y Docker Compose
- **CI/CD**: Pipeline automatizado con GitHub Actions

## 🛠️ Stack Tecnológico

- **Java 21** - Última versión LTS
- **Spring Boot 3.2.0** - Framework principal
- **Spring AI** - Integración con modelos de IA (OpenAI)
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL** - Base de datos relacional
- **Lombok** - Reducción de código boilerplate
- **Swagger/OpenAPI** - Documentación de API
- **TestContainers** - Tests de integración
- **Docker** - Contenerización
- **GitHub Actions** - CI/CD

## 📋 Prerrequisitos

- Java 21 o superior
- Maven 3.8+
- Docker y Docker Compose
- PostgreSQL (o usar el contenedor Docker)
- API Key de OpenAI (configurada en variable de entorno .env)

## 🚀 Instalación y Ejecución

### 1. Clonar el repositorio

git clone https://github.com/YuukoI/Booky.git

2. Configurar variables de entorno

Crea un archivo `.env` en la raíz del proyecto:

OPENAI_API_KEY=tu_api_key_de_openai


3. Ejecutar con Docker Compose (Recomendado)

# Iniciar PostgreSQL
docker-compose up -d postgres

# Construir y ejecutar la aplicación
cd com.booky
./mvnw spring-boot:run


### 4. Ejecución local sin Docker

# Iniciar PostgreSQL localmente
# Luego ejecutar la aplicación
cd com.booky
./mvnw spring-boot:run

La aplicación estará disponible en: http://localhost:8080

## 📖 Documentación de la API

Una vez iniciada la aplicación, puedes acceder a la documentación interactiva de la API en:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔌 Endpoints Principales

### Libros
- GET /api/books - Listar todos los libros (paginado)
- POST /api/books - Crear un nuevo libro
- GET /api/books/{id} - Obtener libro por ID
- PUT /api/books/{id} - Actualizar libro
- DELETE /api/books/{id} - Eliminar libro
- GET /api/books/search?keyword={text} - Buscar libros por palabra clave
- GET /api/books/author/{author} - Buscar libros por autor
- GET /api/books/name/{name} - Buscar libro por nombre

### Reseñas
- GET /api/feedbacks - Listar todas las reseñas
- POST /api/feedbacks - Crear nueva reseña (con análisis automático)
- GET /api/feedbacks/{id} - Obtener reseña por ID
- PUT /api/feedbacks/{id} - Actualizar reseña
- DELETE /api/feedbacks/{id} - Eliminar reseña

## 🔄 CI/CD

El proyecto incluye un pipeline completo de CI/CD con GitHub Actions que:

1. **Tests**: Ejecuta suites de tests automatizados
2. **Build**: Construye la aplicación y genera imagen Docker
3. **Deploy**: Despliega a producción (requiere aprobación manual)

## 📊 Estructura del Proyecto

Booky/
├── com.booky/                 # Aplicación Spring Boot
│   ├── src/
│   │   ├── main/java/
│   │   │   └── com/booky/
│   │   │       ├── controller/    # Controladores REST
│   │   │       ├── service/       # Lógica de negocio
│   │   │       ├── repository/    # Acceso a datos
│   │   │       ├── entity/        # Entidades JPA
│   │   │       ├── dto/           # Objetos de transferencia
│   │   │       └── mapper/        # Mappers entre DTOs y entidades
│   │   └── test/java/             # Tests
│   └── pom.xml                    # Configuración Maven
├── compose.yaml                   # Docker Compose para PostgreSQL
├── Dockerfile                    # Imagen Docker de la aplicación
├── .github/workflows/            # GitHub Actions CI/CD
└── README-DEVOPS.md             # Documentación DevOps detallada
