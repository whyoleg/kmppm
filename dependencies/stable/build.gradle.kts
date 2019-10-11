kampJvm(versionsKind = "stable") {
    plugins(BuiltInPlugins.updates)
    source(properties["collectedDependencies"] as SourceSetBuilder<JvmTarget>.() -> Unit)
}
