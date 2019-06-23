package dev.whyoleg.kamp.dependency

data class RawDependency(val group: String, val name: String, val version: String?, val provider: DependencyProvider?) {
    fun string(rawPostfix: String? = null): String {
        val (group, name, rawVersion) = this
        val postfix = rawPostfix?.let { "-$it" } ?: ""
        val version = rawVersion?.let { ":$it" } ?: ""
        return "$group:$name$postfix$version"
    }
}