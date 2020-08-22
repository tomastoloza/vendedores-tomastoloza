package ar.edu.unahur.obj2.vendedores

class Certificacion(val esDeProducto: Boolean, val puntaje: Int)

abstract class Vendedor {
    // Acá es obligatorio poner el tipo de la lista, porque como está vacía no lo puede inferir.
    // Además, a una MutableList se le pueden agregar elementos
    private val certificaciones = mutableListOf<Certificacion>()

    // Definimos el método abstracto.
    // Como no vamos a implementarlo acá, es necesario explicitar qué devuelve.
    abstract fun puedeTrabajarEn(ciudad: Ciudad): Boolean

    // En las funciones declaradas con = no es necesario explicitar el tipo
    fun esVersatil() =
            certificaciones.size >= 3
                    && this.certificacionesDeProducto() >= 1
                    && this.otrasCertificaciones() >= 1

    // Si el tipo no está declarado y la función no devuelve nada, se asume Unit (es decir, vacío)
    fun agregarCertificacion(certificacion: Certificacion) {
        certificaciones.add(certificacion)
    }

    fun esFirme() = this.puntajeCertificaciones() >= 30

    fun certificacionesDeProducto() = certificaciones.count { it.esDeProducto }
    fun otrasCertificaciones() = certificaciones.count { !it.esDeProducto }

    fun puntajeCertificaciones() = certificaciones.sumBy { c -> c.puntaje }

    abstract fun esInfluyente(): Boolean
}

// En los parámetros, es obligatorio poner el tipo
class VendedorFijo(val ciudadOrigen: Ciudad) : Vendedor() {
    override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
        return ciudad == ciudadOrigen
    }

    override fun esInfluyente() = false

    fun getCiudadEnLaQueVive() = this.ciudadOrigen
}


// A este tipo de List no se le pueden agregar elementos una vez definida
class Viajante(val provinciasHabilitadas: List<Provincia>) : Vendedor() {
    override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
        return provinciasHabilitadas.contains(ciudad.provincia)
    }

    //    viajante: la población total sumando todas las provincias donde está habilitado, debe ser de 10 millones o superior.
    override fun esInfluyente(): Boolean {
        return provinciasHabilitadas.sumBy { provincia -> provincia.poblacion } > 10000000
    }
}

class ComercioCorresponsal(val ciudades: List<Ciudad>) : Vendedor() {
    override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
        return ciudades.contains(ciudad)
    }

    //comercio corresponsal: debe tener sucursales en al menos 5 ciudades, o bien en al menos 3 provincias considerando la provincia de cada ciudad donde tiene sucursal.
    override fun esInfluyente(): Boolean {
        val provincias = this.ciudades.filter { true }
        return this.ciudades.size > 5 || provincias.size > 3

    }
}
