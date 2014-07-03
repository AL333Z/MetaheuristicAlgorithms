package com.al333z.general

object NearestNeighbor {
  def getSolution(problem: Problem, starting: Int): Solution = {

    val solution = new Solution
    val size = problem.numOfCities

    solution.path = problem.cities(starting) +: List()
    solution.tourLength = 0
    for (i <- 0 until size - 1) {
      val nearest = solution.path.last.getNearest(problem, solution)
      solution.tourLength += solution.path.last.dist(nearest)
      solution.path = solution.path :+ nearest
    }
    solution.tourLength += solution.path.last.dist(solution.path.head)
    solution
  }
}