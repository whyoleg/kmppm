import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.version.*

plugins { `kotlin-dsl` }

buildscript {
    val bootstrap: (RepositoryHandler) -> String = { handler: RepositoryHandler ->
        if (properties["dev.whyoleg.bootstrap"] == "true") {
            handler.mavenLocal()
            "local"
        } else {
            handler.maven { setUrl("https://dl.bintray.com/whyoleg/kamp") }
            "0.1.13"
        }
    }
    extra["bootstrap"] = bootstrap
    dependencies.classpath("dev.whyoleg.kamp:kamp:${bootstrap(repositories)}")
}

kampBuild {
    registerVersions {
        val bootstrap = properties["bootstrap"] as (RepositoryHandler) -> String
        default(BuiltInVersions.Stable.copy(kamp = bootstrap(repositories)))

        //versions for dependencies check
        "stable" use BuiltInVersions.Stable
        "latest" use BuiltInVersions.Latest
    }
    with(BuiltInPlugins) {
        resolvePlugins(kotlinJvm, bintray, buildScan, updates)
    }
    dependencies(BuiltInDependencies.kotlin.plugin.gradle)
}