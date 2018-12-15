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
 * Modify Event Servlet that allows the creators to modify their events
 * @author Tae Hyon Lee
 *
 */
public class ModifyEventServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(ModifyEventServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * gets the ID for the event and checks if the current user is the creator for the event
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		ResponseConstants.redirect(response);
		if (httpSession != null) {
			String eventId = request.getParameter("id");
			int userId = (int)(Integer) httpSession.getAttribute("id");
			if (eventId != null && eventId.matches("[0-9]+")) {				
				int id = Integer.parseInt(eventId);
				Event event = null;
				try {
					event = manager.getEvent(id, userId);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (event != null) {
					if (event.getCreatedBy() == userId) {
						ResponseConstants.okay(response);
						PrintWriter out = response.getWriter();
						out.println(HTMLForm.modifyEvent(event));
					}
					else {
						response.setHeader("Location", "/event?id=" + id);
					}
				}
				else {
					response.setHeader("Location", "/eventlist");
				}
			}
			else {
				response.setHeader("Location", "/eventlist");
			}
		}
		else {
			response.setHeader("Location", "/login");
		}
	}
	
	/**
	 * If the event's owner is the current user then modify the event
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		ResponseConstants.redirect(response);
		if(httpSession != null){
			String username = (String) httpSession.getAttribute("username");
			int userId = (int)(Integer) httpSession.getAttribute("id");
			String modify = request.getParameter("modify");
			String eventId = request.getParameter("id");
			String name = request.getParameter("name");
			String numTickets = request.getParameter("number");
			String date = request.getParameter("date");
			String description = request.getParameter("description");
			if (modify != null) {
				if (eventId != null && eventId.matches("[0-9]+")) {				
					int id = Integer.parseInt(eventId);
					Event event = null;
					try {
						event = manager.getEvent(id, userId);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (event != null) { //if the field is empty, keep the old values
						if (name.equals("")) {
							name = event.getName();
						}
						if (date.equals("")) {
							date = event.getDate();							
						}
						if (description.equals("")) {
							description = event.getDescription();
						}
						if (numTickets.equals("")) {
							numTickets = String.valueOf(event.getNumAvailable());
						}
						ResponseConstants.okay(response);
						PrintWriter out = response.getWriter();
 						try {
 							Event modifiedEvent = new Event(id, name, date, description, userId, Integer.parseInt(numTickets), event.getUsername());
							
							if (manager.modifyEvent(modifiedEvent)) {
								response.setHeader("Location", "/?m=" + "modify");
							}
							else {
								out.println(HTMLForm.modifyEvent(event, "Failed to modify"));
								response.setHeader("Location", "/eventlist");
							}
							
						} catch (NumberFormatException e) {
							out.println(HTMLForm.modifyEvent(event, "Failed to modify"));
							response.setHeader("Location", "/eventlist");
							e.printStackTrace();
						}
					}
					else {
						response.setHeader("Location", "/eventlist");
					}
				}
				else {
					response.setHeader("Location", "/eventlist");
				}
			}
		}
		else {
			response.setHeader("Location", "/login");
		}
	}
}
