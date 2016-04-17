package edu.princeton.WordNet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Shortest Ancestral Path
 * An ancestral path between two vertices v and w in a digraph is a directed path from v to a 
 * common ancestor x, together with a directed path from w to the same ancestor x. A shortest 
 * ancestral path is an ancestral path of minimum total length.
 * @author eddie
 * @version 20160320 13:50
 */
public class SAP {
  private static final int INFINITY = Integer.MAX_VALUE;
  private Digraph G;  
  
  //constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {
    validateArg(G);
    this.G = new Digraph(G);    
  }

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w) {
    validateVertex(v);
    validateVertex(w);
    int minLength = sap(v, w)[1];
    if (minLength < INFINITY) return minLength;
    else return -1;    
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w) {
    validateVertex(v);
    validateVertex(w);
    return sap(v, w)[0];  
  }
  
  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    validateArg(v);
    validateArg(w);
    validateVertex(v);
    validateVertex(w);
    int minLength = sap(v, w)[1];
    if (minLength < INFINITY) return minLength;
    else return -1;  
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    validateArg(v);
    validateArg(w);
    validateVertex(v);
    validateVertex(w);
    return sap(v, w)[0];
  }
  
  /**
   * Private helper methods
   */
  
  private void validateArg(Object a) {
    if (a == null) throw new NullPointerException("Argument is null");
  }
  
  private void validateVertex(Iterable<Integer> j) {
    for (int i : j) 
      validateVertex(i);
  }
  
  private void validateVertex(int i) {
    if (i < 0 || i >= G.V())
      throw new IndexOutOfBoundsException("vertex " + i + " is not between 0 and " + (G.V()-1));
  }
  
  // returns true if index is a common ancestor of v and w (not necessarily in SAP)
  private boolean isAncestor(BreadthFirstDirectedPaths v, BreadthFirstDirectedPaths w, int index) {
    return (v.hasPathTo(index) && w.hasPathTo(index));
  }
  
  // compares current minimum length and dist[i] + dist[j}
  private int minLength(int currentMin, int i, int j) {
    if (currentMin < i + j) return currentMin;
    else return i + j;
  }

  /**
   * BFS on two iterables and returns an array containing the ancestor in the SAP
   * and the length of the SAP
   * @param v Iterable of vertices
   * @param w Iterable of vertices
   * @return min length and ancestor (in int[])
   */
  private int[] sap(Iterable<Integer> v, Iterable<Integer> w) {
   int[] sap = new int[2];
   BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
   BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
   int minLength = INFINITY;
   int ancestor = -1;
   for (int i = 0; i < G.V(); i++) {
     if (isAncestor(bfsv, bfsw, i)) {
       int newMin = minLength(minLength, bfsv.distTo(i), bfsw.distTo(i));
       if (newMin < minLength) {
         minLength = newMin;
         ancestor = i;
       }       
     }
   }
   sap[0] = ancestor;
   sap[1] = minLength;
   return sap;
  }
  
  /**
   * BFS on two vertices and returns an array containing the ancestor in the SAP
   * and the length of the SAP
   * @param v vertex v
   * @param w vertex w
   * @return min length and ancestor (in int[])
   */
  private int[] sap(int v, int w) {
    int[] sap = new int[2];
    BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
    BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
    int minLength = INFINITY;
    int ancestor = -1;
    for (int i = 0; i < G.V(); i++) {
      if (isAncestor(bfsv, bfsw, i)) {
        int newMin = minLength(minLength, bfsv.distTo(i), bfsw.distTo(i));
        if (newMin < minLength) {
          minLength = newMin;
          ancestor = i;
        }       
      }
    }
    sap[0] = ancestor;
    sap[1] = minLength;
    return sap;
  }
  
  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}
