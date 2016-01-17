package org.tendiwa.plane.geometry.streets

import org.tendiwa.plane.geometry.segments.Segment
import java.util.*

internal class OptimalBiJointPartition(
    possibleJoints: List<BiJoint>,
    edgesAmount: Int
) {
    private val usedEdges: Set<Segment>
    val biJoints: List<BiJoint>

    init {
        usedEdges = HashSet<Segment>(edgesAmount)
        biJoints = ArrayList<BiJoint>(edgesAmount / 2)
        for (joint in possibleJoints.sortedByDescending { it.straightness }) {
            if (!usedEdges.contains(joint.a) && !usedEdges.contains(joint.b)) {
                biJoints.add(joint)
                usedEdges.add(joint.a)
                usedEdges.add(joint.b)
            }
        }
    }

    fun used(edge: Segment): Boolean = usedEdges.contains(edge)
}
