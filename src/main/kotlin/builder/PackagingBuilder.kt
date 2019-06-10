package dev.whyoleg.kamp.builder

class PackagingBuilder {
    internal val packaging: MutableList<Packaging> = mutableListOf()

    fun jar(block: JarPackaging.() -> Unit) {
        packaging += JarPackaging().apply(block)
    }

    fun docker(block: DockerPackaging.() -> Unit) {
        packaging += DockerPackaging().apply(block)
    }

//    fun webpack(block: JarPackaging.() -> Unit) {
//
//    }
//
//    fun binary(block: JarPackaging.() -> Unit) {
//
//    }

}