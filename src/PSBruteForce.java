import java.util.Iterator;

/**
 * PSBruteForce is a Point collection that provides brute force
 * nearest neighbor searching using red-black tree.
 */
public class PSBruteForce<Value> implements PointSearch<Value> {
    // constructor makes empty collection
    private RedBlackBST<Point,Value> tree;
    private MinPQ<PointDist> q;
    private MinPQ<PointDist> pq;
    private PointDist pd;
    private double dist;

    public PSBruteForce() {
        tree = new RedBlackBST<>();

    }

    // add the given Point to KDTree
    public void put(Point p, Value v) {
        tree.put(p,v);
    }
    public Value get(Point p) {
        return tree.get(p);
    }
    public boolean contains(Point p) {
        return tree.contains(p);
    }
    // return an iterable of all points in collection
    public Iterable<Point> points() {
        return tree.keys();
    }

    // return the Point that is closest to the given Point
    public Point nearest(Point p) {
        pq = new MinPQ<>();
        for (Point pt : this.points()) { // will be a linear search because we look through everything
            dist = pt.dist(p.x(), p.y()); //the distance between Point p and the Point from the iterable of Points, pt
            pd = new PointDist(pt, dist); // pt with a distance from p
            pq.insert(pd);
        }
        if(pq.isEmpty()){
            return null;
        } else {
            return pq.delMin().p(); //stack would be fine too - you just want all at once I think
        }
    }

    // return the Value associated to the Point that is closest to the given Point
    public Value getNearest(Point p) {
        return tree.get(nearest(p));
    }
    // return the min and max for all Points in collection.
    // The min-max pair will form a bounding box for all Points.
    // if KDTree is empty, return null.
    public Point min() { return tree.min(); }
    public Point max() { return tree.max(); }

    // return the k nearest Points to the given Point
    public Iterable<Point> nearest(Point p, int k) {

        // uses the points() method and returns the min
        // PointDist.compareTo()
        //Iterable<Point> maxPQ = new <>();
        q = new MinPQ<>();

        for (Point pt : this.points()) { // will be a linear search because we look through everything
            dist = pt.dist(p.x(), p.y()); //the distance between Point p and the Point from the iterable of Points, pt
            pd = new PointDist(pt, dist); // pt with a distance from p
            q.insert(pd);

        }

        Stack<Point> s = new Stack<>();
        while(s.size() < k) {
           // System.out.printf("q.size: %d \n",s.size());
            if(q.isEmpty()){return null;}
            s.push(q.delMin().p());
        }
        if(s.isEmpty()){
            return null;
        } else {
            return s; //stack would be fine too - you just want all at once I think*/
        }

    }

    public Iterable<Partition> partitions() { return null; }

    // return the number of Points in KDTree
    public int size() { return tree.size(); }
    // return whether the KDTree is empty
    public boolean isEmpty() { return tree.isEmpty(); }


     private static PointSearch<Character> createNewPS() {
        return new PSBruteForce<>();
    }


    // place your timing code or unit testing here
    public static void main(String[] args) {
        ///tests/input100K.txt

        PointSearch<Character> ps = createNewPS();
        In in = new In(args[0]);

        int size = 1000000;
        double[] dbl,time;
        dbl = new double[size];
        int testing_size = 100;
        time = new double[testing_size];
        dbl = in.readAllDoubles();
        double elapsedTime=0;


        for(int i = 0; i<size-1; i=i+2){
            Point p = new Point(dbl[i],dbl[i+1]);
            ps.put(p,'r');
        }


        for(int j = 0; j<testing_size-1;j++) {
            Stopwatch rolex = new Stopwatch();
            ps.nearest(Point.uniform());
            time[j] =rolex.elapsedTime();

           // StdOut.printf("time elapsed: %f\n", time[j]);
            elapsedTime += time[j];

        }

        StdOut.printf("avg nearest calcs per sec: %f\n", 1/(elapsedTime/testing_size));

    }
}
