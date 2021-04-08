package test;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import Module.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class HungarianTest {

    @Test
    void Hungarian_Method(){
        DefaultUndirectedGraph<Integer, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);
        bipartiteGraph graph = new bipartiteGraph(g);

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
