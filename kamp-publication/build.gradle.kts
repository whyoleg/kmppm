plugins {
    ids(Plugins.kotlin)
}

configureKotlin("publication", true) {
    target.dependenciesMain {
        api(Dependencies.kotlin.plugin)
    }
}
