package dev.whyoleg.kamp.builder

import org.gradle.api.*

class JarPackaging(val project: Project) {
    //mainclassname
    //name
    var name = project.path.replace(":", "-").drop(1)
    private val packageName = name.replace("-", ".")
    var className = "${project.group}.$packageName"//.AppKt"

//    val mainClassFile = project.file("src/jvmMain/kotlin/App.kt")
//                require(mainClassFile.exists()) { "Class \"$mainClassFile\" doesn't exists" }

    //

    //
    //            extensions.configure<JavaApplication>("application") { mainClassName = "$group.$packageName.AppKt" }
    //            tasks.withType<ShadowJar> {
    //                baseName = appName
    //                version = this@project.version.toString()
    //                classifier = null
    //                from(jvm.mainCompilation().output)
    //                configurations = listOf(jvm.mainCompilation().runtimeDependencyFiles) as MutableList<Configuration>
    //            }
}