import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.modules.*
import org.jetbrains.kotlin.config.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    use(Plugins.pluginModule)
}

kotlin {
    target {
        dependenciesMain {
            implementation(KotlinDependencies.Stable.gradlePlugin)
            compileOnly(BuiltInDependencies.Stable.shadow)
            // val k = kotlinJvm.classpath!!.copy(version = { "1.3.41" })
            //                val c = k.copy(name = "kotlin-compiler-embeddable")
            //                implementation(k(Target.jvm.invoke()))
            //                compileOnly(c(Target.jvm.invoke()))
            //                compileOnly(shadow)
        }
        options {
            progressive()
            languageVersion = "1.3"
            apiVersion = "1.3"
            jvmTarget = "1.6"
            enableLanguageFeatures(listOf(LanguageFeature.InlineClasses, LanguageFeature.NewInference))
            useExperimentalAnnotations(KotlinExperimentalAnnotations.All)
        }
    }
}

jvmPublication(publication, publisher.provider(publish = false)) {
    artifactId = "kamp"
}

createBintrayPublishTask(publisher)
