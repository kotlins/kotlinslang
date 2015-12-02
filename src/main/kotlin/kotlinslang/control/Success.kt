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
public final class Success<T>(val value: T) : Try<T>, Serializable {

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
        if (other?.javaClass != javaClass) return false

        other as Success<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Success($value)"
    }

}