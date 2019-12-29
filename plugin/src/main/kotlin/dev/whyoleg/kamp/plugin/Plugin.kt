package dev.whyoleg.kamp.plugin

import dev.whyoleg.kamp.dependency.*

data class Plugin(val name: String, val classpath: KampDependency? = null)
