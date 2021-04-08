package Module;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import javax.management.DescriptorAccess;
import java.util.*;

public class Hungarian_Method {
    bipartiteGraph graph;

    public Hungarian_Method(bipartiteGraph g){
        this.graph = g;
    }

    /**
     *This method will apply the Hungarian method for finding a max. matching in bipartite graphs
     * @param g - bipartite graph.
     * @return A Set of edges that would be the max. matching in this graph.
     */
    public Set<DefaultEdge> Hungarian(bipartiteGraph g){
        Set<DefaultEdge> M = new HashSet<>();
        int bSize = 0;
        int aSize = 0;

        boolean flag = true;

        while(flag){
            bSize = M.size();
            M = this.IsAugmenting(g.getA(), g.getB(), M);
            aSize = M.size();
            if(aSize == bSize){
                flag = false;
            }
        }
        return M;
    }

    /**
    This method gets a bipartite graph represented as two group of nodes, one for each side
     and a match on this graph represented as a set of edges.
     @param A - one side of a bipartite graph
     @param B - second side of a bipartite graph
     @param M - a match on this graph
     @return An augmenting path for match M if there is one, or M itself if there isnt.
     */
    public Set<DefaultEdge> IsAugmenting(Set<Integer> A, Set<Integer> B, Set<DefaultEdge> M){
        Set<Integer> A1 = new HashSet<Integer>();
        Set<Integer> A2 = A;
        Set<Integer> B1 = new HashSet<Integer>();
        Set<Integer> B2 = B;

        //Now we set each node in the right group based on it role at match M.
        int tempSrc;
        int tempDst;

        for(DefaultEdge e: M){
            tempSrc =this.graph.getG().getEdgeSource(e);
            tempDst =this.graph.getG().getEdgeTarget(e);

            if(A.contains(tempSrc)) {
                A1.add(tempSrc);
                A2.remove(tempSrc);
                B1.add(tempDst);
                B2.remove(tempDst);
            }
            else{
                B1.add(tempSrc);
                B2.remove(tempSrc);
                A1.add(tempDst);
                A2.remove(tempDst);
            }
        }

        //if one of the un-matched groups is empty, then there is no augmenting path.
        if(A2.isEmpty() || B2.isEmpty()){
            return null;
        }

        //Now we divide the edges to 4 more groups.
        HashSet<DefaultEdge> A1ToB1 = new HashSet<DefaultEdge>();//Group #1, edges from A to B that are not in the current matching.
        HashSet<DefaultEdge> A1ToB2 = new HashSet<DefaultEdge>();//Group #2, edges from A to B that the one in A is matched and the other from B is not.
        HashSet<DefaultEdge> A2ToB1 = new HashSet<DefaultEdge>();//Group #3, like group #2 but in the opposite direction.
        HashSet<DefaultEdge> A2ToB2 = new HashSet<DefaultEdge>();//Group #4, edges that none of the nodes is in the current matching.

        for(DefaultEdge e:this.graph.getG().edgeSet()){
            tempSrc =this.graph.getG().getEdgeSource(e);
            tempDst =this.graph.getG().getEdgeTarget(e);
            if(A1.contains(tempSrc)){
                if(B1.contains(tempDst) && !M.contains(e)){
                    A1ToB1.add(e);
                }
                else if(B2.contains(tempDst)){
                    A1ToB2.add(e);
                }
            }
            else if(A2.contains(tempSrc)){
                if(B1.contains(tempDst)){
                    A2ToB1.add(e);
                }
                else if(B2.contains(tempDst)) {
                    A2ToB2.add(e);
                }
            }
        }

        //Now we build a new DIRECTED graph as follows:
        //  - If an edge is in matching M, then we direct it from A to B.
        //  - Else, we direct it from B to A.

        Graph<Integer, DefaultEdge> Dg = new DefaultDirectedGraph<>(DefaultEdge.class);

        for(int i: this.graph.getG().vertexSet()){
            Dg.addVertex(i);
        }

        for(DefaultEdge e:this.graph.getG().edgeSet()){
            tempSrc =this.graph.getG().getEdgeSource(e);
            tempDst =this.graph.getG().getEdgeTarget(e);


            for(DefaultEdge edge: M){
                if(this.graph.getG().getEdgeSource(edge) == tempSrc && this.graph.getG().getEdgeTarget(edge) == tempDst){
                    Dg.addEdge(tempDst, tempSrc);
                }
            }
            Dg.addEdge(tempSrc, tempDst);
        }

        // Now we need to find a path from A2 to B2.
        // If there is one, we will augment M along it.

        int start = -1;
        int dest = -1;

        for(int i: A2) {
            start = i;
            Iterator<Integer> iterator = new DepthFirstIterator<>(Dg, start);

            while (iterator.hasNext()) {
                int temp = iterator.next();
                if (B2.contains(temp)) {
                    dest = temp;
                    break;
                }
            }
            if (dest != -1) {
                break;
            }
        }

        if(dest == -1){
            return M;
        }

        // TODO: Fix duplicates!
        BFSShortestPath<Integer, DefaultEdge> Dp = new BFSShortestPath<>(Dg);
        M.addAll(Dp.getPath(start, dest).getEdgeList());

        return M;
    }

    // TODO: Add some test's to check the method above.
    public static void main(String[] args){
        Graph <Integer, DefaultEdge> g = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);

    }
}
