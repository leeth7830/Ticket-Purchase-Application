package cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * User infor servlet that displays all user related information including all the events that the user has previously purchased tickets to
 * @author Tae Hyon Lee
 *
 */
public class UserInfoServlet extends HttpServlet{
	private Logger logger = Logger.getLogger(UserInfoServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * Displays the user list page if user is logged in
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		if (httpSession != null) {
			int userId = (int)(Integer) httpSession.getAttribute("id");
			String username = (String) httpSession.getAttribute("username");
			ResponseConstants.okay(response);
			PrintWriter out = response.getWriter();
			try {
				ArrayList<Event> list = manager.getUserList(userId);
				out.println(HTMLForm.userDetail(list, username));
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("ERROR while displaying event");
			}
			out.close();
		}
		else {
			ResponseConstants.redirect(response);
			response.setHeader("Location", "/login");
		}
	}
	
	/**
	 * If the current user pressed transfer then it redirect the user to transfer ticket page
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		ResponseConstants.redirect(response);
		if(httpSession != null){
			String transfer = request.getParameter("transfer");
			String eventId = request.getParameter("event_id");
			if (transfer != null && eventId != null) {
				response.setHeader("Location", "/transferticket?id=" + eventId);
			}
		}
		else {
			response.setHeader("Location", "/login");
		}
	}
}
