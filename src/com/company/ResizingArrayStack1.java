package com.company;//todo: M104020052 & 張孫杰, M104020036 & 邱沛寰

//todo: modify push, pop, and resize functions to achieve the following:
//When the stack overflows after a push operation, increase the array size by 1 to accommodate the new item.
//But if 3 stack overflow occurred consecutively, increase the array size by 3 upon the 3th stack overflow.
//DO NOT EDIT other functions but you can add global variables

import java.util.Iterator;
import java.util.NoSuchElementException;

//ResizingArrayStack1 is modified from ResizingArrayStack from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/ResizingArrayStack.java.html
//JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/ResizingArrayStack.html
public class ResizingArrayStack1<Item> implements Iterable<Item> {

    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 1;

    private Item[] a;         // array of items
    private int n;            // number of elements on stack
    private int resize_times=0;	//建立一個變數計算resize的次數

    /**
     * Initializes an empty stack.
     */
    public ResizingArrayStack1() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    /**
     * Is this stack empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size() {
        return n;
    }

    // Return the size of the stack array
    public int stackArraySize() {
        return a.length;
    }

    // resize the underlying array holding the elements
    // todo: edit this function if necessary
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;
    }


    /**
     * Adds the item to this stack.
     * @param item the item to add
     */

    // todo: edit this function if necessary
    public void push(Item item) {
        if (n == a.length) {
            resize(a.length+1);
            resize_times +=1;//每push一個item,發生resize,resize_times就加一(計算連續resize的次數)
            if(resize_times==3){
                resize(a.length+2);
                resize_times=0;//resize的次數等於三次,則再幫它加2,總共就加3次
            }

        }
        a[n++] = item;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    // todo: edit this function if necessary
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[n-1];
        a[n-1] = null;                              // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length/2);
        resize_times=0; //當pop時，計算resize次數的變數(resize_times)歸零
        return item;
    }


    /**
     * Returns (but does not remove) the item most recently added to this stack.
     * @return the item most recently added to this stack
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[n-1];
    }

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * @return an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = n-1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }


    /**
     * Unit tests the {@code Stack} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        ResizingArrayStack1<String> stack = new ResizingArrayStack1<String>();
        stack.push("5");
        System.out.println("Stack array size: " + stack.stackArraySize()); // 1
        stack.push("1");
        stack.push("2");
        System.out.println("Stack array size: " + stack.stackArraySize()); // 3
        stack.push("2");
        System.out.println("Stack array size: " + stack.stackArraySize()); // 6
        stack.push("2");
        stack.push("5");
        stack.pop();
        stack.push("12");
        stack.push("3");
        stack.push("2");
        System.out.println("Stack array size: " + stack.stackArraySize()); // 8
        stack.push("7");
        System.out.println("Stack array size: " + stack.stackArraySize()); // 11
        stack.pop();
        stack.push("1");
        stack.push("2");
        stack.push("12");
        stack.push("3");
        stack.push("2");
        System.out.println("Stack array size: " + stack.stackArraySize()); // 13
        stack.push("1");
        System.out.println("Stack array size: " + stack.stackArraySize()); // 16
    }
}