package srangeldev

import org.lighthousegames.logging.logging
import srangeldev.controllers.*
import srangeldev.models.*
import srangeldev.views.View


private val logger = logging()

/**
 * Función principal que inicializa la biblioteca y muestra el menú de gestión.
 * @author Angel Sanchez Gasanz
 */
fun main() {
    val biblioteca = Biblioteca(
        id = Biblioteca.getNextId(),
        nombre = "Biblioteca IES Luis Vives",
        ciudad = "Leganes",
    )

    val view = View(biblioteca)

    view.mostrarMenuGestionBiblioteca()
}