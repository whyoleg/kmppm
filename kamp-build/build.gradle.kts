plugins {
    ids(Plugins.kotlin)
}

configureKotlin("build", gradleApi = true) {
    target.kampSourceSetMain.dependencies {
        api(KampModules.dependencies)
        api(KampModules.Features.kamp)
        api(KampModules.Features.kotlin)
    }
}
