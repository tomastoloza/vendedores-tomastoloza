package ar.edu.unahur.obj2.vendedores

import java.lang.Exception

class CentroDistribucion(val ciudad: Ciudad) {
    private val vendedores = mutableListOf<Vendedor>()
    fun agregarVendedor(vendedor: Vendedor) {
        if (this.vendedores.contains(vendedor)) {
            throw Exception("Vendedor ya existe")
        } else {
            this.vendedores.add(vendedor)
        }
    }

    // el vendedor estrella, que es el que tiene mayor puntaje total por certificaciones.
    fun getVendedorEstrella(): Vendedor? = this.vendedores.maxBy { vendedor -> vendedor.puntajeCertificaciones() }

    // si puede cubrir, o no, una ciudad dada. La condición es que al menos uno de los vendedores registrados pueda trabajar en esa ciudad.
    fun puedeCubrir(ciudad: Ciudad): Boolean {
        for (vendedor in this.vendedores) {
            if (vendedor.puedeTrabajarEn(ciudad)) {
                return true
            }
        }
        return false
    }

    //la colección de vendedores genéricos registrados. Un vendedor se considera genérico si tiene al menos una certificación que no es de productos.
    fun getVendedoresGenericos(): MutableList<Vendedor> {
        val vendedoresGenericos = mutableListOf<Vendedor>()
        for (vendedor in this.vendedores) {
            if (vendedor.otrasCertificaciones() > 0) {
                vendedoresGenericos.add(vendedor)
            }
        }
        return vendedoresGenericos
    }

    //si es robusto, la condición es que al menos 3 de sus vendedores registrados sea firme.
    fun esRobusto(): Boolean {
        return this.vendedores.count { vendedor -> vendedor.esFirme() } >= 3
    }

    fun getVendedores() : MutableList<Vendedor> = this.vendedores

}