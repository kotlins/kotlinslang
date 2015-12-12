package kotlinslang.function


/**
 * [TODO: Documentation]
 *
 * @author Deny Trasetyo.
 */


public fun<T1, T2, R> Function2<T1, T2, R>.curried(): (T1) -> (T2) -> R {
    return { p1: T1 -> { p2: T2 -> this(p1, p2) } }
}


public fun<T1, T2, T3, R> Function3<T1, T2, T3, R>.curried(): (T1) -> (T2) -> (T3) -> R {
    return { p1: T1 -> { p2: T2 -> { p3: T3 -> this(p1, p2, p3) } } }
}


public fun<T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R>.curried(): (T1) -> (T2) -> (T3) -> (T4) -> R {
    return { p1: T1 -> { p2: T2 -> { p3: T3 -> { p4: T4 -> this(p1, p2, p3, p4) } } } }
}


public fun<T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R>.curried(): (T1) -> (T2) -> (T3) -> (T4) -> (T5) -> R {
    return { p1: T1 -> { p2: T2 -> { p3: T3 -> { p4: T4 -> { p5: T5 -> this(p1, p2, p3, p4, p5) } } } } }
}


public fun<T1, T2, T3, T4, T5, T6, R> Function6<T1, T2, T3, T4, T5, T6, R>.curried(): (T1) -> (T2) -> (T3) -> (T4) -> (T5) -> (T6) -> R {
    return { p1: T1 -> { p2: T2 -> { p3: T3 -> { p4: T4 -> { p5: T5 -> { p6: T6 -> this(p1, p2, p3, p4, p5, p6) } } } } } }
}


public fun<T1, T2, T3, T4, T5, T6, T7, R> Function7<T1, T2, T3, T4, T5, T6, T7, R>.curried(): (T1) -> (T2) -> (T3) -> (T4) -> (T5) -> (T6) -> (T7) -> R {
    return { p1: T1 -> { p2: T2 -> { p3: T3 -> { p4: T4 -> { p5: T5 -> { p6: T6 -> { p7: T7 -> this(p1, p2, p3, p4, p5, p6, p7) } } } } } } }
}


public fun<T1, T2, R> ((T1) -> (T2) -> R).uncurried(): (T1, T2) -> R {
    return { p1: T1, p2: T2 -> this(p1)(p2) }
}


public fun<T1, T2, T3, R> ((T1) -> (T2) -> (T3) -> R).uncurried(): (T1, T2, T3) -> R {
    return { p1: T1, p2: T2, p3: T3 -> this(p1)(p2)(p3) }
}


public fun<T1, T2, T3, T4, R> ((T1) -> (T2) -> (T3) -> (T4) -> R).uncurried(): (T1, T2, T3, T4) -> R {
    return { p1: T1, p2: T2, p3: T3, p4: T4 -> this(p1)(p2)(p3)(p4) }
}


public fun<T1, T2, T3, T4, T5, R> ((T1) -> (T2) -> (T3) -> (T4) -> (T5) -> R).uncurried(): (T1, T2, T3, T4, T5) -> R {
    return { p1: T1, p2: T2, p3: T3, p4: T4, p5: T5 -> this(p1)(p2)(p3)(p4)(p5) }
}


public fun<T1, T2, T3, T4, T5, T6, R> ((T1) -> (T2) -> (T3) -> (T4) -> (T5) -> (T6) -> R).uncurried(): (T1, T2, T3, T4, T5, T6) -> R {
    return { p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6 -> this(p1)(p2)(p3)(p4)(p5)(p6) }
}


public fun<T1, T2, T3, T4, T5, T6, T7, R> ((T1) -> (T2) -> (T3) -> (T4) -> (T5) -> (T6) -> (T7) -> R).uncurried(): (T1, T2, T3, T4, T5, T6, T7) -> R {
    return { p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6, p7: T7 -> this(p1)(p2)(p3)(p4)(p5)(p6)(p7) }
}