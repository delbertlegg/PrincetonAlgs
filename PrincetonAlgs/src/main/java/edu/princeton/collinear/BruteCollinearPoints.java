package edu.princeton.collinear;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/*****************************************************************************************************
 * Compilation: javac BruteCollinearPoints.java
 * Execution: java BruteCollinearPoints
 * 
 * Examines 4 points at a time and checks whether they all lie on the same line segment, 
 * returning all such line segments. To check whether the 4 points p, q, r, and s are collinear, 
 * check whether the three slopes between p and q, between p and r, and between p and s are all equal.
 * 
 * @author Eddie Legg
 *
 *****************************************************************************************************/


public class BruteCollinearPoints {
  private int numberOfSegments;
  private LineSegment[] segments;
  
  /**
   * Finds all line segments containing 4 points
   * @param points array of Points for analysis
   */
  public BruteCollinearPoints(Point[] points) {    
    int maxPoints = 4;                                                      // maxPoints Number of points that make a segment
	numberOfSegments = 0;
	if (points == null) throw new NullPointerException("Array is null");
	// Arrays.sort(points);
	// Create new array to hold line segments
	segments = new LineSegment[points.length / maxPoints];
	// Compare slope of point to all other points. Stop loop at 4th last point
	// to avoid overflow (segment needs four points)
	for (int i = 0; i <= points.length - maxPoints; i++) {	  
	  if (points[i] == null) throw new NullPointerException("Index is null");
	  // Array to store points in a segment for sorting and drawing
	  Point[] segmentPoints = new Point[maxPoints];
      segmentPoints[0] = points[i];
      segmentPoints[1] = points[i + 1];
	  int segment = 2;
	  // Compare slope to current slope and increment segment if they are equal
	  double slope = points[i].slopeTo(points[i + 1]);
	  for (int j = i + 1; j < points.length - 1; ++j) {
	    if (points[j] == points[i]) throw new IllegalArgumentException("Repeat point");
	    double slopej = points[j].slopeTo(points[j + 1]);
	    if (slopej == slope) {
	      segmentPoints[segment] = points[j + 1];
	      segment++;
	      if (segment == maxPoints) {
	        Arrays.sort(segmentPoints);
	        LineSegment line = new LineSegment(segmentPoints[0], segmentPoints[segmentPoints.length - 1]);
	        segments[numberOfSegments] = line;
	        numberOfSegments++;
	      }
	    }
	  }
	}		
   }
   
  public int numberOfSegments(){
	   return numberOfSegments; // the number of line segments
   }
  
  public LineSegment[] segments() {
	   return segments;// the line segments
   }
  
  public static void main(String[] args) {

    // read the N points from a file
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.show(0);
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
}

}
