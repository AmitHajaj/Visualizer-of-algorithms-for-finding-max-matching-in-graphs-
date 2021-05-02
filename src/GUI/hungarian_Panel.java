package GUI;

import Module.*;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.*;


class hungarian_Panel extends JPanel{

    private static final long serialVersionUID = 1L;
    // == GRAPH STUFF ==
    bipartiteGraph biGraph;
    Set<DefaultEdge> marked = new HashSet<>();// set of marked edges
    private HashSet<Integer> pointsA;
    private HashSet<Integer> pointsB;
    private HashMap<Point, Integer> vertices_locations = new HashMap<>();
    Point pointStart = null;
    Point pointEnd= null;
    private double vertexClickRadius;
    int startKey=-1, endKey=-1;
    boolean setNode = false;

    public hungarian_Panel() {

        // === INIT GRAPH ===
        newGraph();

        // === GRAPHICS ===
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(153, 153, 153));

        addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(setNode){
                    pointStart = getClosestVertex(e.getPoint());

                    if (pointStart != null)
                        startKey = vertices_locations.get(pointStart);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if(setNode){
                    pointEnd = getClosestVertex(e.getPoint());
                    if (pointEnd != null)
                        endKey = vertices_locations.get(pointEnd);

                    setEdge();
                    repaint();

                    pointStart = null;
                    endKey= -1;
                    startKey =-1;
                    setNode = false;
                }
            }
        });
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {

                }

                public void mouseDragged(MouseEvent e) {
                    if(setNode) {
                        pointEnd = e.getPoint();
                        repaint();
                    }
                }
            });
        }


    private Point getClosestVertex(Point clickLocation){
        for(Point p: vertices_locations.keySet()){
            if(clickLocation.distance(p)<vertexClickRadius) {
                return p;
            }
        }
        return null;
    }

    // run algorithm
    public void runAlgo() {
        marked.clear();

        Hungarian_Method algo = new Hungarian_Method(biGraph);
        marked = algo.Hungarian(biGraph);

        repaint();
    }

    // add edge given src and dest
    public void addEdge(){
        setNode = true;
        repaint();
    }

    public void setEdge(){
        marked.clear();
        this.biGraph.addEdge(startKey,endKey);
    }

    // add random edge
    public void addRandomEdge() {
        marked.clear();
        int size = pointsA.size();
        if(size==0) return;

        int src = (new Random().nextInt(size))*2;
        int dest = (new Random().nextInt(size))*2+1;

        this.biGraph.addEdge(src,dest);
        repaint();
    }

    // build random graph
    public void randomizeGraph() {
        newGraph();
        marked.clear();
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

        int n = pointsA.size();
        this.biGraph.addToA(n*2);
        this.biGraph.addToB(n*2 + 1);
        repaint();
    }

    // reset graph
    public void newGraph() {
        marked.clear();
        this.biGraph = new bipartiteGraph();
        this.pointsA = biGraph.getA();
        this.pointsB = biGraph.getB();
        repaint();
    }

    //paint graph
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pointStart != null) {
            g.setColor(Color.BLACK);
            g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
        }

        int n = pointsA.size();
        Iterator<Integer> iterA = pointsA.iterator();
        Iterator<Integer> iterB = pointsB.iterator();

        vertices_locations.clear();
        // draw vertex
        vertexClickRadius = n>1? Math.min((this.getHeight()-20)/(n+1)*(2)-(this.getHeight()-20)/(n+1), 20): 20;
        for (int i=0; i<n; i++) {

            // draw oval
            int y = (this.getHeight()-20)/(n+1)*(i+1);
            int x_a = this.getWidth()/10*6;
            int x_b = this.getWidth()/10*9;

            // set vertex location
            Integer a_Point = iterA.next();
            Integer b_Point = iterB.next();
            biGraph.setLocation(a_Point,x_a,y);
            biGraph.setLocation(b_Point,x_b,y);

            Color drawingColor = new Color(0xCE7474);
            //draw vertex

            if(!setNode || a_Point == startKey)
                g.setColor(Color.RED);
            else
                g.setColor(drawingColor);

            g.fillOval(x_a-5, y-5, 10, 10);

            if(!setNode || b_Point == startKey)
                g.setColor(Color.RED);
            else
                g.setColor(drawingColor);

            g.fillOval(x_b-5, y-5, 10, 10);

            // save nodes locations
            vertices_locations.put(new Point(x_a, y), a_Point);
            vertices_locations.put(new Point(x_b, y), b_Point);

            // draw vertex key
            g.setColor(Color.BLUE);
            g.drawString(""+a_Point, x_b+20, y+5);
            g.drawString(""+b_Point, x_a-20, y+5);
        }

        //draw Edges:
        // marked edges =>  red, regular edges => black

        Set<DefaultEdge> edges = biGraph.getEdges();
        for (DefaultEdge e : edges) {

            Color arrowColor = Color.BLACK;

            Pair src = biGraph.getLocation(biGraph.getG().getEdgeSource(e));
            Pair dest =  biGraph.getLocation(biGraph.getG().getEdgeTarget(e));
            for(DefaultEdge markedE : marked) {
                Pair markedSrc = biGraph.getLocation(biGraph.getG().getEdgeSource(markedE));
                Pair markedDest =  biGraph.getLocation(biGraph.getG().getEdgeTarget(markedE));
                if (src == markedSrc && dest == markedDest)
                {
                    arrowColor =Color.RED;
                }
            }

            // draw edge
            graphParts.LineArrow line = new graphParts.LineArrow(src.x(), src.y(), dest.x(), dest.y(), arrowColor, 3);
            line.draw(g);
        }
    }


}