import dev.whyoleg.kamp.options.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

fun KotlinTarget.commonOptions(compilation: String): KotlinCommonOptions = compilations.getByName(compilation).kotlinOptions
fun KotlinTarget.commonOptionsMain(): KotlinCommonOptions = commonOptions("main")
fun KotlinTarget.commonOptionsTest(): KotlinCommonOptions = commonOptions("test")

fun KotlinTarget.commonOptions(compilation: String, block: KotlinCommonOptions.() -> Unit) {
    compilations.getByName(compilation).kotlinOptions(block)
}

fun KotlinTarget.commonOptionsMain(block: KotlinCommonOptions.() -> Unit) {
    commonOptions("main", block)
}

fun KotlinTarget.commonOptionsTest(block: KotlinCommonOptions.() -> Unit) {
    commonOptions("test", block)
}

fun <T : KotlinCommonOptions> KotlinOnlyTarget<out KotlinCompilation<T>>.options(compilation: String): T =
    compilations.getByName(compilation).kotlinOptions

fun <T : KotlinCommonOptions> KotlinOnlyTarget<out KotlinCompilation<T>>.optionsMain(): T =
    compilations.getByName("main").kotlinOptions

fun <T : KotlinCommonOptions> KotlinOnlyTarget<out KotlinCompilation<T>>.optionsTest(): T =
    compilations.getByName("test").kotlinOptions


fun <T : KotlinCommonOptions> KotlinOnlyTarget<out KotlinCompilation<T>>.options(compilation: String, block: T.() -> Unit) {
    compilations.getByName(compilation).kotlinOptions(block)
}

fun <T : KotlinCommonOptions> KotlinOnlyTarget<out KotlinCompilation<T>>.optionsMain(block: T.() -> Unit) {
    compilations.getByName("main").kotlinOptions(block)
}

fun <T : KotlinCommonOptions> KotlinOnlyTarget<out KotlinCompilation<T>>.optionsTest(block: T.() -> Unit) {
    compilations.getByName("test").kotlinOptions(block)
}


fun KotlinAndroidTarget.options(compilation: String): KotlinJvmOptions = compilations.getByName(compilation).kotlinOptions
fun KotlinAndroidTarget.optionsMain(): KotlinJvmOptions = options("main")
fun KotlinAndroidTarget.optionsTest(): KotlinJvmOptions = options("test")

fun KotlinAndroidTarget.options(compilation: String, block: KotlinJvmOptions.() -> Unit) {
    compilations.getByName(compilation).kotlinOptions(block)
}

fun KotlinAndroidTarget.optionsMain(block: KotlinJvmOptions.() -> Unit) {
    options("main", block)
}

fun KotlinAndroidTarget.optionsTest(block: KotlinJvmOptions.() -> Unit) {
    options("test", block)
}

fun KotlinCommonToolOptions.progressive() {
    freeCompilerArgs += "-progressive"
}

fun KotlinCommonToolOptions.enableLanguageFeatures(features: Iterable<LanguageFeature>) {
    freeCompilerArgs += features.map { "-XXLanguage:+${it.value}" }
}

fun KotlinCommonToolOptions.useExperimentalAnnotations(annotations: Iterable<ExperimentalAnnotation>) {
    freeCompilerArgs += annotations.map { "-Xuse-experimental=${it.value}" }
}

fun KotlinCommonToolOptions.compilerArguments(arguments: Iterable<CompilerArgument>) {
    freeCompilerArgs += arguments.map { "-X${it.value}" }
}


fun LanguageSettingsBuilder.enableLanguageFeatures(features: Iterable<LanguageFeature>) {
    features.forEach { enableLanguageFeature(it.value) }
}

fun LanguageSettingsBuilder.useExperimentalAnnotations(annotations: Iterable<ExperimentalAnnotation>) {
    annotations.forEach { useExperimentalAnnotation(it.value) }
}

