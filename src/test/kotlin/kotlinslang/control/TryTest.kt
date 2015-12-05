package kotlinslang.control

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.NoSuchElementException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

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
        assertNotEquals(failure, success(42))
        assertThat(failure.equals(10)).isFalse()
        assertThat(failure.equals(null)).isFalse()
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
        assertNotEquals(success, failure(NullPointerException("NPE")))
        assertThat(success.equals(10)).isFalse()
        assertThat(success.equals(success(142))).isFalse()
        assertThat(success.equals(null)).isFalse()
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

    @Test
    fun failureOrElseBehaveCorrectly() {
        val elseElement = 42
        val exception = ArithmeticException("Arithmetic")
        val failure = failure<Int>(exception)

        assertEquals(failure.orElse(elseElement), elseElement)
        assertEquals(failure.orElseGet { -> elseElement }, elseElement)
        assertEquals(failure.orElseGet { t -> elseElement }, elseElement)
        assertFailsWith(
                exceptionClass = NullPointerException::class,
                block = { failure.orElseThrow { -> NullPointerException("NPE") } }
        )
        assertFailsWith(
                exceptionClass = NullPointerException::class,
                block = { failure.orElseThrow { t -> NullPointerException("NPE") } }
        )
        var reached = false
        failure.orElseRun { t -> reached = true }
        assertTrue(reached, "Or Else Run Must Invoked")

    }

    @Test
    fun successOrElseBehaveCorrectly() {
        val element = 42
        val elseElement = 142
        val success = success(element)

        assertEquals(success.orElse(elseElement), element)
        assertEquals(success.orElseGet { -> elseElement }, element)
        assertEquals(success.orElseGet { t -> elseElement }, element)
        assertEquals(success.orElseThrow { -> NullPointerException("NPE") }, element)
        assertEquals(success.orElseThrow { t -> NullPointerException("NPE") }, element)
        success.orElseRun { t -> assertTrue(false, "Must not Reached") }
    }

    @Test
    fun toEitherBehaveCorrectly() {
        val exception = ArithmeticException("Arithmetic")
        val failure = failure<Int>(exception)

        val element = 42
        val success = success(element)

        assertThat(failure.toEither()).isExactlyInstanceOf(Left::class.java)
        assertThat(success.toEither()).isExactlyInstanceOf(Right::class.java)

    }

    @Test
    fun andThenBehaveCorrectly() {
        val exception = ArithmeticException("Arithmetic")
        val failure = failure<Int>(exception)

        val element = 42
        val success = success(element)

        failure.andThen { t -> assertTrue(false, "Must not Reached") }
        failure.andThen { -> assertTrue(false, "Must not Reached") }

        var andThenReached = false
        success.andThen { t -> andThenReached = true }
        assertTrue(andThenReached)

        andThenReached = false
        success.andThen { -> andThenReached = true }
        assertTrue(andThenReached)
    }

    @Test
    fun failedBehaveCorrectly() {
        val exception = ArithmeticException("Arithmetic")
        val failure = failure<Int>(exception)

        val element = 42
        val success = success(element)

        assertThat(failure.failed()).isInstanceOf(Success::class.java)
        assertThat(success.failed()).isInstanceOf(Failure::class.java)

    }

    @Test
    fun filterBehaveCorrectly() {
        val exception = ArithmeticException("Arithmetic")
        val failure = failure<Int>(exception)

        val element = 42
        val success = success(element)

        assertEquals(failure.filter { t -> true }, failure)
        assertEquals(success.filter { t -> true }, success)

        assertEquals(failure.filter { t -> false }, failure)
        assertThat(success.filter { t -> false }).isInstanceOf(Failure::class.java)

        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { success.filter { t -> false }.get() }
        )

        assertThat(success.filter { t -> throw IllegalArgumentException("IAE") })
                .isInstanceOf(Failure::class.java)

        assertFailsWith(
                exceptionClass = IllegalArgumentException::class,
                block = { success.filter { t -> throw IllegalArgumentException("IAE") }.get() }
        )

    }

    @Test
    fun peekBehaveCorrectly() {
        val exception = ArithmeticException("Arithmetic")
        val failure = failure<Int>(exception)

        val element = 42
        val success = success(element)

        assertThat(failure.peek { t -> assertTrue(false, "Must not Reached") }).isEqualTo(failure)

        var reached = false
        assertThat(success.peek { t -> reached = true }).isEqualTo(success)
        assertTrue(reached)

    }

}