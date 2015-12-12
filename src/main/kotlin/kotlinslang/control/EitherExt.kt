package kotlinslang.control


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */


/**
 * Gets the Left value or an alternate value, if the projected Either is a Right.
 *
 * @param other an alternative value
 * @return the left value, if the underlying Either is a Left or else {@code other}
 * @throws NoSuchElementException if the underlying either of this LeftProjection is a Right
 */
fun <L : Any, R : Any> Either.LeftProjection<L, R>.orElse(other: L): L {
    return if (either.isLeft()) asLeft() else other
}

/**
 * Gets the Left value or an alternate value, if the projected Either is a Right.
 *
 * @param other a function which converts a Right value to an alternative Left value
 * @return the left value, if the underlying Either is a Left or else the alternative Left value provided by
 * {@code other} by applying the Right value.
 */
fun<L : Any, R : Any> Either.LeftProjection<L, R>.orElseGet(other: (R) -> L): L {
    if (either.isLeft()) {
        return asLeft()
    } else {
        return other(asRight())
    }
}


/**
 * Gets the Right value or an alternate value, if the projected Either is a Left.
 *
 * @param other an alternative value
 * @return the right value, if the underlying Either is a Right or else {@code other}
 * @throws NoSuchElementException if the underlying either of this RightProjection is a Left
 */
fun <L : Any, R : Any> Either.RightProjection<L, R>.orElse(other: R): R {
    return if (either.isRight()) asRight() else other
}

/**
 * Gets the Right value or an alternate value, if the projected Either is a Left.
 *
 * @param other a function which converts a Left value to an alternative Right value
 * @return the right value, if the underlying Either is a Right or else the alternative Right value provided by
 * {@code other} by applying the Left value.
 */
fun <L : Any, R : Any> Either.RightProjection<L, R>.orElseGet(other: (L) -> R): R {
    if (either.isRight()) {
        return asRight()
    } else {
        return other(asLeft())
    }
}
