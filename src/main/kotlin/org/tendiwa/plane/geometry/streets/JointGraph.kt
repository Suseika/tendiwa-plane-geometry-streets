package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.jgrapht.graph.SimpleGraph
import org.tendiwa.graphs.connectivity.connectivityComponents
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.segments.Segment

// TODO: Make this a class that delegates to SimpleGraph
internal fun JointGraph(
    joints: List<Joint>
): UndirectedGraph<Segment, BiJoint> =
    SimpleGraph<Segment, BiJoint>({ a, b -> throw IllegalAccessError() })
        .apply {
            joints.forEach {
                it.intergrateInto(this)
            }
        }

internal val UndirectedGraph<Segment, BiJoint>.paths: List<SegmentPath>
    get() = connectivityComponents()
        .map { JointChain(it).toPath() }



