package cs601.project4;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * HTML Constants that are displayed on user's browser
 * @author Tae Hyon Lee
 *
 */
public class HTMLForm {
	/**
	 * Login HTML page with error message
	 * @param error
	 * @return
	 */
	public static final String loginForm(String error) {
		return "<html><title>Login</title><body>" + 
				"<script type='text/javascript'>" + 
				"alert(" + "'" + error + "'" + ");</script>" +
				"<form name='loginform' onsubmit='validateForm()' method='post'>" +
				"<label>Username</label><br>" +
				" <input type='text' name='username' placeholder='username'><br>" + 
				"<label>Password</label><br>" + 
				"<input type='password' name='password' placeholder='password'><br>" + 
				"<input type='submit' value='Login' onSubmit='validateForm();' /><br>" +
				"<input type='submit' value='Sign Up' name='signup');' />" +
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Login HTML Page without error message
	 * @return
	 */
	public static final String loginForm() {
		return "<html><title>Login</title><body>" +			
				"<form name='loginform' onsubmit='validateForm()' method='post'>" +
				"<label>Username</label><br>" +
				" <input type='text' name='username' placeholder='username'><br>" + 
				"<label>Password</label><br>" + 
				"<input type='password' name='password' placeholder='password'><br>" + 
				"<input type='submit' value='Login' onSubmit='validateForm();' /><br>" +
				"<input type='submit' value='Sign Up' name='signup');' />" +
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Sign up HTML Page with error message
	 * @param error
	 * @return
	 */
	public static final String signUpForm(String error) {
		return "<html><title>Sign up</title><body>" + 
				"<script type='text/javascript'>" + 
				"alert(" + "'" + error + "'" + ");</script>" +
				"<form name='signupform' onsubmit='validateForm()' method='post'>" +
				"<label>Username</label><br>" +
				"<input type='text' name='username' pattern='([a-zA-Z0-9]){5,}$' title='letters and numbers only. Five or more letters long'><br>" + 
				"<label>Password </label><br>" + 
				"<input type='password' name='password' id='password' pattern='^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{7,}$' " + 
				"required title='Minimum of 7 characters. Should have at least one special character and one number and one UpperCase Letter.'><br>" + 
				"<label>Confirm Password</label><br>" + 
				"<input type='password' required='required' name='confirm' oninput='check(this)'><br>" + 
				"<script language='javascript' type='text/javascript'>" +
				" function check(input) { " +
				"if (input.value != document.getElementById('password').value) {" +
				"input.setCustomValidity('Password Must be Matching.');" + 
				"} else { " +
				"input.setCustomValidity('');" + 
				" } } </script>" + 
				"<input type='submit' value='Sign Up!' onSubmit='validateForm();' /><br>" +
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Sign up HTML page without error message
	 * @return
	 */
	public static final String signUpForm() {
		return "<html><title>Sign up</title><body>" + 
				"<form name='signupform' onsubmit='validateForm()' method='post'>" +
				"<label>Username</label><br>" +
				"<input type='text' name='username' pattern='([a-zA-Z0-9]){5,}$' title='letters and numbers only. Five or more letters long'><br>" + 
				"<label>Password </label><br>" + 
				"<input type='password' name='password' id='password' pattern='^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{7,}$' " + 
				"required title='Minimum of 7 characters. Should have at least one special character and one number and one UpperCase Letter.'><br>" + 
				"<label>Confirm Password</label><br>" + 
				"<input type='password' required='required' name='confirm' oninput='check(this)'><br>" + 
				"<script language='javascript' type='text/javascript'>" +
				" function check(input) { " +
				"if (input.value != document.getElementById('password').value) {" +
				"input.setCustomValidity('Password Must be Matching.');" + 
				"} else { " +
				"input.setCustomValidity('');" + 
				" } } </script>" + 
				"<input type='submit' value='Sign Up!' onSubmit='validateForm();' /><br>" +
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * home HTML page with error message
	 * @param username
	 * @param message
	 * @return
	 */
	public static final String homePage(String username, String message) {
		return "<html><title>Home</title><body>Hello, " + username + "!<br/> " +
				"<script type='text/javascript'>" + 
				"alert(" + "'" + message + "'" + ");</script>" +
				"<form name='homeform' onsubmit='validateForm()' method='post'>" +
				"<input type='submit' value='Create Event' name='createvent'/><br/>" + 
				"<input type='submit' value='View Event' name='viewevent'/><br/>" + 
				"<input type='submit' value='View User Info' name='viewuser'/><br/>" + 
				"<input type='submit' value='Logout' name='logout' onSubmit='validateForm()'/><br/>" + 
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * home HTML without error message
	 * @param username
	 * @return
	 */
	public static final String homePage(String username) {
		return "<html><title>Home</title><body>Hello, " + username + "!<br/> " +
				"<form name='homeform' onsubmit='validateForm()' method='post'>" +
				"<input type='submit' value='Create Event' name='createvent'/><br/>" + 
				"<input type='submit' value='View Event' name='viewevent'/><br/>" + 
				"<input type='submit' value='View User Info' name='viewuser'/><br/>" + 
				"<input type='submit' value='Logout' name='logout' onSubmit='validateForm()'/><br/>" + 
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * logout HTML
	 * @return
	 */
	public static final String logout() {
		return "<html><title>Home</title><body> You have successfully logged out!<br/> " +
				"<form name='logoutform' onsubmit='validateForm()' method='post'>" +
				"<input type='submit' value='Home' name='home' onSubmit='validateForm()'/><br/>" + 
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Create event HTML that shows create event form
	 * @return
	 */
	public static final String createEvent() {
		return "<html><title>Create Event</title><body>" + 
				"<form name='createventform' method='post'>" +
				"<label>Name</label><br>" +
				"<input type='text' name='name' placeholder='name' pattern='([a-zA-Z0-9 _]){5,}$' title='letters and numbers only. Five or more letters long'><br>" +
				"<label>date</label><br>" + 
				"<input type='date' name='date'><br>" + 
				"<label>Description</label><br>" + 
				"<input type='text' name='description' placeholder='description'><br>" + 
				"<label>Number of Tickets</label><br>" + 
				"<input type='number' min='0' step='1' name='number' value='' pattern='^(0*([0-9]|[1-8][0-9]|9[0-9]|100))$' title='Enter Valid value'/><br>" +
				"<input type='submit' value='Create' name='create' />" +
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Event List HTML that shows all of the events in the list
	 * @param list
	 * @return
	 */
	public static final String eventList(ArrayList<Event> list) {
		String html = "<html><title>Event List</title><body>Event List<br/> " + 
					"<form name='searchform' method='post'>" +
					"<input type='text' name='term' placeholder='search' pattern='([a-zA-Z0-9 _]){3,}$' title='letters and numbers only. Three or more letters long' required>" + 
					"<input type='submit' value='Search' name='search' />" + 
					"</form>" +
					"<form name='showallform' method='post'>" +
					"<input type='submit' value='Show All' name='showall' />" +
					"</form>" +
					"<table style='width:100%' style='float:right' border='1'>" + 
					"<th>ID</th><th>Event Name</th><th>Event Date</th><th>Number of Available Tickets</th>";
		for (Event event : list) {
			html = html + "<tr><td>" + event.getId() + "</td><td>" + "<a href='/event?id=" + event.getId() + "'>" + event.getName() + "</a>" +
					"</td><td>" + event.getDate() + "</td><td>" + event.getNumAvailable() + "</td>";
		}
		html = html + "</table></body></html>";
		return html;
	}
	
	/**
	 * User detail page that shows user details and all event details that the user purchased or owns tickets to
	 * @param list
	 * @param user
	 * @return
	 */
	public static final String userDetail(ArrayList<Event> list, String user) {
		String html = "<html><title>User Detail</title><body>" + user + "<br/> <table style='width:100%' style='float:right' border='1'>" + 
					"<th>ID</th><th>Event Name</th><th>Event Date</th><th>Event Details</th><th>Created By</th><th>Number of Available Tickets</th><th>Number of Tickets Purchased</th>" +
					"<th>Number of Tickets Owned</th> <th>Transfer?</th>";
		for (Event event : list) {
			html = html + "<tr><td>" + event.getId() + "</td><td>" + "<a href='/event?id=" + event.getId() + "'>" + event.getName() + "</a>" +
					"</td><td>" + event.getDate() + "</td><td>"  + event.getDescription() + "</td><td>"+ event.getUsername() + "</td><td>" + event.getNumAvailable() + "</td><td>" + 
					event.getNumPurchased() + "</td><td>" + event.getNumOwned() + "</td><td>" + 
					"<form name='purchase' method='post'>" + 
					"<input type='hidden' value='" + event.getId() + "' name='event_id'/>" +
					"<input type='submit' value='Transfer' name='transfer'/>" +
					"</form></td>";
		}
		html = html + "</table></body></html>";
		return html;
	}
	
	/**
	 * Event details HTML page that shows an error message
	 * @param event
	 * @param message
	 * @return
	 */
	public static final String viewEvent(Event event, String message) {
		String html = "<html><title>Event</title><body>" + 
				"<script type='text/javascript'>" + 
				"alert(" + "'" + message + "'" + ");</script>" +
				"Event<br/> <table style='width:100%' style='float:right' border='1'>" + 
				"<th>ID</th><th>Event Name</th><th>Event Date</th><th>Description</th><th>Created By</th><th>Number of Available Tickets</th><th>Number of Tickets You Own</th>";
	
		html = html + "<tr><td>" + event.getId() + "</td><td>" + event.getName() +
				"</td><td>" + event.getDate() + "</td><td>" + event.getDescription() + "</td><td>" + event.getUsername() + "</td><td>" + 
				event.getNumAvailable() + "</td><td>" +  event.getNumOwned() + "</td></table>";
		html = html + "<form name='form' method='post'>" +
				"<input type='hidden' value='" + event.getId() + "' name='id'/><br/>" + 
				"<input type='submit' value='Purchase Ticket' name='purchaseticket'/>" + 
				"<input type='submit' value='Modify' name='modify'/>" + 
				"<input type='submit' value='Remove' name='remove'/>" + 
				"</form>";
		html = html + "</body></html>";
		return html;
	}
	
	
	/**
	 * Event details HTML page that does not have an error message
	 * @param event
	 * @return
	 */
	public static final String viewEvent(Event event) {
		String html = "<html><title>Event</title><body>Event<br/> <table style='width:100%' style='float:right' border='1'>" + 
				"<th>ID</th><th>Event Name</th><th>Event Date</th><th>Description</th><th>Created By</th><th>Number of Available Tickets</th><th>Number of Tickets You Own</th>";
	
		html = html + "<tr><td>" + event.getId() + "</td><td>" + event.getName() +
				"</td><td>" + event.getDate() + "</td><td>" + event.getDescription() + "</td><td>" + event.getUsername() + "</td><td>" + 
				event.getNumAvailable() + "</td><td>" +  event.getNumOwned() + "</td></table>";
		html = html + "<form name='purchaseForm' onsubmit='validateForm()' method='post'>" +
				"<input type='hidden' value='" + event.getId() + "' name='id'/><br/>" + 
				"<input type='submit' value='Purchase Ticket' name='purchaseticket'/>" + 
				"<input type='submit' value='Modify' name='modify'/>" + 
				"<input type='submit' value='Remove' name='remove'/>" + 
				"</form>";
		html = html + "</body></html>";
		return html;
	}
	
	/**
	 * Purchase Ticket HTML that displays a purchase form with an error message
	 * @param event
	 * @param message
	 * @return
	 */
	public static final String purchaseTicket(Event event, String message) {
		String html = "<html><title>Purchase</title><body>Purchase for " + event.getName() + "<br/>" +
				"<script type='text/javascript'>" + 
				"alert(" + "'" + message + "'" + ");</script>" +
				"<form name='purchaseForm' method='post'>" +
				"<label>Number of Tickets Available: " + event.getNumAvailable() + "</label><br>" + 
				"<input type='number' min='0' step='1' name='number' value='' pattern='^(0*([0-9]|[1-8][0-9]|9[0-9]|100))$' title='Enter Valid value' required/><br>" +
				"<input type='submit' value='Confirm Purchase' name='purchaseticket'/><br/>" + 
				"<input type='hidden' value='" + event.getId() + "' name = 'id'/><br/>" +
				"</form>" +
				"</body></html>";
		return html;
	}
	
	/**
	 * Purchase Ticket HTML that displays a purchase form without an error message
	 * @param event
	 * @return
	 */
	public static final String purchaseTicket(Event event) {
		String html = "<html><title>Purchase</title><body>Purchase for " + event.getName() + "<br/>" +
				"<form name='purchaseForm' method='post'>" +
				"<label>Number of Tickets Available: " + event.getNumAvailable() + "</label><br>" + 
				"<input type='number' min='0' step='1' name='number' value='' pattern='^(0*([0-9]|[1-8][0-9]|9[0-9]|100))$' title='Enter Valid value' required/><br>" +
				"<input type='submit' value='Confirm Purchase' name='purchaseticket'/><br/>" + 
				"<input type='hidden' value='" + event.getId() + "' name = 'id'/><br/>" +
				"</form>" +
				"</body></html>";
		return html;
	}
	
	/**
	 * Transfer Ticket HTML that lets user transfer tickets to another user
	 * @param event
	 * @param message
	 * @return
	 */
	public static final String transferTicket(Event event, String message) {
		return "<html><title> Transfer </title><body>Event:" + event.getName() + "<br/> " +
				"<script type='text/javascript'>" + 
				"alert(" + "'" + message + "'" + ");</script>" +
				"<form name='transferForm' method='post'>" +
				"<label>Number of Tickets Owned: " + event.getNumOwned() + "</label><br>" + 
				"<input type='number' min='0' step='1' name='number' value='' pattern='^(0*([0-9]|[1-8][0-9]|9[0-9]|100))$' title='Enter Valid value'/><br>" + 
				"<input type='hidden' value='" + event.getId() +"' name='id'/><br/>" + 
				"<label>Receiving User: </label><br>" + 
				"<input type='text' name='username' pattern='([a-zA-Z0-9]){5,}$' title='letters and numbers only. Five or more letters long'><br>" + 
				"<input type='submit' value='Transfer!' name='transfer'/><br/>" + 
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Transfer Ticket HTML that lets user transfer tickets to another user
	 * @param event
	 * @return
	 */
	public static final String transferTicket(Event event) {
		return "<html><title> Transfer </title><body>Event:" + event.getName() + "<br/> " +
				"<form name='transferForm' method='post'>" +
				"<label>Number of Tickets Owned: " + event.getNumOwned() + "</label><br>" + 
				"<input type='number' min='0' step='1' name='number' value='' pattern='^(0*([0-9]|[1-8][0-9]|9[0-9]|100))$' title='Enter Valid value' required/><br>" + 
				"<input type='hidden' value='" + event.getId() +"' name='id'/><br/>" + 
				"<label>Receiving User: </label><br>" + 
				"<input type='text' name='username' pattern='([a-zA-Z0-9]){5,}$' title='letters and numbers only. Five or more letters long' required><br>" + 
				"<input type='submit' value='Transfer!' name='transfer'/><br/>" + 
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Modify Event HTML that lets the creator modify the event
	 * @param event
	 * @param message
	 * @return
	 */
	public static final String modifyEvent(Event event, String message) {
		return "<html><title> Transfer </title><body>Event:" + event.getName() + "<br/> " +
				"<script type='text/javascript'>" + 
				"alert(" + "'" + message + "'" + ");</script>" +
				"<form name='modify' method='post'>" +
				"<label>Modifying Event: " + event.getName() + "</label><br>" + 
				"<label>New Name</label><br>" +
				"<input type='text' name='name' placeholder='name' pattern='([a-zA-Z0-9 _]){5,}$' title='letters and numbers only. Five or more letters long'><br>" +
				"<label>Previous Date: " + event.getDate() + "</label><br>" + 
				"<label>New Date</label><br>" + 
				"<input type='date' name='date'><br>" + 
				"<label>Previous Description: " + event.getDescription() + "</label><br>" + 
				"<label>New Description</label><br>" + 
				"<input type='text' name='description' placeholder='description'><br>" + 
				"<label>Previous number of tickets available: " + event.getNumAvailable() + "</label><br>" + 
				"<label>New Number of Tickets</label><br>" + 
				"<input type='number' min='0' step='1' name='number' value='' pattern='^(0*([0-9]|[1-8][0-9]|9[0-9]|100))$' title='Enter Valid value'/><br>" +
				"<input type='hidden' value='" + event.getId() + "' name='id' />" + 
				"<input type='submit' value='Modify' name='modify');' />" +
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Modify Event HTML that lets the creator modify the event
	 * @param event
	 * @return
	 */
	public static final String modifyEvent(Event event) {
		return "<html><title> Transfer </title><body>Event:" + event.getName() + "<br/> " +
				"<form name='modify' method='post'>" +
				"<label>Modifying Event: " + event.getName() + "</label><br>" + 
				"<label>New Name</label><br>" +
				" <input type='text' name='name' placeholder='name' pattern='([a-zA-Z0-9 _]){5,}$' title='letters and numbers only. Five or more letters long'><br>" +
				"<label>Previous Date: " + event.getDate() + "</label><br>" + 
				"<label>New Date</label><br>" + 
				"<input type='date' name='date'><br>" + 
				"<label>Previous Description: " + event.getDescription() + "</label><br>" + 
				"<label>New Description</label><br>" + 
				"<input type='text' name='description' placeholder='description'><br>" + 
				"<label>Previous number of tickets available: " + event.getNumAvailable() + "</label><br>" + 
				"<label>New Number of Tickets</label><br>" + 
				"<input type='number' min='0' step='1' name='number' value='' pattern='^(0*([0-9]|[1-8][0-9]|9[0-9]|100))$' title='Enter Valid value'/><br>" +
				"<input type='hidden' value='" + event.getId() + "' name='id' />" + 
				"<input type='submit' value='Modify' name='modify');' />" +
				"</form>" +
				"</body></html>";
	}
	
	/**
	 * Remove event HTML that lets the creator remove the event
	 * @param event
	 * @return
	 */
	public static final String removeEvent(Event event) {
		return "<html><title>Remove Event</title><body>Do you want to remove " + event.getName() + "?<br/> " +
				"<form name='transferForm' method='post'>" +
				"<input type='hidden' value='" + event.getId() + "' name=id/>" + 
				"<input type='submit' value='Confirm' name='confirm'/>" +
				"<input type='submit' value='Cancel' name='cancel'/><br/>" + 
				"</form>" +
				"</body></html>";
	}
}
