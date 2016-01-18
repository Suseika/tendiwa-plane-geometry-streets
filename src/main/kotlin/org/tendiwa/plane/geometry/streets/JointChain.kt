package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.tendiwa.collections.loopedLinks
import org.tendiwa.graphs.isChain
import org.tendiwa.graphs.trails.trail
import org.tendiwa.graphs.vertices
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.segments.Segment
import org.tendiwa.plane.geometry.segments.commonEndpoint
import org.tendiwa.plane.geometry.segments.otherEnd

internal class JointChain(
    chain: UndirectedGraph<Segment, BiJoint>
) : UndirectedGraph<Segment, BiJoint> by chain {
    init {
        assert(chain.isChain()) { "graph must be a chain" }
    }

    fun toPath(): SegmentPath {
        val start = vertices
            .find { degreeOf(it) == 1 }
            ?: vertices.first()
        val segments: List<Segment> =
            if (vertices.size == 1) {
                listOf(vertices.single())
            } else {
                trail(start).first().toList()
            }
        val beginning = segments.first()
        return when (degreeOf(beginning)) {
            1 -> openChainPath(segments)
            2 -> closedChainPath(segments)
            0 -> SegmentPath(listOf(beginning.start, beginning.end))
            else -> throw IllegalStateException("Report this bug")
        }
    }

    private fun openChainPath(segments: List<Segment>): SegmentPath {
        val connections = segments
            .loopedLinks
            .toList()
            .dropLast(1)
            .map { it.first.commonEndpoint(it.second) }
        val start: Point = segments[0].otherEnd(connections[0])
        val end: Point = segments.last().otherEnd(connections.last())
        return SegmentPath(listOf(start) + connections + listOf(end))
    }

    private fun closedChainPath(segments: List<Segment>): SegmentPath =
        segments
            .loopedLinks
            .map { it.first.commonEndpoint(it.second) }
            .let { SegmentPath(it) }
}
