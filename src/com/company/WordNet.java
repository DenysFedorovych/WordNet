package com.company;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
    /// CLASS NODE
    private class Node implements Comparable<Node>
    {
        public int id;
        public String[] strings;
        public Node(int id, String string)
        {
            this.id = id;
            this.strings = string.split(" ");
        }

        @Override
        public int compareTo(Node o) {
            return 0;
        }
    }

    /// CLASS WORDNET

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        this.checkNull(synsets);
        this.checkNull(hypernyms);
        In sns = new In(synsets);
        In hps = new In(hypernyms);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns()
    {

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        this.checkNull(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        this.checkNull(nounA);
        this.checkNull(nounB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        this.checkNull(nounA);
        this.checkNull(nounB);
    }

    private void checkNull(Object a)
    {
        if(a==null){throw new IllegalArgumentException("Null");}
    }
    // do unit testing of this class
    public static void main(String[] args){}
}