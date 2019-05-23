package dev.whyoleg.kamp.dsl

@DslMarker
annotation class KampDSL

@Suppress("EnumEntryName")
enum class SourceType { main, test }

@Suppress("EnumEntryName")
enum class DependencySetType { implementation, api, runtimeOnly, compileOnly }

