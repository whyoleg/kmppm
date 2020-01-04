import com.github.jengelman.gradle.plugins.shadow.tasks.*
import org.gradle.api.*
import org.gradle.api.plugins.*

@Suppress("UnstableApiUsage")
fun Project.shadow(className: String? = null, jarName: String? = null, minimize: Boolean = false, useVersion: Boolean = true) {
    val plainName = path.replace(":", "-").drop(1)
    val packageName = plainName.replace("-", ".")
    extensions.configure<JavaApplication>("application") {
        it.mainClassName = className ?: "$group.$packageName.AppKt"
    }
    tasks.withType(ShadowJar::class.java) {
        it.baseName = jarName ?: plainName
        if (useVersion) it.version = version.toString()
        it.classifier = null
        if (minimize) it.minimize()
    }
}
