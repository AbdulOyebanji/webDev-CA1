

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class userReg extends HttpServlet {


public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NullPointerException {

	String username = request.getParameter("Username");
	String password = request.getParameter("Password");
	String rePassword = request.getParameter("rePassword");

	int credits = 500;

	

	
	//2 Put the userinfo into the database
			Connection connection = null;
			try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/onlineusers?serverTimezone=UTC");
				
				
				if (password != null && rePassword!= null && password.equals(rePassword)) {
					
					PreparedStatement storePlayerInfo = connection.prepareStatement(
							"INSERT into PlayerInfo "
							+ "(Username, Password,credit)" +" VALUES (?, ?,?)");
							//pass in the values as parameters
					storePlayerInfo.setString(1, username);
					storePlayerInfo.setString(2, password);
					storePlayerInfo.setInt(3, 500 );
					int rowsUpdated = storePlayerInfo.executeUpdate();
					storePlayerInfo.close();
					response.sendRedirect("Login.html");
					return;
				
				}else {
					response.setContentType("text/html");
					PrintWriter out1 = response.getWriter();
					out1.println("<html><head><title>Response Page</title></head><body> Passwords did not match </body><html>");
					return;
					}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	
}}


