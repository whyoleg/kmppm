package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.source.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
abstract class KampSinglePlatformExtension<KotlinExt : KotlinProjectExtension, T : PlatformTarget>(private val target: T) : KampExtension<KotlinExt>() {

    fun sourceSet(builder: SourceSetBuilder<T>.() -> Unit) {
        val (options, sourceSets) = SourceSetBuilder<T>().apply(builder).data()
        sources += Source(MultiTarget(target), options, sourceSets)
    }

    override fun configureTargets(ext: KotlinExt) {
        targets += target
    }

    override fun sourceTypeTargets(ext: KotlinExt, sourceType: SourceSetType): Map<Target, KotlinSourceSet> =
        mapOf(target to ext.sourceSets.maybeCreate(sourceType.name))
}