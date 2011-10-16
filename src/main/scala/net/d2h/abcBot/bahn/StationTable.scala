// Copyright © 2011 Dirk Husemann. All rights reserved.

package net.d2h.abcBot.bahn { 

    case class Station(location: String, time: String)

    abstract class ArrivalDeparture(val adTime: String, val adTrainType: String, val adTrain: String, 
                                    val adSourceDestination: String, val adstations: List[Station])

    case class Arrival(time: String, trainType: String, train: String, sourceLocation: String, stations: List[Station])
         extends ArrivalDeparture(time, trainType, train, sourceLocation, stations)

    case class Departure(time: String, trainType: String, train: String, destinationLocation: String, stations: List[Station])
         extends ArrivalDeparture(time, trainType, train, destinationLocation, stations)
}
