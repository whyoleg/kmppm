plugins {
    ids(Plugins.kotlin)
}

configureKotlin("dependencies", true) {
    target.dependenciesMain {
        api(Dependencies.kotlin.plugin)
    }
}
