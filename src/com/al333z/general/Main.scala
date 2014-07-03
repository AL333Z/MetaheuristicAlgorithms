package com.al333z.general

import java.io.File
import java.util.regex.Pattern

trait Main extends App {

  // main loop, implemented by algorithms
  def mainLoop: Unit = ???

  // data
  val problemFiles = (new File("instances/")).listFiles()

  //TODO launch all instances, not only the first one )
  val currentInstance = 0

  // parsing input file to an array of numbers
  val input = problemFiles(currentInstance)
  val source = scala.io.Source.fromFile(input)
  val lines = source.getLines.toArray
  source.close()
  //  lines foreach println

  def parseData(tag: String) = lines.filter(_.contains(tag))(0).split(": ")(1)

  val problemName = parseData("NAME")
  val problemDimension = parseData("DIMENSION").toInt
  val problemKnownOpt = parseData("BEST_KNOWN").toInt

  println(s"Inst: $problemName Size: $problemDimension Opt: $problemKnownOpt")

  // instance current problem
  val problem = new Problem(problemName, problemDimension, problemKnownOpt)

  // parse cities and distances
  val citiesStr = lines.dropWhile(s => !s.contains("NODE_COORD_SECTION")).drop(1)
  val patternLine = Pattern.compile("(\\d+) (.+?) (.+?)$")
  for (line <- citiesStr if !line.contains("EOF")) {
    val regex = patternLine.matcher(line)
    if (regex.find()) {
      val id = regex.group(1).toInt
      val x = regex.group(2).toDouble
      val y = regex.group(3).toDouble
      val city = new City(id, x, y, problemDimension)
      problem.cities = problem.cities :+ city
    }
  }

  // let it know each city how far is it to each other
  problem.updateDistances

  // launch computation implemented by subclasses
  mainLoop
}