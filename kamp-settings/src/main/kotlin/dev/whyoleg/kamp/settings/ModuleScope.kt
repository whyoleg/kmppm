package dev.whyoleg.kamp.settings

internal fun root(
    clsName: String,
    block: ModuleScope.() -> Unit
): Root = Root(clsName, ModuleScope().apply(block).inner)

class ModuleScope {
    internal val inner = mutableListOf<Dir>()

    fun module(
        name: String,
        clsName: String = name.dashToCamel(),
        ignore: Boolean = false,
        block: ModuleScope.() -> Unit = {}
    ): Module = Module(name, clsName, ignore, ModuleScope().apply(block).inner).also(inner::add)

    fun folder(
        path: String,
        clsName: String = path.substringAfterLast('\\'),
        ignore: Boolean = false,
        block: ModuleScope.() -> Unit
    ): Folder = Folder(path, clsName, ignore, ModuleScope().apply(block).inner).also(inner::add)

    operator fun Module.invoke(block: ModuleScope.() -> Unit): Module = module(name, clsName, ignore, block)
    operator fun Folder.invoke(block: ModuleScope.() -> Unit): Folder = folder(path, clsName, ignore, block)

    operator fun PrefixedModuleFactory.invoke(
        name: String,
        ignore: Boolean = false,
        block: ModuleScope.() -> Unit = {}
    ): Module = module("$prefix-$name", name.dashToCamel(), ignore, block = block)

    operator fun PrefixedFolderFactory.invoke(
        path: String,
        ignore: Boolean = false,
        block: ModuleScope.() -> Unit = {}
    ): Folder = folder("$prefix-$path", path.substringAfterLast('\\').dashToCamel(), ignore, block = block)
}

class PrefixedModuleFactory(val prefix: String)
class PrefixedFolderFactory(val prefix: String)
