import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*

repositories {
    val eapProviders: List<DependencyProviderClassifier> = listOf(object : KotlinEapProviderClassifier {})
    eapProviders.forEach { it.provider(this) }
}

kampJvm(versionsKind = "latest") {
    plugins(BuiltInPlugins.updates)
    source(properties["collectedDependencies"] as SourceSetBuilder<JvmTarget>.() -> Unit)
}
