import dev.whyoleg.kamp.dependency.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

fun KotlinTarget.sourceSet(compilation: String): KotlinSourceSet = when (this) {
    is KotlinAndroidTarget -> project.kotlin.sourceSets.getByName("android${compilation.capitalize()}")
    else                   -> compilations.getByName(compilation).defaultSourceSet
}

fun KotlinTarget.kampSourceSet(compilation: String): KampSourceSet = KampSourceSet(sourceSet(compilation), KampPlatform(platformType))

val KotlinTarget.sourceSetMain: KotlinSourceSet get() = sourceSet(KotlinCompilation.MAIN_COMPILATION_NAME)
val KotlinTarget.sourceSetTest: KotlinSourceSet get() = sourceSet(KotlinCompilation.TEST_COMPILATION_NAME)

val KotlinTarget.kampSourceSetMain: KampSourceSet get() = kampSourceSet(KotlinCompilation.MAIN_COMPILATION_NAME)
val KotlinTarget.kampSourceSetTest: KampSourceSet get() = kampSourceSet(KotlinCompilation.TEST_COMPILATION_NAME)
