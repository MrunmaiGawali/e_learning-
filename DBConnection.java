package new_e_learning;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "experience@1234");
            System.out.println("Database Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
