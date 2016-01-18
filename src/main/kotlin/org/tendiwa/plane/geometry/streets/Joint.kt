package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.tendiwa.plane.geometry.segments.Segment

internal interface Joint {
    val segments: List<Segment>

    fun intergrateInto(graph: UndirectedGraph<Segment, BiJoint>)
}
