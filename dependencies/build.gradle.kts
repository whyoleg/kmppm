import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.Dependency
import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import kotlin.reflect.*
import kotlin.reflect.full.*

@UseExperimental(ExperimentalStdlibApi::class)
kampJvm(ProjectConfiguration("", "") { "" }) {
    plugins(Plugins.updates)

    val rawType = typeOf<RawDependency>()
    val classifierType = typeOf<Classifier>()
    val depType = typeOf<Dependency>()

    fun Classifier.dependencies(): List<Dependency> {
        val props = this::class.memberProperties
        val recursive = props.filter { it.returnType.isSubtypeOf(classifierType) }.mapNotNull { it.call(this) as? Classifier }.flatMap { it.dependencies() }
        val untyped = props.filter { it.returnType.isSubtypeOf(rawType) }.mapNotNull { it.call(this) as? RawDependency }.map { it(Target.jvm()) }
        val deps = props.filter { it.returnType.isSubtypeOf(depType) }.mapNotNull { it.call(this) as? Dependency }
        return deps + untyped + recursive
    }

    val list = BuiltInDependencies.dependencies().toSet()
    val multi = list.filterIsInstance<MultiDependency>().toSet()
    val typed = list.filterIsInstance<TargetDependency<JvmTarget>>().toSet()

    source {
        main {
            implementation(multi)
            implementation(typed)
        }
    }
}