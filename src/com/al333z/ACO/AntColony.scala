package com.al333z.ACO

import com.al333z.general.Problem
import com.al333z.general.Solution
import scala.collection.mutable.LinkedList

/**
 * An AntColony coordinates all the ants, and stops the algorithm if the optimal
 * solution is found. All the variables of the ant colony system are set here.
 */
class AntColony(problem: Problem, solution: Solution, numOfAnts: Int,
  title: String, val beta: Int, val alpha: Double, val ro: Double,
  val tau0: Double, val maxtIter: Int) {

  val numOfCities = problem.numOfCities
  var bestSolutionEver: Ant = null
  var pheromone = (new Array[Array[Double]](numOfCities)).map(_ => new Array[Double](numOfCities))
  val cities = problem.cities
  private var ants = new LinkedList[Ant]

  def initTaus() = {
    pheromone = pheromone.map { arr => arr.map(_ => tau0) }
  }

  def optimize: Solution = {
    initTaus()
    var iter = 0
    while (iter < maxtIter &&
      (bestSolutionEver == null || bestSolutionEver.tourLength > problem.opt)) {

      //add numOfAnts new ants
      for (i <- 0 until numOfAnts) {
        ants = ants :+ (new Ant(this))
      }

      // ants are already in the first city, so there are numOfCities - 1 moves
      for (i <- 0 until numOfCities - 1) {
        ants.foreach { ant =>
          val nextCity = ant.nextCity
          ant.moveTo(nextCity)
        }
      }

      //find local best Ant
      var minL = ants(0).tourLength
      var bestAnt = ants(0)

      ants.foreach { ant =>
        val tmp = ant.tourLength
        if (tmp <= minL) {
          bestAnt = ant
          minL = tmp
        }
      }

      //update pheromone of best ant (ever)
      if (bestSolutionEver == null) globalTrailUpdate(bestAnt)
      else globalTrailUpdate(bestSolutionEver)

      //if it is the case, update the best ant (solution) ever
      if (bestSolutionEver == null ||
        bestAnt.tourLength < bestSolutionEver.tourLength) {
        bestSolutionEver = bestAnt
//        println("Best tour so far: " + bestSolutionEver.tourLength)
      }

      ants = new LinkedList[Ant]
      iter += 1
    }

    bestSolutionEver
  }

  def globalTrailUpdate(ant: Ant) {
    val constant = 1.0 / ant.tourLength.toDouble

    var prev = ant.path.last
    ant.path.foreach { current =>
      val currentTau = pheromone(prev.id - 1)(current.id - 1)
      val newTau = (1 - alpha) * currentTau + alpha * (constant)

      pheromone(prev.id - 1)(current.id - 1) = newTau
      pheromone(current.id - 1)(prev.id - 1) = newTau

      prev = current
    }
  }

}