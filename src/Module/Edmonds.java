package Module;
import org.jgrapht.graph.*;

import java.util.*;

public class Edmonds {

    /**
     * Given a simple graph, this method will find the maximum matching on this graph.
     *
     * References:
     *  -This algorithm was taken from the pseudo code found here:
     *      https://en.wikipedia.org/wiki/Blossom_algorithm
     *
     *  -D. B. West, Introduction to graph theory, Prentice Hall, Upper Saddle River, NJ, 1996.
     *
     *
     * @param g The graph we work on.
     * @return A graph withe same vertices as g, and only the matching edges.
     */
    public static SimpleGraph<Integer, DefaultEdge> findMaximumMatching(SimpleGraph<Integer, DefaultEdge> g) {
        // Empty graph.
        if (g.vertexSet().size() == 0)
            return new SimpleGraph<>(DefaultEdge.class);

        // Construct a new graph that will hold the matching.
        // Get every node g has, but without the edges.
        SimpleGraph<Integer, DefaultEdge> result = new SimpleGraph<>(DefaultEdge.class);
        for (int node: g.vertexSet())
            result.addVertex(node);

        // Now, we will check over and over to see if there is an alternating path.
        while (true){
            // Get the path
            List<Integer> path = findAlternatingPath(g, result);
            //ig the path return null, it means that no alternating path is exist.
            if (path == null) return result;


            // If we did found one, update the current matching.
            updateMatch(path, result);
        }
    }

    /**
     * Updates the given matching by making Path XOR m.
     * Example:
     *        m: 0---1===2---3
     *        Path: (0, 1), (2,3)
     *
     *        return: 0===1---2===3
     *
     * @param path An alternating path.
     * @param m The matching we will update.
     */
    private static void updateMatch(List<Integer> path, SimpleGraph<Integer, DefaultEdge> m) {
        // As described, we will flip the edges.
        for (int i = 0; i < path.size() - 1; ++i) {
            //Check whether the edge exists.
            if (m.getEdge(path.get(i), path.get(i + 1)) != null)
                m.removeEdge(path.get(i), path.get(i + 1));
            else
                m.addEdge(path.get(i), path.get(i + 1));
        }
    }

    // As described in the algorithm, we need to maintain a forest
    // that keeps track on the growing paths of the explored nodes.
    // For this we will use this structure to keep track on 3 things:
    //      1. who is my parent
    //      2. who is my root node
    //      3. am I an outer or inner node.

    private static final class NodeInformation{
        public final int parent;
        public final int treeRoot;
        public final boolean isOuter; // True for outer node, false for inner.
        public final int value;
        
        public NodeInformation(int parent, int treeRoot, boolean isOuter, int value) {
            this.parent = parent;
            this.treeRoot = treeRoot;
            this.isOuter = isOuter;
            this.value = value;
        }
    }

    // The last structure is the one of the blossom. 
    // When finding an odd cycle we need to contract it to one node
    // and when we find an alternating path for it we ne to extract the blossom and adjust the path.
    // To this purpose we hold in this structure:
    //      - The start point of the blossom.(the stem of the flower)
    //      - The blossom nodes.(in order)
    //      - The blossom nodes.(without order)
    
    private static final class Blossom{
        public final int stem; 
        public final List<Integer> cycle; 
        public final Set<Integer> nodes;
        
        public Blossom(int stem, List<Integer> cycle, Set<Integer> nodes) {
            this.stem = stem;
            this.cycle = cycle;
            this.nodes = nodes;
        }
    }

    /**
     * Given a graph and a matching in that graph, returns an augmenting path.
     *
     * @param g The graph we work on.
     * @param m A matching.
     * @return An alternating path g, or null if there is one.
     */
    private static List<Integer> findAlternatingPath(SimpleGraph<Integer, DefaultEdge> g,
                                                   SimpleGraph<Integer, DefaultEdge> m) {
        // The forest we will maintain.
        Map<Integer, NodeInformation> forest = new HashMap<>();

        //This queue will hold the edges that not yet explored.
        //maintain somehow bfs traverse.
        Queue<DefaultEdge> edgesToExplore = new LinkedList<>();

        // Add each free vertex to the forest as a singleton.
        for (int node: g.vertexSet()) {
            //It is a singleton only if his neighbors are not matched.
            if (!edgesFrom(node, m).isEmpty())
                continue;

            // Add to the forest.
            forest.put(node, new NodeInformation(-1, node, true, node));

            // Add edges to explore for this node.
            for (DefaultEdge neighbor: g.edgesOf(node))
                edgesToExplore.add(neighbor);
        }

        // Start to explore each node and maintain its tree.
        while (!edgesToExplore.isEmpty()) {
            //get edge to explore.
            DefaultEdge curr = edgesToExplore.remove();
            if (m.getEdge(g.getEdgeSource(curr), g.getEdgeTarget(curr)) != null)
                continue;


            int starter, finisher;
            // Take this edge source and target.
            // The condition are to adjust the source and target to fit.
            if(!forest.containsKey(g.getEdgeSource(curr))){
                starter = g.getEdgeTarget(curr);
                finisher = g.getEdgeSource(curr);
            }
            else {
                starter = g.getEdgeSource(curr);
                finisher = g.getEdgeTarget(curr);
            }
            NodeInformation startInfo = forest.get(starter);
            NodeInformation endInfo = forest.get(finisher);

            //Here we now need to choose from 3 options.
            if (endInfo != null) {
                // Option 1: Found an odd blossom. Contract the blossom.
                if (endInfo.isOuter && startInfo.treeRoot == endInfo.treeRoot) {

                    Blossom blossom = findBlossom(forest, startInfo.value, endInfo.value);

                    // Now we will build the graph with new super vertex.
                    // And search for an alternating path on it.
                    List<Integer> path = findAlternatingPath(contractGraph(g, blossom),
                            contractGraph(m, blossom));

                    // If didn't find any path, there isn't any.
                    if (path == null) return path;

                    // If we did found one, expend the blossom and set the new path.
                    return expandPath(path, g, forest, blossom);
                }
                // Option 2: Return the augmenting path from root to root. With the paths in each tree.
                else if (endInfo.isOuter && startInfo.treeRoot != endInfo.treeRoot) {

                    List<Integer> result = new ArrayList<>();

                    //First node to root.
                    for (int node = endInfo.value; node != -1; node = forest.get(node).parent)
                        result.add(node);

                    result = reversePath(result);

                    // Second node to root.
                    for (int node =  startInfo.value; node != -1; node = forest.get(node).parent)
                        result.add(node);

                    return result;
                }
            }
            // If we got here it means that the node we got is on the matching.
            // At this case we add it to the tree with is descendant.
            // Both under the root of the calling node.
            else {
                forest.put(finisher, new NodeInformation(startInfo.value,
                        startInfo.treeRoot,
                        false, finisher));

                // endPoint is the descendant of the node who made us problem.
                int endPoint = edgesFrom(finisher, m).iterator().next();
                forest.put(endPoint, new NodeInformation(finisher,
                        startInfo.treeRoot,
                        true, endPoint));

                // Add the endPoint neighbors to explore list.
                for (DefaultEdge endPNeighbor : g.edgesOf(endPoint))
                    edgesToExplore.add(endPNeighbor);
            }
        }

        //If we got here, no augmenting possible.
        return null;
    }

    /**
     * Suspected blossom.
     * Compute it info.
     *
     * @param forest The forest.
     * @param start The start node of the blossom.
     * @param end The end node of the the blossom.
     * @return A Blossom struct holding information about then blossom.
     */
    private static Blossom findBlossom(Map<Integer, NodeInformation> forest,
                                              int start, int end) {
        //To find the root we will find the paths from start and end to their common parent.
        // Where they will meet will be the blossom's root.
        LinkedList<Integer> onePath = new LinkedList<>(), twoPath = new LinkedList<>();
        for (int node = start; node != -1; node = forest.get(node).parent)
            onePath.addFirst(node);
        for (int node = end; node != -1; node = forest.get(node).parent)
            twoPath.addFirst(node);


        int conflict = 0;
        for (; conflict < onePath.size() && conflict < twoPath.size(); ++conflict)
            if (onePath.get(conflict) != twoPath.get(conflict))
                break;

        // When we found were they have a conflict, we can restore to blossom.
        List<Integer> cycle = new ArrayList<>();
        for (int i = conflict - 1; i < onePath.size(); ++i)
            cycle.add(onePath.get(i));
        for (int i = twoPath.size() - 1; i >= conflict - 1; --i)
            cycle.add(twoPath.get(i));

        return new Blossom(onePath.get(conflict - 1), cycle, new HashSet<Integer>(cycle));
    }

    /**
     * return a contracted graph for a given blossom.
     *
     * @param g The graph.
     * @param B The blossom.
     * @return The contraction g
     */
    private static SimpleGraph<Integer, DefaultEdge> contractGraph(SimpleGraph<Integer, DefaultEdge> g, Blossom B) {
        SimpleGraph<Integer, DefaultEdge> result = new SimpleGraph<>(DefaultEdge.class);

        // Add all nodes that are not in the blossom.
        for (int node: g.vertexSet()) {
            if (!B.nodes.contains(node))
                result.addVertex(node);
        }

        // Add the super vertex to the graph.
        result.addVertex(B.stem);

        // Add edges not around the blossom.
        for (int node: g.vertexSet()){
            //Skip blossom nodes.
            if (B.nodes.contains(node))
                continue;

           //explore all the neighbor of this node.
            for (int endPoint : edgesFrom(node, g)) {
                // Adjust edge to the super vertex
                if (B.nodes.contains(endPoint))
                    endPoint = B.stem;

                result.addEdge(node, endPoint);
            }
        }

        return result;
    }

    /**
     * Set the proper path based on a blossom and a graph.
     *
     * @param path The path in the contracted graph.
     * @param g The original graph.
     * @param forest The forest.
     * @param blossom The blossom.
     * @return An alternating path.
     */
    private static List<Integer> expandPath(List<Integer> path, SimpleGraph<Integer, DefaultEdge> g, Map<Integer, NodeInformation> forest, Blossom blossom) {

        int index = path.indexOf(blossom.stem);

        if (index == -1) return path;

        // If it is at an odd index we will reverse the path.
        if (index % 2 == 1)
            path = reversePath(path);

        // Start expanding the path.
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < path.size(); ++i) {
            //If this one not in the blossom, add it normally.
            if (path.get(i) != blossom.stem) {
                result.add(path.get(i));
            }
            // Handle the blossom.
            else {
                // Add the blossom stem to the path. it will be there.
                result.add(blossom.stem);

                // Find a node on the cycle the has an edge outside.
                int outNode = findNodeLeavingCycle(g, blossom, path.get(i + 1));

                // Check for it index on the blossom.
                int outIndex = blossom.cycle.indexOf(outNode);


                int start = (outIndex % 2 == 0)? 1 : blossom.cycle.size() - 2;
                int step  = (outIndex % 2 == 0)? +1 : -1;


                for (int k = start; k != outIndex + step; k += step)
                    result.add(blossom.cycle.get(k));
            }
        }
        return result;
    }

    /**
     * Reverse a given path.
     * @param path The path we want to reverse.
     * @return The reverse of that path.
     */
    private static List<Integer> reversePath(List<Integer> path) {
        List<Integer> result = new ArrayList<>();

        for (int i = path.size() - 1; i >= 0; --i)
            result.add(path.get(i));

        return result;
    }

    /**
     * Find a node on the blossom that go to the given node.
     *
     * @param g The graph.
     * @param B The blossom.
     * @param node The node outside.
     * @return Some node on the blossom.
     */
        private static int findNodeLeavingCycle(SimpleGraph<Integer, DefaultEdge> g, Blossom B, int node) {
            // Check the blossom nodes for match.
            for (int cycleNode: B.nodes)
                if (g.getEdge(cycleNode, node) != null)
                    return cycleNode;

            return -1;
    }

    /**
     * Find the neighbors of a given node.
     * @param node to search it neighbors.
     * @param g graph to work on.
     * @return Set of integers describes the neighbors of node.
     */
    private static Set<Integer> edgesFrom(int node, SimpleGraph<Integer, DefaultEdge> g){
            HashSet<Integer> neighbors = new HashSet<>();

        Set<DefaultEdge> edgesNeighbors =  g.edgesOf(node);

        for(DefaultEdge edge : edgesNeighbors){
            if(g.getEdgeSource(edge) != node){
                neighbors.add(g.getEdgeSource(edge));
            }
            else{
                neighbors.add(g.getEdgeTarget(edge));
            }
        }
        return neighbors;

    }
}
