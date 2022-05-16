
//M104020052 張孫杰 M104020026 陳亭椰
import java.util.Iterator;
import java.util.NoSuchElementException;

// todo: student 1 id & name, student 2 id & name
// todo: Please implement the put2 and get2 functions that can reduce the amount of collisions
// by at least 30%.
// In your put2 function, you need to add 1 to the totalCollisions whenever you encounter a
// collision (selection of array position is occupied).

//LinearProbingHashST1 is modified from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LinearProbingHashST.java.html
//JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/LinearProbingHashST.html
public class LinearProbingHashST1<Key, Value> {

    // must be a power of 2
    private static final int INIT_CAPACITY = 4000;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values
    private static int totalCollisions = 0;


    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashST1() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the initial capacity
     */
    public LinearProbingHashST1(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and m-1
    private int hashTextbook(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
    // (from Java 7 implementation, protects against poor quality hashCode() implementations)
    private int hash(Key key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (m-1);
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        int i;
        int collisionCount = 0;
        for (i = hash(key); keys[i] != null; i = (i + 100) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
            collisionCount++;
        }

        totalCollisions += collisionCount;

        keys[i] = key;
        vals[i] = val;
        n++;
    }

    // Write your code her
    // We use Quadratic and Double hash to do this.
    public void put2(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        int i;
        int collisionCount = 0;
        for (i = hash(key); keys[i] != null; i = (i + hashTextbook(key) + collisionCount^collisionCount) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
            collisionCount++;
        }

        totalCollisions += collisionCount;

        keys[i] = key;
        vals[i] = val;
        n++;

    }

    /**
     * Returns the value associated with the specified key.
     * @param key the key
     * @return the value associated with {@code key};
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 100) % m)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }

    // Write your code here
    public Value get2(Key key) {
    	int collisionCount = 0;
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + hashTextbook(key) + collisionCount^collisionCount) % m) {
            if (keys[i].equals(key))
                return vals[i];
        	collisionCount++;
        }
        return null;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        assert check();
    }

    public void deleteAll() {
        m = INIT_CAPACITY;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
    }

    /**
     * Returns all keys in this symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in this symbol table
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }

    // integrity check - don't check after each put() because
    // integrity not maintained during a delete()
    private boolean check() {

        // check that hash table is at most 50% full
        if (m < 2*n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }

 // From https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Queue.java.html
    public class Queue<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of queue
        private Node<Item> last;     // end of queue
        private int n;               // number of elements on queue

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty queue.
         */
        public Queue() {
            first = null;
            last  = null;
            n = 0;
        }

        /**
         * Returns true if this queue is empty.
         *
         * @return {@code true} if this queue is empty; {@code false} otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this queue.
         *
         * @return the number of items in this queue
         */
        public int size() {
            return n;
        }

        /**
         * Returns the item least recently added to this queue.
         *
         * @return the item least recently added to this queue
         * @throws NoSuchElementException if this queue is empty
         */
        public Item peek() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            return first.item;
        }

        /**
         * Adds the item to this queue.
         *
         * @param  item the item to add
         */
        public void enqueue(Item item) {
            Node<Item> oldlast = last;
            last = new Node<Item>();
            last.item = item;
            last.next = null;
            if (isEmpty()) first = last;
            else           oldlast.next = last;
            n++;
        }

        /**
         * Removes and returns the item on this queue that was least recently added.
         *
         * @return the item on this queue that was least recently added
         * @throws NoSuchElementException if this queue is empty
         */
        public Item dequeue() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            Item item = first.item;
            first = first.next;
            n--;
            if (isEmpty()) last = null;   // to avoid loitering
            return item;
        }

        /**
         * Returns a string representation of this queue.
         *
         * @return the sequence of items in FIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Item item : this) {
                s.append(item);
                s.append(' ');
            }
            return s.toString();
        }

        /**
         * Returns an iterator that iterates over the items in this queue in FIFO order.
         *
         * @return an iterator that iterates over the items in this queue in FIFO order
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

    private static void showImprovement(int collisions1, int collisions2) {
        System.out.println("Improvement: " + String.format("%.2f", 100*(collisions1 - collisions2)/(double)collisions1) + "%");
        System.out.println();
    }

    public static void main(String[] args) {
        LinearProbingHashST1<String, Integer> st = new LinearProbingHashST1<String, Integer>();

        for(int i=0; i<2000; i++) {
            st.put("A"+i, i);
        }
        st.put("AACSB", 100);
        st.put("ITR", 1123);
        st.put("NSYSU", 1341);
        st.put("Student", 1341);
        st.put("Class", 2002);

        System.out.println("Total Collisions = " + totalCollisions);
        int collisions1 = totalCollisions;
        
        // print keys
        System.out.println("A100 " + st.get("A100"));
        System.out.println("A200 " + st.get("A200"));
        System.out.println("A553 " + st.get("A553"));
        System.out.println("A900 " + st.get("A900"));
        System.out.println("A1500 " + st.get("A1500"));
        System.out.println("NSYSU " + st.get("NSYSU"));
        System.out.println("ITR " + st.get("ITR"));

        st.deleteAll();
        System.out.println();
        totalCollisions = 0;

        for(int i=0; i<2000; i++) {
            st.put2("A"+i, i);
        }
        st.put2("AACSB", 100);
        st.put2("ITR", 1123);
        st.put2("NSYSU", 1341);
        st.put2("Student", 1341);
        st.put2("Class", 2002);

        System.out.println("Total Collisions 2 = " + totalCollisions);
        int collisions2 = totalCollisions;

        System.out.println("A100 " + st.get2("A100"));
        System.out.println("A200 " + st.get2("A200"));
        System.out.println("A553 " + st.get2("A553"));
        System.out.println("A900 " + st.get2("A900"));
        System.out.println("A1500 " + st.get2("A1500"));
        System.out.println("NSYSU " + st.get2("NSYSU"));
        System.out.println("ITR " + st.get2("ITR"));

        System.out.println();
        showImprovement(collisions1,collisions2);

    }


}
