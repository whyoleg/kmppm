package dev.whyoleg.kamp.module

import dev.whyoleg.kamp.dependency.*

data class Module(val name: String, val path: String? = null) : UnTypedDependency