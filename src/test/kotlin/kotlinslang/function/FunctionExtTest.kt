package kotlinslang.function

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertFailsWith


/**
 * Test for FunctionExt
 *
 * @author Deny Prasetyo.
 */

class FunctionExtTest {

    data class RawMaterial(val id: Int, val description: String, val price: Double)
    data class RawProduct(val id: Int, val description: String, val price: Double)
    data class Product(val id: Int, val description: String, val price: Double)

    @Test
    fun composeExecuteParamCorrectly() {
        val before = { d: RawMaterial -> RawProduct(d.id, "Raw Product from Material of " + d.description, (d.price * 1.2)) }
        val funcOne = { d: RawProduct -> Product(d.id, "Product from " + d.description, (d.price * 1.4)) }
        val composedFunction = funcOne.compose(before)

        val rawMaterial = RawMaterial(42, "T Shirt", 1000.0)
        val product = composedFunction(rawMaterial)

        assertThat(product.id).isEqualTo(rawMaterial.id)
        assertThat(product.price).isEqualTo(rawMaterial.price * 1.2 * 1.4)
        assertThat(product.description).startsWith("Product from ").contains("Raw Product").contains(rawMaterial.description)
    }

    @Test
    fun composeWithExceptionWilThrowException() {
        val before = { d: Int -> d / 0 }
        val funcOne = { d: Int -> "String Value of Integer => $d" }
        val composedFunction = funcOne.compose(before)

        val param = 42

        assertFailsWith(
                exceptionClass = ArithmeticException::class,
                block = { composedFunction(param) },
                message = "Must Throws Exception"
        )
    }


}