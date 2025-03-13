package new_e_learning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class StudentDAO {
    // ✅ Email validation regex
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // ✅ Validate email format
    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    // ✅ Check if email already exists in database
    public static boolean isEmailExists(String email) {
        boolean exists = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM student WHERE email_id = ?")) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;  // Email already exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    // ✅ Register a new student
    public int registerStudent(String studentname, String className, String rollno, String emailid, String username, String password) {
        int status = 0;
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO student (student_name, class, roll_no, email_id, username, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentname);
            ps.setString(2, className);
            ps.setString(3, rollno);
            ps.setString(4, emailid);
            ps.setString(5, username);
            ps.setString(6, hashPassword(password));

            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // ✅ Hash password using SHA-256
    public static String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

        // ✅ Validate login credentials (Username & Password)
        public boolean validateLogin(String username, String password) {
            boolean isValid = false;
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM student WHERE username = ?")) {
                
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    if (storedPassword.equals(password)) {  // ✅ Password check
                        isValid = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isValid;
        }
 }


