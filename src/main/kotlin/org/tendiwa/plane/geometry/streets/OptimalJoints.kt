package org.tendiwa.plane.geometry.streets

import org.tendiwa.graphs.vertices
import org.tendiwa.plane.geometry.graphs.Graph2D
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.tools.butIf

internal val Graph2D.optimalJoints: List<Joint>
    get() = vertices
        .flatMap { optimalJointFan(it) }
        .removeRepeatedMonoJointIfIsolatedSegment()

internal fun List<Joint>.removeRepeatedMonoJointIfIsolatedSegment(): List<Joint> =
    butIf(
        { it.all { it is MonoJoint } },
        { listOf(it.first()) }
    )

private fun Graph2D.optimalJointFan(vertex: Point): List<Joint> =
    when (degreeOf(vertex)) {
        0 -> listOf()
        1 -> listOf(monoJoint(vertex))
        2 -> listOf(biJoint(vertex))
        else -> Crossroad(this, vertex).optimalJoints
    }

private fun Graph2D.monoJoint(vertex: Point): MonoJoint =
    edgesOf(vertex)
        .single()
        .let { MonoJoint(it) }

private fun Graph2D.biJoint(vertex: Point): BiJoint =
    edgesOf(vertex)
        .toList()
        .let { BiJoint(it[0], it[1]) }
