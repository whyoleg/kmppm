package dev.whyoleg.kmppm.base

data class ArtifactData(
    val artifact: Artifact,
    val dependenciesConfigurationType: DependenciesConfigurationType,
    val sourceType: SourceType
)