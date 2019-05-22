package dev.whyoleg.kamp

//@file:Suppress("FunctionName", "unused")
//
//import org.gradle.api.artifacts.dsl.*
//import org.gradle.kotlin.dsl.*
//import org.jetbrains.kotlin.gradle.plugin.*
//
//interface JVMDependencyHelper {
//    val jvm: String
//    val exclude: List<String>
//
//    fun DependencyHandler.implement() = add("implementation", jvm) { exclude.forEach { exclude(it) } }
//    fun DependencyHandler.api() = add("api", jvm) { exclude.forEach { exclude(it) } }
//}
//
//data class MPPDependency(
//    private val group: String,
//    private val artifact: String,
//    private val version: String,
//    private val metaPrefix: String? = null,
//    private val jvmPrefix: String? = "JVM"
//) : JVMDependencyHelper {
//
//    override val exclude: List<String> = emptyList()
//
//    private fun String.add(prefix: String?): String = this + (prefix?.let { "-$it" } ?: "")
//    override val jvm = "$group:${artifact.add(jvmPrefix)}:$version"
//    private val meta = "$group:${artifact.add(metaPrefix)}:$version"
//
//    fun KotlinDependencyHandler.implementMeta() = implementation(meta)
//    fun KotlinDependencyHandler.implementJvm() = implementation(jvm)
//    fun KotlinDependencyHandler.apiMeta() = api(meta)
//    fun KotlinDependencyHandler.apiJvm() = api(jvm)
//}
//
//data class JVMDependency(
//    private val group: String,
//    private val artifact: String,
//    private val version: String,
//    override val exclude: List<String> = emptyList()
//) : JVMDependencyHelper {
//    override val jvm = "$group:$artifact:$version"
//
//    fun KotlinDependencyHandler.implement() = implementation(jvm)
//    fun KotlinDependencyHandler.api() = api(jvm)
//}
//
////object Dependency {
////
////    object Group {
////        const val kotlin = "org.jetbrains.kotlin"
////        const val kotlinx = "org.jetbrains.kotlinx"
////        const val koin = "org.koin"
////        const val vertx = "io.vertx"
////        const val ktor = "io.ktor"
////        const val logging = "io.github.microutils"
////        const val sqlDelight = "com.squareup.sqldelight"
////        const val soywiz = "com.soywiz"
////        const val zookeeper = "org.apache.zookeeper"
////        const val curator = "org.apache.curator"
////        const val openjfx = "org.openjfx"
////    }
////
////    inline fun <T> MPP(block: MPP.() -> T): T = block(MPP)
////
////    object MPP {
////        inline fun <T> Kotlin(block: Kotlin.() -> T): T = block(Kotlin)
////
////        object Kotlin {
////            val stdlib = MPPDependency(Group.kotlin, "kotlin-stdlib", Version.kotlin, "common", "jdk8")
////            val coroutines = MPPDependency(Group.kotlinx, "kotlinx-coroutines", Version.coroutines, "core-common", "jdk8")
////            val serialization = MPPDependency(Group.kotlinx, "kotlinx-serialization-runtime", Version.serialization, "common", null)
////        }
////
////        inline fun <T> Ktor(block: Ktor.() -> T): T = block(Ktor)
////
////        object Ktor {
////            val core = MPPDependency(Group.ktor, "ktor-client-core", Version.ktor)
////            val websocket = MPPDependency(Group.ktor, "ktor-client-websocket", Version.ktor)
////        }
////
////        val logging = MPPDependency(Group.logging, "kotlin-logging", Version.logging, "common", null)
////        val krypto = MPPDependency(Group.soywiz, "krypto", Version.krypto, "metadata")
////    }
////
////    inline fun <T> JVM(block: JVM.() -> T): T = block(JVM)
////
////    object JVM {
////        inline fun <T> Kotlin(block: Kotlin.() -> T): T = block(Kotlin)
////
////        object Kotlin {
////            val reflect = JVMDependency(Group.kotlin, "kotlin-reflect", Version.kotlin)
////            val coroutinesJavaFX = JVMDependency(Group.kotlinx, "kotlinx-coroutines-javafx", Version.coroutines)
////            val coroutinesSlf4j = JVMDependency(Group.kotlinx, "kotlinx-coroutines-slf4j", Version.coroutines)
////        }
////
////        inline fun <T> Ktor(block: Ktor.() -> T): T = block(Ktor)
////
////        object Ktor {
////            val cio = JVMDependency(Group.ktor, "ktor-client-cio", Version.ktor)
////        }
////
////        inline fun <T> Koin(block: Koin.() -> T): T = block(Koin)
////
////        object Koin {
////            val core = JVMDependency(Group.koin, "koin-core", Version.koin)
////            val ext = JVMDependency(Group.koin, "koin-core-ext", Version.koin)
////            val slf4j = JVMDependency(Group.koin, "koin-logger-slf4j", Version.koin)
////        }
////
////        val koin = Koin { listOf(core, ext, slf4j) }
////
////        inline fun <T> Logging(block: Logging.() -> T): T = block(Logging)
////
////        object Logging {
////            val slf4j = JVMDependency("org.slf4j", "slf4j-api", Version.slf4j)
////            val julToSlf4j = JVMDependency("org.slf4j", "jul-to-slf4j", Version.slf4j)
////            val logback = JVMDependency("ch.qos.logback", "logback-classic", Version.logback)
////        }
////
////        inline fun <T> Vertx(block: Vertx.() -> T): T = block(Vertx)
////
////        object Vertx {
////            val kotlin = JVMDependency(Group.vertx, "vertx-lang-kotlin", Version.vertx)
////            val coroutines = JVMDependency(Group.vertx, "vertx-lang-kotlin-coroutines", Version.vertx)
////            val serviceDiscovery = JVMDependency(Group.vertx, "vertx-service-discovery", Version.vertx)
////            val config = JVMDependency(Group.vertx, "vertx-config", Version.vertx)
////            val client = JVMDependency(Group.vertx, "vertx-web-client", Version.vertx)
////            val metrics = JVMDependency(Group.vertx, "vertx-micrometer-metrics", Version.vertx)
////            val circuitBreaker = JVMDependency(Group.vertx, "vertx-circuit-breaker", Version.vertx)
////            val healthCheck = JVMDependency(Group.vertx, "vertx-health-check", Version.vertx)
////            val webClient = JVMDependency(Group.vertx, "vertx-web-client", Version.vertx)
////            val zookeeper = JVMDependency(Group.vertx, "vertx-zookeeper", Version.vertx, listOf(Group.zookeeper, Group.curator))
////            val oauth = JVMDependency(Group.vertx, "vertx-auth-oauth2", Version.vertx)
////
////            val jooq = JVMDependency("io.github.jklingsporn", "vertx-jooq-classic-reactive", Version.vertxJooq)
////        }
////
////        inline fun <T> Zookeeper(block: Zookeeper.() -> T): T = block(Zookeeper)
////
////        object Zookeeper {
////            val zookeeper = JVMDependency(Group.zookeeper, "zookeeper", Version.zookeeper)
////            val curatorClient = JVMDependency(Group.curator, "curator-client", Version.curator)
////            val curatorFramework = JVMDependency(Group.curator, "curator-framework", Version.curator)
////            val curatorRecipes = JVMDependency(Group.curator, "curator-recipes", Version.curator)
////        }
////
////        val sqlDelight = JVMDependency(Group.sqlDelight, "sqlite-driver", Version.sqlDelight)
////        val micrometer = JVMDependency("io.micrometer", "micrometer-registry-influx", Version.micrometer)
////        val javaxAnnotations = JVMDependency("javax.annotation", "javax.annotation-api", Version.javaxAnnotations)
////
////        inline fun <T> JavaFX(block: JavaFX.() -> T): T = block(JavaFX)
////
////        object JavaFX {
////            val base = JVMDependency(Group.openjfx, "javafx-base", Version.openjfx)
////            val controls = JVMDependency(Group.openjfx, "javafx-controls", Version.openjfx)
////            val graphics = JVMDependency(Group.openjfx, "javafx-graphics", Version.openjfx)
////            val fxml = JVMDependency(Group.openjfx, "javafx-fxml", Version.openjfx)
////        }
////    }
////
////}