package kotlinslang.control

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.NoSuchElementException
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

class UtilTest {

    @Test
    fun toOptionForNullableShouldSuccess() {
        val nullable: Int? = null
        val none = nullable.toOption()
        assertThat(none.isEmpty()).isTrue()
        assertEquals(none, none())

        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { none.get() },
                message = "Call get() on None Throws NSEE"
        )

        val notNull: Int? = 90
        val some = notNull.toOption()
        assertThat(some.isEmpty()).isFalse()
        assertThat(some.get()).isEqualTo(notNull)

    }

    @Test
    fun noneMustProduceNoneOption() {
        val none = none<Int>()
        assertThat(none.isEmpty()).isTrue()
        assertThat(none.isDefined()).isFalse()
        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { none.get() },
                message = "Call get() on None Throws NSEE"
        )
    }

    @Test
    fun someMustProduceSomeOption() {
        val element = 10
        val some = some(element)
        assertThat(some.isEmpty()).isFalse()
        assertThat(some.isDefined()).isTrue()
        assertThat(some.get()).isEqualTo(element)
    }

    @Test
    fun optionWhenProductCorrectOptionBasedOnCondition() {
        val element = 42
        val none = optionWhen(condition = false, supplier = { element })
        assertThat(none.isEmpty()).isTrue()
        assertThat(none.isDefined()).isFalse()
        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { none.get() },
                message = "Call get() on None Throws NSEE"
        )

        val some = optionWhen(condition = true, supplier = { element })
        assertThat(some.isEmpty()).isFalse()
        assertThat(some.isDefined()).isTrue()
        assertThat(some.get()).isEqualTo(element)
    }

    @Test
    fun toOptionFromJavaOptionalShouldSuccess() {
        val nullable: Int? = null
        val none = Optional.ofNullable(nullable).toOption()
        assertThat(none.isEmpty()).isTrue()
        assertEquals(none, none())

        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { none.get() },
                message = "Call get() on None Throws NSEE"
        )

        val notNull: Int? = 90
        val some = Optional.ofNullable(notNull).toOption()
        assertThat(some.isEmpty()).isFalse()
        assertThat(some.get()).isEqualTo(notNull)
    }


    @Test
    fun rightMustProduceCorrectEither() {
        val value = 42
        val rightEither = right(value)
        assertThat(rightEither.right().isDefined()).isTrue()
        assertThat(rightEither.left().isEmpty()).isTrue()
        assertThat(rightEither.right().get()).isEqualTo(value)
        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { rightEither.left().get() }
        )
    }

    @Test
    fun leftMustProduceCorrectEither() {
        val value = 42
        val leftEither = left(value)
        assertThat(leftEither.left().isDefined()).isTrue()
        assertThat(leftEither.right().isEmpty()).isTrue()
        assertThat(leftEither.left().get()).isEqualTo(value)
        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { leftEither.right().get() }
        )
    }

    @Test
    fun successMustProduceCorrectTry() {
        val value = 42
        val successTry = success(value)
        assertThat(successTry.isDefined()).isTrue()
        assertThat(successTry.isSuccess()).isTrue()
        assertThat(successTry.isEmpty()).isFalse()
        assertThat(successTry.isFailure()).isFalse()
        assertThat(successTry.get()).isEqualTo(value)
        assertFailsWith(
                exceptionClass = UnsupportedOperationException::class,
                block = { successTry.getCause() }
        )
    }

    @Test
    fun failureMustProduceCorrectTry() {
        val cause = IllegalArgumentException("Failure With Illegal Argument")
        val failureTry = failure<Int>(cause)
        assertThat(failureTry.isDefined()).isFalse()
        assertThat(failureTry.isEmpty()).isTrue()
        assertThat(failureTry.isSuccess()).isFalse()
        assertThat(failureTry.isFailure()).isTrue()
        assertThat(failureTry.getCause()).isEqualTo(cause)
        assertFailsWith(
                exceptionClass = IllegalArgumentException::class,
                block = { failureTry.get() }
        )
    }

    @Test
    fun tryRunMustProduceCorrectTry() {
        val successTry = tryRun { println("Something not Throw Exception") }
        assertThat(successTry.isDefined()).isTrue()
        assertThat(successTry.isSuccess()).isTrue()
        assertThat(successTry.isEmpty()).isFalse()
        assertThat(successTry.isFailure()).isFalse()
        assertThat(successTry.get()).isEqualTo(Unit)
        assertFailsWith(
                exceptionClass = UnsupportedOperationException::class,
                block = { successTry.getCause() }
        )

        @Suppress("DIVISION_BY_ZERO")
        val failedTry = tryRun { println("Something Throw Exception ${3 / 0}") }
        assertThat(failedTry.isDefined()).isFalse()
        assertThat(failedTry.isSuccess()).isFalse()
        assertThat(failedTry.isEmpty()).isTrue()
        assertThat(failedTry.isFailure()).isTrue()
        assertThat(failedTry.getCause()).isInstanceOf(Throwable::class.java)
        assertThat(failedTry.getCause()).isExactlyInstanceOf(ArithmeticException::class.java)
        assertFailsWith(
                exceptionClass = ArithmeticException::class,
                block = { failedTry.get() }
        )

    }

    @Test
    fun tryOfMustProduceCorrectTry() {
        val successTry = tryOf { println("Something not Throw Exception") }
        assertThat(successTry.isDefined()).isTrue()
        assertThat(successTry.isSuccess()).isTrue()
        assertThat(successTry.isEmpty()).isFalse()
        assertThat(successTry.isFailure()).isFalse()
        assertThat(successTry.get()).isEqualTo(Unit)
        assertFailsWith(
                exceptionClass = UnsupportedOperationException::class,
                block = { successTry.getCause() }
        )

        @Suppress("DIVISION_BY_ZERO")
        val failedTry = tryOf { println("Something Throw Exception ${3 / 0}") }
        assertThat(failedTry.isDefined()).isFalse()
        assertThat(failedTry.isSuccess()).isFalse()
        assertThat(failedTry.isEmpty()).isTrue()
        assertThat(failedTry.isFailure()).isTrue()
        assertThat(failedTry.getCause()).isInstanceOf(Throwable::class.java)
        assertThat(failedTry.getCause()).isExactlyInstanceOf(ArithmeticException::class.java)
        assertFailsWith(
                exceptionClass = ArithmeticException::class,
                block = { failedTry.get() }
        )

        val value = 42
        val successTryOf = tryOf { value * 1 }
        assertThat(successTryOf.isDefined()).isTrue()
        assertThat(successTryOf.isSuccess()).isTrue()
        assertThat(successTryOf.isEmpty()).isFalse()
        assertThat(successTryOf.isFailure()).isFalse()
        assertThat(successTryOf.get()).isEqualTo(value)
        assertFailsWith(
                exceptionClass = UnsupportedOperationException::class,
                block = { successTryOf.getCause() }
        )

        @Suppress("DIVISION_BY_ZERO")
        val failedTryOf = tryOf { value * (3 / 0) }
        assertThat(failedTryOf.isDefined()).isFalse()
        assertThat(failedTryOf.isSuccess()).isFalse()
        assertThat(failedTryOf.isEmpty()).isTrue()
        assertThat(failedTryOf.isFailure()).isTrue()
        assertThat(failedTryOf.getCause()).isInstanceOf(Throwable::class.java)
        assertThat(failedTryOf.getCause()).isExactlyInstanceOf(ArithmeticException::class.java)
        assertFailsWith(
                exceptionClass = ArithmeticException::class,
                block = { failedTryOf.get() }
        )

    }

}