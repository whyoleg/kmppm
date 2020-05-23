plugins {
    ids(Plugins.kotlin)
    `java-gradle-plugin`
}

configureKotlin {
    target.dependenciesMain {
        api(KampModules.dependencies)
        api(KampModules.Features.kamp)
        api(KampModules.Features.kotlin)
    }
}

gradlePlugin {
    plugins {
        create("kampBuildPlugin") {
            id = "dev.whyoleg.kamp.build"
            displayName = "Kamp Build Plugin"
            implementationClass = "dev.whyoleg.kamp.build.KampBuildPlugin"
        }
    }
}
