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
 * Purchase Ticket Servlet that lets a user purchase tickets for an event
 * @author Tae Hyon Lee
 *
 */
public class PurchaseTicketServlet extends HttpServlet{
	private Logger logger = Logger.getLogger(PurchaseTicketServlet.class.getName());
	private final SQLManager manager = SQLManager.getInstance();
	
	/**
	 * If the event id is valid then show purchase form
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		ResponseConstants.redirect(response);
		if (httpSession != null) {
			int userId = (int)(Integer) httpSession.getAttribute("id");
			String id = request.getParameter("id");
			if (id != null) {
				ResponseConstants.okay(response);
				PrintWriter out = response.getWriter();
				try {
					out.println(HTMLForm.purchaseTicket(manager.getEvent(Integer.parseInt(id), userId)));
				} catch (SQLException e) {
					System.err.println("ERROR while generating eventlist");
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
	 * If the amount specified is less than the available ticket, then purchase the ticket
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		if(httpSession != null){
			int userId = (int) (Integer) (httpSession.getAttribute("id"));
			String purchase = request.getParameter("purchaseticket");
			String numTickets = request.getParameter("number");
			String eventId = request.getParameter("id");
			if (purchase != null && !numTickets.equals("") && !eventId.equals("")) {
				try {
					if (numTickets.equals("") || Integer.parseInt(numTickets) < 1) {
						ResponseConstants.okay(response);
						PrintWriter out = response.getWriter();
						try {
							out.println(HTMLForm.purchaseTicket(manager.getEvent(Integer.parseInt(eventId), userId), "You have to purchase atleast one ticket!"));
						} catch (SQLException e) {
							System.err.println("ERROR while generating eventlist");
							response.setHeader("Location", "/");
						}
					}
					else {
						if (manager.purchaseTicket(userId, Integer.parseInt(eventId), Integer.parseInt(numTickets)))
						{
							ResponseConstants.redirect(response);
							response.setHeader("Location", "/");
						}
						else {
							ResponseConstants.okay(response);
							PrintWriter out = response.getWriter();
							try {
								out.println(HTMLForm.purchaseTicket(manager.getEvent(Integer.parseInt(eventId), userId), "There are not enough tickets!"));
							} catch (SQLException e) {
								System.err.println("ERROR while generating eventlist");
								response.setHeader("Location", "/");
							}
						}
					}
				} catch (NumberFormatException | SQLException e) {
					System.err.println("Error while purchasing ticket");
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
