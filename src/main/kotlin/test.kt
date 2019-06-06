package dev.whyoleg.kamp

import dev.whyoleg.kamp.base.*
import dev.whyoleg.kamp.dsl.kampJvm
import dev.whyoleg.kamp.ext.KampMultiplatformExtension
import org.gradle.api.Project

val test: KampMultiplatformExtension.() -> Unit = {
    val linux = linuxX64.copy(name = "linux")
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

    val kotlinTest =
        Dependency(common, MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31"))

    sourceSets {
        common {
            main {
                implementation(kotlind)
            }
            test {
                implementation {
                    +testd
                    +kotlinTest
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

fun Project.m() {
    kampJvm {
        sourceSet {
            main {
                implementation {

                }
            }
        }
    }
}