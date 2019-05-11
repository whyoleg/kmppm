plugins {
    id(Plugin.updates) version Version.updates
    `kotlin-dsl`
}

kotlinDslPluginOptions { experimentalWarning.set(false) }

repositories {
    jcenter()
    mavenCentral()
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlinx") }
}

dependencies {
    implementation(Dependencies.kotlinGradlePlugin)
//    implementation("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:1.0.2")
//    implementation("com.github.jengelman.gradle.plugins:shadow:5.0.0")
}