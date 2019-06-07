package dev.whyoleg.kamp.base.target

data class LinuxX64Target(override val name: String = "linuxX64") : PlatformTarget(name, { linuxX64(it.name) })