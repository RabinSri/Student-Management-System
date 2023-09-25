package myproject;
import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/firstdb";
    static final String JDBC_USER = "root";
    static final String JDBC_PASSWORD = "srirabin@7";

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException {
        try 
           {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS students (roll_number INT PRIMARY KEY, name VARCHAR(255), age INT, score FLOAT, department VARCHAR(255), enrollment_year INT)");


            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (username.equals("Rabin") && password.equals("1234")) {
                while (true) {
                    System.out.println("\nStudent Management System");
                    System.out.print("1. Add ");
                    System.out.print(" 2. Remove ");
                    System.out.print(" 3. Update ");
                    System.out.println(" 4. Display ");
                    System.out.println("5. Sort Students");
                    System.out.println("6. Total Student Count");
                    System.out.println("7. Search Students by Name");
                    System.out.println("8. Exit");
                    System.out.print("Select an option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter Roll Number: ");
                            int rollNumber = scanner.nextInt();
                            ResultSet rollCheck = stmt.executeQuery("SELECT * FROM students WHERE roll_number = " + rollNumber);
                            while (rollCheck.next()) {
                                System.out.println("Roll Number " + rollNumber + " already exists. Please enter a unique roll number.");
                                System.out.print("Enter Valid Roll Number: ");
                                rollNumber = scanner.nextInt();
                                rollCheck = stmt.executeQuery("SELECT * FROM students WHERE roll_number = " + rollNumber);
                            }
                            scanner.nextLine();
                            System.out.print("Enter Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter Age: ");
                            int age = scanner.nextInt();
                            System.out.print("Enter Score: ");
                            float score = scanner.nextFloat();
                            scanner.nextLine();
                            System.out.print("Enter Department: ");
                            String department = scanner.nextLine();
                            System.out.print("Enter Enrollment Year: ");
                            int enrollmentYear = scanner.nextInt();
                            stmt.executeUpdate("INSERT INTO students VALUES (" + rollNumber + ", '" + name + "', " + age + ", " + score + ", '" + department + "', " + enrollmentYear + ")");
                            System.out.println("Student added successfully.");
                            break;
                        case 2:
                            System.out.print("Enter Roll Number to Remove: ");
                            int rollToRemove = scanner.nextInt();
                            stmt.executeUpdate("DELETE FROM students WHERE roll_number = " + rollToRemove);
                            System.out.println("Student removed successfully.");
                            break;
                        case 3:
                            System.out.print("Enter Roll Number to Update: ");
                            int rollToUpdate = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter New Name: ");
                            String newName = scanner.nextLine();
                            System.out.print("Enter New Age: ");
                            int newAge = scanner.nextInt();
                            System.out.print("Enter New Score: ");
                            float newScore = scanner.nextFloat();
                            scanner.nextLine();
                            System.out.print("Enter New Department: ");
                            String newDepartment = scanner.nextLine();
                            System.out.print("Enter New Enrollment Year: ");
                            int newEnrollmentYear = scanner.nextInt();
                            stmt.executeUpdate("UPDATE students SET name = '" + newName + "', age = " + newAge + ", score = " + newScore + ", department = '" + newDepartment + "', enrollment_year = " + newEnrollmentYear + " WHERE roll_number = " + rollToUpdate);
                            System.out.println("Student updated successfully.");
                            break;
                        case 4:
                            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                            displayStudentResults(rs);
                            break;
                        case 5:
                            sortStudents(stmt);
                            break;
                        case 6:
                            int studentCount = getTotalStudentCount(stmt);
                            System.out.println("Total Student Count: " + studentCount);
                            break;
                        case 7:
                            System.out.print("Enter Student Name to Search: ");
                            String searchName = scanner.nextLine();
                            ResultSet searchResult = stmt.executeQuery("SELECT * FROM students WHERE name LIKE '%" + searchName + "%'");
                            displayStudentResults(searchResult);
                            break;
                        case 8:
                            System.exit(0);
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
            } else {
                System.out.println("Authentication failed. Exiting.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void displayStudentResults(ResultSet rs) throws SQLException {
        System.out.println("\nRoll Number\tName\t\tAge\tScore\tDepartment\tEnrollment Year");
        while (rs.next()) {
            System.out.println(rs.getInt("roll_number") + "\t\t" + rs.getString("name") + "\t\t" + rs.getInt("age") + "\t" + rs.getFloat("score") + "\t" + rs.getString("department") + "\t\t" + rs.getInt("enrollment_year"));
        }
    }

    private static void sortStudents(Statement stmt) throws SQLException {
        System.out.println("\nSort Students:");
        System.out.println("1. Sort by Roll Number (Ascending)");
        System.out.println("2. Sort by Roll Number (Descending)");
        System.out.println("3. Sort by Name (Ascending)");
        System.out.println("4. Sort by Name (Descending)");
        System.out.println("5. Sort by Age (Ascending)");
        System.out.println("6. Sort by Age (Descending)");
        System.out.println("7. Sort by Score (Ascending)");
        System.out.println("8. Sort by Score (Descending)");
        System.out.println("9. Sort by Department (Ascending)");
        System.out.println("10. Sort by Department (Descending)");
        System.out.println("11. Sort by Enrollment Year (Ascending)");
        System.out.println("12. Sort by Enrollment Year (Descending)");
        System.out.print("Select a sorting option: ");

        int sortChoice = scanner.nextInt();
        String sortOrder = (sortChoice % 2 == 0) ? "DESC" : "ASC";

        String orderBy = "";
        switch (sortChoice) {
            case 1:
            case 2:
                orderBy = "roll_number " + sortOrder;
                break;
            case 3:
            case 4:
                orderBy = "name " + sortOrder;
                break;
            case 5:
            case 6:
                orderBy = "age " + sortOrder;
                break;
            case 7:
            case 8:
                orderBy = "score " + sortOrder;
                break;
            case 9:
            case 10:
                orderBy = "department " + sortOrder;
                break;
            case 11:
            case 12:
                orderBy = "enrollment_year " + sortOrder;
                break;
            default:
                System.out.println("Invalid sorting option. Please try again.");
                return;
        }

        ResultSet sortedStudents = stmt.executeQuery("SELECT * FROM students ORDER BY " + orderBy);
        displayStudentResults(sortedStudents);
    }

    private static int getTotalStudentCount(Statement stmt) throws SQLException {
        ResultSet countResult = stmt.executeQuery("SELECT COUNT(*) AS count FROM students");
        if (countResult.next()) {
            return countResult.getInt("count");
        }
        return 0;
    }
}