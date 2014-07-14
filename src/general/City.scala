package general

/**
 * A city is modeled with an id, coordinates (x,y) and an array
 * of distances to other cities.
 */
class City(val id: Int, val x: Double, val y: Double, val numOfCities: Int) {

  var dist: Array[Int] = new Array(numOfCities)

  def setDist(c: City) = {
    if (id != c.id) {
      val dx = this.x - c.x
      val dy = this.y - c.y
      dist(c.id - 1) = Math.round(Math.sqrt(dx * dx + dy * dy).toFloat)
    } else dist(c.id - 1) = Int.MaxValue // dist from the same city 
  }

  def dist(c: City): Int = {
    dist(c.id - 1)
  }

  def knows(c: City): Boolean = (dist(c.id - 1) != 0)

  def getNearest(prob: Problem, sol: Solution): City = {
    var nearest: City = null
    var min = Int.MaxValue
    val remainingCities = prob.cities.filterNot(sol.path.contains(_))

    for (i <- (0 until prob.cities.size)) {
      if (remainingCities.contains(prob.cities(i))) {
        val c = prob.cities(i)
        val tmp = dist(i)
        if (min > tmp) {
          min = tmp
          nearest = c
        }
      }
    }
    nearest
  }
}