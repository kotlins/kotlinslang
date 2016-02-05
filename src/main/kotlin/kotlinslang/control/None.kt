package kotlinslang.control

import java.io.Serializable
import java.util.NoSuchElementException


/**
 * None is a singleton representation of the {@Code Nothing} {@link kotlinslang.control.Option}. The instance is obtained by
 * calling {@link #instance()}.
 *
 * @param <T> The type of the optional value.
 * @author Daniel Dietrich, Deny Prasetyo
 * @since 1.0.0
 */

object None : Option<Nothing>, Serializable {

    override fun get(): Nothing {
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

}