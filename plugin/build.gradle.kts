import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

configuredLibrary {
    with(BuiltInPlugins) {
        plugins(javaPlugin, mavenPublish)
        source {
            main {
                implementation(kotlinJvm.classpath!!.copy(version = { "1.3.41" })(Target.jvm.invoke()))
                compileOnly(kotlinJvm, shadow, updates, docker, detekt, bintray, buildScan)
            }
        }
    }
}
