pluginManagement {
    repositories {
        maven("https://dl.bintray.com/whyoleg/kamp")
        mavenCentral()
        gradlePluginPortal()
    }

    fun v(name: String): String = extra["kamp.version.$name"] as String
    plugins {
        kotlin("jvm") version KotlinVersion.CURRENT.toString()
        id("com.github.ben-manes.versions") version v("updates")
    }
}

buildscript {
    repositories {
        maven("https://dl.bintray.com/whyoleg/kamp")
        mavenCentral()
        gradlePluginPortal()
    }

    fun v(name: String): String = extra["kamp.version.$name"] as String
    dependencies {
        classpath("dev.whyoleg.kamp:kamp-settings:${v("kamp")}")
    }
}

kamp {
    versions()
    modules {
        val kamp = "kamp".prefixedModule
        val feature = "kamp-feature".prefixedModule

        kamp("settings")
        kamp("publication")
        kamp("dependencies")
        kamp("build")

        folder("kamp-features", "Features") {
            feature("kotlin")
            feature("kotlinx")
            feature("exposed")
            feature("ktor")
            feature("logging")
            feature("shadow")
            feature("updates")
            feature("jib")
            feature("android")
            feature("gradle")
            feature("kamp")
        }
    }
}
