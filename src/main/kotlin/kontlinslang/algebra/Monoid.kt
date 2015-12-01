package kontlinslang.algebra


/**
 * <p>A Monoid is a {@linkplain javaslang.algebra.Semigroup} (types with an associative binary operation) that has an
 * identity element {@code zero}.</p>
 * <p>Given a type {@code A}, instances of Monoid should satisfy the following laws:</p>
 * <ul>
 * <li>Associativity: {@code combine(combine(x,y),z) == combine(x,combine(y,z))} for any {@code x,y,z} of type
 * {@code A}.</li>
 * <li>Identity: {@code combine(zero(), x) == x == combine(x, zero())} for any {@code x} of type {@code A}.</li>
 * </ul>
 * <p>Example: {@linkplain java.lang.String} is a Monoid with zero {@code ""} (empty String) and String concatenation
 * {@code +} as combine operation.</p>
 * <p>Please note that some types can be viewed as a monoid in more than one way, e.g. both addition and multiplication
 * on numbers.</p>
 *
 * @param <A> A type.
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.1.0
 */

interface Monoid<A> : Semigroup<A> {

    /**
     * The unique neutral element regarding {@linkplain #combine(Object, Object)}.
     *
     * @return The zero element of this Monoid
     */
    fun zero(): A

    companion object {
        /**
         * Factory method for monoids, taking a zero and a Semigroup.
         *
         * @param <A>       Value type
         * @param zero      The zero of the Monoid.
         * @param semigroup The associative binary operation of the Monoid. Please note that
         *                  {@linkplain javaslang.algebra.Semigroup} is a {@linkplain java.lang.FunctionalInterface}.
         * @return a new Monoid on type A
         * @throws NullPointerException if {@code semigroup} is null
         */
        fun <A> of(zero: A, semigroup: Semigroup<A>): Monoid<A> {
            return object : Monoid<A> {
                override fun zero(): A {
                    return zero
                }

                override fun combine(a1: A, a2: A): A {
                    return semigroup.combine(a1, a2)
                }

            }
        }

        /**
         * Factory method for monoids, taking a zero and a Semigroup.
         *
         * @param <A>       Value type
         * @param zero      The zero of the Monoid.
         * @param semigroup The associative binary operation of the Monoid. Please note that
         *                  {@linkplain javaslang.algebra.Semigroup} is a {@linkplain java.lang.FunctionalInterface}.
         * @return a new Monoid on type A
         * @throws NullPointerException if {@code semigroup} is null
         */
        fun <A> of(zero: A, semigroup: (A, A) -> A): Monoid<A> {
            return object : Monoid<A> {
                override fun zero(): A {
                    return zero
                }

                override fun combine(a1: A, a2: A): A {
                    return semigroup(a1, a2)
                }
            }
        }

        fun <A> endoMonoid(): Monoid<(A) -> A> {
            val zero: (A) -> A = { a: A -> a }
            val semigroup: ((A) -> A, (A) -> A) -> A = {a,b -> a()}
            return of(zero, semigroup)
        }
    }
}