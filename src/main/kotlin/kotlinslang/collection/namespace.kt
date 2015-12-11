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
