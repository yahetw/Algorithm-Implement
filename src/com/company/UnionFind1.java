package com.company;

public class UnionFind1 {


	// WeightedQuickUnionUF from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/WeightedQuickUnionUF.java.html
	// JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/WeightedQuickUnionUF.html
	public static class WeightedQuickUnionUF {
		private int[] parent;   // parent[i] = parent of i
		private int[] size;     // size[i] = number of elements in subtree rooted at i
		private int count;      // number of components

		/**
		 * Initializes an empty union-find data structure with
		 * {@code n} elements {@code 0} through {@code n-1}.
		 * Initially, each element is in its own set.
		 *
		 * @param  n the number of elements
		 * @throws IllegalArgumentException if {@code n < 0}
		 */
		public WeightedQuickUnionUF(int n) {
			count = n;
			parent = new int[n];
			size = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
				size[i] = 1;
			}
		}

		/**
		 * Returns the number of sets.
		 *
		 * @return the number of sets (between {@code 1} and {@code n})
		 */
		public int count() {
			return count;
		}

		/**
		 * Returns the canonical element of the set containing element {@code p}.
		 *
		 * @param  p an element
		 * @return the canonical element of the set containing {@code p}
		 * @throws IllegalArgumentException unless {@code 0 <= p < n}
		 */

		//find(p)，找爸爸
		public int find(int p) {
			validate(p);
			while (p != parent[p])
				p = parent[p];
			return p;
		}

		/**
		 * Returns true if the two elements are in the same set.
		 *
		 * @param  p one element
		 * @param  q the other element
		 * @return {@code true} if {@code p} and {@code q} are in the same set;
		 *         {@code false} otherwise
		 * @throws IllegalArgumentException unless
		 *         both {@code 0 <= p < n} and {@code 0 <= q < n}
		 * @deprecated Replace with two calls to {@link #find(int)}.
		 */
		@Deprecated
		public boolean connected(int p, int q) {
			return find(p) == find(q);
		}

		// validate that p is a valid index
		//就只是單純驗證是不是一個合法的
		private void validate(int p) {
			int n = parent.length;
			if (p < 0 || p >= n) {
				throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
			}
		}

		/**
		 * Merges the set containing element {@code p} with the
		 * the set containing element {@code q}.
		 *
		 * @param  p one element
		 * @param  q the other element
		 * @throws IllegalArgumentException unless
		 *         both {@code 0 <= p < n} and {@code 0 <= q < n}
		 */
		public void union(int p, int q) {
			int rootP = find(p);
			int rootQ = find(q);
			if (rootP == rootQ) return;

			// make smaller root point to larger one
			if (size[rootP] < size[rootQ]) {
				parent[rootP] = rootQ;
				size[rootQ] += size[rootP];
			}
			else {
				parent[rootQ] = rootP;
				size[rootP] += size[rootQ];
			}
			count--;
		}

		// todo: write an algorithm to find the height of the tree that holds the connected component
		// height of tree is 0 if there is only one item in the tree.
		public int treeHeight(int p) {

			int max =0;

	    	/*for(int i =0;i<20;i++) {
	    		if (find(i)==find(p) ) {
	    			temp[i] = i;
	    		}else temp[i] = 0;
	    	}*/


			for(int i=0;i<20;i++) {

				//找出跟p相同root的node(i點)
				if (find(p)== find(i)) {
					int maxtemp=0;
					int itemp=i;

					//計算每個i點find的次數，並存到每回合i點的maxtemp中(也就是該i點的當前高度)
					//每個迴圈最後會比較每個i點的maxtemp(也就是每個與p相同root的node，他們的高度)跟max大小，所以可以找出該component的tree height
					while (itemp != parent[itemp]) {
						itemp= parent[itemp];
						maxtemp+=1;
					}

					//比較每個max temp的最大值，若maxtemp大於max，則讓maxtemp為max
					if(maxtemp >max) {
						max= maxtemp;
					}

				}
			}
			return max;
		}

		// todo: write an algorithm to find the size of the connected component with item p
		public int componentSize(int p) {
			// write your code here

			//找到他root是誰，並回傳該component的size
			return size[find(p)];
		}
	}

	public static void main(String[] args) {
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(20);
		uf.union(1, 2);
		uf.union(3, 4);
		uf.union(1, 3);
		uf.union(5, 1);
		uf.union(7, 2);
		uf.union(7, 3);
		uf.union(1, 6);
		uf.union(10, 11);
		uf.union(15, 12);
		uf.union(2, 17);
		uf.union(3, 15);
		uf.union(4, 11);
		uf.union(1, 3);
		uf.union(6, 8);
		uf.union(8, 19);
		uf.union(11, 17);
		uf.union(12, 15);
		uf.union(11, 18);
		uf.union(5, 14);

		System.out.println("Component tree height of 16 = " + uf.treeHeight(16)); // expected output 0
		System.out.println("Component tree height of 10 = " + uf.treeHeight(10)); // expected output 2
		System.out.println("Component tree height of 12 = " + uf.treeHeight(12)); // expected output 2

		System.out.println("Component size of 5 = " + uf.componentSize(5)); // expected output 16
		System.out.println("Component size of 16 = " + uf.componentSize(16)); // expected output 1
	}
}
