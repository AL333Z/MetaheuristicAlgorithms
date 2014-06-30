package com.al333z.GA

import scala.util.Random

object Individual {
  def crossover(indiv1: Individual, indiv2: Individual): Array[Individual] = {
    val newIndiv: Array[Individual] = Array(
      new Individual(indiv1.numberOfGenes, indiv1.costs),
      new Individual(indiv1.numberOfGenes, indiv1.costs))
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

class Individual(val numberOfGenes: Int, val costs: Array[Array[Int]]) {
  var fitness: Int = 0
  var genes: Array[Int] = new Array[Int](numberOfGenes)
  private val rand = new Random

  /*
   * random solution
   * assigning each items randomly
   */
  def randGenes = {

    // helper function to remove an element in a list
    def dropIndex[T](list: List[T], idx: Int): List[T] =
      list.zipWithIndex.filter(_._2 != idx).map(_._1)

    // helper function to randomly assign items
    def randomAssignment(notAssigned: Array[Int]): Array[Int] = notAssigned match {
      case Array() => Array()
      case assigned => {
        val elemIdx = rand.nextInt(notAssigned.length)
        val newNotAssigned = dropIndex(notAssigned.toList, elemIdx).toArray
        assigned ++ (notAssigned(elemIdx) +: randomAssignment(newNotAssigned))
      }
    }
    genes = randomAssignment((0 until numberOfGenes).toArray)
  }

  /*
   * best solution, with an exact algorithm
   * need to adapt cost matrix to the structure accepted by the algorithm
   */
  def optIndividual = {
    println("Compute best solution")
    val hungarianAlg = new HungarianAlgorithm
    val trasposedCosts = costs.transpose.map { _.map { _.toFloat } }
    val sol = hungarianAlg.computeAssignments(trasposedCosts)
    //    sol.foreach { l => println("Item " + l(1) + " assigned to " + l(0)) }
    genes = sol.map(_(0))
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
      partialSum + costs(i)(genes(i)) // cost to assign i-item to genes(i)-agent
    }
    fitness
  }
}