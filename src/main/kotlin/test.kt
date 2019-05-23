package dev.whyoleg.kamp

import dev.whyoleg.kamp.base.*
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.base.Target.Companion.android
import dev.whyoleg.kamp.base.Target.Companion.common
import dev.whyoleg.kamp.base.Target.Companion.js
import dev.whyoleg.kamp.base.Target.Companion.jvm
import dev.whyoleg.kamp.base.Target.Companion.jvm6
import dev.whyoleg.kamp.dsl.DependencySetType.api
import dev.whyoleg.kamp.dsl.DependencySetType.implementation
import dev.whyoleg.kamp.dsl.SourceType.main
import dev.whyoleg.kamp.dsl.SourceType.test


val test: KampExtension.() -> Unit = {
    val linux = Target.linuxX64.copy(name = "linux")
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
    val k = MavenArtifact<JvmBasedTarget>("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31")

    targets += linux + jvm + js

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
        android {
            main {
                implementation(k)
            }
            test {
                api {
                    +k
                }
            }
        }

        val s = (jvm + android).sourceSet("jvm6")
        s {
            main {
                implementation(k)
            }
            test {
                api {
                    +k
                }
            }
        }
        android {
            main {
                implementation(k)
            }
            test {
                api {
                    +k
                }
            }
        }
        (jvm + jvm6) {
            main {
                implementation(k)
            }
        }
    }

//    data(jvm + js + linux) {
//        dependencies {
//            main {
//                implementation(kotlind)
//            }
//            test {
//                implementation {
//                    +testd
//                    +Dependency(
//                        common,
//                        MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31")
//                    )
//                }
//            }
//        }
//        jvm.data {
//            dependencies {
//                test {
//                    implementation(
//                        Dependency(
//                            jvm,
//                            MavenArtifact("org.jetbrains.kotlin", "kotlin-test-junit", "1.3.31")
//                        )
//                    )
//                }
//            }
//        }
//    }
}