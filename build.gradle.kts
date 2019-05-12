plugins {
    id(Plugin.updates) version Version.updates
    `kotlin-dsl`
    `maven-publish`
}
group = "dev.whyoleg.kmppm"
version = "1.0.0"

kotlinDslPluginOptions { experimentalWarning.set(false) }

repositories {
    jcenter()
    mavenCentral()
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlinx") }
}

dependencies {
    implementation(Dependencies.kotlinGradlePlugin)
}

gradlePlugin {
    plugins {
        create("kmppm") {
            id = "dev.whyoleg.kmppm"
            implementationClass = "dev.whyoleg.kmppm.KMPPMPlugin"
        }
    }
}