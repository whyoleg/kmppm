import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugin.updates) version Version.updates
    kotlin("jvm") version Version.kotlin
    `java-gradle-plugin`
//    `kotlin-dsl`
    `maven-publish`
}

group = "dev.whyoleg.kmppm"
version = "0.2.0"

repositories {
    jcenter()
    mavenCentral()
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
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

tasks.withType<KotlinCompile> {
    sourceCompatibility = Version.jdk
    targetCompatibility = Version.jdk
    kotlinOptions.jvmTarget = Version.jdk
    kotlinOptions {
        apiVersion = Version.kotlinApi
        languageVersion = Version.kotlinLanguage
        freeCompilerArgs = Compiler.all()
    }
}
