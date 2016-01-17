package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.tendiwa.math.sets.allPossiblePairs
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.segments.Segment

internal class Crossroad(
    graph: UndirectedGraph<Point, Segment>,
    commonPoint: Point
) {
    private val adjacentEdges = graph.edgesOf(commonPoint)
        .apply { assert(size > 2) }

    private val optimalPartition =
        OptimalBiJointPartition(
            possibleJoints = adjacentEdges
                .allPossiblePairs()
                .map { BiJoint(it.first, it.second) },
            edgesAmount = adjacentEdges.size
        )

    private val nonPairedEdge: Segment?
        get() =
        if (hasNonPairedEdge()) {
            adjacentEdges
                .filter { optimalPartition.used(it) }
                .first()
        } else {
            null
        }

    private fun hasNonPairedEdge(): Boolean =
        adjacentEdges.size % 2 == 1

    private val nonPairedJoint: MonoJoint?
        get() = nonPairedEdge
            .let { it?.let { MonoJoint(it) } }

    val optimalJoints: List<Joint> =
        optimalPartition.biJoints + listOfNonPairedJoints()

    fun listOfNonPairedJoints(): List<MonoJoint> =
        if (nonPairedJoint == null) {
            listOf()
        } else {
            listOf(nonPairedJoint!!)
        }

}

fun a() {
    val list = listOf(1, 2, 3) + 5
}
