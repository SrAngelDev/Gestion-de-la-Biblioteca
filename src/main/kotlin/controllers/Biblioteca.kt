package srangeldev.controllers

import org.lighthousegames.logging.logging
import srangeldev.models.Informe
import srangeldev.models.Libros
import java.time.LocalDateTime

const val MAX_LIBROS = 1000

class Biblioteca(
    val id: Int = NEW_ID,
    val nombre: String,
    val ciudad: String,
    val telefono: String,
) {
    private val logger = logging()
    private val libros: Array<Libros?> = arrayOfNulls(MAX_LIBROS)
    private var numLibros: Int = 0

    companion object {
        const val NEW_ID = -1
        private var nextId = 1
        fun getNextId(): Int = nextId++
    }

    fun getAll(): Array<Libros> {
        logger.debug { "Obteniendo todos los libros" }
        return getLibrosSinNulls()
    }

    fun getLibrosSinNulls(): Array<Libros> {
        logger.debug { "Obteniendo los libros sin nulos" }
        val librosSinNulos = Array<Libros?>(numLibros) { null }
        var index = 0
        for (libro in libros) {
            if (libro!= null) {
                librosSinNulos[index] = libro
                index++
            }
        }
        return librosSinNulos as Array<Libros>
    }

    fun findById(id: Int): Libros? {
        logger.debug { "Buscando libro por ID: $id" }
        for (libro in libros) {
            if (libro?.id == id) {
                return libro
            }
        }
        logger.error{"Libro con ID $id no encontrado"}
        throw Exception("Libro con ID $id no encontrado")
    }

    fun create(libro: Libros): Libros {
        logger.debug { "Creando libro: $libro en la biblioteca $nombre" }
        val indexLibroVacio = findEmptyIndex()
        val timeStamp = LocalDateTime.now()
        val nuevoLibro = libro.copy(
            id = Libros.getNextId(),
            createdAt = timeStamp,
            updatedAt = timeStamp
        )
        libros[indexLibroVacio] = nuevoLibro
        numLibros++
        return nuevoLibro
    }

    fun update(id: Int, libro: Libros): Libros? {
        logger.debug { "Actualizando libro con ID: $id en la biblioteca $nombre" }
        for (i in libros.indices) {
            if (libros[i]?.id == id) {
                val libroActualizado = libro.copy(
                    id = id,
                    createdAt = libros[i]!!.createdAt,
                    updatedAt = LocalDateTime.now()
                )
                libros[i] = libroActualizado
                return libroActualizado
            }
        }
        logger.error{"Libro con ID $id no encontrado"}
        throw Exception("Libro con ID $id no encontrado")
    }

    fun delete(id: Int): Libros? {
        logger.debug { "Eliminando libro con ID: $id en la biblioteca $nombre" }
        for (i in libros.indices) {
            if (libros[i]?.id == id) {
                val libroAEliminar = libros[i]!!.copy(
                    id = id,
                    updatedAt = LocalDateTime.now(),
                    isAvailable = false
                )
                libros[i] = null
                numLibros--
                return libroAEliminar
            }
        }
        logger.error{"Libro con ID $id no encontrado"}
        throw Exception("Libro con ID $id no encontrado")
    }
    fun getInforme(): Informe {
        TODO("Hacer el informe aquí")
    }
    private fun findEmptyIndex(): Int {
        logger.debug { "Buscando índice vacío para el libro" }
        for (i in libros.indices) {
            if (libros[i] == null) {
                return i
            }
        }
        logger.error{"No hay espacio para más libros en la biblioteca $nombre"}
        throw Exception("No hay espacio para más libros en la biblioteca $nombre")
    }
}