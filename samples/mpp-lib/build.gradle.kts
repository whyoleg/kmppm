import dev.whyoleg.kamp.base.*
import dev.whyoleg.kamp.base.MavenArtifact
import dev.whyoleg.kamp.base.Target.Companion.common
import dev.whyoleg.kamp.base.Target.Companion.js
import dev.whyoleg.kamp.base.Target.Companion.jvm
import dev.whyoleg.kamp.base.Target.Companion.jvm6
import dev.whyoleg.kamp.base.Target.Companion.jvmBased
import dev.whyoleg.kamp.base.Target.Companion.linuxX64
import dev.whyoleg.kamp.dsl.DependencySetType.api
import dev.whyoleg.kamp.dsl.DependencySetType.implementation
import dev.whyoleg.kamp.dsl.SourceType.main
import dev.whyoleg.kamp.dsl.SourceType.test
import dev.whyoleg.kamp.kamp

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("dev.whyoleg.kamp:kamp:0.1.0")
    }
}
repositories {
    mavenCentral()
    mavenLocal()
}

val linux = linuxX64.copy(name = "linux")

kamp {
    targets += js + jvm// + linux

    val kotlind = Dependency("kotlin") {
        val cmn = MavenArtifact("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.31")
        common use cmn.copy(postfix = "common")
        jvmBased use cmn.copy(postfix = "")
        jvm use cmn.copy(postfix = "jdk8")
        js use cmn.copy(postfix = "js")
        linux use cmn.copy(postfix = "linux")
    }

    val testd = Dependency("test") {
        val cmn = MavenArtifact("org.jetbrains.kotlin", "kotlin-test", "1.3.31")
        common use cmn.copy(postfix = "common")
        jvmBased use cmn
        js use cmn.copy(postfix = "js")
        ignore(linux)
    }

    sourceSets {
        common {
            main {
                implementation(kotlind)
            }
            test {
                implementation {
                    +testd
                    +Dependency(
                        common,
                        MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31")
                    )
                }
            }
        }

        jvm {
            test {
                implementation(
                    Dependency(
                        jvm,
                        MavenArtifact("org.jetbrains.kotlin", "kotlin-test-junit", "1.3.31")
                    )
                )
            }
        }
        (jvm) {
            main {
                implementation(kotlind)
            }
        }
        (jvm + js) {
            main {
                implementation(kotlind)
            }
        }
        val s = setOf(jvm, jvm6).sourceSet("jvm6")
        s {
            main {
                implementation(kotlind.copy { jvmBased use it[jvm6]!! })
            }
            test {
                api {
                    +kotlind
                }
            }
        }
    }
}