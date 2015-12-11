package kotlinslang.algebra


/**
 * <p>Defines a Functor by generalizing the map.</p>
 *
 * All instances of the Functor interface should obey the two functor laws:
 * <ul>
 * <li>{@code m.map(a -> a) ≡ m}</li>
 * <li>{@code m.map(f.compose(g)) ≡ m.map(g).map(f)}</li>
 * </ul>
 * where "f, g ∈ Function".
 *
 * @param <T> component type of this functor
 * @author Daniel Dietrich, Deny Prasetyo
 * @see <a href="http://www.haskellforall.com/2012/09/the-functor-design-pattern.html">The functor design pattern</a>
 * @since 1.0.0
 */

interface Functor<out T : Any> {
    /**
     * Applies a function f to the components of this Functor.
     *
     * @param <U>    type of the component of the resulting Functor
     * @param mapper a Function which maps the component of this Functor
     * @return a new Functor
     * @throws NullPointerException if {@code f} is null
     */

    fun <U : Any> map(mapper: (T) -> (U)): Functor<U>
}
