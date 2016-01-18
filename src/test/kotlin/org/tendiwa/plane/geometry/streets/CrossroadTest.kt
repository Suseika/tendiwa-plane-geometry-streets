package org.tendiwa.plane.geometry.streets

import org.junit.Test
import org.tendiwa.graphs.edges
import org.tendiwa.plane.directions.CardinalDirection.E
import org.tendiwa.plane.directions.CardinalDirection.N
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.graphs.constructors.Graph2D
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.points.spanSegment
import kotlin.test.assertEquals

class CrossroadTest {
    @Test
    fun `produces non-paired joint if number of edges is odd`() {
        val commonPoint = Point(1.2, 3.4)
        val graph = Graph2D(
            setOf(
                commonPoint.spanSegment(NE, 1.0),
                commonPoint.spanSegment(SE, 2.0),
                commonPoint.spanSegment(E, 2.0)
            )
        )
        Crossroad(graph, commonPoint)
            .optimalJoints
            .apply {
                filter { it is MonoJoint }
                    .apply { assertEquals(1, size) }
                filter { it is BiJoint }
                    .apply { assertEquals(1, size) }
            }
    }

    @Test
    fun `produces only bijoints if number of edges is even`() {
        val commonPoint = Point(1.2, 3.4)
        val graph = Graph2D(
            setOf(
                commonPoint.spanSegment(NE, 1.0),
                commonPoint.spanSegment(SE, 2.0),
                commonPoint.spanSegment(E, 2.0),
                commonPoint.spanSegment(N, 2.0)
            )
        )
        Crossroad(graph, commonPoint)
            .optimalJoints
            .apply { assert(all { it is BiJoint }) }
            .apply { assertEquals(graph.edgesOf(commonPoint).size / 2, size) }
    }

    @Test
    fun `optimal joints have distinct segments`() {
        val commonPoint = Point(1.2, 3.4)
        val graph = Graph2D(
            setOf(
                commonPoint.spanSegment(NE, 1.0),
                commonPoint.spanSegment(SE, 2.0),
                commonPoint.spanSegment(E, 2.0)
            )
        )
        assertEquals(
            expected = graph.edges,
            actual = Crossroad(graph, commonPoint)
                .optimalJoints
                .flatMap { it.segments }
                .toSet()
        )
    }
}
