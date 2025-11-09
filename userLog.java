import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class userLog extends HttpServlet {

    @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the username and password from the Login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Connect to the database
        Connection connection = null;
        try {
            // Connect to MySQL database
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/onlineusers?serverTimezone=UTC",
                    "root", "root"
            );

            // Prepare a SQL statement to find a matching username and password
            PreparedStatement ps = connection.prepareStatement(
                "SELECT credit FROM playerInfo WHERE Username = ? AND Password = ?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            // Run the query and store the result
            ResultSet rs = ps.executeQuery();

            // If a user exists with matching credentials
            if (rs.next()) {
                int credits = rs.getInt("credit"); 
                // Login successful → redirect to welcome page
                response.sendRedirect("Welcome.html");
            } else {
                // Login failed → show error message
                PrintWriter out = response.getWriter();
                out.println("<html><body>Invalid Username or Password</body></html>");
            }

            // Close database objects
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
