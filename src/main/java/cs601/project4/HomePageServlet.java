package cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Home page servlet that gives a user option to create new events, view all events, view user information and log out.
 * @author Tae Hyon Lee
 *
 */
public class HomePageServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(HomePageServlet.class.getName());

	/**
	 * Generates home page with a message if it is valid
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		String username = null;
		if(httpSession != null){
			String message = request.getParameter("m");
			username = (String) httpSession.getAttribute("username");
			logger.log(Level.INFO, "Request from: " + username);
			ResponseConstants.okay(response);
			PrintWriter out = response.getWriter();	
			if (message == null) {
				out.println(HTMLForm.homePage(username));
			}
			else if (message.equals("create")){
				out.println(HTMLForm.homePage(username, "Successfully created!"));
			}
			else if (message.equals("modify")){
				out.println(HTMLForm.homePage(username, "Successfully modified!"));
			}
			else if (message.equals("remove")){
				out.println(HTMLForm.homePage(username, "Successfully removed!"));
			}
		}
		else {
			ResponseConstants.redirect(response);
			response.setHeader("Location", "/login");
		}
	}
	
	/**
	 * Takes in the parameter of the buttons user pressed and redirects them to the appropriate path
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		String username = null;
		ResponseConstants.redirect(response);
		if(httpSession != null){
			String logout = request.getParameter("logout");
			String viewEvent = request.getParameter("viewevent");
			String createEvent = request.getParameter("createvent");
			String viewuUser = request.getParameter("viewuser");
			
			if (logout != null) {
				response.setHeader("Location", "/logout");
			}
			else if (viewEvent != null) {
				response.setHeader("Location", "/eventlist");
			}
			else if (createEvent != null) {
				response.setHeader("Location", "/eventcreate");
			}
			else if (viewuUser != null) {
				response.setHeader("Location", "/userinfo");
			}			
			else {
				username = (String) httpSession.getAttribute("username");
				ResponseConstants.okay(response);
				PrintWriter out = response.getWriter();
				out.println(HTMLForm.homePage(username));
			}  
		}
		else {
			ResponseConstants.redirect(response);
		}
	}
}
