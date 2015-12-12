package kotlinslang.control


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */


fun <T : Any> Try<T>.orElseGet(other: (Throwable) -> T): T {
    if (isFailure()) {
        return other(getCause())
    } else {
        return get()
    }
}

/**
 * Returns {@code this}, if this is a {@code Success}, otherwise tries to recover the exception of the failure with {@code f},
 * i.e. calling {@code Try.of(() -> f.apply(throwable))}.
 *
 * @param f A recovery function taking a Throwable
 * @return a new Try
 */
fun <T : Any> Try<T>.recover(f: (Throwable) -> T): Try<T> {
    if (isFailure()) {
        return tryOf { f(getCause()) }
    } else {
        return this
    }
}

/**
 * Returns {@code this}, if this is a Success, otherwise tries to recover the exception of the failure with {@code f},
 * i.e. calling {@code f.apply(cause.getCause())}. If an error occurs recovering a Failure, then the new Failure is
 * returned.
 *
 * @param f A recovery function taking a Throwable
 * @return a new Try
 */
fun <T : Any> Try<T>.recoverWith(f: (Throwable) -> Try<T>): Try<T> {
    if (isFailure()) {
        return try {
            f(getCause())
        } catch (t: Throwable) {
            Failure(t)
        }

    } else {
        return this
    }
}

fun <T : Any> Try<T>.toEither(): Either<Throwable, T> {
    if (isFailure()) {
        return Left(getCause())
    } else {
        return Right(get())
    }
}
