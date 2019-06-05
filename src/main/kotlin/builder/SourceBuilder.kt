package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.base.RealTarget
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.base.TargetSet
import dev.whyoleg.kamp.base.sourceSet

@PublishedApi
internal data class Source(val targetSet: TargetSet<*>, val configurations: List<SourceSetConfiguration>)

@KampDSL
class SourceBuilder {
    @PublishedApi
    internal val sources = mutableListOf<Source>()

    inline fun <reified T : Target> name(): String =
        T::class.simpleName.orEmpty().substringBefore("Target").decapitalize()

    inline operator fun <reified T : Target> T.invoke(crossinline build: SourceSetConfigurationBuilder<T>.() -> Unit) =
        invoke(SourceSetConfigurationBuilder(setOf(this)).apply(build))

    inline operator fun <reified T : Target> T.invoke(builder: SourceSetConfigurationBuilder<T>) {
        sources += Source(TargetSet(this), builder.data())
    }

    inline operator fun <reified T : RealTarget> Set<T>.invoke(crossinline build: SourceSetConfigurationBuilder<T>.() -> Unit): Unit =
        sourceSet(name<T>()).invoke { build() }

    inline operator fun <reified T : RealTarget> Set<T>.invoke(builder: SourceSetConfigurationBuilder<T>): Unit =
        sourceSet(name<T>())(builder)

    operator fun <T : RealTarget> TargetSet<T>.invoke(build: SourceSetConfigurationBuilder<T>.() -> Unit) =
        invoke(SourceSetConfigurationBuilder(targets).apply(build))

    operator fun <T : RealTarget> TargetSet<T>.invoke(builder: SourceSetConfigurationBuilder<T>) {
        sources += Source(this, builder.data())
    }

}