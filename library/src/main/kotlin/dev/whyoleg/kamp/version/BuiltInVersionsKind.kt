package dev.whyoleg.kamp.version

enum class BuiltInVersionsKind(override val versions: BuiltInVersions) : VersionsKind {
    Default(BuiltInVersions()),
    Stable(BuiltInVersions()),
    Latest(BuiltInVersions())
}
