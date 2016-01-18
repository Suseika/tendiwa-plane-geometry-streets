package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.tendiwa.graphs.SimpleGraphWithoutAutoEdges
import org.tendiwa.graphs.connectivity.connectivityComponents
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.segments.Segment

internal class JointGraph(
    joints: List<Joint>
) : UndirectedGraph<Segment, BiJoint> by SimpleGraphWithoutAutoEdges()
{
    init {
        joints.forEach {
            it.intergrateInto(this)
        }
    }

    val paths: List<SegmentPath>
        get() = connectivityComponents()
            .map { JointChain(it).toPath() }
}
