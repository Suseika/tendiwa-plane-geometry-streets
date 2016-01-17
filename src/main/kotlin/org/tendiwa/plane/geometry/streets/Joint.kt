package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.tendiwa.plane.geometry.segments.Segment

internal interface Joint {
    fun intergrateInto(graph: UndirectedGraph<Segment, BiJoint>)
}
