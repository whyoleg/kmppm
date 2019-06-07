package dev.whyoleg.kamp.base.target

data class TargetWithPostfix<T : Target>(val target: T, val postfix: String?)

operator fun <T : Target> T.invoke(postfix: String? = null): TargetWithPostfix<T> =
    TargetWithPostfix(this, postfix)