package kotlinslang.control

import java.io.Serializable


/**
 * Some represents a defined {@link javaslang.control.Option}. It contains a value which may be null. However, to
 * create an Option containing null, {@code new Some(null)} has to be called. In all other cases
 * {@link Option#of(Object)} is sufficient.
 *
 * @param <T> The type of the optional value.
 * @author Daniel Dietrich
 * @since 1.0.0
 */
final class Some<T>(val value: T?) : Serializable {
    companion object {
        /**
         * The singleton instance of None.
         */
        private val INSTANCE: Some<*> = Some<Nothing>(null)

        /**
         * Returns the singleton instance of None as {@code None<T>} in the context of a type {@code <T>}, e.g.
         * <pre>
         * <code>final Option&lt;Integer&gt; o = None.instance(); // o is of type None&lt;Integer&gt;</code>
         * </pre>
         *
         * @param <T> The type of the optional value.
         * @return None
         */
        public fun <T> nothing(): Some<*> {
            @Suppress("UNCHECKED_CAST")
            val none = INSTANCE as Some<T>;
            return none;
        }
    }

    fun get(): T {
        return value!!;
    }

    fun isEmpty(): Boolean {
        return false
    }

    override fun toString(): String {
        return "Some($value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Some<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }


}