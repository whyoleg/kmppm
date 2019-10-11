import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*

kampJvm(versionsKind = "stable") {
    plugins(BuiltInPlugins.updates)
    source(properties["collectedDependencies"] as SourceSetBuilder<JvmTarget>.() -> Unit)
}
