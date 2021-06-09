package Module;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.text.MutableAttributeSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// TODO change algo classes to static

/**
 * Algorithm to find graph's line covering.
 *
 * Input: Graph.
 * Output: set of edges.
 *
 * Algorithm description:
 *      1- Find a maximum matching using the Edmonds algorithm.
 *
 *      2- Extending it greedily so that all vertices are covered.
 *         for each of uncovered vertices we add a random edge that adjacent to it.
 *
 */
public class LineCoverAlgorithm {
    private static SimpleGraph<Integer, DefaultEdge> _graph;

    /**
     * Run algorithm to find line cover of a graph.
     *
     * @return Set of edges (the line cover).
     */
    public static Set<DefaultEdge> runAlgo(SimpleGraph<Integer, DefaultEdge> graph){

        _graph = graph;
        HashSet<DefaultEdge> edges = new HashSet<>( Edmonds.findMaximumMatching(_graph).edgeSet());
        Set<Integer> nodes = getNotCovered(edges);

        for(Integer n: nodes) {
            edges.add(graph.edgesOf(n).iterator().next());
        }

        return edges;
    }

    /**
     * Iterate over the edgesSet and return all nodes that are not adjacent to any of it's edges.
     *
     * @param edgesSet perfect matching of graph.
     * @return  set of vertices (Integer).
     */
    private static Set<Integer> getNotCovered(Set<DefaultEdge> edgesSet){
        HashSet<Integer> nodes = new HashSet<>(_graph.vertexSet());

        for(DefaultEdge e: edgesSet){
            nodes.remove(_graph.getEdgeSource(e));
            nodes.remove(_graph.getEdgeTarget(e));
        }

        return nodes;
    }
}

