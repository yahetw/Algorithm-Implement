// todo: student 1 id & name, student 2 id & name
// todo: Complete the function nodeToRemoveToSplitInto3Components that can split the calling
// undirected graph into three connected components and return the node to remove. 
// DO NOT EDIT other functions NOR add global variables.

import java.util.Iterator;
import java.util.NoSuchElementException;

//Graph1 is modified from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Graph.java.html
//JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Graph.html
public class Graph1 {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    
    /**
     * Initializes an empty graph with {@code V} vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public Graph1(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }
    
    // Sets the number of edges in this graph
    public void setE(int e) {
    	E = e;
    }
    
    // Returns a copy of the edges from in this graph
    public Bag<Integer>[] copyEdges(){
    	Bag<Integer>[] adj2 = (Bag<Integer>[]) new Bag[V];
    	for (int v = 0; v < V; v++) {
            adj2[v] = adj[v];
        }
    	return adj2;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the undirected edge v-w to this graph.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        
        for (int u : adj[v]) {
        	if(u == w) {
        		return;
        	}
        }
        
        for (int u : adj[w]) {
        	if(u == v) {
        		return;
        	}
        }
        
        adj[v].add(w);
        adj[w].add(v);
    }


    /**
     * Returns the vertices adjacent to vertex {@code v}.
     *
     * @param  v the vertex
     * @return the vertices adjacent to vertex {@code v}, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }
    
    // Returns one node that can be removed to break the graph into 3 components
    public int nodeToRemoveToSplitInto3Components() {
        // write your code here
        
        // no node removal can break the graph into 3 components
        return -1;
    }
    
    public Graph1 replicateGraph(Graph1 G) {
    	Graph1 G2 = new Graph1(G.V());
//    	G2.adj = copyEdges();
    	for (int v = 0; v < V; v++) {
            for (int w : adj[v]) {
                G2.addEdge(v, w);
            }
        }
//    	G2.setE(G.E());
    	return G2;
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    
    //Bag is modified from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Bag.java.html
    //JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Bag.html
    public class Bag<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of bag
        private int n;               // number of elements in bag

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty bag.
         */
        public Bag() {
            first = null;
            n = 0;
        }

        /**
         * Returns true if this bag is empty.
         *
         * @return {@code true} if this bag is empty;
         *         {@code false} otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this bag.
         *
         * @return the number of items in this bag
         */
        public int size() {
            return n;
        }

        /**
         * Adds the item to this bag.
         *
         * @param  item the item to add to this bag
         */
        public void add(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
            n++;
        }
        
        // Removes the item from this bag
        public void remove(Item item) {
        	// if first
        	if(first.item == item) {
        		first = first.next;        		
        	}else{
        		// find item
        		Node<Item> current;
        		for(current = first; true; current = current.next) {
        			if(current.next.item == item) {
        				current.next = current.next.next;
        				return;
        			}
        		}
        	}
        }


        /**
         * Returns an iterator that iterates over the items in this bag in arbitrary order.
         *
         * @return an iterator that iterates over the items in this bag in arbitrary order
         */
        public Iterator<Item> iterator()  {
            return new LinkedIterator(first);  
        }

        // an iterator, doesn't implement remove() since it's optional
        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext()  { return current != null;                     }
            public void remove()      { throw new UnsupportedOperationException();  }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next; 
                return item;
            }
        }

    }
    
    //ConnectedComponent is modified from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/DepthFirstSearch.java.html
    //JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/DepthFirstSearch.html
    public static class ConnectedComponent {
        private boolean[] marked;    // marked[v] = is there an s-v path?
        private int count;           // number of vertices connected to s
        private int[] id;

        /**
         * Computes the vertices in graph {@code G} that are
         * connected to the source vertex {@code s}.
         * @param G the graph
         * @param s the source vertex
         * @throws IllegalArgumentException unless {@code 0 <= s < V}
         */
        public ConnectedComponent(Graph1 G, int s) {
        	marked = new boolean[G.V()];
            id = new int[G.V()];
            for (int v = 0; v < G.V(); v++)
            {	
               if(G.adj[v] == null) continue;
               if (!marked[v])
               {
                  dfs(G, v);
                  count++;
               }
            }
        }
        
        public int id(int v){
        	return id[v];
        }
        
        public boolean connected(int v, int w) { 
        	return id[v] == id[w];
        }

        // depth first search from v
        private void dfs(Graph1 G, int v) {
            marked[v] = true;
            id[v] = count;
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    dfs(G, w);
                }
            }
        }

        /**
         * Is there a path between the source vertex {@code s} and vertex {@code v}?
         * @param v the vertex
         * @return {@code true} if there is a path, {@code false} otherwise
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public boolean marked(int v) {
            validateVertex(v);
            return marked[v];
        }

        /**
         * Returns component count
         */
        public int count() {
            return count;
        }

        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            int V = marked.length;
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }

    }

    
    /**
     * Unit tests the {@code Graph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    	
    	Graph1 dg1 = new Graph1(10);
    	dg1.addEdge(1, 2);
    	dg1.addEdge(0, 1);
    	dg1.addEdge(0, 2);
    	dg1.addEdge(1, 3);
    	dg1.addEdge(0, 4);
    	dg1.addEdge(7, 4);
    	dg1.addEdge(4, 6);
    	dg1.addEdge(4, 5);
    	dg1.addEdge(5, 8);
    	dg1.addEdge(5, 9);
    	dg1.addEdge(6, 9);
    	
    	System.out.println(dg1);
    	
    	int nodeToRemove = dg1.nodeToRemoveToSplitInto3Components();
        System.out.println("Remove " + nodeToRemove + " To Break Into 3 Components");
        System.out.println();
        
    	Graph1 dg2 = new Graph1(16);
    	dg2.addEdge(0, 1);
    	dg2.addEdge(0, 7);
    	dg2.addEdge(1, 10);
    	dg2.addEdge(7, 10);
    	dg2.addEdge(10, 8);
    	dg2.addEdge(11, 8);
    	dg2.addEdge(9, 10);
    	dg2.addEdge(10, 6);
    	dg2.addEdge(3, 5);
    	dg2.addEdge(6, 3);
    	dg2.addEdge(11, 9);
    	dg2.addEdge(2, 3);
    	dg2.addEdge(2, 4);
    	dg2.addEdge(2, 5);
    	dg2.addEdge(5, 12);
    	dg2.addEdge(5, 13);
    	dg2.addEdge(14, 5);
    	dg2.addEdge(5, 15);
    	
    	System.out.println(dg2);
    	
    	nodeToRemove = dg2.nodeToRemoveToSplitInto3Components();
        System.out.println("Remove " + nodeToRemove + " To Break Into 3 Components");
        
    }
}


