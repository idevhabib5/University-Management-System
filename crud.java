import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class crud {
    private JPanel dynamicPanel;

    @SuppressWarnings("unused")
    public crud() {
        JFrame frame = new JFrame("CRUD Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();

        JMenu studentMenu = new JMenu("Student");
        JMenuItem addStudentMenuItem = new JMenuItem("Add Student");
        JMenuItem viewStudentMenuItem = new JMenuItem("View Student by ID");
        JMenuItem updateStudentMenuItem = new JMenuItem("Update Student by ID");
        JMenuItem deleteStudentMenuItem = new JMenuItem("Delete Student by ID");
        studentMenu.add(addStudentMenuItem);
        studentMenu.add(viewStudentMenuItem);
        studentMenu.add(updateStudentMenuItem);
        studentMenu.add(deleteStudentMenuItem);

        JMenu adminMenu = new JMenu("Administrative Staff");
        JMenuItem addAdminMenuItem = new JMenuItem("Add Staff");
        JMenuItem viewAdminMenuItem = new JMenuItem("View Staff by ID");
        JMenuItem updateAdminMenuItem = new JMenuItem("Update Staff by ID");
        JMenuItem deleteAdminMenuItem = new JMenuItem("Delete Staff by ID");
        adminMenu.add(addAdminMenuItem);
        adminMenu.add(viewAdminMenuItem);
        adminMenu.add(updateAdminMenuItem);
        adminMenu.add(deleteAdminMenuItem);

        JMenu reportMenu = new JMenu("Reports");
        JMenuItem generateReportMenuItem = new JMenuItem("Generate University Report");
        reportMenu.add(generateReportMenuItem);

        menuBar.add(adminMenu);
        menuBar.add(reportMenu);

        JMenu teacherMenu = new JMenu("Teacher");
        JMenuItem addTeacherMenuItem = new JMenuItem("Add Teacher");
        JMenuItem viewTeacherMenuItem = new JMenuItem("View Teacher by ID");
        JMenuItem updateTeacherMenuItem = new JMenuItem("Update Teacher by ID");
        JMenuItem deleteTeacherMenuItem = new JMenuItem("Delete Teacher by ID");
        teacherMenu.add(addTeacherMenuItem);
        teacherMenu.add(viewTeacherMenuItem);
        teacherMenu.add(updateTeacherMenuItem);
        teacherMenu.add(deleteTeacherMenuItem);

        JMenu courseMenu = new JMenu("Course");
        JMenuItem addCourseMenuItem = new JMenuItem("Add Course");
        JMenuItem viewCourseMenuItem = new JMenuItem("View Course by ID");
        JMenuItem updateCourseMenuItem = new JMenuItem("Update Course by ID");
        JMenuItem deleteCourseMenuItem = new JMenuItem("Delete Course by ID");
        courseMenu.add(addCourseMenuItem);
        courseMenu.add(viewCourseMenuItem);
        courseMenu.add(updateCourseMenuItem);
        courseMenu.add(deleteCourseMenuItem);

        menuBar.add(studentMenu);
        menuBar.add(teacherMenu);
        menuBar.add(courseMenu);

        frame.setJMenuBar(menuBar);

        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.Y_AXIS));
        frame.add(dynamicPanel, BorderLayout.CENTER);

        // Student menu actions
        addStudentMenuItem.addActionListener(e -> showAddStudentForm());
        viewStudentMenuItem.addActionListener(e -> handleViewStudent());
        updateStudentMenuItem.addActionListener(e -> handleUpdateStudent());

        deleteStudentMenuItem.addActionListener(e -> handleDeleteStudent());

        // Teacher menu actions
        addTeacherMenuItem.addActionListener(e -> showAddTeacherForm());
        viewTeacherMenuItem.addActionListener(e -> handleViewTeacher());
        updateTeacherMenuItem.addActionListener(e -> handleUpdateTeacher());
        deleteTeacherMenuItem.addActionListener(e -> handleDeleteTeacher());

        // Course menu actions
        addCourseMenuItem.addActionListener(e -> handleAddCourse());
        viewCourseMenuItem.addActionListener(e -> handleViewCourse());
        updateCourseMenuItem.addActionListener(e -> handleUpdateCourse());
        deleteCourseMenuItem.addActionListener(e -> handleDeleteCourse());

        addAdminMenuItem.addActionListener(e -> showAddAdminForm());
        viewAdminMenuItem.addActionListener(e -> handleViewAdmin());
        updateAdminMenuItem.addActionListener(e -> handleUpdateAdmin());
        deleteAdminMenuItem.addActionListener(e -> handleDeleteAdmin());

        // Add action listener for Report Generation
        generateReportMenuItem.addActionListener(e -> handleGenerateReport());

        frame.setVisible(true);
    }

    private void showAddAdminForm() {
        dynamicPanel.removeAll();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameInput = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailInput = new JTextField(20);
        JLabel staffIdLabel = new JLabel("Staff ID:");
        JTextField staffIdInput = new JTextField(20);
        JLabel dobLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
        JTextField dobInput = new JTextField(20);
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressInput = new JTextField(20);
        JLabel roleLabel = new JLabel("Role:");
        JTextField roleInput = new JTextField(20);
        JLabel deptLabel = new JLabel("Department:");
        JTextField deptInput = new JTextField(20);
        JButton saveButton = new JButton("Save");

        dynamicPanel.add(nameLabel);
        dynamicPanel.add(nameInput);
        dynamicPanel.add(emailLabel);
        dynamicPanel.add(emailInput);
        dynamicPanel.add(staffIdLabel);
        dynamicPanel.add(staffIdInput);
        dynamicPanel.add(dobLabel);
        dynamicPanel.add(dobInput);
        dynamicPanel.add(addressLabel);
        dynamicPanel.add(addressInput);
        dynamicPanel.add(roleLabel);
        dynamicPanel.add(roleInput);
        dynamicPanel.add(deptLabel);
        dynamicPanel.add(deptInput);
        dynamicPanel.add(saveButton);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();

        saveButton.addActionListener(e -> {
            try {
                String name = nameInput.getText();
                String email = emailInput.getText();
                String staffId = staffIdInput.getText();
                LocalDate dob = LocalDate.parse(dobInput.getText());
                String address = addressInput.getText();
                String role = roleInput.getText();
                String department = deptInput.getText();

                AdministrativeStaff staff = new AdministrativeStaff(name, email, dob, address, staffId, role,
                        department);

                University.saveOneObject("UniversityData.ser", staff);

                JOptionPane.showMessageDialog(null, "Staff member added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear inputs
                nameInput.setText("");
                emailInput.setText("");
                staffIdInput.setText("");
                dobInput.setText("");
                addressInput.setText("");
                roleInput.setText("");
                deptInput.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void handleDeleteAdmin() {
        String staffId = JOptionPane.showInputDialog("Enter Staff ID to delete:");
        if (staffId != null && !staffId.trim().isEmpty()) {
            ArrayList<AdministrativeStaff> staffList = University.StaffList;
            boolean found = false;

            for (int i = 0; i < staffList.size(); i++) {
                if (staffList.get(i).getStaffID().equals(staffId)) {
                    staffList.remove(i);
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(null, "Staff member deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Staff member not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Report Generation
    private void handleGenerateReport() {
        try {
            dynamicPanel.removeAll();

            // Create text area for report
            JTextArea reportArea = new JTextArea(20, 40);
            reportArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(reportArea);

            // Generate report content
            StringBuilder report = new StringBuilder();
            report.append("University Report\n");
            report.append("================\n\n");
            report.append("Total Students: ").append(University.totalStudentsEnrolled()).append("\n");
            report.append("Total Teachers: ").append(University.totalTeachers()).append("\n");
            report.append("Total Administrative Staff: ").append(University.StaffList.size()).append("\n");
            report.append("Total Courses: ").append(University.totalCourses()).append("\n\n");

            // Course details
            report.append("Course Details:\n");
            report.append("==============\n");
            for (Course course : University.CourseList) {
                report.append("Course: ").append(course.getCourseName())
                        .append(" (ID: ").append(course.getCourseId()).append(")\n")
                        .append("Credits: ").append(course.getCredits()).append("\n")
                        .append("Enrolled Students: ").append(course.getStudents().size()).append("\n\n");
            }

            // Student details
            report.append("Student List:\n");
            report.append("=============\n");
            for (Student student : University.StudentList) {
                report.append("Student Name: ").append(student.getName())
                        .append(" (ID: ").append(student.getStudentId()).append(")\n")
                        .append("Total Marks: ").append(student.getMarks()).append("\n\n");
            }

            // Teacher details
            report.append("Teacher List:\n");
            report.append("=============\n");
            for (Teacher teacher : University.TeacherList) {
                report.append("Teacher Name: ").append(teacher.getName())
                        .append(" (ID: ").append(teacher.getTeacherId()).append(")\n")
                        .append("Specialization: ").append(teacher.getSpecialization()).append("\n\n");
            }

            // Administrative Staff details
            report.append("Administrative Staff List:\n");
            report.append("==========================\n");
            for (AdministrativeStaff staff : University.StaffList) {
                report.append("Staff Name: ").append(staff.getName())
                        .append(" (ID: ").append(staff.getStaffID()).append(")\n")
                        .append("Role: ").append(staff.getRole()).append("\n")
                        .append("Department: ").append(staff.getDepartment()).append("\n\n");
            }

            reportArea.setText(report.toString());
            dynamicPanel.add(scrollPane);

            // Add export button
            JButton exportButton = new JButton("Export Report");
            exportButton.addActionListener(e -> University.saveToFile(report.toString()));
            dynamicPanel.add(exportButton);

            dynamicPanel.revalidate();
            dynamicPanel.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generating report: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleViewAdmin() {
        String staffId = JOptionPane.showInputDialog("Enter Staff ID to view:");
        if (staffId != null && !staffId.trim().isEmpty()) {
            ArrayList<AdministrativeStaff> staffList = University.StaffList;
            for (AdministrativeStaff staff : staffList) {
                if (staff.getStaffID().equals(staffId)) {
                    JOptionPane.showMessageDialog(null, staff.toString(), "Staff Details",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Staff member not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateAdmin() {
        String staffId = JOptionPane.showInputDialog("Enter Staff ID to update:");
        if (staffId != null && !staffId.trim().isEmpty()) {
            for (AdministrativeStaff staff : University.StaffList) {
                if (staff.getStaffID().equals(staffId)) {
                    showAdminUpdateForm(staff);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Staff member not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAdminUpdateForm(AdministrativeStaff staff) {
        dynamicPanel.removeAll();
    }

    // Course methods
    private void handleAddCourse() {
        dynamicPanel.removeAll();

        JLabel courseNameLabel = new JLabel("Course Name:");
        JTextField courseNameInput = new JTextField(20);
        JLabel courseIdLabel = new JLabel("Course ID:");
        JTextField courseIdInput = new JTextField(20);
        JLabel courseCreditsLabel = new JLabel("Course Credits:");
        JTextField courseCreditsInput = new JTextField(20);
        JButton saveButton = new JButton("Save");

        dynamicPanel.add(courseNameLabel);
        dynamicPanel.add(courseNameInput);
        dynamicPanel.add(courseIdLabel);
        dynamicPanel.add(courseIdInput);
        dynamicPanel.add(courseCreditsLabel);
        dynamicPanel.add(courseCreditsInput);
        dynamicPanel.add(saveButton);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();

        saveButton.addActionListener(e -> {
            try {
                String courseName = courseNameInput.getText();
                String courseId = courseIdInput.getText();
                double courseCredits = Double.parseDouble(courseCreditsInput.getText());

                if (courseName.isEmpty() || courseId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Course course = new Course(courseName, courseId, courseCredits, new ArrayList<>(), null);
                // Add to University's course list
                // University.CourseList.add(course);
                // Save all university data
                University.saveOneObject("UniversityData.ser", course);

                JOptionPane.showMessageDialog(null, "Course added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                courseNameInput.setText("");
                courseIdInput.setText("");
                courseCreditsInput.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void handleViewCourse() {
        String courseId = JOptionPane.showInputDialog("Enter Course ID to view:");
        if (courseId != null && !courseId.trim().isEmpty()) {
            ArrayList<Course> courseList = University.getCourseList();
            for (Course course : courseList) {
                if (course.getCourseId().equals(courseId)) {
                    JOptionPane.showMessageDialog(null, course.toString(), "Course Details",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid Course ID.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleUpdateCourse() {
        String courseId = JOptionPane.showInputDialog("Enter Course ID to update:");
        if (courseId != null && !courseId.trim().isEmpty()) {
            ArrayList<Course> courseList = University.getCourseList();
            for (Course course : courseList) {
                if (course.getCourseId().equals(courseId)) {
                    // Show the course update form
                    dynamicPanel.removeAll();

                    JLabel courseNameLabel = new JLabel("Course Name:");
                    JTextField courseNameInput = new JTextField(course.getCourseName(), 20);
                    JLabel courseIdLabel = new JLabel("Course ID:");
                    JTextField courseIdInput = new JTextField(course.getCourseId(), 20);
                    JLabel courseCreditsLabel = new JLabel("Course Credits:");
                    JTextField courseCreditsInput = new JTextField(String.valueOf(course.getCredits()), 20);
                    JButton updateButton = new JButton("Update");

                    dynamicPanel.add(courseNameLabel);
                    dynamicPanel.add(courseNameInput);
                    dynamicPanel.add(courseIdLabel);
                    dynamicPanel.add(courseIdInput);
                    dynamicPanel.add(courseCreditsLabel);
                    dynamicPanel.add(courseCreditsInput);
                    dynamicPanel.add(updateButton);

                    dynamicPanel.revalidate();
                    dynamicPanel.repaint();

                    updateButton.addActionListener(updateEvent -> {
                        try {
                            course.setCourseName(courseNameInput.getText());
                            course.setCourseId(courseIdInput.getText());
                            course.setCredits(Integer.parseInt(courseCreditsInput.getText()));

                            JOptionPane.showMessageDialog(null, "Course updated successfully!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid Course ID.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleDeleteCourse() {
        String courseId = JOptionPane.showInputDialog("Enter Course ID to delete:");
        if (courseId != null && !courseId.trim().isEmpty()) {
            ArrayList<Course> courseList = University.getCourseList();
            boolean found = false;

            // Loop through the course list to find and delete the course
            for (int i = 0; i < courseList.size(); i++) {
                if (courseList.get(i).getCourseId().equals(courseId)) {
                    courseList.remove(i);
                    found = true;
                    break;
                }
            }

            // Show appropriate message based on whether the course was found and deleted
            if (found) {
                // Update the course list in University
                University.setCourseList(courseList);

                JOptionPane.showMessageDialog(null, "Course deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Course ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid Course ID.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Teacher methods
    private void showAddTeacherForm() {
        showFormForTeacher("Teacher", "Name", "Email", "Teacher ID",
                "Date of Birth (YYYY-MM-DD)", "Address", "Specialization");
    }

    private void showFormForTeacher(String entity, String field1Label, String field2Label, String field3Label,
            String field4Label, String field5Label, String field6Label) {
        dynamicPanel.removeAll();

        // Input fields
        JLabel field1 = new JLabel(field1Label + ":");
        JTextField field1Input = new JTextField(20);
        JLabel field2 = new JLabel(field2Label + ":");
        JTextField field2Input = new JTextField(20);
        JLabel field3 = new JLabel(field3Label + ":");
        JTextField field3Input = new JTextField(20);
        JLabel field4 = new JLabel(field4Label + ":");
        JTextField field4Input = new JTextField(20);
        JLabel field5 = new JLabel(field5Label + ":");
        JTextField field5Input = new JTextField(20);
        JLabel field6 = new JLabel(field6Label + ":");
        JTextField field6Input = new JTextField(20);
        JButton saveButton = new JButton("Save");

        // Add inputs to the panel
        dynamicPanel.add(field1);
        dynamicPanel.add(field1Input);
        dynamicPanel.add(field2);
        dynamicPanel.add(field2Input);
        dynamicPanel.add(field3);
        dynamicPanel.add(field3Input);
        dynamicPanel.add(field4);
        dynamicPanel.add(field4Input);
        dynamicPanel.add(field5);
        dynamicPanel.add(field5Input);
        dynamicPanel.add(field6);
        dynamicPanel.add(field6Input);
        dynamicPanel.add(saveButton);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();

        saveButton.addActionListener(e -> {
            try {
                String name = field1Input.getText();
                String email = field2Input.getText();
                String teacherId = field3Input.getText();
                LocalDate dateOfBirth = LocalDate.parse(field4Input.getText());
                String address = field5Input.getText();
                String specialization = field6Input.getText();

                if (name.isEmpty() || email.isEmpty() || teacherId.isEmpty() || address.isEmpty()
                        || specialization.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<Course> courses = new ArrayList<>();
                Teacher teacher = new Teacher(name, email, dateOfBirth, address, teacherId, name, specialization,
                        courses);
                // Add to University's teacher list
                // Save all university data
                University.saveOneObject("UniversityData.ser", teacher);

                JOptionPane.showMessageDialog(null, "Teacher added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear input fields
                field1Input.setText("");
                field2Input.setText("");
                field3Input.setText("");
                field4Input.setText("");
                field5Input.setText("");
                field6Input.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void handleViewTeacher() {
        String id = JOptionPane.showInputDialog("Enter Teacher ID to view:");
        if (id != null && !id.trim().isEmpty()) {
            ArrayList<Teacher> teachers = University.getTeacherList();
            for (Teacher teacher : teachers) {
                if (teacher.getTeacherId().equals(id)) {
                    JOptionPane.showMessageDialog(null, teacher.toString(), "Teacher Details",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Teacher not found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleUpdateTeacher() {
        String id = JOptionPane.showInputDialog("Enter Teacher ID to update:");
        if (id != null && !id.trim().isEmpty()) {
            ArrayList<Teacher> teachers = University.getTeacherList();
            for (Teacher teacher : teachers) {
                if (teacher.getTeacherId().equals(id)) {
                    // Show the teacher update form
                    dynamicPanel.removeAll();

                    JLabel nameLabel = new JLabel("Name:");
                    JTextField nameInput = new JTextField(teacher.getName(), 20);
                    JLabel emailLabel = new JLabel("Email:");
                    JTextField emailInput = new JTextField(teacher.getEmail(), 20);
                    JLabel teacherIdLabel = new JLabel("Teacher ID:");
                    JTextField teacherIdInput = new JTextField(teacher.getTeacherId(), 20);
                    JLabel addressLabel = new JLabel("Address:");
                    JTextField addressInput = new JTextField(teacher.getAddress(), 20);
                    JLabel specializationLabel = new JLabel("Specialization:");
                    JTextField specializationInput = new JTextField(teacher.getSpecialization(), 20);
                    JButton updateButton = new JButton("Update");

                    dynamicPanel.add(nameLabel);
                    dynamicPanel.add(nameInput);
                    dynamicPanel.add(emailLabel);
                    dynamicPanel.add(emailInput);
                    dynamicPanel.add(teacherIdLabel);
                    dynamicPanel.add(teacherIdInput);
                    dynamicPanel.add(addressLabel);
                    dynamicPanel.add(addressInput);
                    dynamicPanel.add(specializationLabel);
                    dynamicPanel.add(specializationInput);
                    dynamicPanel.add(updateButton);

                    dynamicPanel.revalidate();
                    dynamicPanel.repaint();

                    // Add action listener for the update button
                    updateButton.addActionListener(updateEvent -> {
                        try {
                            teacher.setName(nameInput.getText());
                            teacher.setEmail(emailInput.getText());
                            teacher.setTeacherId(teacherIdInput.getText());
                            teacher.setAddress(addressInput.getText());
                            teacher.setSpecialization(specializationInput.getText());

                            JOptionPane.showMessageDialog(null, "Teacher updated successfully!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // After update, make sure to save the updated list (if necessary)
                            University.setTeacherList(teachers); // If needed

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Teacher not found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid Teacher ID.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleDeleteTeacher() {
        String id = JOptionPane.showInputDialog("Enter Teacher ID to delete:");
        if (id != null && !id.trim().isEmpty()) {
            ArrayList<Teacher> teachers = University.getTeacherList();
            boolean teacherFound = false;

            // Remove the teacher from the list
            for (int i = 0; i < teachers.size(); i++) {
                if (teachers.get(i).getTeacherId().equals(id)) {
                    teachers.remove(i);
                    teacherFound = true;
                    break;
                }
            }

            if (teacherFound) {
                // Update the list in University class
                // You might not need to set it again; just modify the existing list
                University.setTeacherList(teachers); // You can directly modify the static list

                // Rewrite the updated list to the file

                JOptionPane.showMessageDialog(null, "Teacher deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Teacher ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showAddStudentForm() {
        showForm("Student", "Name", "Email", "Student ID", "Date of Birth (YYYY-MM-DD)", "Address", "Marks");
    }

    private void handleViewStudent() {
        String id = JOptionPane.showInputDialog("Enter Student ID to view:");
        if (id != null && !id.trim().isEmpty()) {
            ArrayList<Student> students = University.getStudentList();
            for (Student student : students) {
                if (student.getStudentId().equals(id)) {
                    JOptionPane.showMessageDialog(null, student.toString(), "Student Details",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleUpdateStudent() {
        String id = JOptionPane.showInputDialog("Enter Student ID to update:");
        if (id != null && !id.trim().isEmpty()) {
            ArrayList<Student> students = University.getStudentList();
            for (Student student : students) {
                if (student.getStudentId().equals(id)) {
                    // Show the student update form
                    dynamicPanel.removeAll();

                    JLabel nameLabel = new JLabel("Name:");
                    JTextField nameInput = new JTextField(student.getName(), 20);
                    JLabel emailLabel = new JLabel("Email:");
                    JTextField emailInput = new JTextField(student.getEmail(), 20);
                    JLabel studentIdLabel = new JLabel("Student ID:");
                    JTextField studentIdInput = new JTextField(student.getStudentId(), 20);
                    JLabel addressLabel = new JLabel("Address:");
                    JTextField addressInput = new JTextField(student.getAddress(), 20);
                    JLabel marksLabel = new JLabel("Marks:");
                    JTextField marksInput = new JTextField(String.valueOf(student.getMarks()), 20);
                    JButton updateButton = new JButton("Update");

                    dynamicPanel.add(nameLabel);
                    dynamicPanel.add(nameInput);
                    dynamicPanel.add(emailLabel);
                    dynamicPanel.add(emailInput);
                    dynamicPanel.add(studentIdLabel);
                    dynamicPanel.add(studentIdInput);
                    dynamicPanel.add(addressLabel);
                    dynamicPanel.add(addressInput);
                    dynamicPanel.add(marksLabel);
                    dynamicPanel.add(marksInput);
                    dynamicPanel.add(updateButton);

                    dynamicPanel.revalidate();
                    dynamicPanel.repaint();

                    // Add action listener for the update button
                    updateButton.addActionListener(updateEvent -> {
                        try {
                            student.setName(nameInput.getText());
                            student.setEmail(emailInput.getText());
                            student.setStudentId(studentIdInput.getText());
                            student.setAddress(addressInput.getText());
                            student.setMarks(Double.parseDouble(marksInput.getText()));

                            JOptionPane.showMessageDialog(null, "Student updated successfully!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // After update, save the updated student list (if needed)
                            University.setStudentList(students); // If needed

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid Student ID.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleDeleteStudent() {
        String id = JOptionPane.showInputDialog("Enter Student ID to delete:");
        if (id != null && !id.trim().isEmpty()) {
            ArrayList<Student> students = University.getStudentList();
            students.removeIf(student -> student.getStudentId().equals(id));
            University.setStudentList(students);
            JOptionPane.showMessageDialog(null, "Student deleted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showForm(String entity, String field1Label, String field2Label, String field3Label,
            String field4Label, String field5Label, String field6Label) {
        dynamicPanel.removeAll();

        // Input fields
        JLabel field1 = new JLabel(field1Label + ":");
        JTextField field1Input = new JTextField(20);
        JLabel field2 = new JLabel(field2Label + ":");
        JTextField field2Input = new JTextField(20);
        JLabel field3 = new JLabel(field3Label + ":");
        JTextField field3Input = new JTextField(20);
        JLabel field4 = new JLabel(field4Label + ":");
        JTextField field4Input = new JTextField(20);
        JLabel field5 = new JLabel(field5Label + ":");
        JTextField field5Input = new JTextField(20);
        JLabel field6 = new JLabel(field6Label + ":");
        JTextField field6Input = new JTextField(20);
        JButton saveButton = new JButton("Save");

        // Add inputs to the panel
        dynamicPanel.add(field1);
        dynamicPanel.add(field1Input);
        dynamicPanel.add(field2);
        dynamicPanel.add(field2Input);
        dynamicPanel.add(field3);
        dynamicPanel.add(field3Input);
        dynamicPanel.add(field4);
        dynamicPanel.add(field4Input);
        dynamicPanel.add(field5);
        dynamicPanel.add(field5Input);
        dynamicPanel.add(field6);
        dynamicPanel.add(field6Input);
        dynamicPanel.add(saveButton);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();

        saveButton.addActionListener(e -> {
            try {
                String name = field1Input.getText();
                String email = field2Input.getText();
                String studentId = field3Input.getText();
                LocalDate dateOfBirth = LocalDate.parse(field4Input.getText());
                String address = field5Input.getText();
                double marks = Double.parseDouble(field6Input.getText());

                if (name.isEmpty() || email.isEmpty() || studentId.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<Course> courses = new ArrayList<>();
                Student student = new Student(name, email, dateOfBirth, address, studentId, marks, courses);
                // Add to University's student list
                // Save all university data
                University.saveOneObject("universityData.ser", student);
                ;

                JOptionPane.showMessageDialog(null, "Student added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear input fields
                field1Input.setText("");
                field2Input.setText("");
                field3Input.setText("");
                field4Input.setText("");
                field5Input.setText("");
                field6Input.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Teacher> teachers = new ArrayList<>();
        ArrayList<AdministrativeStaff> staff = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();

        University university = new University("Tech Campus", students, teachers, staff, courses);
        new crud();
        University.saveToFile("universityData.ser");
        ArrayList<Object> dataList = University.allObjectsList; // Load data as a generic list

        // Separate the data into specific lists
        ArrayList<Student> student1 = new ArrayList<>();
        ArrayList<Teacher> teacher1 = new ArrayList<>();
        ArrayList<AdministrativeStaff> staffs = new ArrayList<>();
        ArrayList<Course> course1 = new ArrayList<>();

        // Iterate through the loaded dataList and separate them by type
        for (Object item : dataList) {
            if (item instanceof Student) {
                student1.add((Student) item);
            } else if (item instanceof Teacher) {
                teacher1.add((Teacher) item);
            } else if (item instanceof AdministrativeStaff) {
                staffs.add((AdministrativeStaff) item);
            } else if (item instanceof Course) {
                course1.add((Course) item);
            }
        }

        // Now, you can use these lists to process or display the data as needed
        System.out.println("Students:");
        for (Student student : student1) {
            System.out.println(student);
        }

        System.out.println("Teachers:");
        for (Teacher teacher : teacher1) {
            System.out.println(teacher);
        }

        System.out.println("Administrative Staff:");
        for (AdministrativeStaff admin : staffs) {
            System.out.println(admin);
        }

        System.out.println("Courses:");
        for (Course course : course1) {
            System.out.println(course);
        }

    }
}
