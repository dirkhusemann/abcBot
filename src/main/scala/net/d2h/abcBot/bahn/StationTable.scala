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

    /**
     * ViaStation captures stations listed for a connection.
     * 
     * @param name String containing station name
     * @param time String containing listed departure time
     */
    case class ViaStation(name: String, time: String)

    /**
     * ArrivalDeparture is the abstract super class capturing an
     * arrival or departure line listed in a station table.
     * 
     * @param adTime String containing arrival or departure time of
     *               the train
     * @param adTrainType String containg the train type of the train;
     *                    for example, "IR", "IC", "ICE", "TGV"
     * @param adTrain String containing the complete train identifier;
     *                for example, "ICE 104"
     * @param adSourceDestination String containing the original
     *                            source or final destination of the
     *                            train
     * @param adStations List containing ViaStations
     */
    abstract class ArrivalDeparture(val time: String, val trainType: String, val train: String, 
                                    val sourceDestination: String, val stations: List[ViaStation])
    
    /**
     * Arrival captures an arrival line listed in a station table.
     * 
     * @param time String containing arrival time of the train
     * @param trainType String containg the train type of the train;
     *                  for example, "IR", "IC", "ICE", "TGV"
     * @param train String containing the complete train identifier;
     *              for example, "ICE 104"
     * @param sourceLocation String containing the original
     *                       source location of the train
     * @param stations List containing ViaStations
     */
    case class Arrival(arrTime: String, arrTrainType: String, arrTrain: String, sourceLocation: String, arrStations: List[ViaStation])
         extends ArrivalDeparture(arrTime, arrTrainType, arrTrain, sourceLocation, arrStations)

    /**
     * Departure captures a departure line listed in a station table.
     * 
     * @param time String containing departure time of the train
     * @param trainType String containg the train type of the train;
     *                  for example, "IR", "IC", "ICE", "TGV"
     * @param train String containing the complete train identifier;
     *              for example, "ICE 104"
     * @param destinationLocation String containing the final
     *                            destination location of the train
     * @param stations List containing ViaStations
     */
    case class Departure(depTime: String, depTrainType: String, depTrain: String, destinationLocation: String, depStations: List[ViaStation]) 
         extends ArrivalDeparture(depTime, depTrainType, depTrain, destinationLocation, depStations)


    class StationTable(station: String, date: String) { 
        var arrivals: Map[String, List[Arrival]] = Map()
        var departures: Map[String, List[Departure]] = Map()

        def +=(arrival: Arrival): StationTable = { 
            arrivals(arrival.time) = arrival :: arrivals.getOrElse(arrival.time, Nil) 
            this
        }

        def +=(departure: Departure): StationTable = { 
            departures(departure.time) = departure :: departures.getOrElse(departure.time, Nil)
            this
        }
    }

    object StationTable { 
        
    }
}
