package dev.whyoleg.kamp

import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.ext.*
import dev.whyoleg.kamp.target.*

val test: KampMultiPlatformExtension.() -> Unit = {
    val linux = linuxX64.copy(name = "linux")
    val kotlin = object : GroupVersionClassifier, MavenCentralProviderClassifier {
        override val group: String = "org.jetbrains.kotlin"
        override val version: String = "1.3.31"
    }
    val commonTargets = setOf(common("common"), jvm("jdk8"), js("js"))
    val kotlind = kotlin.dependency("kotlin-stdlib", commonTargets)
    val testd = kotlin.dependency("kotlin-test", common("common"), jvm(), js("js"))
    val k = kotlin.dependency("kotlin-test-annotations-common", jvm(), jvm6(), android())
    val kotlinTest = kotlin.dependency("kotlin-test", common("annotations-common"), jvm("junit"))

    targets(jvm, js) // + linux

    languageSettings {

    }

    sourceSets {
        //common
        common {
            kotlinOptions {

            }

            main(sources = listOf(), resources = listOf()) {
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

        val s = setOf(jvm, android).named("jvm6")
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
        setOf(jvm, jvm6).invoke {
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

val depTest: KampMultiPlatformExtension.() -> Unit = {
    sourceSets {
        common {
            main {
                BuiltInDependencies.kotlin {
                    implementation(stdlib)
                }
            }
            test {
                BuiltInDependencies.kotlin {
                    implementation(test, annotations)
                }
            }
        }
    }
}

val depTest2: KampMultiPlatformExtension.() -> Unit = {
    with(BuiltInPlugins) { plugins(serialization, atomicfu) }

    sourceSets {
        common {
            main {
                with(BuiltInDependencies) {
                    kotlin {
                        implementation(stdlib)
                    }
                    kotlinx {
                        implementation(atomicfu, serialization)
                    }
                    coroutines {
                        implementation(core)
                    }
                }
            }
        }
    }
}