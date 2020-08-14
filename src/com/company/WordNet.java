//package com.company;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.BinarySearchST;

import java.util.ArrayList;

public class WordNet {

    /// CLASS WORDNET

    private String[] nouns;
    private BinarySearchST tree;
    private Digraph graph;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        this.checkNull(synsets);
        this.checkNull(hypernyms);
        tree = new BinarySearchST<String, ArrayList<Integer>>();
        In sns = new In(synsets);
        In hps = new In(hypernyms);
        nouns = sns.readAllStrings();
        graph = new Digraph(nouns.length);
        for (int i = 0; i < nouns.length; i++) {
            String[] current = nouns[i].split(",");
            int id = Integer.parseInt(current[0]);
            String[] curr = current[1].split(" ");
            for (String each : curr) {
                if (tree.contains(each)) {
                    ArrayList<Integer> k = (ArrayList<Integer>) tree.get(each);
                    k.add(id);
                    tree.put(each, k);
                } else {
                    ArrayList<Integer> k = new ArrayList<>();
                    k.add(id);
                    tree.put(each, k);
                }
            }
        }
        while (hps.hasNextLine()) {
            String[] current = hps.readLine().split(",");
            int k = 0;
            for (String each : current) {
                if (k == 0) {
                    k++;
                    continue;
                }
                graph.addEdge(Integer.parseInt(current[0]), Integer.parseInt(each));
            }
        }
        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return tree.keys();
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
        ArrayList<Integer> listA = (ArrayList<Integer>) tree.get(nounA);
        ArrayList<Integer> listB = (ArrayList<Integer>) tree.get(nounB);
        return nouns[sap.ancestor(listA, listB)].split(",")[1];
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