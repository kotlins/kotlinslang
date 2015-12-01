package kotlinslang.control

import kotlinslang.IteratorUtil
import kotlinslang.Value
import java.util.Objects
import java.util.Optional


/**
 * Replacement for {@link java.util.Optional}.
 * <p>
 * Option is a <a href="http://stackoverflow.com/questions/13454347/monads-with-java-8">monadic</a> container type which
 * represents an optional value. Instances of Option are either an instance of {@link javaslang.control.Some} or the
 * singleton {@link javaslang.control.None}.
 * <p>
 * Most of the API is taken from {@link java.util.Optional}. A similar type can be found in <a
 * href="http://hackage.haskell.org/package/base-4.6.0.1/docs/Data-Maybe.html">Haskell</a> and <a
 * href="http://www.scala-lang.org/api/current/#scala.Option">Scala</a>.
 *
 * @param <T> The type of the optional value.
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
interface Option<T> : Value<T> {
    companion object {
        /**
         * Creates a new {@code Option} of a given value.
         *
         * @param value A value
         * @param <T>   type of the value
         * @return {@code Some(value)} if value is not {@code null}, {@code None} otherwise
         */
        fun <T> of(value: T?): Option<T> {
            return if (value == null) None.instance() else Some(value)
        }


        /**
         * Creates a new {@code Some} of a given value.
         * <p>
         * The only difference to {@link Option#of(Object)} is, when called with argument {@code null}.
         * <pre>
         * <code>
         * Option.of(null);   // = None
         * Option.some(null); // = Some(null)
         * </code>
         * </pre>
         *
         * @param value A value
         * @param <T>   type of the value
         * @return {@code Some(value)}
         */
        fun <T> some(value: T): Option<T> {
            return Some(value)
        }

        /**
         * Returns the single instance of {@code None}
         *
         * @param <T> component type
         * @return the single instance of {@code None}
         */
        fun <T> none(): Option<T> {
            return None.instance()
        }

        /**
         * Creates {@code Some} of suppliers value if condition is true, or {@code None} in other case
         *
         * @param <T>       type of the optional value
         * @param condition A boolean value
         * @param supplier  An optional value supplier, may supply {@code null}
         * @return return {@code Some} of supplier's value if condition is true, or {@code None} in other case
         * @throws NullPointerException if the given {@code supplier} is null
         */
        fun <T> whenCondition(condition: Boolean, supplier: () -> T): Option<T> {
            return if (condition) of(supplier()) else none()
        }

        /**
         * Wraps a Java Optional to a new Option
         *
         * @param optional a given optional to wrap in {@code Option}
         * @param <T>      type of the value
         * @return {@code Some(optional.get())} if value is Java {@code Optional} is present, {@code None} otherwise
         */
        fun <T> ofOptional(optional: Optional<out T>): Option<T> {
            return if (optional.isPresent) of(optional.get()) else none()
        }
    }

    /**
     * Returns true, if this is {@code None}, otherwise false, if this is {@code Some}.
     *
     * @return true, if this {@code Option} is empty, false otherwise
     */
    override fun isEmpty(): Boolean

    /**
     * Returns true, if this is {@code Some}, otherwise false, if this is {@code None}.
     * <p>
     * Please note that it is possible to create {@code new Some(null)}, which is defined.
     *
     * @return true, if this {@code Option} has a defined value, false otherwise
     */
    override fun isDefined(): Boolean {
        return !isEmpty()
    }

    /**
     * An option is a singleton type.
     *
     * @return {@code true}
     */
    override fun isSingletonType(): Boolean {
        return true
    }

    override fun get(): T

    override fun getOption(): Option<T> {
        return this
    }

    /**
     * Returns the value if this is a {@code Some} or the {@code other} value if this is a {@code None}.
     * <p>
     * Please note, that the other value is eagerly evaluated.
     *
     * @param other An alternative value
     * @return This value, if this Option is defined or the {@code other} value, if this Option is empty.
     */
    override fun orElse(other: T): T {
        return if (isEmpty()) other else get()
    }

    /**
     * Returns the value if this is a {@code Some}, otherwise the {@code other} value is returned,
     * if this is a {@code None}.
     * <p>
     * Please note, that the other value is lazily evaluated.
     *
     * @param supplier An alternative value supplier
     * @return This value, if this Option is defined or the {@code other} value, if this Option is empty.
     */
    override fun orElseGet(supplier: () -> T): T {
        return if (isEmpty()) supplier() else get()
    }

    /**
     * Returns the value if this is a {@code Some}, otherwise throws an exception.
     *
     * @param supplier An exception supplier
     * @param <X>               A throwable
     * @return This value, if this Option is defined, otherwise throws X
     * @throws X a throwable
     */
    @Throws(exceptionClasses = Throwable::class)
    override fun <X : Throwable> orElseThrow(supplier: () -> X): T {
        if (isEmpty()) {
            throw supplier()
        } else {
            return get()
        }
    }

    /**
     * Returns {@code Some(value)} if this is a {@code Some} and the value satisfies the given predicate.
     * Otherwise {@code None} is returned.
     *
     * @param predicate A predicate which is used to test an optional value
     * @return {@code Some(value)} or {@code None} as specified
     */
    override fun filter(predicate: (T) -> Boolean): Option<T> {
        return if ((isEmpty() || predicate(get()))) this else None.instance()
    }

    /**
     * Maps the value to a new {@code Option} if this is a {@code Some}, otherwise returns {@code None}.
     *
     * @param mapper A mapper
     * @param <U>    Component type of the resulting Option
     * @return a new {@code Option}
     */
    override fun <U> flatMap(mapper: (T) -> Iterable<U>): Option<U> {
        if (isEmpty()) {
            return None.instance()
        } else {
            val iterable = mapper(get())
            if (iterable is Value) {
                return iterable.toOption()
            } else {
                val iterator = iterable.iterator()
                if (iterator.hasNext()) {
                    return Some(iterator.next())
                } else {
                    return None.instance()
                }
            }
        }
    }

    /**
     * Maps the value and wraps it in a new {@code Some} if this is a {@code Some}, returns {@code None}.
     *
     * @param mapper A value mapper
     * @param <U>    The new value type
     * @return a new {@code Some} containing the mapped value if this Option is defined, otherwise {@code None}, if this is empty.
     */
    override fun <U> map(mapper: (T) -> U): Option<U> {
        if (isEmpty()) {
            return None.instance()
        } else {
            return Some(mapper(get()))
        }
    }

    /**
     * Applies an action to this value, if this option is defined, otherwise does nothing.
     *
     * @param action An action which can be applied to an optional value
     * @return this {@code Option}
     */
    override fun peek(action: (T) -> Unit): Option<T> {
        Objects.requireNonNull(action, "action is null")
        if (isDefined()) {
            action(get())
        }
        return this
    }

    override fun iterator(): Iterator<T> {
        return if (isEmpty()) IteratorUtil.empty() else IteratorUtil.of(get())
    }

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    override fun toString(): String

}