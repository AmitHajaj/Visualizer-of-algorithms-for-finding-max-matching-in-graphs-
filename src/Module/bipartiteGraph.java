package Module;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * This simple class represent a bipartite graph with a graph object and two sets of vertices.
 *
 */
public class bipartiteGraph {
    private DefaultUndirectedGraph<Integer, DefaultEdge> g;
    Set<Integer> A;
    Set<Integer> B;

    public bipartiteGraph(DefaultUndirectedGraph<Integer, DefaultEdge> g) {
        this.g = g;
        A = new HashSet<Integer>();
        B = new HashSet<Integer>();
    }

    /**
     * Empty constructor.
     */
    public bipartiteGraph(){
        this.g = new DefaultUndirectedGraph<>(DefaultEdge.class);
        A = new HashSet<Integer>();
        B = new HashSet<Integer>();
    }

    public DefaultUndirectedGraph<Integer, DefaultEdge> getG() {
        return g;
    }

    public void setG(DefaultUndirectedGraph<Integer, DefaultEdge> g) {
        this.g = g;
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
        this.g.addVertex(n);
    }
}
