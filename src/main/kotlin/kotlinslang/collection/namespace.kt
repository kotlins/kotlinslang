package kotlinslang.collection

import kotlin.support.AbstractIterator


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */


private object EMPTY : AbstractIterator<Nothing>() {
    override fun computeNext() {
        done()
    }
}

fun <T> emptyIterator(): Iterator<T> {
    return EMPTY
}

fun <T> iteratorOf(element: T): Iterator<T> {
    return listOf(element).iterator()
}

fun <T> iteratorOf(vararg element: T): Iterator<T> {
    return listOf(*element).iterator()
}

fun <T> List<T>.tail(): List<T> {
    return this.drop(1)
}

infix fun <T> T.prependTo(list: List<T>): List<T> {
    return listOf(this) + list
}
