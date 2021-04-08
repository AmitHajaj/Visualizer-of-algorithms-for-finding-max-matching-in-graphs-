package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class graphFrame extends JFrame implements ActionListener{
    JPanel sideMenu;
    Board workingArea;

    JButton addVertex;
    JButton removeVertex;
    JButton connect;
    JButton disconnect;
    JButton findMaxMatch;
    JButton clean;
    Graph<Integer, DefaultEdge> graph;

    public graphFrame( Graph<Integer, DefaultEdge> g ){
        super("Graph visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.graph = g;

        sideMenu = new JPanel();
        workingArea = new Board();
        this.add(sideMenu);
        this.add(workingArea);


        // buttons
        addVertex = new JButton("Add vertex");
        addVertex.setBounds(10, 10, 140, 75);
        addVertex.addActionListener(this);
        sideMenu.add(addVertex);

        removeVertex = new JButton("Remove vertex");
        removeVertex.setBounds(10, 175, 140, 75);
        removeVertex.addActionListener(this);
        sideMenu.add(removeVertex);

        connect = new JButton("Connect");
        connect.setBounds(10, 340, 140, 75);
        connect.addActionListener(this);
        sideMenu.add(connect);

        disconnect = new JButton("Disconnect");
        disconnect.setBounds(10, 505, 140, 75);
        disconnect.addActionListener(this);
        sideMenu.add(disconnect);

        findMaxMatch = new JButton("Find max. match");
        findMaxMatch.setBounds(10, 670, 140, 75);
        findMaxMatch.addActionListener(this);
        sideMenu.add(findMaxMatch);

        clean = new JButton("Clean board");
        clean.setBounds(10, 835, 140, 75);
        clean.addActionListener(this);
        sideMenu.add(clean);

    }

    public void setSideMenuSize(int x, int y, int width, int height) {
        this.sideMenu.setBounds(x, y, width, height);
    }


    public void setWorkingAreaSize(int x, int y, int width, int height) {
        this.workingArea.setBounds(x, y, width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        DefaultEdge edge;

        // TODO use proxy graph?
        switch( actionCommand){
            case "Add vertex":
                this.graph.addVertex(1);
                break;
            case "Remove vertex":
                this.graph.removeVertex(0);
                break;
            case "Connect":
                edge = new DefaultEdge();
                //TODO check if both needed
                this.graph.addEdge(1,0, edge);
                this.graph.setEdgeWeight(edge,2);
                break;
            case "Disconnect":
                edge = this.graph.getEdge(1, 0);
                this.graph.removeEdge(edge);
                break;
            case "Find max. match":
                //
                break;
            case "Clean board":
//                this.graph.removeAllVertices();
//                this.graph.removeAllEdges();
                break;
        }

        // TODO re-draw
    }
}
