package srangeldev.controllers

import org.lighthousegames.logging.logging
import srangeldev.models.Libros

const val MAX_LIBROS = 1000

class Biblioteca(
    val id: Int = NEW_ID,
    val nombre: String,
    val ciudad: String,
) {
    private val logger = logging()
    private val libros: Array<Libros?> = arrayOfNulls(MAX_LIBROS)
    private var numLibros: Int = 0

    companion object {
        const val NEW_ID = -1
        private var nextId = 1
        fun getNextId(): Int = nextId++
    }
}