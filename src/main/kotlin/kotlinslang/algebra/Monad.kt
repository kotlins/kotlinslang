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
 * <li><strong>Associativity:</strong> {@code m.flatMap(f).flatMap(g) ≡ m.flatMap(x -> f(x).flatMap(g))}</li>
 * </ul>
 *
 * To read further about monads in Java please refer to
 * <a href="http://java.dzone.com/articles/whats-wrong-java-8-part-iv">What's Wrong in Java 8, Part IV: Monads</a>.
 *
 * @param <T> component type of this monad
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */

interface Monad<out T : Any> : Functor<T>, Iterable<T> {

    /**
     * Filters this `Monad` by testing a predicate.
     *
     * If given filtered.isEmpty() will return true, if no element satisfied
     * the given predicate.
     *
     *
     * Also, an implementation may throw `NoSuchElementException`, if no element makes it through the filter
     * and this state cannot be reflected.
     *
     * @param predicate A predicate function
     * @return a new Monad instance
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
     * @param <U> Component type of the mapped {@code Monad}
     * @return a mapped {@code Monad}
     */
    fun <U : Any> flatMap(mapper: (T) -> Iterable<U>): Monad<U>

    /**
     * Performs the given [operation] on element.
     * Only invoked when {@code Monad} is not Empty.
     */
    fun forEach(operation: (T) -> Unit)

    /**
     * Checks, if an element exists such that the predicate holds.
     *
     * @param predicate A Predicate function
     * @return true, if predicate holds for one or more elements, false otherwise
     */
    fun any(predicate: (T) -> Boolean): Boolean

    /**
     * Checks, if the given predicate holds for all elements.
     *
     * @param predicate A Predicate
     * @return true, if the predicate holds for all elements, false otherwise
     */
    fun all(predicate: (T) -> Boolean): Boolean

    /**
     * Maps this value to a new value with different component type.
     *
     * @param mapper A mapper
     * @param <U> Component type of the mapped {@code Monad}
     * @return a mapped {@code Monad}
     */
    override fun <U : Any> map(mapper: (T) -> U): Monad<U>

}
