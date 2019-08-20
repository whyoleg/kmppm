package dev.whyoleg.kamp.settings

enum class ExperimentalAnnotation(val value: String) {
    ObsoleteCoroutinesApi("kotlinx.coroutines.ObsoleteCoroutinesApi"),
    ExperimentalCoroutinesApi("kotlinx.coroutines.ExperimentalCoroutinesApi"),
    InternalCoroutinesApi("kotlinx.coroutines.InternalCoroutinesApi"),
    FlowPreview("kotlinx.coroutines.FlowPreview"),
    ImplicitReflectionSerializer("kotlinx.serialization.ImplicitReflectionSerializer"),
    KtorExperimentalAPI("io.ktor.util.KtorExperimentalAPI"),
    Experimental("kotlin.Experimental"),
    ExperimentalUnsignedTypes("kotlin.ExperimentalUnsignedTypes"),
    ExperimentalContracts("kotlin.contracts.ExperimentalContracts"),
    ExperimentalTime("kotlin.time.ExperimentalTime")
}