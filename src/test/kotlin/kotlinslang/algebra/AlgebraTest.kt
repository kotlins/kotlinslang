package kotlinslang.algebra


import kotlinslang.control.optionOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

class AlgebraTest {

    @Test
    fun shouldCombineMonoids() {
        val endo: Monoid<(Int) -> Int> = endoMonoid()
        val after = { i: Int -> i + 1 }
        val before = { i: Int -> i * 2 }
        assertThat(endo.combine(after, before)(2)).isEqualTo(5)
    }

    @Test
    fun monoidOfShouldCreate() {
        val monoid = monoidOf(zero = 10, semigroup = { a: Int, b: Int -> a * b })
        assertThat(monoid.zero()).isEqualTo(10)
        assertThat(monoid.combine(2, 4)).isEqualTo(8)
    }

    @Test
    fun monoidOfSemigroupShouldCreate() {
        val monoid = monoidOf(zero = 1, semigroup = object : Semigroup<Int> {
            override fun combine(a1: Int, a2: Int): Int {
                return a1 + a2
            }
        })
        assertThat(monoid.zero()).isEqualTo(1)
        assertThat(monoid.combine(2, 4)).isEqualTo(6)
    }

    @Test
    fun monadLiftShouldCreate() {
        val liftedMonad = monadLift<Int, Double> { l -> l + 4 * 0.1 }
        val monadOption = optionOf(20)
        val liftedOption = liftedMonad(monadOption)
        liftedOption.forEach { o -> println(o) };
    }

}