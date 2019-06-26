package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.source.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import dev.whyoleg.kamp.target.configuration.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
abstract class KampSinglePlatformExtension<KotlinExt : KotlinSingleTargetExtension, T : PlatformTarget, TO : TargetOptions>(
    private val target: T,
    private val options: TO
) : KampExtension<KotlinExt>() {

    fun source(builder: SourceSetBuilder<T>.() -> Unit) {
        sources += Source(MultiTarget(target), SourceSetBuilder<T>().apply(builder).data())
    }

    fun options(block: TO.() -> Unit) {
        options.apply(block)
    }

    override fun configureTargets(ext: KotlinExt) {
        targetConfigurations += TargetConfiguration(target, options)
        configureTarget(ext.target, options)
    }

    override fun sourceTypeTargets(ext: KotlinExt, sourceType: SourceSetType): Map<Target, KotlinSourceSet> =
        mapOf(target to ext.sourceSets.maybeCreate(sourceType.name))
}