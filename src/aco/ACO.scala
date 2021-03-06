package aco

import scala.util.Random
import general.Main
import general.NearestNeighbor

object ACO extends Main {

  override def mainLoop = {

    // generate a initial solution, used only to initialize track
    val initialSolution = NearestNeighbor.getSolution(problem, 1)
    //    initialSolution.printSolution

    // parameters
    val numOfAnts = 20
    val alpha = 0.1d // attr. vs track
    val beta = 2 // track vs attr.
    val numOfCities = problem.numOfCities
    val tau0 = 1.0 / (initialSolution.tourLength * numOfCities) // initial track
    val ro = 0.5d // coef. of evaporation
    val maxIter = 100 // end condition

    // compute solution
    val colony = new AntColony(problem, numOfAnts, problemName,
      beta, alpha, ro, tau0, maxIter)
    val sol = colony.optimize
    sol.printSolution
  }
}