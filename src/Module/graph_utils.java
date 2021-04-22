package Module;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class purpose is to generalize the work on the algorithms.
 * It would create several kinds of graph based on the algorithm we work on.
 */
public class graph_utils {

    public graph_utils() {
    }

    /**
     * returns an empty bipartite graph.
     * @return
     */
    public static bipartiteGraph getBiPartiteGraph(){
        DefaultUndirectedGraph<Integer, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);
        bipartiteGraph bpg = new bipartiteGraph(g);
        return bpg;
    }

    /**
         * Generate a random graph with v_size nodes and e_size edges
         * @param v_size number of vertices to add.
         * @param e_size number of edges to add.
         * @return
         */
    public static bipartiteGraph getRandomBiPartiteGraph(int v_size, int e_size) {
        DefaultUndirectedGraph<Integer, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);
        bipartiteGraph bpg = new bipartiteGraph(g);
        for(int i=0;i<v_size;i++) {
            if(i % 2 == 0){
                bpg.addToA(i);
                bpg.getG().addVertex(i);
            }
            else{
                bpg.addToB(i);
                bpg.getG().addVertex(i);
            }
        }
            // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        Set<Integer> nodesA = bpg.getA();
        Set<Integer> nodesB = bpg.getB();
        Random r = new Random();
        while(bpg.getG().edgeSet().size() < e_size) {
            int i = r.nextInt(v_size);
            int j = r.nextInt(v_size);
            if(i!=j && (i%2 != j%2)) {
                bpg.getG().addEdge(i, j);
            }
            else{
                continue;
            }
        }
        return bpg;
    }
}