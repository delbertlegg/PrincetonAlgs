package edu.princeton.collinear;

import edu.princeton.cs.algs4.Queue;

public class FastCollinearPoints {
  private int numberOfSegments;
  private LineSegment[] segments;
  
  /**
   * Notes: As of right now, it is set up to iterate through the array indices, exchange them with the 0th
   * index, and create an array of slopes to the 0th index point. From there it will sort by slope and find
   * the equal slopes (not sure how I'd do that). Another approach (which I'm sure is the intended one) is
   * to use the slopeOrder() method in the Point class, then compare the slopeTo(p) of each.
   * 
   * @param points
   */
  public FastCollinearPoints(Point[] points) {
    // finds all line segments containing 4 or more points
    if (points == null) throw new NullPointerException();                 // corner case
    numberOfSegments = 0;
    Queue<Point> lineSegments = new Queue<Point>();
    // Compare slopes from each point to all other points
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) throw new NullPointerException();            // corner case
      exch(points, 0, i); 
      double[] slopes = new double[points.length - 1];
      for (int j = 1; j < points.length; j++) {
        if (points[i] == points[j]) throw new IllegalArgumentException(); // corner case
        slopes[j - 1] = points[i].slopeTo(points[j]);
      }
    }
    
  }
  public int numberOfSegments() {
    return numberOfSegments;// the number of line segments
  }
  public LineSegment[] segments() {
	return segments;                                                      // the line segments
  }
  
  /**
   * Private helper methods
   */
  private static boolean less(Point v, Point w) {
    return v.compareTo(w) < 0;
  }
  
  private static boolean eq(Point v, Point w) {
    return v.compareTo(w) == 0;
  }
  
  private static void exch(Point[] a, int i, int j) {
    Point swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }
}
