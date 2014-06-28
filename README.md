AI - Unibo 2014
===============

Implementing 2 metaheuristic algorithms to solve the [assignment problem](http://en.wikipedia.org/wiki/Assignment_problem).
The metaheuristic used are:
- [GA](http://en.wikipedia.org/wiki/Genetic_algorithm)
- [ACO](http://en.wikipedia.org/wiki/Ant_colony_optimization_algorithms)

Assignment problem
------------------

There are currently 12 data files, taken from [the OR-Library](https://files.nyu.edu/jeb21/public/jeb/orlib/assigninfo.html).

These data files are taken from J.E.Beasley
"*Linear programming on Cray supercomputers*" Journal
of the Operational Research Society 41 (1990) 133-139.

Eight problems are from Table 2 of that paper.

The following table gives the relationship between test
problem set in Table 2 and the appropriate files:

```
Problem set        Files
100                assign100
200                assign200
...                ...
```
The format of these data files is:
```
number of items to be assigned (n)
for each item i (i=1,...,n):
the cost of allocating item i to item j (c(i,j),j=1,...,n)
```

Four problems are from Table 3 of that paper.

The following table gives the relationship between test
problem set in Table 3 and the appropriate files:
```
Problem set        Files
800                assignp800
1500               assignp1500
...                ...
```

The format of these data files is:
```
number of items to be assigned (n)
for each valid assignment:
i, j, c(i,j) the cost of allocating item i to item j
```

All the instances are in the `instances` folder.
The value of the optimal solution for each of these data
files is given in the file `instances/assignopt`.
