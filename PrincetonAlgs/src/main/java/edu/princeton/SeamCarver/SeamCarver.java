package edu.princeton.SeamCarver;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

/**
 * Compilation: javac SeamCarver.java
 * Seam-carving is a content-aware image resizing technique where the image
 * is reduced in size by one pixel of height (or width) at a time. A vertical 
 * seam in an image is a path of pixels connected from the top to the bottom 
 * with one pixel in each row. (A horizontal seam is a path of pixels connected 
 * from the left to the right with one pixel in each column.)
 * @author Eddie Legg
 *
 */


public class SeamCarver {
	private int[][] color;
	private int height, width;
	
	/**
	 * Constructor with picture argument
	 * @param picture
	 */
	public SeamCarver(Picture picture) {
		if(picture == null) throw new NullPointerException();
		height = picture.height();
		width = picture.width();
		color = new int[width][height];
		for (int i = 0; i < color.length; i++) {
			for (int j = 0; j < color[i].length; j++) {
				setColorArray(picture, i, j);
			}
		}
	}
	
	/**
	 * Return the picture based on the current color array
	 * @return picture
	 */
	public Picture picture() {
		Picture picture = new Picture(width, height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color c = new Color(getRed(color[i][j]), getGreen(color[i][j]), getBlue(color[i][j]));
				picture.set(i, j, c);
			}
		}
		return picture;
	}
	
	/**
	 * Current picture width
	 * @return this.width
	 */
	public     int width() {
		return width;
	}
	
	/**
	 * Current picture height
	 * @return this.height
	 */
	public     int height() {
		return height;
	}
	
	/**
	 * Calculate the energy of a pixel. Find the energy of the X axis and Y axis. Energy of the pixel
	 * is the square root of energyX + energyY
	 * @param x pixel x coordinate
	 * @param y pixel y coordinate
	 * @return calculated energy
	 */
	public  double energy(int x, int y) {
		checkIndices(x, y);
		if (isBorder(x,y)) return 1000;
		double energyX = getEnergyX(x, y);
		double energyY = getEnergyY(x, y);
		// energy of pixel at column x and row y
		return Math.sqrt(energyX + energyY);
	}
	
	/**
	 * EnergyX is calculated by finding the difference between Red, Green
	 * and Blue values at (x +/- 1, y), then adding the square of Rdiff, Gdiff and
	 * Bdiff
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return energyX
	 */
	private double getEnergyX(int x, int y) {				
		double redDiff = Math.abs(getRed(color[x - 1][y]) - getRed(color[x + 1][y]));
		double greenDiff = Math.abs(getGreen(color[x - 1][y]) - getGreen(color[x + 1][y]));
		double blueDiff = Math.abs(getBlue(color[x - 1][y]) - getBlue(color[x + 1][y]));
		return (Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2));		
	}
	/**
	 * EnergyY is calculated by finding the difference between the min and max of Red, Green
	 * and Blue values at (x, y) and (x, y +/- 1), then adding the square of Rdiff, Gdiff and
	 * Bdiff
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return energyY
	 */
	private double getEnergyY(int x, int y) {
		double redDiff = Math.abs(getRed(color[x][y - 1]) - getRed(color[x][y + 1]));
		double greenDiff = Math.abs(getGreen(color[x][y - 1]) - getGreen(color[x][y + 1]));
		double blueDiff = Math.abs(getBlue(color[x][y - 1]) - getBlue(color[x][y + 1]));
		return (Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2));
	}
	
	/**
	 * Check for border pixel
	 * @param x coordinate
	 * @param y coordinate
	 * @return true if pixel is on border
	 */
	private boolean isBorder(int x, int y) {
		return (x == 0 || x == width - 1 || y == 0 || y == height - 1);
	}
	
	/**
	 * The behavior of findHorizontalSeam() is analogous to that of findVerticalSeam()
	 * except that it returns an array of length W such that entry x is the row number 
	 * of the pixel to be removed from column x of the image.
	 * @return seam
	 */
	public   int[] findHorizontalSeam() {
		int[] horizontalSeam = new int[width];
		int[][] vertexTo = new int[width][height];
		double[][] distTo = new double[width][height];
		double max = Double.POSITIVE_INFINITY;
		for (double[] row : distTo) {
			Arrays.fill(row, max);
		}
		for (int i = 0; i < height; i++) {
			vertexTo[0][i] = -1;
			distTo[0][i] = 0;
		}
		for (int i = 0; i < width - 1; i++) {			
			for (int j = 1; j < height - 1; j++) {
				for (int k = j - 1; k <= j + 1; k++) {
					if (distTo[i][k] + energy(i + 1, j) < distTo[i + 1][j]) {
						distTo[i + 1][j] = distTo[i][k] + energy(i + 1, j);
						vertexTo[i + 1][j] = k;
					}
				}
			}
		}
		int minIndex = 0;
		double minDist = Double.MAX_VALUE;
		for (int i = 0; i < height; i++) {
			if (distTo[width - 1][i] < minDist) {
				minDist = distTo[width - 1][i];
				minIndex = i;
			}
		}
		for (int i = width - 1; i >= 0; i--) {
			horizontalSeam[i] = minIndex;
			minIndex = vertexTo[i][minIndex];
		}
		return horizontalSeam;
	}
	
	/**
	 * Checks the adjacent pixels and "relaxes" them
	 * @param x
	 * @param y
	 * @return
	 */
	
	
	/**
	 * The findVerticalSeam() method returns an array of length H such that entry y 
	 * is the column number of the pixel to be removed from row y of the image.
	 * @return seam
	 */
	public   int[] findVerticalSeam() {
		int[] verticalSeam = new int[height];
		int[][] vertexTo = new int[width][height];
		double[][] distTo = new double[width][height];
		double max = Double.POSITIVE_INFINITY;
		for (double[] row : distTo) {
			Arrays.fill(row, max);
		}
		
		for (int i = 0; i < width; i++) {
			vertexTo[i][0] = -1;
			distTo[i][0] = 0;
		}
		for (int i = 0; i < height - 1; i++) {			
			for (int j = 1; j < width - 1; j++) {
				for (int k = j - 1; k <= j + 1; k++) {
					if (distTo[k][i] + energy(j, i + 1) < distTo[j][i + 1]) {
						distTo[j][i + 1] = distTo[k][i] + energy(j, i + 1);
						vertexTo[j][i + 1] = k;
					}
				}
			}
		}
		int minIndex = 0;
		double minDist = Double.MAX_VALUE;
		for (int i = 0; i < width; i++) {
			if (distTo[i][height - 1] < minDist) {
				minDist = distTo[i][height - 1];
				minIndex = i;
			}
		}
		for (int i = height - 1; i >= 0; i--) {
			verticalSeam[i] = minIndex;
			minIndex = vertexTo[minIndex][i];
		}
		return verticalSeam;
	}
	
	
	
	public    void removeHorizontalSeam(int[] seam) {
		checkSeam(seam);
		if (seam.length != width) throw new IllegalArgumentException();
		for (int i = 0; i < seam.length; i++) {
			if (seam[i] < 0 || seam[i] >= height) throw new IllegalArgumentException();
			for (int j = seam[i]; j < color[i].length - 1; j++) {
				color[i][j] = color[i][j + 1];
			}			
		}
		height--;
	}
	
	public    void removeVerticalSeam(int[] seam) {
		checkSeam(seam);
		if (seam.length != height) throw new IllegalArgumentException();
		for (int i = 0; i < seam.length; i++) {
			if (seam[i] < 0 || seam[i] >= width) throw new IllegalArgumentException();
			for (int j = seam[i]; j < color.length - 1; j++) {
				color[j][i] = color[j + 1][i];
			}
		}
		width--;
	}
	
	/**
	 * Convert a pixel RGB value to a single int for storage. Int value is stored as three
	 * 3-digit integers representing the RGB values from 0-255. Example: 255255255 = 
	 * rgb(255, 255, 255). 
	 * @param x column of pixel/x index of color array
	 * @param y row of pixel/y index of color array
	 */
	private void setColorArray(Picture picture, int x, int y) {
		int colorInt = (picture.get(x, y).getRed()) * 1000000;
		colorInt += (picture.get(x, y).getGreen()) * 1000;
		colorInt += (picture.get(x, y).getBlue());
		color[x][y] = colorInt;
	}
	
	private int getRed(int i) {
		return i / 1000000;
	}
	
	private int getGreen(int i) {
		return ((i % 1000000) / 1000);
	}
	
	private int getBlue(int i) {
		return ((i % 1000000) % 1000);
	}
	
	private void checkIndices(int x, int y) {
		if (!(x >= 0 && x < width && y >= 0 && y < height)) throw new IndexOutOfBoundsException();
	}
	
	private void checkSeam(int[] seam) {
		if (seam == null) throw new NullPointerException();
		for (int i = 0; i < seam.length - 1; i++) {
			if (Math.abs(seam[i] - seam[i + 1]) > 1) throw new IllegalArgumentException();
		}
	}
}
