package kotlinslang.function


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */


public fun<T1, T2, R> Function2<T1, T2, R>.reverse(): (T2, T1) -> R {
    return { t2: T2, t1: T1 -> this(t1, t2) }
}


public fun<T1, T2, T3, R> Function3<T1, T2, T3, R>.reverse(): (T3, T2, T1) -> R {
    return { t3: T3, t2: T2, t1: T1 -> this(t1, t2, t3) }
}


public fun<T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R>.reverse(): (T4, T3, T2, T1) -> R {
    return { t4: T4, t3: T3, t2: T2, t1: T1 -> this(t1, t2, t3, t4) }
}


public fun<T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R>.reverse(): (T5, T4, T3, T2, T1) -> R {
    return { t5: T5, t4: T4, t3: T3, t2: T2, t1: T1 -> this(t1, t2, t3, t4, t5) }
}


public fun<T1, T2, T3, T4, T5, T6, R> Function6<T1, T2, T3, T4, T5, T6, R>.reverse(): (T6, T5, T4, T3, T2, T1) -> R {
    return { t6: T6, t5: T5, t4: T4, t3: T3, t2: T2, t1: T1 -> this(t1, t2, t3, t4, t5, t6) }
}


public fun<T1, T2, T3, T4, T5, T6, T7, R> Function7<T1, T2, T3, T4, T5, T6, T7, R>.reverse(): (T7, T6, T5, T4, T3, T2, T1) -> R {
    return { t7: T7, t6: T6, t5: T5, t4: T4, t3: T3, t2: T2, t1: T1 -> this(t1, t2, t3, t4, t5, t6, t7) }
}

