package GUI;

import Module.*;
import Module.Pair;
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


public class Main_Panel extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;

    // == GRAPH STUFF ==
    bipartiteGraph biGraph;
    HashSet<DefaultEdge> marked = new HashSet<>();// set of marked edges
    private HashSet<Integer> pointsA;
    private HashSet<Integer> pointsB;

    //  == BUTTONS ==

    JButton runAlgoButton;
    JButton newEdgeButton;
    JButton newPointButton;
    JButton clearButton;
    JButton randomGraphButton;

    // buttons text
    final String runAlg = "-Run algorithm-", newEdge = "New random edge",
                 newNodes = "New 2 Nodes", clear = "Clear",
                 randomGraph = "Randomize a graph";

    AlgoRun pnl_algorun = new AlgoRun();
    JPanel buttonsPanel = new JPanel();

    JPanel topMenu = new JPanel();
    JTabbedPane panes = new JTabbedPane();

    public Main_Panel(){

        //    ==== SET BUTTONS TO BOTTOM PANEL ====

        runAlgoButton = new JButton(runAlg);
        runAlgoButton.addActionListener(this);
        buttonsPanel.add(runAlgoButton);

        newEdgeButton = new JButton(newEdge);
        newEdgeButton.addActionListener(this);
        buttonsPanel.add(newEdgeButton);

        newPointButton = new JButton(newNodes);
        newPointButton.addActionListener(this);
        buttonsPanel.add(newPointButton);

        clearButton = new JButton(clear);
        clearButton.addActionListener(this);
        buttonsPanel.add(clearButton);

        randomGraphButton = new JButton(randomGraph);
        randomGraphButton.addActionListener(this);
        buttonsPanel.add(randomGraphButton);


        // === GRAPH STUFF ===
        this.biGraph = new bipartiteGraph();
        this.pointsA = biGraph.getA();
        this.pointsB = biGraph.getB();

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

        panes.add("Algorithm", pnl_algorun);
        panes.add("Explanation", jScrollPane);
        buttonsPanel.setBackground(Color.DARK_GRAY);

        pnl_algorun.add(buttonsPanel, BorderLayout.SOUTH);
//
//        runAlgoButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                runAlgo();
//            }
//        });
//        newEdgeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addRandomEdge();
//            }
//        });
//        newPointButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addVertex();
//            }
//        });
//        clearButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                clear();
//            }
//        });
//        randomGraphButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                randomizeGraph();
//            }
//        });
        // panes.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.CYAN));
        this.add(panes);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        Set<DefaultEdge> M = new HashSet<DefaultEdge>();
        // TODO use proxy graph?
        switch(actionCommand){
            case runAlg:
                runAlgo();
                break;
            case newEdge:
                addRandomEdge();
                break;
            case newNodes:
                addVertex();
                break;
            case clear:
                clear();
                break;
            case randomGraph:
                randomizeGraph();
                break;
        }
    }
    public void runAlgo() {
        marked.clear();
        /**
                            |/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/|
                            |\/\/\/\ CODE FOR DEMONSTRATION ONLY! \/\/\/|
                            |/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/|
        **/
        int size = pointsA.size(), item, j;
        Set<DefaultEdge> edges = biGraph.getEdges();

        for(int i = 0; i< (size/2); i++){
            item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
            j = 0;
            for (DefaultEdge e : edges) {
                if (j == item)
                    marked.add(e);
                j++;
            }
        }
        repaint();
    }

    public void addRandomEdge() {
        int size = pointsA.size();
        if(size==0) return;

        int src = (new Random().nextInt(size))*2;
        int dest = (new Random().nextInt(size))*2+1;


        this.biGraph.addEdge(src,dest);
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
                Integer a_Point = iterA.next();
                Integer b_Point = iterB.next();
                biGraph.setLocation(a_Point,x_a,y);
                biGraph.setLocation(b_Point,x_b,y);

                //draw vertex
                g.fillOval(x_a-5, y-5, 10, 10);
                g.fillOval(x_b-5, y-5, 10, 10);

                // draw vertex key
                g.setColor(Color.BLUE);
                g.drawString(""+a_Point, x_b+20, y+5);
                g.drawString(""+b_Point, x_a-20, y+5);
            }

            //draw Edges:
            Set<DefaultEdge> edges = biGraph.getEdges();
            for (DefaultEdge e : edges) {
                Color arrowColor = marked.contains(e)? Color.RED: Color.BLACK;

                Pair src = biGraph.getLocation(biGraph.getG().getEdgeSource(e));
                Pair dest =  biGraph.getLocation(biGraph.getG().getEdgeTarget(e));

                graphParts.LineArrow line = new LineArrow(src.x(), src.y(), dest.x(), dest.y(), arrowColor, 3);
                line.draw(g);
            }
        }
    }
}
