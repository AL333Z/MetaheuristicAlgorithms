package com.al333z.general

/**
 * A Problem contains all the cities, the number of cities, a name and the
 * optimal tour length.
 */
class Problem(val name: String, val numOfCities: Int, val opt: Int) {
  var cities: List[City] = List()

  def updateDistances = {
    cities.foreach { c =>
      cities.foreach { d =>
        if (!c.knows(d)) {
          c.setDist(d)
          d.setDist(c)
        }
      }
    }
  }

  def printCoordinates = cities.foreach {
    c => println("City: " + c.id + " x: " + c.x + " y: " + c.y)
  }
}