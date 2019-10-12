package dev.whyoleg.kamp.version

import org.gradle.api.*

class VersionUpdatersBuilder internal constructor() {
    internal val updaters: MutableList<VersionUpdater> = mutableListOf()

    fun register(taskName: String, finders: List<VersionFinder>, versionUpdate: Version.() -> Version) {
        updaters += VersionUpdater(taskName, finders, versionUpdate)
    }
}

internal data class VersionUpdater(
    val taskName: String,
    val finders: List<VersionFinder>,
    val versionUpdate: Version.() -> Version
)

data class VersionFinder(
    val filePath: String,
    val textBeforeLine: String,
    val lineStart: String,
    val lineEnd: String
)

data class Version(val major: Int, val minor: Int, val patch: Int)

fun Version.incrementPatch(): Version = copy(patch = patch + 1)
fun Version.incrementMinor(): Version = copy(minor = minor + 1, patch = 0)
fun Version.incrementMajor(): Version = copy(major = major + 1, minor = 0, patch = 0)

internal data class VersionLine(val start: String, val version: String, val end: String)

internal fun Project.updateFileLines(path: String, block: (MutableList<String>) -> Unit) {
    val file = rootDir.resolve(path)
    val lines = file.readLines().toMutableList()
    block(lines)
    file.writeText(lines.joinToString("\n"))
}

internal fun MutableList<String>.replaceLineAfterContained(text: String, block: (String) -> String) {
    val index = indexOfFirst { it.contains(text) }
    this[index + 1] = block(this[index + 1])
}

internal fun String.version(): Version {
    val (major, minor, patch) = split(".").map(String::toInt)
    return Version(major, minor, patch)
}

internal fun Version.string(): String = "$major.$minor.$patch"

internal fun VersionLine.string(): String = start + version + end

internal fun String.splitLine(start: String, end: String): VersionLine {
    val lineWithVersion = substringAfterLast(start + "\"")
    val version = lineWithVersion.substringBefore("\"" + end)
    val lineStart = substringBeforeLast(lineWithVersion)
    val lineEnd = lineWithVersion.substringAfter(version)
    return VersionLine(lineStart, version, lineEnd)
}

internal fun String.increment(block: Version.() -> Version): String = version().block().string()

internal fun VersionLine.incrementVersion(block: Version.() -> Version): VersionLine = copy(version = version.increment(block))

internal fun VersionUpdater.update(project: Project) {
    finders.forEach { (filePath, textBeforeLine, lineStart, lineEnd) ->
        project.updateFileLines(filePath) { lines ->
            lines.replaceLineAfterContained(textBeforeLine) { line ->
                line.splitLine(lineStart, lineEnd).incrementVersion(versionUpdate).string()
            }
        }
    }
}
