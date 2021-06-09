//package GUI;
//
//import Module.*;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.SimpleGraph;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Random;
//import java.util.Set;
//import java.util.function.Supplier;
//
//
//class Edmonds_Panel extends JPanel{
//
//    private static final long serialVersionUID = 1L;
//
//    // == GRAPH STUFF ==
//    SimpleGraph<Integer, DefaultEdge> _graph;
//    Set<DefaultEdge> marked = new HashSet<>();// set of marked edges
//
//    public Edmonds_Panel() {
//
//        // === INIT GRAPH ===
//        newGraph();
//
//        // === GRAPHICS ===
//        this.setLayout(new BorderLayout());
//        this.setBackground(new Color(153, 153, 153));
//
//    }
//
//    // run algorithm
//    //TODO: put here the Edmonds algo.
//    public void runAlgo() {
//        marked.clear();
//
//        _graph.removeVertex(0);
//        SimpleGraph<Integer, DefaultEdge> edmondsSubGraph = Edmonds.findMaximumMatching(_graph);
//        marked = edmondsSubGraph.edgeSet();
//
//
//        /**
//         |/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/|
//         |\/\/\/\ CODE FOR DEMONSTRATION ONLY! \/\/\/|
//         |/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/|
//         **/
//
////        int size = pointsA.size(), item, j;
////        Set<DefaultEdge> edges = biGraph.getEdges();
////
////        for(int i = 0; i< (size/2); i++){
////            item = new Random().nextInt(size);
////            j = 0;
////            for (DefaultEdge e : edges) {
////                if (j == item)
////                    marked.add(e);
////                j++;
////            }
////        }
//
//        repaint();
//    }
//
//
//
//    // add edge given src and dest
//    public void addEdge(){
//        //TODO add implementation
//    }
//
//    // add random edge
//    public void addRandomEdge() {
//        int size = pointsA.size();
//        if(size==0) return;
//
//        int src = (new Random().nextInt(size))*2;
//        int dest = (new Random().nextInt(size))*2+1;
//
//        this._graph.addEdge(src,dest);
//        repaint();
//    }
//
//    // build random graph
//    //TODO: randomize a generic graph, not bipartite.
//    public void randomizeGraph() {
//        newGraph();
//        int size = (int) (Math.random() * 20) + 1;
//        for (int i = 0; i < size; i++) {
//            addVertex();
//        }
//        int e = (int) (Math.random() * 20);
//        for (int i = 0; i < e; i++) {
//            addRandomEdge();
//        }
//    }
//
//    // new node
//    public void addVertex() {
//        _graph.addVertex();
//        repaint();
//    }
//
//    // reset graph
//    public void newGraph() {
//        this._graph = new SimpleGraph<>(DefaultEdge.class);
////        this._graph = new SimpleGraph<>();
//        repaint();
//    }
//
//    //paint graph
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        int n = pointsA.size();
//        Iterator<Integer> iterA = pointsA.iterator();
//        Iterator<Integer> iterB = pointsB.iterator();
//
//        // draw vertex
//        for (int i=0; i<n; i++) {
//            // draw oval
//            g.setColor(Color.RED);
//            int y = (this.getHeight()-20)/(n+1)*(i+1);
//            int x_a = this.getWidth()/10*6;
//            int x_b = this.getWidth()/10*9;
//
//            // set vertex location
//            Integer a_Point = iterA.next();
//            Integer b_Point = iterB.next();
//            biGraph.setLocation(a_Point,x_a,y);
//            biGraph.setLocation(b_Point,x_b,y);
//
//            //draw vertex
//            g.fillOval(x_a-5, y-5, 10, 10);
//            g.fillOval(x_b-5, y-5, 10, 10);
//
//            // draw vertex key
//            g.setColor(Color.BLUE);
//            g.drawString(""+a_Point, x_b+20, y+5);
//            g.drawString(""+b_Point, x_a-20, y+5);
//        }
//
//        //draw Edges:
//        // marked edges =>  red, regular edges => black
//
//        Set<DefaultEdge> edges = biGraph.getEdges();
//        for (DefaultEdge e : edges) {
//
//            Color arrowColor = Color.BLACK;
//
//            Pair src = biGraph.getLocation(biGraph.getG().getEdgeSource(e));
//            Pair dest =  biGraph.getLocation(biGraph.getG().getEdgeTarget(e));
//            for(DefaultEdge markedE : marked) {
//                Pair markedSrc = biGraph.getLocation(biGraph.getG().getEdgeSource(markedE));
//                Pair markedDest =  biGraph.getLocation(biGraph.getG().getEdgeTarget(markedE));
//                if (src == markedSrc && dest == markedDest)
//                {
//                    arrowColor =Color.RED;
//                }
//            }
//
//            // draw edge
//            graphParts.LineArrow line = new graphParts.LineArrow(src.x(), src.y(), dest.x(), dest.y(), arrowColor, 3);
//            line.draw(g);
//        }
//    }
//}