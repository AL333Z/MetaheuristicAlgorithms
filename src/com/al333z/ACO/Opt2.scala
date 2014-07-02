package com.al333z.ACO

import com.al333z.general.Solution
import java.util.ListIterator


object Opt2 {
  def optimize(solution: Solution, firstImprovement: Boolean) = {
    val fitness = solution.tourLength
    var best_gain = -1
    var final_gain = 0
    var first_improvement = firstImprovement
    val path = solution.path

    		while (best_gain != 0) {
    			best_gain = 0
    			val bestc = path.head
    			var bestb = null
    			var last = path.last
    			
    			
    			
    			
//    			val itr = path.listIterator(path.indexOf(bestc))
//    			itr.add(path.getLast())
    //	        while (itr.hasNext()) {	
    //	            final City a = itr.previous()
    //	            itr.next()
    //	            final City b = itr.next()
    //	            
    //	            if (last == b || a == b) { break}
    //	            
    //	            ListIterator<City> itr2 = path.listIterator(path.indexOf(b)+2)
    //	            while (itr2.hasNext()) {	
    //	            	final City c = itr2.previous()
    //		            itr2.next()
    //		            final City d = itr2.next()
    //		            int gain = (d.dist(b) + c.dist(a)) - (a.dist(b) + c.dist(d))
    //		            if (gain < best_gain) {
    //		            	best_gain = gain
    //		            	bestb = b
    //		            	bestc = c
    //		            	if (first_improvement) break
    //		            }
    //	            }
    //	            if (best_gain < 0 && first_improvement) break
    //	        }
    //	        path.remove(path.getFirst())
    //	        final_gain += best_gain
    //	        if (bestb != null && bestc != null) {
    //		        int posb = path.indexOf(bestb)
    //		        int posc = path.indexOf(bestc)
    //		        List<City> l = path.subList(Math.min(posc, posb), Math.max(posc, posb)+1)
    //		        Collections.reverse(l)
    //	       }
    		}
    		solution.tourLength = fitness+final_gain
  }
}