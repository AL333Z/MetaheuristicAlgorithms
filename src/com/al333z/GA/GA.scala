package com.al333z.GA

import scala.util.Random

object GA extends App {

  // parsing opt results for all tests
  val optSource = scala.io.Source.fromFile("instances/assignopt.txt")
  val optLines = optSource.getLines.drop(1).filter(_ != "").toArray.map {
    a =>
      val instanceName = a.takeWhile(_ != ' ')
      val instanceOpt = a.drop(instanceName.length()).dropWhile((_ == ' '))
      (instanceName, instanceOpt)
  }
  optSource.close()

  //  optLines.foreach {
  //    case (name, opt) => println("name: " + name + "\t\topt: " + opt)
  //  }

  //TODO launch all instances, not only the first one ;)
  val currentInstance = 0

  // parsing input file to an array of numbers
  val input = "instances/" + optLines(currentInstance)._1 + ".txt"
  val source = scala.io.Source.fromFile(input)
  val lines = source.getLines.mkString("\n").split(Array(' ', '\n')).
    filter(_ != "").map(Integer.parseInt(_))
  source.close()

  // extract the number of items to assign
  val n = lines(0)

  // build costs table
  // each elements is an array, containing costs associated to each item
  // costsTable(i)(j) is the cost for i-item and j-agent
  val costs = lines.drop(1)
  val costsTable = for {
    start: Int <- (0 until n)
    costPerItem: Array[Int] = costs.drop(start * n).take(n)
  } yield costPerItem

  //  costsTable.foreach(ci => println(ci.mkString("\t")))

  val rand = new Random

  // init initial population  
  val populationSize: Int = 2 // population size
  //  val maxIterations: Int = 2000 // max number of iterations
  val maxIterations: Int = 200 // max number of iterations

  //  val mutationRate: Double = 0.05 // probability of mutation
  //  val crossoverRate: Double = 0.7 // probability of crossover
  val mutationRate: Double = 1 // probability of mutation
  val crossoverRate: Double = 1 // probability of crossover

  // init initial population
  val pop = new Population(populationSize, n, costsTable.toArray)
  val newPop = new Array[Individual](populationSize)
  var indiv = new Array[Individual](2)

  val optIndividual = new Individual(n, costsTable.toArray)
  optIndividual.optIndividual

  // current population
  print("Instance = " + optLines(currentInstance)._1)
  print("\tTotal fitness = " + pop.totalFitness)
  print("\tOpt = " + optLines(currentInstance)._2)
  print("\tOpt individual fitness = " + optIndividual.evaluate)
  println("\tFitness of initial population= " + pop.findBestIndividual.fitness)

  // main loop
  var count = 0
  (0 until maxIterations).foreach { _ =>

    // build new Population
    (0 until populationSize).by(2).foreach { count: Int =>
      // Selection
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

    pop.population = newPop

    // reevaluate current population
    pop.evaluate

    print("Total Fitness = " + pop.totalFitness)
    println("\tBest Fitness = " + pop.findBestIndividual.fitness)
  }

  // best indiv
  val bestIndiv = pop.findBestIndividual
  println("Completed with Best Fitness = " + pop.findBestIndividual.fitness)

}
