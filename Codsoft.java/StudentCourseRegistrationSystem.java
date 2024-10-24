import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private final String courseCode;
    private final String title;
    private final String description;
    private final int capacity;
    private int enrolledStudents;
    private final String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
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

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean register() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean drop() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    public boolean hasAvailableSlots() {
        return enrolledStudents < capacity;
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode +
                ", Title: " + title +
                ", Description: " + description +
                ", Capacity: " + capacity +
                ", Enrolled: " + enrolledStudents +
                ", Schedule: " + schedule;
    }
}

class Student {
    private final String studentId;
    private final String name;
    private final List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course.register()) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.remove(course) && course.drop()) {
            return true;
        }
        return false;
    }
}

class CourseRegistrationSystem {
    private final List<Course> courses;
    private final List<Student> students;

    public CourseRegistrationSystem() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        initializeCourses();
    }

    private void initializeCourses() {
        courses.add(new Course("CS101", "Introduction to Computer Science", "Learn the basics of programming.", 30, "Mon-Wed-Fri 10:00-11:00"));
        courses.add(new Course("MATH201", "Calculus I", "An introduction to differential calculus.", 25, "Tue-Thu 9:00-10:30"));
        courses.add(new Course("ENG202", "English Literature", "Study major works of English literature.", 20, "Mon-Wed 14:00-15:30"));
    }

    public void displayCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void registerStudent(String studentId, String name) {
        students.add(new Student(studentId, name));
    }

    public Student findStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}

public class StudentCourseRegistrationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        while (true) {
            System.out.println("\n1. Display Courses");
            System.out.println("2. Register Student");
            System.out.println("3. Register for Course");
            System.out.println("4. Drop Course");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    system.displayCourses();
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    system.registerStudent(studentId, name);
                    System.out.println("Student registered successfully.");
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextLine();
                    Student student = system.findStudent(studentId);
                    if (student != null) {
                        System.out.print("Enter Course Code to register: ");
                        String courseCode = scanner.nextLine();
                        Course course = system.findCourse(courseCode);
                        if (course != null && student.registerCourse(course)) {
                            System.out.println("Registered successfully for " + course.getTitle());
                        } else {
                            System.out.println("Registration failed. Course may be full or not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextLine();
                    student = system.findStudent(studentId);
                    if (student != null) {
                        System.out.print("Enter Course Code to drop: ");
                        String dropCourseCode = scanner.nextLine();
                        Course course = system.findCourse(dropCourseCode);
                        if (course != null && student.dropCourse(course)) {
                            System.out.println("Dropped successfully from " + course.getTitle());
                        } else {
                            System.out.println("Drop failed. Course may not be registered or not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
