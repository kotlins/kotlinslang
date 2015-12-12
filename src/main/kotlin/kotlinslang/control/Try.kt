package kotlinslang.control

import kotlinslang.Value
import kotlinslang.collection.emptyIterator
import kotlinslang.collection.iteratorOf
import kotlinslang.toTry
import java.util.NoSuchElementException


/**
 * An implementation similar to Scala's Try control.
 *
 * @param <T> Value type in the case of success.
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */
interface Try<out T : Any> : Value<T> {

    /**
     * Gets the result of this Try if this is a Success or throws if this is a Failure.
     *
     * @return The result of this Try.
     * @throws Throwable if this is a Failure
     */
    override fun get(): T

    /**
     * Gets the cause if this is a Failure or throws if this is a Success.
     *
     * @return The cause if this is a Failure
     * @throws UnsupportedOperationException if this is a Success
     */
    fun getCause(): Throwable

    /**
     * Checks whether this Try has no result, i.e. is a Failure.
     *
     * @return true if this is a Failure, returns false if this is a Success.
     */
    override fun isEmpty(): Boolean

    /**
     * Checks if this is a Failure.
     *
     * @return true, if this is a Failure, otherwise false, if this is a Success
     */
    fun isFailure(): Boolean

    /**
     * Checks if this is a Success.
     *
     * @return true, if this is a Success, otherwise false, if this is a Failure
     */
    fun isSuccess(): Boolean


    /**
     * Runs the given checked consumer if this is a {@code Success},
     * passing the result of the current expression to it.
     * If this expression is a {@code Failure} then it'll return a new
     * {@code Failure} of type T with the original exception.
     *
     * The main use case is chaining checked functions using method references:
     *
     * <pre>
     * <code>
     * Try.of(() -&gt; 100)
     *    .andThen(i -&gt; System.out.println(i));
     *
     * </code>
     * </pre>
     *
     * @param consumer A checked consumer taking a single argument.
     * @return a new {@code Try}
     */

    fun andThen(consumer: (T) -> Unit): Try<T> {
        if (isFailure()) {
            return this
        } else {
            return tryRun { consumer(get()) }.flatMap { ignored -> this }
        }
    }

    /**
     * Runs the given runnable if this is a {@code Success}, otherwise returns this {@code Failure}.
     * Shorthand for {@code flatMap(ignored -> Try.run(runnable))}.
     * The main use case is chaining runnables using method references:
     *
     * <pre>
     * <code>
     * Try.run(A::methodRef).andThen(B::methodRef).andThen(C::methodRef);
     * </code>
     * </pre>
     *
     * Please note that these lines are semantically the same:
     *
     * <pre>
     * <code>
     * Try.run(() -&gt; { doStuff(); })
     *    .andThen(() -&gt; { doMoreStuff(); })
     *    .andThen(() -&gt; { doEvenMoreStuff(); });
     *
     * Try.run(() -&gt; {
     *     doStuff();
     *     doMoreStuff();
     *     doEvenMoreStuff();
     * });
     * </code>
     * </pre>
     *
     * @param runnable A checked runnable
     * @return a new {@code Try}
     */
    fun andThen(runnable: () -> Unit): Try<Unit> {
        return flatMap { ignored -> tryRun(runnable) }
    }

    /**
     * Returns {@code Success(throwable)} if this is a {@code Failure(throwable)}, otherwise
     * a {@code Failure(new NoSuchElementException("Success.failed()"))} if this is a Success.
     *
     * @return a new Try
     */
    fun failed(): Try<Throwable> {
        if (isFailure()) {
            return Success(getCause())
        } else {
            return Failure(NoSuchElementException("Success.failed()"))
        }
    }

    /**
     * Returns {@code this} if this is a Failure or this is a Success and the value satisfies the predicate.
     * <p>
     * Returns a new Failure, if this is a Success and the value does not satisfy the Predicate or an exception
     * occurs testing the predicate.
     *
     * @param predicate A predicate
     * @return a new Try
     */
    override fun filter(predicate: (T) -> Boolean): Try<T> {
        if (isFailure()) {
            return this
        } else {
            try {
                if (predicate(get())) {
                    return this
                } else {
                    return Failure(NoSuchElementException("Predicate does not hold for " + get()))
                }
            } catch (t: Throwable) {
                return Failure(t)
            }

        }
    }

    /**
     * FlatMaps the value of a Success or returns a Failure.
     *
     * @param mapper A mapper
     * @param <U>    The new component type
     * @return a new Try
     */
    override fun <U : Any> flatMap(mapper: (T) -> Iterable<U>): Try<U> {
        if (isFailure()) {
            @Suppress("UNCHECKED_CAST")
            return this as Failure<U>
        } else {
            try {
                val iterable = mapper(get())
                if (iterable is Value<U>) {
                    return iterable.toTry()
                } else {
                    return tryOf { Value[iterable] }
                }
            } catch (t: Throwable) {
                return Failure(t)
            }

        }
    }

    /**
     * Runs the given checked function if this is a {@code Success},
     * passing the result of the current expression to it.
     * If this expression is a {@code Failure} then it'll return a new
     * {@code Failure} of type R with the original exception.
     *
     * The main use case is chaining checked functions using method references:
     *
     * <pre>
     * <code>
     * Try.of(() -&gt; 0)
     *    .mapTry(x -&gt; 1 / x); // division by zero
     * </code>
     * </pre>
     *
     * @param <U>    The new component type
     * @param mapper A checked function
     * @return a new {@code Try}
     */
    override fun <U : Any> map(mapper: (T) -> U): Try<U> {
        if (isFailure()) {
            @Suppress("UNCHECKED_CAST")
            return this as Failure<U>
        } else {
            return tryOf { mapper(get()) }
        }
    }

    override fun iterator(): Iterator<T> {
        return if (isSuccess()) iteratorOf(get()) else emptyIterator()
    }

    /**
     * Consumes the throwable if this is a Failure.
     *
     * @param action An exception consumer
     * @return a new Failure, if this is a Failure and the consumer throws, otherwise this, which may be a Success or
     * a Failure.
     */
    fun onFailure(action: (Throwable) -> Unit): Try<T> {
        if (isFailure()) {
            try {
                action(getCause())
                return this
            } catch (t: Throwable) {
                return Failure(t)
            }

        } else {
            return this
        }
    }

    /**
     * Consumes the value if this is a Success.
     *
     * @param action A value consumer
     * @return a new Failure, if this is a Success and the consumer throws, otherwise this, which may be a Success or
     * a Failure.
     */
    fun onSuccess(action: (T) -> Unit): Try<T> {
        if (isSuccess()) {
            try {
                action(get())
                return this
            } catch (t: Throwable) {
                return Failure(t)
            }

        } else {
            return this
        }
    }

    fun orElseRun(action: (Throwable) -> Unit) {
        if (isFailure()) {
            action(getCause())
        }
    }

    @Throws(Throwable::class)
    fun <X : Throwable> orElseThrow(exceptionProvider: (Throwable) -> X): T {
        if (isFailure()) {
            throw exceptionProvider(getCause())
        } else {
            return get()
        }
    }

    /**
     * Applies the action to the value of a Success or does nothing in the case of a Failure.
     *
     * @param action A Consumer
     * @return this Try
     */
    override fun peek(action: (T) -> Unit): Try<T> {
        return onSuccess(action)
    }


    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    override fun toString(): String

}
