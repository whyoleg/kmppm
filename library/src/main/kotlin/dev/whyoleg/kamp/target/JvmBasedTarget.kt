package dev.whyoleg.kamp.target

abstract class JvmBasedTarget(configure: TargetProvider) : PlatformTarget(configure)

abstract class JvmOnlyTarget(configure: TargetProvider) : JvmBasedTarget(configure)

data class JvmTarget(override val name: String = "jvm") : JvmOnlyTarget({ jvm(name) })
data class Jvm8Target(override val name: String = "jvm8") : JvmOnlyTarget({ jvm(name) })
data class Jvm6Target(override val name: String = "jvm6") : JvmOnlyTarget({ jvm(name) })
data class AndroidTarget(override val name: String = "android") : JvmBasedTarget({ android(name) })
