// Copyright © 2011 Dirk Husemann. All rights reserved.

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
    abstract class ArrivalDeparture(val adTime: String, val adTrainType: String, val adTrain: String, 
                                    val adSourceDestination: String, val adstations: List[ViaStation])
    
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
    case class Arrival(time: String, trainType: String, train: String, sourceLocation: String, stations: List[ViaStation])
         extends ArrivalDeparture(time, trainType, train, sourceLocation, stations)

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
    case class Departure(time: String, trainType: String, train: String, destinationLocation: String, stations: List[ViaStation])
         extends ArrivalDeparture(time, trainType, train, destinationLocation, stations)


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
