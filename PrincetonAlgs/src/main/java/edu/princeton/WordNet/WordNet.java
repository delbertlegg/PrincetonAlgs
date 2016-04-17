package edu.princeton.WordNet;

import java.util.HashMap;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Topological;

/**
 * Compilation: javac WordNet.java
 * Run: java Wordnet <synsets> <hypernyms>
 * WordNet is a semantic lexicon for the English language that is used extensively by computational linguists 
 * and cognitive scientists; for example, it was a key component in IBM's Watson. WordNet groups words into 
 * sets of synonyms called synsets and describes semantic relationships between them. One such relationship is 
 * the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). 
 * For example, a plant organ is a hypernym of carrot and plant organ is a hypernym of plant root.
 * @author eddie
 * @version 20160322 20:54
 */
public class WordNet {
  private Digraph G;
  private HashMap<String, Queue<Integer>> nounMap;  // Stores all individual nouns for sap and distance purposes
  private HashMap<Integer, String> synsetMap;       // Stores synsets for return values
  private SAP S;
  
  //constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms) {
    if (synsets.isEmpty() || hypernyms.isEmpty()) throw new NullPointerException("String argument is null");
    nounMap = new HashMap<String, Queue<Integer>>();
    synsetMap = new HashMap<Integer, String>();
    int lines = setNounMap(synsets);    
    G = new Digraph(lines);
    setDigraph(hypernyms);
    S = new SAP(G);
  }

  // returns all WordNet nouns
  public Iterable<String> nouns() {
    return nounMap.keySet();    
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word) {
    if (word.isEmpty()) throw new NullPointerException();
    return nounMap.containsKey(word);    
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    validateNouns(nounA, nounB);
    return S.length(nounMap.get(nounA), nounMap.get(nounB));    
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
    validateNouns(nounA, nounB);
    int ancestorID = S.ancestor(nounMap.get(nounA), nounMap.get(nounB));
    return getKey(ancestorID);
  }
  
  /**
   * Private helper methods
   */
  
  /**
   * Builds treemap of noun keys and id values. Returns number of lines
   * read in for Digraph size.
   * @param synsets string In arg
   * @return number of lines read in
   */
  private int setNounMap(String synsets) {
    In nouns = new In(synsets);
    // Build noun index to id (vertex)
    int lines = 0;
    while (nouns.hasNextLine()) {
      String line = nouns.readLine();
      String[] s = line.split(",");
      int id = Integer.parseInt(s[0]);
      String synset = s[1];
      synsetMap.put(id, synset);
      String[] nounList = synset.split(" ");
      for (String string : nounList) {
        if (!isNoun(string)) {
          Queue<Integer> q = new Queue<Integer>();
          q.enqueue(id);
          nounMap.put(string, q);        
      }
        else {
          nounMap.get(string).enqueue(id);
        }
     }
      lines++;
    }
    return lines;
  }
  
  /**
   * Builds Digraph from edges read from In
   * @param hypernyms String In arg
   */
  private void setDigraph(String hypernyms) {
    In edges = new In(hypernyms);
    while (edges.hasNextLine()) {
      String line = edges.readLine();
      String[] s = line.split(",");
      int index = Integer.parseInt(s[0]);
      for (int i = 1; i < s.length; i++) {
        G.addEdge(index, Integer.parseInt(s[i]));
      }
    }
    Topological t = new Topological(G);
    if (!t.hasOrder())throw new IllegalArgumentException("Not a DAG; terminating...");
  }
  
  /**
   * Provide validation for all methods passing nouns as args
   */
  private void validateNouns(String nounA, String nounB) {
    if (nounA.isEmpty() || nounB.isEmpty()) throw new NullPointerException("Noun argument is null");
    if (!(isNoun(nounA) && isNoun(nounB))) throw new IllegalArgumentException("Not a WordNet noun: "
        + nounA + " " + nounB);
  }
  
  /**
   * Searches map for key by value
   * @param val Int search arg
   * @return String key
   */
  private String getKey(int val) {
    return synsetMap.get(val);
  }

  // do unit testing of this class
  public static void main(String[] args) {
    String synsets = args[0];
    String hypernyms = args[1];
    WordNet wn = new WordNet(synsets, hypernyms);
    int d = wn.distance("Aeneas", "regatta");
    System.out.println(d);
    String s = wn.sap("Aeneas", "regatta");
    System.out.println(s);
    
  }
}
