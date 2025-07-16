import java.util.Scanner;
import java.util.ArrayList;

class Student{
    String name;
   
    int grade;
    Student(String name,int grade){
        this.name=name;
        this.grade=grade;
    }
}

public class StudentGradeTracker {
    public static void main(String[] args) throws Exception {
       ArrayList <Student> students=new ArrayList<>();
       Scanner sc=new Scanner(System.in);

       System.out.println("***   Student Grade Tracker   ***");
       System.out.println("Please enter the number of students :");

       int numstds=sc.nextInt();
      sc.nextLine();

       for(int i = 1 ; i <= numstds ; i++){
        System.out.println("Please enter the name of the student "+ i+":");
        String name=sc.nextLine();
        

        int grade;
        while (true){

            System.out.println("Please enter the grade for "+name+" "+" :");
            grade=sc.nextInt();
            sc.nextLine();

            if (grade >= 0 && grade <= 20) break;
            System.out.println("Please entrer a valid grade between 0 and 20");
        }
        students.add(new Student(name, grade));
       }

       int totalgrades=0;
       Student highest=students.get(0);
       Student lowest=students.get(0);

       for(Student s :students){
        totalgrades+=s.grade;
        if (s.grade>highest.grade){highest=s;}
        if (s.grade<lowest.grade){lowest=s;}
       }
       double average=(double) totalgrades/students.size();

       System.out.println("====    Grade Summary    ====");
       System.out.println("Total Students : "+numstds);
       System.out.println("Average Grade : "+average);
       System.out.println("Highest Grade : "+highest.grade +" (Student : "+highest.name +" )");
       System.out.println("Lowest Grade : "+lowest.grade +" (Student : "+lowest.name +" )");

       sc.close();
    }
    
}
