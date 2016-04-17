package edu.princeton.UnionFind;


public class QuickUF extends UF{

	public QuickUF(int N) {
		super(N);
		// TODO Auto-generated constructor stub
	}
	
	public int find(int p){
		while (p != super.getId()[p]) p = super.getId()[p];
		return p;		
	}
	
	public void union (int p, int q){
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		super.getId()[i]= j;
		super.setCount(super.getCount() - 1);
	}
}
