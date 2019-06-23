package dev.whyoleg.kamp.plugin

import dev.whyoleg.kamp.dependency.*

data class Plugin(val name: String, val classpath: RawDependency?) {
    fun version(version: String): Plugin = copy(classpath = classpath?.copy(version = version))
}
