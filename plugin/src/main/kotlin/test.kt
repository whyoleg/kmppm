import dev.whyoleg.kamp.dependencies.*
import dev.whyoleg.kamp.platform.*
import org.jetbrains.kotlin.gradle.dsl.*

fun KotlinMultiplatformExtension.test() {
    val kotlin = KotlinModule(KotlinVersion.stable)
    listOf(jvm(), js()).dependencies("main") {

    }
    listOf(jvm(), js()).dependenciesMain {
        with(kotlin.dependencies) {
            api(stdlib)
            api(reflect) {

            }
        }
    }
    jvm("jvm6") {
        dependencies("main") {

        }
    }
    val jvmMain = sourceSets.create("jvmMain")
    val jsMain = sourceSets.create("jsMain")
    listOf(jvmMain, jsMain).dependencies(KampPlatform.common) {

    }
}
