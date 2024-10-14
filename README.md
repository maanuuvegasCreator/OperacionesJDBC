# Gestión de Productos - Consola Java

Este es un proyecto básico de gestión de productos utilizando Java y MySQL. La aplicación permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre una base de datos de productos.

## Requisitos previos

Antes de ejecutar este proyecto, asegúrate de tener instalado lo siguiente:

- **Java JDK 8** o superior
- **MySQL** (servidor de base de datos)
- **IDE** compatible con Java (Eclipse, IntelliJ IDEA, NetBeans, etc.)
- **Conector JDBC para MySQL** (si no está configurado, asegúrate de tener el archivo `mysql-connector-java.jar` en tu `classpath`)

## Instalación

### 1. Clonar el repositorio

Clona el repositorio desde GitHub:

git clone https://github.com/maanuuvegasCreator/OperacionesJDBC.git

### 2. Configurar la base de datos

1. **Crear la base de datos en MySQL**:
   ```sql
   CREATE DATABASE tienda;
  2. Crear la tabla de productos
   USE tienda;

CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT,
    precio DOUBLE
);


3. Configurar el archivo de conexión a la base de datos
El archivo ProductoDAO.java contiene los parámetros de conexión a la base de datos. Asegúrate de actualizar los valores de DB_URL, DB_USER y DB_PASSWORD según tu entorno de MySQL.
// ProductoDAO.java
private static final String DB_URL = "jdbc:mysql://localhost:3306/tienda";
private static final String DB_USER = "root";  // Cambiar si tu usuario de MySQL es diferente
private static final String DB_PASSWORD = "";  // Cambiar si tienes contraseña

4. Ejecutar el proyecto
Abre el proyecto en tu IDE favorito.
Asegúrate de que el conector JDBC de MySQL esté en tu classpath.
Ejecuta la clase Interfaz.java, que contiene el método main() para lanzar la aplicación.
5. Uso de la aplicación
Una vez que la aplicación esté en ejecución, podrás:

Agregar Productos: Ingresa el nombre, descripción y precio del producto.
Listar Productos: Muestra todos los productos registrados en la base de datos.
Actualizar Productos: Selecciona un producto de la tabla, introduce nuevos datos y actualízalo.
Eliminar Producto


