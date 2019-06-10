package dev.whyoleg.kamp.target

abstract class JvmBasedTarget(name: String, configure: TargetProvider) : PlatformTarget(name, configure)

abstract class JvmOnlyTarget(name: String, configure: TargetProvider) : JvmBasedTarget(name, configure)

data class JvmTarget(override val name: String = "jvm") : JvmOnlyTarget(name, { jvm(it.name) })
data class Jvm8Target(override val name: String = "jvm8") : JvmOnlyTarget(name, { jvm(it.name) })
data class Jvm6Target(override val name: String = "jvm6") : JvmOnlyTarget(name, { jvm(it.name) })
data class AndroidTarget(override val name: String = "android") : JvmBasedTarget(name, { android(it.name) })
