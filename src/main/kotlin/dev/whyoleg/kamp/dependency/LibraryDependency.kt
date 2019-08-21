package dev.whyoleg.kamp.dependency

data class LibraryDependency(val path: String, val isFolder: Boolean = true) : UnTypedDependency