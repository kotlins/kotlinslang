package kotlinslang.function


/**
 * Helpers and Extension Functions that applied to Function1 class.
 *
 * @author Daniel Dietrich, Deny Prasetyo.
 */


/**
 * Returns a composed function that first applies the {@code before}
 * function to its input, and then applies this function to the result.
 * If evaluation of either function throws an exception, it is relayed to
 * the caller of the composed function.
 *
 * @param <V,T> the type of {@code before} function
 * @param <T,R> the type of Function1, Extended function
 * @param before the function to apply before this function is applied
 * @return a composed function with type <V,R> that first applies the {@code before}
 * function and then applies this function
 *
 * @see #andThen(Function1)
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
 * @param <R,V> the type of {@code after} function
 * @param <T,R> the type of Function1, Extended function
 * @param after the function to apply after this function is applied
 * @return a composed function with type <T,V> that first applies this function and then
 * applies the {@code after} function
 *
 * @see #compose(Function1)
 */
infix public fun<V, T, R> Function1<T, R>.forwardCompose(after: (R) -> V): (T) -> V = andThen(after)

/**
 * Returns a composed function that first applies this function to
 * its input, and then applies the {@code after} function to the result.
 * If evaluation of either function throws an exception, it is relayed to
 * the caller of the composed function.
 *
 * @param <R,V> the type of {@code after} function
 * @param <T,R> the type of Function1, Extended function
 * @param after the function to apply after this function is applied
 * @return a composed function with type <T,V> that first applies this function and then
 * applies the {@code after} function
 *
 * @see #compose(Function1)
 * @see #forwardCompose(Function1)
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
