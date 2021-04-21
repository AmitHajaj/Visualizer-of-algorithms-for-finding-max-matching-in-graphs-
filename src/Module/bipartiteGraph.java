package Module;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represent a bipartite graph with a graph object and two sets of vertices.
 */
public class bipartiteGraph {
    private DefaultUndirectedGraph<Integer, DefaultEdge> graph;
    Set<Integer> A;
    Set<Integer> B;

    public bipartiteGraph() {
        DefaultUndirectedGraph<Integer, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);
        this.graph = g;
        A = new HashSet<Integer>();
        B = new HashSet<Integer>();
    }

    public DefaultUndirectedGraph<Integer, DefaultEdge> getG() {
        return graph;
    }

    public void setG(DefaultUndirectedGraph<Integer, DefaultEdge> g) {
        this.graph = g;
    }

    public Set<Integer> getA() {
        return A;
    }

    public void addToA(int n) {
        A.add(n);
    }

    public void removeFromA(int n){
        A.remove(n);
    }

    public Set<Integer> getB() {
        return B;
    }

    public void addToB(int n) {
        B.add(n);
    }


    public void removeFromB(int n){
        B.remove(n);
    }

    public void addVertex(int n){
        if(n%2 == 0){
            this.addToA(n);
        }
        else{
            this.addToB(n);
        }
        this.graph.addVertex(n);
    }
}
