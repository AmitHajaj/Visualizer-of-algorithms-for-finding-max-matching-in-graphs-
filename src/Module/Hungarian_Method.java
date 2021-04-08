package Module;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import javax.management.DescriptorAccess;
import java.util.*;

public class Hungarian_Method {
    Graph<Integer, DefaultEdge> graph;
    Set<Integer> A;
    Set<Integer> B;

    public Hungarian_Method(Graph<Integer, DefaultEdge> g){
        this.graph = g;
        this.A = new HashSet<Integer>();
        this.B = new HashSet<Integer>();
    }

    /**
     *This method will apply the Hungarian method for finding a max. matching in bipartite graphs
     * @param A - One side of the bipartite graph.
     * @param B - The other side of the bipartite graph.
     * @return A Set of edges that would be the max. matching in this graph.
     */
    public Set<DefaultEdge> Hungarian(Set<Integer> A, Set<Integer> B){
        Set<DefaultEdge> M = new HashSet<DefaultEdge>();
        int bSize = 0;
        int aSize = 0;

        Boolean flag = true;

        while(flag){
            bSize = M.size();
            M = this.IsAugmenting(A, B, M);
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
            tempSrc =this.graph.getEdgeSource(e);
            tempDst =this.graph.getEdgeTarget(e);

            A1.add(tempSrc);
            A2.remove(tempSrc);
            B1.add(tempDst);
            B2.remove(tempDst);
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

        for(DefaultEdge e:this.graph.edgeSet()){
            tempSrc =this.graph.getEdgeSource(e);
            tempDst =this.graph.getEdgeTarget(e);
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

        for(DefaultEdge e:this.graph.edgeSet()){
            tempSrc =this.graph.getEdgeSource(e);
            tempDst =this.graph.getEdgeTarget(e);

            Dg.addVertex(tempSrc);
            Dg.addVertex(tempDst);
            if(M.contains(e)){
                // If current edge in M, so we direct it as told.
                Dg.addEdge(tempDst, tempSrc);
            }
            else{
                Dg.addEdge(tempSrc, tempDst);
            }
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
            if(dest != -1){
                break;
            }
        }

        DijkstraShortestPath<Integer, DefaultEdge> Dp = new DijkstraShortestPath<>(Dg);
        M.addAll(Dp.getPath(start, dest).getEdgeList());

        return M;
    }

    // TODO: Add some test's to the method above.
    public static void main(String[] args){
        Graph <Integer, DefaultEdge> g = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);

    }
}
