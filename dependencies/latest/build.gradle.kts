repositories {
    val eapProviders: List<DependencyProviderClassifier> = listOf(object : KotlinEapProviderClassifier {})
    eapProviders.forEach { it.provider(this) }
}

kampJvm(versionsKind = "latest") {
    plugins(BuiltInPlugins.updates)
    source(properties["collectedDependencies"] as SourceSetBuilder<JvmTarget>.() -> Unit)
}
