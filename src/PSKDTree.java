import java.util.ArrayList;
import java.util.Iterator;

/**
 * PSKDTree is a Point collection that provides nearest neighbor searching using
 * 2d tree
 */
public class PSKDTree<Value> implements PointSearch<Value> {

    private class Node {
        Point p;
        Value v;
        Node left, right;
        Partition.Direction dir;
    }

    private Node root;
    private ArrayList<Point> listOfPoints; //stackOfPoints;
    // constructor makes empty kD-tree
    public PSKDTree() {
        listOfPoints = new ArrayList<Point>();
    }

    // add the given Point to kD-tree
    public void put(Point p, Value v) {
        Node newNode = new Node();
        newNode.p = p;
        newNode.v = v;
        Node finger;
        if(this.isEmpty()){
            newNode.dir = Partition.Direction.LEFTRIGHT;
            root = newNode;
        } else {
            finger = root;
            while (true) {
                //comparing x's
                double fingerValue = finger.p.xy(finger.dir);
                double newNodeValue = newNode.p.xy(finger.dir);
                if (newNodeValue < fingerValue) { //go down the left
                    if (finger.left == null) {
                        if (!this.contains(newNode.p)) {
                            listOfPoints.add(newNode.p);
                            StdOut.println(newNode.p);
                        }
                        finger.left = newNode;
                        break; //baby
                    } else {
                        finger = finger.left;
                    }
                } else { //go down the right
                    if (finger.right == null) {
                        if (!this.contains(newNode.p)) {
                            listOfPoints.add(newNode.p);
                            StdOut.println(newNode.p);
                        }
                        finger.right = newNode;
                        break; //baby
                    } else {
                        finger = finger.right;
                    }
                }
            }
        }
    }

    public Value get(Point p) {
        Node finger;
        if(this.isEmpty()){
            return null;
        } else {
            finger = root;
            while (true) {
                //comparing x's
                double fingerValue = finger.p.xy(finger.dir);
                double pValue = p.xy(finger.dir);
                if (pValue < fingerValue) { //go down the left
                    if (finger.left == null) {
                        return null;
                    } else {
                        if (finger.left.p.equals(p)) {
                            return finger.v;
                        }
                        finger = finger.left;
                    }
                } else { //go down the right
                    if (finger.right == null) {
                        return null;
                    } else {
                        if (finger.right.p.equals(p)) {
                            return finger.v;
                        }
                        finger = finger.right;
                    }
                }
            }
        }
    }

    public boolean contains(Point p) {
        Node finger;
        if (this.isEmpty()) {
            return false;
        } else {
            finger = root;
            while (true) {
                //comparing x's
                double fingerValue = finger.p.xy(finger.dir);
                double pValue = p.xy(finger.dir);
                if (pValue < fingerValue) { //go down the left
                    if (finger.left == null) {
                        return false;
                    } else {
                        if (finger.left.p.equals(p)) {
                            return true;
                        }
                        finger = finger.left;
                    }
                } else { //go down the right
                    if (finger.right == null) {
                        return false;
                    } else {
                        if (finger.right.p.equals(p)) {
                            return true;
                        }
                        finger = finger.right;
                    }
                }
            }
        }
    }

    public Value getNearest(Point p) {
        return null;
    }

    // return an iterable of all points in collection
    public Iterable<Point> points() { return listOfPoints; }

    // return an iterable of all partitions that make up the kD-tree
    public Iterable<Partition> partitions() {
        return null;
    }

    // return the Point that is closest to the given Point
    public Point nearest(Point p) {
        return null;
    }

    // return the k nearest Points to the given Point
    public Iterable<Point> nearest(Point p, int k) {
        return null;
    }

    // return the min and max for all Points in collection.
    // The min-max pair will form a bounding box for all Points.
    // if kD-tree is empty, return null.
    public Point min() { return null; }
    public Point max() { return null; }

    // return the number of Points in kD-tree
    public int size() { return 0; }

    // return whether the kD-tree is empty
    public boolean isEmpty() {
        return root==null; }

    // place your timing code or unit testing here
    public static void main(String[] args) {
    }

}
