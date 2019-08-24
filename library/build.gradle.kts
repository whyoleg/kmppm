import dev.whyoleg.kamp.*
import org.jetbrains.kotlin.gradle.tasks.*
import java.util.*

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
    id("com.jfrog.bintray")
    id("net.nemerosa.versioning")
}

val v = "0.1.3-${versioning.info.build}"
//val v = "0.1.3"

repositories {
    jcenter()
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { setUrl("https://plugins.gradle.org/m2/") }
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
}
dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.41")
    compileOnly("com.github.jengelman.gradle.plugins:shadow:5.1.0")
    compileOnly("com.github.ben-manes:gradle-versions-plugin:0.22.0")
    compileOnly("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:1.5.0")
    compileOnly("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.0.0")
    compileOnly("gradle.plugin.net.nemerosa:versioning:2.8.2")
    compileOnly("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
}

tasks.withType<KotlinCompile> {
    val jdkVersion = "1.8"
    sourceCompatibility = jdkVersion
    targetCompatibility = jdkVersion
    kotlinOptions.jvmTarget = jdkVersion
    kotlinOptions {
        apiVersion = "1.3"
        languageVersion = "1.3"
        freeCompilerArgs =
            listOf("-progressive", "-XXLanguage:+InlineClasses", "-XXLanguage:+NewInference", "-Xuse-experimental=kotlin.Experimental")
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

group = "dev.whyoleg.kamp"
version = v

publishing {
    publications {
        create<MavenPublication>("bintray") {
            groupId = "dev.whyoleg.kamp"
            artifactId = "kamp"
            version = v

            from(components["java"])
            artifact(sourcesJar)

            pom.withXml {
                asNode().apply {
                    appendNode("description", "Gradle plugin for kotlin MPP")
                    appendNode("name", rootProject.name)
                    appendNode("url", "https://github.com/whyoleg/kamp")
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", "The Apache Software License, Version 2.0")
                        appendNode("url", "http://www.apache.org/licenses/LICENSE-2.0.txt")
                        appendNode("distribution", "repo")
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", "whyoleg")
                        appendNode("name", "Oleg")
                        appendNode("email", "whyoleg@gmail.com")
                    }
                    appendNode("scm").apply {
                        appendNode("url", "git@github.com:whyoleg/kamp.git")
                        appendNode("connection", "scm:git:git@github.com:whyoleg/kamp.git")
                    }
                }
            }
        }
    }
}

bintray {
    // Getting bintray user and key from properties file or command line
    user = System.getenv("bintray_user")
    key = System.getenv("bintray_key")

    // Automatic publication enabled
    publish = true
//    override = true

    // Set maven publication onto bintray plugin
    setPublications("bintray")

    // Configure package
    pkg.apply {
        repo = "kamp"
        name = "kamp"
        setLicenses("Apache-2.0")
        setLabels("Kotlin", "MPP", "Plugin")
        vcsUrl = "git@github.com:whyoleg/kamp.git"
        websiteUrl = "https://github.com/whyoleg/kamp"
        githubRepo = "https://github.com/whyoleg/kamp"
        githubReleaseNotesFile = "https://github.com/whyoleg/kamp/README.md"

        // Configure version
        version.apply {
            name = v
            desc = "Gradle plugin for kotlin MPP"
            released = Date().toString()
//            vcsTag = project.versioning.info.tag
        }
    }
}

tasks.getByName("bintrayUpload") {
    dependsOn("build")
    dependsOn("sourcesJar")
    dependsOn("generatePomFileForBintrayPublication")
}
