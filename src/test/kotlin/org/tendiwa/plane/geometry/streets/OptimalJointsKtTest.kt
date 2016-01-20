package org.tendiwa.plane.geometry.streets

import org.junit.Test
import org.tendiwa.plane.geometry.graphs.constructors.Graph2D
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.polylines.Polyline
import org.tendiwa.plane.geometry.rectangles.Rectangle
import org.tendiwa.plane.geometry.segments.Segment
import kotlin.test.assertEquals

class OptimalJointsKtTest {
    @Test
    fun `polygon graph has 1 joint per corner`() {
        val polygon = Rectangle(10.0, 10.0, 10.0, 10.0)
        Graph2D(polygon)
            .optimalJoints
            .apply { assertEquals(polygon.points.size, size) }
    }

    @Test
    fun `polyline graph has 1 joint per 2-degree point plus 2 monojoints`() {
        val polyline = Polyline(Point(0.0, 0.0), {
            moveX(1.0)
            moveY(1.0)
            moveY(1.0)
            moveX(1.0)
        })
        Graph2D(polyline)
            .optimalJoints
            .apply {
                assertEquals(
                    polyline.points.size - 2,
                    filter { it is BiJoint }.size
                )
                assertEquals(
                    2,
                    filter { it is MonoJoint }.size
                )
            }
    }

    @Test
    fun `dipper graph has monojoints on ends and bijoints in other places`() {
        val dipper = SegmentPath(
            Point(1.2, 3.4),
            {
                moveX(10.0)
                moveX(1.0)
                moveY(1.0)
                moveX(-1.0)
                moveTo(points[2])
            }
        )
        Graph2D(dipper)
            .optimalJoints
            .apply {
                assertEquals(
                    2,
                    filter { it is MonoJoint }.size
                )
                assertEquals(
                    dipper.points.size - 2,
                    filter { it is BiJoint }.size
                )
            }
    }

    @Test
    fun `single segment graph has single monojoint`() {
        Graph2D(
            Segment(
                Point(1.2, 3.4),
                Point(1.3, 4.5)
            )
        )
            .optimalJoints
            .apply { assert(single() is MonoJoint) }
    }
}
