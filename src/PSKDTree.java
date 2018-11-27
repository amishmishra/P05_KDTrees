import sun.security.util.Length;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

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
    private Point minPoint;
    private Point maxPoint;
    // constructor makes empty kD-tree
    public PSKDTree() {
        listOfPoints = new ArrayList<Point>(); // an iterable

    }

    // TODO update the direction. Currently it just sticks to LeftRight
    // using something like: finger.dir = Partition.nextDirection(finger.dir);
    // I'm confused about how this works though...I asked a question on Piazza!

    // add the given Point to kD-tree
    public void put(Point p, Value v) {
        Node newNode = new Node();
        newNode.p = p;
        newNode.v = v;
        Node finger;
        if(this.isEmpty()){
            newNode.dir = Partition.Direction.LEFTRIGHT; //our root will always start out LEFTRIGHT
            root = newNode;
            listOfPoints.add(root.p);
            minPoint = root.p;
            maxPoint = root.p;
        } else {
            finger = root;
            if (p.x() <= minPoint.x()) { //p's x is smaller than min
                minPoint = new Point(p.x(), minPoint.y());
            }
            if (p.y() <= minPoint.y()) { //p's y is smaller than min
                minPoint = new Point(minPoint.x(),p.y());
            }
            if (p.x() >= maxPoint.x()) { //p's x is larger than max
                maxPoint = new Point(p.x(), maxPoint.y());
            }
            if (p.y() >= maxPoint.y()) { //p's y is larger than max
                maxPoint = new Point(maxPoint.x(),p.y());
            }
            while (finger != null) {
                double fingerValue = finger.p.xy(finger.dir);
                double pValue = p.xy(finger.dir);
                if (pValue < fingerValue) {
                    finger = finger.left;
                }
                else if (pValue > fingerValue) {
                    finger = finger.right;
                }
                else {
                    return null;
                }
            }
            finger = newNode;
            treeSize++;
            if (!this.contains(newNode.p)) {
                listOfPoints.add(newNode.p);
            }
//            while (true) {
//                //comparing x's
//                double fingerValue = finger.p.xy(finger.dir); //pull the value of finger based on the direction we are on
//                double newNodeValue = newNode.p.xy(finger.dir);
//                if (newNodeValue < fingerValue) { //go down the left
//                    if (finger.left == null) {
//                        if (!this.contains(newNode.p)) {
//                            listOfPoints.add(newNode.p);
//                        }
//                        finger.left = newNode;
//                        treeSize++;
//                        break; //baby
//                    } else {
//                        finger = finger.left;
//                    }
//                } else { //go down the right
//                    if (finger.right == null) {
//                        if (!this.contains(newNode.p)) {
//                            listOfPoints.add(newNode.p);
//                        }
//                        finger.right = newNode;
//                        treeSize++;
//                        break; //baby
//                    } else {
//                        finger = finger.right;
//                    }
//                }
//            }
        }
    }

    public Value get(Point p) {
        Node finger;
        if(this.isEmpty()){
            return null;
        } else {
            finger = root;
            while (finger != null) {
                double fingerValue = finger.p.xy(finger.dir);
                double pValue = p.xy(finger.dir);
                if (pValue < fingerValue) {
                    finger = finger.left;
                }
                else if (pValue > fingerValue) {
                    finger = finger.right;
                }
                else {
                    return finger.v;
                }
            }
            return null;
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
        return get(nearest(p));
    }

    // return an iterable of all points in collection
    public Iterable<Point> points() { return listOfPoints; }

    // return an iterable of all partitions that make up the kD-tree
    public Iterable<Partition> partitions() {
        Queue<Partition> q = new Queue<>();
        Partition partition;

//        partition = new Partition(listOfPoints.get(0),listOfPoints.get(1), Partition.Direction.LEFTRIGHT);
//        q.enqueue(partition);

        for(Point pt : listOfPoints){
            Node finger;
            finger = root;
            Node node = new Node();
            while (true) {
                //comparing x's
                double fingerValue = finger.p.xy(finger.dir);
                double pValue = pt.xy(finger.dir);
                if (pValue < fingerValue) { //go down the left
                    if (finger.left == null) {
                        node = null;
                        break;
                    } else {
                        if (finger.left.p.equals(pt)) {
                            node = finger;
                            break;
                        }
                        finger = finger.left;
                    }
                } else { //go down the right
                    if (finger.right == null) {
                        node = null;
                        break;
                    } else {
                        if (finger.right.p.equals(pt)) {
                            node = finger;
                            break;
                        }
                        finger = finger.right;
                    }
                }
            }
//            partition = new Partition(pt, finger.dir);
//            q.enqueue(pt);
        }

        return q;
    }

    private void kNearestTraverse(Node n, Point p, MaxPQ<PointDist> kNearest, int k) {
        if (n == null) {
            return;
        }
        Comparator<Point> distCalc = p.distanceToComparator();
        if (kNearest.size() < k){
            kNearest.insert(new PointDist(n.p, n.p.dist(p)));

        }
        else {
            PointDist minDistP = kNearest.max();
            if (distCalc.compare(n.p, minDistP.p()) < 0) { //found a new close enough point!
                kNearest.insert(new PointDist(n.p, n.p.dist(p)));
                if (kNearest.size() > k) {
                    kNearest.delMax();
                }
            }
        }

        //TODO: Pruning
        double distToPartition = n.p.xy(n.dir) - p.xy(n.dir);
        if (distToPartition < 0 ){
            kNearestTraverse(n.right, p, kNearest, k);
            if (kNearest.max().d() > distToPartition) {
                kNearestTraverse(n.left, p, kNearest, k);
            }
        } else {
            kNearestTraverse(n.left, p, kNearest, k);
            if (kNearest.max().d() > distToPartition) {
                kNearestTraverse(n.right, p, kNearest, k);
            }
        }
    }



    // return the Point that is closest to the given Point
    public Point nearest(Point p) {
        Node n = root;
        MaxPQ<PointDist> kNearest = new MaxPQ<>();
        kNearestTraverse(n, p, kNearest, 1);
        return kNearest.max().p();
    }

    // return the k nearest Points to the given Point
    public Iterable<Point> nearest(Point p, int k) throws IllegalArgumentException{
        if (p == null || k < 0) {
            throw new IllegalArgumentException("Woah there! Stay in your limits, boi.");
        }
        else if (k == 0) {
            return null;
        }
        Node n = root;
        MaxPQ<PointDist> kNearest = new MaxPQ<>();
        kNearestTraverse(n, p, kNearest, k);
        Stack<Point> stackOfKNearest = new Stack<Point>();
        for (PointDist ptDist: kNearest) {
            stackOfKNearest.push(ptDist.p());
        }
        return stackOfKNearest;
    }

    // return the min and max for all Points in collection.
    // The min-max pair will form a bounding box for all Points.
    // if kD-tree is empty, return null.
    public Point min() { return minPoint; }
    public Point max() { return maxPoint; }

    // return the number of Points in kD-tree
    public int size() { return treeSize; } //incremented in put, which is fine since we have no delete for our K-D Tree

    // return whether the kD-tree is empty
    public boolean isEmpty() {
        return root==null; }

    // place your timing code or unit testing here
    public static void main(String[] args) {
    }

}
