package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.tendiwa.plane.geometry.segments.Segment

internal data class MonoJoint(val segment: Segment) : Joint {
    override val segments: List<Segment>
        get() = listOf(segment)

    override fun intergrateInto(graph: UndirectedGraph<Segment, BiJoint>) {
        graph.addVertex(segment)
    }
}
