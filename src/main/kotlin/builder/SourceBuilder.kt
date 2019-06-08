package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

@PublishedApi
internal data class Source(val targetSet: MultiTarget<*>, val configurations: List<SourceSetConfiguration>)

@KampDSL
class SourceBuilder : MainTargets {
    @PublishedApi
    internal val sources = mutableListOf<Source>()

    inline fun <reified T : Target> name(): String =
        T::class.simpleName.orEmpty().substringBefore("Target").decapitalize()

    inline operator fun <reified T : Target> T.invoke(crossinline build: SourceSetConfigurationBuilder<T>.() -> Unit) =
        invoke(SourceSetConfigurationBuilder(setOf(this)).apply(build))

    inline operator fun <reified T : Target> T.invoke(builder: SourceSetConfigurationBuilder<T>) {
        sources += Source(MultiTarget(this), builder.data())
    }

    inline operator fun <reified T : PlatformTarget> Set<T>.invoke(crossinline build: SourceSetConfigurationBuilder<T>.() -> Unit): Unit =
        named(name<T>()).invoke { build() }

    inline operator fun <reified T : PlatformTarget> Set<T>.invoke(builder: SourceSetConfigurationBuilder<T>): Unit =
        named(name<T>())(builder)

    operator fun <T : PlatformTarget> MultiTarget<T>.invoke(build: SourceSetConfigurationBuilder<T>.() -> Unit) =
        invoke(SourceSetConfigurationBuilder(targets).apply(build))

    operator fun <T : PlatformTarget> MultiTarget<T>.invoke(builder: SourceSetConfigurationBuilder<T>) {
        sources += Source(this, builder.data())
    }

}