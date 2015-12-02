package kotlinslang.control

import java.io.Serializable


/**
 * A failed Try.
 *
 * @param <T> component type of this Failure
 * @property throwable, Throwable cause
 * @constructor Construct a Failure
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */

public final class Failure<T>(private val throwable: Throwable) : Try<T>, Serializable {

    override fun get(): T {
        throw throwable
    }

    override fun getCause(): Throwable {
        return throwable;
    }

    override fun isEmpty(): Boolean {
        return true
    }

    override fun isFailure(): Boolean {
        return true
    }

    override fun isSuccess(): Boolean {
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Failure<*>

        if (throwable != other.throwable) return false

        return true
    }

    override fun hashCode(): Int {
        return throwable.hashCode()
    }

    override fun toString(): String {
        return "Failure($throwable)"
    }
}