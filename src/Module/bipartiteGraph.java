package Module;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represent a bipartite graph with a graph object and two sets of vertices.
 */
public class bipartiteGraph {
    private DefaultUndirectedGraph<Integer, DefaultEdge> graph;
    private HashMap<Integer, Pair> locations = new HashMap<>();
    HashSet<Integer> A;
    HashSet<Integer> B;

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

    public void addEdge(int src, int dst){
        this.graph.addEdge(src,dst);
    }

    public Set<DefaultEdge> getEdges(){
        return this.graph.edgeSet();
    }

    public HashSet<Integer> getA() {
        return A;
    }

    public void addToA(int n) {
        A.add(n);
        addToGraph(n);
    }

    public void removeFromA(int n){
        A.remove(n);
    }

    public HashSet<Integer> getB() {
        return B;
    }

    public void addToB(int n) {
        B.add(n);
        addToGraph(n);
    }


    public void removeFromB(int n){
        B.remove(n);
    }

    public void setLocation(int n, int x, int y){
        this.locations.put(n,new Pair(x, y));
    }

    public Pair getLocation(int n){
        return this.locations.get(n);
    }

    public void addVertex(int n){
        if(n%2 == 0){
            this.addToA(n);
        }
        else{
            this.addToB(n);
        }
        this.locations.put(n,new Pair());
        this.graph.addVertex(n);
    }

    private void addToGraph(int n){
        graph.addVertex(n);
        this.locations.put(n,new Pair());
    }
}
