package srangeldev.models

import java.time.LocalDateTime

data class Libros(
    val id: Int = NEW_ID,
    val titulo: String,
    val autor: String,
    val genero: Genero,
    val anioPublicacion: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isReserved: Boolean = false,
) {
    override fun toString(): String {
        return ("Libro(id=$id, titulo='$titulo', autor='$autor', genero='$genero', anioPublicacion=$anioPublicacion, createdAt=$createdAt, updatedAt=$updatedAt)")
    }
    companion object {
        const val NEW_ID = 1
        private var nextId = 1
        fun getNextId(): Int = nextId++
    }

    val isAvailable: Boolean
        get() = isReserved == false

    enum class Genero {
        LITERATURA,
        FICCION,
        NOVELA,
        TECNOLOGIA,
        ARTE,
        CIENCIA,
    }
}