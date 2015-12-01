package kotlinslang.algebra


/**
 * Defines a Monad by generalizing the flatMap function.
 * <p>
 * A {@code Monad} is a {@link Functor} with a {@code flatMap} method that satisfies the Monad laws, also known
 * as the three control laws:
 * <p>
 * Let
 * <ul>
 * <li>{@code A}, {@code B}, {@code C} be types</li>
 * <li>{@code unit: A -> Monad<A>} a constructor</li>
 * <li>{@code f: A -> Monad<B>}, {@code g: B -> Monad<C>} functions</li>
 * <li>{@code a} be an object of type {@code A}</li>
 * <li>{@code m} be an object of type {@code Monad<A>}</li>
 * </ul>
 * Then all instances of the {@code Monad} interface should obey the three control laws:
 * <ul>
 * <li><strong>Left identity:</strong> {@code unit(a).flatMap(f) ≡ f a}</li>
 * <li><strong>Right identity:</strong> {@code m.flatMap(unit) ≡ m}</li>
 * <li><strong>Associativity:</strong> {@code m.flatMap(f).flatMap(g) ≡ m.flatMap(x -> f.apply(x).flatMap(g))}</li>
 * </ul>
 *
 * To read further about monads in Java please refer to
 * <a href="http://java.dzone.com/articles/whats-wrong-java-8-part-iv">What's Wrong in Java 8, Part IV: Monads</a>.
 *
 * @param <T> component type of this monad
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
interface Monad<T> : Functor<T>, Iterable<T>, Convertible<T> {

    companion object {
        /**
         * Lifts a {@code Function} to a higher {@code Function} that operates on Monads.
         *
         * @param <T> 1st argument type of f
         * @param <R> result type of f
         * @param f a Function
         * @return a new Function that lifts the given function f in a layer that operates on monads.
         */
        fun <T, R> lift(f: (T) -> (R)): (Monad<T>) -> Monad<R> {
            return { mt: Monad<T> -> mt.map { f(it) } }
        }
    }

    /**
     * Filters this `Monad` by testing a predicate.
     *
     *
     * The semantics may vary from class to class, e.g. for single-valued type (like Option) and multi-values types
     * (like Traversable). The commonality is, that filtered.isEmpty() will return true, if no element satisfied
     * the given predicate.
     *
     *
     * Also, an implementation may throw `NoSuchElementException`, if no element makes it through the filter
     * and this state cannot be reflected. E.g. this is the case for [javaslang.control.Either.LeftProjection] and
     * [javaslang.control.Either.RightProjection].

     * @param predicate A predicate
     * *
     * @return a new Monad instance
     * *
     * @throws NullPointerException if `predicate` is null
     */
    fun filter(predicate: (T) -> Boolean): Monad<T>


    /**
     * FlatMaps this value to a new value with different component type.
     * <p>
     * FlatMap is the sequence operation for functions and behaves like the imperative {@code ;}.
     * <p>
     * If the previous results are needed, flatMap cascades:
     * <pre>
     * <code>
     * m1().flatMap(result1 -&gt;
     *      m2(result1).flatMap(result2 -&gt;
     *          m3(result1, result2).flatMap(result3 -&gt;
     *              ...
     *          )
     *      )
     * );
     * </code>
     * </pre>
     * If only the last result is needed, flatMap may be used sequentially:
     * <pre>
     * <code>
     * m1().flatMap(this::m2)
     *     .flatMap(this::m3)
     *     .flatMap(...);
     * </code>
     * </pre>
     *
     * @param mapper A mapper
     * @param <U>    Component type of the mapped {@code Monad}
     * @return a mapped {@code Monad}
     * @throws NullPointerException if {@code mapper} is null
     */
    fun <U> flatMap(mapper: (T) -> Iterable<U>): Monad<U>

    /**
     * Maps this value to a new value with different component type.
     *
     * @param mapper A mapper
     * @param <U>    Component type of the mapped {@code Monad}
     * @return a mapped {@code Monad}
     * @throws NullPointerException if {@code mapper} is null
     */
    override fun <U> map(mapper: (T) -> U): Monad<U>
}
