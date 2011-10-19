ABCBot
======

_ABCBot_ expands train time tables.

license
=======

ABCBot is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

ABCBot is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License in the LICENSE file for more details.

You should have received a copy of the GNU General Public License
along with ABCBot.  If not, see <http://www.gnu.org/licenses/>.


links used
==========

station names
-------------

    https://reiseauskunft.bahn.de/bin/ajax-getstop.exe/dn?REQ0JourneyStopsS0A=1&REQ0JourneyStopsS0G=


departure & arrival tables
--------------------------

POST to 

    https://reiseauskunft.bahn.de/bin/bhftafel.exe/dn?ld=9667&protocol=https:&rt=1&

for example: (application/x-www-form-urlencoded)

    GUIREQProduct_0 on
    GUIREQProduct_1 on
    GUIREQProduct_2 on
    GUIREQProduct_3 on
    GUIREQProduct_4 on
    GUIREQProduct_5 on
    GUIREQProduct_6 on
    GUIREQProduct_7 on
    GUIREQProduct_8 on
    REQ0JourneyStopsSID     
    REQTrain_name   
    advancedProductMode     
    boardType       arr
    date    Do, 13.10.11
    input   Zürich HB
    inputRef        Zürich HB#008503000
    start   Suchen
    time    00:00

source:

    input=Z%FCrich+HB&inputRef=Z%FCrich+HB%23008503000&date=Do%2C+13.10.11&time=00%3A00&boardType=arr&REQTrain_name=&advancedProductMode=&GUIREQProduct_0=on&GUIREQProduct_1=on&GUIREQProduct_2=on&GUIREQProduct_3=on&GUIREQProduct_4=on&GUIREQProduct_5=on&GUIREQProduct_6=on&GUIREQProduct_7=on&GUIREQProduct_8=on&start=Suchen

boardType can be either

* `dep` — for the departure table, or
* `arr` — for the arrival table
