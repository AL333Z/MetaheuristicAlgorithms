package com.al333z.GA

import scala.util.Random
import com.al333z.general.Solution
import com.al333z.general.NearestNeighbor
import com.al333z.general.Problem
import com.al333z.general.City
import java.util.Collections
import scala.collection.JavaConverters._

object Individual {
  def crossover(indiv1: Individual, indiv2: Individual): Array[Individual] = {
    val newIndiv: Array[Individual] = Array(
      new Individual(indiv1.problem), new Individual(indiv2.problem))

    var newGenes1 = List[City]()
    var newGenes2 = List[City]()

    // choose two random numbers for the start and end indices of the slice
    val randPoint1 = (new Random).nextInt(indiv1.numberOfGenes - 1)
    val randPoint2 = (new Random).nextInt(indiv1.numberOfGenes)

    // make the smaller the start and the larger the end
    val start = Math.min(randPoint1, randPoint2)
    val end = Math.max(randPoint1, randPoint2)

    // add the sublist in between the start and end points to the children
    newGenes1 = indiv1.genes.slice(start, end).toList
    newGenes2 = indiv2.genes.slice(start, end).toList

    // iterate over each city in the parent paths
    var currentCityIndex = 0
    var currentCityInTour1: City = null
    var currentCityInTour2: City = null

    for (i <- 0 until indiv1.numberOfGenes) {
      // get the index of the current city
      currentCityIndex = (end + i) % indiv1.numberOfGenes

      // get the city at the current index in each of the two parent tours
      currentCityInTour1 = indiv1.genes(currentCityIndex)
      currentCityInTour2 = indiv1.genes(currentCityIndex)

      // if child 1 does not already contain the current city in tour 2, add it
      if (!newGenes1.contains(currentCityInTour2)) {
        newGenes1 = newGenes1 :+ currentCityInTour2
      }

      // if child 2 does not already contain the current city in tour 1, add it
      if (!newGenes2.contains(currentCityInTour1)) {
        newGenes2 = newGenes2 :+ currentCityInTour1
      }
    }

    // rotate the lists so the original slice is in the same place as in the
    // parent tours
    var list1 = newGenes1.asJava
    var list2 = newGenes1.asJava

    Collections.rotate(list1, start)
    Collections.rotate(list2, start)

    newIndiv(0).genes = list1.asScala.toArray
    newIndiv(1).genes = list2.asScala.toArray
    newIndiv
  }
}

class Individual extends Solution {
  var fitness: Int = 0
  var genes: Array[City] = null
  private var problem: Problem = null
  private val rand = new Random
  var numberOfGenes = 0

  def this(problem: Problem) = {
    // super
    this

    this.problem = problem
    this.numberOfGenes = problem.numOfCities
    this.genes = new Array[City](numberOfGenes)
  }

  def init = {
    genes = NearestNeighbor.getSolution(problem, 1).path.toArray
//    genes.foreach { e => println(e.id)  }
  }

  def mutate = {
    val index = rand.nextInt(genes.length)
    val index2 = rand.nextInt(genes.length)

    // implementing mutation as 2-swap
    val temp = genes(index)
    genes(index) = genes(index2)
    genes(index2) = temp
  }

  def evaluate: Int = {
    fitness = (0 until numberOfGenes).foldLeft(0) { (partialSum: Int, i: Int) =>
      if (i == (numberOfGenes - 1)) partialSum + genes(i).dist(genes(0))
      else partialSum + genes(i).dist(genes(i + 1))
    }
    tourLength = fitness
    fitness
  }
}