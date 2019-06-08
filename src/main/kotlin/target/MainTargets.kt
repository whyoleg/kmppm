package dev.whyoleg.kamp.target

interface MainTargets {
    //fast accessors
    val common get() = Companion.common
    val jvm get() = Companion.jvm
    val jvm6 get() = Companion.jvm6
    val android get() = Companion.android
    val jvmBased get() = Companion.jvmBased
    val js get() = Companion.js
    val linuxX64 get() = Companion.linuxX64

    private companion object {
        val common = MetaTarget()

        val jvm = JvmTarget()
        val jvm6 = Jvm6Target()

        val android = AndroidTarget()

        val jvmBased = jvm + jvm6 + android

        val js = JsTarget()
        val linuxX64 = LinuxX64Target()
    }
}