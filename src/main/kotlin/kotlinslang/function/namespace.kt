package kotlinslang.function


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */


/**
 * Returns a composed function that first applies the {@code before}
 * function to its input, and then applies this function to the result.
 * If evaluation of either function throws an exception, it is relayed to
 * the caller of the composed function.
 *
 * @param <V> the type of input to the {@code before} function, and to the
 *           composed function
 * @param before the function to apply before this function is applied
 * @return a composed function that first applies the {@code before}
 * function and then applies this function
 * @throws NullPointerException if before is null
 *
 * @see #andThen(Function)
 */
infix public fun<V, T, R> Function1<T, R>.compose(before: (V) -> T): (V) -> R {
    return { v: V -> this(before(v)) }
}

/**
 * Returns a composed function that first applies this function to
 * its input, and then applies the {@code after} function to the result.
 * If evaluation of either function throws an exception, it is relayed to
 * the caller of the composed function.
 *
 * @param <V> the type of output of the {@code after} function, and of the
 *           composed function
 * @param after the function to apply after this function is applied
 * @return a composed function that first applies this function and then
 * applies the {@code after} function
 * @throws NullPointerException if after is null
 *
 * @see #compose(Function)
 */
infix public fun<V, T, R> Function1<T, R>.forwardCompose(after: (R) -> V): (T) -> V = andThen(after)

/**
 * Returns a composed function that first applies this function to
 * its input, and then applies the {@code after} function to the result.
 * If evaluation of either function throws an exception, it is relayed to
 * the caller of the composed function.
 *
 * @param <V> the type of output of the {@code after} function, and of the
 *           composed function
 * @param after the function to apply after this function is applied
 * @return a composed function that first applies this function and then
 * applies the {@code after} function
 * @throws NullPointerException if after is null
 *
 * @see #forwardCompose(Function)
 */
infix public fun<V, T, R> Function1<T, R>.andThen(after: (R) -> V): (T) -> V {
    return { t: T -> after(this(t)) }
}

/**
 * Returns a function that always returns its input argument.
 *
 * @param <T> the type of the input and output objects to the function
 * @return a function that always returns its input argument
 */
public fun<T> identity(): (T) -> T {
    return { t: T -> t }
}
