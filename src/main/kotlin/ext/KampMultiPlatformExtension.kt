package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.source.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import dev.whyoleg.kamp.target.configuration.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import kotlin.reflect.*

@KampDSL
class KampMultiPlatformExtension(versions: BuiltInVersions) : KampExtension<KotlinMultiplatformExtension>(versions) {
    override val extPlugin: Plugin = builtIn.plugins.kotlinMpp
    override val extPluginClass: KClass<KotlinMultiplatformExtension> = KotlinMultiplatformExtension::class

    fun sources(builder: SourceBuilder.() -> Unit) {
        sources += SourceBuilder().apply(builder).sources
    }

    fun targets(builder: TargetConfigurationBuilder.() -> Unit) {
        targetConfigurations += TargetConfigurationBuilder().apply(builder).data()
    }

    private fun targets(): List<Target> = targetConfigurations.map(TargetConfiguration::target) + Target.common

    override fun configureTargets(ext: KotlinMultiplatformExtension) {
        Target.common.provider(ext)
        targetConfigurations.forEach { (target, options) ->
            configureTarget(target.provider(ext), options)
        }
    }

    override fun sourceTypeTargets(ext: KotlinMultiplatformExtension, sourceType: SourceSetType): Map<Target, KotlinSourceSet> =
        targets().associateWith { ext.sourceSets.maybeCreate(it.name + sourceType.name.capitalize()) }

    override fun createSourceSet(ext: KotlinMultiplatformExtension, multiTarget: MultiTarget<*>, sourceSetType: SourceSetType): KotlinSourceSet =
        ext.sourceSets.maybeCreate(multiTarget.name + sourceSetType.name.capitalize())

}
