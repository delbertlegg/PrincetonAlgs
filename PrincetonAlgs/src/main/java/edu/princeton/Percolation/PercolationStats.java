package edu.princeton.Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double[] ratio;
  // private double[] stdev;
  // private double ratio;

  /**
   * Default constructor. Creates new PercolationStats with a N-N grid that
   * executes T times and a Percolation(N)
   * 
   * @param num size of grid.
   * @param times frequency of testing
   */
  public PercolationStats(int num, int times) {
    // perform T independent experiments on an N-by-N grid
    if (num <= 0 || times <= 0) {
      throw new IllegalArgumentException();
    }
    int gridSize = num;
    // mean = new double[times];
    // stdev = new double[times];
    ratio = new double[times];
    for (int i = 0; i < times; i++) {
      ratio[i] = 0;
    }
    for (int i = 0; i < times; i++) {
      Percolation perc = new Percolation(gridSize);
      int open = 0;
      while (!perc.percolates()) {
        int rand1 = StdRandom.uniform(num) + 1;
        int rand2 = StdRandom.uniform(num) + 1;
        while (!perc.isOpen(rand1, rand2)) {
          perc.open(rand1, rand2);
          open++;
        }
      }
      double percRatio = (open * 1.0000000) / (num * num);
      ratio[i] = percRatio;
      // ratio += ((open * 1.0000000) / (num * num));
      // mean[i] = ratio;
      // stdev[i] = ratio;
    }
  }

  public double mean() {
    // sample mean of percolation threshold
    return StdStats.mean(ratio);
  }

  public double stddev() {
    // sample standard deviation of percolation threshold
    return StdStats.stddev(ratio);
  }

  public double confidenceLo() {
    return mean() - ((1.96 * stddev()) / Math.sqrt(ratio.length));
    // low endpoint of 95% confidence interval
  }

  public double confidenceHi() {
    // high endpoint of 95% confidence interval
    return mean() + ((1.96 * stddev()) / Math.sqrt(ratio.length));
  }

  /**
   * Main method. Takes command line arguments for num grid size and time
   * frequency of testing
   * 
   * @param args 0 num 1 times
   */

  public static void main(String[] args) {
    // test client (described below)
    int num = Integer.parseInt(args[0]);
    int times = Integer.parseInt(args[1]);

    PercolationStats ps = new PercolationStats(num, times);

    StdOut.println("mean                    = " + ps.mean());
    StdOut.println("stddev                  = " + ps.stddev());
    StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());

  }
}
