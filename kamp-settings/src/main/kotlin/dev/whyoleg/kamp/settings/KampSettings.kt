package dev.whyoleg.kamp.settings

class KampSettings internal constructor() {
    internal var root: Root? = null
    internal var versionsClsName: String? = null

    fun modules(clsName: String = "KampModules", block: ModuleScope.() -> Unit) {
        root = root(clsName, block)
    }

    fun versions(clsName: String = "KampVersions") {
        versionsClsName = clsName
    }
}
