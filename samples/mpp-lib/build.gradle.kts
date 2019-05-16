import dev.whyoleg.kmppm.base.DependenciesConfigurationType.implementation
import dev.whyoleg.kmppm.base.Dependency
import dev.whyoleg.kmppm.base.IgnoredArtifact
import dev.whyoleg.kmppm.base.MavenArtifact
import dev.whyoleg.kmppm.base.SourceType.main
import dev.whyoleg.kmppm.base.SourceType.test
import dev.whyoleg.kmppm.base.Target.Companion.JS
import dev.whyoleg.kmppm.base.Target.Companion.JVM
import dev.whyoleg.kmppm.base.Target.Companion.LINUX_X64
import dev.whyoleg.kmppm.base.Target.Companion.META
import dev.whyoleg.kmppm.base.plus
import dev.whyoleg.kmppm.sources

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
        val common = MavenArtifact("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.31")
        META use common.copy(postfix = "common")
        JVM use common.copy(postfix = "jdk8")
        JS use common.copy(postfix = "js")
        linux use IgnoredArtifact
    }

    val testd = Dependency("test") {
        val common = MavenArtifact("org.jetbrains.kotlin", "kotlin-test", "1.3.31")
        META use common.copy(postfix = "common")
        JVM use common
        JS use common.copy(postfix = "js")
        linux use IgnoredArtifact
    }

    sources(JVM + JS + linux) {
        dependencies {
            main {
                implementation(kotlind)
            }
            test {
                implementation {
                    +testd
                    +Dependency(META, MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31"))
                }
            }
        }
        JVM.sources {
            dependencies {
                test {
                    implementation(
                        Dependency(
                            JVM,
                            MavenArtifact("org.jetbrains.kotlin", "kotlin-test-junit", "1.3.31")
                        )
                    )
                }
            }
        }
    }
}