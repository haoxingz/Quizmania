package quizmania;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet(description = "Check user information for login", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String password = request.getParameter("password");
		
		// TODO Consult the database whether username/password combination exists
		// TODO If exists, redirect to profile.jsp
		// TODO If not, redirect to the error page telling people to sign up
		
		out.println("<!DOCTYPE html>"); 
		out.println("<head>"); 
		out.println("<meta charset=\"UTF-8\" />"); 
		out.println("<title>Login</title>"); 
		out.println("</head>"); 
		out.println("<body>"); 
		out.println("<h1>The Login servlet has not been implemented. </h1>"); 
		out.println("Username: " + username);
		out.println("Password: " + password);
		out.println("</body>"); 
		out.println("</html>"); 

	}

}
