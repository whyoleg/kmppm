package dev.whyoleg.kamp.base

import dev.whyoleg.kamp.MagicDSL

data class Sources<T : Target>(
    val targetSet: TargetSet<T>,
    val sourceConfigurations: List<SourceConfiguration>
)

@MagicDSL
open class SourcesBuilder {
    private val sources = mutableListOf<Sources<*>>()

    inline fun <reified T : Target> name(): String =
        T::class.simpleName.orEmpty().substringBefore("Target").decapitalize()

    inline operator fun <reified T : Target> T.invoke(crossinline build: SourceConfigurationBuilder<T>.() -> Unit) =
        TargetSet(this).invoke { build() }

    inline operator fun <reified T : Target> T.invoke(builder: SourceConfigurationBuilder<T>) =
        TargetSet(this)(builder)

    inline operator fun <reified T : Target> Set<T>.invoke(crossinline build: SourceConfigurationBuilder<T>.() -> Unit): Unit =
        sourceSet(name<T>()).invoke { build() }

    inline operator fun <reified T : Target> Set<T>.invoke(builder: SourceConfigurationBuilder<T>): Unit =
        sourceSet(name<T>())(builder)

    operator fun <T : Target> TargetSet<T>.invoke(build: SourceConfigurationBuilder<T>.() -> Unit) =
        invoke(SourceConfigurationBuilder(targets).apply(build))

    operator fun <T : Target> TargetSet<T>.invoke(builder: SourceConfigurationBuilder<T>) {
        sources += Sources(this, builder.data())
    }

    fun data(): List<Sources<*>> = sources
}

//fun Sources.targets(): Set<Target> {
//    val targets = mutableSetOf<Target>()
//    targets += targetSet.targets
//    targets += dependentSources.flatMap(Sources::targets)
//    return targets
//}