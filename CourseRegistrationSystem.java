import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class to represent a course
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean enrollStudent() {
        if (enrolled < capacity) {
            enrolled++;
            if (capacity - enrolled <= 3) {
                System.out.println("Warning: The course is nearly full. Only " + (capacity - enrolled) + " slots left.");
            }
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + ", Title: " + title + ", Description: " + description +
                ", Capacity: " + capacity + ", Enrolled: " + enrolled + ", Schedule: " + schedule;
    }
}

// Class to represent a student
class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course.enrollStudent()) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            return true;
        }
        return false;
    }
}

// Main class to handle the course registration system
public class CourseRegistrationSystem {
    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample data
        courses.add(new Course("CS101", "Introduction to Computer Science", "Basics of computer science", 30, "Mon-Wed-Fri 10:00-11:00"));
        courses.add(new Course("MA101", "Calculus I", "Introduction to calculus", 25, "Tue-Thu 09:00-10:30"));
        students.add(new Student("S001", "Alice"));
        students.add(new Student("S002", "Bob"));

        while (true) {
            System.out.println("\nCourse Registration System");
            System.out.println("1. List Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. List Registered Courses");
            System.out.println("5. Search Course by Title");
            System.out.println("6. Search Student by Name");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            
            int choice = getValidatedChoice();
            
            switch (choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    listRegisteredCourses();
                    break;
                case 5:
                    searchCourseByTitle();
                    break;
                case 6:
                    searchStudentByName();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getValidatedChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void listCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void registerCourse() {
        System.out.print("Enter student ID: ");
        String studentID = scanner.next();
        Student student = findStudent(studentID);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.next();
        Course course = findCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.registerCourse(course)) {
            System.out.println("Course registered successfully.");
        } else {
            System.out.println("Failed to register course. It might be full.");
        }
    }

    private static void dropCourse() {
        System.out.print("Enter student ID: ");
        String studentID = scanner.next();
        Student student = findStudent(studentID);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.next();
        Course course = findCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("Course dropped successfully.");
        } else {
            System.out.println("Failed to drop course. You might not be registered in it.");
        }
    }

    private static void listRegisteredCourses() {
        System.out.print("Enter student ID: ");
        String studentID = scanner.next();
        Student student = findStudent(studentID);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Registered Courses for " + student.getName() + ":");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course);
        }
    }

    private static void searchCourseByTitle() {
        System.out.print("Enter course title to search: ");
        String title = scanner.next();
        boolean found = false;

        for (Course course : courses) {
            if (course.getTitle().equalsIgnoreCase(title)) {
                System.out.println(course);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No course found with the title \"" + title + "\".");
        }
    }

    private static void searchStudentByName() {
        System.out.print("Enter student name to search: ");
        String name = scanner.next();
        boolean found = false;

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println("Student ID: " + student.getStudentID() + ", Name: " + student.getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found with the name \"" + name + "\".");
        }
    }

    private static Student findStudent(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
