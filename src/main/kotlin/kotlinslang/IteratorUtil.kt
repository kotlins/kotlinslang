package kotlinslang

import java.util.NoSuchElementException
import kotlin.support.AbstractIterator


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

private object EMPTY : AbstractIterator<Nothing>() {
    override fun computeNext() {
        throw NoSuchElementException("next() on empty iterator")
    }
}

object IteratorUtil {
    fun <T> empty(): Iterator<T> {
        return EMPTY
    }

    fun <T> of(element: T): Iterator<T> {
        return object : AbstractIterator<T>() {
            override fun computeNext() {
                setNext(element)
                done()
            }
        }
    }
}
