package kotlinslang.control

import java.util.NoSuchElementException
import java.util.Optional

/**
 * Create new Option from a Java Optional
 *
 * @param <T>      type of the value
 * @return {@code Some(optional.get())} if value is Java {@code Optional} is present, {@code None} otherwise
 */
fun <T : Any> Optional<T?>.toOption(): Option<T> {
    return if (this.isPresent) optionOf(this.get()) else none()
}

/**
 * Creates a new {@code Option} from nullable Value.
 *
 * @param <T> type of the nullable value
 * @return {@code Some(value)} if value is not {@code null}, {@code None} otherwise
 */
public fun<T : Any> T?.toOption(): Option<T> {
    return if (this != null) {
        Some(this)
    } else {
        None
    }
}

/**
 * Create new Try from a Java Optional
 *
 * @param <T>      type of the value
 * @return {@code Success(optional.get())} if value is Java {@code Optional} is present, {@code Failure(NoSuchElementException)} otherwise
 */
fun <T : Any> Optional<T?>.toTry(): Try<T> {
    return if (this.isPresent) {
        this.get().toTry()
    } else {
        failure(NoSuchElementException("No value present"))
    }
}

/**
 * Creates a new {@code Try} from nullable Value.
 *
 * @param <T> type of the nullable value
 * @return {@code Success(value)} if value is not {@code null}, {@code Failure(NoSuchElementException)} otherwise
 */
public fun<T : Any> T?.toTry(): Try<T> {
    return if (this != null) {
        success(this)
    } else {
        failure(NoSuchElementException("No value present"))
    }
}

/**
 * Creates a new {@code Option} of a given value.
 *
 * @param value A value, can be {@code null}
 * @param <T>   type of the value
 * @return {@code Some(value)} if value is not {@code null}, {@code None} otherwise
 */
fun <T : Any> optionOf(value: T?): Option<T> {
    return if (value == null) None else Some(value)
}

/**
 * Creates a new {@code Some} of a given value.
 * <p>
 * The only difference to {@link optionOf(Object)} is, cannot receive {@code null}.
 * <pre>
 * <code>
 * Option.of(null);   // = None
 * Option.some(null); // = Compile Error
 * </code>
 * </pre>
 *
 * @param value A value
 * @param <T>   type of the value
 * @return {@code Some(value)}
 */
fun <T : Any> some(value: T): Option<T> {
    return Some(value)
}

/**
 * Returns the single instance of {@code None}
 *
 * @param <T> component type
 * @return the single instance of {@code None}
 */
fun <T : Any> none(): Option<T> {
    return None
}

/**
 * Creates {@code Some} of suppliers value if condition is true, or {@code None} in other case
 *
 * @param <T>       type of the optional value
 * @param condition A boolean value
 * @param supplier  An optional value supplier
 * @return return {@code Some} of supplier's value if condition is true, or {@code None} in other case
 */
fun <T : Any> optionWhen(condition: Boolean, supplier: () -> T): Option<T> {
    return if (condition) optionOf(supplier()) else none()
}

/**
 * Creates a Try of a CheckedSupplier.
 *
 * @param supplier A checked supplier
 * @param <T>      Component type
 * @return {@code Success(supplier.get())} if no exception occurs, otherwise {@code Failure(throwable)} if an
 * exception occurs calling {@code supplier.get()}.
 */
fun <T : Any> tryOf(supplier: () -> T?): Try<T> {
    return try {
        supplier().toTry()
    } catch (t: Throwable) {
        Failure(t)
    }
}

/**
 * Creates a Try of a Nullable value.
 *
 * @param value A nullable value.
 * @param <T>  Component type
 * @return {@code Success(value)} if value is not {@code null}, {@code Failure(NoSuchElementException)} otherwise
 * exception occurs calling {@code supplier.get()}.
 */
fun <T : Any> tryOf(value: T?): Try<T> {
    return value.toTry()
}

/**
 * Creates a Try of a Runnable.
 *
 * @param runnable A checked runnable
 * @return {@code Success(Unit)} if no exception occurs, otherwise {@code Failure(throwable)} if an exception occurs
 * calling {@code runnable()}.
 */

fun tryRun(runnable: () -> Unit): Try<Unit> {
    return try {
        runnable()
        Success(Unit)
    } catch (t: Throwable) {
        Failure(t)
    }
}

/**
 * Creates a {@link Success} that contains the given {@code value}. Shortcut for {@code new Success<>(value)}.
 *
 * @param value A value.
 * @param <T> Type of the given {@code value}.
 * @return A new {@code Success}.
 */
fun <T : Any> success(value: T): Try<T> {
    return Success(value)
}

/**
 * Creates a {@link Failure} that contains the given {@code exception}. Shortcut for {@code new Failure<>(exception)}.
 *
 * @param exception An exception.
 * @param <T> Component type of the {@code Try}.
 * @return A new {@code Failure}.
 */
fun <T : Any> failure(exception: Throwable): Try<T> {
    return Failure(exception)
}

/**
 * Constructs a {@link Right}
 *
 * @param right The value.
 * @param <L>   Type of left value.
 * @param <R>   Type of right value.
 * @return A new {@code Right} instance.
 */
fun <R : Any> right(right: R): Either<Any, R> {
    return Right(right)
}

/**
 * Constructs a {@link Left}
 *
 * @param left The value.
 * @param <L>  Type of left value.
 * @param <R>  Type of right value.
 * @return A new {@code Left} instance.
 */
fun <L : Any> left(left: L): Either<L, Any> {
    return Left(left)
}

