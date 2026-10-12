package com.airtribe.learntrack;

import java.util.List;
import java.util.Scanner;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

public class Main {
    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    private Main(Scanner scanner, StudentService studentService, CourseService courseService,
            EnrollmentService enrollmentService) {
        this.scanner = scanner;
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        Main application = new Main(new Scanner(System.in), new StudentService(studentRepository),
                new CourseService(courseRepository),
                new EnrollmentService(enrollmentRepository, studentRepository, courseRepository));
        application.run();
    }

    private void run() {
        System.out.println(AppConstants.APPLICATION_NAME);
        boolean running = true;
        while (running) {
            printMainMenu();
            int option = InputValidator.readInt(scanner, "Choose an option: ");
            switch (option) {
                case MenuOptions.STUDENTS:
                    studentMenu();
                    break;
                case MenuOptions.COURSES:
                    courseMenu();
                    break;
                case MenuOptions.ENROLLMENTS:
                    enrollmentMenu();
                    break;
                case MenuOptions.EXIT:
                    running = false;
                    break;
                default:
                    System.out.println(AppConstants.INVALID_OPTION);
            }
        }
        System.out.println("Goodbye.");
    }

    private void printMainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("0. Exit");
    }

    private void studentMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nStudent Management");
            System.out.println("1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Deactivate student");
            System.out.println("0. Back");
            int option = InputValidator.readInt(scanner, "Choose an option: ");

            try {
                switch (option) {
                    case MenuOptions.ADD:
                        addStudent();
                        break;
                    case MenuOptions.LIST:
                        printStudents(studentService.listStudents());
                        break;
                    case MenuOptions.SEARCH:
                        printStudent(studentService.findStudentById(
                                InputValidator.readInt(scanner, "Student ID: ")));
                        break;
                    case MenuOptions.DEACTIVATE:
                        int studentId = InputValidator.readInt(scanner, "Student ID: ");
                        studentService.deactivateStudent(studentId);
                        System.out.println("Student deactivated.");
                        break;
                    case MenuOptions.BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION);
                }
            } catch (EntityNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void addStudent() {
        String firstName = InputValidator.readRequiredText(scanner, "First name: ");
        String lastName = InputValidator.readRequiredText(scanner, "Last name: ");
        String email = InputValidator.readOptionalText(scanner, "Email (optional): ");
        String batch = InputValidator.readRequiredText(scanner, "Batch: ");
        Student student = email.isEmpty()
                ? studentService.addStudent(firstName, lastName, batch)
                : studentService.addStudent(firstName, lastName, email, batch);
        System.out.println("Student added with ID " + student.getId() + ".");
    }

    private void printStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println(AppConstants.EMPTY_LIST);
            return;
        }
        for (Student student : students) {
            printStudent(student);
        }
    }

    private void printStudent(Student student) {
        System.out.println(student);
    }

    private void courseMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nCourse Management");
            System.out.println("1. Add course");
            System.out.println("2. View all courses");
            System.out.println("3. Activate course");
            System.out.println("4. Deactivate course");
            System.out.println("0. Back");
            int option = InputValidator.readInt(scanner, "Choose an option: ");

            try {
                switch (option) {
                    case MenuOptions.ADD:
                        addCourse();
                        break;
                    case MenuOptions.LIST:
                        printCourses(courseService.listCourses());
                        break;
                    case MenuOptions.ACTIVATE:
                        courseService.setCourseActive(InputValidator.readInt(scanner, "Course ID: "), true);
                        System.out.println("Course activated.");
                        break;
                    case MenuOptions.DEACTIVATE:
                        courseService.setCourseActive(InputValidator.readInt(scanner, "Course ID: "), false);
                        System.out.println("Course deactivated.");
                        break;
                    case MenuOptions.BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION);
                }
            } catch (EntityNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void addCourse() {
        String name = InputValidator.readRequiredText(scanner, "Course name: ");
        String description = InputValidator.readRequiredText(scanner, "Description: ");
        int duration = InputValidator.readInt(scanner, "Duration in weeks: ");
        Course course = courseService.addCourse(name, description, duration);
        System.out.println("Course added with ID " + course.getId() + ".");
    }

    private void printCourses(List<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println(AppConstants.EMPTY_LIST);
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void enrollmentMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nEnrollment Management");
            System.out.println("1. Enroll student in a course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. Mark enrollment completed");
            System.out.println("4. Mark enrollment cancelled");
            System.out.println("0. Back");
            int option = InputValidator.readInt(scanner, "Choose an option: ");

            try {
                switch (option) {
                    case MenuOptions.ENROLL:
                        enrollStudent();
                        break;
                    case MenuOptions.LIST_FOR_STUDENT:
                        printEnrollments(enrollmentService.listEnrollmentsForStudent(
                                InputValidator.readInt(scanner, "Student ID: ")));
                        break;
                    case MenuOptions.COMPLETE:
                        updateEnrollmentStatus(EnrollmentStatus.COMPLETED);
                        break;
                    case MenuOptions.CANCEL:
                        updateEnrollmentStatus(EnrollmentStatus.CANCELLED);
                        break;
                    case MenuOptions.BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION);
                }
            } catch (EntityNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void enrollStudent() {
        int studentId = InputValidator.readInt(scanner, "Student ID: ");
        int courseId = InputValidator.readInt(scanner, "Course ID: ");
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrollment created with ID " + enrollment.getId() + ".");
    }

    private void updateEnrollmentStatus(EnrollmentStatus status) {
        int enrollmentId = InputValidator.readInt(scanner, "Enrollment ID: ");
        enrollmentService.updateStatus(enrollmentId, status);
        System.out.println("Enrollment marked " + status.name().toLowerCase() + ".");
    }

    private void printEnrollments(List<Enrollment> enrollments) {
        if (enrollments.isEmpty()) {
            System.out.println(AppConstants.EMPTY_LIST);
            return;
        }
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }
}