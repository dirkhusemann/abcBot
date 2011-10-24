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

        "This is a specification to check the StationTable class"     ^
                                                                      p^
        "A StationTable should"                                       ^
            "start out empty"                                         ! freshStationTable().isEmpty ^
            "accept and provide an Arrival object for a certain time" ! arrivalAdd().isFaithful ^
                                                                      end


        trait ArrivalSamples { 
            val arr0000 = Arrival(new LocalTime("00:00"), "S", "S2", "Pfäffikon (SZ)", 
                                  ViaStation("Thalwil", new LocalTime("23:23")) :: 
                                  ViaStation("Pfäffikon (SZ)", new LocalTime("23:28")) :: Nil)            
        }
                                                  
        trait DepartureSamples { 
            val dep0000 = Departure(new LocalTime("00:00"), "S", "S2", "Pfäffikon SZ", 
                                    ViaStation("Thalwil", new LocalTime("00:25")) :: 
                                    ViaStation("Horgen", new LocalTime("00:28")) :: 
                                    ViaStation("Wädenswil", new LocalTime("00:32")) :: Nil)            
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

        case class arrivalAdd() extends CleanStationTable with ArrivalSamples with DepartureSamples { 
            stationTable += arr0000
            stationTable += dep0000
            
            def isFaithful = { 
                (stationTable.arrivals   must haveKey(new LocalTime("00:00"))) and
                (stationTable.departures must haveKey(new LocalTime("00:00")))
            }
                             
        }
    }
}
