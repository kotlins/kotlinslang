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
    fun someMushProduceSomeOption() {
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
}