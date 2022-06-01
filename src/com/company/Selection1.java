package com.company;

import java.util.Comparator;

//todo: student 邱沛寰, m104020036 & 張孫杰, m104020052

//todo: write code in the sort(Comparable[] a) function to sort the array as shown in the expected output.
//DO NOT EDIT other functions NOR add global variables. 

//Selection1 is modified from Selection from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/ResizingArrayStack.java.html
//JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/ResizingArrayStack.html

public class Selection1 {

    // This class should not be instantiated.
    private Selection1() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */

    // todo: write your code in this function
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i+1; j < n; j++) {
            	// parse input string, run two levels of comparisons
            	String stringPartJ = (((String) a[j]).replaceAll("[^A-Za-z]", ""));
            	int integerPartJ = Integer.parseInt(((String) a[j]).replaceAll("[^0-9]", ""));
            	
            	String stringPartMin = (((String) a[min]).replaceAll("[^A-Za-z]", ""));
            	int integerPartMin = Integer.parseInt(((String) a[min]).replaceAll("[^0-9]", ""));            	

            	
            	if (stringPartJ.isEmpty()&&stringPartMin.isEmpty()) {
            		if(integerPartJ>integerPartMin)min = j;}//如果文字部分為空，則比較數字，Ｊ大於Min，兩者交換
            	else if (!stringPartJ.isEmpty()&&stringPartMin.isEmpty())
            		min=j;//如果Ｊ的文字部分不為空，Min的文字部分為空，Ｊ與Min直接交換
            	else if (!stringPartJ.isEmpty()&&!stringPartMin.isEmpty()){
            		if (less(stringPartJ, stringPartMin))
            			min = j;//如果Ｊ和Min的文字部分皆不為空，比較字母順序
            		else if(stringPartJ.equals(stringPartMin))
            			if(integerPartJ>integerPartMin)min=j;}//如果Ｊ和Min的文字部分皆不為空，且Ｊ與Min的字母相同,則比較數字，Ｊ大於Min，兩者交換
   
            }
            exch(a, i, min);
            assert isSorted(a, 0, i);
        }
       
        assert isSorted(a);
    }

    /**
     * Rearranges the array in ascending order, using a comparator.
     * @param a the array
     * @param comparator the comparator specifying the order
     */
    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i+1; j < n; j++) {
                if (less(comparator, a[j], a[min])) min = j;
            }
            exch(a, i, min);
            assert isSorted(a, comparator, 0, i);
        }
        assert isSorted(a, comparator);
    }


   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
    }
        
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/

    // is the array a[] sorted?
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }
        
    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is the array a[] sorted?
    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(comparator, a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    /**
     * Reads in a sequence of strings from standard input; selection sorts them; 
     * and prints them to standard output in ascending order. 
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = {"1000","100","jk200","t1","a300","ba1200","a1","ab5","ca1","150","a100","b10","bb1000","ba1000","z100"};
        Selection1.sort(a);
        show(a); // expected output: a300 a100 a1 ab5 b10 ba1200 ba1000 bb1000 ca1 jk200 t1 z100 1000 150 100
    }
}
