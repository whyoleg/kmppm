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
    val textBeforeVersion: String,
    val offset: Int,
    val lineStart: String,
    val lineEnd: String
)

data class Version(val major: Int, val minor: Int, val patch: Int)

fun Version.incrementPatch(): Version = copy(patch = patch + 1)
fun Version.incrementMinor(): Version = copy(minor = minor + 1, patch = 0)
fun Version.incrementMajor(): Version = copy(major = major + 1, minor = 0, patch = 0)

internal data class VersionLine(val start: String, val version: String, val end: String)

internal fun Project.readFileLines(path: String, block: (List<String>) -> Unit) {
    val file = rootDir.resolve(path)
    val lines = file.readLines()
    block(lines)
}

internal fun Project.updateFileLines(path: String, block: (MutableList<String>) -> Unit) {
    val file = rootDir.resolve(path)
    val lines = file.readLines().toMutableList()
    block(lines)
    file.writeText(lines.joinToString("\n"))
}

internal fun List<String>.indexAfterContained(text: String, offset: Int): Int {
    val index = indexOfFirst { it.contains(text) }
    val versionLineIndex = index + offset
    require(index >= 0) { "No line starting with '$text'" }
    require(size > versionLineIndex) { "Wrong offset last line '$lastIndex', version line '$versionLineIndex' " }
    return versionLineIndex
}

internal fun List<String>.findLineAfterContained(text: String, offset: Int): String {
    val versionLineIndex = indexAfterContained(text, offset)
    return this[versionLineIndex]
}

internal fun MutableList<String>.replaceLineAfterContained(text: String, offset: Int, block: (String) -> String) {
    val versionLineIndex = indexAfterContained(text, offset)
    this[versionLineIndex] = block(this[versionLineIndex])
}

fun String.version(): Version {
    val components = split(".")
    require(components.size == 3) { "Version must be in format 'x.y.z' but was '$this'" }
    val (major, minor, patch) = components.map {
        requireNotNull(it.toIntOrNull()) { "Version component must be Int, but was '$it'" }
    }
    return Version(major, minor, patch)
}

internal fun Version.string(): String = "$major.$minor.$patch"

internal fun VersionLine.string(): String = start + version + end

internal fun String.splitLine(start: String, end: String): VersionLine {
    val lineWithVersion = substringAfterLast(start)
    val version = lineWithVersion.substringBefore(end)
    val lineStart = substringBeforeLast(lineWithVersion)
    val lineEnd = lineWithVersion.substringAfter(version)
    return VersionLine(lineStart, version, lineEnd)
}

internal fun String.increment(block: Version.() -> Version): String = version().block().string()

internal fun VersionLine.incrementVersion(block: Version.() -> Version): VersionLine = copy(version = version.increment(block))

internal fun VersionUpdater.update(project: Project) {
    finders.forEach { (filePath, textBeforeVersion, offset, lineStart, lineEnd) ->
        project.readFileLines(filePath) { lines ->
            lines.findLineAfterContained(textBeforeVersion, offset).splitLine(lineStart, lineEnd).version.version()
        }
    }
    finders.forEach { (filePath, textBeforeVersion, offset, lineStart, lineEnd) ->
        project.updateFileLines(filePath) { lines ->
            lines.replaceLineAfterContained(textBeforeVersion, offset) { line ->
                line.splitLine(lineStart, lineEnd).incrementVersion(versionUpdate).string()
            }
        }
    }
}
