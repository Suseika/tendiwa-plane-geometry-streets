package org.tendiwa.plane.geometry.streets

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.tendiwa.math.constants.EPSILON
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.points.move
import org.tendiwa.plane.geometry.points.moveHorizontally
import org.tendiwa.plane.geometry.points.segmentTo
import org.tendiwa.tools.expectIllegalArgument

class BiJointTest {
    @JvmField @Rule val expectRule = ExpectedException.none()

    @Test
    fun straightness() {
        val a = Point(0.0, 0.0)
        val b = a.moveHorizontally(10.0)
        val cStraight = b.moveHorizontally(10.0)
        val cNonStraight = b.move(dx = 10.0, dy = 1.0)
        val straight = BiJoint(
            a segmentTo b,
            b segmentTo cStraight
        )
        val nonStraight = BiJoint(
            a segmentTo b,
            b segmentTo cNonStraight
        )
        assert(straight.straightness > nonStraight.straightness)
    }

    @Test
    fun `fails if has more than 1 common endpoint`() {
        expectRule.expectIllegalArgument(
            "Segments must have exactly one common endpoint"
        )
        val a = Point(1.0, 0.0)
        val b = Point(2.0, 2.0)
        BiJoint(a segmentTo b, b segmentTo a)
    }

    @Test
    fun `fails if has no common endpoints`() {
        expectRule.expectIllegalArgument(
            "Segments must have exactly one common endpoint"
        )
        val a = Point(1.0, 0.0)
        val b = Point(2.0, 2.0)
        val c = Point(3.4, 5.6)
        val d = Point(4.1, 4.0)
        BiJoint(a segmentTo b, c segmentTo d)
    }

    @Test
    fun `direction of segment doesnt matter`() {
        val a = Point(1.0, 0.0)
        val b = Point(2.0, 2.0)
        val c = Point(3.4, 5.6)
        val joint1 = BiJoint(a segmentTo b, b segmentTo c)
        val joint2 = BiJoint(a segmentTo b, c segmentTo b)
        Assert.assertEquals(joint1.straightness, joint2.straightness, EPSILON)
    }
}
