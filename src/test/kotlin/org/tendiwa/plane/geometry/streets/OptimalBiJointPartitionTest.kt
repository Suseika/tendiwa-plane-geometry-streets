package org.tendiwa.plane.geometry.streets

import org.junit.Test
import org.tendiwa.math.sets.allPossiblePairs
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.points.spanSegment
import kotlin.test.assertEquals

class OptimalBiJointPartitionTest {
    @Test
    fun `finds optimal joints amont odd number of joints`() {
        val center = Point(1.2, 3.4)
        val ne = center.spanSegment(NE, 1.0)
        val nw = center.spanSegment(NW, 1.0)
        val sw = center.spanSegment(SW, 1.0)
        OptimalBiJointPartition(
            listOf(
                BiJoint(ne, nw),
                BiJoint(ne, sw),
                BiJoint(nw, sw)
            ),
            edgesAmount = 3
        )
            .biJoints
            .apply { assertEquals(1, size) }
    }

    @Test
    fun `finds optimal joints among even number of joints`() {
        val center = Point(1.2, 3.4)
        val ne = center.spanSegment(NE, 1.0)
        val nw = center.spanSegment(NW, 1.0)
        val se = center.spanSegment(SE, 1.0)
        val sw = center.spanSegment(SW, 1.0)
        OptimalBiJointPartition(
            possibleJoints = listOf(ne, nw, se, sw)
                .allPossiblePairs()
                .map { BiJoint(it.first, it.second) },
            edgesAmount = 4
        )
            .biJoints
            .apply { assertEquals(2, size) }
    }

    @Test
    fun `partition of odd number of segments leaves one segment unused`() {
        val center = Point(1.2, 3.4)
        val ne = center.spanSegment(NE, 1.0)
        val nw = center.spanSegment(NW, 1.0)
        val sw = center.spanSegment(SW, 1.0)
        val partition = OptimalBiJointPartition(
            listOf(
                BiJoint(ne, nw),
                BiJoint(ne, sw),
                BiJoint(nw, sw)
            ),
            edgesAmount = 3
        )
        listOf(ne, nw, sw)
            .filter { !partition.used(it) }
            .apply { assertEquals(1, size) }
    }

    @Test
    fun `partition of even number of segments leaves no segments unused`() {
        val center = Point(1.2, 3.4)
        val ne = center.spanSegment(NE, 1.0)
        val nw = center.spanSegment(NW, 1.0)
        val se = center.spanSegment(SE, 1.0)
        val sw = center.spanSegment(SW, 1.0)
        val partition = OptimalBiJointPartition(
            possibleJoints = listOf(ne, nw, se, sw)
                .allPossiblePairs()
                .map { BiJoint(it.first, it.second) },
            edgesAmount = 4
        )
        listOf(ne, nw, se, sw)
            .filter { !partition.used(it) }
            .apply { assertEquals(0, size) }
    }
}
