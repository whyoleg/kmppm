import dev.whyoleg.kamp.*

//plugins {
//    `build-scan`
//}
kampRoot {
    with(Plugins) {
        plugins(updates)
    }
}
//extensions.configure<com.gradle.scan.plugin.internal.api.j>("buildScan") {
//    termsOfServiceUrl = "https://gradle.com/terms-of-service"
//    termsOfServiceAgree = "yes"
//
//    publishAlways()
//}