package kotlinslang.control

import java.io.Serializable


/**
 * The {@code Right} version of an {@code Either}.
 *
 * @param <L> left component type
 * @param <R> right component type
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
public final class Right<L : Any, R : Any>(val value: R) : Either<L, R>, Serializable {

    override fun isLeft(): Boolean {
        return false
    }

    override fun isRight(): Boolean {
        return true
    }

    override fun <X : Any, Y : Any> bimap(leftMapper: (L) -> X, rightMapper: (R) -> Y): Right<X, Y> {
        return Right(rightMapper(value))
    }

    /**
     * Returns the value of this {@code Right}.
     *
     * @return the value of this {@code Right}
     */
    override fun get(): R {
        return value
    }

    /**
     * Wrap the value of this {@code Right} in a new {@code Left}.
     *
     * @return a new {@code Left} containing this value
     */
    override fun swap(): Left<R, L> {
        return Left(value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other != null && other is Right<*,*> && value == other.value) return true
        return false
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Right($value)"
    }
}