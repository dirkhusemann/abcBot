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
    
    import org.joda.time.{LocalDate, LocalTime}

    import org.specs2._

    class StationTableSpec extends Specification { def is =

        "This is a specification to check the StationTable class"             ^
                                                                              p^
        "A StationTable should"                                               ^
            "start out empty"                                                 ! freshStationTable().isEmpty ^
            "accept and provide an Arrival object for a certain time"         ! arrivalDepartureAdd().isFaithful ^
            "return a list of departure objects with the same departure time" ! multipleDeparturesSameTimes().returnsList ^
            "return a list of departure objects with the same departure time" ! multipleArrivalSameTimes().returnsList ^
                                                                              end


        trait ArrivalSamples { 
            val arr0000a = Arrival(new LocalTime("00:00"), "S", "S2", "Pfäffikon (SZ)", 
                                   ViaStation("Thalwil", new LocalTime("23:23")) :: 
                                   ViaStation("Pfäffikon (SZ)", new LocalTime("23:28")) :: Nil)            
            val arr0000b = Arrival(new LocalTime("00:00"), "TGV", "TGV 26", "Paris Gare du Lyon", 
                                   ViaStation("Basel", new LocalTime("22:57")) :: 
                                   ViaStation("Paris Gare du Lyon", new LocalTime("20:03")) :: Nil)            
        }
                                                  
        trait DepartureSamples { 
            val dep0000a = Departure(new LocalTime("00:00"), "S", "S2", "Pfäffikon SZ", 
                                     ViaStation("Thalwil", new LocalTime("00:25")) :: 
                                     ViaStation("Horgen", new LocalTime("00:28")) :: 
                                     ViaStation("Wädenswil", new LocalTime("00:32")) :: Nil)
            val dep0000b = Departure(new LocalTime("00:00"), "ICE", "ICE 123", "Hamburg", 
                                     ViaStation("Stuttgart", new LocalTime("02:30")) :: 
                                     ViaStation("Frankfurt", new LocalTime("04:28")) :: 
                                     ViaStation("Hamburg", new LocalTime("08:32")) :: Nil)
        }

        trait CleanStationTable { 
            val stationTable = new StationTable("Zürich HB", new LocalDate("2011-10-24"))
        }
                                                  

        case class freshStationTable() extends CleanStationTable { 
            def isEmpty = { 
                (stationTable.arrivals.size mustEqual 0) and
                (stationTable.departures.size mustEqual 0)
            }
        }

        case class arrivalDepartureAdd() extends CleanStationTable with ArrivalSamples with DepartureSamples { 
            stationTable += arr0000a
            stationTable += dep0000a
            
            def isFaithful = { 
                (stationTable.arrivals   must haveKey(new LocalTime("00:00"))) and
                (stationTable.departures must haveKey(new LocalTime("00:00")))
            }
        }
                                                  
        case class multipleDeparturesSameTimes() extends CleanStationTable with DepartureSamples { 
            stationTable += dep0000a
            stationTable += dep0000b

            def returnsList = { 
                (stationTable.departures(new LocalTime("00:00")) must contain(dep0000a, dep0000b)) and
                (stationTable.departures.size mustEqual 1)
                (stationTable.departures(new LocalTime("00:00")).size mustEqual 2)
            }
        }

        case class multipleArrivalSameTimes() extends CleanStationTable with ArrivalSamples { 
            stationTable += arr0000a
            stationTable += arr0000b

            def returnsList = { 
                (stationTable.arrivals(new LocalTime("00:00")) must contain(arr0000a, arr0000b)) and 
                (stationTable.arrivals.size mustEqual 1) and 
                (stationTable.arrivals(new LocalTime("00:00")).size mustEqual 2)
            }
        }
    }
}
