package com.company;
//TODO: M104020029 邱承漢
//TODO: This assignment aims to write a function simulate to allocate the course process by student��s preference.

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


public class HW2_M104020029 {
    
  private HW2_M104020029() {}
  
  public static class Course {
          int id = 0; // course's id. ITR->1, MIS->2, DataBase->3, ResearchMethod->4
          String name; // course's name
          Student[] candidate; // The course selection result.
          int candidateCnt = 0; //用來紀錄目前課堂的選課人數

          // Course should initial by course id and name.
          private Course(int id, String name, int limit_number) {
              this.id = id;
              this.name = name;
              this.candidate = new Student[limit_number];
          }

      }

      public static class PriorityComparator implements Comparator<Student> { //實做Student的排序規則
          @Override
          public int compare(Student a, Student b) {
              if (a.year != b.year) { //年級較大優先權較高，排到隊伍的前面(ascending)
                  if (a.year > b.year) {
                      return -1;
                  } else if (a.year < b.year) {
                      return 1;
                  }
              } else { //如果年級相等，id小的優先權較高，排到隊伍前面(ascending)
                  if (a.id < b.id) {
                      return -1;
                  } else if (a.id > b.id) {
                      return 1;
                  }
              }
              return 0; //如果完全相等
          }
      }

      public static class Student {
          int year; // Student's year. freshman->1, sophomore->2, junior->3, senior ->4
          int id; // Unique student id
          //A set of student's preferences of courses id. e.g. [4,3,2,1]. The first priority of course is 4, which means ResearchMethod
          int[] preference;
          boolean selected = false; //用來記錄學生是否已經選到課

          // Student should initial by year, id ,and candidate_courses.
          private Student(int year, int id, int[] preference) {
              this.year = year;
              this.preference = preference;
              this.id = id;
          }

          //overriding the toString() method
          @Override
          public String toString() {
              return "大" + this.year + " 學號" + this.id;
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
          students[0] = new Student(1, 11, new int[]{1, 2, 3, 4});
          students[1] = new Student(1, 12, new int[]{1, 2, 3, 4});
          students[2] = new Student(1, 13, new int[]{1, 2, 3, 4});
          students[3] = new Student(2, 21, new int[]{1, 2, 3, 4});
          students[4] = new Student(2, 22, new int[]{1, 2, 3, 4});
          students[5] = new Student(2, 23, new int[]{1, 2, 3, 4});
          students[6] = new Student(3, 31, new int[]{1, 2, 3, 4});
          students[7] = new Student(3, 32, new int[]{1, 2, 3, 4});
          students[8] = new Student(3, 33, new int[]{1, 2, 3, 4});
          students[9] = new Student(4, 41, new int[]{1, 2, 3, 4});
          students[10] = new Student(4, 42, new int[]{1, 2, 3, 4});
          students[11] = new Student(4, 43, new int[]{1, 2, 3, 4});

          System.out.println("Test case1:");
          long startTime = System.nanoTime();
          Course[] result = simulate(students, courses);
          long estimatedTime = System.nanoTime() - startTime;
          print_course(result);
          print_first_priority(result);
          System.out.println("Performance(time): " + estimatedTime);
      }

      // Test case 2
      private static void test2() {
          Course[] courses = new Course[3];
          courses[0] = new Course(1, "ITR", 6);
          courses[1] = new Course(2, "MIS", 2);
          courses[2] = new Course(3, "DataBase", 4);

          Student[] students = new Student[12];
          students[0] = new Student(1, 11, new int[]{1, 2, 3});
          students[1] = new Student(1, 12, new int[]{1, 2, 3});
          students[2] = new Student(1, 13, new int[]{1, 2, 3});
          students[3] = new Student(2, 21, new int[]{1, 2, 3});
          students[4] = new Student(2, 22, new int[]{1, 2});
          students[5] = new Student(2, 23, new int[]{2, 1, 3});
          students[6] = new Student(3, 31, new int[]{1, 2, 3});
          students[7] = new Student(3, 32, new int[]{1, 2, 3});
          students[8] = new Student(3, 33, new int[]{1, 2, 3});
          students[9] = new Student(4, 41, new int[]{});
          students[10] = new Student(4, 42, new int[]{1, 2, 3});
          students[11] = new Student(4, 43, new int[]{1, 2, 3});

          System.out.println("Test case2:");
          long startTime = System.nanoTime();
          Course[] result = simulate(students, courses);
          long estimatedTime = System.nanoTime() - startTime;
          print_course(result);
          print_first_priority(result);
          System.out.println("Performance(time): " + estimatedTime);
      }

      // Abstract test case 3. Prepare for hidden test case.
      // It is normal that the function is no code.
      private static void test3() {

    Course[] courses = new Course[5];
    courses[0] = new Course(1, "ITR", 3);
    courses[1] = new Course(2, "MIS", 3);
    courses[2] = new Course(3, "DataBase", 3);
    courses[3] = new Course(4, "ResearchMethod", 2);
    courses[4] = new Course(5, "ResearchMethod", 2);

    Student[] students = new Student[16];
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
    students[12] =  new Student(3, 34, new int[]{1, 2, 3, 4});
    students[13] =  new Student(4, 44, new int[]{1, 2, 3, 4});
    students[14] =  new Student(4, 45, new int[]{1, 2, 3, 4});
    students[15] =  new Student(4, 46, new int[]{1, 2, 3, 4});


    System.out.println("Test case3:");
    long startTime = System.nanoTime();
    Course[] result = simulate(students, courses);
    long estimatedTime = System.nanoTime() - startTime;
    print_course(result);
    print_first_priority(result);
    System.out.println("Performance(time): "+estimatedTime);

      }

      // TODO: write your code in this function
      // Simulate courses allocating process
      private static Course[] simulate(Student[] students, Course[] courses) {
          LinkedList<Student> orderedQueue = new LinkedList();
//	ArrayList<Student> orderedQueue = new ArrayList();
          for (int i = 0; i < students.length; i++) {
              orderedQueue.add(students[i]);
          }
          orderedQueue.sort(new PriorityComparator()); //使用自定義的排序規根據學生的優先權進行排序
          System.out.println(orderedQueue);
          Queue<Student> not_selected = new LinkedList<>(); //紀錄第一輪未選上課程的學生
          for (Student s : orderedQueue) { //根據優先權，循序讓學生選課
              for (int p : s.preference) { //根據學生的偏好排序進行選課
                  if (courses[p - 1].candidateCnt == courses[p - 1].candidate.length) { //如果課堂已滿，根據學生的下一個偏好選課
                      continue;
                  } else { //新增學生到課堂名單中
                      courses[p - 1].candidate[courses[p - 1].candidateCnt++] = s;
                      s.selected = true;
                      break;
                  }
              }
              if (!s.selected) { //如果學生偏好已用完卻還沒選到課，則加入未選上名單，待所有人選完再進行候補。
                  not_selected.add(s);
              }
          }
          for (Course c : courses) {  //把還沒選到課的學生加到人數還沒滿的課程
              while (c.candidateCnt < c.candidate.length) {
                  c.candidate[c.candidateCnt++] = not_selected.poll();
              }
          }
          return courses;
      }

      // helper function
      // print result of allocating the student to course
      private static void print_course(Course[] courses) {
          for (Course course : courses) {
              System.out.print(course.name + " : ");
              System.out.println(Arrays.toString(course.candidate));
          }
      }

      // helper function
      // Calculate the number of students who meet his first priority.
      private static void print_first_priority(Course[] courses) {
          int count = 0;
          for (Course course : courses) {
              for (Student one : course.candidate) {
                  if (one != null && one.preference.length > 0 && one.preference[0] == course.id) {
                      count++;
                  }
              }
          }
          System.out.println("Students satisfaction: " + count);
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