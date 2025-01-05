import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class University implements Reportable, Serializable {
    private String campusName;
    public static ArrayList<Student> StudentList;
    public static ArrayList<Teacher> TeacherList;
    public static ArrayList<AdministrativeStaff> StaffList;
    public static ArrayList<Course> CourseList;
    public static ArrayList<Object> allObjectsList = new ArrayList<>();

    public University(String campusName, ArrayList<Student> studentList, ArrayList<Teacher> teacherList,
            ArrayList<AdministrativeStaff> staffList, ArrayList<Course> courseList) {
        this.campusName = campusName;
        StudentList = studentList;
        TeacherList = teacherList;
        StaffList = staffList;
        CourseList = courseList;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public static ArrayList<Student> getStudentList() {
        return StudentList;
    }

    public static void setStudentList(ArrayList<Student> studentList) {
        StudentList = studentList;
    }

    public static ArrayList<Teacher> getTeacherList() {
        return TeacherList;
    }

    public static void setTeacherList(ArrayList<Teacher> teacherList) {
        TeacherList = teacherList;
    }

    public ArrayList<AdministrativeStaff> getStaffList() {
        return StaffList;
    }

    public void setStaffList(ArrayList<AdministrativeStaff> staffList) {
        StaffList = staffList;
    }

    public static ArrayList<Course> getCourseList() {
        return CourseList;
    }

    public static void setCourseList(ArrayList<Course> courseList) {
        CourseList = courseList;
    }
    // file input out put wala mehtod

    public static void saveToFile(String filename) {
        try {
            boolean append = new File(filename).exists();
            FileOutputStream fileOut = new FileOutputStream(filename, true);
            ObjectOutputStream out;

            if (append) {
                // Use a custom ObjectOutputStream that doesn't write the header
                out = new ObjectOutputStream(fileOut) {
                    @Override
                    protected void writeStreamHeader() throws IOException {
                        reset(); // Reset the stream instead of writing a header
                    }
                };
            } else {
                // Write a regular header for a new file
                out = new ObjectOutputStream(fileOut);
            }

            // Save the last element of each list if the list is not empty
            if (!StudentList.isEmpty()) {
                out.writeObject(StudentList.get(StudentList.size() - 1));
            }
            if (!TeacherList.isEmpty()) {
                out.writeObject(TeacherList.get(TeacherList.size() - 1));
            }
            if (!StaffList.isEmpty()) {
                out.writeObject(StaffList.get(StaffList.size() - 1));
            }
            if (!CourseList.isEmpty()) {
                out.writeObject(CourseList.get(CourseList.size() - 1));
            }

            out.close();
            fileOut.close();
            System.out.println("Last elements of the lists have been saved to " + filename);

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static void saveOneObject(String filename, Object object) {
        try {
            // Check if the file exists to decide whether to append
            boolean append = new File(filename).exists();
    
            FileOutputStream fileOut = new FileOutputStream(filename, true);
            ObjectOutputStream out;
    
            if (append) {
                // Custom ObjectOutputStream to suppress the header
                out = new ObjectOutputStream(fileOut) {
                    @Override
                    protected void writeStreamHeader() throws IOException {
                        reset(); // Avoid writing a new header
                    }
                };
            } else {
                // Write the header for a new file
                out = new ObjectOutputStream(fileOut);
            }
    
            out.writeObject(object);
    
            out.close();
            fileOut.close();
            System.out.println("Object has been saved to " + filename);
    
        } catch (IOException e) {
            System.out.println("Error saving object: " + e.getMessage());
        }
    }
    

    public void exportToFile() {
        University.saveToFile("UniversityData.ser");
    }

    public static List<Object> loadData(String filename) {
        List<Object> loadedObjects = new ArrayList<>();
    
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
    
            while (true) {
                try {
                    // Read an object and add it to the list
                    Object obj = in.readObject();
                    loadedObjects.add(obj);
    
                } catch (EOFException e) {
                    // End of file reached; exit the loop
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found: " + e.getMessage());
                    break;
                }
            }
            System.out.println("Data has been loaded from " + filename);
    
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    
        return loadedObjects;
    }
    
    
    

    // Helper method to find a Course by its name
    private static Course findCourse(String courseName) {
        for (Course course : CourseList) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null; // Return null if not found
    }

    // Helper method to find a Teacher by name
    private static Teacher findTeacher(String teacherName) {
        for (Teacher teacher : TeacherList) {
            if (teacher.getName().equals(teacherName)) {
                return teacher;
            }
        }
        return null; // Return null if not found
    }

    // Other methods remain unchanged...

    public static void addStudent(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (StudentList.contains(s)) {
            throw new IllegalStateException("Student already enrolled in this course: " + s.getStudentId());
        }

        StudentList.add(s);

        System.out.println("Student " + s.getStudentId() + " is successfully enrolled in University ");
    }

    public static void removeStudent(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (!StudentList.contains(s)) {
            throw new IllegalStateException("Student does not Exist");
        }

        StudentList.remove(s);

        System.out.println("Student " + s.getStudentId() + " is successfully removed in University ");
    }

    public static int totalStudentsEnrolled() {
        return StudentList.size();
    }

    public static int totalTeachers() {
        return TeacherList.size();
    }

    public static int totalCourses() {
        return CourseList.size();
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
}