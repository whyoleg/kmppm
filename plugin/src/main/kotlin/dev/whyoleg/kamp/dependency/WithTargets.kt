package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

interface WithTargets {
    val targets: Iterable<TargetWithPostfix>

    companion object {
        internal operator fun invoke(targets: Iterable<TargetWithPostfix>): WithTargets = object : WithTargets {
            override val targets: Iterable<TargetWithPostfix> get() = targets
        }
    }
}

fun Group.targets(targets: Iterable<TargetWithPostfix>): GroupWithTargets =
    object : GroupWithTargets, Group by this, WithTargets by WithTargets(targets) {}

fun GroupWithVersion.targets(targets: Iterable<TargetWithPostfix>): GroupWithVersionTargets =
    object : GroupWithVersionTargets, GroupWithVersion by this, WithTargets by WithTargets(targets) {}

fun GroupWithArtifact.targets(targets: Iterable<TargetWithPostfix>): GroupWithTargetsArtifact =
    object : GroupWithTargetsArtifact, GroupWithArtifact by this, WithTargets by WithTargets(targets) {}

fun GroupWithVersionArtifact.targets(targets: Iterable<TargetWithPostfix>): Dependency =
    object : Dependency, GroupWithVersionArtifact by this, WithTargets by WithTargets(targets) {}


fun Group.targets(target: TargetWithPostfix, vararg targets: TargetWithPostfix): GroupWithTargets =
    targets(targets.toList() + target)

fun GroupWithVersion.targets(target: TargetWithPostfix, vararg targets: TargetWithPostfix): GroupWithVersionTargets =
    targets(targets.toList() + target)

fun GroupWithArtifact.targets(target: TargetWithPostfix, vararg targets: TargetWithPostfix): GroupWithTargetsArtifact =
    targets(targets.toList() + target)

fun GroupWithVersionArtifact.targets(target: TargetWithPostfix, vararg targets: TargetWithPostfix): Dependency =
    targets(targets.toList() + target)

