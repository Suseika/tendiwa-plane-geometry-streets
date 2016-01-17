package org.tendiwa.plane.geometry.streets

import org.tendiwa.plane.geometry.graphs.constructors.Graph2D
import org.tendiwa.plane.geometry.paths.SegmentPath
import org.tendiwa.plane.geometry.shapes.SegmentGroup

fun SegmentGroup.streets(): List<SegmentPath> =
    JointGraph(Graph2D(this).optimalJoints).paths
