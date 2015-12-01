package kotlinslang.control

import java.io.Serializable
import java.util.*


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

final class None<T> private constructor() : Option<T>, Serializable {
    companion object {
        /**
         * The singleton instance of None.
         */
        private val INSTANCE: None<*> = None<Nothing>()

        /**
         * Returns the singleton instance of None as {@code None<T>} in the context of a type {@code <T>}, e.g.
         * <pre>
         * <code>final Option&lt;Integer&gt; o = None.instance(); // o is of type None&lt;Integer&gt;</code>
         * </pre>
         *
         * @param <T> The type of the optional value.
         * @return None
         */
        public fun <T> instance(): None<T> {
            @Suppress("UNCHECKED_CAST")
            val none = INSTANCE as None<T>;
            return none;
        }
    }

    override fun get(): T {
        throw NoSuchElementException("No value present")
    }

    override fun isEmpty(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        return other === this;
    }

    override fun hashCode(): Int {
        return 1
    }

    override fun toString(): String {
        return "None"
    }

    /**
     * Instance control for object serialization.
     *
     * @return The singleton instance of None.
     * @see java.io.Serializable
     */
    private fun readResolve(): Any {
        return INSTANCE
    }
}