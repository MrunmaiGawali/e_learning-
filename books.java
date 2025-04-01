package bookNotebooks;



import java.sql.*;
import java.util.Scanner;

public class books
{
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/register"; // Change DB name if needed
        String user = "root";
        String password = "Gawalim@12";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Scanner sc = new Scanner(System.in);
            boolean keepRunning = true;

            while (keepRunning) {
                System.out.println("\n=== BOOK MENU ===");
                System.out.println("1. Enter Book Details");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Book Title: ");
                        String bookTitle = sc.nextLine();
                        System.out.print("Book Image File Path: ");
                        String bookImage = sc.nextLine();
                        System.out.print("Book File Link (URL): ");
                        String bookFile = sc.nextLine();
                        System.out.print("Class Name");
                        String className = sc.nextLine();

                        String insertBookSQL = "INSERT INTO book (title, image_file, file_link, class_name) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement psBook = conn.prepareStatement(insertBookSQL, Statement.RETURN_GENERATED_KEYS)) {
                            psBook.setString(1, bookTitle);
                            psBook.setString(2, bookImage);
                            psBook.setString(3, bookFile);
                            psBook.setString(4, className);
                            psBook.executeUpdate();
                            ResultSet rs = psBook.getGeneratedKeys();
                            if (rs.next()) {
                                int id = rs.getInt(1);
                                System.out.println("\nInserted Book Record:");
                                System.out.println("ID: " + id);
                                System.out.println("Title: " + bookTitle);
                                System.out.println("Image: " + bookImage);
                                System.out.println("File Link: " + bookFile);
                                System.out.println("Class Name: " + className);
                            }
                        }
                        break;

                    case 2:
                        keepRunning = false;
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }

            sc.close();
            System.out.println("Exited!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

