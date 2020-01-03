import dev.whyoleg.kamp.dependencies.*
import dev.whyoleg.kamp.dependency.*

plugins {
    use(Plugins.pluginModule)
}

kotlin {
    target.dependenciesMain {
        implementation(KotlinDependencies.stable.gradlePlugin)
        //        source { main { compileOnly(kotlinJvm, shadow, updates, docker, detekt, bintray, buildScan) } }
    }
}
