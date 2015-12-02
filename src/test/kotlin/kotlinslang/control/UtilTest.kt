package kotlinslang.control

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.NoSuchElementException
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
}