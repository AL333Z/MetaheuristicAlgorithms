package com.al333z.ACO

import com.al333z.general.Solution
import scala.util.Random
import com.al333z.general.City

class Ant extends Solution {

  private val rnd = new Random
  var citiesToVisit: List[City] = null
  var taus: Array[Array[Double]] = null
  var colony: AntColony = null
  var size = 0

  def this(col: AntColony) = {
    // super
    this

    colony = col
    citiesToVisit = colony.cities
    size = colony.numOfCities
    taus = colony.pheromone

    moveTo(citiesToVisit(rnd.nextInt(size)), true)
  }

  def moveTo(city: City, start: Boolean = false) = {
    if (!start) {
      localUpdate(path.last, city)
      tourLength += path.last.dist(city)
    }
    path = path :+ city

    // remove element form list
    citiesToVisit = citiesToVisit.filterNot(_.id == city.id)

    if (citiesToVisit.isEmpty) {
      tourLength += city.dist(path.head)
      localUpdate(city, path.head)
    }
  }

  private def localUpdate(current: City, city: City) = {
    val newTau = (1 - colony.ro) * getTau(current, city) +
      colony.ro * colony.tau0
    taus(current.id - 1)(city.id - 1) = newTau
    taus(city.id - 1)(current.id - 1) = newTau
  }

  def nextCity: City = {
    var max: City = citiesToVisit(0)
    var last = -1.0

    citiesToVisit.foreach { c =>
      val tmp = (getTau(path.last, c) *
        Math.pow(getEta(path.last, c), colony.beta)).toDouble
      if (tmp > last) {
        last = tmp
        max = c
      }
    }
    max
  }

  def getEta(a: City, b: City): Double = {
    1.0 / a.dist(b).toDouble
  }

  def getTau(a: City, b: City): Double = {
    taus(a.id - 1)(b.id - 1)
  }

}