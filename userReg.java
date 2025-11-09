

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
		PrintWriter out1 = response.getWriter();
		
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		int credits = 500;

		//2 Put the userinfo into the database
	 if (password != null){
		//2 	System.out.println("hello im triggered here the password is not empty");
		//System.out.println("hello im triggered here password is the same");
		if (password.equals(rePassword)) { 
			System.out.println("hello im triggered here password is the same");
				// gets the users info
			Connection connection = null;
			try{
				connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/onlineusers?serverTimezone=UTC", "root", "root");
				
				PreparedStatement storePlayerInfo = connection.prepareStatement(
						"INSERT into playerInfo (username, password) VALUES (?, ?)");
				//pass in the values as parameters
				storePlayerInfo.setString(1, userName);
				storePlayerInfo.setString(2, password);

				int rowsUpdated = storePlayerInfo.executeUpdate();
				storePlayerInfo.close();
				
				out1.println("<html><head><title>Response Page</title></head>");
				out1.println("<body> Registered Successfully </body><html>");

				if (rowsUpdated > 0 ) {

					System.out.println("User Registered: " + userName);
					response.sendRedirect("Login.html");
				}
				else {
					System.out.println("No rows inserted");
				}

			
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				
				out1.println(rePassword);
			}	out1.println("<body> Passwords did not match </body><html>");
			}
		
			response.setContentType("text/html");
			
			
			return;
		}	
		
	}







