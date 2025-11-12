# Sistema de Gestión de Restaurante Gourmet

Sistema completo de gestión para restaurantes desarrollado con Spring Boot, Thymeleaf y MySQL. Incluye módulos de administración, cocina, facturación, inventario y más.

## 🚀 Características

- **Autenticación y Autorización**: Sistema de roles (ADMIN, MOZO, CAJERO, COCINERO)
- **Gestión de Pedidos**: Creación, seguimiento y gestión de pedidos
- **Módulo de Cocina**: Interfaz especializada para cocineros
- **Facturación**: Generación de facturas con cálculo automático de IGV
- **Inventario**: Control de stock y alertas de productos bajos
- **Dashboard**: Panel de control con métricas y reportes
- **Clientes y Mesas**: Gestión de clientes y configuración de mesas
- **Bitácora**: Registro de actividades del sistema

## 🛠️ Tecnologías

- **Backend**: Spring Boot 3.x, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **Base de Datos**: MySQL 8.x
- **Build Tool**: Maven
- **Java**: JDK 17+

## 📦 Instalación

### Prerrequisitos
- Java 17 o superior
- MySQL 8.x
- Maven 3.6+

### Configuración

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/sant1ago-sl/Exam_java_12_restaurante.git
   cd restaurante_gourmet_12
   ```

2. **Configurar base de datos**:
   - Crear una base de datos MySQL llamada `restaurante_gourmet`
   - Configurar las credenciales en `src/main/resources/application.properties`

3. **Ejecutar la aplicación**:
   ```bash
   mvn spring-boot:run
   ```

4. **Acceder al sistema**:
   - Abrir http://localhost:8085
   - Usar las credenciales de prueba (ver sección de usuarios)

## 👥 Usuarios de Prueba

El sistema incluye usuarios preconfigurados:

| Usuario    | Contraseña | Rol       | Acceso                     |
|------------|------------|-----------|----------------------------|
| admin      | admin123   | ADMIN     | Todas las funcionalidades  |
| mozo       | mozo123    | MOZO      | Gestión de pedidos         |
| cajero     | cajero123  | CAJERO    | Facturación y pagos        |
| cocinero   | cocinero123| COCINERO  | Módulo de cocina           |

## 🗂️ Estructura del Proyecto

```
restaurante_gourmet_12/
├── src/main/java/com/salas/edu/restaurante_gourmet_12/
│   ├── controller/     # Controladores Spring MVC
│   ├── model/          # Entidades JPA
│   ├── repository/     # Repositorios Spring Data
│   ├── service/        # Lógica de negocio
│   └── security/       # Configuración de Spring Security
├── src/main/resources/
│   ├── templates/      # Plantillas Thymeleaf
│   │   ├── admin/      # Panel de administración
│   │   ├── cocina/     # Interfaz de cocina
│   │   ├── facturas/   # Gestión de facturas
│   │   └── layout.html # Layout base
│   ├── static/         # Archivos estáticos (CSS, JS, imágenes)
│   └── application.properties
├── database/
│   ├── schema.sql      # Esquema de la base de datos
│   └── data.sql        # Datos de prueba
└── pom.xml
```

## 🎯 Funcionalidades Principales

### 1. Gestión de Pedidos
- Creación de pedidos con múltiples platos
- Asignación a mesas y clientes
- Seguimiento del estado (PENDIENTE, EN PREPARACIÓN, LISTO, ENTREGADO)
- Interfaz específica para cocineros

### 2. Facturación
- Generación de facturas desde pedidos
- Cálculo automático de IGV (18%)
- Diferentes métodos de pago (EFECTIVO, TARJETA, TRANSFERENCIA)
- Historial de facturas

### 3. Administración
- Gestión de usuarios y roles
- Control de inventario y stock
- Reportes y estadísticas
- Configuración del sistema

### 4. Cocina
- Vista de pedidos pendientes
- Marcado de pedidos como preparados
- Organización por prioridad

## 🔧 Configuración de Base de Datos

El archivo `application.properties` contiene la configuración de conexión:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante_gourmet
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
```

Los scripts de inicialización se encuentran en `database/schema.sql` y `database/data.sql`.

## 🚀 Ejecución

### Desarrollo
```bash
mvn spring-boot:run
```

### Producción
```bash
mvn clean package
java -jar target/restaurante_gourmet_12-0.0.1-SNAPSHOT.jar
```

## 📊 API Endpoints

El sistema expone los siguientes endpoints principales:

- `GET /` - Dashboard principal
- `GET /login` - Formulario de login
- `GET /admin` - Panel de administración
- `GET /cocina` - Módulo de cocina
- `GET /pedidos` - Gestión de pedidos
- `GET /facturas` - Gestión de facturas
- `GET /inventario` - Control de inventario

## 🐛 Solución de Problemas

### Error común: Puerto en uso
```bash
# Cambiar puerto en application.properties
server.port=8080
```

### Error de base de datos
- Verificar que MySQL esté ejecutándose
- Confirmar credenciales en application.properties

### Error de templates
- Verificar que Thymeleaf esté configurado correctamente

## 📝 Licencia

Este proyecto está desarrollado para fines educativos.

## 🤝 Contribuir

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📞 Soporte

Para soporte técnico o preguntas, contactar al desarrollador.

---

**Desarrollado por Santiago Salas** - [GitHub](https://github.com/sant1ago-sl)