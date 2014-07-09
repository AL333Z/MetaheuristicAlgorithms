package com.al333z.general

/**
 * A solution contains a list of cities (a path), and a tour length (cost).
 */
class Solution {
  var path: List[City] = List()
  var tourLength: Int = 0

  def printSolution = {
    println("Completed with tour lenght: " + tourLength)
    println("Solution: ")
    println(path.map(_.id).mkString(" -> "))
  }
}