package dev.whyoleg.kamp.plugin

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

data class Plugin(val name: String, val classpath: RawDependency?) : PackageDependency, UnTypedDependency {
    override val raw: RawDependency get() = classpath ?: error("No classpath for plugin")
    override val targets: Set<TargetWithPostfix<*>> = setOf(Target.jvm())
}