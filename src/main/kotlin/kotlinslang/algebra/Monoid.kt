package kotlinslang.algebra

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
 * @since 1.0.0
 */

interface Monoid<A> {

    /**
     * The unique neutral element regarding {@linkplain #combine(Object, Object)}.
     *
     * @return The zero element of this Monoid
     */
    fun zero(): A

    /**
     * Combines two elements of the same type, which is also returned.
     *
     * @param a1 An element
     * @param a2 Another element
     * @return The combination of a1 and a2
     */
    fun combine(a1: A, a2: A): A
}