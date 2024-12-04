# Biblioteca IES Luis Vives

Este proyecto es una aplicación de gestión de bibliotecas, que permite gestionar y visualizar la información de una biblioteca. En este caso, se ha configurado para la "Biblioteca IES Luis Vives" en Leganés.

## Estructura del Proyecto

- **Package:** `srangeldev`
- **Controladores:** `srangeldev.controllers.*`
- **Modelos:** `srangeldev.models.*`
- **Vistas:** `srangeldev.views.*`
- **Logging:** `org.lighthousegames.logging.logging`
- **Terminal:** `com.github.ajalt.mordant:mordant:2.2.0`

## Funcionalidades Principales

- **Inicialización de la Biblioteca:** La aplicación crea una instancia de la biblioteca con un ID único, un nombre y la ciudad donde se encuentra.
- **Interfaz de Usuario:** Utiliza la clase `View` para mostrar el menú de gestión de la biblioteca, que incluye opciones como obtener todos los libros, buscar libros por ID, crear, modificar y eliminar libros, y generar un informe de la biblioteca.
- **Registro de Logs:** Implementado mediante la biblioteca `org.lighthousegames.logging.logging` para realizar un seguimiento de las acciones y eventos.
- **Personalizacion de la terminal:** Implementado mediante la biblioteca `com.github.ajalt.mordant:mordant:2.2.0` para modificar los colores de la terminal para hacer el programa mas estético.

## Autor

**Ángel Sánchez Gasanz**

---
