package dev.whyoleg.kamp.target

abstract class NativeTarget(configure: TargetProvider) : PlatformTarget(configure)

data class LinuxX64Target(override val name: String = "linuxX64") : NativeTarget({ linuxX64(name) })