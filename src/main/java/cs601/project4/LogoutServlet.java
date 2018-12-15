package cs601.project4;

import java.io.IOException;  
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

/**
 * Log out servlet that lets the user log out
 * @author Tae Hyon lee
 *
 */
public class LogoutServlet extends HttpServlet {  
	private Logger logger = Logger.getLogger(LogoutServlet.class.getName());
	/**
	 * Invalidates the session
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        ResponseConstants.okay(response);
        PrintWriter out=response.getWriter();  
        out.print(HTMLForm.logout());
        HttpSession session = request.getSession();  
        session.invalidate();   
    }  
    
    /**
     * When pressed home button, redirect the user to login
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession(false); 
		String home = request.getParameter("home");
		if (home != null) {
			ResponseConstants.redirect(response);
			response.setHeader("Location", "/login");
		}	    
	}
}  