package dev.whyoleg.kamp.packager

import dev.whyoleg.kamp.*

class PackagersBuilder(val builtIn: BuiltIn) {
    internal val packagers: MutableList<Packager> = mutableListOf()

    fun jar(block: JarPackager.() -> Unit = {}) {
        packagers += JarPackager(builtIn).apply(block)
    }

    fun docker(block: DockerPackager.() -> Unit = {}) {
        packagers += DockerPackager(builtIn).apply(block)
    }

//    fun webpack(block: JarPackaging.() -> Unit) {
//
//    }
//
//    fun binary(block: JarPackaging.() -> Unit) {
//
//    }

}