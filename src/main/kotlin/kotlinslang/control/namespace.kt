package kotlinslang.control

public fun<T> T?.toOption(): Option<T> {
    return if (this != null) {
        Some(this)
    } else {
        None.instance()
    }
}