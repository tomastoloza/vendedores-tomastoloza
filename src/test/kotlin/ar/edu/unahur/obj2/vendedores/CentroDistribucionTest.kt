package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class CentroDistribucionTest : DescribeSpec({
    val misiones = Provincia(1300000)
    val sanIgnacio = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(sanIgnacio)


    describe("Centro de distribucion") {

        describe("Se crea un centro") {
            val centro = CentroDistribucion(sanIgnacio)
            it("empieza con 0 vendedores") {
                centro.getVendedores().size.shouldBe(0)
            }
            it("se agrega 1 vendedor") {
                centro.agregarVendedor(vendedorFijo)
                centro.getVendedores().size.shouldBe(1)
            }
        }

        describe("getVendedorEstrella") {
            val centro = CentroDistribucion(sanIgnacio)
            it("cuando no hay vendedores") {
                centro.getVendedorEstrella().shouldBeNull()
            }
            it("cuando se agrega un vendedor") {
                centro.agregarVendedor(vendedorFijo)
                centro.getVendedorEstrella().shouldBe(vendedorFijo)
            }
            it("cuando hay dos vendedores") {
                val vendedor0 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor1 = VendedorFijo(Ciudad(Provincia(10000)))
                vendedor0.agregarCertificacion(Certificacion(true, 10))
                centro.agregarVendedor(vendedor1)
                centro.agregarVendedor(vendedor0)
                centro.getVendedorEstrella().shouldBe(vendedor0)
            }
        }
        describe("puedeCubrir") {
            val centro = CentroDistribucion(sanIgnacio)
            centro.agregarVendedor(vendedorFijo)
            it("cubre su ciudad de origen") {
                centro.puedeCubrir(sanIgnacio).shouldBeTrue()
            }
            it("no cubre otra ciudad") {
                centro.puedeCubrir(Ciudad(Provincia(10))).shouldBeFalse()
            }
        }
        describe("getVendedoresGenericos") {
            val centro = CentroDistribucion(sanIgnacio)
            val vendedor0 = VendedorFijo(Ciudad(Provincia(10000)))
            centro.agregarVendedor(vendedor0)

            it("no hay vendedor generico") {
                vendedor0.agregarCertificacion(Certificacion(true, 10))
                centro.getVendedoresGenericos().shouldBeEmpty()
            }
            it("tiene un vendedor generico") {
                vendedor0.agregarCertificacion(Certificacion(false, 10))
                centro.getVendedoresGenericos().size.shouldBe(1)
            }

        }

        describe("esRobusto") {


            it("no es robusto sin vendedores") {
                val centro = CentroDistribucion(sanIgnacio)
                val vendedor0 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor1 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor2 = VendedorFijo(Ciudad(Provincia(10000)))
                centro.esRobusto().shouldBeFalse()
            }

            it("no es robusto con vendedores") {
                val centro = CentroDistribucion(sanIgnacio)
                val vendedor0 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor1 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor2 = VendedorFijo(Ciudad(Provincia(10000)))
                centro.agregarVendedor(vendedor0)
                centro.agregarVendedor(vendedor1)
                centro.agregarVendedor(vendedor2)
                centro.esRobusto().shouldBeFalse()

            }
            it("no es robusto con vendedores certificados de bajo puntaje") {
                val centro = CentroDistribucion(sanIgnacio)
                val vendedor0 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor1 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor2 = VendedorFijo(Ciudad(Provincia(10000)))
                vendedor0.agregarCertificacion(Certificacion(false, 10))
                vendedor1.agregarCertificacion(Certificacion(false, 10))
                vendedor2.agregarCertificacion(Certificacion(false, 10))
                centro.agregarVendedor(vendedor0)
                centro.agregarVendedor(vendedor1)
                centro.agregarVendedor(vendedor2)
                centro.esRobusto().shouldBeFalse()
            }
            it("es robusto") {
                val centro = CentroDistribucion(sanIgnacio)
                val vendedor0 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor1 = VendedorFijo(Ciudad(Provincia(10000)))
                val vendedor2 = VendedorFijo(Ciudad(Provincia(10000)))
                vendedor0.agregarCertificacion(Certificacion(false, 40))
                vendedor1.agregarCertificacion(Certificacion(false, 50))
                vendedor2.agregarCertificacion(Certificacion(false, 60))
                centro.agregarVendedor(vendedor0)
                centro.agregarVendedor(vendedor1)
                centro.agregarVendedor(vendedor2)
                centro.esRobusto().shouldBeTrue()
            }
        }
    }
})