package com.al333z.GA

import scala.util.Random

object Individual {
  def crossover(indiv1: Individual, indiv2: Individual): Array[Individual] = {
    val newIndiv: Array[Individual] = Array(new Individual(indiv1.numberOfGenes, indiv1.costs), new Individual(indiv1.numberOfGenes, indiv1.costs))
    val randPoint = (new Random).nextInt(indiv1.numberOfGenes)

    (0 until randPoint).foreach { i: Int =>
      newIndiv(0).genes(i) = indiv1.genes(i)
      newIndiv(1).genes(i) = indiv2.genes(i)
    }

    (randPoint until indiv1.numberOfGenes).foreach { i: Int =>
      newIndiv(0).genes(i) = indiv2.genes(i)
      newIndiv(1).genes(i) = indiv1.genes(i)
    }

    newIndiv
  }
}

class Individual(val numberOfGenes: Int, val costs: Seq[Array[Int]]) {
  var fitness: Int = 0
  var genes: Array[Int] = new Array[Int](numberOfGenes)
  private val rand = new Random

  // TODO consider putting this out
  def randGenes = {
    // initialize genes randomly
    genes = genes.map(_ => rand.nextInt(numberOfGenes))
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
    fitness = (0 until genes.length).foldLeft(0) { (partialSum: Int, i: Int) =>
      partialSum + costs(i)(genes(i)) // cost to assign i-item to genes(i)-agent
    }
    fitness
  }
}