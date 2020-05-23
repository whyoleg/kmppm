import com.github.benmanes.gradle.versions.updates.*
import com.github.benmanes.gradle.versions.reporter.result.*
import groovy.lang.*
import org.gradle.api.*

fun Project.dependencyUpdates(block: DependencyUpdatesTask.() -> Unit) {
    tasks.withType(DependencyUpdatesTask::class.java) {
        it.block()
        it.outputFormatter = object : Closure<Unit?>(this) {
            fun doCall(v: Result) {
                v.outdated.dependencies.forEach {
                    println(it.name)
                    println(it.group)
                    println(it.version)
                    println(it.available.milestone)
                    println(it.available.integration)
                    println(it.available.release)
                    println()
                }
            }
        }
    }
}
