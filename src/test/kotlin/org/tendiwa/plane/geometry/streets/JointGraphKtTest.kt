package org.tendiwa.plane.geometry.streets

import org.junit.Test
import org.tendiwa.graphs.connectivity.connectivityComponents
import org.tendiwa.graphs.vertices
import org.tendiwa.plane.directions.CardinalDirection.*
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.points.move
import org.tendiwa.plane.geometry.points.segmentTo
import org.tendiwa.plane.geometry.segments.Segment
import kotlin.test.assertEquals

class JointGraphKtTest {
    @Test
    fun `joint graph is a forest of chains`() {
        val center = Point(1.2, 3.4)
        val a = center.move(E, 1.0)
        val b = center.move(N, 1.0)
        val c = center.move(W, 1.0)
        val d = center.move(S, 1.0)
        JointGraph(
            listOf(
                BiJoint(a segmentTo center, center segmentTo b),
                BiJoint(c segmentTo center, center segmentTo d),
                MonoJoint(Segment(Point(10.0, 20.0), Point(30.0, 40.0)))
            )
        )
            .connectivityComponents()
            .apply {
                assert(
                    all { component ->
                        component.vertices.all { component.degreeOf(it) <= 2 }
                    }
                )
            }
    }

    @Test
    fun `joint graph connects overlapping joints to chains`() {
        val a = Point(1.2, 3.4)
        val b = a.move(N, 1.0)
        val c = b.move(N, 1.0)
        val d = c.move(N, 1.0)
        JointGraph(
            listOf(
                BiJoint(a segmentTo b, c segmentTo b),
                BiJoint(c segmentTo b, d segmentTo c)
            )
        )
            .paths
            .apply { assertEquals(1, size) }
    }
}
