package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
abstract class KampSinglePlatformExtension<KotlinExt : KotlinProjectExtension, T : PlatformTarget>(private val target: T) : KampExtension<KotlinExt>() {

    fun sourceSet(builder: SourceSetConfigurationBuilder<T>.() -> Unit) {
        sources += Source(
            MultiTarget(target.name, target::class, setOf(target)),
            SourceSetConfigurationBuilder<T>().apply(builder).data()
        )
    }

    override fun configureTargets(ext: KotlinExt) {
        targets += target
    }

    override fun sourceTypeTargets(ext: KotlinExt, sourceType: SourceType): Map<Target, KotlinSourceSet> =
        mapOf(target to ext.sourceSets.maybeCreate(sourceType.name))
}