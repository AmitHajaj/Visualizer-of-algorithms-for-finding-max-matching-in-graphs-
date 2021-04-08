package Controller;

import GUI.graphFrame;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class graph {
    // TODO: Add some test's to the method above.
    public static void main(String[] args){
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        graphFrame frame = new graphFrame(g);

        //set the window size
        frame.setSideMenuSize(0, 0, 150, 1000);
        frame.setWorkingAreaSize(150, 0, 850, 1000);
    }
}
