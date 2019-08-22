package dev.whyoleg.kamp.module

abstract class RootModule : BaseModule() {
    final override val name: String = ""
    final override val path: String? = null
    final override val ignored: Boolean = false

    /**
     * Hack to initialize module structure based on delegation
     */
    internal fun lazy(): Unit = lazy(this)

    private fun lazy(any: Any): Unit = any::class.nestedClasses.forEach { it.objectInstance?.let(this::lazy) }
}