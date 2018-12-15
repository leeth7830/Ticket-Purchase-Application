package cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Event List servlet that shows all the events available. It will also let the user search for name, description, or the creater's user id to find events
 * @author Tae Hyon Lee
 *
 */
public class EventListServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(EventListServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * If there is a query parameter, then the list generates a list of events that are related to the query. If it is not present, then it will just display all of the events
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		if(httpSession != null){
			ResponseConstants.okay(response);
			PrintWriter out = response.getWriter();
			String query = request.getParameter("query");
			if (query != null && !query.equals("")) {
				query = query.replaceAll("[^A-Za-z0-9]", "");
				try {
					out.println(HTMLForm.eventList(manager.searchEventList(query)));
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("ERROR while generating eventlist with search");
				}
			}
			else {
				try {
					out.println(HTMLForm.eventList(manager.getEventList()));
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("ERROR while generating eventlist");
				}
			}
		}
		else {
			ResponseConstants.redirect(response);
			response.setHeader("Location", "/login");
		}
	}
	
	/**
	 * When a user enters a query in a form and submits, it will perform a search
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false);
		ResponseConstants.redirect(response);
		if(httpSession != null){
			String showAll = request.getParameter("showall");
			String query = request.getParameter("term");
			int userId = (int)(Integer) httpSession.getAttribute("id");
			if (showAll != null) {
				response.setHeader("Location", "/eventlist");
			}
			else if (query != null && !query.equals("")) {	
				query.replaceAll("[^A-Za-z0-9]", "");
				response.setHeader("Location", "/eventlist?query=" + query);
			}
			else {
				response.setHeader("Location", "/eventlist");
			}
		}
		else {
			response.setHeader("Location", "/login");
		}
	}
}
