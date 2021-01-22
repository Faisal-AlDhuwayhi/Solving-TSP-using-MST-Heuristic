#  Solving TSP for Metric Graphs using MST Heuristic
The goal of this project is to build a program that solves the Travelling salesman problem using an optimal solution and compare it with [the approximation solution](https://en.wikipedia.org/wiki/Approximation_algorithm).

## Specification
Given an arbitrary metric graph, construct its Minimum spanning tree using Kruskal's algorithm.
you can assume adjacency matrix representation of graphs. If you wish, you can reuse external
libraries for heaps. Now use the constructed MST to find an approximate estimate for the TSP
problem. You can choose to implement any of the two approximation algorithms specified in [Wikipedia's entry on TSP](https://en.wikipedia.org/wiki/Travelling_salesman_problem) – One with an approximation factor of 1.5 (Christofides) or 2. Compare it
with the optimal answer. You can use some external library to find the optimal solution to the
TSP problem.

## Problem Definition
**Travelling salesman problem** (also called traveling salesperson problem or *TSP*) is an NP-hard problem in combinatorial optimization. TSP problem asks a question: given a list of cities and the
distances between each pair of cities, what is the shortest route that visits each city and returns to the initial city?

## Conclusion
After finishing the experiment, we have come to some conclusions and observations that summarizes the results:

Noticing that the approximation algorithm (Christofide’s algorithm) does not have an exact pattern for the approximation solution (approximation factor), and that depends on such things as the minimum spanning tree of the graph and the Eulerian tour, etc...

However, using the brute-force approach (optimal solution) is not the best choice though, due to the massive growth of the solution time versus the input size.

Finally, we can figure out that the approximation algorithm is a good choice but it’s also a double-edged sword algorithm, since it gives a faster running time but not always giving the correct solution.





- Finally, there are more details and more information about the experiment that can be found in the [**Report**](Report.pdf). Also, the graphs that have been used in the experiments appear inside the [**Experimental graph folder**](The-Experimental-Graph/).