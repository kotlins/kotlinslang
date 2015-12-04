package kotlinslang.algebra

import kotlinslang.compose
import kotlinslang.identity


/**
 * Helper function for algebra
 *
 * @author Deny Prasetyo.
 */

/**
 * Lifts a {@code Function} to a higher {@code Function} that operates on Monads.
 *
 * @param <T> 1st argument type of f
 * @param <R> result type of f
 * @param function a Function
 * @return a new Function that lifts the given function f in a layer that operates on monads.
 */
fun <T : Any, R : Any> monadLift(function: (T) -> (R)): (Monad<T>) -> Monad<R> {
    return { mt: Monad<T> -> mt.map { function(it) } }
}

/**
 * Factory method for monoids, taking a zero and a Semigroup.
 *
 * @param <A>       Value type
 * @param zero      The zero of the Monoid.
 * @param combiner The associative binary operation of the Monoid. Please note that
 *                  {@linkplain javaslang.algebra.Semigroup} is a {@linkplain java.lang.FunctionalInterface}.
 * @return a new Monoid on type A
 * @throws NullPointerException if {@code semigroup} is null
 */
fun <A : Any> monoidOf(zero: A, combiner: (A, A) -> A): Monoid<A> {
    return object : Monoid<A> {
        override fun zero(): A {
            return zero
        }

        override fun combine(a1: A, a2: A): A {
            return combiner(a1, a2)
        }
    }
}

/**
 * The monoid of endomorphisms under composition.
 *
 * @param <A> Value type
 * @return The monoid of endomorphisms of type A.
 */
fun <A : Any> endoMonoid(): Monoid<(A) -> A> {
    return monoidOf(identity(), { a, b -> a.compose(b) })
}