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
    private int treeSize;
    // constructor makes empty kD-tree
    public PSKDTree() {
        listOfPoints = new ArrayList<Point>(); // an iterable

    }

    // TODO update the direction. Currently it just sticks to LeftRight
    // using something like: finger.dir = Partition.nextDirection(finger.dir);
    // I'm confused about how this works though...I asked a question on Piazza!
    // TODO (like the dog, except spelled a little different!)

    // add the given Point to kD-tree
    public void put(Point p, Value v) {
        Node newNode = new Node();
        newNode.p = p;
        newNode.v = v;
        Node finger;
        if(this.isEmpty()){
            newNode.dir = Partition.Direction.LEFTRIGHT; //our root will always start out LEFTRIGHT
            root = newNode;
        } else {
            finger = root;
            while (true) {
                //comparing x's
                double fingerValue = finger.p.xy(finger.dir); //pull the value of finger based on the direction we are on
                double newNodeValue = newNode.p.xy(finger.dir);
                if (newNodeValue < fingerValue) { //go down the left
                    if (finger.left == null) {
                        if (!this.contains(newNode.p)) {
                            listOfPoints.add(newNode.p);
                            StdOut.println(newNode.p);
                        }
                        finger.left = newNode;
                        treeSize++;
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
                        treeSize++;
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
        Queue<Partition> q = new Queue<>();
        Partition partition;

        partition = new Partition(listOfPoints.get(0),listOfPoints.get(1), Partition.Direction.LEFTRIGHT);
        q.enqueue(partition);



        for(Point pt : listOfPoints){


            q.enqueue(partition);
        }

        return q;
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
    public int size() { return treeSize; } //incremented in put, which is fine since we have no delete for our K-D Tree

    // return whether the kD-tree is empty
    public boolean isEmpty() {
        return root==null; }

    // place your timing code or unit testing here
    public static void main(String[] args) {
    }

}
