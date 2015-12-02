package kotlinslang.control

import kotlinslang.Value
import kotlinslang.emptyIterator
import kotlinslang.iteratorOf
import java.util.Objects


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
        return if (isEmpty()) emptyIterator() else iteratorOf(get())
    }

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    override fun toString(): String

}