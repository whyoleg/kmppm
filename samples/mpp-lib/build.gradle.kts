import dev.whyoleg.kmppm.base.DependenciesConfigurationType.implementation
import dev.whyoleg.kmppm.base.Dependency
import dev.whyoleg.kmppm.base.MavenArtifact
import dev.whyoleg.kmppm.base.SourceType.main
import dev.whyoleg.kmppm.base.SourceType.test
import dev.whyoleg.kmppm.base.Target.Companion.android
import dev.whyoleg.kmppm.base.Target.Companion.common
import dev.whyoleg.kmppm.base.Target.Companion.js
import dev.whyoleg.kmppm.base.Target.Companion.jvm
import dev.whyoleg.kmppm.base.Target.Companion.linuxX64
import dev.whyoleg.kmppm.base.plus
import dev.whyoleg.kmppm.common
import dev.whyoleg.kmppm.mppm
import dev.whyoleg.kmppm.sourceSets
import dev.whyoleg.kmppm.targets

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("dev.whyoleg.kmppm:kmppm:0.1.0")
    }
}

repositories {
    mavenCentral()
}

val linux = linuxX64.copy(name = "linux")

mppm {
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