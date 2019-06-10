package dev.whyoleg.kamp

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dsl.*
import dev.whyoleg.kamp.ext.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.target.*
import org.gradle.api.*

val test: KampMultiPlatformExtension.() -> Unit = {
    val linux = linuxX64.copy(name = "linux")
    val kotlin = object : GroupVersionClassifier {
        override val group: String = "org.jetbrains.kotlin"
        override val version: String = "1.3.31"
    }
    val commonTargets = setOf(common("common"), jvm("jdk8"), js("js"))
    val kotlind = kotlin.dependency("kotlin-stdlib", commonTargets)
    val testd = kotlin.dependency("kotlin-test", common("common"), jvm(), js("js"))
    val k = kotlin.dependency("kotlin-test-annotations-common", jvm, jvm6, android)
    val kotlinTest = kotlin.dependency("kotlin-test", common("annotations-common"), jvm("junit"))

    targets += jvm + js // + linux

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
        with(BuiltInPlugins) {
            plugins(serialization, atomicfu)
        }

        sourceSet {
            main {
                implementation {
                    //                    +serialization("1.3.31")
//                    +kotlin
//                    +atmoicfu

                }
            }
        }
    }

//    kamp {
//        with
//        plugins(serialization, atomicfu)
//
//        packaging {
//            jvm {
//
//            }
//            android {
//
//            }
//            js {
//
//            }
//        }
//        publishing {
//            jvm {
//
//            }
//            android {
//
//            }
//            js {
//
//            }
//        }
//        sourceSets {
//            jvm {
//                main {
//                    implementation {
//                        +serialization("1.3.31")
//                        +kotlin
//                        +atmoicfu
//
//                    }
//                }
//            }
//        }
//    }
}

