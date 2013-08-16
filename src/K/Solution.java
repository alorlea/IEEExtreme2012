/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package K;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *@author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com>
 */
public class Solution {

    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        String line;
        ArrayList<WeightEdge> edges = new ArrayList();
        while ((line = in.readLine()) != null) {
            String[] temp = line.split(" ");
            int source = Integer.parseInt(temp[0]);
            int destiny = Integer.parseInt(temp[1]);
            edges.add(new WeightEdge(source - 1, destiny - 1, 0));
        }
    }
}

class Graph {

    private final int V;
    private int E;
    //private StackArray<Integer>[] adj;
    private LinkedList<Integer>[] adj;

    public Graph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be no-negative");
        }
        this.V = V;
        this.E = 0;
        //adj = (StackArray<Integer>[]) new StackArray[V];
        adj = new LinkedList[V];
        for (int v = 0; v < V; v++) {
            //adj[v]= new StackArray<>();
            adj[v] = new LinkedList();
        }
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }

    /**
     * Add the undirected edge v-w to Graph
     *
     * @param v
     * @param w
     */
    public void addUnEdge(int v, int w) {
        if (v < 0 || v >= V) {
            throw new IndexOutOfBoundsException();
        }
        if (w < 0 || w >= V) {
            throw new IndexOutOfBoundsException();
        }
        E++;
        adj[v].push(w);
        adj[w].push(v);
    }

    /**
     * Add the directed edge v-w to Graph
     *
     * @param v
     * @param w
     */
    public void addDiEdge(int v, int w) {
        if (v < 0 || v >= V) {
            throw new IndexOutOfBoundsException();
        }
        if (w < 0 || w >= V) {
            throw new IndexOutOfBoundsException();
        }
        E++;
        adj[v].push(w);
    }

    /**
     * Get Neighbour list of that vertex out edges also for directed case
     *
     * @param v
     * @return
     */
    public Iterable<Integer> adj(int v) {
        if (v < 0 || v >= V) {
            throw new IndexOutOfBoundsException();
        }
        return adj[v];
    }

    public boolean hasEdge(int i, int j) {
        return adj[i].contains(j);
    }

    public List<Integer> inEdges(int i) {
        List<Integer> edges = new LinkedList<>();
        for (int j = 0; j < V; j++) {
            if (adj[j].contains(i)) {
                edges.add(j);
            }
        }
        return edges;
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