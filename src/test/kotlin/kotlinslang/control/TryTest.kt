package kotlinslang.control

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

class TryTest {

    @Test
    fun failureBehaveCorrectly() {
        val exception = ArithmeticException("Arithmetic")
        val failure = failure<Int>(exception)
        val failureTry = tryOf { throw exception }

        assertThat(failure.equals(failure)).isTrue()
        assertThat(failure.equals(failureTry)).isTrue()
        assertThat(failure.equals(10)).isFalse()
        assertThat(failure.equals(failure<Double>(NullPointerException("NPE"))))

        assertThat(failure.isEmpty()).isTrue()
        assertThat(failure.isDefined()).isFalse()
        assertThat(failure.isFailure()).isTrue()
        assertThat(failure.isSuccess()).isFalse()

        assertEquals(failure.hashCode(), failureTry.hashCode())
        assertEquals(failure.toString(), failureTry.toString())

        assertThat(failure.getCause()).isInstanceOf(ArithmeticException::class.java)
        assertFailsWith(
                exceptionClass = ArithmeticException::class,
                block = { failure.get() }
        )

    }

    @Test
    fun successBehaveCorrectly() {
        val element = 46
        val success = success(element)
        val successTry = tryOf { element }

        assertThat(success.equals(success)).isTrue()
        assertThat(success.equals(successTry)).isTrue()
        assertThat(success.equals(10)).isFalse()
        assertThat(success.equals(success(element)))

        assertThat(success.isEmpty()).isFalse()
        assertThat(success.isDefined()).isTrue()
        assertThat(success.isFailure()).isFalse()
        assertThat(success.isSuccess()).isTrue()

        assertEquals(success.hashCode(), successTry.hashCode())
        assertEquals(success.toString(), successTry.toString())

        assertThat(success.get()).isEqualTo(element)
        assertFailsWith(
                exceptionClass = UnsupportedOperationException::class,
                block = { success.getCause() }
        )
    }
}