package srangeldev.views

import org.lighthousegames.logging.Logger
import org.lighthousegames.logging.logging
import srangeldev.controllers.Biblioteca

class View(
    private val biblioteca: Biblioteca
) {
   private val logger = logging()

    fun mostrarMenuGestionBiblioteca() {
        println("Bienvenido a la gestión de la biblioteca ${biblioteca.nombre}")

        var opcionElegida = -1

        do {
            println("\nMenú Gestión Biblioteca:")
            println("1. Obtener todos los libros")
            print("2. Buscar libro por ID")
            println("3. Crear libro")
            println("4. Modificar libro")
            println("5.Eliminar libro")
            println("6. Informe de la biblioteca")
            println("0. Salir")

            print("\nOpción elegida: ")
            opcionElegida = readln().toIntOrNull() ?: -1
            when (opcionElegida) {
                1 -> obtenerTodosLosLibros()
                2 -> buscarLibroPorId()
                3 -> crearLibro()
                4 -> modificarLibro()
                5 -> eliminarLibro()
                6 -> informeDeLaBiblioteca()
                0 -> println("Saliendo del menú...")
            }
        } while (opcionElegida != 0)
    }

    private fun buscarLibroPorId() {
        logger.debug{ "Buscando libro por ID" }
        print("Ingrese la ID del libro: ")
        val id = readln().toIntOrNull()?: -1
        if (id <= 0) {
            println("ID inválido. Intente nuevamente.")
            return
        }
        val libro = biblioteca.findById(id)
        if (libro == null) {
            println("Libro no encontrado.")
        } else {
            println("Libro encontrado:")
            println(libro)
        }
    }

    private fun obtenerTodosLosLibros() {
        logger.debug { "Mostrando todos los libros" }
        val res = biblioteca.getAll()
        if (res.isEmpty()) {
            println("No hay libros en la biblioteca.")
        } else {
            println("Libros en la biblioteca:")
            for (libro in res) {
                println(libro)
            }
        }
    }
}