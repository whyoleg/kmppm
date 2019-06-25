package dev.whyoleg.kamp.module

import dev.whyoleg.kamp.dependency.*
import kotlin.properties.*
import kotlin.reflect.*

interface Module : UnTypedDependency {
    val name: String
    val path: String?
    val ignored: Boolean
    val childModules: Set<Module>

    operator fun component1(): String = name
    operator fun component2(): String? = path

    fun named(childName: String, path: String? = null): Module
    fun named(path: String? = null): ModuleDelegate = ModuleDelegate(path)

    fun all(): Set<Module> {
        val filtered = childModules.filterNot(Module::ignored)
        return filtered.toSet() + filtered.flatMap(Module::all)
    }

    val i: Module
        get() {
            if (this is ModuleImpl) ignored = true
            return this
        }
}

abstract class RootModule : Module {
    final override val name: String = ""
    final override val path: String? = null
    final override val ignored: Boolean = false
    final override val childModules: MutableSet<Module> = mutableSetOf()
    final override fun named(childName: String, path: String?): Module = ModuleImpl("$name:$childName", path).also { childModules += it }

    /**
     * Hack to initialize module structure based on delegation
     */
    internal fun lazy(): Unit = lazy(this)

    private fun lazy(any: Any): Unit = any::class.nestedClasses.forEach { it.objectInstance?.let(this::lazy) }
}

internal data class ModuleImpl(override val name: String, override val path: String?, override var ignored: Boolean = false) : Module {
    override val childModules: MutableSet<ModuleImpl> = mutableSetOf()
    override fun named(childName: String, path: String?): Module = ModuleImpl("$name:$childName", path).also { childModules += it }
}

/**
 * Hack classes for delegation
 */
inline class ModuleDelegate(private val path: String?) {
    operator fun provideDelegate(thisRef: Module, property: KProperty<*>): InternalModule = InternalModule(thisRef.named(property.name, path))
}

inline class InternalModule(private val module: Module) : ReadOnlyProperty<Module, Module> {
    override fun getValue(thisRef: Module, property: KProperty<*>): Module = module
}