package Module;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashSet;
import java.util.Set;

public class bipartiteGraph {
    private Graph<Integer, DefaultEdge> g;
    Set<Integer> A;
    Set<Integer> B;

    public bipartiteGraph(Graph<Integer, DefaultEdge> g) {
        this.g = g;
        A = new HashSet<Integer>();
        B = new HashSet<Integer>();
    }

    public Graph<Integer, DefaultEdge> getG() {
        return g;
    }

    public void setG(Graph<Integer, DefaultEdge> g) {
        this.g = g;
    }

    public Set<Integer> getA() {
        return A;
    }

    public void addToA(int n) {
        A.add(n);
    }

    public Set<Integer> getB() {
        return B;
    }

    public void addToB(int n) {
        B.add(n);
    }
}
