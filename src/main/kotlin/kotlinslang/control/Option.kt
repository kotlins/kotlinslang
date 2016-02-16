package kotlinslang.control

import kotlinslang.Value
import kotlinslang.collection.emptyIterator
import kotlinslang.collection.iteratorOf
import kotlinslang.toOption


/**
 * Replacement for {@link java.util.Optional}.
 * <p>
 * Option is a <a href="http://stackoverflow.com/questions/13454347/monads-with-java-8">monadic</a> container type which
 * represents an optional value. Instances of Option are either an instance of {@link javaslang.control.Some} or the
 * singleton {@link javaslang.control.None}.
 * <p>
 * Most of the API is taken from {@link java.util.Optional}. A similar type can be found in <a
 * href="http://hackage.haskell.org/package/base-4.6.0.1/docs/Data-Maybe.html">Haskell</a> and <a
 * href="http://www.scala-lang.org/api /current/#scala.Option">Scala</a>.
 *
 * @param <T> The type of the optional value.
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
interface Option<out T : Any> : Value<T> {
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
        return super.orElseThrow(supplier)
    }

    /**
     * Returns {@code Some(value)} if this is a {@code Some} and the value satisfies the given predicate.
     * Otherwise {@code None} is returned.
     *
     * @param predicate A predicate which is used to test an optional value
     * @return {@code Some(value)} or {@code None} as specified
     */
    override fun filter(predicate: (T) -> Boolean): Option<T> {
        return if ((isEmpty() || predicate(get()))) this else None
    }

    /**
     * Maps the value to a new {@code Option} if this is a {@code Some}, otherwise returns {@code None}.
     *
     * @param mapper A mapper
     * @param <U>    Component type of the resulting Option
     * @return a new {@code Option}
     */
    override fun <U : Any> flatMap(mapper: (T) -> Iterable<U>): Option<U> {
        if (isEmpty()) {
            return None
        } else {
            val iterable = mapper(get())
            if (iterable is Value) {
                return iterable.toOption()
            } else {
                val iterator = iterable.iterator()
                if (iterator.hasNext()) {
                    return Some(iterator.next())
                } else {
                    return None
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
    override fun <U : Any> map(mapper: (T) -> U?): Option<U> {
        if (isEmpty()) {
            return None
        } else {
            val result = mapper(get())
            if (result != null) {
                return Some(result)
            } else {
                return None
            }
        }
    }


    fun<P : Any, U : Any> map(p: Option<P>, f: (T, P) -> U): Option<U> {
        return flatMap { t -> p.map { pp1 -> f(t, pp1) } }
    }

    /**
     * Applies an action to this value, if this option is defined, otherwise does nothing.
     *
     * @param action An action which can be applied to an optional value
     * @return this {@code Option}
     */
    override fun peek(action: (T) -> Unit): Option<T> {
        if (isDefined()) {
            action(get())
        }
        return this
    }

    /**
     * Transforms this {@code Option}.
     *
     * @param function   A transformation
     * @param <U> Type of transformation result
     * @return An instance of type {@code U}
     */
    fun <U : Any?> transform(function: (Option<T>) -> U): U {
        return function(this)
    }

    override fun iterator(): Iterator<T> {
        return if (isEmpty()) emptyIterator() else iteratorOf(get())
    }

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    override fun toString(): String

}

