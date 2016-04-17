package edu.princeton.UnionFind;

public class UF{
        private int[] id; // Access to component ID
        private int count; // number of components

        public UF(int N){ // Initialize component id array
                setCount(N);
                setId(new int[N]);
                for (int i = 0; i < N; i++)
                        getId()[i] = i;
        }

        public int count(){
                return getCount();
        }

        public boolean connected(int p, int q){
                return find(p) == find(q);
        }

        public int find(int p){ 
                return getId()[p];
        }

        public void union(int p, int q){ // Put p and q into same component
                int pID = find(p);
                int qID = find(q);

                // Nothing to do if p and q are already in same
                // component

                if (pID == qID) return;

                // Change values from id[p] to id[q]

                for (int i = 0; i < getId().length; i++)
                        if (getId()[i] == pID) getId()[i] = qID;
                setCount(getCount() - 1);
        }

		public int[] getId() {
			return id;
		}

		public void setId(int[] id) {
			this.id = id;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
}


