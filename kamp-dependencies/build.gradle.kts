plugins {
    ids(Plugins.kotlin)
}

configureKotlin("dependencies", true) {
    target.kampSourceSetMain.dependencies {
        api(Dependencies.kotlin.plugin)
    }
}
