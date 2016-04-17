package edu.princeton.Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

/**
 * Compilation:    javac Deque.java
 * @author Eddie] Legg
 * @version 1/31/15 21:21 EST
 * 
 * A generic queue that allow for add and remove functions from
 * both the front and back of the queue.
 * 
 * Follow up: set up for doubly linked list. Consider using sentinels to head and tail
 * to keep track of first and last.
 */

public class Deque<Item> implements Iterable<Item> {
  private Node first;  // Pointer to first node
  private Node last;   // Pointer to last node
  private int size;    // Size of stack
  
  
  // helper linked list class
  private class Node {   // static for efficiency
    private Item item;          // Object in node
    private Node next;          // Pointer to next node
    private Node previous;      // doubly linked list
  }
  
  /**
   * Initializes empty stack.
   */
  public Deque() {
    first = null;
    last = null;
    size = 0;
    assert check();
  }
  
  
  /**
   * @return is the deque empty?
   */
  public boolean isEmpty() {
    return (size == 0);
  }
  
  /**
   * @return the number of items on the deque
   */
  public int size() {
    return size;
  }
  
  /**
   * add the item to the front
   * @param item
   */
  public void addFirst(Item item) {
    if (!isNull(item)) {
      Node oldfirst = first;                    // add item to front of list
      first = new Node();
      first.item = item;
      first.previous = null;
      if (size > 0) oldfirst.previous = first;
      first.next = oldfirst;                    // push old first to next
      if (size == 0) last = first;              // point last to last node (or first if size == 1)
      size++;
      assert check();
    }
  }
  
  /**
   * add the item to the end
   * @param item
   */
  public void addLast(Item item) {
    if (!isNull(item)) {
      Node node = new Node();
      node.item = item;
      node.next = null;
      if (size != 0) {
        last.next = node;
        node.previous = last;
        last = node;
      }
      else {
        node.previous = null;
        first = node;
        last = node;
      }
      size++;
      assert check();  
    }
  }
  
  /**
   * remove and return the item from the front
   * @return first
   */
  public Item removeFirst() {
    if (size == 0) throw new NoSuchElementException();
    Node node = first;
    first = first.next;
    if (first != null) first.previous = null;
    Item item = node.item;
    node = null;
    size--;
    return item;   
  }
  
  // Converted to doubly linked list up to here. Finish later
  
  /**
   * remove and return the item from the end
   * @return last
   */
  public Item removeLast() {
    if (size == 0) throw new NoSuchElementException();   
    Node node = last;
    Item item = node.item;
    if (first == last) {
      first = null;
      last = null;
      size--;
      return item;   // if only one object in stack
    }
    last = node.previous;
    node = null;
    size--;
    return item;   
  }
  
  
  /**
   * return an iterator over items in order from front to end
   */
  public Iterator<Item> iterator() {
    return new ListIterator();
  }
  
  
  /**
   * Overrides iterator to throw exception when remove() is called
   */
  private class ListIterator implements Iterator<Item> {
    private Node current = first;
    public boolean hasNext() { return current != null; }
    public void remove() { throw new UnsupportedOperationException(); }
    
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }  
  
 /**
  * Check internal invariants
  * @return true if invariants are fulfilled
  */
  private boolean check() {
    // check a few properties of instance variable 'first'
    if (size < 0) {
      return false;
    }
    if (size == 0) {
      if (first != null) return false;
    }
    else if (size == 1) {
      if (first == null) return false;
      if (first.next != null) return false;
    }
    else {
      if (first == null) return false;
      if (first.next == null) return false;
    }
    return true;    
  }
  
  /**
   * Corner case: throw exception if parameter is null
   * @param args
   */
  private boolean isNull(Item item) {
    if (item == null) throw new NullPointerException();
    return false;
  }
  
  public static void main(String[] args) {
    // unit testing
    int[] numbers = {1, 2, 3, 4, 5};
    int[] numbers2 = {6, 7, 8, 9, 10};
    Deque<Integer> deq = new Deque<Integer>();
    for (int i = 0; i < numbers.length; i++) {
      deq.addFirst(numbers[i]);      
      deq.addLast(numbers2[i]);
    }
    StdOut.println(deq.removeFirst());
    StdOut.println(deq.removeLast());
    StdOut.println(deq.size());
    StdOut.println(deq.isEmpty());
    while (deq.size() != 0) deq.removeFirst();
    deq.addLast(1);
    StdOut.println(deq.removeFirst());
    deq.addFirst(2);
    StdOut.println(deq.removeLast());
    
  } 
}
