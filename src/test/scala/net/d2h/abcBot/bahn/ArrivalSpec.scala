// Copyright © 2011 Dirk Husemann. All Rights Reserved.

// This file is part of ABCBot.
// 
// ABCBot is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// ABCBot is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with ABCBot.  If not, see <http://www.gnu.org/licenses/>.

package net.d2h.abcBot.bahn {

    import org.specs2._

    class ArrivalSpec extends Specification { def is = 

        "This is a specification to check the Arrival class" ^
                                                             p^
        "An Arrival object should"                           ^
            "capture arrival time information"               ! arrival().isCaptured ^
                                                             end


        trait ArrivalSample { 
            val arr = Arrival("00:00", "S", "S2", "Zürich", ViaStation("Thalwil", "23:23") :: ViaStation("Horgen", "23:28") :: Nil)            
        }

        case class arrival() extends ArrivalSample { 
            def isCaptured =
                (arr.time mustEqual "00:00") and 
                (arr.trainType mustEqual "S") and 
                (arr.train mustEqual "S2") and
                (arr.sourceLocation mustEqual "Zürich") and
                (arr.stations.size mustEqual 2)
        }
    }

    class DepartureSpec extends Specification { def is = 

        "This is a specification to check the Departure class" ^
                                                               p^
        "An Departure object should"                           ^
            "capture departure time information"               ! departure().isCaptured ^
                                                               end


        trait DepartureSample { 
            val dep = Departure("00:00", "S", "S2", "Pfäffikon SZ", ViaStation("Thalwil", "00:25") :: ViaStation("Horgen", "00:28") :: 
                                ViaStation("Wädenswil", "00:32") :: Nil)            
        }

        case class departure() extends DepartureSample { 
            def isCaptured =
                (dep.time mustEqual "00:00") and 
                (dep.trainType mustEqual "S") and 
                (dep.train mustEqual "S2") and
                (dep.destinationLocation mustEqual "Pfäffikon SZ") and
                (dep.stations.size mustEqual 3)
        }
    }
}
