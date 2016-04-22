package edu.princeton.Boggle;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieST;

public class BoggleSolver {  
  private TrieST<Integer> searchTree = new TrieST<Integer>();
  
  
  //Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public BoggleSolver(String[] dictionary) {
    for (int i = 0; i < dictionary.length; i++)
      searchTree.put(dictionary[i], i);  // Still initializing null?
  } 

  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board) {
    final List<String> validWords = new ArrayList<String>();
    for (int i = 0; i < board.cols(); i++) {
      for (int j = 0; j < board.rows(); j++) {
        solve(board, i, j, board.getLetter(i, j) + "", validWords);
      }
    }
    return validWords;
    /**
     * Some possible solutions:
     * Use CharSequence or StringBuilder
     * DFS or DP
     * Maybe save the current string path in each index and check for contains()?
     *  - Two arrays; one for strings, the other for marked
     *  - Marked maybe a byte array to track marked from left, right, up, down, etcf?
     *  Node charMatrix[][] = new Node[board.cols()][board.rows()];
    for (int i = 0; i < charMatrix.length; i++) {
      for (int j = 0; j < charMatrix[i].length; j++) {
        charMatrix[i][j] = new Node();
        Node root = charMatrix[i][j];
        root.currentString = "" + board.getLetter(i, j);
        root.marked = true;
        checkForWords(root, i, j, 0);        
        }
      }  
     */    
  }
  
  /**
   * Check surrounding indices for valid prefixes in dictionary. If valid, continue to next
   * index and repeat. If invalid, set node to null and return to previous node. Return currentString
   * if valid word in dictionary
   * @param x Node
   * @param counter keep track of root node
   */
  private void solve(BoggleBoard board, int x, int y, String prefix, List<String> validWords) {
    for (int i = Math.max(0, x - 1); i < Math.min(board.cols(), x + 2); i++) {
      for (int j = Math.max(0, y - 1); j < Math.min(board.rows(), y + 2); j++) {
        String word = prefix + board.getLetter(i, j);
        
        if (searchTree.keysThatMatch(word) != null) validWords.add(word);
        if (searchTree.keysWithPrefix(word) != null) solve(board, i, j, word, validWords);       
      }
    }
  }
        
  

  // Returns the score of the given word if it is in the dictionary, zero otherwise.
  // (You can assume the word contains only the uppercase letters A through Z.)
  public int scoreOf(String word) {
    int score = 0;
    if (word.length() < 3) score = 0;
    else if (word.length() >= 8) score = 11;
    else switch(word.length()) {
    case 3: case 4: score = 1; break;
    case 5: score = 2; break;
    case 6: score = 3; break;
    case 7: score = 5; break;    
    }
    return score;
  }
  
  public static void main(String[] args)
  {
      In in = new In(args[0]);
      String[] dictionary = in.readAllStrings();
      BoggleSolver solver = new BoggleSolver(dictionary);
      BoggleBoard board = new BoggleBoard(args[1]);
      int score = 0;
      for (String word : solver.getAllValidWords(board))
      {
          StdOut.println(word);
          score += solver.scoreOf(word);
      }
      StdOut.println("Score = " + score);
  }
}


