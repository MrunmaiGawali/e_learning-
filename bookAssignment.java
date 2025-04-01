package bookNotebooks;


import java.sql.*;
import java.util.Scanner;

public class bookAssignment {
    private static final String URL = "jdbc:mysql://localhost:3306/register";
    private static final String USER = "root";
    private static final String PASSWORD = "Gawalim@12";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connected Successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void insertBookAssignment(int bookId, Integer rollNo, Integer teacherId) {
        String sql = "INSERT INTO book_assignment (book_id, roll_no, teacher_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, bookId);
            if (rollNo != null) pstmt.setInt(2, rollNo); else pstmt.setNull(2, Types.INTEGER);
            if (teacherId != null) pstmt.setInt(3, teacherId); else pstmt.setNull(3, Types.INTEGER);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book Assignment Created!");
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int assignmentId = rs.getInt(1);
                    System.out.println("Assignment ID: " + assignmentId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertMCQ(int assignmentId, String question, String optionA, String optionB, String optionC, String optionD, char correctOption) {
        String sql = "INSERT INTO mcq_questions (assignment_id, question, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, assignmentId);
            pstmt.setString(2, question);
            pstmt.setString(3, optionA);
            pstmt.setString(4, optionB);
            pstmt.setString(5, optionC);
            pstmt.setString(6, optionD);
            pstmt.setString(7, String.valueOf(correctOption));
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("MCQ Question Added!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertTheoreticalQuestion(int assignmentId, String question) {
        String sql = "INSERT INTO theoretical_questions (assignment_id, question) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, assignmentId);
            pstmt.setString(2, question);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Theoretical Question Added!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchAssignments() {
        String sql = "SELECT ba.assignment_id, ba.book_id, ba.roll_no, ba.teacher_id, " +
                     "mcq.question AS mcq_question, mcq.option_a, mcq.option_b, mcq.option_c, mcq.option_d, mcq.correct_option, " +
                     "tq.question AS theoretical_question " +
                     "FROM book_assignment ba " +
                     "LEFT JOIN mcq_questions mcq ON ba.assignment_id = mcq.assignment_id " +
                     "LEFT JOIN theoretical_questions tq ON ba.assignment_id = tq.assignment_id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int assignmentId = rs.getInt("assignment_id");
                int bookId = rs.getInt("book_id");
                Integer rollNo = rs.getObject("roll_no", Integer.class);
                Integer teacherId = rs.getObject("teacher_id", Integer.class);
                String mcqQuestion = rs.getString("mcq_question");
                String mcqOptionA = rs.getString("option_a");
                String mcqOptionB = rs.getString("option_b");
                String mcqOptionC = rs.getString("option_c");
                String mcqOptionD = rs.getString("option_d");
                String correctOption = rs.getString("correct_option");
                String theoreticalQuestion = rs.getString("theoretical_question");

                System.out.println("Assignment ID: " + assignmentId + ", Book ID: " + bookId + ", Roll No: " + rollNo + ", Teacher ID: " + teacherId);
                if (mcqQuestion != null) {
                    System.out.println("MCQ: " + mcqQuestion);
                    System.out.println("A) " + mcqOptionA + "  B) " + mcqOptionB + "  C) " + mcqOptionC + "  D) " + mcqOptionD);
                    System.out.println("Correct Answer: " + correctOption);
                }
                if (theoreticalQuestion != null) {
                    System.out.println("Theoretical Question: " + theoreticalQuestion);
                }
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Insert Book Assignment");
            System.out.println("2. Insert MCQ Question");
            System.out.println("3. Insert Theoretical Question");
            System.out.println("4. Fetch All Assignments");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    System.out.print("Enter Roll No (or 0 for null): ");
                    int rollNo = scanner.nextInt();
                    System.out.print("Enter Teacher ID (or 0 for null): ");
                    int teacherId = scanner.nextInt();
                    insertBookAssignment(bookId, rollNo == 0 ? null : rollNo, teacherId == 0 ? null : teacherId);
                    break;
                case 2:
                    System.out.print("Enter Assignment ID: ");
                    int assignmentId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter MCQ Question: ");
                    String question = scanner.nextLine();
                    insertMCQ(assignmentId, question, "A", "B", "C", "D", 'A');
                    break;
                case 3:
                    System.out.print("Enter Assignment ID: ");
                    int asgId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Theoretical Question: ");
                    String tq = scanner.nextLine();
                    insertTheoreticalQuestion(asgId, tq);
                    break;
                case 4:
                    fetchAssignments();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }
}