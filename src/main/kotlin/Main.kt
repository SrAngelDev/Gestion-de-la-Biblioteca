package srangeldev

import org.lighthousegames.logging.logging
import srangeldev.controllers.*
import srangeldev.models.*
import srangeldev.views.View


private val logger = logging()

fun main() {
    val biblioteca = Biblioteca(
        id = Biblioteca.getNextId(),
        nombre = "Biblioteca IES Luis Vives",
        ciudad = "Leganes",
        telefono = "987654321",
    )

    val view = View(biblioteca)

    view.mostrarMenuGestionBiblioteca()
}