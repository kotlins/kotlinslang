package kotlinslang

import kotlinslang.control.failure
import kotlinslang.control.optionOf
import kotlinslang.control.some
import kotlinslang.control.success
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.NoSuchElementException
import kotlin.test.assertFailsWith


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

class ValueTest {

    @Test
    fun staticGetBehaveCorrectly() {
        val value = 42
        val option = optionOf(value)
        assertThat(Value.get(option)).isEqualTo(value)

        val list = listOf(value)
        assertThat(Value.get(list)).isEqualTo(value)
    }

    @Test
    fun getOptionBehaveCorrectly() {
        val element = 42
        val option = optionOf(element)
        assertThat(option.toOption()).isEqualTo(option)
        assertThat(option.getOption()).isEqualTo(option)

        val success = success(element)
        assertThat(success.toOption()).isEqualTo(option)
        assertThat(success.getOption()).isEqualTo(option)
    }

    @Test
    fun toTryBehaveCorrectly() {
        val element = 42
        val option = optionOf(element)
        val expected = success(element)

        val tryFromOption = option.toTry()
        assertThat(tryFromOption).isEqualTo(expected)
        assertThat(tryFromOption.get()).isEqualTo(element)

        assertThat(expected.toTry()).isEqualTo(expected)

        val none = optionOf(null)

        val tryFromNone = none.toTry()
        assertThat(tryFromNone.getCause()).isExactlyInstanceOf(NoSuchElementException::class.java)
        assertThat(tryFromNone.toTry()).isEqualTo(tryFromNone)
        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { tryFromNone.get() }
        )
    }

    @Test
    fun orElseFromEmptyBehaveCorrectly() {
        val elseValue = 42
        val none = optionOf<Int>(null)
        val failure = failure<Int>(ArithmeticException("Arithmetic Exception"))

        assertThat(none.orElse(elseValue)).isEqualTo(elseValue)
        assertThat(failure.orElse(elseValue)).isEqualTo(elseValue)

        assertThat(none.orElseGet({ elseValue })).isEqualTo(elseValue)
        assertThat(failure.orElseGet({ Unit -> elseValue })).isEqualTo(elseValue)
        assertThat(failure.orElseGet({ t: Throwable -> elseValue })).isEqualTo(elseValue)

        assertFailsWith(
                exceptionClass = NullPointerException::class,
                block = { none.orElseThrow({ NullPointerException("NPE") }) }
        )

        assertFailsWith(
                exceptionClass = NullPointerException::class,
                block = { failure.orElseThrow({ t -> NullPointerException("NPE") }) }
        )

    }

    @Test
    fun orElseFromNonEmptyBehaveCorrectly() {
        val element = 42
        val some = optionOf(element)
        val success = success(element)

        assertThat(some.orElse(element)).isEqualTo(element)
        assertThat(success.orElse(element)).isEqualTo(element)

        assertThat(some.orElseGet({ element })).isEqualTo(element)
        assertThat(success.orElseGet({ Unit -> element })).isEqualTo(element)
        assertThat(success.orElseGet({ t: Throwable -> element })).isEqualTo(element)

        assertThat(some.orElseThrow { NullPointerException("NPE") }).isEqualTo(element)
        assertThat(success.orElseThrow({ t -> NullPointerException("NPE") })).isEqualTo(element)

    }


    @Test
    fun ifDefineIfEmptyFromEmptyBehaveCorrectly() {

        val falseValue = 24
        val trueValue = 42
        val none = optionOf<Int>(null)
        val failure = failure<Int>(ArithmeticException("Arithmetic Exception"))

        assertThat(none.ifDefined(trueValue, falseValue)).isEqualTo(falseValue)
        assertThat(none.ifDefined({ trueValue }, { falseValue })).isEqualTo(falseValue)
        assertThat(none.ifDefined(trueValue, falseValue)).isNotEqualTo(trueValue)
        assertThat(none.ifDefined({ trueValue }, { falseValue })).isNotEqualTo(trueValue)

        assertThat(failure.ifDefined(trueValue, falseValue)).isEqualTo(falseValue)
        assertThat(failure.ifDefined({ trueValue }, { falseValue })).isEqualTo(falseValue)
        assertThat(failure.ifDefined(trueValue, falseValue)).isNotEqualTo(trueValue)
        assertThat(failure.ifDefined({ trueValue }, { falseValue })).isNotEqualTo(trueValue)

        assertThat(none.ifEmpty(trueValue, falseValue)).isEqualTo(trueValue)
        assertThat(none.ifEmpty({ trueValue }, { falseValue })).isEqualTo(trueValue)
        assertThat(none.ifEmpty(trueValue, falseValue)).isNotEqualTo(falseValue)
        assertThat(none.ifEmpty({ trueValue }, { falseValue })).isNotEqualTo(falseValue)

        assertThat(failure.ifEmpty(trueValue, falseValue)).isEqualTo(trueValue)
        assertThat(failure.ifEmpty({ trueValue }, { falseValue })).isEqualTo(trueValue)
        assertThat(failure.ifEmpty(trueValue, falseValue)).isNotEqualTo(falseValue)
        assertThat(failure.ifEmpty({ trueValue }, { falseValue })).isNotEqualTo(falseValue)
    }

    @Test
    fun ifDefineIfEmptyFromNonEmptyBehaveCorrectly() {

        val element = 142
        val falseValue = 24
        val trueValue = 42
        val some = optionOf(element)
        val success = success(element)

        assertThat(some.ifDefined(trueValue, falseValue)).isEqualTo(trueValue)
        assertThat(some.ifDefined({ trueValue }, { falseValue })).isEqualTo(trueValue)
        assertThat(some.ifDefined(trueValue, falseValue)).isNotEqualTo(falseValue)
        assertThat(some.ifDefined({ trueValue }, { falseValue })).isNotEqualTo(falseValue)

        assertThat(success.ifDefined(trueValue, falseValue)).isEqualTo(trueValue)
        assertThat(success.ifDefined({ trueValue }, { falseValue })).isEqualTo(trueValue)
        assertThat(success.ifDefined(trueValue, falseValue)).isNotEqualTo(falseValue)
        assertThat(success.ifDefined({ trueValue }, { falseValue })).isNotEqualTo(falseValue)

        assertThat(some.ifEmpty(trueValue, falseValue)).isEqualTo(falseValue)
        assertThat(some.ifEmpty({ trueValue }, { falseValue })).isEqualTo(falseValue)
        assertThat(some.ifEmpty(trueValue, falseValue)).isNotEqualTo(trueValue)
        assertThat(some.ifEmpty({ trueValue }, { falseValue })).isNotEqualTo(trueValue)

        assertThat(success.ifEmpty(trueValue, falseValue)).isEqualTo(falseValue)
        assertThat(success.ifEmpty({ trueValue }, { falseValue })).isEqualTo(falseValue)
        assertThat(success.ifEmpty(trueValue, falseValue)).isNotEqualTo(trueValue)
        assertThat(success.ifEmpty({ trueValue }, { falseValue })).isNotEqualTo(trueValue)
    }

    @Test
    fun existAndForAllFromEmptyBehaveCorrectly() {
        val none = optionOf<Int>(null)
        val nil = emptyList<Int>()
        val failure = failure<Int>(ArithmeticException("Arithmetic Exception"))

        assertThat(none.any({ t -> true })).isEqualTo(nil.any { t -> true })
        assertThat(none.all({ t -> true })).isEqualTo(nil.all { t -> true })

        assertThat(failure.any({ t -> true })).isEqualTo(nil.any { t -> true })
        assertThat(failure.all({ t -> true })).isEqualTo(nil.all { t -> true })
    }

    @Test
    fun existAndForAllFromNonEmptyBehaveCorrectly() {
        val element = 42
        val list = listOf(element)
        val some = some(element)
        val success = success(element)

        assertThat(some.any({ t -> false })).isEqualTo(list.any { t -> false })
        assertThat(some.all({ t -> true })).isEqualTo(list.all { t -> true })

        assertThat(success.any({ t -> true })).isEqualTo(list.any { t -> true })
        assertThat(success.all({ t -> false })).isEqualTo(list.all { t -> false })
    }
}