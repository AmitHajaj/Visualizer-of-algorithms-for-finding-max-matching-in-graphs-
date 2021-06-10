package Module;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class is an implementation of the Hungarian method for finding a max. matching in a bipartite graph.
 * For a given bipartite graph G= (A U B, V) where A and B are the two sides of the graph, we can apply Hungarian_method on it
 * and we will get back the max. matching for this graph G.
 *
 * This version of the Hungarian method runs in O(??).
 *
 * @author Amit Hajaj, Kfir Ettinger, Shani Shuv
 *
 */
public class Hungarian{
//    private static bipartiteGraph _graph;

    /**
     *This method will apply the Hungarian method for finding a max. matching in bipartite graphs
     * @param g - bipartite graph.
     * @return A Set of edges that would be the max. matching in this graph.
     */
    public static Set<DefaultEdge> runAlgo(bipartiteGraph g, boolean SBS){
        Set<DefaultEdge> M = new HashSet<>();
        int bSize, aSize;

        boolean flag = true;

        while(flag){

            bSize = M.size();

            HashSet<Integer> ACopy = new HashSet<>(g.getA());
            HashSet<Integer> BCopy = new HashSet<>(g.getB());
            Set<DefaultEdge> MCopy = new HashSet<>(M);

            M = IsAugmenting(ACopy, BCopy, M, g);
            //TODO: To implement step by step, we need to return the current M each time.
            if(M != null){
                aSize = M.size();
                if(aSize == bSize){
                    flag = false;
                }
            }
            else{
                return MCopy;
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
    public static Set<DefaultEdge> IsAugmenting(Set<Integer> A, Set<Integer> B, Set<DefaultEdge> M, bipartiteGraph _graph){
        Set<Integer> A1 = new HashSet<Integer>();
        Set<Integer> A2 = A;
        Set<Integer> B1 = new HashSet<Integer>();
        Set<Integer> B2 = B;

        //Now we set each node in the right group based on it role at match M.
        int tempSrc;
        int tempDst;

        for(DefaultEdge e: M){
            tempSrc = _graph.getG().getEdgeSource(e);
            tempDst = _graph.getG().getEdgeTarget(e);

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

        for(DefaultEdge e : _graph.getG().edgeSet()){
            tempSrc = _graph.getG().getEdgeSource(e);
            tempDst = _graph.getG().getEdgeTarget(e);
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
        //  - If an edge is in matching M, then we direct it from B to A.
        //  - Else, we direct it from A to B.

        Graph<Integer, DefaultEdge> Dg = new DefaultDirectedGraph<>(DefaultEdge.class);

        for(int i: _graph.getG().vertexSet()){
            Dg.addVertex(i);
        }

        for(DefaultEdge e : _graph.getG().edgeSet()){
            tempSrc = _graph.getG().getEdgeSource(e);
            tempDst = _graph.getG().getEdgeTarget(e);

            Dg.addEdge(tempSrc, tempDst);
            for(DefaultEdge edge: M){
                if(_graph.getG().getEdgeSource(edge) == tempSrc && _graph.getG().getEdgeTarget(edge) == tempDst){
                    Dg.addEdge(tempDst, tempSrc);
                    Dg.removeEdge(tempSrc, tempDst);
                }
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
            if (dest != -1) {
                break;
            }
        }
        if(dest == -1){
            return M;
        }

        // If we did find a reachable node in B2, we need to find the path to it and then to get the augmented path from it.
        BFSShortestPath<Integer, DefaultEdge> Dp = new BFSShortestPath<>(Dg);
        List <DefaultEdge> augmentPath = Dp.getPath(start, dest).getEdgeList();
        DefaultEdge temp = new DefaultEdge();
        DefaultEdge tempRemove = new DefaultEdge();

        // The path we got is set to be:
        //  - Odd edges is the augmenting edges.
        //  - Even edges are edges from the old matching.
        // To return the full augmenting path we need to take out the even edges as follows:
        for(int i=0; i< augmentPath.size(); i++){
            if(i%2 == 1) {
                temp = augmentPath.get(i);
                Iterator<DefaultEdge> itr = M.iterator();
                while(itr.hasNext()){
                    tempRemove = itr.next();
                    if(_graph.getG().getEdgeTarget(temp) == _graph.getG().getEdgeSource(tempRemove) &&
                            _graph.getG().getEdgeSource(temp) == _graph.getG().getEdgeTarget(tempRemove)){
                        itr.remove();
                    }
                }
                augmentPath.remove(i);
            }
        }

        M.addAll(augmentPath);

        return M;
    }
}
