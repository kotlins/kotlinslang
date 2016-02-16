package kotlinslang.collection

import kotlinslang.control.Option
import kotlinslang.control.Some
import kotlinslang.control.toOption


/**
 * Extensions for Kotlin Collection that related to Kotlinslang Construct.
 *
 * @author Deny Prasetyo.
 * @since 1.0.0
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

fun<T : Any> Iterable<T>.head(): T {
    return this.first()
}

fun<T : Any> Iterable<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}

fun<T : Any> List<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}

inline fun <T : Any> Iterable<T>.firstOption(predicate: (T) -> Boolean): Option<T> {
    return firstOrNull(predicate).toOption()
}

inline fun <T : Any> Sequence<T>.firstOption(predicate: (T) -> Boolean): Option<T> {
    return firstOrNull(predicate).toOption()
}

fun<T : Any, R : Any> List<T>.traverse(f: (T) -> Option<R>): Option<List<R>> {
    return foldRight(Some(emptyList())) { i: T, accumulator: Option<List<R>> ->
        f(i).map(accumulator) { head: R, tail: List<R> ->
            head prependTo tail
        }
    }
}

fun<T : Any> List<Option<T>>.sequential(): Option<List<T>> {
    return traverse { it }
}

fun<T : Any> List<Option<T>>.flatten(): List<T> {
    return filter { it.isDefined() }.map { it.get() }
}


fun<T : Any> Sequence<T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}


fun<T : Any> Array<out T>.firstOption(): Option<T> {
    return firstOrNull().toOption()
}

fun BooleanArray.firstOption(): Option<Boolean> {
    return firstOrNull().toOption()
}

fun ByteArray.firstOption(): Option<Byte> {
    return firstOrNull().toOption()
}

fun CharArray.firstOption(): Option<Char> {
    return firstOrNull().toOption()
}

fun DoubleArray.firstOption(): Option<Double> {
    return firstOrNull().toOption()
}

fun FloatArray.firstOption(): Option<Float> {
    return firstOrNull().toOption()
}


fun IntArray.firstOption(): Option<Int> {
    return firstOrNull().toOption()
}


fun LongArray.firstOption(): Option<Long> {
    return firstOrNull().toOption()
}


fun ShortArray.firstOption(): Option<Short> {
    return firstOrNull().toOption()
}


fun String.firstOption(): Option<Char> {
    return firstOrNull().toOption()
}

inline fun <T : Any> Array<out T>.firstOption(predicate: (T) -> Boolean): Option<T> {
    return firstOrNull(predicate).toOption()
}

inline fun BooleanArray.firstOption(predicate: (Boolean) -> Boolean): Option<Boolean> {
    return firstOrNull(predicate).toOption()
}

inline fun ByteArray.firstOption(predicate: (Byte) -> Boolean): Option<Byte> {
    return firstOrNull(predicate).toOption()
}

inline fun CharArray.firstOption(predicate: (Char) -> Boolean): Option<Char> {
    return firstOrNull(predicate).toOption()
}

inline fun DoubleArray.firstOption(predicate: (Double) -> Boolean): Option<Double> {
    return firstOrNull(predicate).toOption()
}

inline fun FloatArray.firstOption(predicate: (Float) -> Boolean): Option<Float> {
    return firstOrNull(predicate).toOption()
}

inline fun IntArray.firstOption(predicate: (Int) -> Boolean): Option<Int> {
    return firstOrNull(predicate).toOption()
}

inline fun LongArray.firstOption(predicate: (Long) -> Boolean): Option<Long> {
    return firstOrNull(predicate).toOption()
}

inline fun ShortArray.firstOption(predicate: (Short) -> Boolean): Option<Short> {
    return firstOrNull(predicate).toOption()
}


inline fun String.firstOption(predicate: (Char) -> Boolean): Option<Char> {
    return firstOrNull(predicate).toOption()
}

