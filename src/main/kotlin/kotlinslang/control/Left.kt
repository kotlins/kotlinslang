package kotlinslang.control

import java.io.Serializable


/**
 * The {@code Left} version of an {@code Either}.
 *
 * @param <L> left component type
 * @param <R> right component type
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */

final class Left<out L : Any, out R : Any>(val value: L) : Either<L, R>, Serializable {


    override fun isLeft(): Boolean {
        return true
    }

    override fun isRight(): Boolean {
        return false
    }

    /**
     * Returns the value of this {@code Left}.
     *
     * @return the value of this {@code Left}
     */
    override fun get(): L {
        return value
    }

    /**
     * Wrap the value of this {@code Left} in a new {@code Right}.
     *
     * @return a new {@code Right} containing this value
     */
    override fun swap(): Right<R, L> {
        return Right(value)
    }

    override fun <X : Any, Y : Any> bimap(leftMapper: (L) -> X, rightMapper: (R) -> Y): Left<X, Y> {
        return Left(leftMapper(value))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other != null && other is Left<*, *> && value == other.value) return true
        return false
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Left($value)"
    }
}