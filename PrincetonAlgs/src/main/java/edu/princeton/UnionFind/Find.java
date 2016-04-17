package edu.princeton.UnionFind;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;
import java.io.*;

public class Find {
	public static void main(String[] args){ // Solve dynamic connectivity problem on StdIn
		/* int N = StdIn.readInt();
		UF uf = new UF(N);
		while (!StdIn.isEmpty())
		{
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (uf.connected(p, q)) continue;
			uf.union(p, q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(uf.count() + " components"); */
		String file1 = "numbers1";
		try {
			Scanner s = new Scanner(new File(file1));
			int N = s.nextInt();
			UF uf = new UF(N);
			while (s.hasNext())
			{
				int p = s.nextInt();
				int q = s.nextInt();
				if (uf.connected(p, q)) continue;
				uf.union(p, q);
			}
			s.close();
			for (int i = 0; i < N; i++) System.out.print(uf.getId()[i] + " ");
			System.out.print("\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String file2 = "numbers2";
		try {
			Scanner s = new Scanner(new File(file2));
			int N = s.nextInt();
			WeightedQUF wquf = new WeightedQUF(N);
			while (s.hasNext())
			{
				int p = s.nextInt();
				int q = s.nextInt();
				if (wquf.connected(p, q)) continue;
				wquf.union(p, q);
			}
			s.close();
			for (int i = 0; i < N; i++) System.out.print(wquf.getId()[i] + " ");
			System.out.print("\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
