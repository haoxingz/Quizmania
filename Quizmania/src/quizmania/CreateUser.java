package quizmania;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet(description = "Try to create a new user with the user input", urlPatterns = { "/CreateUser" })
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
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

		request.setAttribute("errorMessage", "Create User not implemented yet");
		
		RequestDispatcher dispatch = request.getRequestDispatcher("SignUpError.jsp");
		dispatch.forward(request, response);
		//TODO: Check if two passwords match each other. 
		//If not, go to the wrong signup page, pass error message
		
		//TODO: Check if the username exists, if exists, go to the error page, pass message
		
		//TODO: Create user. Upon success, go to Profile.jsp
	}

}
