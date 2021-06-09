package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;


public class Edmonds_FramePanel extends JPanel implements ActionListener{
//    private static final long serialVersionUID = 1L;

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
    Edmonds_Panel edmondsPanel =  new Edmonds_Panel();

    // buttons text
    final String runAlg = "-Run algorithm-", rndEdge = "New random edge",
            newNodes = "New Node", clear = "Clear",
            randomGraph = "Randomize a graph", normalEdge = "New edge";

    JTabbedPane panes = new JTabbedPane();

    // |+++++++++++++++++++++++|
    // |+++++ CONSTRUCTOR +++++|
    // |+++++++++++++++++++++++|

    public Edmonds_FramePanel(){
        //    === SET PANELS ===
        buttonsPanel = new JPanel();
        topMenu = new JPanel();
        edmondsPanel = new Edmonds_Panel();

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

        panes.add("Algorithm", edmondsPanel);
        panes.add("Explanation", jScrollPane);
        buttonsPanel.setBackground(Color.DARK_GRAY);

        edmondsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        this.add(panes);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // === BUTTONS SWITCH CASE ===
        String actionCommand = e.getActionCommand();
        switch(actionCommand){
            case runAlg:
                edmondsPanel.runAlgo();
                break;
            case rndEdge:
                edmondsPanel.addRandomEdge();
                break;
            case normalEdge:
                edmondsPanel.addEdge();
                break;
            case newNodes:
                edmondsPanel.newNode();
                break;
            case clear:
                edmondsPanel.newGraph();
                break;
            case randomGraph:
                edmondsPanel.randomizeGraph();
                break;
        }
    }
}
