package cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Event Detail servlet that shows full details about the event
 * It also gives you an option to purchase or modify and remove if you are the creator of the event
 * @author Tae Hyon Lee
 *
 */
public class EventDetailServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(EventDetailServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * Checks for event ID parameter. If it is a valid id, then it will show details for the event. If it not valid, then it will bring the user to the event list path
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		if (httpSession != null) {
			String id = request.getParameter("id");
			String message = request.getParameter("m");
			int userId = (int)(Integer) httpSession.getAttribute("id");
			if (id != null) {
				ResponseConstants.okay(response);
				PrintWriter out = response.getWriter();
				try {
					Event event = manager.getEvent(Integer.parseInt(id), userId);
					if (event != null) {
						if (message != null) {
							out.println(HTMLForm.viewEvent(event, "Failed to remove the event"));
						}
						else {
							out.println(HTMLForm.viewEvent(event));
						}
					}
					else {
						ResponseConstants.redirect(response);
						response.setHeader("Location", "/eventlist");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("ERROR while displaying event");
				}
			}
			else {
				ResponseConstants.redirect(response);
				response.setHeader("Location", "/eventlist");
			}
		}
		else {
			ResponseConstants.redirect(response);
			response.setHeader("Location", "/login");
		}
	}
	
	/**
	 * Gets the parameters and redirects the user to appropriate paths
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		ResponseConstants.redirect(response);
		if(httpSession != null){
			String purchase = request.getParameter("purchaseticket");
			String modify = request.getParameter("modify");
			String remove = request.getParameter("remove");
			String id = request.getParameter("id");
			if (purchase != null && id != null) {
				response.setHeader("Location", "/purchaseticket?id=" + id);
			}
			else if (modify != null && id != null) {
				response.setHeader("Location", "/modifyevent?id=" + id);
			}
			else if (remove != null && id != null) {
				response.setHeader("Location", "/removeevent?id=" + id);
			}
		}
		else {
			response.setHeader("Location", "/login");
		}
	}
}
