/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package B;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com>
 */
public class Solution {

    public static void main(String[] args) {

        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        String line;
        try {
            line = in.readLine();
            int screens = Integer
                    .parseInt(line.substring(0, line.indexOf(' ')));
            int links = Integer.parseInt(line.substring(line.indexOf(' ') + 1));

            WeightedGraph wg = new WeightedGraph(screens);
            int weight = 0;
            int c1, c2, p;
            for (int i = 0; i < links; i++) {
                line = in.readLine();
                c1 = Integer.parseInt(line.substring(0, line.indexOf(' '))) - 1;
                c2 = Integer.parseInt(line.substring(line.indexOf(' ') + 1,
                        line.lastIndexOf(' '))) - 1;
                p = Integer.parseInt(line.substring(line.lastIndexOf(' ') + 1));
                wg.addDiEdge(new WeightEdge(c1, c2, p));
                wg.addDiEdge(new WeightEdge(c2, c1, p));
            }
            Dijkstra d = new Dijkstra(wg, 0);

            ArrayList<Integer> eliminated = new ArrayList();

            for (WeightEdge edge : d.pathTo(screens - 1)) {
                // System.out.println("source: "+edge.getSource()+" destiny: "+edge.getDestiny() +"weight: "+edge.getWeight());
                weight += edge.getWeight();
                if (edge.getDestiny() != screens - 1) {
                    eliminated.add(edge.getDestiny());
                }
                if (edge.getSource() != 0) {
                    eliminated.add(edge.getSource());
                }
            }

            // eliminated.add(3);
            WeightedGraph reverse = new WeightedGraph(screens);
            //System.out.println(eliminated);
            for (WeightEdge edge : wg.edges()) {

                if (eliminated.contains(edge.getDestiny()) || eliminated.contains(edge.getSource())) {
                    //    System.out.println("elimino "+ edge.getDestiny() +"-"+edge.getSource());
                    continue;
                }
                //    System.out.println("guardo "+ edge.getDestiny() +"-"+edge.getSource());
                reverse.addDiEdge(edge);
                reverse.addDiEdge(new WeightEdge(edge.getDestiny(), edge.getSource(), edge.getWeight()));
            }

            Dijkstra d2 = new Dijkstra(reverse, screens - 1);
            //System.out.println(d2.hasPathTo(0));
            for (WeightEdge edge : d2.pathTo(0)) {
                // System.out.println("source: "+edge.getSource()+" destiny: "+edge.getDestiny()+" peso: "+edge.getWeight());
                weight += edge.getWeight();
            }

            System.out.println(weight);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
}

class Dijkstra {
    //Elements for Relaxation (maintain min distances and path)

    private double[] distTo;
    private WeightEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public Dijkstra(WeightedGraph G, int s) {

        distTo = new double[G.getVertices()];
        edgeTo = new WeightEdge[G.getVertices()];

        for (int i = 0; i < distTo.length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        pq = new IndexMinPQ(G.getVertices());

        pq.insert(s, distTo[s]);

        while (!pq.isEmpty()) {
            int v = pq.delMin();

            for (WeightEdge edge : G.adj(v)) {
                double alt = distTo[v] + edge.getWeight();
                if (distTo[edge.getDestiny()] > alt) {
                    distTo[edge.getDestiny()] = alt;
                    edgeTo[edge.getDestiny()] = edge;
                    if (pq.contains(edge.getDestiny())) {
                        pq.change(edge.getDestiny(), distTo[edge.getDestiny()]);
                    } else {
                        pq.insert(edge.getDestiny(), distTo[edge.getDestiny()]);
                    }
                }
            }
        }

    }
    // length of shortest path from s to v

    public double distTo(int v) {
        return distTo[v];
    }

    // is there a path from s to v?
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // shortest path from s to v as an Iterable, null if no such path
    public Iterable<WeightEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        LinkedList<WeightEdge> path = new LinkedList();
        for (WeightEdge e = edgeTo[v]; e != null; e = edgeTo[e.getSource()]) {
            path.push(e);
        }
        return path;
    }
}

class WeightEdge implements Comparable<WeightEdge> {

    private final int source;
    private final int destiny;
    private final double weight;

    public WeightEdge(int source, int destiny, double weight) {
        this.source = source;
        this.destiny = destiny;
        this.weight = weight;
    }

    public int getSource() {
        return source;
    }

    public int getDestiny() {
        return destiny;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(WeightEdge o) {
        if (this.weight < o.weight) {
            return -1;
        }
        if (this.weight > o.weight) {
            return 1;
        } else {
            return 0;
        }
    }

    public String toString() {
        return String.format("%d-%d %.5f", source, destiny, weight);
    }
}

class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {

    private int NMAX;        // maximum number of elements on PQ
    private int N;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing
    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys;      // keys[i] = priority of i

    public IndexMinPQ(int NMAX) {
        if (NMAX < 0) {
            throw new IllegalArgumentException();
        }
        this.NMAX = NMAX;
        keys = (Key[]) new Comparable[NMAX + 1];    // make this of length NMAX??
        pq = new int[NMAX + 1];
        qp = new int[NMAX + 1];                   // make this of length NMAX??
        for (int i = 0; i <= NMAX; i++) {
            qp[i] = -1;
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean contains(int i) {
        if (i < 0 || i >= NMAX) {
            throw new IndexOutOfBoundsException();
        }
        return qp[i] != -1;
    }

    public int size() {
        return N;
    }

    public void insert(int i, Key key) {
        if (i < 0 || i >= NMAX) {
            throw new IndexOutOfBoundsException();
        }
        if (contains(i)) {
            throw new NoSuchElementException("item is already in pq");
        }
        N++;
        qp[i] = N;
        pq[N] = i;
        keys[i] = key;
        swim(N);
    }

    public int minIndex() {
        if (N == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return pq[1];
    }

    public Key minKey() {
        if (N == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return keys[pq[1]];
    }

    public int delMin() {
        if (N == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        int min = pq[1];
        exch(1, N--);
        sink(1);
        qp[min] = -1;            // delete
        keys[pq[N + 1]] = null;    // to help with garbage collection
        pq[N + 1] = -1;            // not needed
        return min;
    }

    public Key keyOf(int i) {
        if (i < 0 || i >= NMAX) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in pq");
        } else {
            return keys[i];
        }
    }

    @Deprecated
    public void change(int i, Key key) {
        changeKey(i, key);
    }

    public void changeKey(int i, Key key) {
        if (i < 0 || i >= NMAX) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in pq");
        }
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    public void decreaseKey(int i, Key key) {
        if (i < 0 || i >= NMAX) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in pq");
        }
        if (keys[i].compareTo(key) <= 0) {
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        }
        keys[i] = key;
        swim(qp[i]);
    }

    public void increaseKey(int i, Key key) {
        if (i < 0 || i >= NMAX) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("item is not in pq");
        }
        if (keys[i].compareTo(key) >= 0) {
            throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
        }
        keys[i] = key;
        sink(qp[i]);
    }

    public void delete(int i) {
        if (i < 0 || i >= NMAX) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("item is not in pq");
        }
        int index = qp[i];
        exch(index, N--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

    /**
     * ************************************************************
     * General helper functions
    *************************************************************
     */
    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    /**
     * ************************************************************
     * Heap helper functions
    *************************************************************
     */
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    /**
     * *********************************************************************
     * Iterators
    *********************************************************************
     */
    /**
     * Return an iterator that iterates over all of the elements on the priority
     * queue in ascending order.
     * <p>
     * The iterator doesn't implement <tt>remove()</tt> since it's optional.
     */
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq

        private IndexMinPQ<Key> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for (int i = 1; i <= N; i++) {
                copy.insert(pq[i], keys[pq[i]]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMin();
        }
    }
}

class WeightedGraph {

    private final int vertices;
    private int edges;
    private LinkedList<WeightEdge>[] adj;

    public WeightedGraph(int vertices) {
        if (vertices < 0) {
            throw new RuntimeException("No negative vertices are possible");
        }
        this.vertices = vertices;
        this.edges = 0;
        adj = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public void addUnEdge(WeightEdge e) {
        int source = e.getSource();
        int destiny = e.getDestiny();
        adj[source].push(e);
        adj[destiny].push(e);
        edges++;
    }

    public void addDiEdge(WeightEdge e) {
        int source = e.getSource();
        int destiny = e.getDestiny();
        adj[source].push(e);
        edges++;
    }

    public Iterable<WeightEdge> adj(int v) {
        if (v < 0 || v >= vertices) {
            throw new IndexOutOfBoundsException();
        }
        return adj[v];
    }

    public Iterable<WeightEdge> edges() {
        LinkedList<WeightEdge> list = new LinkedList<>();
        for (int v = 0; v < vertices; v++) {
            int selfLoops = 0;
            for (WeightEdge e : adj(v)) {
                if (e.getDestiny() > v) {
                    list.add(e);
                } //esto funciona por que al almacenar el edge, no cambia
                //la posiciones de sitio y lo detecta
                // only add one copy of each self loop
                else if (e.getDestiny() == v) {
                    if (selfLoops % 2 == 0) {
                        list.add(e);
                    }
                    selfLoops++;
                }
            }
        }
        return list;
    }
}
