package dev.whyoleg.kamp

import dev.whyoleg.kamp.base.*
import dev.whyoleg.kamp.base.dependency.*
import dev.whyoleg.kamp.base.target.*
import dev.whyoleg.kamp.dsl.*
import dev.whyoleg.kamp.ext.*
import org.gradle.api.*

val test: KampMultiPlatformExtension.() -> Unit = {
    val linux = linuxX64.copy(name = "linux")

    val kotlin = Group("org.jetbrains.kotlin")
    val commonTargets = setOf(common("common"), jvm("jdk8"), js("js"))
    val base = kotlin.base("1.3.31")(commonTargets)
    val kotlind = base("kotlin-stdlib")
    val testd = base("kotlin-test")

    val k = kotlin.dependency("kotlin-test-annotations-common", "1.3.31")(jvm, jvm6)

    targets += linux + jvm + js

    val kotlinTest = kotlin.dependency("kotlin-test-annotations-common", "1.3.31")(common)

    sourceSets {
        //common
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
        //jvmbased for android
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

        val s = (jvm + android).named("jvm6")
        //jvmbased
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