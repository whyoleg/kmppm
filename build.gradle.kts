import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.Dependency
import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import kotlin.reflect.full.*

kampRoot {
    plugins(BuiltInPlugins.updates)

    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}

@UseExperimental(ExperimentalStdlibApi::class)
val collectedDependencies: SourceSetBuilder<JvmTarget>.() -> Unit = {
    main {
        val rawType = kotlin.reflect.typeOf<RawDependency>()
        val classifierType = kotlin.reflect.typeOf<Classifier>()
        val depType = kotlin.reflect.typeOf<Dependency>()

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
