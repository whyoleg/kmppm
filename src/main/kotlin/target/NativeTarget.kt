package dev.whyoleg.kamp.target

abstract class NativeTarget(name: String, configure: TargetProvider) : PlatformTarget(name, configure)

data class LinuxX64Target(override val name: String = "linuxX64") : NativeTarget(name, { linuxX64(it.name) })