package general

import java.io.File
import java.util.regex.Pattern

trait Main extends App {

  // main loop, implemented by algorithms
  def mainLoop: Unit = ???

  var problem: Problem = null
  var problemName: String = null
  var problemDimension: Int = 0
  var problemKnownOpt: Int = 0

  // data
  val problemFiles = (new File("instances/")).listFiles()

  for (i <- 0 until problemFiles.length) {
    val currentInstance = i

    // parsing input file to an array of numbers
    val input = problemFiles(currentInstance)
    val source = scala.io.Source.fromFile(input)
    val lines = source.getLines.toArray
    source.close()
    //  lines foreach println

    def parseData(tag: String) = lines.filter(_.contains(tag))(0).split(": ")(1)

    problemName = parseData("NAME")
    problemDimension = parseData("DIMENSION").toInt
    problemKnownOpt = parseData("BEST_KNOWN").toInt

    println("*****************************************************************")
    println(s"Inst: $problemName Size: $problemDimension Opt: $problemKnownOpt")

    // instance current problem
    problem = new Problem(problemName, problemDimension, problemKnownOpt)

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

}