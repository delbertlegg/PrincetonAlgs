package edu.princeton.Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Compilation:    javac RandomizedQueue.java
 * @author Eddie Legg
 * @version 2/2/2016 22:32 EST
 * 
 * A generic array queue that inserts into the front of the array and removes randomly.
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] queue;
  private int num;
  
  /**
   * Constructor for empty queue
   */

  public RandomizedQueue() {
    queue = (Item[]) new Object[2];
    num = 0;
  }
  
  /**
   * is the queue empty?
   * @return true if size == 0
   */
  public boolean isEmpty() {
    return num == 0;
  }
  
  /**
   * Size of array
   * @return array size
   */
  public int size() {
    return num;
  }
  
  /**
   * Add item to end of array
   * @param item to be added
   */
  public void enqueue(Item item) {
    if (item == null) throw new NullPointerException();
    if (num == queue.length) resize(2*queue.length);
    queue[num++] = item;
  }
  
  /**
   * Remove and return random item
   * @return random item
   */
  public Item dequeue() {
    if (num == 0) throw new NoSuchElementException();
    int rand = StdRandom.uniform(num);  // random queue index
    Item item = queue[rand];
    if (queue[rand] != null) {
      queue[rand] = queue[num - 1];
      queue[num - 1] = null;
      num--;
      // shrink size of array if necessary
      if (num > 0 && num == queue.length / 4) resize(queue.length / 2);
      
      return item;
    }
    else throw new NoSuchElementException();
  }
  
  /**
   * Sample random item
   * @return (but do not remove) random item
   */
  public Item sample() {
    if (num == 0) throw new NoSuchElementException();
    int rand = StdRandom.uniform(num);
    Item item = queue[rand];
    if (queue[rand] != null) return item;
    else throw new NoSuchElementException(Integer.toString(rand));
  }
  
  /**
   * Changes the size of the item array. Doubles the array size if 
   * num == array length and halves array size if num == array length / 4
   * @param size new array length
   */
  private void resize(int size) {
    assert size >= num;
    Item[] temp = (Item[]) new Object[size];
    for (int i = 0; i < num; i++) {
      temp[i] = queue[i];
      }
    queue = temp;
  }
  
  /**
   * Overloaded iterator
   */
  public Iterator<Item> iterator() {
    return new ListIterator();
    // return an independent iterator over items in random order
  }
  
  private class ListIterator implements Iterator<Item> {
    private int[] randomNumbers;  // int array of indices to be randomly accessed
    private int iteratorNum;      // counter for iterator
    
    /**
     * Constructs iterator with a shuffled array of indices to allow
     * for independently random next() calls
     */
    public ListIterator() {
      iteratorNum = num;                        // copies current size of item array
      randomNumbers = new int[iteratorNum];     // creates the random index array
      for (int i = 0; i < iteratorNum; i++)
        randomNumbers[i] = i;
      StdRandom.shuffle(randomNumbers);
    }
    public boolean hasNext() { return iteratorNum != 0; }
    public void remove() { throw new UnsupportedOperationException(); }
        
    /**
     * Iterates through shuffled index array until all indices have
     * been returned, then throws exception
     */
    public Item next() {      
      if (!hasNext()) throw new NoSuchElementException();
      int rand = randomNumbers[iteratorNum - 1];     
      Item item = queue[rand];
      iteratorNum--;
      return item;
    }
  }  
  public static void main(String[] args) {
    // unit testing
    int testNumber = 10;
    
    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
   
    for (int i = 0; i < testNumber; i++) {
      rq.enqueue(i);
    }
    
    StdOut.println("Sample:");
    for (int i = 0; i < testNumber; i++) {
      StdOut.println(rq.sample());
    }
    Iterator<Integer> it = rq.iterator();
    StdOut.println("Next");
    for (int i = 0; i < testNumber; i++) {
      StdOut.println(it.next());
    }
    StdOut.println("Dequeue");
    for (int i = 0; i < testNumber; i++) {
      StdOut.println(rq.dequeue());
    }
    
    StdOut.println("Size after dequeue: " + rq.size());
  }

}
