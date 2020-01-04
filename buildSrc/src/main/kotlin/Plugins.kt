import dev.whyoleg.kamp.modules.*
import dev.whyoleg.kamp.dependency.*

object Plugins {
    val pluginModule = listOf(KotlinPlugins.Stable.kotlinJvm, GradleBuiltInPlugins.javaPlugin, GradleBuiltInPlugins.mavenPublish)
}
