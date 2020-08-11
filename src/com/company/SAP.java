package com.company;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import java.util.ArrayList;
public class SAP {
    private Digraph graph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        this.checkNull(G);
        this.graph = G;
    }

    private void deepanc(Digraph e, int s, BreadthFirstDirectedPaths k, int[] a){
        if(k.hasPathTo(s)){a[0] = s;}
        else{
            for(int each : e.adj(s)){deepanc(e,each,k,a);}
        }
    }
    private void deepanc(Digraph e, Iterable<Integer> s, BreadthFirstDirectedPaths k, int[] a){
        ArrayList<Integer> array = new ArrayList<>();
        for(int n : s) {
            int m[] = new int[1];
            this.deepanc(e,n,k,m);
            array.add(m[0]);
            }
        BreadthFirstDirectedPaths fund = new BreadthFirstDirectedPaths(e,s);
        int c = 1000000;
        for(int each : array){
            int sum = fund.distTo(each)+k.distTo(each);
            if(sum<c){c=sum;}
        }
        a[0] = c;
        }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        int a = ancestor(v,w);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths c = new BreadthFirstDirectedPaths(graph, v);
        if(a==-1){return a;}
        else{return b.distTo(a)+c.distTo(a);}
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        BreadthFirstDirectedPaths fund = new BreadthFirstDirectedPaths(graph, w);
        int[] a = new int[1];
        a[0] = -1;
        this.deepanc(graph,v,fund,a);
        return a[0];
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        this.checkNull(v);
        this.checkNull(w);
        int a = ancestor(v,w);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(graph, w);
        BreadthFirstDirectedPaths c = new BreadthFirstDirectedPaths(graph, v);
        if(a==-1){return a;}
        else{return b.distTo(a)+c.distTo(a);}
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        this.checkNull(v);
        this.checkNull(w);
        BreadthFirstDirectedPaths fund = new BreadthFirstDirectedPaths(graph, w);
        int[] a = new int[1];
        a[0] = -1;
        this.deepanc(graph,v,fund,a);
        return a[0];
    }

    private void checkNull(Object a)
    {
        if(a==null){throw new IllegalArgumentException("Null");}
    }
    private void checkNull(Iterable a)
    {
        for(Object each : a){this.checkNull(each);}
    }
    // do unit testing of this class
    public static void main(String[] args){}
}
