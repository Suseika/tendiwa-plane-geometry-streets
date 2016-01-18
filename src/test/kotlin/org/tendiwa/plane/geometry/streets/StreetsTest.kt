package org.tendiwa.plane.geometry.streets

import org.junit.Test
import org.tendiwa.plane.directions.CardinalDirection.*
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.points.move
import org.tendiwa.plane.geometry.polylines.Polyline
import org.tendiwa.plane.geometry.rectangles.Rectangle
import org.tendiwa.plane.geometry.shapes.SegmentGroup
import kotlin.test.assertEquals

class StreetsTest {
    @Test
    fun `single-segment SegmentGroup is 1 street`() {
        Polyline(
            listOf(
                Point(1.2, 3.5),
                Point(5.2, 3.9)
            )
        )
            .streets()
            .apply { assertEquals(1, size) }
    }

    @Test
    fun `polyline with more than 2 points is 1 street`() {
        Polyline(
            listOf(
                Point(1.2, 3.4),
                Point(1.2, 5.9),
                Point(3.2, 9.8)
            )
        )
            .streets()
            .apply { assertEquals(1, size) }
    }

    @Test
    fun `polygon has 1 street`() {
        Rectangle(10.0, 10.0, 10.0, 10.0)
            .streets()
            .apply { assertEquals(1, size) }
    }

    @Test
    fun `intersecting polylines are 2 streets`() {
        val center = Point(0.0, 0.0)
        val n = center.move(N, 1.0)
        val e = center.move(E, 1.0)
        val s = center.move(S, 1.0)
        val w = center.move(W, 1.0)
        SegmentGroup(
            Polyline(n, center, s),
            Polyline(e, center, w)
        )
            .streets()
            .apply { assertEquals(2, size) }
    }

    @Test
    fun `T shape is two streets`() {
        val center = Point(0.0, 0.0)
        val e = center.move(E, 1.0)
        val s = center.move(S, 1.0)
        val w = center.move(W, 1.0)
        SegmentGroup(
            Polyline(e, center, w),
            Polyline(center, s)
        )
            .streets()
            .apply { assertEquals(2, size) }
    }

    @Test
    fun `P shape is one street`() {
        SegmentPath(
            Point(1.2, 3.4),
            {
                moveX(10.0)
                moveX(1.0)
                moveY(1.0)
                moveX(-1.0)
                moveTo(points[2])
            }
        )
            .streets()
            .apply { assertEquals(1, size) }
    }

}
