package bookNotebooks;

import java.sql.*;
import java.util.Scanner;

public class pages {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/register";
        String user = "root"; // your DB username
        String password = "Gawalim@12"; // your DB password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // Take dynamic input
            System.out.print("Enter Roll No: ");
            int rollNo = sc.nextInt();

            System.out.print("Enter Notebook ID: ");
            int notebookId = sc.nextInt();

            System.out.print("Enter Page Number: ");
            int pageNumber = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            System.out.print("Enter Page Type: ");
            String pageType = sc.nextLine();

            // Updated SQL query without LONGBLOB
            String sql = "INSERT INTO pages (roll_no, notebook_id, page_number, page_type) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, rollNo);
                ps.setInt(2, notebookId);
                ps.setInt(3, pageNumber);
                ps.setString(4, pageType);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("✅ Insert successful into register.pages!");
                } else {
                    System.out.println("⚠️ Insert failed.");
                }

            } catch (SQLException e) {
                System.out.println("❌ Error while inserting: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
        }
    }
}
