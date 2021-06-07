package GUI;


import Module.LineCoverAlgorithm;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class LineCover_Panel  extends JPanel {

    private static final long serialVersionUID = 1L;
    // == GRAPH STUFF ==
    SimpleGraph<Integer, DefaultEdge> _graph;
    Set<DefaultEdge> marked = new HashSet<>();// set of marked edges


    public LineCover_Panel() {
        // === INIT GRAPH ===
        newGraph();

        // === GRAPHICS ===
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(153, 153, 153));

    }

    // run algorithm
    public void runAlgo() {
        marked.clear();

        marked = LineCoverAlgorithm.runAlgo(_graph);

        repaint();
    }

    // add edge given src and dest
    public void addEdge(){
        //TODO add implementation.
        String firstVertex = JOptionPane.showInputDialog("Please choose a source vertex: ");
        String secondVertex = JOptionPane.showInputDialog("Please choose a target vertex: ");

        int first = Integer.parseInt(firstVertex);
        int second = Integer.parseInt(secondVertex);

        this._graph.addEdge(first, second);

        repaint();

    }

    // add random edge
    public void addRandomEdge() {
//        int size = pointsA.size();
//        if(size==0) return;
//
//        int src = (new Random().nextInt(size))*2;
//        int dest = (new Random().nextInt(size))*2+1;
//
//        this._graph.addEdge(src,dest);
        repaint();
    }

    // build random graph
    public void randomizeGraph() {
        newGraph();
        int size = (int) (Math.random() * 20) + 1;
        for (int i = 0; i < size; i++) {
            addVertex();
        }
        int e = (int) (Math.random() * 20);
        for (int i = 0; i < e; i++) {
            addRandomEdge();
        }
    }

    // new node
    public void addVertex() {
//        int n = pointsA.size();
//        this._graph.addToA(n*2);
//        this._graph.addToB(n*2 + 1);
//        repaint();
    }

    // reset graph
    public void newGraph() {
        this._graph = new SimpleGraph<>(DefaultEdge.class);
        repaint();
    }

    //paint graph
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
