import dev.whyoleg.kamp.*

kampRoot {
    with(Plugins) {
        plugins(updates)
    }

    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}
