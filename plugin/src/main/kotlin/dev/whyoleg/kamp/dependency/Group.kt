package dev.whyoleg.kamp.dependency

interface Group {
    val group: String
    val groupProvider: RepositoryProvider?

    companion object {
        internal operator fun invoke(group: String, groupProvider: RepositoryProvider? = null): Group = object : Group {
            override val group: String get() = group
            override val groupProvider: RepositoryProvider? get() = groupProvider
        }
    }
}
