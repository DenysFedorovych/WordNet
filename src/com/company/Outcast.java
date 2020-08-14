//package com.company;

public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        String result = null;
        int res = 0;
        for (String each : nouns) {
            int dist = 0;
            for (String str : nouns) {
                dist += wordnet.distance(each, str);
            }
            if (dist > res) {
                res = dist;
                result = each;
            }
        }
        return result;
    }
    // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
    }  // see test client below
}
