package srangeldev.models

/**
 * Clase que representa un informe de la biblioteca.
 * @property totalLibros Total de libros en la biblioteca.
 * @property librosDisponibles Total de libros disponibles en la biblioteca.
 * @property librosPrestados Total de libros prestados en la biblioteca.
 * @property porcentajeDisponibles Porcentaje de libros disponibles en la biblioteca.
 * @property porcentajePrestados Porcentaje de libros prestados en la biblioteca.
 */
data class Informe(
    val totalLibros: Int,
    val librosDisponibles: Int,
    val librosPrestados: Int,
    val porcentajeDisponibles: Double,
    val porcentajePrestados: Double
) {

}