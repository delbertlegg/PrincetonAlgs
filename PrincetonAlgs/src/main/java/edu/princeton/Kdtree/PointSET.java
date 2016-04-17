package edu.princeton.Kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

/**
 * 
 */

/**
 * @author eddie
 *
 */
public class PointSET {
  private SET<Point2D> set;

  public PointSET() {
    // construct an empty set of points
    set = new SET<Point2D>();
  }
  public boolean isEmpty() {
 // is the set empty? 
    return size() == 0;    
  }
  public int size() {
    // number of points in the set
    return set.size();
    
  }
  public void insert(Point2D p) {
    // add the point to the set (if it is not already in the set)
    if (!(contains(p))) set.add(p);
  }
  
  public boolean contains(Point2D p) {
    // does the set contain point p?
    return set.contains(p);     
  }
  
  public void draw() {
    // draw all points to standard draw
    for (Point2D point : set) point.draw();    
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    // all points that are inside the rectangle 
    Stack<Point2D> stack = new Stack<Point2D>();
    for (Point2D point : set) {
      if (rect.contains(point)) stack.push(point);
    }
    return stack;
  }
  public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to point p; null if the set is empty
    double d = Double.MAX_VALUE;
    Point2D nearest = null;
    for (Point2D point : set) {
      if (point.distanceSquaredTo(p) < d) {
        d = point.distanceSquaredTo(p);
        nearest = point;
      }
    }
    return nearest;
  }

  public static void main(String[] args) {
    // unit testing of the methods (optional) 
  }

}
