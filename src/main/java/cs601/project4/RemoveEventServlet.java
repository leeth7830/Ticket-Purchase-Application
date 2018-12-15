package cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Remove event Servlet that lets the creator remove events
 * @author Tae Hyon Lee
 *
 */
public class RemoveEventServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(RemoveEventServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * Shows a confirmation page to creators if they want to delete the event
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
					response.setHeader("Location", "/eventlist");
				}
				
				if (event != null) {
					if (event.getCreatedBy() == userId) {
						ResponseConstants.okay(response);
						PrintWriter out = response.getWriter();
						out.println(HTMLForm.removeEvent(event));
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
	 * If confirmed is pressed, then remove the event and all associated transaction. If cancelled is pressed, then redirect to previous event
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		ResponseConstants.redirect(response);
		if(httpSession != null){
			String username = (String) httpSession.getAttribute("username");
			int userId = (int)(Integer) httpSession.getAttribute("id");
			String remove = request.getParameter("confirm");
			String cancel = request.getParameter("cancel");
			String eventId = request.getParameter("id");
			if (eventId != null && eventId.matches("[0-9]+")) {				
				int id = Integer.parseInt(eventId);
				Event event = null;
				try {
					event = manager.getEvent(id, userId);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (event != null) { 
					if (remove != null) { 
						if (manager.removeEvent(id)) {
							response.setHeader("Location", "/?m=remove");
						}
						else {
							response.setHeader("Location", "/event?id=" + id + "&m=removefail");
						}
					}
					else if (cancel != null){
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
}
