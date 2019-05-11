import org.gradle.api.*
import org.gradle.kotlin.dsl.apply

class DockerApp : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val imageName = path.replace(":", "-").drop(1)
        val packageName = imageName.replace("-", ".")
        val mainClassFile = file("src/main/kotlin/App.kt")
        require(mainClassFile.exists()) { "Class \"$mainClassFile\" doesn't exists" }

        apply(plugin = Plugins.jib)
        extensions.configure<JibExtension>("jib") {
            from { image = "adoptopenjdk/openjdk11:alpine-slim" }
            to {
                image = imageName
                tags = setOf(version.toString(), "latest")
            }
            container {
                useCurrentTimestamp = true
                ports = listOf("8080")
                mainClass = "$group.$packageName.AppKt"
                format = ImageFormat.OCI
                jvmFlags = listOf(
                    "-server",
                    "-Djava.awt.headless=true",
                    "-XX:+UnlockExperimentalVMOptions",
                    "-XX:+UseG1GC",
                    "-XX:MaxGCPauseMillis=100",
                    "-XX:+UseStringDeduplication",
                    "-Djava.library.path=\"/app/libs:\${PATH}\""
                )
            }
        }
    }
}