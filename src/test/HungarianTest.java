package test;

import org.jgrapht.graph.DefaultEdge;

import Module.*;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Test;

import java.util.*;

public class HungarianTest {

    @Test
    void maximumMatching(){
        SimpleGraph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);


        for(int i=0; i<10; i++){
            graph.addVertex(i);
        }
        Set<DefaultEdge> ans = new HashSet<>();

        ans.add(graph.addEdge(8, 0));//this in matching
        graph.addEdge(0, 1);
        ans.add(graph.addEdge(1, 2));//this in matching
        graph.addEdge(2, 3);
        ans.add(graph.addEdge(3, 4));//this in matching
        graph.addEdge(3, 7);
        graph.addEdge(4, 5);
        ans.add(graph.addEdge(5, 6));//this in matching
        graph.addEdge(7, 6);
        ans.add(graph.addEdge(9, 7));//this in matching

        DefaultEdge flas = graph.getEdge(0, 8);
        if(flas != null){
            System.err.println("Hooza!!!");
        }else{
            System.err.println("Fucked");
        }


        SimpleGraph<Integer, DefaultEdge> M = Edmonds.findMaximumMatching(graph);





    }
}
