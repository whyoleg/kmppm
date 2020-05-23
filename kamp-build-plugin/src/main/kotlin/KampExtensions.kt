import dev.whyoleg.kamp.build.*
import org.gradle.api.*

//TODO remove when publish
inline fun Project.kamp(crossinline block: KampBuildExtension.() -> Unit = {}) {
    apply { it.plugin(KampBuildPlugin::class.java) }
    extensions.configure(KampBuildExtension::class.java) { it.block() }
}
