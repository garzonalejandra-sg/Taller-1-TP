# Taller 1

## Descripción del proyecto

Este proyecto consiste en una aplicación de consola desarrollada en Java
para la gestión de productos, cuyo propósito principal es implementar la
persistencia de datos mediante una base de datos SQLite.

La aplicación permite realizar operaciones CRUD (crear, consultar,
actualizar y eliminar registros), utilizando JDBC para establecer la
comunicación entre Java y la base de datos.

## Tecnologías utilizadas

- Java
- SQLite
- JDBC (Java Database Connectivity)
- Controlador SQLite JDBC
- Visual Studio Code
- Git y GitHub

## Persistencia de datos

La persistencia de datos permite conservar la información incluso después
de cerrar la aplicación. En este proyecto, los registros se almacenan en
una base de datos SQLite mediante el archivo `productos.db`.

A diferencia de un almacenamiento temporal en memoria, este mecanismo
permite recuperar los registros en ejecuciones posteriores, siempre que
se conserve el archivo de la base de datos y se utilice su misma ubicación.

## Conexión con la base de datos

La conexión entre Java y SQLite se realiza mediante JDBC, utilizando la
biblioteca externa `sqlite-jdbc-3.41.2.2.jar`.

La clase `BaseDatos.java` contiene la lógica para establecer la conexión
con SQLite y crear la tabla `productos` cuando esta no existe. La conexión
se realiza mediante la URL:

`jdbc:sqlite:productos.db`

La clase `Main.java` contiene el método principal y la interacción con
el usuario. A través de las operaciones CRUD, la aplicación ejecuta
consultas e instrucciones SQL sobre la base de datos para gestionar
los registros de productos.

## Estructura del proyecto

- `Main.java`: contiene el método principal, el menú y la interacción
  con el usuario para gestionar los productos.
- `BaseDatos.java`: administra la conexión con SQLite y la creación
  de la tabla.
- `productos.db`: archivo de base de datos utilizado para almacenar
  los registros.
- `sqlite-jdbc-3.41.2.2.jar`: biblioteca necesaria para conectar Java
  con SQLite mediante JDBC.
- `Main.class` y `BaseDatos.class`: archivos generados por el compilador
  de Java a partir de los archivos fuente.

## Compilación y ejecución

Desde una terminal ubicada en la carpeta del proyecto, compilar los
archivos fuente con el siguiente comando desde la terminal:

```bash
javac Main.java BaseDatos.java
