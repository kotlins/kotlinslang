package kotlinslang

import  org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.NoSuchElementException
import kotlin.test.assertFailsWith


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

class UtilTest {

    @Test
    fun emptyIteratorThrowsException() {
        val emptyIter = emptyIterator<Int>()
        assertThat(emptyIter.hasNext()).isFalse()
        assertFailsWith(
                exceptionClass = NoSuchElementException::class,
                block = { emptyIter.next() },
                message = "Throws Exception when call next() on EmptyIterator"
        )
    }

    @Test
    fun iteratorOfProduceIterator() {
        val element = 10
        val nonEmptyIter = iteratorOf(element)
        assertThat(nonEmptyIter.hasNext()).isTrue()
        assertThat(nonEmptyIter.next()).isEqualTo(element)
        assertThat(nonEmptyIter.hasNext()).isFalse()

        val pair: Pair<Int, Int> = Pair(10, 42)
        val nonEmptyPairIter = iteratorOf(pair.first, pair.second)
        assertThat(nonEmptyPairIter.hasNext()).isTrue()
        assertThat(nonEmptyPairIter.next()).isEqualTo(pair.first)

        assertThat(nonEmptyPairIter.hasNext()).isTrue()
        assertThat(nonEmptyPairIter.next()).isEqualTo(pair.second)
        assertThat(nonEmptyPairIter.hasNext()).isFalse()

    }


    @Test
    fun identityFunctionCorrectResult() {
        val initial = 10
        val identityFunction = identity<Int>()
        assertThat(identityFunction(initial)).isEqualTo(initial)
    }

    @Test
    fun listIsIterable() {
        val list = listOf(5, 6, 3, 3, 4, 2)
        val check = when (list) {
            is Iterable<*> -> true
            else -> false
        }

        assertThat(check).isTrue()
    }

    @Test
    fun forwardComposeAndThenBehaveCorrectly() {
        val functionOne = { i: Int -> i * 1.0 }
        val functionTwo = { d: Double -> d.toString() }

        val initial = 10
        val expected = "10.0"
        val forwardComposed = functionOne forwardCompose functionTwo

        assertThat(forwardComposed(initial)).isEqualTo(expected)

        val andThenFunction = functionOne andThen functionTwo
        assertThat(andThenFunction(initial)).isEqualTo(expected)
    }

    @Test
    fun composeBehaveCorrectly() {
        val functionOne = { i: Int -> i * 1.0 }
        val beforeFunction = { s: String -> s.toInt() }

        val initial = "42"
        val expected = 42.0
        val composed = functionOne compose beforeFunction

        assertThat(composed(initial)).isEqualTo(expected)
    }

}