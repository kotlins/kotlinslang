package kotlinslang

import kotlinslang.algebra.Foldable
import kotlinslang.algebra.Monad
import kotlinslang.control.None
import kotlinslang.control.Option
import kotlinslang.control.Some
import kotlinslang.control.Try


/**
 * Functional programming is all about values and transformation of values using functions. The {@code Value}
 * type reflects the values in a functional setting. It can be seen as the result of a partial function application.
 * Hence the result may be undefined. If a value is undefined, we say it is empty.
 * <p>
 * How the empty state is interpreted depends on the context, i.e. it may be <em>undefined</em>, <em>failed</em>,
 * <em>not yet defined</em>, etc.
 * <p>
 *
 * Static methods:
 *
 * <ul>
 * <li>{@link #get(java.lang.Iterable)}</li>
 * </ul>
 *
 * Basic operations:
 *
 * <ul>
 * <li>{@link #get()}</li>
 * <li>{@link #getOption()}</li>
 * <li>{@link #ifDefined(Supplier, Supplier)}</li>
 * <li>{@link #ifDefined(Object, Object)}</li>
 * <li>{@link #ifEmpty(Supplier, Supplier)}</li>
 * <li>{@link #ifEmpty(Object, Object)}</li>
 * <li>{@link #isDefined()}</li>
 * <li>{@link #isEmpty()}</li>
 * <li>{@link #orElse(Object)}</li>
 * <li>{@link #orElseGet(Supplier)}</li>
 * <li>{@link #orElseThrow(Supplier)}</li>
 * </ul>
 *
 * Equality checks:
 *
 * <ul>
 * <li>{@link #corresponds(java.lang.Iterable, BiPredicate)}</li>
 * <li>{@link #eq(Object)}</li>
 * </ul>
 *
 * Side-effects:
 *
 * <ul>
 * <li>{@link #out(PrintStream)}</li>
 * <li>{@link #out(PrintWriter)}</li>
 * <li>{@link #peek(Consumer)}</li>
 * <li>{@link #stderr()}</li>
 * <li>{@link #stdout()}</li>
 * </ul>
 *
 * Tests:
 *
 * <ul>
 * <li>{@link #isSingletonType()}</li>
 * </ul>
 *
 * @param <T> The type of the wrapped value.
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */

interface Value<T> : Iterable<T>, Foldable<T>, Monad<T> {

    companion object {
        /**
         * Gets the first value of the given Iterable if exists, otherwise throws.
         *
         * @param iterable An java.lang.Iterable
         * @param <T>      Component type
         * @return An object of type T
         * @throws java.util.NoSuchElementException if the given iterable is empty
         */
        operator fun <T> get(iterable: Iterable<T>): T {
            if (iterable is Value<T>) {
                return iterable.get()
            } else {
                return iterable.iterator().next()
            }
        }
    }

    /**
     * Gets the underlying as Option.
     *
     * @return Some(value) if a value is present, None otherwise
     */
    fun getOption(): Option<T> {
        return if (isEmpty()) None.instance() else Some(get())
    }

    fun get(): T


    override fun toOption(): Option<T> {
        if (this is Option) {
            return this
        } else {
            return if (isEmpty()) None.instance() else Some(get())
        }
    }

    override fun toTry(): Try<T> {
        if (this is Try<T>) {
            return this
        } else {
            return Try.of({ this.get() })
        }
    }

    /**
     * Checks, this {@code Value} is empty, i.e. if the underlying value is absent.
     *
     * @return false, if no underlying value is present, true otherwise.
     */
    fun isEmpty(): Boolean

    /**
     * Checks, this {@code Value} is defined, i.e. if the underlying value is present.
     *
     * @return true, if an underlying value is present, false otherwise.
     */
    fun isDefined(): Boolean {
        return !isEmpty()
    }

    /**
     * States, if this value may contain (at most) one element or more than one element, like collections.
     * <p>
     * We call a type <em>singleton type</em>, which may contain at most one element.
     *
     * @return {@code true}, if this is a singleton type, {@code false} otherwise.
     */
    fun isSingletonType(): Boolean

    /**
     * Returns the underlying value if present, otherwise {@code other}.
     *
     * @param other An alternative value.
     * @return A value of type {@code T}
     */
    fun orElse(other: T): T {
        return if (isEmpty()) other else get()
    }

    /**
     * Returns the underlying value if present, otherwise {@code other}.
     *
     * @param supplier An alternative value supplier.
     * @return A value of type {@code T}
     * @throws NullPointerException if supplier is null
     */
    fun orElseGet(supplier: () -> T): T {
        return if (isEmpty()) supplier() else get()
    }

    /**
     * Returns the underlying value if present, otherwise throws {@code supplier.get()}.
     *
     * @param <X>      a Throwable type
     * @param supplier An exception supplier.
     * @return A value of type {@code T}.
     * @throws NullPointerException if supplier is null
     * @throws X                    if no value is present
     */
    @Throws(exceptionClasses = Throwable::class)
    fun <X : Throwable> orElseThrow(supplier: () -> X): T {
        if (isEmpty()) {
            throw supplier()
        } else {
            return get()
        }
    }

    /**
     * Performs the given {@code action} on the first element if this is an <em>eager</em> implementation.
     * Performs the given {@code action} on all elements (the first immediately, successive deferred),
     * if this is a <em>lazy</em> implementation.
     *
     * @param action The action that will be performed on the element(s).
     * @return this instance
     */
    fun peek(action: (T) -> Unit): Value<T>

    /**
     * Clarifies that values have a proper equals() method implemented.
     * <p>
     * See <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-">Object.equals(Object)</a>.
     *
     * @param o An object
     * @return true, if this equals o, false otherwise
     */
    override fun equals(other: Any?): Boolean

    /**
     * Clarifies that values have a proper hashCode() method implemented.
     * <p>
     * See <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode--">Object.hashCode()</a>.
     *
     * @return The hashcode of this object
     */
    override fun hashCode(): Int

    /**
     * Clarifies that values have a proper toString() method implemented.
     * <p>
     * See <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#toString--">Object.toString()</a>.
     *
     * @return A String representation of this object
     */
    override fun toString(): String

    /**
     * A fluent if-expression for this value. If this is defined (i.e. not empty) trueVal is returned,
     * otherwise falseVal is returned.
     *
     * @param trueVal  The result, if this is defined.
     * @param falseVal The result, if this is not defined.
     * @return trueVal if this.isDefined(), otherwise falseVal.
     */
    fun ifDefined(trueVal: T, falseVal: T): T {
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
    fun ifDefined(trueSupplier: () -> T, falseSupplier: () -> T): T {
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
    fun ifEmpty(trueVal: T, falseVal: T): T {
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
    fun ifEmpty(trueSupplier: () -> T, falseSupplier: () -> T): T {
        return if (isEmpty()) trueSupplier() else falseSupplier()
    }

    override fun filter(predicate: (T) -> Boolean): Value<T>
    override fun <U> flatMap(mapper: (T) -> Iterable<U>): Value<U>
    override fun <U> map(mapper: (T) -> U): Value<U>

    // DEV-NOTE: default implementations for singleton types, needs to be overridden by multi valued types
    override fun <U> foldLeft(zero: U, combine: (U, T) -> U): U {
        return if (isEmpty()) zero else combine(zero, get())
    }

    // DEV-NOTE: default implementations for singleton types, needs to be overridden by multi valued types
    override fun <U> foldRight(zero: U, combine: (T, U) -> U): U {
        return if (isEmpty()) zero else combine(get(), zero)
    }
}