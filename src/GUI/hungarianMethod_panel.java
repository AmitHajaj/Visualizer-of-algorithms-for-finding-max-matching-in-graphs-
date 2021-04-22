package GUI;

import Module.*;
import Module.Point;
import GUI.graphParts.LineArrow;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.jar.JarException;

public class hungarianMethod_panel extends JPanel {
    bipartiteGraph biGraph;
    private static final long serialVersionUID = 1L;
//    private final LinkedList<Edge> edges = new LinkedList<>();
    private HashSet<Integer> pointsA;
    private HashSet<Integer> pointsB;

    AlgoRun pnl_algorun = new AlgoRun();
    JPanel buttonsPanel = new JPanel();
    JButton newEdgeButton = new JButton("New random edge");
    JButton newPointButton = new JButton("New Point");
    JButton clearButton = new JButton("Clear");
    JButton randomGraphButton = new JButton("Randomize a graph");
    JPanel topMenu = new JPanel();
    JTabbedPane panes = new JTabbedPane();

    public hungarianMethod_panel(){
        //set variables
        this.biGraph = new bipartiteGraph();
        this.pointsA = biGraph.getA();
        this.pointsA = biGraph.getB();

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(30, 30, 30));
        pnl_algorun.setLayout(new BorderLayout());
        pnl_algorun.setBackground(new Color(153, 153, 153));

        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        URL url = getClass().getResource("/data/Hungarian.html");

        try {
            jEditorPane.setPage(url);
        } catch (IOException e) {
            jEditorPane.setContentType("text/html");
            jEditorPane.setText("<html>Page not found.</html>");
        }
        JScrollPane jScrollPane = new JScrollPane(jEditorPane);

        panes.add("Run algorithm", pnl_algorun);
        panes.add("Explenation", jScrollPane);
        buttonsPanel.setBackground(Color.DARK_GRAY);
        buttonsPanel.add(newEdgeButton);
        buttonsPanel.add(newPointButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(randomGraphButton);
        pnl_algorun.add(buttonsPanel, BorderLayout.SOUTH);
        newEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRandomEdge();
            }
        });
        newPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVertex();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        randomGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomizeGraph();
            }
        });
        // panes.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.CYAN));
        this.add(panes);
    }
    public void addRandomEdge() {
        int n = pointsA.size();
        int src = (int)(Math.random()*n), dst;
        do {
            dst = (int) (Math.random() * n);
        }while (src==dst);

        this.biGraph.addEdge(src,dst);
        repaint();
    }

    public void randomizeGraph() {
        clear();
        int size = (int) (Math.random() * 20) + 1;
        for (int i = 0; i < size; i++) {
            addVertex();
        }
        int e = (int) (Math.random() * 20);
        for (int i = 0; i < e; i++) {
            addRandomEdge();
        }
    }

    public void addVertex() {
        int n = pointsA.size();
        this.biGraph.addToA(n*2);
        this.biGraph.addToB(n*2 + 1);
        repaint();
    }

    public void clear() {
        this.biGraph = new bipartiteGraph();
        repaint();
    }

    class AlgoRun extends JPanel{

        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //draw Nodes:
            int n = pointsA.size();
            Iterator<Integer> iterA = pointsA.iterator();
            Iterator<Integer> iterB = pointsB.iterator();

            // draw vertex
            for (int i=0; i<n; i++) {
                // draw oval
                g.setColor(Color.RED);
                int y = (this.getHeight()-20)/(n+1)*(i+1);
                int x_a = this.getWidth()/10*6;
                int x_b = this.getWidth()/10*9;

                // set vertex location
                biGraph.setLocation(i,x_a,y);
                biGraph.setLocation(i,x_b,y);

                //draw vertex
                g.fillOval(x_a-5, y-5, 10, 10);
                g.fillOval(x_b-5, y-5, 10, 10);

                // draw vertex key
                g.setColor(Color.BLUE);
                g.drawString(""+iterA.next(), x_b+20, y+5);
                g.drawString(""+iterB.next(), x_a-20, y+5);
            }

            //draw Edges:
            Set<DefaultEdge> edges = biGraph.getEdges();
            for (DefaultEdge e : edges) {
                double r = 2;
                DefaultUndirectedGraph<Integer, DefaultEdge> g1 = biGraph.getG();
                Point src = biGraph.getLocation(biGraph.getG().getEdgeSource(e));
                Point dest =  biGraph.getLocation(biGraph.getG().getEdgeTarget(e));

                graphParts.LineArrow line = new LineArrow(src.x(), src.y(), dest.x(), dest.y(), Color.BLACK, 3);
                line.draw(g);
            }
        }
    }
}
