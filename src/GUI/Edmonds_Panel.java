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
import java.io.Serial;
import java.util.*;


class Edmonds_Panel extends JPanel{

    @Serial
    private static final long serialVersionUID = 1L;
    // == GRAPH STUFF ==
    SimpleGraph<Integer, DefaultEdge> _graph;
    static int key = 1;
    Set<DefaultEdge> marked = new HashSet<>();// set of marked edges

    private HashMap<Integer, Point> Rvertices_locations = new HashMap<>();
    private HashMap<Point, Integer> vertices_locations = new HashMap<>();
    Point pointStart = null;
    Point pointEnd= null;
    static
    private double vertexClickRadius;
    int startKey=-1, endKey=-1;
    boolean setNode = false;

    public Edmonds_Panel() {

        // === INIT GRAPH ===
        newGraph();

        // === GRAPHICS ===
        this.setLayout(new BorderLayout());

        this.setBackground(new Color(86, 102, 144));

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
            public void mouseMoved(MouseEvent e) {}

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
        _graph.removeVertex(0);

        marked = Edmonds.findMaximumMatching(_graph).edgeSet();

        repaint();
    }

    // add edge given src and dest
    public void addEdge(){
        setNode = true;
        repaint();
    }

    public void setEdge(){
        marked.clear();
        this._graph.addEdge(startKey,endKey);
    }

    // add random edge
    public void addRandomEdge() {
        marked.clear();

        Set<Integer> nodes = _graph.vertexSet();
        int size = nodes.size(),  i = 0, next, src = -1, dest = 0;
        if(size<2){
            return;
        }
        int rand  = (new Random().nextInt(size));
        
        for(Iterator<Integer> itr = nodes.iterator(); itr.hasNext(); i++){
            next = itr.next();
            if (i == rand) {
                if(src == -1){
                    src = next;
                    rand  = (new Random().nextInt(size-1));
                }else {
                    dest = next;
                    break;
                }
            }
            itr.remove();
            itr = nodes.iterator();
        }

        this._graph.addEdge(src,dest);
        repaint();
    }

    // build random graph
    public void randomizeGraph() {
        newGraph();
        marked.clear();
        int size = (int) (Math.random() * 20) + 1;
        for (int i = 0; i < size; i++) {
            newNode();
        }

        int e = (int) (Math.random() * 20);
        for (int i = 0; i < e; i++) {
            addRandomEdge();
        }
    }

    private Point locateInCircle(int i){
        Set<Integer> nodes = _graph.vertexSet();
        Iterator<Integer> itr = nodes.iterator();
        double radius = 2*Math.PI;
        int r = 20, centerX = 506, centerY = 356, x, y;

        double currentAngle = i * radius / nodes.size();
        x = (int) (centerX + r * Math.cos(currentAngle));
        y = (int) (centerY + r * Math.sin(currentAngle));

        return new Point(x,y);

    }

    // new node
    public void newNode() {
        _graph.addVertex(++key);
        repaint();
    }

    // reset graph
    public void newGraph() {
        marked.clear();
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

        vertices_locations.clear();
        Rvertices_locations.clear();

        // draw vertex
        vertexClickRadius = nodes.size()>1? Math.min((this.getHeight()-20)/(nodes.size()+1)*(2)-(this.getHeight()-20)/(nodes.size()+1), 20): 20;
        for (int i=0; i<nodes.size(); i++) {

            int vertex = itr.next();

            Color drawingColor = new Color(0xCE7474);

            //draw vertex
            if(!setNode || vertex == startKey)
                g.setColor(Color.RED);
            else
                g.setColor(drawingColor);

            Point point = locateInCircle(i);

            g.fillOval(point.x, point.y, 10, 10);


            // save nodes locations
            vertices_locations.put(point, vertex);
            Rvertices_locations.put(vertex, point);

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
                Point markedSrc = Rvertices_locations.get(_graph.getEdgeSource(e));
                Point markedDest = Rvertices_locations.get(_graph.getEdgeTarget(e));

                if (src == markedSrc && dest == markedDest)
                {
                    arrowColor =Color.RED;
                }
            }

            // draw edge
            graphParts.LineArrow line = new graphParts.LineArrow(src.x, src.y, dest.x, dest.y, arrowColor, 3);
            line.draw(g);
        }
    }
}