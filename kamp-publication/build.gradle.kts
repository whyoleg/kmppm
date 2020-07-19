plugins {
    ids(Plugins.kotlin)
}

configureKotlin("publication", true) {
    target.kampSourceSetMain.dependencies {
        api(Dependencies.kotlin.plugin)
    }
}
