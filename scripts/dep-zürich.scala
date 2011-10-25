import org.jsoup.Jsoup
import scala.collection.JavaConversions._

val cnx = Jsoup.connect("https://reiseauskunft.bahn.de/bin/bhftafel.exe/dn?ld=9667&protocol=https:&rt=1&")
val map = Map[String, String]("GUIREQProduct_0" -> "on",
                              "GUIREQProduct_1" -> "on",
                              "GUIREQProduct_2" -> "on",
                              "GUIREQProduct_3" -> "on",
                              "GUIREQProduct_4" -> "on",
                              "GUIREQProduct_5" -> "on",
                              "GUIREQProduct_6" -> "on",
                              "GUIREQProduct_7" -> "on",
                              "GUIREQProduct_8" -> "on",
                              "REQ0JourneyStopsSID" -> "",    
                              "REQTrain_name" -> "",
                              "advancedProductMode" -> "",
                              "boardType" -> "dep",
                              "date" -> "26.10.2011",
                              "input" -> "Zürich HB",
                              "inputRef" -> "Zürich HB#008503000",
                              "start" -> "Suchen",
                              "time" -> "18:00")
val post = cnx.data(map)
val table = post.post()

for (t <- table.select("td.time") if t.text != "früher" && t.text != "später") { 
    val c = t.parent
    val time = c.select("td.time").text
    val train = c.select("td.train > a").text
    val location = c.select("td.route > span").text
    val stations = c.select("td.route").text.stripPrefix(location).trim.split(" - ")
    println("%s - %s - %s" format(time, train, location))
    println("stations %s" format(stations.mkString("|")))
}
