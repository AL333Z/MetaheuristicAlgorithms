package com.al333z.GA

import scala.util.Random
import com.al333z.general.Main
import com.al333z.general.NearestNeighbor

object GA extends Main {

  override def mainLoop = {
    val rand = new Random

    val initialSolution = NearestNeighbor.getSolution(problem, 0)
    //    initialSolution.printSolution

    // parameters
    val populationSize: Int = 20 // population size
    val maxIter: Int = 100 // max number of iterations
    val mutationRate: Double = 0.05 // probability of mutation
    val crossoverRate: Double = 0.7 // probability of crossover

    // initial population
    val pop = new Population(problem, populationSize)

    // main loop
    val t0 = System.nanoTime()
    for (
      i <- 0 to maxIter if (pop.findBestIndividual.fitness > problemKnownOpt) &&
        (System.nanoTime() - t0) / 60000000000l < 2
    ) {

      // build new Population
      val newPop = new Array[Individual](populationSize)
      (0 until populationSize).by(2).foreach { count: Int =>
        // Selection
        val parent1 = pop.rouletteWheelSelection
        val parent2 = pop.rouletteWheelSelection

        var child1 = parent1
        var child2 = parent2

        // Crossover
        if (rand.nextDouble < crossoverRate) {
          child1 = Individual.crossover(parent1, parent2)
          child2 = Individual.crossover(parent2, parent1)
        }

        // Mutation
        if (rand.nextDouble < mutationRate) {
          child1.mutate
        }
        if (rand.nextDouble < mutationRate) {
          child2.mutate
        }

        // add to new population
        newPop(count) = child1
        newPop(count + 1) = child2
      }

      // evaluate current population
      pop.population = newPop
      pop.evaluate

      //      println("Best Fitness = " + pop.findBestIndividual.fitness)
    }

    // best indiv
    val sol = pop.findBestIndividual
    sol.printSolution
  }

}
