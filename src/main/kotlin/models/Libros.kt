package srangeldev.models

import java.time.LocalDateTime

/**
 * Clase que representa un libro en la biblioteca.
 * @property id Identificador del libro.
 * @property titulo Título del libro.
 * @property autor Autor del libro.
 * @property genero Género del libro.
 * @property anioPublicacion Año de publicación del libro.
 * @property createdAt Fecha de creación del libro.
 * @property updatedAt Fecha de actualización del libro.
 * @property isAvailable Indicador de disponibilidad del libro.
 */
data class Libros(
    val id: Int = NEW_ID,
    val titulo: String,
    val autor: String,
    val genero: Genero,
    val anioPublicacion: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isAvailable: Boolean = true,
) {
    companion object {
        const val NEW_ID = 1
        private var nextId = 1
        fun getNextId(): Int = nextId++
    }

    /**
     * Enumeracion de los generos de libros.
     * @property LITERATURA Libro de literatura.
     * @property FICCION Libro de ficción.
     * @property NOVELA Libro de novela.
     * @property TERROR Libro de terror.
     * @property ARTE Libro de arte.
     * @property CIENCIA Libro de ciencia.
     */
    enum class Genero {
        LITERATURA,
        FICCION,
        NOVELA,
        TERROR,
        ARTE,
        CIENCIA,
    }
}