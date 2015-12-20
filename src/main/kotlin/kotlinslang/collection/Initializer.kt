package kotlinslang.collection

import kotlin.support.AbstractIterator


/**
 * Helper Functions to Initialize Collection. Used by Kotlinslang.
 *
 * @author Deny Prasetyo.
 * @since 1.0.0
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
