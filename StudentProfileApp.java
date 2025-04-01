package new_e_learning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class StudentProfileApp {

    public void createProfile(int roll_no) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Address:");
        String address = sc.nextLine();

        System.out.println("Enter Guardian Name:");
        String guardianName = sc.nextLine();

        System.out.println("Enter Guardian Phone:");
        String guardianPhone = sc.nextLine();

        System.out.println("Enter Marksheet File Path (optional):");
        String marksheet = sc.nextLine();
        if (marksheet.isEmpty()) marksheet = null;

        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO student_profiles (roll_no, address, guardian_name, guardian_phone, marksheet) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, roll_no);
            ps.setString(2, address);
            ps.setString(3, guardianName);
            ps.setString(4, guardianPhone);
            ps.setString(5, marksheet);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Profile created successfully!");
            } else {
                System.out.println("Profile creation failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
