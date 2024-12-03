package srangeldev.controllers

import org.lighthousegames.logging.logging
import srangeldev.models.Informe
import srangeldev.models.Libros
import java.time.LocalDateTime

const val MAX_LIBROS = 1000

/*+
 * Clase que representa una biblioteca.
 *
 * @property id Identificador único de la biblioteca.
 * @property nombre Nombre de la biblioteca.
 * @property ciudad Ciudad donde se encuentra la biblioteca.
 */
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

    /**
     * Obtiene todos los libros sin nulos.
     *
     * @return Array de libros sin nulos.
     */
    fun getAll(): Array<Libros> {
        logger.debug { "Obteniendo todos los libros" }
        return getLibrosSinNulls()
    }

    /**
     * Obtiene los libros del array sin nulos.
     *
     * @return Array de libros sin nulos.
     */
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

    /**
     * Busca un libro por su ID.
     *
     * @param id ID del libro a buscar.
     * @return El libro encontrado.
     * @throws Exception si no encuentra el libro.
     */
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

    /**
     * Crea un nuevo libro en la biblioteca.
     *
     * @param libro Libro a crear.
     * @return El libro creado con su ID asignado.
     */
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

    /**
     * Actualiza un libro existente en la biblioteca.
     *
     * @param id ID del libro a actualizar.
     * @param libro Datos del libro a actualizar.
     * @return El libro actualizado.
     * @throws Exception si no se encuentra el libro.
     */
    fun update(id: Int, libro: Libros?): Libros? {
        logger.debug { "Actualizando libro con ID: $id en la biblioteca $nombre" }
        for (i in libros.indices) {
            if (libros[i]?.id == id) {
                val libroActualizado = libro?.copy(
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

    /**
     * Elimina un libro de la biblioteca.
     *
     * @param id ID del libro a eliminar.
     * @return El libro eliminado.
     * @throws Exception si no se encuentra el libro.
     */
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

    /**
     * Genera y devuelve un informe de la biblioteca.
     *
     * @return Informe de la biblioteca.
     */
    fun getInforme(): Informe {
        logger.debug { "Generando informe de la biblioteca $nombre" }
        val totalLibros = numLibros
        val librosDisponibles = isAvailableLibros()
        val librosPrestados = numLibros - librosDisponibles
        val porcentajeDisponibles = (librosDisponibles.toDouble() / totalLibros.toDouble()) * 100
        val porcentajePrestados = (librosPrestados.toDouble() / totalLibros.toDouble()) * 100

        return Informe(
            totalLibros = totalLibros,
            librosDisponibles = librosDisponibles,
            librosPrestados = numLibros - librosDisponibles,
            porcentajeDisponibles = porcentajeDisponibles,
            porcentajePrestados = porcentajePrestados
        )
    }

    /**
     * Comprueba la cantidad de libros disponibles.
     *
     * @return La cantidad de libros disponibles.
     */
    private fun isAvailableLibros(): Int {
        logger.debug { "Comprobando libros disponibles" }
        var librosDisponibles = 0
        for (libro in libros) {
            if (libro!= null && libro.isAvailable) {
                librosDisponibles++
            }
        }
        return librosDisponibles
    }

    /**
     * Busca un índice vacío en el array de libros.
     *
     * @return El índice vacío encontrado.
     * @throws Exception Si no hay espacio para más libros en la biblioteca.
     */
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
