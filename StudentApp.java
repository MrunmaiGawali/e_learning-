package new_e_learning;

import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAO();

        System.out.println("📌 Welcome to E-Learning Platform");
        System.out.println("1️⃣ Register");
        System.out.println("2️⃣ Login");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (choice == 1) {
            // 🔹 Registration Process
            System.out.println("Enter Student Name:");
            String studentname = sc.nextLine();

            System.out.println("Enter Class:");
            String className = sc.nextLine();

            System.out.println("Enter Roll No:");
            String rollno = sc.nextLine();

            String emailid;
            do {
                System.out.println("Enter Email ID:");
                emailid = sc.nextLine();
                if (!StudentDAO.isValidEmail(emailid)) {
                    System.out.println("❌ Invalid email format. Try again.");
                } else if (StudentDAO.isEmailExists(emailid)) {
                    System.out.println("❌ An account with this email already exists. Please enter a different email.");
                }
            } while (!StudentDAO.isValidEmail(emailid) || StudentDAO.isEmailExists(emailid));

            System.out.println("Enter Username:");
            String username = sc.nextLine();

            System.out.println("Enter Password:");
            String password = sc.nextLine();

            int result = studentDAO.registerStudent(studentname, className, rollno, emailid, username, password);

            if (result > 0) {
                System.out.println("✅ Student Registered Successfully!");
            } else {
                System.out.println("❌ Registration failed. Please try again.");
            }

        } else if (choice == 2) {
            // 🔹 Login Process
            System.out.println("Enter Username:");
            String username = sc.nextLine();

            System.out.println("Enter Password:");
            String password = sc.nextLine();

            boolean loginSuccess = studentDAO.validateLogin(username, password);
            if (loginSuccess) {
                System.out.println("✅ Login Successful! Welcome " + username);
            } else {
                System.out.println("❌ Invalid Credentials. Please try again.");
            }
        } else {
            System.out.println("❌ Invalid choice! Please restart the application.");
        }
        

        sc.close();
    }
}
