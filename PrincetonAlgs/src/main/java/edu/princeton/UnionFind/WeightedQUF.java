package edu.princeton.UnionFind;


public class WeightedQUF extends QuickUF{
	private int[]sz;

	public WeightedQUF(int N) {
		super(N);
		sz = new int[N];
		for (int i = 0; i < N; i++) sz[i] = 1;
	}
	
	public void union (int p, int q){
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		
		// Make smaller root point to larger one
		if (sz[i] < sz[j]){
			super.getId()[i] = j;
			sz[j] += sz[i];			
		}
		else{
			super.getId()[j] = i;
			sz[i] += sz[j];			
		}
		super.setCount(super.getCount() - 1);
	}

}
