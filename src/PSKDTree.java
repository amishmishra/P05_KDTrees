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
        Node newNode = new Node();
        newNode.p = p;
        newNode.v = v;
        Node finger;
        if(this.isEmpty()){
            newNode.dir = Partition.Direction.LEFTRIGHT;
            root = newNode;
        } else {
            finger = root;
            while (finger.left != null || finger.right != null) { //this while loop should get us to the bottom
                if (finger.dir == Partition.Direction.LEFTRIGHT) {
                    if (finger.p.x() >= newNode.p.x()) { //the new node is to the left
                        finger = finger.left;
                    } else {
                        finger = finger.right;
                    }
                } else {//the finger node is a down up partitioning node
                    if (finger.p.y() >= newNode.p.y()) { //the new node is below
                        finger = finger.left;
                    } else {
                        finger = finger.right;
                    }
                }
            }
            //at this point, we are at the bottom of the tree (finger is pointing to a leaf with no further leaves
            if (finger.dir == Partition.Direction.LEFTRIGHT) {
                newNode.dir = Partition.Direction.LEFTRIGHT;
                if (finger.p.x() >= newNode.p.x()) { //the new node is to the left
                    finger.left = newNode;
                } else {
                    finger.right = newNode;
                }
            } else {//the finger node is a down up partitioning node
                newNode.dir = Partition.Direction.DOWNUP;
                if (finger.p.y() >= newNode.p.y()) { //the new node is below
                    finger.left = newNode;
                } else {
                    finger.right = newNode;
                }
            }
        }

    }

    public Value get(Point p) {
        if (this.isEmpty()) {
            return null;
        }
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
    public boolean isEmpty() {
        return root==null; }

    // place your timing code or unit testing here
    public static void main(String[] args) {
    }

}
