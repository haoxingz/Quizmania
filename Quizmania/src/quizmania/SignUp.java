package quizmania;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUp
 */
@WebServlet(description = "Try to create a new user with the user input", urlPatterns = { "/SignUp" })
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		String passwordOnce = request.getParameter("passwordOnce");
		String passwordTwice = request.getParameter("passwordTwice");

		out.println("<!DOCTYPE html>"); 
		out.println("<head>"); 
		out.println("<meta charset=\"UTF-8\" />"); 
		out.println("<title>Login</title>"); 
		out.println("</head>"); 
		out.println("<body>"); 
		out.println("<h1>The SignUp servlet has not been implemented. </h1>"); 
		out.println("Username: " + username);
		out.println("Password: " + passwordOnce + "<br>");
		out.println("Password: " + passwordTwice + "<br>");
		out.println("</body>"); 
		out.println("</html>"); 
		
		//TODO: Check if two passwords match each other. 
		//If not, go to the wrong signup page, pass error message
		
		//TODO: Check if the username exists, if exists, go to the error page, pass message
		
		//TODO: Create user. Upon success, go to Profile.jsp
	}

}
