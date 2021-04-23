package GUI;

import Module.*;
import Module.Pair;
import GUI.graphParts.LineArrow;
import GUI.hungarian_Panel;
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

    //  === BUTTONS ===
    JButton runAlgoButton;
    JButton rndEdgeButton;
    JButton edgeButton;
    JButton newPointButton;
    JButton clearButton;
    JButton randomGraphButton;

    // === PANELS ===
    JPanel buttonsPanel;
    JPanel topMenu;
    hungarian_Panel task1;

    // buttons text
    final String runAlg = "-Run algorithm-", rndEdge = "New random edge",
                 newNodes = "New 2 Nodes", clear = "Clear",
                 randomGraph = "Randomize a graph", normalEdge = "New edge";

    JTabbedPane panes = new JTabbedPane();

    // |+++++++++++++++++++++++|
    // |+++++ CONSTRUCTOR +++++|
    // |+++++++++++++++++++++++|
    public Main_Panel(){
        //    === SET PANELS ===
        buttonsPanel = new JPanel();
        topMenu = new JPanel();
        task1 = new hungarian_Panel();

        //    ==== SET BUTTONS TO BOTTOM PANEL ====
        runAlgoButton = new JButton(runAlg);
        runAlgoButton.addActionListener(this);
        buttonsPanel.add(runAlgoButton);

        rndEdgeButton = new JButton(rndEdge);
        rndEdgeButton.addActionListener(this);
        buttonsPanel.add(rndEdgeButton);

        edgeButton = new JButton(normalEdge);
        edgeButton.addActionListener(this);
        buttonsPanel.add(edgeButton);

        newPointButton = new JButton(newNodes);
        newPointButton.addActionListener(this);
        buttonsPanel.add(newPointButton);

        clearButton = new JButton(clear);
        clearButton.addActionListener(this);
        buttonsPanel.add(clearButton);

        randomGraphButton = new JButton(randomGraph);
        randomGraphButton.addActionListener(this);
        buttonsPanel.add(randomGraphButton);

        // === GRAPHICS ===
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(30, 30, 30));
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);

        // === EXPLANATIONS PANEL ===
        URL url = getClass().getResource("/data/Hungarian.html");
        try {
            jEditorPane.setPage(url);
        } catch (IOException e) {
            jEditorPane.setContentType("text/html");
            jEditorPane.setText("<html>Page not found.</html>");
        }
        JScrollPane jScrollPane = new JScrollPane(jEditorPane);

        panes.add("Algorithm", task1);
        panes.add("Explanation", jScrollPane);
        buttonsPanel.setBackground(Color.DARK_GRAY);

        task1.add(buttonsPanel, BorderLayout.SOUTH);

        this.add(panes);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // === BUTTONS SWITCH CASE ===
        String actionCommand = e.getActionCommand();
        switch(actionCommand){
            case runAlg:
                task1.runAlgo();
                break;
            case rndEdge:
                task1.addRandomEdge();
                break;
            case normalEdge:
                task1.addEdge();
                break;
            case newNodes:
                task1.addVertex();
                break;
            case clear:
                task1.newGraph();
                break;
            case randomGraph:
                task1.randomizeGraph();
                break;
        }
    }
}
