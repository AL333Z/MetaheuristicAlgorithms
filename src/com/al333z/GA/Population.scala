package com.al333z.GA

import scala.util.Random

class Population(val populationSize: Int, val numberOfGenes: Int, costs: Array[Array[Int]]) {

  private val rand = new Random
  var totalFitness = 0.0
  var population = new Array[Individual](populationSize)

  //TODO put next in constructor?

  // init population
  (0 until populationSize).foreach { i: Int =>
    population(i) = new Individual(numberOfGenes, costs)
    population(i).randGenes
  }

  // evaluate current population
  evaluate

  def evaluate: Double = {
    totalFitness = population.foldLeft(0) { (partialSum: Int, individual: Individual) =>
      partialSum + individual.evaluate
    }
    totalFitness
  }

  def rouletteWheelSelection: Individual = {
    var randNum = rand.nextDouble * totalFitness
    var idx = 0
    while (idx < populationSize && randNum > 0) {
      randNum -= population(idx).fitness
      idx += 1
    }

    population(idx - 1)
  }

  def findBestIndividual: Individual = {
    var idxMax = 0
    var idxMin = 0
    var currentMax = 0.0
    var currentMin = 1.0
    var currentVal = 0

    (0 until populationSize).foreach { idx =>
      currentVal = population(idx).fitness
      if (currentMax < currentMin) {
        currentMax = currentVal
        currentMin = currentVal
        idxMax = idx
        idxMin = idx
      }

      if (currentVal > currentMax) {
        currentMax = currentVal
        idxMax = idx
      }

      if (currentVal < currentMin) {
        currentMin = currentVal
        idxMin = idx
      }
    }

    population(idxMin)      // minimization
//    population(idxMax) // maximization
  }
}