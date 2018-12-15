package cs601.project4;

import javax.servlet.http.HttpServletResponse;

/**
 * Response constants for redirect and valid page
 * @author Tae Hyon Lee
 *
 */
public class ResponseConstants {
	public static final void redirect(HttpServletResponse response) {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
	}
	
	public static final void okay(HttpServletResponse response) {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
}
