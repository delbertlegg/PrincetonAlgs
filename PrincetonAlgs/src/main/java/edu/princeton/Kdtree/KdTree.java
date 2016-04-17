package edu.princeton.Kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {
  private Node root;
  private int size = 0;
  
  private enum Orientation {
    xcoord, ycoord;

    public Orientation nextCoord() {
      if (this.equals(Orientation.xcoord)) return Orientation.ycoord;
      return Orientation.xcoord;
    }
  } 
  
  private static class Node {
    private Point2D point;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb, rt;    // the left/bottom and right/top subtree 
    
    private Node(Point2D p) {
      point = p;
    }
  }
    
  public KdTree() {
    // construct an empty set of points    
  }
  
  public boolean isEmpty() {
 // is the set empty? 
    return size() == 0;    
  }
  
  public int size() {
    // number of points in the set
    return size;   
  }
  
  public void insert(Point2D p) {
    // add the point to the set (if it is not already in the set)
    if (p == null) throw new NullPointerException("Argument to put() is null");
    root = put(root, p, Orientation.xcoord);
  }
  
  private Node put(Node x, Point2D p, Orientation o) {
    if (x == null) return new Node(p);
    int cmp = compare(p, x.point, o);
    if (cmp < 0) {
      x.lb = put (x.lb, p, o.nextCoord());
    }
    else {
      x.rt = put(x.rt, p, o.nextCoord());
    }
    return x;
  }
  
  private int compare(Point2D p, Point2D q, Orientation o) {
    if (o.equals(Orientation.xcoord)) return Double.compare(p.x(), q.x());
    return Double.compare(p.y(), q.y());
  }
  
  public boolean contains(Point2D p) {
    // does the set contain point p?
    if (p == null) throw new NullPointerException("Argment for contains() is null");
    return get(p) != null;    
   }
    
  private Point2D get(Point2D p) {
    return get(root, p, Orientation.xcoord);
  }

  private Point2D get(Node x, Point2D p, Orientation o) {
    if (x == null) return null;
    if (x.point.equals(p)) return p;
    int cmp = compare(p, x.point, o);
    if (cmp < 0) return get(x.lb, p, o.nextCoord());
    else return get(x.rt, p, o.nextCoord());
  }

  public void draw() {
    // draw all points to standard draw
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    // all points that are inside the rectangle 
    Stack<Point2D> stack = new Stack<Point2D>();
    return stack;
  }
  
  public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to point p; null if the set is empty
    return null;
  }

  public static void main(String[] args) {
    // unit testing of the methods (optional) 
  }
}
