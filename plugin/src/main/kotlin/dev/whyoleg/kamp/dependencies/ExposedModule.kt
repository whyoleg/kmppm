package dev.whyoleg.kamp.dependencies

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*

inline class ExposedVersion(val value: String) {
    companion object {
        val stable = KtorVersion("0.20.2")
    }
}

class ExposedDependencies(version: ExposedVersion) :
    GroupWithVersionPlatforms by group("org.jetbrains.exposed", provider).version(version.value).jvm {

    companion object {
        val provider = RepositoryProviders.maven("https://dl.bintray.com/kotlin/exposed")
    }

    val core = artifact("exposed-core")
    val dao = artifact("exposed-dao")
    val jdbc = artifact("exposed-jdbc")

    val time by lazy(::Time)

    inner class Time {
        val java = artifact("exposed-java-time")
        val joda = artifact("exposed-jodatime")
    }

    val spring by lazy(::Spring)

    inner class Spring {
        val starter = artifact("exposed-spring-boot-starter")
        val transaction = artifact("spring-transaction")
    }
}
