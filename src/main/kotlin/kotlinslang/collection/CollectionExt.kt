package kotlinslang.collection

import kotlinslang.control.Option
import kotlinslang.control.Some
import kotlinslang.control.toOption


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */


fun <T> List<T>.head(): T {
    return this.first()
}

fun <T> List<T>.tail(): List<T> {
    return this.drop(1)
}

infix fun <T> T.prependTo(list: List<T>): List<T> {
    return listOf(this) + list
}

public fun<T : Any> Iterable<T>.head(): T {
    return this.first()
}

public fun<T : Any> Iterable<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}

public fun<T : Any> List<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}

public inline fun <T : Any> Iterable<T>.firstOption(predicate: (T) -> Boolean): Option<T> {
    return firstOrNull(predicate).toOption()
}

public inline fun <T : Any> Sequence<T>.firstOption(predicate: (T) -> Boolean): Option<T> {
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


public fun<T : Any> Sequence<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
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


public inline fun String.firstOption(predicate: (Char) -> Boolean): Option<Char> {
    return firstOrNull(predicate).toOption()
}

