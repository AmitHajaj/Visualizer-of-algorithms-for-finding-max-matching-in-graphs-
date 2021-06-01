package Module;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.*;

/**
 * This class is an implementation of Edmonds blossom algorithm for finding a max. matching in a general graph.
 *
 * This version of the Edmonds blossom algorithm runs in O(V^4).
 *
 * @author Amit Hajaj, Kfir Ettinger, Shani Shuv
 *
 */
public class EdmondsBlossom {
    DefaultUndirectedGraph<Integer, DefaultEdge> graph;

    public EdmondsBlossom() {
        this.graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    }

    /**
     *This method will apply the Edmonds blossom algorithm for finding a max. matching in general graphs.
     * @return A Set of edges that would be the max. matching in this graph.
     */
    public Set<DefaultEdge> Edmonds(Set<DefaultEdge> M) {
        //Init F as set of free nodes.
        HashSet<Integer> F = new HashSet<>();
        for(DefaultEdge edge : M){
            F.add(this.graph.getEdgeSource(edge));
            F.add(this.graph.getEdgeTarget(edge));
        }


        //TODO: here needed a big while loop.
        //Init graph
        DefaultUndirectedGraph<Integer, DefaultEdge> graph = this.graph;
        //Init tree
        HashMap<Integer, nodeInfo> T = new HashMap<>();
        //Init BFS queue
        LinkedList<Integer> BFSQueue = new LinkedList<>();
        //Push some free vertex to the bfs queue and start iterating.
        int r = F.iterator().next();
        BFSQueue.push(r);
        T.put(r, new nodeInfo(r, null));
        while(!BFSQueue.isEmpty()){
            int v = BFSQueue.pop();
            Set<Integer> neighbors = getNeighbors(graph, v);
            //Iterate over this vertex neighbors.
            for(int neighbor : neighbors){
                //If the neighbor is not in the bfs tree and matched
                if(!T.containsKey(neighbor) && !F.contains(neighbor)){
                    T.put(neighbor, new nodeInfo(neighbor, T.get(v)));
                    int vMate = getNeighborInMatch(M, v);
                    T.put(vMate, new nodeInfo(vMate, T.get(neighbor)));
                    BFSQueue.push(vMate);
                }
                else if(T.containsKey(neighbor)){
                    //Check if a cycle is detected
                    Set<Integer> neighborsOfNeighbor = getNeighbors(graph, v);
                    if(neighborsOfNeighbor.contains(r)){//cycle detected.
                        //check for its length.
                        Set<Integer> cycle = checkCycleLength(T, v, neighbor);
                        if(cycle.size()%2 == 0){
                            //if it is even length cycle, do nothing.
                            continue;
                        }
                        else{
                            //If it is ODD length cycle, we have a blossom to contract.
                            //And on our queue, we put the new vertex instead of any old vertex of the cycle.
                            graph = blossomContractor(cycle, graph);
                            //remove cycle vertices from the queue
                            BFSQueue.removeIf(cycle::contains);
                            //add the new blossom to the queue as one vertex.
                            BFSQueue.push(this.graph.vertexSet().size()-1);
                        }
                    }
                }
                else if(F.contains(neighbor)){
                    //expend the blossoms.
                    graph = this.graph;
                    //reconstruct an augmenting path.

                    //invert augmenting path.
                }
            }
        }




    }


    private void alternatePath(Set<DefaultEdge> M, int start, int target, Map<Integer, nodeInfo> T, Set<Integer> C, DefaultUndirectedGraph<Integer, DefaultEdge> graph){
        Set<DefaultEdge> newM = new HashSet<>();
        Set<Integer> neighbors = getNeighbors(graph, start);
        int pointer = -1;

        for(int neighbor : neighbors){
            if(C.contains(neighbor)){
                newM.add(graph.getEdge(start, neighbor));
                pointer = neighbor;
                break;
            }
        }
        for()

    }


    private DefaultUndirectedGraph<Integer, DefaultEdge> blossomContractor(Set<Integer> cycle, DefaultUndirectedGraph<Integer, DefaultEdge> graph){
        DefaultUndirectedGraph<Integer, DefaultEdge> resultGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        //add to the result graph any node except from those in the cycle.
        for(int node : graph.vertexSet()){
            if(!cycle.contains(node)) {
                resultGraph.addVertex(node);
            }
        }
        //create the super-vertex
        resultGraph.addVertex(this.graph.vertexSet().size());

        for(DefaultEdge edge : this.graph.edgeSet()){
            int tempSrc = this.graph.getEdgeSource(edge);
            int tempTarget = this.graph.getEdgeTarget(edge);

            //If this edge not on the cycle add it to the result.
            if(!cycle.contains(tempSrc) && !cycle.contains(tempTarget)){

            }
            //If it is on the cycle adjust it to result graph with the super-vertex
            else{
                if(cycle.contains(tempSrc)){
                    resultGraph.addEdge(this.graph.vertexSet().size(), tempTarget);
                }
                else if(cycle.contains(tempTarget)){
                    resultGraph.addEdge(tempSrc, this.graph.vertexSet().size());
                }
            }
        }

        return resultGraph;
    }

    private Set<Integer> checkCycleLength(HashMap<Integer, nodeInfo> nodes, int v, int u){
        LinkedList<nodeInfo> vPath = new LinkedList<>();
        LinkedList<nodeInfo> uPath = new LinkedList<>();

        //The next two loops set the paths from u and v to the beginning of the run.
        boolean flag = true;
        int tempRunner = v;
        while(flag){
            if(nodes.get(tempRunner).whoCalledMe == null){
                flag = false;
            }
            else{
                vPath.add(nodes.get(tempRunner).whoCalledMe);
                tempRunner = nodes.get(tempRunner).whoCalledMe.value;
            }
        }

        tempRunner = u;

        flag = true;
        while(flag) {
            if (nodes.get(tempRunner).whoCalledMe == null) {
                flag = false;
            }
            else{
                uPath.add(nodes.get(tempRunner).whoCalledMe);
                tempRunner = nodes.get(tempRunner).whoCalledMe.value;
            }
        }


        //Now we will check where they are meet and close a cycle.
        int uIndexOfMeeting = -1;
        int vIndexOfMeeting = -1;
        for(int i=0; i<vPath.size(); i++){
            if(uPath.contains(vPath.get(i))){
                uIndexOfMeeting = uPath.indexOf(vPath.get(i));
                vIndexOfMeeting = i;
                break;
            }
        }
        HashSet<Integer> cycleNodes = new HashSet<>();
        ListIterator<nodeInfo> uIter = uPath.listIterator();
        while(uIter.hasNext()){
            cycleNodes.add(uIter.next().value);
        }

        ListIterator<nodeInfo> vIter = vPath.listIterator();
        while(vIter.hasNext()){
            cycleNodes.add(vIter.next().value);
        }


        return cycleNodes;
    }

    private int getNeighborInMatch(Set<DefaultEdge> M, int vertex){
        int result = -1;
        for(DefaultEdge edge : M){
            if(this.graph.getEdgeSource(edge) == vertex){
                result = this.graph.getEdgeTarget(edge);
            }
            else if(this.graph.getEdgeTarget(edge) == vertex){
                result = this.graph.getEdgeSource(edge);
            }
        }
        return result;
    }

    private Set<Integer> getNeighbors(DefaultUndirectedGraph<Integer, DefaultEdge> graph, int vertex){
        HashSet<Integer> neighbors = new HashSet<>();
        for(DefaultEdge edge : graph.edgeSet()){
            if(graph.getEdgeSource(edge) == vertex){
                neighbors.add(graph.getEdgeTarget(edge));
            }
            else if(graph.getEdgeTarget(edge) == vertex){
                neighbors.add(graph.getEdgeSource(edge));
            }
        }
        return neighbors;
    }


    private class nodeInfo{
        public nodeInfo whoCalledMe;
        public int value;

        public nodeInfo(int value, nodeInfo whoCalledMe) {
            this.value = value;
            this.whoCalledMe = whoCalledMe;
        }
    }
}

