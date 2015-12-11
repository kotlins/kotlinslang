package kotlinslang.control

import kotlinslang.Value
import kotlinslang.collection.emptyIterator
import kotlinslang.collection.iteratorOf
import kotlinslang.collection.prependTo
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
    override fun <U : Any> map(mapper: (T) -> U): Option<U> {
        if (isEmpty()) {
            return None
        } else {
            return Some(mapper(get()))
        }
    }


    public fun<P1 : Any, R : Any> map(p1: Option<P1>, f: (T, P1) -> R): Option<R> {
        return flatMap { t -> p1.map { pp1 -> f(t, pp1) } }
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

    override fun iterator(): Iterator<T> {
        return if (isEmpty()) emptyIterator() else iteratorOf(get())
    }

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    override fun toString(): String

}


public fun<T : Any> Array<out T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}

public fun BooleanArray.firstOption(): Option<Boolean> {
    return firstOrNull().toOption()
}

public fun ByteArray.firstOption(): Option<Byte> {
    return firstOrNull().toOption()
}

public fun CharArray.firstOption(): Option<Char> {
    return firstOrNull().toOption()
}

public fun DoubleArray.firstOption(): Option<Double> {
    return firstOrNull().toOption()
}

public fun FloatArray.firstOption(): Option<Float> {
    return firstOrNull().toOption()
}


public fun IntArray.firstOption(): Option<Int> {
    return firstOrNull().toOption()
}


public fun LongArray.firstOption(): Option<Long> {
    return firstOrNull().toOption()
}


public fun ShortArray.firstOption(): Option<Short> {
    return firstOrNull().toOption()
}

public fun<T : Any> Iterable<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}

public fun<T : Any> List<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}

public fun<T : Any> Sequence<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}


public fun String.firstOption(): Option<Char> {
    return firstOrNull().toOption()
}

public inline fun <T : Any> Array<out T>.firstOption(predicate: (T) -> Boolean): Option<T> {
    return firstOrNull(predicate).toOption()
}

public inline fun BooleanArray.firstOption(predicate: (Boolean) -> Boolean): Option<Boolean> {
    return firstOrNull(predicate).toOption()
}

public inline fun ByteArray.firstOption(predicate: (Byte) -> Boolean): Option<Byte> {
    return firstOrNull(predicate).toOption()
}

public inline fun CharArray.firstOption(predicate: (Char) -> Boolean): Option<Char> {
    return firstOrNull(predicate).toOption()
}

public inline fun DoubleArray.firstOption(predicate: (Double) -> Boolean): Option<Double> {
    return firstOrNull(predicate).toOption()
}

public inline fun FloatArray.firstOption(predicate: (Float) -> Boolean): Option<Float> {
    return firstOrNull(predicate).toOption()
}

public inline fun IntArray.firstOption(predicate: (Int) -> Boolean): Option<Int> {
    return firstOrNull(predicate).toOption()
}

public inline fun LongArray.firstOption(predicate: (Long) -> Boolean): Option<Long> {
    return firstOrNull(predicate).toOption()
}

public inline fun ShortArray.firstOption(predicate: (Short) -> Boolean): Option<Short> {
    return firstOrNull(predicate).toOption()
}

public inline fun <T : Any> Iterable<T>.firstOption(predicate: (T) -> Boolean): Option<T> {
    return firstOrNull(predicate).toOption()
}

public inline fun <T : Any> Sequence<T>.firstOption(predicate: (T) -> Boolean): Option<T> {
    return firstOrNull(predicate).toOption()
}


public inline fun String.firstOption(predicate: (Char) -> Boolean): Option<Char> {
    return firstOrNull(predicate).toOption()
}


public fun<T : Any, R : Any> List<T>.traverse(f: (T) -> Option<R>): Option<List<R>> {
    return foldRight(Some(emptyList())) { i: T, accumulator: Option<List<R>> ->
        f(i).map(accumulator) { head: R, tail: List<R> ->
            head prependTo tail
        }
    }
}

public fun<T : Any> List<Option<T>>.sequential(): Option<List<T>> {
    return traverse { it }
}

public fun<T : Any> List<Option<T>>.flatten(): List<T> {
    return filter { it.isDefined() }.map { it.get() }
}
