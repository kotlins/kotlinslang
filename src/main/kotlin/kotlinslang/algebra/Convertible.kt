package kotlinslang.algebra

import kotlinslang.control.Option

/**
 * Conversion methods.

 * @param  Component type.
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
interface Convertible<T> {
    fun toOption(): Option<T>
}