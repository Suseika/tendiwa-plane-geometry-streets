package org.tendiwa.plane.geometry.streets

import org.jgrapht.UndirectedGraph
import org.tendiwa.plane.directions.DirectionFan
import org.tendiwa.plane.directions.measure
import org.tendiwa.plane.geometry.points.directionTo
import org.tendiwa.plane.geometry.segments.Segment
import org.tendiwa.plane.geometry.segments.commonEndpoint
import org.tendiwa.plane.geometry.segments.otherEnd
import org.tendiwa.tools.argumentsConstraint

internal data class BiJoint(
    val a: Segment,
    val b: Segment
) : Joint {

    /**
     * The greater the closer the angle is to Math.PI radians.
     */
    val straightness: Double

    init {
        argumentsConstraint(
            setOf(a.start, b.start, a.end, b.end).size == 3,
            {
                "Segments must have exactly one common endpoint; " +
                    "segments are $a and $b "
            }
        )
        straightness =
            Math.PI * 2 - Math.abs(directionFan().measure.radians - Math.PI)
    }

    override val segments: List<Segment>
        get() = listOf(a, b)

    override fun intergrateInto(graph: UndirectedGraph<Segment, BiJoint>) {
        graph.addVertex(a)
        graph.addVertex(b)
        graph.addEdge(a, b, this)
    }

    private fun directionFan(): DirectionFan =
        a.commonEndpoint(b)
            .let { common ->
                DirectionFan(
                    cw = common directionTo a.otherEnd(common),
                    ccw = common directionTo b.otherEnd(common)
                )
            }

}
