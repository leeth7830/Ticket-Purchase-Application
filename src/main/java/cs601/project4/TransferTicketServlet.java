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
 * Transfer Ticket Servlet that lets the current user transfer tickets to another user
 * @author Tae Hyon Lee
 *
 */
public class TransferTicketServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(EventDetailServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * If the event id is valid, then display the transfer ticket form
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		ResponseConstants.redirect(response);
		if (httpSession != null) {
			String id = request.getParameter("id");
			int userId = (int)(Integer) httpSession.getAttribute("id");
			if (id != null) {
				ResponseConstants.okay(response);
				PrintWriter out = response.getWriter();
				try {
					int numTickets = manager.checkNumTickets(userId, (int) Integer.parseInt(id));
					if (numTickets != -1) {
						out.println(HTMLForm.transferTicket(manager.getEvent(Integer.parseInt(id), userId)));
					}
					else {
						ResponseConstants.redirect(response);
						response.setHeader("Location", "/userinfo");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("ERROR while displaying event");
				}
			}
			else {
				response.setHeader("Location", "/userinfo");
			}
		}
		else {
			response.setHeader("Location", "/login");
		}
	}
	
	/**
	 * Transfers tickets to a specified user
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		if(httpSession != null){
			int userId = (int)(Integer) httpSession.getAttribute("id");
			String transfer = request.getParameter("transfer");
			String eventId = request.getParameter("id");
			String receivingUser = request.getParameter("username");
			String numTickets = request.getParameter("number");
			if (transfer != null && eventId != null && receivingUser != null && numTickets!=null) {
				try {
					ResponseConstants.okay(response);
					PrintWriter out = response.getWriter();
					int result = manager.transferTicket(userId, receivingUser, Integer.parseInt(eventId), Integer.parseInt(numTickets));
					if (result == 1) {
						out.println(HTMLForm.transferTicket(manager.getEvent(Integer.parseInt(eventId), userId), "Sucessfully transferred!"));
					}
					else if (result == -1) {
						out.println(HTMLForm.transferTicket(manager.getEvent(Integer.parseInt(eventId), userId), "User does not exist"));
					}
					else if (result == -2) {
						out.println(HTMLForm.transferTicket(manager.getEvent(Integer.parseInt(eventId), userId), "You do not have that many tickets!"));
					}
					else {
						out.println(HTMLForm.transferTicket(manager.getEvent(Integer.parseInt(eventId), userId), "You cannot send to yourself"));
					}
				} catch (NumberFormatException | SQLException e) {
					ResponseConstants.redirect(response);
					response.setHeader("Location", "/");
					e.printStackTrace();
				}
			}
		}
		else {
			ResponseConstants.redirect(response);
			response.setHeader("Location", "/login");
		}
	}
}
