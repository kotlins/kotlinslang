package kotlinslang.algebra


/**
 * Folding is an application of {@code Monoid}s.
 * <p>
 * <strong>Example:</strong>
 *
 * <pre><code>
 * Monoid&lt;String&gt; concat = Monoid.of("", (a1, a2) -&gt; a1 + a2);
 * Stream.of("1", "2", "3").fold(concat);
 * </code></pre>
 *
 * is the same as
 *
 * <pre><code>
 * Stream.of("1", "2", "3").fold("", (a1, a2) -&gt; a1 + a2);
 * </code></pre>
 *
 * @param <T> Component type of this foldable
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
interface Foldable<T> {

    /**
     * Folds this elements from the left, starting with {@code zero} and successively calling {@code combine}.
     *
     * @param <U>     the type of the folded value
     * @param zero    A zero element to start with.
     * @param combine A function which combines elements.
     * @return a folded value
     * @throws NullPointerException if {@code combine} is null
     */
    fun <U> foldLeft(zero: U, combine: (U, T) -> U): U

    /**
     * Folds this elements from the right, starting with {@code zero} and successively calling {@code combine}.
     *
     * @param <U>     the type of the folded value
     * @param zero    A zero element to start with.
     * @param combine A function which combines elements.
     * @return a folded value
     * @throws NullPointerException if {@code combine} is null
     */
    fun <U> foldRight(zero: U, combine: (T, U) -> U): U

    /**
     * Folds this elements from the left, starting with {@code monoid.zero()} and successively calling {@code monoid::combine}.
     *
     * @param monoid A monoid, providing a {@code zero} and a {@code combine} function.
     * @return a folded value
     * @throws NullPointerException if {@code monoid} is null
     */
    fun fold(monoid: Monoid<out T>): T {
        @Suppress("UNCHECKED_CAST")
        val m = monoid as Monoid<T>
        return foldLeft(m.zero(), { t, t2 -> m.combine(t, t2) })
    }

    /**
     * Folds this elements from the left, starting with {@code zero} and successively calling {@code combine}.
     *
     * @param zero    A zero element to start with.
     * @param combine A function which combines elements.
     * @return a folded value
     * @throws NullPointerException if {@code combine} is null
     */
    fun fold(zero: T, combine: (T, T) -> T): T {
        return foldLeft(zero, combine)
    }

    /**
     * Folds this elements from the left, starting with {@code monoid.zero()} and successively calling {@code monoid::combine}.
     *
     * @param monoid A monoid, providing a {@code zero} and a {@code combine} function.
     * @return a folded value
     * @throws NullPointerException if {@code monoid} is null
     */
    fun foldLeft(monoid: Monoid<out T>): T {
        @Suppress("UNCHECKED_CAST")
        val m = monoid as Monoid<T>
        return foldLeft(m.zero(), { t, t2 -> m.combine(t, t2) })
    }

    /**
     * Maps this elements to a {@code Monoid} and applies {@code foldLeft}, starting with {@code monoid.zero()}:
     * <pre><code>
     *  foldLeft(monoid.zero(), (ys, x) -&gt; monoid.combine(ys, mapper.apply(x)));
     * </code></pre>
     *
     * @param monoid A Monoid
     * @param mapper A mapper
     * @param <U>    Component type of the given monoid.
     * @return the folded monoid value.
     * @throws NullPointerException if {@code monoid} or {@code mapper} is null
     */
    fun <U> foldMap(monoid: Monoid<U>, mapper: (T) -> U): U {
        return foldLeft(monoid.zero()) { ys, x -> monoid.combine(ys, mapper(x)) }
    }

    /**
     * Folds this elements from the right, starting with {@code monoid.zero()} and successively calling {@code monoid::combine}.
     *
     * @param monoid A monoid, providing a {@code zero} and a {@code combine} function.
     * @return a folded value
     * @throws NullPointerException if {@code monoid} is null
     */
    fun foldRight(monoid: Monoid<out T>): T {
        @Suppress("UNCHECKED_CAST")
        val m = monoid as Monoid<T>
        return foldRight(m.zero(), { t, t2 -> m.combine(t, t2) })
    }


}