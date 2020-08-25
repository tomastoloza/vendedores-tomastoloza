package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class VendedorTest : DescribeSpec({
    val misiones = Provincia(1300000)
    val sanIgnacio = Ciudad(misiones)

    describe("Vendedor fijo") {
        val obera = Ciudad(misiones)
        val vendedorFijo = VendedorFijo(obera)

        describe("puedeTrabajarEn") {
            it("su ciudad de origen") {
                vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
            }
            it("otra ciudad") {
                vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
            }
        }

        describe("esInfluyente") {
            it("falso") {
                vendedorFijo.esInfluyente().shouldBeFalse()
            }
        }
    }

    describe("Viajante") {
        val cordoba = Provincia(2000000)
        val villaDolores = Ciudad(cordoba)
        var viajante = Viajante(listOf(misiones))

        describe("puedeTrabajarEn") {
            it("una ciudad que pertenece a una provincia habilitada") {
                viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
            }
            it("una ciudad que no pertenece a una provincia habilitada") {
                viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
            }
        }
        describe("esInfluyente") {
            it("falso con menos de 1000000") {
                viajante.esInfluyente().shouldBeFalse()
            }
            it("verdadero con mas de 1000000") {
                viajante = Viajante(listOf(Provincia(10000000), Provincia(20000000)))
                viajante.esInfluyente().shouldBeTrue()

            }
        }
    }

    describe("Comercio corresponsal") {
        val corresponsal = ComercioCorresponsal(listOf(sanIgnacio))
        describe("puedeTrabajarEn") {
            it("la ciudad definida") {
                corresponsal.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
            }
            it("otra ciudad") {

                corresponsal.puedeTrabajarEn(Ciudad(Provincia(10000))).shouldBeFalse()
            }
        }
    }

    describe("Vendedor") {
        val vendedor = VendedorFijo(sanIgnacio)
        describe("esVersatil") {
            it("no es versatil") {
                vendedor.esVersatil().shouldBeFalse()
            }
            it("es versatil") {
                vendedor.agregarCertificacion(Certificacion(true, 40))
                vendedor.agregarCertificacion(Certificacion(false, 40))
                vendedor.agregarCertificacion(Certificacion(false, 40))
                vendedor.esVersatil().shouldBeTrue()
            }
        }
    }


})
