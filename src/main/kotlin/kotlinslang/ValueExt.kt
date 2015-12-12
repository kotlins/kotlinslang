package kotlinslang

import kotlinslang.algebra.Monoid
import kotlinslang.control.None
import kotlinslang.control.Option
import kotlinslang.control.Some
import kotlinslang.control.Try
import kotlinslang.control.tryOf


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */


fun <T : Any> Value<T>.toOption(): Option<T> {
    if (this is Option) {
        return this
    } else {
        return if (isEmpty()) None else Some(get())
    }
}

fun <T : Any> Value<T>.toTry(): Try<T> {
    if (this is Try<T>) {
        return this
    } else {
        return tryOf({ this.get() })
    }
}


/**
 * Returns the underlying value if present, otherwise {@code other}.
 *
 * @param other An alternative value.
 * @return A value of type {@code T}
 */
fun<T : Any> Value<T>.orElse(other: T): T {
    return if (isEmpty()) other else get()
}

/**
 * Returns the underlying value if present, otherwise {@code other}.
 *
 * @param supplier An alternative value supplier.
 * @return A value of type {@code T}
 * @throws NullPointerException if supplier is null
 */
fun<T : Any> Value<T>.orElseGet(supplier: () -> T): T {
    return if (isEmpty()) supplier() else get()
}

fun <U : Any, T : Any> Value<T>.foldLeft(zero: U, combiner: (U, T) -> U): U {
    return if (isEmpty()) zero else combiner(zero, get())
}

fun <U : Any, T : Any> Value<T>.foldRight(zero: U, combiner: (T, U) -> U): U {
    return if (isEmpty()) zero else combiner(get(), zero)
}


/**
 * A fluent if-expression for this value. If this is defined (i.e. not empty) trueVal is returned,
 * otherwise falseVal is returned.
 *
 * @param trueVal  The result, if this is defined.
 * @param falseVal The result, if this is not defined.
 * @return trueVal if this.isDefined(), otherwise falseVal.
 */
fun <T : Any> Value<T>.ifDefined(trueVal: T, falseVal: T): T {
    return if (isDefined()) trueVal else falseVal
}

/**
 * A fluent if-expression for this value. If this is defined (i.e. not empty) trueSupplier.get() is returned,
 * otherwise falseSupplier.get() is returned.
 *
 * @param trueSupplier  The result, if this is defined.
 * @param falseSupplier The result, if this is not defined.
 * @return trueSupplier.get() if this.isDefined(), otherwise falseSupplier.get().
 */
fun <T : Any> Value<T>.ifDefined(trueSupplier: () -> T, falseSupplier: () -> T): T {
    return if (isDefined()) trueSupplier() else falseSupplier()
}

/**
 * A fluent if-expression for this value. If this is empty (i.e. not defined) trueVal is returned,
 * otherwise falseVal is returned.
 *
 * @param trueVal  The result, if this is empty.
 * @param falseVal The result, if this is not empty.
 * @return trueVal if this.isEmpty(), otherwise falseVal.
 */
fun <T : Any> Value<T>.ifEmpty(trueVal: T, falseVal: T): T {
    return if (isEmpty()) trueVal else falseVal
}

/**
 * A fluent if-expression for this value. If this is empty (i.e. not defined) trueSupplier.get() is returned,
 * otherwise falseSupplier.get() is returned.
 *
 * @param trueSupplier  The result, if this is defined.
 * @param falseSupplier The result, if this is not defined.
 * @return trueSupplier.get() if this.isEmpty(), otherwise falseSupplier.get().
 */
fun <T : Any> Value<T>.ifEmpty(trueSupplier: () -> T, falseSupplier: () -> T): T {
    return if (isEmpty()) trueSupplier() else falseSupplier()
}


/**
 * Folds this elements from the left, starting with {@code monoid.zero()} and successively calling {@code monoid::combine}.
 *
 * @param monoid A monoid, providing a {@code zero} and a {@code combine} function.
 * @return a folded value
 * @throws NullPointerException if {@code monoid} is null
 */
fun <T : Any> Value<T>.fold(monoid: Monoid<T>): T {
    return foldLeft(monoid)
}

/**
 * Folds this elements from the left, starting with {@code zero} and successively calling {@code combine}.
 *
 * @param zero    A zero element to start with.
 * @param combiner A function which combines elements.
 * @return a folded value
 * @throws NullPointerException if {@code combine} is null
 */
fun <T : Any> Value<T>.fold(zero: T, combiner: (T, T) -> T): T {
    return foldLeft(zero, combiner)
}

/**
 * Folds this elements from the left, starting with {@code monoid.zero()} and successively calling {@code monoid::combine}.
 *
 * @param monoid A monoid, providing a {@code zero} and a {@code combine} function.
 * @return a folded value
 * @throws NullPointerException if {@code monoid} is null
 */
fun <T : Any> Value<T>.foldLeft(monoid: Monoid<T>): T {
    return foldLeft(monoid.zero(), { t, t2 -> monoid.combine(t, t2) })
}

/**
 * Maps this elements to a {@code Monoid} and applies {@code foldLeft}, starting with {@code monoid.zero()}:
 * <pre><code>
 *  foldLeft(monoid.zero(), (ys, x) -&gt; monoid.combine(ys, mapper.apply(x)));
 * </code></pre>
 *
 * @param monoid A Monoid
 * @param mapper A mapper
 * @param <U : Any>    Component type of the given monoid.
 * @return the folded monoid value.
 * @throws NullPointerException if {@code monoid} or {@code mapper} is null
 */
fun <U : Any, T : Any> Value<T>.foldMap(monoid: Monoid<U>, mapper: (T) -> U): U {
    return foldLeftMap(monoid, mapper)
}

/**
 * Maps this elements to a {@code Monoid} and applies {@code foldLeft}, starting with {@code monoid.zero()}:
 * <pre><code>
 *  foldLeft(monoid.zero(), (ys, x) -&gt; monoid.combine(ys, mapper.apply(x)));
 * </code></pre>
 *
 * @param monoid A Monoid
 * @param mapper A mapper
 * @param <U : Any>    Component type of the given monoid.
 * @return the folded monoid value.
 * @throws NullPointerException if {@code monoid} or {@code mapper} is null
 */
fun <U : Any, T : Any> Value<T>.foldLeftMap(monoid: Monoid<U>, mapper: (T) -> U): U {
    return foldLeft(monoid.zero(), { ys, x -> monoid.combine(ys, mapper(x)) })
}

/**
 * Maps this elements to a {@code Monoid} and applies {@code foldLeft}, starting with {@code monoid.zero()}:
 * <pre><code>
 *  foldLeft(monoid.zero(), (ys, x) -&gt; monoid.combine(ys, mapper.apply(x)));
 * </code></pre>
 *
 * @param monoid A Monoid
 * @param mapper A mapper
 * @param <U : Any>    Component type of the given monoid.
 * @return the folded monoid value.
 * @throws NullPointerException if {@code monoid} or {@code mapper} is null
 */
fun <U : Any, T : Any> Value<T>.foldRightMap(monoid: Monoid<U>, mapper: (T) -> U): U {
    return foldRight(monoid.zero(), { ys, x -> monoid.combine(mapper(ys), x) })
}

/**
 * Folds this elements from the right, starting with {@code monoid.zero()} and successively calling {@code monoid::combine}.
 *
 * @param monoid A monoid, providing a {@code zero} and a {@code combine} function.
 * @return a folded value
 * @throws NullPointerException if {@code monoid} is null
 */
fun <T : Any> Value<T>.foldRight(monoid: Monoid<T>): T {
    return foldRight(monoid.zero(), { t, t2 -> monoid.combine(t, t2) })
}
