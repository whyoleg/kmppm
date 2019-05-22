package dev.whyoleg.kamp.base

import dev.whyoleg.kamp.MagicDSL

data class Sources<T : Target>(
    val sourceSet: SourceSet<T>,
    val sourceConfigurations: List<SourceConfiguration>
//    val dependentSources: List<Sources>,
)

@MagicDSL
open class SourcesBuilder {
    private val sources = mutableListOf<Sources<*>>()

    inline fun <reified T : Target> name(): String =
        T::class.simpleName.orEmpty().substringBefore("Target").decapitalize()

    operator fun <T : Target> T.invoke(build: TypedSourceConfigurationBuilder<T>.() -> Unit) =
        SourceSet(this)(build)

    operator fun <T : Target> T.invoke(builder: TypedSourceConfigurationBuilder<T>) =
        SourceSet(this)(builder)

    inline operator fun <reified T : Target> Set<T>.invoke(crossinline build: TypedSourceConfigurationBuilder<T>.() -> Unit): Unit =
        sourceSet(name()).invoke { build() }

    inline operator fun <reified T : Target> Set<T>.invoke(builder: TypedSourceConfigurationBuilder<T>): Unit =
        sourceSet(name())(builder)

    operator fun <T : Target> SourceSet<T>.invoke(build: TypedSourceConfigurationBuilder<T>.() -> Unit) =
        invoke(TypedSourceConfigurationBuilder(targets).apply(build))

    operator fun <T : Target> SourceSet<T>.invoke(builder: TypedSourceConfigurationBuilder<T>) {
        sources += Sources(this, builder.data())
    }

    fun sources(): List<Sources<*>> = sources
}

//fun Sources.targets(): Set<Target> {
//    val targets = mutableSetOf<Target>()
//    targets += sourceSet.targets
//    targets += dependentSources.flatMap(Sources::targets)
//    return targets
//}