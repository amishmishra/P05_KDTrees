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

    // constructor makes empty kD-tree
    public PSKDTree() {

    }

    // add the given Point to kD-tree
    public void put(Point p, Value v) {
        Node newNode = new Node;
        newNode.p = p;
        newNode.v = v;
        if(this.isEmpty()){
            root = newNode;
        } else{
            Node finger;
            finger = root;
            while(finger.left != null || finger.right != null){

                if(newNode.dir == Partition.Direction.LEFTRIGHT && finger.p.x() >= newNode.p.x()){
                    finger = finger.left;
                } else {
                    finger = finger.right;
                }
            }
        }

    }

    public Value get(Point p) {
        return null;
    }

    public boolean contains(Point p) {
        return false;
    }

    public Value getNearest(Point p) {
        return null;
    }

    // return an iterable of all points in collection
    public Iterable<Point> points() { return null; }

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
    public boolean isEmpty() { return true; }

    // place your timing code or unit testing here
    public static void main(String[] args) {
    }

}
