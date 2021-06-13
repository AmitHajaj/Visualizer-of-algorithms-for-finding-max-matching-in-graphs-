# The maximum matching problem
Famous problem in the field of Graph theory is the one about finding the maximum matching in a given graph.

## Defenitions

Graph: A structure amounting to a set of objects in which some pairs of the objects are in some sense "related". (Wikipedia) <br/>
Vertex: As said, a graph contains objects. this objects called vertices and they store data.<br/>
Edge: The idea behind graphs, is to represent the coonection between two vertices. Two vertices in a graph are connected with an edge.<br/>
Matching: An independent set of edges, without common vertices.<br/>
Maximum matching: A maximum set as described above.


## The problem
To get an intuitive understanding about the maximum matching lets describe a problem we want to solve.<br/>
In one room we have many people that we want to match.<br/>
When entering the room we ask each participant to write down his\her preferences to match with.<br/>

To make the best matching(in quantity aspect) we ask the following question:<br/>
what is maximum number of couples that i can create it this room?<br/>

## Problem -> Graph
To answer this question we first translate this scenario to a graph. How we are going to do it?<br/>
  - People -> Vertices.<br/>
  - Preferencess -> Edges.<br/>

## The two ways to look this problem
We can divide this problem to two problems. First, Solve this problem on a bipartite graph. Second, on ageneral graph. Bipartite graph is a graph that there is a two independent sets of vertices and there is no edge between vertices in the same set. In our problem, we can think of it as bipartite graph, if we say that we can only match man to women and vice versa.

## The algorithms
For finding a maximum matching in bipartite graph we have implemented the Hungarian method.<br/>
And for general graph we have implemented the Edmonds blossom algorithm.
