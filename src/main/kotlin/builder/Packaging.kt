package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.target.*

class Packaging {

    fun JvmOnlyTarget.jar(block: JarPackaging.() -> Unit) {
        //            apply(plugin = "com.github.johnrengelman.shadow")
        //            apply(plugin = "org.gradle.application")
    }

    fun JvmOnlyTarget.docker(block: JarPackaging.() -> Unit) {
//apply(plugin = Plugins.jib)
    }

    fun JsTarget.webpack(block: JarPackaging.() -> Unit) {

    }

    fun NativeTarget.binary(block: JarPackaging.() -> Unit) {

    }

}