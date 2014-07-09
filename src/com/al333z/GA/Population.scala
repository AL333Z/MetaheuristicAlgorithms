package com.al333z.GA

import scala.util.Random
import com.al333z.general.Problem

class Population(val problem: Problem, val populationSize: Int) {

  private val rand = new Random
  private val numberOfGenes = problem.numOfCities
  private val cities = problem.cities

  var population = new Array[Individual](populationSize)

  // init population with one NN individual
  population(0) = new Individual(problem)
  population(0).init

  // and other random individual
  for (i <- 1 until populationSize) {
    population(i) = new Individual(problem)
    population(i).initRandom
  }

  // evaluate current population
  evaluate

  def evaluate = population.foreach(_.evaluate)

  def rouletteWheelSelection: Individual = {
    var randNum = rand.nextDouble
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

    for (idx <- 0 until populationSize) {
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

    population(idxMin) // minimization
    //    population(idxMax) // maximization
  }
}