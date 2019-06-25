package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

interface PackageDependency : Dependency {
    val raw: RawDependency
    val targets: Set<TargetWithPostfix<*>>
}
