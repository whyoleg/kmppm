import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.Dependency
import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import dev.whyoleg.kamp.version.*
import kotlin.reflect.*
import kotlin.reflect.full.*

kampRoot {
    plugins(BuiltInPlugins.updates)

    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }

    versionUpdate {
        val finders = listOf(
            VersionFinder(
                filePath = "buildSrc/src/main/kotlin/Ext.kt",
                textBeforeVersion = "private val configuration = ProjectConfiguration",
                offset = 1,
                lineStart = " \"",
                lineEnd = "\""
            ),
            VersionFinder(
                filePath = "buildSrc/build.gradle.kts",
                textBeforeVersion = "https://dl.bintray.com/whyoleg/kamp",
                offset = 1,
                lineStart = " \"",
                lineEnd = "\""
            ),
            VersionFinder(
                filePath = "library/src/main/kotlin/dev/whyoleg/kamp/version/BuiltInVersions.kt",
                textBeforeVersion = "val Stable: BuiltInVersions",
                offset = 1,
                lineStart = "kamp = \"",
                lineEnd = "\","
            )
        )
        listOf<Pair<String, (Version) -> Version>>(
            "Major" to Version::incrementMajor,
            "Minor" to Version::incrementMinor,
            "Patch" to Version::incrementPatch,
            "Full" to { System.getProperty("NEW_VERSION").version() }
        ).forEach { (name, update) ->
            register("update${name}Version", finders, update)
        }
    }
}

@UseExperimental(ExperimentalStdlibApi::class)
val collectedDependencies: SourceSetBuilder<JvmTarget>.() -> Unit = {
    main {
        val rawType = typeOf<RawDependency>()
        val classifierType = typeOf<Classifier>()
        val depType = typeOf<Dependency>()

        fun Classifier.dependencies(): List<Dependency> {
            val props = this::class.memberProperties
            val recursive =
                props
                    .filter { it.returnType.isSubtypeOf(classifierType) }
                    .mapNotNull { it.call(this) as? Classifier }
                    .flatMap { it.dependencies() }
            val untyped =
                props
                    .filter { it.returnType.isSubtypeOf(rawType) }
                    .mapNotNull { it.call(this) as? RawDependency }
                    .filter { it.name != "kamp" }
                    .map { it(Target.jvm()) }
            val deps =
                props
                    .filter { it.returnType.isSubtypeOf(depType) }
                    .mapNotNull { it.call(this) as? Dependency }
            return deps + untyped + recursive
        }

        val list = BuiltInDependencies.dependencies().toSet()
        val multi = list.filterIsInstance<MultiDependency>().toSet()
        val typed = list.filterIsInstance<TargetDependency<JvmTarget>>().toSet()

        implementation(multi)
        implementation(typed)
    }
}

project.extra["collectedDependencies"] = collectedDependencies
