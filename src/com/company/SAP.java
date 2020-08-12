//package com.company;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Queue;

public class SAP {
    private Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.checkNull(G);
        this.graph = G;
    }

    private void deepanc(Digraph e, int s, BreadthFirstDirectedPaths k, int[] a) {
        Queue<Integer> queue = new Queue<>();
        boolean[] marked = new boolean[e.V()];
        int[] dist = new int[e.V()];
        for (boolean each : marked) {
            each = false;
        }
        marked[s] = true;
        dist[s] = 0;
        queue.enqueue(s);
        a[0] = -1;
        int champ = 10000000;
        while (!queue.isEmpty()) {
            int curr = queue.dequeue();
            if (k.hasPathTo(curr)) {
                if (champ > k.distTo(curr) + dist[curr]) {
                    a[0] = curr;
                    champ = k.distTo(curr) + dist[curr];
                }
            }
            for (int each : e.adj(curr)) {
                if (!marked[each]) {
                    marked[each] = true;
                    dist[each] = dist[curr] + 1;
                    queue.enqueue(each);
                }
            }
        }
    }

    private void deepanc(Digraph e, Iterable<Integer> s, BreadthFirstDirectedPaths k, int[] a) {
        ArrayList<Integer> array = new ArrayList<>();
        Queue<Integer> queue = new Queue<>();
        boolean[] marked = new boolean[e.V()];
        int[] dist = new int[e.V()];
        a[0] = -1;
        int champ = 10000000;
        for (int n : s) {
            marked[n] = true;
            dist[n] = 0;
            queue.enqueue(n);
        }
        while (!queue.isEmpty()) {
            int curr = queue.dequeue();
            if (k.hasPathTo(curr)) {
                if (champ > k.distTo(curr) + dist[curr]) {
                    a[0] = curr;
                    champ = k.distTo(curr) + dist[curr];
                }
            }
            for (int each : e.adj(curr)) {
                if (!marked[each]) {
                    marked[each] = true;
                    dist[each] = dist[curr] + 1;
                    queue.enqueue(each);
                }
            }
        }
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        this.checkNull(v);
        this.checkNull(w);
        if (v > graph.V() || w > graph.V() || v < 0 || w < 0) {
            throw new IllegalArgumentException("wrong");
        }
        int a = this.ancestor(v, w);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths c = new BreadthFirstDirectedPaths(graph, v);
        if (a == (-1)) {
            return -1;
        } else {
            return b.distTo(a) + c.distTo(a);
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        this.checkNull(v);
        this.checkNull(w);
        if (v > graph.V() || w > graph.V() || v < 0 || w < 0) {
            throw new IllegalArgumentException("wrong");
        }
        if (v < 0 || w < 0) {
            throw new IllegalArgumentException("wrong");
        }
        BreadthFirstDirectedPaths fund = new BreadthFirstDirectedPaths(graph, w);
        int[] a = new int[1];
        this.deepanc(graph, v, fund, a);
        return a[0];
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        this.checkNull(v);
        this.checkNull(w);
        for (int each : v) {
            this.checkNull(each);
            if (each < 0 || each > graph.V()) {
                throw new IllegalArgumentException("wrong");
            }
        }
        for (int each : w) {
            this.checkNull(each);
            if (each < 0 || each > graph.V()) {
                throw new IllegalArgumentException("wrong");
            }
        }
        int a = this.ancestor(v, w);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths c = new BreadthFirstDirectedPaths(graph, v);
        if (a == (-1)) {
            return -1;
        } else {
            return b.distTo(a) + c.distTo(a);
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        this.checkNull(v);
        this.checkNull(w);
        for (int each : v) {
            this.checkNull(each);
            if (each < 0 || each > graph.V()) {
                throw new IllegalArgumentException("wrong");
            }
        }
        for (int each : w) {
            this.checkNull(each);
            if (each < 0 || each > graph.V()) {
                throw new IllegalArgumentException("wrong");
            }
        }
        BreadthFirstDirectedPaths fund = new BreadthFirstDirectedPaths(graph, w);
        int[] a = new int[1];
        this.deepanc(graph, v, fund, a);
        return a[0];
    }

    private void checkNull(Object a) {
        if (a == null) {
            throw new IllegalArgumentException("Null");
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
