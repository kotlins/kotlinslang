package kotlinslang.control

import kotlinslang.Value
import kotlinslang.emptyIterator
import kotlinslang.iteratorOf
import java.util.NoSuchElementException
import java.util.Objects


/**
 * Either represents a value of two possible types. An Either is either a {@link javaslang.control.Left} or a
 * {@link javaslang.control.Right}.
 * <p>
 * If the given Either is a Right and projected to a Left, the Left operations have no effect on the Right value.<br>
 * If the given Either is a Left and projected to a Right, the Right operations have no effect on the Left value.<br>
 * If a Left is projected to a Left or a Right is projected to a Right, the operations have an effect.
 * <p>
 * <strong>Example:</strong> A compute() function, which results either in an Integer value (in the case of success) or
 * in an error message of type String (in the case of failure). By convention the success case is Right and the failure
 * is Left.
 *
 * <pre>
 * <code>
 * Either&lt;String,Integer&gt; value = compute().right().map(i -&gt; i * 2).toEither();
 * </code>
 * </pre>
 *
 * If the result of compute() is Right(1), the value is Right(2).<br>
 * If the result of compute() is Left("error), the value is Left("error").
 *
 * @param <L> The type of the Left value of an Either.
 * @param <R> The type of the Right value of an Either.
 * @author Daniel Dietrich
 * @since 1.0.0
 */
interface Either<L, R> {

    /**
     * Returns a LeftProjection of this Either.
     *
     * @return a new LeftProjection of this
     */
    fun left(): LeftProjection<L, R> {
        return LeftProjection(this)
    }

    /**
     * Returns a RightProjection of this Either.
     *
     * @return a new RightProjection of this
     */
    fun right(): RightProjection<L, R> {
        return RightProjection(this)
    }

    /**
     * Returns whether this Either is a Left.
     *
     * @return true, if this is a Left, false otherwise
     */
    fun isLeft(): Boolean

    /**
     * Returns whether this Either is a Right.
     *
     * @return true, if this is a Right, false otherwise
     */
    fun isRight(): Boolean

    /**
     * Maps either the left or the right side of this disjunction.
     *
     * @param leftMapper  maps the left value if this is a Left
     * @param rightMapper maps the right value if this is a Right
     * @param <X>         The new left type of the resulting Either
     * @param <Y>         The new right type of the resulting Either
     * @return A new either instance
     */
    fun <X, Y> bimap(leftMapper: (L) -> X, rightMapper: (R) -> Y): Either<X, Y>

    /**
     * Converts a {@code Left} to a {@code Right} vice versa by wrapping the value in a new type.
     *
     * @return a new {@code Either}
     */
    fun swap(): Either<R, L>


    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    override fun toString(): String

    /**
     * A left projection of an either.
     *
     * @param <L> The type of the Left value of an Either.
     * @param <R> The type of the Right value of an Either.
     * @since 1.0.0
     */
    class LeftProjection<L, R> constructor(val either: Either<L, R>) : Value<L> {

        override fun isEmpty(): Boolean {
            return either.isRight()
        }


        private fun asLeft(): L {
            return (either as Left<L, R>).get()
        }

        private fun asRight(): R {
            return (either as Right<L, R>).get()
        }

        /**
         * Gets the Left value or throws.
         *
         * @return the left value, if the underlying Either is a Left
         * @throws NoSuchElementException if the underlying either of this LeftProjection is a Right
         */
        override fun get(): L {
            if (either.isLeft()) {
                return asLeft()
            } else {
                throw NoSuchElementException("Either.left().get() on Right")
            }
        }

        /**
         * Gets the Left value or an alternate value, if the projected Either is a Right.
         *
         * @param other an alternative value
         * @return the left value, if the underlying Either is a Left or else {@code other}
         * @throws NoSuchElementException if the underlying either of this LeftProjection is a Right
         */
        override fun orElse(other: L): L {
            return if (either.isLeft()) asLeft() else other
        }

        /**
         * Gets the Left value or an alternate value, if the projected Either is a Right.
         *
         * @param other a function which converts a Right value to an alternative Left value
         * @return the left value, if the underlying Either is a Left or else the alternative Left value provided by
         * {@code other} by applying the Right value.
         */
        fun orElseGet(other: (R) -> L): L {
            Objects.requireNonNull(other, "other is null")
            if (either.isLeft()) {
                return asLeft()
            } else {
                return other(asRight())
            }
        }

        /**
         * Runs an action in the case this is a projection on a Right value.
         *
         * @param action an action which consumes a Right value
         */
        fun orElseRun(action: (R) -> Unit) {
            Objects.requireNonNull(action, "action is null")
            if (either.isRight()) {
                action(asRight())
            }
        }

        @Throws(Throwable::class)
        fun <X : Throwable> orElseThrow(exceptionFunction: (R) -> X): L {
            Objects.requireNonNull(exceptionFunction, "exceptionFunction is null")
            if (either.isLeft()) {
                return asLeft()
            } else {
                throw exceptionFunction(asRight())
            }
        }

        /**
         * Returns the underlying either of this projection.
         * @return the underlying either
         */
        fun toEither(): Either<L, R> {
            return either
        }

        /**
         * Returns {@code Some} value of type L if this is a left projection of a Left value and the predicate
         * applies to the underlying value.
         *
         * @param predicate A predicate
         * @return A new Option
         */
        override fun filter(predicate: (L) -> Boolean): Option<L> {
            Objects.requireNonNull(predicate, "predicate is null")
            if (either.isLeft()) {
                val value = asLeft()
                return if (predicate(value)) Some(value) else None.instance()
            } else {
                return None.instance()
            }
        }

        /**
         * FlatMaps the left value if the projected Either is a Left.
         *
         * @param mapper A mapper which takes a left value and returns a java.lang.Iterable
         * @param <U>    The new type of a Left value
         * @return A new LeftProjection
         */
        override fun <U> flatMap(mapper: (L) -> Iterable<U>): LeftProjection<U, R> {
            if (either.isLeft()) {
                return Left<U, R>(Value[mapper(asLeft())]).left()
            } else {
                @Suppress("CAST_NEVER_SUCCEEDS")
                return this as LeftProjection<U, R>
            }
        }

        /**
         * Maps the left value if the projected Either is a Left.
         *
         * @param mapper A mapper which takes a left value and returns a value of type U
         * @param <U>    The new type of a Left value
         * @return A new LeftProjection
         */
        override fun <U> map(mapper: (L) -> U): LeftProjection<U, R> {
            if (either.isLeft())
                return Left<U, R>(mapper(asLeft())).left()
            else {
                @Suppress("CAST_NEVER_SUCCEEDS")
                return this as LeftProjection<U, R>
            }
        }

        /**
         * Applies the given action to the value if the projected either is a Left. Otherwise nothing happens.
         *
         * @param action An action which takes a left value
         * @return this LeftProjection
         */
        override fun peek(action: (L) -> Unit): LeftProjection<L, R> {
            if (either.isLeft()) {
                action(asLeft())
            }
            return this
        }

        override fun iterator(): Iterator<L> {
            if (either.isLeft()) {
                return iteratorOf(asLeft())
            } else {
                return emptyIterator()
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other?.javaClass != javaClass) return false

            other as LeftProjection<*, *>

            if (either != other.either) return false

            return true
        }

        override fun hashCode(): Int {
            return either.hashCode()
        }

        override fun toString(): String {
            return "LeftProjection($either)"
        }
    }

    class RightProjection<L, R> constructor(val either: Either<L, R>) : Value<R> {

        override fun isEmpty(): Boolean {
            return either.isLeft()
        }


        private fun asLeft(): L {
            return (either as Left<L, R>).get()
        }

        private fun asRight(): R {
            return (either as Right<L, R>).get()
        }

        /**
         * Gets the Right value or throws.
         *
         * @return the left value, if the underlying Either is a Right
         * @throws NoSuchElementException if the underlying either of this RightProjection is a Left
         */
        override fun get(): R {
            if (either.isRight()) {
                return asRight()
            } else {
                throw NoSuchElementException("Either.right().get() on Left")
            }
        }

        /**
         * Gets the Right value or an alternate value, if the projected Either is a Left.
         *
         * @param other an alternative value
         * @return the right value, if the underlying Either is a Right or else {@code other}
         * @throws NoSuchElementException if the underlying either of this RightProjection is a Left
         */
        override fun orElse(other: R): R {
            return if (either.isRight()) asRight() else other
        }

        /**
         * Gets the Right value or an alternate value, if the projected Either is a Left.
         *
         * @param other a function which converts a Left value to an alternative Right value
         * @return the right value, if the underlying Either is a Right or else the alternative Right value provided by
         * {@code other} by applying the Left value.
         */
        fun orElseGet(other: (L) -> R): R {
            if (either.isRight()) {
                return asRight()
            } else {
                return other(asLeft())
            }
        }

        /**
         * Runs an action in the case this is a projection on a Left value.
         *
         * @param action an action which consumes a Left value
         */
        fun orElseRun(action: (L) -> Unit) {
            if (either.isLeft()) {
                action(asLeft())
            }
        }

        /**
         * Gets the Right value or throws, if the projected Either is a Left.
         *
         * @param <X>               a throwable type
         * @param exceptionFunction a function which creates an exception based on a Left value
         * @return the right value, if the underlying Either is a Right or else throws the exception provided by
         * {@code exceptionFunction} by applying the Left value.
         * @throws X if the projected Either is a Left
         */
        @Throws(Throwable::class)
        fun <X : Throwable> orElseThrow(exceptionFunction: (L) -> X): R {
            Objects.requireNonNull(exceptionFunction, "exceptionFunction is null")
            if (either.isRight()) {
                return asRight()
            } else {
                throw exceptionFunction(asLeft())
            }
        }

        /**
         * Returns the underlying either of this projection.
         *
         * @return the underlying either
         */
        fun toEither(): Either<L, R> {
            return either
        }

        /**
         * Returns {@code Some} value of type R if this is a right projection of a Right value and the predicate
         * applies to the underlying value.
         *
         * @param predicate A predicate
         * @return A new Option
         */
        override fun filter(predicate: (R) -> Boolean): Option<R> {
            if (either.isRight()) {
                val value = asRight()
                return if (predicate(value)) Some(value) else None.instance()
            } else {
                return None.instance()
            }
        }

        /**
         * FlatMaps the right value if the projected Either is a Right.
         *
         * @param mapper A mapper which takes a right value and returns a java.lang.Iterable
         * @param <U>    The new type of a Right value
         * @return A new RightProjection
         */
        override fun <U> flatMap(mapper: (R) -> Iterable<U>): RightProjection<L, U> {
            Objects.requireNonNull(mapper, "mapper is null")
            if (either.isRight()) {
                return Right<L, U>(Value[mapper(asRight())]).right()
            } else {
                @Suppress("CAST_NEVER_SUCCEEDS")
                return this as RightProjection<L, U>
            }
        }

        /**
         * Maps the right value if the projected Either is a Right.
         *
         * @param mapper A mapper which takes a right value and returns a value of type U
         * @param <U>    The new type of a Right value
         * @return A new RightProjection
         */
        override fun <U> map(mapper: (R) -> U): RightProjection<L, U> {
            if (either.isRight())
                return Right<L, U>(mapper(asRight())).right()
            else {
                @Suppress("CAST_NEVER_SUCCEEDS")
                return this as RightProjection<L, U>
            }
        }

        /**
         * Applies the given action to the value if the projected either is a Right. Otherwise nothing happens.
         *
         * @param action An action which takes a right value
         * @return this {@code Either} instance
         */
        override fun peek(action: (R) -> Unit): RightProjection<L, R> {
            if (either.isRight()) {
                action(asRight())
            }
            return this
        }

        override fun iterator(): Iterator<R> {
            if (either.isRight()) {
                return iteratorOf(asRight())
            } else {
                return emptyIterator()
            }
        }


        override fun toString(): String {
            return "RightProjection($either)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other?.javaClass != javaClass) return false

            other as RightProjection<*, *>

            if (either != other.either) return false

            return true
        }

        override fun hashCode(): Int {
            return either.hashCode()
        }
    }

}
