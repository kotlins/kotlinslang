package kotlinslang.control

import kotlinslang.collection.emptyIterator
import kotlinslang.collection.iteratorOf
import kotlinslang.orElse
import kotlinslang.orElseGet
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.NoSuchElementException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

class OptionTest {

    @Test
    fun noneBehaveCorrectly() {
        val none = none<Int>()
        val nothing = none<Nothing>()
        val noneOption = optionOf<Int>(null)

        assertThat(none.equals(noneOption)).isTrue()
        assertThat(nothing.equals(nothing)).isTrue()
        assertThat(none.equals(42)).isFalse()
        assertThat(none.isEmpty()).isTrue()
        assertThat(none.isDefined()).isFalse()
        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { none.get() }
        )

        assertEquals(none, noneOption)
        assertEquals(none.hashCode(), noneOption.hashCode())
        assertEquals(none.toString(), noneOption.toString())
        assertEquals(None, None)

        val otherElement = 142
        assertEquals(none.orElse(otherElement), otherElement)
        assertEquals(none.orElseGet { otherElement }, otherElement)
        assertFailsWith(
                exceptionClass = NullPointerException::class,
                block = { none.orElseThrow { -> NullPointerException("NPE") } }
        )

        assertEquals(none.filter { t -> true }, none)
        assertEquals(none.filter { t -> false }, none)
        none.peek { t -> assertTrue(false, "Must Not reached") }

        assertEquals(none.iterator(), emptyIterator())

        assertEquals(none.map { t -> t + 1 }, none)

        assertEquals(none.flatMap { t -> some(t + 1) }, none)

        assertEquals(none.transform { null }, null)

    }

    @Test
    fun someBehaveCorrectly() {
        val element = 42
        val some = some(element)
        val someOption = optionOf(element)

        assertThat(some.equals(someOption)).isTrue()
        assertThat(some.isEmpty()).isFalse()
        assertThat(some.isDefined()).isTrue()

        assertEquals(some.get(), element)

        assertEquals(some, someOption)
        assertThat(some.equals(42)).isFalse()
        assertThat(some.equals(null)).isFalse()
        assertThat(some.equals(some(142))).isFalse()
        assertThat(some.equals(some(42.0))).isFalse()
        assertEquals(some.hashCode(), someOption.hashCode())
        assertEquals(some.toString(), someOption.toString())


        val otherElement = 142
        assertEquals(some.orElse(otherElement), element)
        assertEquals(some.orElseGet { otherElement }, element)
        assertEquals(some.orElseThrow { -> NullPointerException("NPE") }, element)

        assertEquals(some.filter { t -> true }, some)
        assertEquals(some.filter { t -> false }, none())

        var reached = false
        some.peek { t -> reached = true }
        assertThat(reached).isTrue()

        assertEquals(some.iterator().next(), iteratorOf(element).next())

        assertEquals(some.map { t -> t + 1 }, some(element + 1))

        assertEquals(some.flatMap { t -> some(t + 1) }, some(element + 1))
        assertEquals(some.flatMap { t -> listOf(t + 1) }, some(element + 1))
        assertEquals(some.flatMap { t -> listOf<Int>() }, none<Int>())

        val transformed = some.transform { listOf(it.get()) }
        assertThat(transformed).isInstanceOf(List::class.java)

    }
}