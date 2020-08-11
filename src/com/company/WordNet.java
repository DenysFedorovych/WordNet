//package com.company;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.BinarySearchST;

import java.util.ArrayList;

public class WordNet {
    /// CLASS NODE
    private class Node implements Comparable<Node> {
        private boolean special;
        public int id;
        public String[] strings;

        public Node(int id, String string, boolean special) {
            this.id = id;
            this.strings = string.split(" ");
            this.special = special;
        }

        @Override
        public int compareTo(Node o) {
            if (this.special) {
                for (String each : o.strings) {
                    if (each.equals(this.strings[0])) {
                        return 0;
                    }
                }
            }
            return this.strings[0].compareTo(o.strings[0]);
        }
    }

    /// CLASS WORDNET

    private String[] nouns;
    private BinarySearchST tree;
    private Digraph graph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        this.checkNull(synsets);
        this.checkNull(hypernyms);
        tree = new BinarySearchST<String, Integer>();
        In sns = new In(synsets);
        In hps = new In(hypernyms);
        int k = 0;
        while (hps.hasNextLine()) {
            k++;
            String str = hps.readLine();
        }
        nouns = new String[k];
        graph = new Digraph(k);
        while (hps.hasNextLine()) {
            String[] current = hps.readLine().split(",");
            for (String each : current) {
                graph.addEdge(Integer.parseInt(current[0]), Integer.parseInt(each));
            }
        }
        while (sns.hasNextLine()) {
            String[] current = sns.readLine().split(",");
            int id = Integer.parseInt(current[0]);
            nouns[id] = current[1];
            for (String each : current[1].split(" ")) {
                tree.put(each, id);
            }
        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        ArrayList<String> array = new ArrayList<>();
        for (String each : nouns) {
            array.add(each);
        }
        return array;
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
        SAP sap = new SAP(graph);
        ArrayList<Integer> listA = new ArrayList<>();
        ArrayList<Integer> listB = new ArrayList<>();
        for (int i = 0; i < nouns.length; i++) {
            for (String each : nouns[i].split(" ")) {
                if (each.equals(nounA)) {
                    listA.add(i);
                }
                if (each.equals(nounB)) {
                    listB.add(i);
                }
            }
        }
        return sap.length(listA, listB);
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        this.checkNull(nounA);
        this.checkNull(nounB);
        if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException("Wrong");
        }
        SAP sap = new SAP(graph);
        ArrayList<Integer> listA = new ArrayList<>();
        ArrayList<Integer> listB = new ArrayList<>();
        for (int i = 0; i < nouns.length; i++) {
            for (String each : nouns[i].split(" ")) {
                if (each.equals(nounA)) {
                    listA.add(i);
                }
                if (each.equals(nounB)) {
                    listB.add(i);
                }
            }
        }
        if (sap.ancestor(listA, listB) != (-1)) {
            return nouns[sap.ancestor(listA, listB)];
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