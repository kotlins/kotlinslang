package kotlinslang.control

import java.io.Serializable


/**
 * Some represents a defined {@link javaslang.control.Option}. It contains a value which may be null. However, to
 * create an Option containing null, {@code new Some(null)} has to be called. In all other cases
 * {@link Option#of(Object)} is sufficient.
 *
 * @param <T> The type of the optional value.
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
public final class Some<T : Any>(val value: T) : Option<T>, Serializable {

    override fun get(): T {
        return value;
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun toString(): String {
        return "Some($value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other != null && other is Some<*> && value == other.value) return true
        return false
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}