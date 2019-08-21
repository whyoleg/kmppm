package dev.whyoleg.kamp.target

/**
 * Provide fast accessors to built in targets
 */
interface MainTargets {
    val common get() = Companion.common

    val jvm get() = Companion.jvm
    val jvm6 get() = Companion.jvm6
    val jvm8 get() = Companion.jvm8
    val jvmOnly get() = Companion.jvmOnly

    val android get() = Companion.android
    val jvmBased get() = Companion.jvmBased

    val js get() = Companion.js
    val jsBased get() = Companion.jsBased

    val linuxX64 get() = Companion.linuxX64
    val native get() = Companion.native

    private companion object {
        val common = MetaTarget()

        val jvm = JvmTarget()
        val jvm6 = Jvm6Target()
        val jvm8 = Jvm8Target()
        val jvmOnly = setOf(jvm, jvm6, jvm8)

        val android = AndroidTarget()
        val jvmBased = jvmOnly + android

        val js = JsTarget()
        val jsBased = setOf(js)

        val linuxX64 = LinuxX64Target()
        val native = setOf(linuxX64)
    }
}