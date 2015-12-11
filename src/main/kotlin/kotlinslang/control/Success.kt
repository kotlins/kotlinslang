package kotlinslang.control

import java.io.Serializable


/**
 * A succeeded Try.
 *
 * @param <T> component type of this Success
 * @property value The value of this Success
 * @constructor Constructs a Success
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
public final class Success<out T : Any>(val value: T) : Try<T>, Serializable {

    override fun get(): T {
        return value
    }

    override fun getCause(): Throwable {
        throw UnsupportedOperationException("getCause on Success")
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun isFailure(): Boolean {
        return false
    }

    override fun isSuccess(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other != null && other is Success<*> && value == other.value) return true
        return false
    }


    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Success($value)"
    }

}