package cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Authentication.User;

/**
 * Login Servlet that lets the user log in if authenticated
 * @author Tae Hyon Lee
 *
 */
public class LoginServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(LoginServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * Displays the login form if the user is not logged in. Otherwise, redirect to home page
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession httpSession = request.getSession(false); 
		if(httpSession != null){
			logger.log(Level.INFO, "Logged in already");
		    ResponseConstants.redirect(response);
			response.setHeader("Location", "/");
		}
		
		else {	
			logger.log(Level.INFO, "Anonymouse user request for login");
			ResponseConstants.okay(response);
			PrintWriter out = response.getWriter();
			out.println(HTMLForm.loginForm());
		}
	}
	
	/**
	 * Takes in username and password to authenticate the user
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String signup = request.getParameter("signup");
		ResponseConstants.redirect(response);
		if (signup != null) {
			
			response.setHeader("Location", "/signup");
		}
		else {
			String username = request.getParameter("username").toLowerCase();
			String password = request.getParameter("password");
			try {
				Integer id = manager.authenticate(username, password);
				if (id != -1) {
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("username", username);
					httpSession.setAttribute("id", id);
					logger.log(Level.INFO, "Login successful for " + username);
					response.setHeader("Location", "/");
				}
				else {
					ResponseConstants.okay(response);
					PrintWriter out = response.getWriter();
					out.println(HTMLForm.loginForm("Wrong username/password"));
				}
			} catch (SQLException e) {
				System.err.println("Error while authenticating");
			}			
		}
	}
}
