# The maximum matching problem
Famous problem in the field of Graph thery is the one about finding the maximum matching in a given graph.

## Defenitions

Graph: A structure amounting to a set of objects in which some pairs of the objects are in some sense "related". (Wikipedia)
Vertex: As said, a graph contains objects. this objects called vertices and they store data.
Edge: The idea behind graphs, is to represent the coonection between two vertices. Two vertices in a graph are connected with an edge.
Matching: An independent set of edges, without common vertices.
Maximum matching: A maximum set as described above.


## The problem
To get an intuitive understanding about the max matching lets describe a problem we want to solve.
In one room we have many people that we want to match.
When entering the room we ask from the each participant to write down his\her preferences to match with.

To make the best matching(in quantity aspect) we ask the following question:
what is maximum number of couples that i can create it this room?

## Problem -> Graph
To answer this question we first translate this scenario to a graph. How we are going to do it?
  - People -> Vertices.
  - Preferencess -> Edges.

## The two ways to look this problem
We can divide this problem to two problems. First, Solve this problem on a bipartite graph. Second, on ageneral graph. Bipartite graph is a graph that there is a two independent sets of vertices and there is no edge between vertices in the same set. In our problem, we can think of it as bipartite graph, if we say that we can only match man to women and vice versa.

## The algorithms
For finding a maximum matching in bipartite graph we have implemented the Hungarian method.
And for general graph we have implemented the Edmonds blossom algorithm.
