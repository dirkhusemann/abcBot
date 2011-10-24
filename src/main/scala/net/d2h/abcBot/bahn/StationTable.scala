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

    import scala.collection.mutable.Map
    import org.joda.time.{LocalTime, LocalDate, DateTime}

    /**
     * ViaStation captures stations listed for a connection.
     * 
     * @param name String containing station name
     * @param time String containing listed departure time
     */
    case class ViaStation(name: String, time: LocalTime)

    /**
     * ArrivalDeparture is the abstract super class capturing an
     * arrival or departure line listed in a station table.
     * 
     * @param time LocalTime object containing arrival or departure time of
     *             the train
     * @param trainType String containg the train type of the train;
     *                  for example, "IR", "IC", "ICE", "TGV"
     * @param train String containing the complete train identifier;
     *              for example, "ICE 104"
     * @param sourceDestination String containing the original
     *                          source or final destination of the
     *                          train
     * @param stations List containing ViaStations
     */
    abstract class ArrivalDeparture(val time: LocalTime, val trainType: String, val train: String, 
                                    val sourceDestination: String, val stations: List[ViaStation])
    
    /**
     * Arrival captures an arrival line listed in a station table.
     * 
     * @param arrTime LocalTime containing arrival time of the train
     * @param arrTrainType String containg the train type of the train;
     *                     for example, "IR", "IC", "ICE", "TGV"
     * @param arrTrain String containing the complete train identifier;
     *                 for example, "ICE 104"
     * @param arrSourceLocation String containing the original
     *                       source location of the train
     * @param arrStations List containing ViaStations
     */
    case class Arrival(arrTime: LocalTime, arrTrainType: String, arrTrain: String, sourceLocation: String, arrStations: List[ViaStation])
         extends ArrivalDeparture(arrTime, arrTrainType, arrTrain, sourceLocation, arrStations)

    /**
     * Departure captures a departure line listed in a station table.
     * 
     * @param depTime LocalTime containing departure time of the train
     * @param depTrainType String containg the train type of the train;
     *                     for example, "IR", "IC", "ICE", "TGV"
     * @param depTrain String containing the complete train identifier;
     *                 for example, "ICE 104"
     * @param depDestinationLocation String containing the final
     *                               destination location of the train
     * @param depStations List containing ViaStations
     */
    case class Departure(depTime: LocalTime, depTrainType: String, depTrain: String, destinationLocation: String, depStations: List[ViaStation]) 
         extends ArrivalDeparture(depTime, depTrainType, depTrain, destinationLocation, depStations)


    /**
     * StationTable class for capturing departure and arrival
     * information for a station.
     * @param station String containing the station name
     * @param date DateTime object containing the date for which to
     *             retrieve the station table
     */
    class StationTable(station: String, date: LocalDate) { 
        /**
         * Map containing LocalTime -> List[Arrival] mappings which
         * each list containing the arrivals for a given time.
         */
        var arrivals: Map[LocalTime, List[Arrival]] = Map()
        /**
         * Map containing LocalTime -> List[Departure] mappings which
         * each list containing the departures for a given time.
         */
        var departures: Map[LocalTime, List[Departure]] = Map()

        /**
         * Add an Arrival to the StationTable.
         * @param arrival Arrival object to add
         * @return reference to self for chaining
         */
        def +=(arrival: Arrival): StationTable = { 
            arrivals(arrival.time) = arrival :: arrivals.getOrElse(arrival.time, Nil) 
            this
        }

        /**
         * Add a Departure to the StationTable.
         * @param departure Departure object to add
         * @return reference to self for chaining
         */
        def +=(departure: Departure): StationTable = { 
            departures(departure.time) = departure :: departures.getOrElse(departure.time, Nil)
            this
        }
    }

    object StationTable { 
        
    }
}
