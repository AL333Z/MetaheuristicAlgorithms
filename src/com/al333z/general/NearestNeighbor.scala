package com.al333z.general

object NearestNeighbor {
  //TODO something is not working here!!!
  def getSolution(problem: Problem, starting: Int): Solution = {

    val solution = new Solution
    val size = problem.numOfCities
    var tourLength = 0
    solution.path = solution.path :+ problem.cities(starting)
    for (i <- 0 until size) {
      //TODO check here
      val nearest = solution.path.last.getNearest(problem, solution)
      tourLength += solution.path.last.dist(nearest)
      solution.path = solution.path :+ nearest
    }
    tourLength += solution.path.last.dist(solution.path.head)
    solution.tourLength = tourLength
    solution
  }
}