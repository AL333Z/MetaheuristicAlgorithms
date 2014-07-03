package com.al333z.GA

import scala.util.Random
import com.al333z.general.Solution
import com.al333z.general.NearestNeighbor
import com.al333z.general.Problem
import com.al333z.general.City
import java.util.Collections
import scala.collection.JavaConverters._
import scala.util.control.Breaks._

object Individual {

  // applies crossover to a pair of parents
  def crossover(indiv1: Individual, indiv2: Individual): Individual = {

    // Create new child tour
    val size = indiv1.genes.length
    val child = new Individual(indiv1.problem)
    child.genes = new Array[City](size)

    // get start and end sub tour positions for parent1's tour
    // choose two random numbers for the start and end indices of the slice
    val startPos = (new Random).nextInt(size)
    val endPos = (new Random).nextInt(size)

    // loop and add the sub tour from parent1 to our child
    for (i <- 0 until size) {
      // If our start position is less than the end position
      if (startPos < endPos && i > startPos && i < endPos) {
        child.genes(i) = indiv1.genes(i)
      } // If our start position is larger
      else if (startPos > endPos) {
        if (!(i < startPos && i > endPos)) {
          child.genes(i) = indiv1.genes(i)
        }
      }
    }

    // loop through parent2's city tour
    for (i <- 0 until size) {
      // If child doesn't have the city add it
      if (!child.genes.contains(indiv2.genes(i))) {
        // Loop to find a spare position in the child's tour
        breakable {
          for (ii <- 0 until size) {
            // Spare position found, add city
            if (child.genes(ii) == null) {
              child.genes(ii) = indiv2.genes(i)
              break
            }
          }
        }
      }
    }

    child.evaluate
    child
  }

}

class Individual extends Solution {
  var fitness = 0
  var numberOfGenes = 0
  var genes: Array[City] = null
  private var problem: Problem = null
  private val rand = new Random

  def this(problem: Problem) = {
    // super
    this

    this.problem = problem
    this.numberOfGenes = problem.numOfCities
    this.genes = new Array[City](numberOfGenes)
  }

  def init = {
    genes = NearestNeighbor.getSolution(problem, rand.nextInt(numberOfGenes)).path.toArray
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
    fitness = 0
    for (i <- 0 until numberOfGenes - 1) {
      fitness += genes(i).dist(genes(i + 1))
    }
    fitness += genes(numberOfGenes - 1).dist(genes(0))

    path = genes.toList
    tourLength = fitness
    fitness
  }
}