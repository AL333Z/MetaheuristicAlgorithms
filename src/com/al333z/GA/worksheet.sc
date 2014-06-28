package com.al333z.GA

object worksheet {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  // parsing input file to an array of numbers

  val source = "4 52 89 40 77 52 89 40 77 "       //> source  : String = "4 52 89 40 77 52 89 40 77 "
  val lines = source.mkString("\n").split(Array(' ', '\n')).
    filter(_ != "").map(Integer.parseInt(_))      //> lines  : Array[Int] = Array(4, 5, 2, 8, 9, 4, 0, 7, 7, 5, 2, 8, 9, 4, 0, 7, 
                                                  //| 7)

  // extract the number of items to assign
  val n = lines(0)                                //> n  : Int = 4

  val costs = lines.drop(1)                       //> costs  : Array[Int] = Array(5, 2, 8, 9, 4, 0, 7, 7, 5, 2, 8, 9, 4, 0, 7, 7)

  val costsTable = for {
    start <- (0 until costs.length).by(2)
    costPerItem <- costs.drop(start).take(n)
  } yield costPerItem                             //> costsTable  : scala.collection.immutable.IndexedSeq[Int] = Vector(5, 2, 8, 9
                                                  //| , 8, 9, 4, 0, 4, 0, 7, 7, 7, 7, 5, 2, 5, 2, 8, 9, 8, 9, 4, 0, 4, 0, 7, 7, 7,
                                                  //|  7)

  costsTable foreach println                      //> 5
                                                  //| 2
                                                  //| 8
                                                  //| 9
                                                  //| 8
                                                  //| 9
                                                  //| 4
                                                  //| 0
                                                  //| 4
                                                  //| 0
                                                  //| 7
                                                  //| 7
                                                  //| 7
                                                  //| 7
                                                  //| 5
                                                  //| 2
                                                  //| 5
                                                  //| 2
                                                  //| 8
                                                  //| 9
                                                  //| 8
                                                  //| 9
                                                  //| 4
                                                  //| 0
                                                  //| 4
                                                  //| 0
                                                  //| 7
                                                  //| 7
                                                  //| 7
                                                  //| 7
}