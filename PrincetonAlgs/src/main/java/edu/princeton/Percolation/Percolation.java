package edu.princeton.Percolation;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Compilation: javac Percolation.java Execution: 
 * 
 * <p>This program takes the name of a file as a command-line argument. From that 
 * file, it:
 *  - Reads the grid size N of the percolation system.
 *  - Creates an N-by-N grid of sites (initially all blocked).
 *  - Creates a Weighted Quick Union UF size N^2 to track percolation
 *  - Reads in a sequence of sites (row i, column j) to open.
 * </p>
 * @author Delbert Legg
 * @version 1/31/16
 * 
 *         KNOWN ISSUE: XY coordinate inputs are 1-base indexed. All internal
 *         calls to methods decrease coordinates by 1 to adjust to 0-base arrays
 *         and increase by 1 during internal method calls to avoid array out of
 *         bounds exception.
 */

public class Percolation {
  private boolean[][] grid;                 // Grid to store open/blocked sites
  private WeightedQuickUnionUF percCheck;   // unionFind to determine if percolating (avoids backwash)
  private WeightedQuickUnionUF unionFind;   // unionFind representing full sites
  private int gridLength;                   // Length of grid
  private int virtualTop;                   // Site representing top row
  private int virtualBottom;                // Site representing bottom row

  /**
   * Default constructor Creates N by N boolean grid and N^2 Weighted Quick
   * Union Find Instantiates both to default values and sets virtual top and
   * bottom sites. 
   * @param num number of rows and columns
   */
  public Percolation(int num) {
    if (num <= 0) {
      throw new IllegalArgumentException();
    }
    gridLength = num;
    grid = new boolean[gridLength][gridLength];
    unionFind = new WeightedQuickUnionUF(gridLength * gridLength + 2);
    percCheck = new WeightedQuickUnionUF(gridLength * gridLength + 2);
    virtualTop = gridLength * gridLength;
    virtualBottom = virtualTop + 1;
    for (int i = 0; i < gridLength; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = false;
      }
    }
    for (int i = 0; i < grid.length; i++) {
      unionFind.union(virtualTop, i);
      percCheck.union(virtualTop, i);
      percCheck.union((virtualBottom - 2 - i), virtualBottom);
    }
  }

  /**
   * Check if adjacent indices in a 2D array are active If true, union two
   * indices.
   * @param i row
   * @param j column
   */
  private void checkAdjacentIndicies(int xcoord, int ycoord) {

    // 
    final int minX = 0;
    final int minY = 0;
    final int maxX = gridLength - 1;
    final int maxY = gridLength - 1;
    int startPosX;
    if (xcoord - 1 < minX) {
      startPosX = xcoord;
    }
    else {
      startPosX = xcoord - 1;
    }
    int startPosY;
    if (ycoord - 1 < minY) {
      startPosY = ycoord;
    }
    else {
      startPosY = ycoord - 1;
    }
    int endPosX;
    if (xcoord + 1 > maxX) {
      endPosX = xcoord;
    }
    else {
      endPosX = xcoord + 1;
    }
    final int endPosY;
    if (ycoord + 1 > maxY) {
      endPosY = ycoord;
    }
    else {
      endPosY = ycoord + 1;
    }

    // Ugly but works...look for more efficient code
    if (isOpen(startPosX + 1, ycoord + 1)) {
      unionFind.union(xyTo1D(xcoord, ycoord), xyTo1D(startPosX, ycoord));
      percCheck.union(xyTo1D(xcoord, ycoord), xyTo1D(startPosX, ycoord));
    }
    if (isOpen(xcoord + 1, startPosY + 1)) {
      unionFind.union(xyTo1D(xcoord, ycoord), xyTo1D(xcoord, startPosY));
      percCheck.union(xyTo1D(xcoord, ycoord), xyTo1D(xcoord, startPosY));
    }
    if (isOpen(xcoord + 1, endPosY + 1)) {
      unionFind.union(xyTo1D(xcoord, ycoord), xyTo1D(xcoord, endPosY));
      percCheck.union(xyTo1D(xcoord, ycoord), xyTo1D(xcoord, endPosY));
    }
    if (isOpen(endPosX + 1, ycoord + 1)) {
      unionFind.union(xyTo1D(xcoord, ycoord), xyTo1D(endPosX, ycoord));
      percCheck.union(xyTo1D(xcoord, ycoord), xyTo1D(endPosX, ycoord));
    }

    /*
     * // Checks all eight surrounding indices; only want to check immediately
     * above, below and beside // See how many are alive for (int
     * rowNum=startPosX; rowNum<=endPosX; rowNum++) { for (int colNum=startPosY;
     * colNum<=endPosY; colNum++) { // All the neighbors will be
     * grid[rowNum][colNum] if (isOpen(rowNum + 1, colNum + 1)){
     * unionFind.union(xyTo1D(i, j), xyTo1D(rowNum, colNum)); } } }
     */

  }

  /**
   * Convert xy coordinates to a 1D array index.
   * @param i x coordinate
   * @param j y coordinate
   * @return 1D array index representing xy coordinate
   */
  private int xyTo1D(int xcoord, int ycoord) {
    return (xcoord * gridLength) + ycoord;
  }

  /**
   * Validates that xy coordinate is within bounds of 2D array.
   * @param i row
   * @param j column
   */
  private void validateIndex(int xcoord, int ycoord) {
    int minXY = min(xcoord, ycoord);
    int maxXY = max(xcoord, ycoord);
    if (minXY < 0 || maxXY >= gridLength) {
      throw new IndexOutOfBoundsException(xcoord + " " + ycoord);
    }
  }

  /**
   * Returns larger of two ints.
   * @param i int 1
   * @param j int 2
   * @return max
   */
  private int max(int i, int j) {
    if (i > j) {
      return i;
    }
    return j;
  }

  /**
   * Returns smaller of two ints.
   * @param i int 1
   * @param j int 2
   * @return min
   */
  private int min(int i, int j) {
    if (i < j) {
      return i;
    }
    return j;
  }

  /**
   * Sets site (index) to open (true) if not open already.
   * @param i row
   * @param j column
   */
  public void open(int i, int j) {
    i -= 1;
    j -= 1;
    validateIndex(i, j);
    if (!isOpen(i + 1, j + 1)) {
      grid[i][j] = true;
    }
    checkAdjacentIndicies(i, j);
  }

  /**
   * Checks if site is open.
   * @param i row
   * @param j column
   * @return True if index = true
   */
  public boolean isOpen(int i, int j) {
    i -= 1;
    j -= 1;
    validateIndex(i, j);
    return (grid[i][j]);
  }

  /**
   * Check if site is full (ie, index is connected to virtual top).
   * @param i row
   * @param j column
   * @return True if index is connected to virtual top
   */
  public boolean isFull(int i, int j) {
    i -= 1;
    j -= 1;
    validateIndex(i, j);
    if (isOpen(i + 1, j + 1)) {
      return (unionFind.connected(xyTo1D(i, j), virtualTop));
    }
    return false;
  }

  /**
   * Check if virtualBottom is connected to virtualTop.
   * @return connected
   */
  public boolean percolates() {
    return (percCheck.connected(virtualBottom, virtualTop));
  }

  public static void main(String[] args){}
}
