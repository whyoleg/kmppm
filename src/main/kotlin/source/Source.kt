package dev.whyoleg.kamp.source

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.settings.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

@PublishedApi
internal data class Source(
    val multiTarget: MultiTarget<*>,
    val kotlinOption: KotlinOptionsBuilder<*>,
    val sourceSets: List<SourceSet>
)

@KampDSL
class SourceBuilder : MainTargets {
    @PublishedApi
    internal val sources = mutableListOf<Source>()

    inline fun <reified T : Target> name(): String = T::class.simpleName.orEmpty().substringBefore("Target").decapitalize()

    inline operator fun <reified T : PlatformTarget> Set<T>.invoke(noinline build: SourceSetBuilder<T>.() -> Unit): Unit = named(name<T>())(build)

    inline operator fun <reified T : Target> T.invoke(noinline build: SourceSetBuilder<T>.() -> Unit) = MultiTarget(this).internal(build)

    operator fun <T : PlatformTarget> MultiTarget<T>.invoke(build: SourceSetBuilder<T>.() -> Unit): Unit = internal(build)

    @PublishedApi
    internal fun <T : Target> MultiTarget<T>.internal(build: SourceSetBuilder<T>.() -> Unit) {
        val (options, sourceSets) = SourceSetBuilder<T>().apply(build).data()
        sources += Source(this, options, sourceSets)
    }

}