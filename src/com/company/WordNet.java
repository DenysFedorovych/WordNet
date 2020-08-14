//package com.company;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.BinarySearchST;

import java.util.ArrayList;

public class WordNet {
    /// CLASS NODE
//    private class Node implements Comparable<Node> {
//        private boolean special;
//        public int id;
//        public String[] strings;
//
//        public Node(int id, String string, boolean special) {
//            this.id = id;
//            this.strings = string.split(" ");
//            this.special = special;
//        }
//
//        @Override
//        public int compareTo(Node o) {
//            if (this.special) {
//                for (String each : o.strings) {
//                    if (each.equals(this.strings[0])) {
//                        return 0;
//                    }
//                }
//            }
//            return this.strings[0].compareTo(o.strings[0]);
//        }
//    }

    /// CLASS WORDNET

    private ArrayList<String> nouns = new ArrayList<>();
    private BinarySearchST tree;
    private Digraph graph;
    private SAP sap;
    private ArrayList<String> result = new ArrayList<>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        this.checkNull(synsets);
        this.checkNull(hypernyms);
        tree = new BinarySearchST<String, Integer>();
        In sns = new In(synsets);
        In hps = new In(hypernyms);
        while (sns.hasNextLine()) {
            String[] current = sns.readLine().split(",");
            nouns.add(current[1]);
            int id = Integer.parseInt(current[0]);
            for (String each : current[1].split(" ")) {
                tree.put(each, id);
                if(!result.contains(each)){result.add(each);}
            }
        }
        graph = new Digraph(nouns.size());
        while (hps.hasNextLine()) {
            String[] current = hps.readLine().split(",");
            for (String each : current) {
                graph.addEdge(Integer.parseInt(current[0]), Integer.parseInt(each));
//                System.out.println("new edge from "+Integer.parseInt(current[0])+ " to "+Integer.parseInt(each)+ " was created");
            }
        }
        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return result;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        this.checkNull(word);
        return tree.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        this.checkNull(nounA);
        this.checkNull(nounB);
        if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException("Wrong");
        }
        ArrayList<Integer> listA = new ArrayList<>();
        ArrayList<Integer> listB = new ArrayList<>();
        while(tree.contains(nounA)){
            int a = (int) tree.get(nounA);
            listA.add(a);
//            System.out.println(nounA+" "+tree.get(nounA)+" "+ a);
//            for(int k : graph.adj(a)){
//                System.out.println(k);
//            }
//            if(graph.adj(a)==null){
//                System.out.println("no adj");
//            }
//            else{
//                System.out.println("adj not null");
//            }
            tree.delete(nounA);
        }
        for(int k : listA){tree.put(nounA,k); }
        while(tree.contains(nounB)){
            int a = (int) tree.get(nounB);
            listB.add(a);
//            System.out.println(nounB+ " " +tree.get(nounB) +" "+ a);
//            for(int k : graph.adj(a)){
//                System.out.println(k);
//            }
//            if(graph.adj(a)==null){
//                System.out.println("no adj");
//            }
//            else{
//                System.out.println("adj not null");
//            }
            tree.delete(nounB);
        }
        for(int k : listB){tree.put(nounB,k); }
//        for (int i = 0; i < nouns.length; i++) {
//            for (String each : nouns[i].split(" ")) {
//                if (each.equals(nounA)) {
//                    listA.add(i);
//                }
//                if (each.equals(nounB)) {
//                    listB.add(i);
//                }
//            }
//        }
//        int dist = 10000000;
//        for(int each : listA){
//            for(int l : listB){
//                if(sap.length(each,l)<dist){dist = sap.length(each,l);}
//            }
//        }
//        return dist;

        return sap.length(listA,listB);
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        this.checkNull(nounA);
        this.checkNull(nounB);
        if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException("Wrong");
        }
        ArrayList<Integer> listA = new ArrayList<>();
        ArrayList<Integer> listB = new ArrayList<>();
//        for (int i = 0; i < nouns.length; i++) {
//            for (String each : nouns[i].split(" ")) {
//                if (each.equals(nounA)) {
//                    listA.add(i);
//                }
//                if (each.equals(nounB)) {
//                    listB.add(i);
//                }
//            }
//        }
        while(tree.contains(nounA)){
            int a = (int) tree.get(nounA);
            listA.add(a);
            tree.delete(nounA);
        }
        for(int k : listA){tree.put(nounA,k);}
        while(tree.contains(nounB)){
            int a = (int) tree.get(nounB);
            listB.add(a);
            tree.delete(nounB);
        }
        for(int k : listB){tree.put(nounB,k);}
        if (sap.ancestor(listA, listB) != (-1)) {
            return nouns.get(sap.ancestor(listA, listB));
        } else {
            return null;
        }
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