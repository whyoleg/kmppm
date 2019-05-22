import dev.whyoleg.kamp.base.DependenciesConfigurationType.implementation
import dev.whyoleg.kamp.base.Dependency
import dev.whyoleg.kamp.base.MavenArtifact
import dev.whyoleg.kamp.base.SourceType.main
import dev.whyoleg.kamp.base.SourceType.test
import dev.whyoleg.kamp.base.Target.Companion.android
import dev.whyoleg.kamp.base.Target.Companion.common
import dev.whyoleg.kamp.base.Target.Companion.js
import dev.whyoleg.kamp.base.Target.Companion.jvm
import dev.whyoleg.kamp.base.Target.Companion.linuxX64
import dev.whyoleg.kamp.base.plus
import dev.whyoleg.kamp.common
import dev.whyoleg.kamp.kamp
import dev.whyoleg.kamp.sourceSets
import dev.whyoleg.kamp.targets

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
}

val linux = linuxX64.copy(name = "linux")

kamp {
    targets(js, jvm, linux)

    val kotlind = Dependency("kotlin") {
        val cmn = MavenArtifact("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.31")
        common use cmn.copy(postfix = "common")
        jvm use cmn.copy(postfix = "jdk8")
        js use cmn.copy(postfix = "js")
        ignore(linux)
    }

    val testd = Dependency("test") {
        val cmn = MavenArtifact("org.jetbrains.kotlin", "kotlin-test", "1.3.31")
        common use cmn.copy(postfix = "common")
        jvm use cmn
        js use cmn.copy(postfix = "js")
        ignore(linux)
    }

    common {
        main {
            implementation(kotlind)
        }
        test {
            implementation {
                +testd
                +Dependency(common, MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31"))
            }
        }
    }

    sourceSets {
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
        (jvm + android) {

        }
        (jvm + js) {

        }
    }
}