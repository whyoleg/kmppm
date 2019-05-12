package dev.whyoleg.kmppm

//@file:Suppress("FunctionName", "unused")
//
//import org.gradle.api.*
//import org.gradle.kotlin.dsl.*
//import org.jetbrains.kotlin.gradle.dsl.*
//import org.jetbrains.kotlin.gradle.plugin.*
//import org.jetbrains.kotlin.gradle.plugin.mpp.*
//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//
//private class JVMMagicExtension(
//    private val implementations: Dependencies.() -> List<JVMDependencyHelper>,
//    private val apis: Dependencies.() -> List<JVMDependencyHelper>,
//    private val moduleImplementations: Module.() -> List<String>,
//    private val moduleApis: Module.() -> List<String>,
//    private val libDirs: List<String>
//) {
//
//    private inline fun <R> List<R>.each(block: R.() -> Unit) = forEach { it.run(block) }
//
//    fun Project.doMagic() {
//        dependencies {
//            implementations(Dependencies).each { implement() }
//            moduleImplementations(Module).forEach { add("implementation", project(it)) }
//
//            apis(Dependencies).each { api() }
//            moduleApis(Module).forEach { add("api", project(it)) }
//
//            (libDirs + "src/main/libs").forEach { add("implementation", fileTree(it)) }
//        }
//        tasks.withType<KotlinCompile> {
//            sourceCompatibility = Version.jdk
//            targetCompatibility = Version.jdk
//            kotlinOptions.jvmTarget = Version.jdk
//            kotlinOptions.configure()
//        }
//    }
//
//    private fun <T : KotlinCompilation<*>> KotlinOnlyTarget<T>.configure(configure: KotlinSourceSet.() -> Unit) =
//        compilations.getByName("main").defaultSourceSet(configure)
//
//    private fun KotlinMultiplatformExtension.jvmMain(block: KotlinSourceSet.() -> Unit) = jvm { configure(block) }
//    private fun KotlinMultiplatformExtension.metaMain(block: KotlinSourceSet.() -> Unit) = metadata { configure(block) }
//
//}
//
//fun Project.JVMMagic(
//    dockerApp: Boolean = false,
//    implementations: Dependencies.() -> List<JVMDependencyHelper> = { emptyList() },
//    apis: Dependencies.() -> List<JVMDependencyHelper> = { emptyList() },
//    moduleImplementations: Module.() -> List<String> = { emptyList() },
//    moduleApis: Module.() -> List<String> = { emptyList() },
//    libDirs: List<String> = emptyList()
//): Unit = JVMMagicExtension(implementations, apis, moduleImplementations, moduleApis, libDirs).run {
//    doMagic()
//    if (dockerApp) apply<DockerApp>()
//}
