package dev.whyoleg.kmppm

//@file:Suppress("FunctionName", "unused", "UnstableApiUsage", "UNCHECKED_CAST", "DEPRECATION")
//
//import org.gradle.api.artifacts.*
//import org.gradle.api.plugins.*
//import org.gradle.kotlin.dsl.*
//import org.jetbrains.kotlin.gradle.dsl.*
//import org.jetbrains.kotlin.gradle.plugin.*
//import org.jetbrains.kotlin.gradle.plugin.mpp.*
//
//private class MPPMagicExtension(
//    private val mppImplementations: Dependencies.MPP.() -> List<MPPDependency>,
//    private val mppApis: Dependencies.MPP.() -> List<MPPDependency>,
//    private val jvmImplementations: Dependencies.JVM.() -> List<JVMDependency>,
//    private val jvmApis: Dependencies.JVM.() -> List<JVMDependency>,
//    private val moduleImplementations: Module.() -> List<String>,
//    private val moduleApis: Module.() -> List<String>,
//    private val libDirs: List<String>,
//    private val needJar: Boolean
//) {
//
//    private inline fun <R> List<R>.each(block: R.() -> Unit) = forEach { it.run(block) }
//
//    fun KotlinMultiplatformExtension.doMagic() {
//        metaMain {
//            dependencies {
//                mppImplementations(Dependencies.MPP).each { implementMeta() }
//                moduleImplementations(Module).forEach { implementation(project(it)) }
//
//                mppApis(Dependencies.MPP).each { apiMeta() }
//                moduleApis(Module).forEach { api(project(it)) }
//            }
//        }
//
//        val jvm = jvmMain {
//            dependencies {
//                mppImplementations(Dependencies.MPP).each { implementJvm() }
//                jvmImplementations(Dependencies.JVM).each { implement() }
//
//                mppApis(Dependencies.MPP).each { apiJvm() }
//                jvmApis(Dependencies.JVM).each { api() }
//                val project = (this as DefaultKotlinDependencyHandler).project
//                (libDirs + "src/jvmMain/libs").forEach { implementation(project.fileTree(it)) }
//            }
//        }
//
//        if (needJar) jvm.project.run project@{
//            val appName = path.replace(":", "-").drop(1)
//            val packageName = appName.replace("-", ".")
//            val mainClassFile = file("src/jvmMain/kotlin/App.kt")
//            require(mainClassFile.exists()) { "Class \"$mainClassFile\" doesn't exists" }
//
//            apply(plugin = "com.github.johnrengelman.shadow")
//            apply(plugin = "org.gradle.application")
//
//            extensions.configure<JavaApplication>("application") { mainClassName = "$group.$packageName.AppKt" }
//            tasks.withType<ShadowJar> {
//                baseName = appName
//                version = this@project.version.toString()
//                classifier = null
//                from(jvm.mainCompilation().output)
//                configurations = listOf(jvm.mainCompilation().runtimeDependencyFiles) as MutableList<Configuration>
//            }
//        }
//
//        sourceSets.all {
//            languageSettings.progressiveMode = true
//            languageSettings.apiVersion = Version.kotlinApi
//            languageSettings.languageVersion = Version.kotlinLanguage
//        }
//
//        targets.all {
//            compilations.all {
//                kotlinOptions.configure()
//                if (targetName == "JVM") (kotlinOptions as KotlinJvmOptions).jvmTarget = Version.jdk
//            }
//        }
//    }
//
//}
//
//fun KotlinOnlyTarget<out KotlinCompilation<*>>.configure(configure: KotlinSourceSet.() -> Unit) = defaultSourceSet.configure()
//
//val KotlinOnlyTarget<out KotlinCompilation<*>>.defaultSourceSet: KotlinSourceSet
//    get() = mainCompilation().defaultSourceSet
//
//fun <T : KotlinCompilation<*>> KotlinOnlyTarget<T>.mainCompilation(): T = compilations.getByName("main")
//
//fun KotlinMultiplatformExtension.jvmMain(name: String = "JVM", block: KotlinSourceSet.() -> Unit) =
//    jvm(name) { configure(block) }
//
//fun KotlinMultiplatformExtension.metaMain(block: KotlinSourceSet.() -> Unit) =
//    metadata { configure(block) }
//
//fun Magic(
//    mppImplementations: Dependencies.MPP.() -> List<MPPDependency> = { emptyList() },
//    mppApis: Dependencies.MPP.() -> List<MPPDependency> = { emptyList() },
//    jvmImplementations: Dependencies.JVM.() -> List<JVMDependency> = { emptyList() },
//    jvmApis: Dependencies.JVM.() -> List<JVMDependency> = { emptyList() },
//    moduleImplementations: Module.() -> List<String> = { emptyList() },
//    moduleApis: Module.() -> List<String> = { emptyList() },
//    libDirs: List<String> = emptyList(),
//    needJar: Boolean = false
//): KotlinMultiplatformExtension.() -> Unit = {
//    MPPMagicExtension(
//        mppImplementations,
//        mppApis,
//        jvmImplementations,
//        jvmApis,
//        moduleImplementations,
//        moduleApis,
//        libDirs,
//        needJar
//    ).run { doMagic() }
//}
//
//
//internal fun KotlinCommonOptions.configure() {
//    apiVersion = Version.kotlinApi
//    languageVersion = Version.kotlinLanguage
//    freeCompilerArgs = Compiler.all()
//}
