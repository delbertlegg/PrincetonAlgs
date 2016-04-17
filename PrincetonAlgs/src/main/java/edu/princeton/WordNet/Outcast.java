package edu.princeton.WordNet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 * Given a list of wordnet nouns A1, A2, ..., An, which noun is the least related
 *  to the others? To identify an outcast, compute the sum of the distances between 
 *  each noun and every other one:
 *  di   =   dist(Ai, A1)   +   dist(Ai, A2)   +   ...   +   dist(Ai, An)
 *  and return a noun At for which dt is maximum.
 * @author eddie
 * @version 20160322 20:59
 */
public class Outcast {
  private static WordNet w;
  
  //constructor takes a WordNet object
  public Outcast(WordNet wordnet) {
     w = wordnet;
  }
   
  /**
   * Given an array of nouns, return the outcast
   * @param nouns
   * @return outcast (maximum dt)
   */
  public String outcast(String[] nouns) {
    String outcast = "";
    int max = 0;
    for (int i = 0; i < nouns.length; i++) {
      int distance = 0;
      for (int j = 0; j < nouns.length; j++) {
        if (i != j) distance += w.distance(nouns[i], nouns[j]);
      }
      if (distance > max) {
        outcast = nouns[i];
        max = distance;
      }
     
    }
    return outcast;
  }
   
  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}