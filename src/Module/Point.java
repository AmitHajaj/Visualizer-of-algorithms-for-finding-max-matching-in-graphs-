package Module;

import org.jetbrains.annotations.NotNull;

/**
 * This class represents a geo location (x,y,z), aka Point3D
 */
public class Point {
    private double x,y;

    /**
     * empty constructor
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * constructor
     * @param x x Location
     * @param y y Location
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * copy constructor
     * @param other Point
     */
    public Point(Point other) {
        this.x = other.x();
        this.y = other.y();
    }

    
    public double x() {
        return x;
    }
    
    public double y() {
        return y;
    }

    /**
     * return the dist from this to other
     * @param other geo_location other
     */
    public double distance(Point other) {
        //3d distance = ((x2 - x1)2 + (y2 - y1)2 + (z2 - z1)2)1/2
        return Math.sqrt(Math.pow(this.x-other.x(), 2) + Math.pow(this.y-other.y(), 2));
    }
    
//    /**
//     * Compares this Point with the specified object for order.
//     * @param o other Point
//     */
//    public int compareTo(@NotNull Point o) {
//        Point firsAxes = new Point(0, 0);
//
//        if (distance(firsAxes) > o.distance(firsAxes)) {
//            return 1;
//        }
//        if (distance(firsAxes) < o.distance(firsAxes)) {
//            return -1;
//        }
//        return 0;
//    }

}