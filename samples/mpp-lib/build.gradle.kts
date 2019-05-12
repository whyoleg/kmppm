import dev.whyoleg.kmppm.*
import dev.whyoleg.kmppm.Target.Companion.JS
import dev.whyoleg.kmppm.Target.Companion.JVM
import dev.whyoleg.kmppm.Target.Companion.LINUX_X64
import dev.whyoleg.kmppm.Target.Companion.META

plugins {
    id("kotlin-multiplatform")
}

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("dev.whyoleg.kmppm:kmppm:1.0.0")
    }
}

repositories {
    mavenCentral()
}

kotlin {
    val linux = LINUX_X64.copy(name = "linux")
    val kotlind = Dependency("kotlin") {
        val common = Maven("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.31")
        META with common.copy(postfix = "common")
        JVM with common.copy(postfix = "jdk8")
        JS with common.copy(postfix = "js")
        linux with Ignored
    }

    val testd = Dependency("test") {
        val common = Maven("org.jetbrains.kotlin", "kotlin-test", "1.3.31")
        META with common.copy(postfix = "common")
        JVM with common
        JS with common.copy(postfix = "js")
        linux with Ignored
    }

    sources(JVM + JS + linux) {
        main {
            implementation(kotlind)
        }
        test {
            implementation {
                +testd
                +Maven("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31").on(META)
            }
        }
        JVM.sources {
            test {
                implementation(Maven("org.jetbrains.kotlin", "kotlin-test-junit", "1.3.31").on(JVM))
            }
        }
    }
}