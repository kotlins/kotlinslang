package kotlinslang.collection


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */


fun <T> List<T>.tail(): List<T> {
    return this.drop(1)
}

infix fun <T> T.prependTo(list: List<T>): List<T> {
    return listOf(this) + list
}
