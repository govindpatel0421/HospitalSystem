import java.sql.*;
import java.util.Scanner;

public class HospitalSystem {
    // Database credentials
    static final String URL = "jdbc:mysql://localhost:3306/hospitaldb";
    static final String USER = "root";
    static final String PASS = "********"; 
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== HOSPITAL BOOKING SYSTEM ===");
                System.out.println("1. Register a New Patient");
                System.out.println("2. Book an Appointment");
                System.out.println("3. View All Patients");
                System.out.println("4. View All Appointments");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice == 1) {
                    System.out.print("Enter Patient Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Patient Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Gender (M/F): ");
                    String gender = scanner.nextLine();

                    String sql = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setInt(2, age);
                    pstmt.setString(3, gender);
                    pstmt.executeUpdate();
                    System.out.println("--> Patient registered successfully!");

                } else if (choice == 2) {
                    System.out.print("Enter Patient ID (Must exist in Patient table): ");
                    int patientId = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter Doctor's Name: ");
                    String doctor = scanner.nextLine();
                    System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();

                    String sql = "INSERT INTO appointments (patient_id, doctor_name, appointment_date) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, patientId);
                    pstmt.setString(2, doctor);
                    pstmt.setString(3, date);
                    pstmt.executeUpdate();
                    System.out.println("--> Appointment booked successfully!");

                } else if (choice == 3) {
                    String sql = "SELECT * FROM patients";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    System.out.println("\n--- PATIENT LIST ---");
                    System.out.println("ID | Name | Age | Gender");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getInt("age") + " | " + rs.getString("gender"));
                    }

                } else if (choice == 4) {
                    String sql = "SELECT a.id, p.name, a.doctor_name, a.appointment_date FROM appointments a JOIN patients p ON a.patient_id = p.id";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    System.out.println("\n--- APPOINTMENT LIST ---");
                    System.out.println("Appt ID | Patient Name | Doctor | Date");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getString("doctor_name") + " | " + rs.getString("appointment_date"));
                    }

                } else if (choice == 5) {
                    System.out.println("Exiting system. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
            scanner.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}