package org.tendiwa.plane.geometry.streets

import org.tendiwa.plane.directions.CardinalDirection.*
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.holeygons.Holeygon
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.trails.Polygon


fun TwoHoleHoleygon(): Holeygon =
    Holeygon(
        enclosing = Polygon(
            Point(10.0, 10.0),
            {
                move(100.0, E)
                move(60.0, NE)
                move(80.0, E)
                move(80.0, NW)
                move(40.0, N)
                move(40.0, NE)
                move(60.0, NW)
                move(50.0, W)
                move(50.0, SW)
            }
        ),
        holes = listOf(
            Polygon(
                Point(95.0, 220.0),
                {
                    move(20.0, E)
                    move(30.0, S)
                    move(50.0, SW)
                }
            ),
            Polygon(
                Point(108.0, 77.0),
                {
                    move(40.0, N)
                    move(35.0, SE)
                }
            )
        )
    )

