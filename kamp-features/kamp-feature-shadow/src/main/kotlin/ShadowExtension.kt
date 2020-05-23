import com.github.jengelman.gradle.plugins.shadow.tasks.*
import org.gradle.api.*
import org.gradle.api.plugins.*

/**
 * Configure gradle application and shadow plugin
 *
 * @param jarName name of produced jar default to [Project] name
 *
 * @param packageName name of package relative to [Project] group default to [Project] name with dashes replaced by dots
 * @param className name of main class to launch jar
 *
 * @param classFullName full path to main class; will override [packageName] and [className]
 * @param block additional configuration of [ShadowJar] task
 */
inline fun Project.shadow(
    jarName: String = name,
    packageName: String = name.replace("-", "."),
    className: String = "AppKt",
    classFullName: String? = null,
    crossinline block: ShadowJar.() -> Unit = {}
) {
    extensions.configure<JavaApplication>("application") {
        it.mainClassName = classFullName ?: "$group.$packageName.$className"
    }
    tasks.withType(ShadowJar::class.java) {
        it.archiveBaseName.set(jarName)
        it.block()
    }
}
