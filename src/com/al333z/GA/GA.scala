package com.al333z.GA

import scala.util.Random
import com.al333z.general.Main
import com.al333z.general.NearestNeighbor

object GA extends Main {

  override def mainLoop = {
    val rand = new Random

    val initialSolution = NearestNeighbor.getSolution(problem, 1)
    initialSolution.printSolution
    println(initialSolution.path.map(_.id).mkString(" "))

    // parameters
    val populationSize: Int = 20 // population size
    val maxIter: Int = 100 // max number of iterations
    val mutationRate: Double = 0.05 // probability of mutation
    val crossoverRate: Double = 0.7 // probability of crossover

    // initial population
    val pop = new Population(problem, populationSize)

    // main loop
    for (
      i <- 0 to maxIter if (pop.findBestIndividual.fitness > problemKnownOpt)
    ) {

      // build new Population
      val newPop = new Array[Individual](populationSize)
      (0 until populationSize).by(2).foreach { count: Int =>
        // Selection
        var indiv = new Array[Individual](2)
        indiv(0) = pop.rouletteWheelSelection
        indiv(1) = pop.rouletteWheelSelection

        // Crossover
        if (rand.nextDouble < crossoverRate) {
          indiv = Individual.crossover(indiv(0), indiv(1))
        }

        // Mutation
        if (rand.nextDouble < mutationRate) {
          indiv(0).mutate
        }
        if (rand.nextDouble < mutationRate) {
          indiv(1).mutate
        }

        // add to new population
        newPop(count) = indiv(0)
        newPop(count + 1) = indiv(1)
      }

      // evaluate current population
      pop.population = newPop
      pop.evaluate

      println("\tBest Fitness = " + pop.findBestIndividual.fitness)
    }

    // best indiv
    val sol = pop.findBestIndividual
    sol.printSolution
  }

}
