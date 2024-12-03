package srangeldev.views

import com.github.ajalt.mordant.rendering.TextColors
import org.lighthousegames.logging.logging
import srangeldev.controllers.Biblioteca
import srangeldev.models.Libros

typealias color = TextColors

class View(
    private val biblioteca: Biblioteca
) {
    private val logger = logging()

    /**
     * Menu de gestión de la biblioteca
     *
     * 1. Obtener todos los libros
     * 2. Buscar libro por ID
     * 3. Crear libro
     * 4. Modificar libro
     * 5. Eliminar libro
     * 6. Informe de la biblioteca
     * 0. Salir
     *
     *
     * @return opcion elegida
     */
    fun mostrarMenuGestionBiblioteca() {
        println(color.brightYellow("Bienvenido a la gestión de la") + (" ") + color.brightBlue("${biblioteca.nombre} de ${biblioteca.ciudad}"))

        var opcionElegida = -1

        do {
            println(color.yellow("\nMenú Gestión de la Biblioteca:"))
            println(color.yellow("1. Obtener todos los libros"))
            println(color.yellow("2. Buscar libro por ID"))
            println(color.yellow("3. Crear libro"))
            println(color.yellow("4. Modificar libro"))
            println(color.yellow("5. Eliminar libro"))
            println(color.yellow("6. Informe de la biblioteca"))
            println(color.yellow("0. Salir"))

            print(color.brightYellow("\nOpción elegida: "))
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

    /**
     * Genera y muestra un informe de la biblioteca.
     */
    private fun informeDeLaBiblioteca() {
        logger.debug{"Generando informe de la biblioteca" }
        println("Informe de la biblioteca ${biblioteca.nombre}")
        val informe = biblioteca.getInforme()

        println(color.brightRed("\nInforme:"))
        println(color.blue("Total de libros: ${informe.totalLibros}"))
        println(color.blue("Libros disponibles: ${informe.librosDisponibles}"))
        println(color.blue("Libros prestados: ${informe.librosPrestados}"))
        println(color.blue("Porcentaje de libros disponibles: ${informe.porcentajeDisponibles}%"))
        println(color.blue("Porcentaje de libros prestados: ${informe.porcentajePrestados}%"))
    }

    /**
     * Elimina un libro de la biblioteca.
     */
    private fun eliminarLibro() {
        logger.debug{ "Eliminando libro" }
        println("Eliminando libro")

        val id = leerId()

        try {
            val libro = biblioteca.delete(id)
            println("Libro eliminado: ${libro?.titulo}")
        } catch (e: Exception) {
            println(TextColors.red("Error al eliminar el libro: ${e.message}"))
        }
    }

    /**
     * Lee y devuelve un ID ingresado por el usuario.
     *
     * @return ID del libro ingresado por el usuario.
     */
    private fun leerId(): Int {
        var idABuscar: Int
        do {
            print("Ingrese el ID del libro a buscar: ")
            idABuscar = readln().toIntOrNull()?: 0
            if (idABuscar == 0) {
                println("Debe ingresar un ID válido.")
            }
        } while (idABuscar <= 0)
        return idABuscar
    }

    /**
     * Modifica los datos de un libro existente.
     */
    private fun modificarLibro() {
        logger.debug{ "Modificando libro" }
        println("Modificando libro")

        val id = leerId()

        var libroAModificar: Libros? = null
        try {
            libroAModificar = biblioteca.findById(id)
            println("Libro encontrado:")
            println(libroAModificar)
        } catch (e: Exception) {
            println(TextColors.red("Error al buscar el libro: ${e.message}"))
            return
        }

        println("Nuevos datos del libro:")

        val tituloNuevo = actualizarTitulo()
        val autorNuevo = actualizarAutor()
        val anioPublicacionNuevo = actualizarAnioPublicacion()
        var isReservadoNuevo = actualizarReservado()

        val nuevoLibro = libroAModificar?.copy(
            titulo = if (tituloNuevo.isNotEmpty()) tituloNuevo else libroAModificar.titulo,
            autor = if (autorNuevo.isNotEmpty()) autorNuevo else libroAModificar.autor,
            anioPublicacion = if (anioPublicacionNuevo > 0) anioPublicacionNuevo else libroAModificar.anioPublicacion,
            isAvailable = if (isReservadoNuevo) false else libroAModificar.isAvailable,
        )

        try {
            val res = biblioteca.update(id, nuevoLibro)
            println("Libro modificado con éxito:")
            println(res)
        } catch (e: Exception) {
            println(TextColors.red("Error al modificar el libro: ${e.message}"))
            return
        }
    }

    /**
     * Actualiza el título de un libro.
     *
     * @return El nuevo título del libro.
     */
    private fun actualizarTitulo(): String {
        logger.debug{ "Actualizando título del libro" }
        var opcionTitulo = "s"
        var nuevoTitulo = ""
        do {
            print("Desea modificar el título del libro (s/n)? ")
            opcionTitulo = readln().toLowerCase()
        } while (opcionTitulo!= "s" && opcionTitulo!= "n")
        if (opcionTitulo == "s") {
            nuevoTitulo = leerTitulo()
        }
        return nuevoTitulo
    }

    /**
     * Actualiza el autor de un libro.
     *
     * @return El nuevo autor del libro.
     */
    private fun actualizarAutor(): String {
        logger.debug{ "Actualizando autor del libro" }
        var opcionAutor = "s"
        var nuevoAutor = ""
        do {
            print("Desea modificar el autor del libro (s/n)? ")
            opcionAutor = readln().toLowerCase()
        } while (opcionAutor!= "s" && opcionAutor!= "n")
        if (opcionAutor == "s") {
            nuevoAutor = leerAutor()
        }
        return nuevoAutor
    }

    /**
     * Actualiza el año de publicación de un libro.
     *
     * @return El nuevo año de publicación del libro.
     */
    private fun actualizarAnioPublicacion(): Int {
        logger.debug{ "Actualizando año de publicación del libro" }
        var opcionAnioPublicacion = "s"
        var nuevoAnioPublicacion = 0
        do {
            print("Desea modificar el año de publicación del libro (s/n)? ")
            opcionAnioPublicacion = readln().toLowerCase()
        } while (opcionAnioPublicacion!= "s" && opcionAnioPublicacion!= "n")
        if (opcionAnioPublicacion == "s") {
            nuevoAnioPublicacion = leerAnioPublicacion()
        }
        return nuevoAnioPublicacion
    }

    /**
     * Funcion para actualizar la disponibilidad del libro.
     *
     * @return Disponibilidad del libro.
     */
    private fun actualizarReservado(): Boolean {
        logger.debug{ "Actualizando estado de reservación del libro" }
        var opcionReservado = "s"
        var isReservado = false
        do {
            print("Desea modificar el estado de reservación del libro (s/n)? ")
            opcionReservado = readln().toLowerCase()
        } while (opcionReservado!= "s" && opcionReservado!= "n")
        if (opcionReservado == "s") {
            isReservado = leerReservado()
        }
        return isReservado
    }


    /**
     * Funcion para leer si el libro está disponible.
     * @return Disponibilidad del libro.
     */
    private fun leerReservado(): Boolean {
        logger.debug{ "Leyendo estado de reservación del libro" }
        var opcionReservado = "s"
        var isReservado = false
        do {
            print("Ingrese 'S' para establecer el libro como reservado o 'N' para no establecerlo: ")
            opcionReservado = readln().toLowerCase()
        } while (opcionReservado!= "s")
        isReservado = opcionReservado == "s"
        return isReservado
    }

    /**
     * Funcion para crear el libro en el menu.
     * @return Libro creado.
     * @throws Exception si no puede crear el libro.
     */
    private fun crearLibro() {
        logger.debug{ "Creando libro" }
        val titulo = leerTitulo()

        val autor = leerAutor()

        val anioPublicacion = leerAnioPublicacion()

        val genero = obtenerGenero()

        val libroCreado = Libros(
            titulo = titulo,
            autor = autor,
            genero = genero,
            anioPublicacion = anioPublicacion
        )
        try {
            val res = biblioteca.create(libroCreado)
            println("Libro creado con éxito:")
            println(res)
        } catch (e: Exception) {
            println(TextColors.red("Error al crear el libro: ${e.message}"))
        }
    }

    /**
     * Funcion para leer el año de publicación del libro.
     * @return Año de publicación del libro elegido.
     */
    private fun leerAnioPublicacion(): Int {
        var anioPublicacion = -1
        do {
            print("Ingrese el año de publicación del libro: ")
            anioPublicacion = readln().toIntOrNull()?: -1
            if (anioPublicacion <= 0) {
                println("Año de publicación inválido. Intente nuevamente.")
            }
        } while (anioPublicacion <= 0)
        return anioPublicacion
    }

    /**
     * Fucion para leer el autor del libro.
     * @return Autor del libro elegido.
     */
    private fun leerAutor(): String {
        var autor = ""
        do {
            print("Ingrese el autor del libro: ")
            autor = readln()
            if (autor.isEmpty()) {
                println("El autor del libro no puede estar vacío.")
            }
        } while (autor.isEmpty())
        return autor
    }

    /**
     * Funcion para leer el título del libro.
     * @return Título del libro elegido.
     */
    private fun leerTitulo(): String {
        var titulo = ""
        do {
            print("Ingresa el titulo del libro: ")
            titulo = readln()
            if (titulo.isEmpty()) {
                println("El título del libro no puede estar vacío.")
            }
        } while (titulo.isEmpty())
        return titulo
    }

    /**
     * Funcion para obtener el género del libro.
     * @return Género del libro elegido.
     */
    private fun obtenerGenero(): Libros.Genero {
        var opcionElegida: Int
    
        do {
            logger.debug { "Obteniendo género" }
            println("Seleccione el género del libro:")
            println("1. Literatura")
            println("2. Ciencia Ficción")
            println("3. Novela")
            println("4. Terror")
            println("5. Arte")
            println("6. Ciencia")
            print("\nOpción elegida: ")
            opcionElegida = readln().toIntOrNull() ?: -1
        } while (opcionElegida !in 1..6)
    
        return when (opcionElegida) {
            1 -> Libros.Genero.LITERATURA
            2 -> Libros.Genero.FICCION
            3 -> Libros.Genero.NOVELA
            4 -> Libros.Genero.TERROR
            5 -> Libros.Genero.ARTE
            6 -> Libros.Genero.CIENCIA
            else -> throw Exception("Opción inválida")
        }
    }

    /**
     * Funcion para buscar un libro por ID.
     * @return ID del libro a buscar.
     */
    private fun buscarLibroPorId() {
        logger.debug{ "Buscando libro por ID" }
        val idABuscar = leerId()

        try {
            val libro = biblioteca.findById(idABuscar)
            println("Libro encontrado:")
            println(libro)
        } catch (e: Exception) {
            println(TextColors.red("Error al buscar el libro: ${e.message}"))
        }
    }

    /**
     * Funcion para obtener todos los libros de la biblioteca.
     * @return Lista de libros en la biblioteca.
     */
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