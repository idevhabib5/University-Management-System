// Create a base class Person with common attributes like name, email, and dateOfBirth. Use 
// inheritance to create Student, Teacher, and AdministrativeStaff classes.

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface Reportable {
    void generateReport();

    void exportToFile();

}

class Repository<T> {
    private ArrayList<T> items;

    public Repository() {
        this.items = new ArrayList<>();
    }

    public void add(T item) {
        items.add(item);
        System.out.println(item.getClass().getSimpleName() + " " + item + " added to the repository.");
    }

    public void remove(T item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println(item.getClass().getSimpleName() + " " + item + " removed from the repository.");
        } else {
            System.out.println(item.getClass().getSimpleName() + " " + item + " not found in the repository.");
        }
    }

    public ArrayList<T> getAll() {
        return new ArrayList<>(items);
    }
}

abstract class person implements Serializable {
    protected String name;
    protected String email;
    protected LocalDate dateOfBirth;
    protected String address;

    public person() {
    }

    public person(String name, String email, LocalDate dateOfBirth, String ad) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = ad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "person [name=" + name + ", email=" + email + ", dateOfBirth=" + dateOfBirth + "]";
    }

}

class Student extends person {
    private String StudentId;
    private ArrayList<Course> courses;
    private double marks;
    private String c1;

    public Student() {
        University.StudentList.add(this);
    }

    public Student(String c) {
        this.c1 = c;

    }

    public Student(String name, String email, LocalDate dateOfBirth, String ad, String studentId, double marks,
            ArrayList<Course> courses) {
        super(name, email, dateOfBirth, ad);
        StudentId = studentId;
        this.courses = courses;
        this.marks = marks;
        University.StudentList.add(this);

    }

    public String getStudentId() {
        return StudentId;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", email=" + email + ", StudentId=" + StudentId + ", marks=" + marks + "]";
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        System.out.println("Student " + this.StudentId + " is successfully enrolled in " + course.getCourseName());
    }

    // Method to display all enrolled courses
    public void displayEnrolledCourses() {
        System.out.println(
                this.getName() + " whose ID number is " + this.StudentId + " is enrolled in the following courses:");
        for (Course course : courses) {
            System.out.println(course.getCourseName() + " with Credit Hours: " + course.getCredits());
        }
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }
}

class Course implements Serializable {
    // Attributes: courseID, title, credits, assignedTeacher, list of enrolled
    // students.
    private String courseName;
    private String courseId;
    private double credits;
    private ArrayList<Student> students;
    private Teacher assignedteacher;

    public Course() {
    }

    public Course(String courseName, String courseId, double credits, ArrayList<Student> students, Teacher teacher) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.credits = credits;
        this.students = students;
        this.assignedteacher = teacher;
        University.CourseList.add(this);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return assignedteacher;
    }

    public void setTeacher(Teacher teacher) {
        this.assignedteacher = teacher;
    }

    public void addStudent(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (students == null) {
            students = new ArrayList<>(); // Initialize the list if it's null
        }

        if (students.contains(s)) {
            throw new IllegalStateException("Student already enrolled in this course: " + s.getStudentId());
        }
        this.students.add(s);

        System.out.println("Student " + s.getStudentId() + " is successfully enrolled in " + courseName);
    }

    public void removeStudent(Student s) {
        if (students.contains(s)) {
            this.students.remove(s);

            System.out.println("Student " + s.getStudentId() + " is successfully removed from " + courseName);
        } else {

            System.out.println("Student with ID " + s.getStudentId() + " not found in the list.");
        }
    }

    public void averageGrade() {
        if (students.isEmpty()) {
            System.out.println("No students enrolled in " + courseName);
            return;
        }
        Double sum = 0.0;
        for (Student student : students) {
            sum += student.getMarks();
        }
        Double average = sum / students.size();

        System.out.println("Average grade for" + courseName + " :" + average);

    }

    public Double calculateMedianGrade() {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students enrolled in " + courseName + ". Cannot calculate median.");
        }

        ArrayList<Double> grades = new ArrayList<>();
        for (Student student : students) {
            grades.add(student.getMarks());
        }

        Collections.sort(grades);

        int size = grades.size();
        if (size % 2 == 1) {
            // Odd number
            return grades.get(size / 2);
        } else {
            // Even number
            return (grades.get(size / 2 - 1) + grades.get(size / 2)) / 2.0;
        }
    }

    @Override
    public String toString() {
        return "Course [courseName=" + courseName + ", courseId=" + courseId + ", credits=" + credits + "]";
    }
}

class Teacher extends person implements Reportable {
    // . Attributes: teacherID, name, specialization, list of courses taught.
    private String teacherId;
    private String teacherName;
    private String specialization;

    private ArrayList<Course> courses;

    public Teacher() {

    }

    public Teacher(String name) {
        this.teacherName = name;

    }

    public Teacher(String name, String email, LocalDate dateOfBirth, String ad, String teacherId,
            String teacherName, String specialization, ArrayList<Course> courses) {
        super(name, email, dateOfBirth, ad);
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.specialization = specialization;
        this.courses = courses;
        University.TeacherList.add(this);

    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void assignCourse(Course c) {
        this.courses.add(c);

        System.out.println("The Course " + c.getCourseName() + " has been assigned to : " + teacherName);
    }

    public void displayCourse() {
        System.out.println("The " + teacherName + " has been assigned to the following courses:");

        // Check if the teacher is assigned to any courses
        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
        } else {
            for (Course course : courses) {

                System.out.println("Course Name: " + course.getCourseName() +
                        " | Course ID: " + course.getCourseId());
            }
        }

    }

    public void exportToFile() {
        University.saveToFile("UniversityData.ser");
    }

    public void generateReport() {

        if (University.StudentList == null || University.CourseList == null) {
            throw new NullPointerException("People or courses list cannot be null");
        }

        System.out.println("Report Summary:");
        System.out.println("***************************");

        System.out.println("Total Students: " + University.totalStudentsEnrolled());
        System.out.println("Total Teachers: " + University.totalTeachers());
        System.out.println("Total Administrative Staff: " + University.StaffList.size());
        System.out.println("Total Courses: " + University.totalCourses());

    }

    public String toString() {
        return "Teacher [name=" + name + ", email=" + email + ", teacherId=" + teacherId + ", teacherName="
                + teacherName + ", specialization=" + specialization + "]";
    }

}

class AdministrativeStaff extends person implements Reportable {
    // Attributes: staffID, name, role, and department.
    private String staffID;
    private String role;
    private String department;

    public AdministrativeStaff(String name, String email, LocalDate dateOfBirth, String ad, String staffID, String role,
            String department) {
        super(name, email, dateOfBirth, ad);
        this.staffID = staffID;
        this.role = role;
        this.department = department;
        University.StaffList.add(this);
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void exportToFile() {
        University.saveToFile("UniversityData.ser");
    }

    public void generateReport() {

        if (University.StudentList == null || University.CourseList == null) {
            throw new NullPointerException("People or courses list cannot be null");
        }

        System.out.println("Report Summary:");
        System.out.println("***************************");

        System.out.println("Total Students: " + University.totalStudentsEnrolled());
        System.out.println("Total Teachers: " + University.totalTeachers());
        System.out.println("Total Administrative Staff: " + University.StaffList.size());
        System.out.println("Total Courses: " + University.totalCourses());

        System.out.println("\nCourse Details:");
        for (Course course : University.CourseList) {
            System.out.println("Course Name: " + course.getCourseName() +
                    ", Total Students Who Enrolled in this Course: " + course.getStudents().size() +
                    ", Teacher Who is Teaching  this Course: " + course.getTeacher().getName() +
                    ", Course ID: " + course.getCourseId() +
                    ", Credits: " + course.getCredits());
        }
    }

    public String toString() {
        return "AdministrativeStaff [name=" + name + ", email=" + email + ", staffID=" + staffID + ", role=" + role
                + ", department=" + department + "]";
    }
}

public class semproject1 {
    public static void main(String[] args) {

        try {
            // Create a new OOP course
            Teacher oopTeacher = new Teacher("John Smith", "john.smith@university.edu",
                    LocalDate.of(1980, 1, 1), "123 Faculty St", "T001",
                    "Dr. Smith", "Computer Science", new ArrayList<>());

            ArrayList<Student> students = new ArrayList<>();
            Course oopCourse = new Course("OOP", "CS101", 3.0, students, oopTeacher);

            // Create and add students
            Student student1 = new Student("Alice Johnson", "alice@university.edu",
                    LocalDate.of(2000, 3, 15), "456 Student Ave", "S001", 85.5, new ArrayList<>());

            Student student2 = new Student("Bob Wilson", "bob@university.edu",
                    LocalDate.of(2001, 5, 20), "789 College Rd", "S002", 92.0, new ArrayList<>());

            Student student3 = new Student("Carol Martinez", "carol@university.edu",
                    LocalDate.of(2000, 8, 10), "321 Campus Dr", "S003", 88.5, new ArrayList<>());

            Student student4 = new Student("David Lee", "david@university.edu",
                    LocalDate.of(2001, 2, 25), "654 University Blvd", "S004", 90.0, new ArrayList<>());

            Student student5 = new Student("Eva Brown", "eva@university.edu",
                    LocalDate.of(2000, 11, 5), "987 Scholar St", "S005", 87.5, new ArrayList<>());

            // Add students to the course
            oopCourse.addStudent(student1);
            oopCourse.addStudent(student2);
            oopCourse.addStudent(student3);
            oopCourse.addStudent(student4);
            oopCourse.addStudent(student5);

            // Create administrative staff to generate report
            AdministrativeStaff admin = new AdministrativeStaff(
                    "Admin User",
                    "admin@university.edu",
                    LocalDate.of(1975, 1, 1),
                    "Admin Building",
                    "A001",
                    "Registrar",
                    "Student Affairs");

            // Generate report
            System.out.println("\nGenerating Administrative Report:");
            System.out.println("================================");
            admin.generateReport();

            // Calculate and display course statistics
            System.out.println("\nOOP Course Statistics:");
            System.out.println("=====================");
            oopCourse.averageGrade();
            System.out.println("Median Grade: " + oopCourse.calculateMedianGrade());

            // Save data
            University.saveToFile("UniversityData.ser");

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        new crud();
    }

}
