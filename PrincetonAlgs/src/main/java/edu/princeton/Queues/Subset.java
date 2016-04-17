package edu.princeton.Queues;

import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 * Compile: javac Subset.java
 * Execute: java Subset k < input
 * Client that takes a command line integer k, reads a sequence of N strings
 * from standard input using StdIn.readString(); and prints out exactly k of them,
 * uniformly at random. Each item from the sequence can be printed out at most once.
 * 
 * @author Eddie Legg
 * @version 2/3/16 20:52 EST
 */
public class Subset {
  public static void main(String[] args) {
    
    int printOut = Integer.parseInt(args[0]);  // Number of objects from Stdin to be printed
    RandomizedQueue<String> rq = new RandomizedQueue<String>();
    
    while (!StdIn.isEmpty()) {                  // Reads all StdIn
      rq.enqueue(StdIn.readString());
    }
    
    Iterator<String> it = rq.iterator();
    for (int i = 0; i < printOut; i++) {
      StdOut.println(it.next());
    }
  }

}
