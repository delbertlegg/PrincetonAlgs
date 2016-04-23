

/**
 * Compilation: java BoggleSolver [dictionary][board]
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
  
  private SmallTrieST<Integer> searchTree = new SmallTrieST<Integer>();
  private boolean[][] marked;
  
  //Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public BoggleSolver(String[] dictionary) {
    for (int i = 0; i < dictionary.length; i++)
      searchTree.put(dictionary[i], i);
    
  } 

  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board) {
    marked = new boolean[board.rows()][board.cols()];
    for (boolean[] row : marked) {
      Arrays.fill(row, false);
    }
    final List<String> validWords = new ArrayList<String>();
    for (int i = 0; i < board.rows(); i++) {
      for (int j = 0; j < board.cols(); j++) {
        marked[i][j] = true;        
        solve(board, i, j, new StringBuilder().append(board.getLetter(i, j)), validWords);
        marked[i][j] = false;
      }
    }
    return validWords;   
  }
  
  /**
   * Check surrounding indices for valid prefixes in dictionary. If valid, continue to next
   * index and repeat. If invalid, set node to null and return to previous node. Return currentString
   * if valid word in dictionary
   * @param x Node
   * @param counter keep track of root node
   */
  private void solve(BoggleBoard board, int x, int y, StringBuilder prefix, List<String> validWords) {
    for (int i = Math.max(0, x - 1); i < Math.min(board.rows(), x + 2); i++) {
      for (int j = Math.max(0, y - 1); j < Math.min(board.cols(), y + 2); j++) {
        if ((i == x && j == y) || marked[i][j] == true) continue;
        marked[i][j] = true;
        StringBuilder word = new StringBuilder().append(prefix);
        if (word.charAt(word.length() - 1) == 'Q') word.append("U");        
        word.append(board.getLetter(i, j));
        if (searchTree.keysThatMatch(word.toString()).iterator().hasNext()
            && (!validWords.contains(word.toString()))
            && word.length() > 2) validWords.add(word.toString());
        if (searchTree.keysWithPrefix(word.toString()).iterator().hasNext()) {
          
          solve(board, i, j, word, validWords);      
        }
        marked[i][j] = false;
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


