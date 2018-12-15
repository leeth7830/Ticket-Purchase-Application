package cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Create Event Servlet that handles creating new events for logged in users
 * @author Tae Hyon Lee
 *
 */
public class CreateEventServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(EventDetailServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * Checks for login status and displays create event form if logged in
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		if (httpSession != null) {
			ResponseConstants.okay(response);
			PrintWriter out = response.getWriter();
			out.println(HTMLForm.createEvent());
			out.close();
		}
		else {
			ResponseConstants.redirect(response);
			response.setHeader("Location", "/login");
		}
	}
	
	/**
	 * Checks for login status and creates new event with the specified parameters
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		if(httpSession != null){
			String create = request.getParameter("create");
			int userId = (int)(Integer) httpSession.getAttribute("id");
			if (create != null) {
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				Date date = Date.valueOf(request.getParameter("date"));
				int numTickets = Integer.parseInt(request.getParameter("number"));
				try {
					manager.createEvent(name, description, numTickets, date, userId);
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Error while creating event");
				}
				ResponseConstants.redirect(response);
				response.setHeader("Location", "/?m=create");
			}
		}
		else {
			ResponseConstants.redirect(response);
			response.setHeader("Location", "/login");
		}
	}
}

