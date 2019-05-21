object Compiler {
    fun all() = listOf(
        "-progressive",
        "-XXLanguage:+InlineClasses",
        "-XXLanguage:+NewInference"
    )
//        "-Xuse-experimental=" + listOf(
//            ObsoleteCoroutinesApi,
//            ExperimentalCoroutinesApi,
//            InternalCoroutinesApi,
//            ImplicitReflectionSerializer,
//            KtorExperimentalAPI,
//            FlowPreview,
//            Experimental,
//            ExperimentalUnsignedTypes

    private const val ObsoleteCoroutinesApi = "kotlinx.coroutines.ObsoleteCoroutinesApi"
    private const val ExperimentalCoroutinesApi = "kotlinx.coroutines.ExperimentalCoroutinesApi"
    private const val InternalCoroutinesApi = "kotlinx.coroutines.InternalCoroutinesApi"
    private const val FlowPreview = "kotlinx.coroutines.FlowPreview"
    private const val ImplicitReflectionSerializer = "kotlinx.serialization.ImplicitReflectionSerializer"
    private const val KtorExperimentalAPI = "io.ktor.util.KtorExperimentalAPI"
    private const val Experimental = "kotlin.Experimental"
    private const val ExperimentalUnsignedTypes = "kotlin.ExperimentalUnsignedTypes"
}
