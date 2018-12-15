package cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Sign up servlet that lets the user sign up to create an account
 * @author Tae Hyon Lee
 *
 */
public class SignUpServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(SignUpServlet.class.getName());
	private SQLManager manager = SQLManager.getInstance();

	/**
	 * Displays the sign up for if the user has not logged in yet
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession httpSession = request.getSession(false); 
		if(httpSession != null){
			logger.log(Level.INFO, "Logged in already");
		    ResponseConstants.redirect(response);
			response.setHeader("Location", "/");
		}
		
		else {	
			logger.log(Level.INFO, "Anonymouse user request for signup");
			ResponseConstants.okay(response);
			PrintWriter out = response.getWriter();
			out.println(HTMLForm.signUpForm());
		}
	}
	
	/**
	 * Creates a new user if the requirements are met
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String username = request.getParameter("username").toLowerCase();
		String password = request.getParameter("password");
		try {
			if (manager.checkExistingUser(username)) {
				ResponseConstants.okay(response);
				PrintWriter out = response.getWriter();
				out.println(HTMLForm.signUpForm("User already exists"));
				out.close();
			}
			else {
				byte[] salt = Password.getNextSalt();
				byte[] hashedPassword = Password.hash(password.toCharArray(), salt);
				int id = manager.createUser(username, Base64.getEncoder().encodeToString(hashedPassword), Base64.getEncoder().encodeToString(salt));
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("username", username);
				httpSession.setAttribute("id", id);
				logger.log(Level.INFO, "Sign up successful for " + username);
				ResponseConstants.redirect(response);
				response.setHeader("Location", "/");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error while signing up");
			response.setHeader("Location", "/signup");
		}
	}
}
