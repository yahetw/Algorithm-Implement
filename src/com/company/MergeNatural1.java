package com.company;

//todo: student 1 M104020026 & 陳亭椰, student 2 M104020052 & 張孫杰
//todo: modify sort and findNextRun functions to implement a mergesort that finds natural ordered items
//with "runs" and merges them using a bottom up approach.
//DO NOT EDIT other functions NOR add global variables.

//MergeNatural1 is modified from MergeBU from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/MergeBU.java.html
//JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/MergeBU.html
public class MergeNatural1 {

    // This class should not be instantiated.
    private MergeNatural1() { }

    // stably merge a[lo..mid] with a[mid+1..hi] using aux[lo..hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];  // this copying is unneccessary
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }

    }

    class Run{
        int startIndex;
        int endIndex;
        public Run(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
    }

    // todo: write code in this function
    public Run findNextRun(Comparable[]a, int startIndex) {
        int endIndex = a.length -1;

        //找出該次startIndex的endIndex(結束位址)
        for(int i = startIndex ; i< endIndex;i++) {
            if(less(a[i+1],a[i])) {
                endIndex = i;
                //如果找到endIndex在哪，跳出for迴圈
                break;
            }
        }
        // do not modify the following lines
        System.out.println("Run start: " + startIndex + ", end: " + endIndex);
        show(a,startIndex,endIndex);
        return new Run(startIndex, endIndex);
    }

    // todo: write code in this function
    public static void sort(Comparable[] a) {
        int n = a.length;

        Comparable[] aux = new Comparable[n];
        MergeNatural1 mn1 = new MergeNatural1();

        //建立i變數，用於終止while迴圈
        int i = 0;

        //如果陣列a沒有排好，繼續while
        while(!isSorted(a)){

            //建立一個Run物件，叫做temp，儲存第一個run的startIndex跟endIndex位址
            Run temp = mn1.findNextRun(a, i);
            //lo, mid(merge sort的輸入)
            int lo = temp.startIndex;
            int mid = temp.endIndex;

            //如果第一個run的endIndex跑到陣列a結束，重置起始排序位址，進入到下一圈while
            if(mid == a.length -1 ) {i = 0; continue;};

            //建立一個Run物件，叫做temp2，儲存第二個run的startIndex跟endIndex位址
            Run temp2 = mn1.findNextRun(a, mid + 1);
            //hi會等於第二個run的結束位址(merge sort的輸入)
            int hi = temp2.endIndex;

            //MergeSort第一個run跟第二個run
            merge(a, aux, lo, mid, hi);

            //如果第一個run的endIndex跑到陣列a結束，重置起始排序位址，進入到下一圈while
            if(hi == a.length -1 ) {i = 0; continue;};

            //起始排序位址會等於第二個run的結束位址
            i = hi + 1;
        }

        assert isSorted(a);
    }

    /***********************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    // print array between indices to standard output
    private static void show(Comparable[] a, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] a = {1,2,3,7,1,30,31,9,77,14,5,1,3,800,0,20};
        MergeNatural1.sort(a);
        show(a);
    }
}
