# Booky - CI/CD Pipeline

## 🚀 Despliegue Automatizado

Este proyecto utiliza un pipeline CI/CD completo con GitHub Actions para automatizar las pruebas, construcción y despliegue.

## 📋 Flujo del Pipeline

### 1. **Trigger** (Disparadores)
- **Push** a rama `main` o `develop`
- **Pull Request** hacia `main`

### 2. **Jobs** (Tareas)

#### 🔍 Test Job
- Ejecuta tests unitarios y de integración
- Base de datos PostgreSQL para pruebas
- Genera reportes de pruebas
- Variables de entorno configuradas automáticamente

#### 🏗️ Build Job
- Solo se ejecuta en rama `main`
- Construye la aplicación
- Crea y publica imagen Docker a GitHub Container Registry
- Etiquetado automático de versiones

#### 🚀 Deploy Job
- Solo se ejecuta en rama `main`
- Despliega a producción
- Environment protection con aprobación requerida

## 🔧 Configuración Requerida

### Secrets en GitHub
Configura estos secrets en tu repositorio:

```
OPENAI_API_KEY=Tu_API_Key_de_OpenAI
```

### Permisos Necesarios
El pipeline utiliza `GITHUB_TOKEN` para publicar imágenes en el Container Registry.

## 📦 Etiquetas de Imágenes Docker

Las imágenes se publican con las siguientes etiquetas:
- `main-latest` - Última versión de main
- `main-<commit-sha>` - Versión específica por commit
- `pr-<number>` - Para pull requests

## 🐳 Uso Local con Docker

```bash
# Construir imagen
docker build -t booky .

# Ejecutar con PostgreSQL
docker-compose up -d

# Ejecutar la aplicación
docker run -p 8080:8080 booky
```

## 🔍 Monitoreo

### Health Check
La aplicación incluye health checks en:
- `http://localhost:8080/actuator/health`

### Logs
Los logs del pipeline están disponibles en la pestaña "Actions" de GitHub.

## 🛠️ Personalización

### Agregar Tests
Los tests deben estar en `src/test/java/` y seguir la convención de JUnit 5.

### Modificar Deploy
Edita el job `deploy` en `.github/workflows/cicd.yml` para agregar:
- Comandos kubectl para Kubernetes
- Scripts de despliegue personalizados
- Integración con servicios cloud

### Variables de Entorno
Agrega más secrets en GitHub y úsalas en el pipeline con `${{ secrets.NOMBRE_VARIABLE }}`.

## 🚨 Troubleshooting

### Tests Fallidos
- Verifica que la base de datos PostgreSQL esté configurada correctamente
- Revisa las variables de entorno en el job `test`

### Build Fallido
- Asegúrate que el Dockerfile esté en la raíz del proyecto
- Verifica que todos los dependencies estén en `pom.xml`

### Deploy Fallido
- Configura correctamente los secrets de producción
- Verifica los permisos del environment en GitHub

## 📚 Recursos

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
- [GitHub Container Registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry)
