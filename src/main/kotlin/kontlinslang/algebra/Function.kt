package kontlinslang.algebra


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

public fun<V, T, R> Function1<T, R>.compose(before: (V) -> T): (V) -> R {
    return {v:V ->   run(before(v)  }
}