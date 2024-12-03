package srangeldev.models

data class Informe(
    val totalLibros: Int,
    val librosDisponibles: Int,
    val librosPrestados: Int,
    val porcentajeDisponibles: Double,
    val porcentajePrestados: Double
) {

}