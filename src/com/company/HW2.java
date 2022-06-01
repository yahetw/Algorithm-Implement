package com.company;
//TODO: M104020052 張孫杰
//TODO: This assignment aims to write a function simulate to allocate the course process by student’s preference.
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HW2 {
    
  private HW2() {}

  public static class Heap{
    private Heap() { }

    public static void sort(Course[] pq) {
      int n = pq.length;

      // heapify phase
      for (int k = n/2; k >= 1; k--)
        sink(pq, k, n);

      // sortdown phase
      // write your code here
      int k = n;

      while(n > 1) {
        exch(pq, 1, n--);  // 最大的跟最後面換
        if (less(pq, 2, 3)) {  //如果左子樹比較小
          exch(pq, 1, 3);  //將右子樹交換到root
          sink(pq, 3, n);  //將右子樹sink
        }
        else {
          exch(pq, 1, 2);  //相反
          sink(pq, 2, n);
        }
      }
    }

    private static void sink(Course[] pq, int k, int n) {
      while (2*k <= n) {
        int j = 2*k;
        if (j < n && less(pq, j, j+1)) j++;
        if (!less(pq, k, j)) break;
        exch(pq, k, j);
        k = j;
      }
    }

    private static boolean less(Course[] pq, int i, int j) {
      return pq[i-1].compareTo(pq[j-1]) < 0;
    }

    private static void exch(Object[] pq, int i, int j) {
      Object swap = pq[i-1];
      pq[i-1] = pq[j-1];
      pq[j-1] = swap;
    }
  }

  public static class Course{
    int id = 0; // course's id. ITR->1, MIS->2, DataBase->3, ResearchMethod->4
    String name; // course's name
    Student[] candidate; // The course selection result. 
    
    // Course should initial by course id and name.
    private Course(int id, String name, int limit_number) {
        this.id = id;
        this.name = name;
        this.candidate = new Student[limit_number];
    }
    
  }
  
  public static class Student{
    int year; // Student's year. freshman->1, sophomore->2, junior->3, senior ->4
    int id; // Unique student id
    //A set of student's preferences of courses id. e.g. [4,3,2,1]. The first priority of course is 4, which means ResearchMethod
    int[] preference;
    
    // Student should initial by year, id ,and candidate_courses.
    private Student(int year, int id, int[] preference) {
        this.year = year;
        this.preference = preference;
        this.id = id;
    }
    
    //overriding the toString() method
    @Override
    public String toString()
    {
        return "大"+this.year + " 學號" + this.id;
    }
  }
     
  // Test case 1
  private static void test1() {
    Course[] courses = new Course[4];
    courses[0] = new Course(1, "ITR", 3);
    courses[1] = new Course(2, "MIS", 3);
    courses[2] = new Course(3, "DataBase", 3);
    courses[3] = new Course(4, "ResearchMethod", 3);
    
    Student[] students = new Student[12];
    students[0] =  new Student(1, 11, new int[]{1, 2, 3, 4});
    students[1] =  new Student(1, 12, new int[]{1, 2, 3, 4});
    students[2] =  new Student(1, 13, new int[]{1, 2, 3, 4});
    students[3] =  new Student(2, 21, new int[]{1, 2, 3, 4});
    students[4] =  new Student(2, 22, new int[]{1, 2, 3, 4});
    students[5] =  new Student(2, 23, new int[]{1, 2, 3, 4});
    students[6] =  new Student(3, 31, new int[]{1, 2, 3, 4});
    students[7] =  new Student(3, 32, new int[]{1, 2, 3, 4});
    students[8] =  new Student(3, 33, new int[]{1, 2, 3, 4});
    students[9] =  new Student(4, 41, new int[]{1, 2, 3, 4});
    students[10] =  new Student(4, 42, new int[]{1, 2, 3, 4});
    students[11] =  new Student(4, 43, new int[]{1, 2, 3, 4});
    
    System.out.println("Test case1:");
    long startTime = System.nanoTime();
    Course[] result = simulate(students, courses);
    long estimatedTime = System.nanoTime() - startTime;
    print_course(result);
    print_first_priority(result);
    System.out.println("Performance(time): "+estimatedTime);
  }
  
  // Test case 2
  private static void test2() {
    Course[] courses = new Course[3];
    courses[0] = new Course(1, "ITR", 6);
    courses[1] = new Course(2, "MIS", 2);
    courses[2] = new Course(3, "DataBase", 4);
    
    Student[] students = new Student[12];
    students[0] =  new Student(1, 11, new int[]{1, 2, 3});
    students[1] =  new Student(1, 12, new int[]{1, 2, 3});
    students[2] =  new Student(1, 13, new int[]{1, 2, 3});
    students[3] =  new Student(2, 21, new int[]{1, 2, 3});
    students[4] =  new Student(2, 22, new int[]{1, 2});
    students[5] =  new Student(2, 23, new int[]{2, 1, 3});
    students[6] =  new Student(3, 31, new int[]{1, 2, 3});
    students[7] =  new Student(3, 32, new int[]{1, 2, 3});
    students[8] =  new Student(3, 33, new int[]{1, 2, 3});
    students[9] =  new Student(4, 41, new int[]{});
    students[10] =  new Student(4, 42, new int[]{1, 2, 3});
    students[11] =  new Student(4, 43, new int[]{1, 2, 3});
    
    System.out.println("Test case2:");
    long startTime = System.nanoTime();
    Course[] result = simulate(students, courses);
    long estimatedTime = System.nanoTime() - startTime;
    print_course(result);
    print_first_priority(result);
    System.out.println("Performance(time): "+estimatedTime);
  }
  
  // Abstract test case 3. Prepare for hidden test case.
  // It is normal that the function is no code.
  private static void test3() {
  }
  
  // TODO: write your code in this function
  // Simulate courses allocating process
  private static Course[] simulate(Student[] students, Course[] courses) {

//    for(Course course:courses) {
//      System.out.println(course.name+" : ");
////      System.out.println(Arrays.toString(course.candidate));
//
//    }

    // 依照年級進行switch case，大的年級優先

    // 應該先看志願序，如果志願相同，比年級，再比student id
    // 假如年級相同，會先比student id
    for(Student student: students){
      switch (student.year){
        case 1:
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:

          break;
      }
    }

    Student[] assign_students = new Student[3];
    assign_students[0] = students[0];
    assign_students[1] = students[1];

    courses[0].candidate =assign_students;

    System.out.println(students[0].id);


    return courses;
  }
  
  // helper function
  // print result of allocating the student to course
  private static void print_course(Course[] courses) {
    for(Course course:courses) {
        System.out.print(course.name+" : ");
        System.out.println(Arrays.toString(course.candidate));
    }
  }
  
  // helper function
  // Calculate the number of students who meet his first priority.
  private static void print_first_priority(Course[] courses) {
    int count = 0;
    for(Course course:courses) {
        for(Student one:course.candidate) {
            if (one != null &&  one.preference.length>0 && one.preference[0] == course.id) {
                count++;
            }
        }
    }
    System.out.println("Students satisfaction: "+ count);
  }


  public static void main(String[] args) {    
      test1();
//    test1 expected output: 
//    ITR : [大4 學號41, 大4 學號42, 大4 學號43]
//      MIS : [大3 學號31, 大3 學號32, 大3 學號33]
//      DataBase : [大2 學號21, 大2 學號22, 大2 學號23]
//      ResearchMethod : [大1 學號11, 大1 學號12, 大1 學號13]
//      Students satisfaction: 3
//      Performance(time): XXXX
      test2();
//    test2 expected output:
//    ITR : [大4 學號42, 大4 學號43, 大3 學號31, 大3 學號32, 大3 學號33, 大2 學號21]
//      MIS : [大2 學號22, 大2 學號23]
//      DataBase : [大1 學號11, 大1 學號12, 大1 學號13, 大4 學號41]
//      Students satisfaction: 7
//      Performance(time): XXXX
      test3(); // hidden test case 3
  }
}