package GUI;

import Module.Edmonds;
import Module.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.util.*;


class Edmonds_Panel extends JPanel{


    // == GRAPH STUFF ==
    SimpleGraph<Integer, DefaultEdge> _graph;
    static int key = 0;
    Set<DefaultEdge> marked = new HashSet<>();// set of marked edges

    private HashMap<Integer, Point> Rvertices_locations = new HashMap<>();
    private HashMap<Point, Integer> vertices_locations = new HashMap<>();
    Point pointStart = null;
    Point pointEnd= null;
    Point dropPoint= null;
    static
    private double vertexClickRadius;
    int startKey=-1, endKey=-1;
    boolean setEdge = false;
    boolean setNode = false;

    public Edmonds_Panel() {

        // === INIT GRAPH ===
        newGraph();

        // === GRAPHICS ===
        this.setLayout(new BorderLayout());

        this.setBackground(new Color(86, 102, 144));

        addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(setEdge){
                    pointStart = getClosestVertex(e.getPoint());

                    if (pointStart != null)
                        startKey = vertices_locations.get(pointStart);
                }
                else if(setNode){
                    dropPoint = e.getPoint();
                }
            }

            public void mouseReleased(MouseEvent e) {
                if(setEdge){
                    pointEnd = getClosestVertex(e.getPoint());
                    if (pointEnd != null)
                        endKey = vertices_locations.get(pointEnd);

                    setEdge();
                    repaint();

                    pointStart = null;
                    endKey= -1;
                    startKey =-1;
//                    setEdge = false;
                }
                else if(setNode){
                    setNode();
                    repaint();
                    dropPoint = null;
//                    setNode = false;
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {}

            public void mouseDragged(MouseEvent e) {
                if(setEdge) {
                    pointEnd = e.getPoint();
                    repaint();
                }
            }
        });
    }

    private Point getClosestVertex(Point clickLocation){
        for(Point p: vertices_locations.keySet()){
            if(clickLocation.distance(p)<vertexClickRadius)  {
                return p;
            }
        }
        return null;
    }

    // run algorithm
    public void runAlgo() {
        setEdge = false;
        setNode = false;
        marked.clear();
        _graph.removeVertex(0);
        Set<DefaultEdge> match = Edmonds.findMaximumMatching(_graph).edgeSet();
        marked.addAll(match);

        repaint();
    }

    // add edge given src and dest
    public void addEdge(){
        setNode = false;
        setEdge = true;
        repaint();
    }

    public void setEdge(){
        marked.clear();
        this._graph.addEdge(startKey,endKey);
    }

    // add random edge
    public void addRandomEdge() {
        setEdge = false;
        setNode = false;
        marked.clear();

        Set<Integer> nodes = _graph.vertexSet();
        int size = nodes.size(),  i = 0, next, src = -1, dest = 0;
        if(size<2){
            return;
        }
        int srcRand  = (new Random().nextInt(size));
        int destRand  = (new Random().nextInt(size-1));
        if (srcRand == destRand)
            destRand ++;
        
        for(Iterator<Integer> itr = nodes.iterator(); itr.hasNext(); i++){
            next = itr.next();
            if (i == srcRand) {
                src = next;
            }
            else if( i == destRand){
                dest = next;
            }

        }

        this._graph.addEdge(src,dest);
        repaint();
    }

    // build random graph
    public void randomizeGraph() {
        setEdge = false;
        setNode = false;
        newGraph();
        marked.clear();
        int size = (int) (Math.random() * 20) + 1;
        for (int i = 0; i < size; i++) {
            newRndNode();
        }

        int e = (int) (Math.random() * 20);
        for (int i = 0; i < e; i++) {
            addRandomEdge();
        }
    }

    private Point locateInCircle(int i){
        Set<Integer> nodes = _graph.vertexSet();
        double size = nodes.size();

        int height = this.getHeight()-50;
        int width = this.getWidth()-50;

        double angle = Math.toRadians(i*360/size);
        if(i % 2 == 0)
            angle*=-1;

        int centerX = width/2, centerY = height/2, x, y;
        double r = (Math.min(height, width)/2.5);

        x = (int) (centerX + r * Math.cos(angle));
        y = (int) (centerY + r * Math.sin(angle));

        return new Point(x,y);

    }

    // new node
    public void newNode() {
        setEdge = false;
        setNode = true;
        repaint();
    }

    public void newRndNode() {
        setEdge = false;
        setNode = false;

        _graph.addVertex(++key);
        Point point = locateInCircle(key - 1);
        vertices_locations.put(point, key);
        Rvertices_locations.put(key, point);
        repaint();
    }

    public void setNode(){
        _graph.addVertex(++key);
        vertices_locations.put(dropPoint, key);
        Rvertices_locations.put(key, dropPoint);
    }

    // reset graph
    public void newGraph(){
        setEdge = false;
        setNode = false;
        marked.clear();
        vertices_locations.clear();
        Rvertices_locations.clear();
        this._graph = new SimpleGraph<>(DefaultEdge.class);

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

        Set<Integer> nodes = _graph.vertexSet();
        Iterator<Integer> itr = nodes.iterator();

//        vertices_locations.clear();
//        Rvertices_locations.clear();

        // draw vertex
        vertexClickRadius = nodes.size()>1? Math.min((this.getHeight()-20)/(nodes.size()+1)*(2)-(this.getHeight()-20)/(nodes.size()+1), 20): 20;
        for (int i=0; i<nodes.size(); i++) {

            int vertex = itr.next();

            Color drawingColor = new Color(0xCE7474);

            //draw vertex
            if((!setEdge && !setNode)|| vertex == startKey)
                g.setColor(Color.RED);
            else
                g.setColor(drawingColor);

//            Point point = locateInCircle(i);
            Point point = Rvertices_locations.get(vertex);
            g.fillOval(point.x, point.y, 10, 10);


//            // save nodes locations
//            vertices_locations.put(point, vertex);
//            Rvertices_locations.put(vertex, point);

            // draw vertex key
            g.setColor(Color.BLUE);
            g.drawString(String.valueOf(vertex), point.x, point.y);
        }

        //draw Edges:
        // marked edges =>  red, regular edges => black
        Set<DefaultEdge> edges = _graph.edgeSet();
        for (DefaultEdge e : edges) {
            Color arrowColor = Color.BLACK;


            Point src = Rvertices_locations.get(_graph.getEdgeSource(e));
            Point dest = Rvertices_locations.get(_graph.getEdgeTarget(e));

            for(DefaultEdge markedE : marked) {
//                Point markedSrc = Rvertices_locations.get(_graph.getEdgeSource(e));
//                Point markedDest = Rvertices_locations.get(_graph.getEdgeTarget(e));

                if (/*src == markedSrc && dest == markedDest*/ edgesEqual(markedE, e, _graph))
                {
                    arrowColor =Color.RED;
                }
            }

            // draw edge
            graphParts.LineArrow line = new graphParts.LineArrow(src.x, src.y, dest.x, dest.y, arrowColor, 3);
            line.draw(g);
        }
    }

    private boolean edgesEqual(DefaultEdge first, DefaultEdge second, SimpleGraph<Integer, DefaultEdge> g){
        int firstSrc = g.getEdgeSource(first);
        int firstDst = g.getEdgeTarget(first);
        int secondSrc = g.getEdgeSource(second);
        int secondDst = g.getEdgeTarget(second);

        boolean result = false;
        if(firstSrc == secondSrc){
            if(firstDst == secondDst){
                result = true;
            }
        }
        else if(firstSrc == secondDst){
            if(firstDst == secondSrc){
                result = true;
            }
        }
        return result;
    }
}