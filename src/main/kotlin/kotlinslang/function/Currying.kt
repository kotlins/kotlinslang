package kotlinslang.function


/**
 * Extension for Convert Function[2 - 7] to Curried Function and Vice-versa.
 *
 * @author Mario Arias, Deny Prasetyo.
 */


public fun<T1, T2, R> Function2<T1, T2, R>.curried(): (T1) -> (T2) -> R {
    return { t1: T1 -> { t2: T2 -> this(t1, t2) } }
}


public fun<T1, T2, T3, R> Function3<T1, T2, T3, R>.curried(): (T1) -> (T2) -> (T3) -> R {
    return { t1: T1 -> { t2: T2 -> { t3: T3 -> this(t1, t2, t3) } } }
}


public fun<T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R>.curried(): (T1) -> (T2) -> (T3) -> (T4) -> R {
    return { t1: T1 -> { t2: T2 -> { t3: T3 -> { t4: T4 -> this(t1, t2, t3, t4) } } } }
}


public fun<T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R>.curried(): (T1) -> (T2) -> (T3) -> (T4) -> (T5) -> R {
    return { t1: T1 -> { t2: T2 -> { t3: T3 -> { t4: T4 -> { t5: T5 -> this(t1, t2, t3, t4, t5) } } } } }
}


public fun<T1, T2, T3, T4, T5, T6, R> Function6<T1, T2, T3, T4, T5, T6, R>.curried(): (T1) -> (T2) -> (T3) -> (T4) -> (T5) -> (T6) -> R {
    return { t1: T1 -> { t2: T2 -> { t3: T3 -> { t4: T4 -> { t5: T5 -> { t6: T6 -> this(t1, t2, t3, t4, t5, t6) } } } } } }
}


public fun<T1, T2, T3, T4, T5, T6, T7, R> Function7<T1, T2, T3, T4, T5, T6, T7, R>.curried(): (T1) -> (T2) -> (T3) -> (T4) -> (T5) -> (T6) -> (T7) -> R {
    return { t1: T1 -> { t2: T2 -> { t3: T3 -> { t4: T4 -> { t5: T5 -> { t6: T6 -> { t7: T7 -> this(t1, t2, t3, t4, t5, t6, t7) } } } } } } }
}


public fun<T1, T2, R> ((T1) -> (T2) -> R).uncurried(): (T1, T2) -> R {
    return { t1: T1, t2: T2 -> this(t1)(t2) }
}


public fun<T1, T2, T3, R> ((T1) -> (T2) -> (T3) -> R).uncurried(): (T1, T2, T3) -> R {
    return { t1: T1, t2: T2, t3: T3 -> this(t1)(t2)(t3) }
}


public fun<T1, T2, T3, T4, R> ((T1) -> (T2) -> (T3) -> (T4) -> R).uncurried(): (T1, T2, T3, T4) -> R {
    return { t1: T1, t2: T2, t3: T3, t4: T4 -> this(t1)(t2)(t3)(t4) }
}


public fun<T1, T2, T3, T4, T5, R> ((T1) -> (T2) -> (T3) -> (T4) -> (T5) -> R).uncurried(): (T1, T2, T3, T4, T5) -> R {
    return { t1: T1, t2: T2, t3: T3, t4: T4, t5: T5 -> this(t1)(t2)(t3)(t4)(t5) }
}


public fun<T1, T2, T3, T4, T5, T6, R> ((T1) -> (T2) -> (T3) -> (T4) -> (T5) -> (T6) -> R).uncurried(): (T1, T2, T3, T4, T5, T6) -> R {
    return { t1: T1, t2: T2, t3: T3, t4: T4, t5: T5, t6: T6 -> this(t1)(t2)(t3)(t4)(t5)(t6) }
}


public fun<T1, T2, T3, T4, T5, T6, T7, R> ((T1) -> (T2) -> (T3) -> (T4) -> (T5) -> (T6) -> (T7) -> R).uncurried(): (T1, T2, T3, T4, T5, T6, T7) -> R {
    return { t1: T1, t2: T2, t3: T3, t4: T4, t5: T5, t6: T6, t7: T7 -> this(t1)(t2)(t3)(t4)(t5)(t6)(t7) }
}