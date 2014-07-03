package com.al333z.ACO

import scala.util.Random
import com.al333z.general.Main
import com.al333z.general.NearestNeighbor

object ACO extends Main {

  override def mainLoop = {

    // generate a initial solution, used only to initialize track
    val initialSolution = NearestNeighbor.getSolution(problem, 1)
    initialSolution.printSolution

    // parameters
    val numOfAnts = 10
    val alpha = 0.1d // attr. vs track
    val beta = 2 // track vs attr.
    val numOfCities = problem.numOfCities
    val tau0 = 1.0 / (initialSolution.tourLength * numOfCities) // initial track
    val ro = 0.1d // coef. of evaporation
    val maxIter = 100 // end condition

    // compute solution
    val colony = new AntColony(problem, initialSolution, numOfAnts, problemName,
      beta, alpha, ro, tau0, maxIter)
    val sol = colony.optimize
    sol.printSolution
  }
}