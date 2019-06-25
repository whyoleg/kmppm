package dev.whyoleg.kamp.packaging

class PackagersBuilder {
    internal val packagers: MutableList<Packager> = mutableListOf()

    fun jar(block: JarPackager.() -> Unit = {}) {
        packagers += JarPackager().apply(block)
    }

    fun docker(block: DockerPackager.() -> Unit = {}) {
        packagers += DockerPackager().apply(block)
    }

//    fun webpack(block: JarPackaging.() -> Unit) {
//
//    }
//
//    fun binary(block: JarPackaging.() -> Unit) {
//
//    }

}