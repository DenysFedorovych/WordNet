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
    private Digraph graph = new Digraph(100000);
    private SAP sap;
  //  private ArrayList<String> result = new ArrayList<>(); ////here

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        this.checkNull(synsets);
        this.checkNull(hypernyms);
        tree = new BinarySearchST<String, ArrayList<Integer>>();
        In sns = new In(synsets);
        In hps = new In(hypernyms);
        while (sns.hasNextLine()) {
            String[] current = sns.readLine().split(",");
            nouns.add(current[1]);
            int id = Integer.parseInt(current[0]);
            for (String each : current[1].split(" ")) {
                if(tree.contains(each)) {
                    ArrayList<Integer> k = (ArrayList<Integer>) tree.get(each);
                    k.add(id);
                    tree.put(each, k);
                }
                else{
                    ArrayList<Integer> k = new ArrayList<>();
                    k.add(id);
                    tree.put(each, k);
                }
            }
        }
        //graph = new Digraph(nouns.size());
        while (hps.hasNextLine()) {
            String[] current = hps.readLine().split(",");
            int k=0;
            for (String each : current) {
                if(k==0){k++; continue;}
                graph.addEdge(Integer.parseInt(current[0]), Integer.parseInt(each));
            }
        }
        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns;
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
        ArrayList<Integer> listA = (ArrayList<Integer>) tree.get(nounA);
        ArrayList<Integer> listB = (ArrayList<Integer>) tree.get(nounB);

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
        ArrayList<Integer> listA = (ArrayList<Integer>) tree.get(nounA);
        ArrayList<Integer> listB = (ArrayList<Integer>) tree.get(nounB);
        return nouns.get(sap.ancestor(listA, listB));
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