package GUI;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.HashMap;
import Module.bipartiteGraph;

public class graphParts {
    bipartiteGraph graph;

//    public static class Edge {
//        Node a,b;
//        int color, id;
//        static int countE = 0;
//        public Edge(Node a, Node b) {
//            this.a = a;
//            this.b = b;
//
//            // in my implementaion I added a color for the painting part, if we don't need we can delete
//            this.color = 0;
//            this.id = countE++;
//        }
//        public boolean setColor(int c){
//            if(c<0 || c>2){
//                return false;
//            }
//            this.color = c;
//            return true;
//        }
//        public int getColor(){
//            return this.color;
//        }
//    }
//    public static class Node {
//        public static int countN;
//        final int key;
//        int x,y;
//        public Node() {
//            this.key = countN++;
//        }
//    }

//    public static class Graph{
//        // the implementation is not so elegant but at the end i'll check about hashing- not kriti
//        HashMap<Integer, Node> nodeM;
//        HashMap<Integer, Edge> edgeM; // to get back all of the edges <id, edge>
//        HashMap<Node, HashMap<Node, Integer>> edgeI; // <src, <dest, edge.id>>
//
//        Graph(){
//            this.nodeM = new HashMap<Integer, Node>();
//            this.edgeM = new HashMap<Integer, Edge>();
//            this.edgeI = new HashMap<Node, HashMap<Node, Integer>>();
//        }
//        public void addNode(Node n){
//            nodeM.put(n.key, n);
//        }
//        public void addEdge(Edge e){
//            edgeM.put(e.id, e);
//
//            HashMap<Node, Integer> temp = new HashMap<Node, Integer>();
//            temp.put(e.a, e.id);
//            edgeI.put(e.b, temp);
//
//            temp = new HashMap<Node, Integer>();
//            temp.put(e.b, e.id);
//            edgeI.put(e.a, temp);
//        }
//
//        public Collection<Edge> getAE(){
//            return edgeM.values();
//        }
//    }

    private static final Polygon ARROW_HEAD = new Polygon();
    static {
        ARROW_HEAD.addPoint(0, 0);
        ARROW_HEAD.addPoint(-5, -10);
        ARROW_HEAD.addPoint(5, -10);
    }
    public static class LineArrow {

        private final double x;
        private final double y;
        private final double endX;
        private final double endY;
        private final Color color;
        private final int thickness;

        public LineArrow(double x1, double y1, double x2, double y2, Color color, int thickness) {
            super();
            this.x = x1;
            this.y = y1;
            this.endX = x2;
            this.endY = y2;
            this.color = color;
            this.thickness = thickness;
        }

        public void draw(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            double angle = Math.atan2(endY - y, endX - x);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawLine((int)x, (int)y, (int)(endX - 10 * Math.cos(angle)), (int) (endY - 10 * Math.sin(angle)));
            AffineTransform tx1 = g2.getTransform();
            AffineTransform tx2 = (AffineTransform) tx1.clone();
            tx2.translate(endX, endY);
            tx2.rotate(angle - Math.PI / 2);
            g2.setTransform(tx2);
            g2.fill(ARROW_HEAD);
            g2.setTransform(tx1);
        }
    }
}
