package GUI;

import Module.*;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarException;

public class mainPanel extends JPanel {
    bipartiteGraph graph;
    private static final long serialVersionUID = 1L;
    private final LinkedList<Edge> edges = new LinkedList<>();
    private final LinkedList<Node> pointsA = new LinkedList<>();
    private final LinkedList<Node> pointsB = new LinkedList<>();
    AlgoRun pnl_algorun = new AlgoRun();
    JPanel buttonsPanel = new JPanel();
    JButton newEdgeButton = new JButton("New random edge");
    JButton newPointButton = new JButton("New Point");
    JButton clearButton = new JButton("Clear");
    JButton randomGraphButton = new JButton("Randomize a graph");
    JPanel topMenu = new JPanel();
    JTabbedPane panes = new JTabbedPane();

    public mainPanel(){
        this.graph = new bipartiteGraph();

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
                addPoint();
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
        int n1 = (int)(Math.random()*n);
        int n2 = (int)(Math.random()*n);
        edges.add(new Edge(pointsA.get(n1), pointsB.get(n2)));
        repaint();
    }

    public void randomizeGraph() {
        clear();
        int size = (int)(Math.random()*20)+1;
        for (int i = 0; i < size; i++) {
            addPoint();
        }
        int e = (int)(Math.random()*20);
        for (int i = 0; i<e; i++) {
            addRandomEdge();
        }
    }

    public void addPoint() {
        pointsA.add(new Node());
        pointsB.add(new Node());
        repaint();
    }

    public void clear() {
        edges.clear();
        pointsA.clear();
        pointsB.clear();
        Node.countN = 0;
        repaint();
    }

    class AlgoRun extends JPanel{

        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //draw Nodes:
            int n = pointsA.size();
            for (int i=0; i<n; i++) {
                g.setColor(Color.RED);
                int y = (this.getHeight()-20)/(n+1)*(i+1);
                int x_a = this.getWidth()/10*6;
                int x_b = this.getWidth()/10*9;
                pointsA.get(i).y = y;
                pointsB.get(i).y = y;
                pointsA.get(i).x = x_a;
                pointsB.get(i).x = x_b;
                g.fillOval(x_a-5, y-5, 10, 10);
                g.fillOval(x_b-5, y-5, 10, 10);
                g.setColor(Color.BLUE);
                g.drawString(""+pointsB.get(i).key, x_b+20, y+5);
                g.drawString(""+pointsA.get(i).key, x_a-20, y+5);
            }
            //draw Edges:
            for (Edge e : edges) {
                double r = 2;
                double x1 = e.a.x, y1 = e.a.y;
                double x2 = e.b.x, y2 = e.b.y;
                // double distp1p2 = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
                // if(distp1p2 == 0) distp1p2 = 1;
                // double dir_x = (x1 - x2) / distp1p2;
                // double dir_y = (y1 - y2) / distp1p2;
                // x1 = dir_x * (-r) + x1;
                // y1 = dir_y * (-r) + y1;
                // x2 = dir_x * r + x2;
                // y2 = dir_y * r + y2;
                // g.drawLine(e.a.x, e.a.y, e.b.x, e.b.y);
                LineArrow line = new LineArrow(x1, y1, x2, y2, Color.BLACK, 3);
                line.draw(g);
            }
        }
    }
}
