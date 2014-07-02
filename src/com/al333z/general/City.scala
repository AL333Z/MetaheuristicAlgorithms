package com.al333z.general

/**
 * A city is modeled with an id, coordinates (x,y) and an array
 * of distances to other cities.
 */
class City(val id: Int, val x: Double, val y: Double, val numOfCities: Int) {

  var dist: Array[Int] = new Array(numOfCities)

  def setDist(c: City) = {
    val dx = this.x - c.x
    val dy = this.y - c.y
    dist(c.id - 1) = Math.round(Math.sqrt(dx * dx + dy * dy).toFloat)
  }

  def dist(c: City): Int = dist(c.id - 1)

  def knows(c: City): Boolean = (dist(c.id - 1) != 0)

  def getNearest(prob: Problem, sol: Solution): City = {
    var nearest = prob.cities(0)
    var min = dist(0)
    
    for (i <- (1 until prob.cities.size)) {
      val c = prob.cities(i)
      val tmp = dist(i)
      if (min > tmp && !sol.path.contains(c) && c != nearest) {
        min = tmp
        nearest = c
      }
    }
    nearest
  }
}