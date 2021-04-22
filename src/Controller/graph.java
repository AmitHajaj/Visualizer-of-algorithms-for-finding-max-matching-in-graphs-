package Controller;

import GUI.graphFrame;
import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteBipartiteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.*;
import Module.bipartiteGraph;

import java.util.Set;
import Module.*;

public class graph {

    public static void main(String[] args){
//        DefaultUndirectedGraph<Integer, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);
//        bipartiteGraph bg = new bipartiteGraph(g);
//        graphFrame frame = new graphFrame(bg);
//
//        //set the window size
//        frame.setSideMenuSize(0, 0, 150, 1000);
//        frame.setWorkingAreaSize(150, 0, 850, 1000);

        DefaultUndirectedGraph<Integer, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);
        bipartiteGraph graph = graph_utils.getRandomBiPartiteGraph(12, 8);;


        Hungarian_Method algo = new Hungarian_Method(graph);

        for(int i=0; i<12; i++){
            graph.addVertex(i);
        }
        graph.getG().addEdge(0, 3);
        graph.getG().addEdge(0, 5);
        graph.getG().addEdge(4, 1);
        graph.getG().addEdge(4, 7);
        graph.getG().addEdge(6, 5);
        graph.getG().addEdge(8, 5);
        graph.getG().addEdge(8, 7);
        graph.getG().addEdge(10, 11);

        Set<DefaultEdge> M = algo.Hungarian(graph);

        System.out.println(M.toString());
    }
}
