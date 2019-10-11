package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.version.*

data class RawDependency(
    val group: String,
    val name: String,
    val version: (BuiltInVersions) -> String?,
    val provider: DependencyProvider?
) {
    fun string(versions: BuiltInVersions, rawPostfix: String? = null): String {
        val (group, name, rawVersionBlock) = this
        val rawVersion = rawVersionBlock(versions)
        val postfix = rawPostfix?.let { "-$it" } ?: ""
        val version = rawVersion?.let { ":$it" } ?: ""
        return "$group:$name$postfix$version"
    }
}
