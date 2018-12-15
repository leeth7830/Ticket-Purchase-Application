package cs601.project4;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

/**
 * SQL Manager that handles all communication with the database
 * @author Tae Hyon Lee
 *
 */
public class SQLManager {
	
	private Config config;
	private String dbusername;
	private String dbpassword;
	private String db;
	private String url;
	
	
	public SQLManager() {}
	//Singleton Pattern
	private static final SQLManager instance = new SQLManager();
	
	public static SQLManager getInstance() {
        return instance;
    }
	
	/**
	 * Initialize all config variables
	 * @param config
	 */
	public void setConfig(Config config) {
		this.config = config;
		this.dbusername = config.getUsername();
		this.dbpassword = config.getPassword();
		this.db = config.getDb();
		this.url = config.getUrl();
	}
	/**
	 * Create a connection to the databsae
	 * @return
	 * @throws SQLException
	 */
	public Connection connection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (Exception e) {
			System.err.println("Can't find driver");
			e.printStackTrace();
			System.exit(1);
		}
		// format "jdbc:mysql://[hostname][:port]/[dbname]"
		//note: if connecting through an ssh tunnel make sure to use 127.0.0.1 and
		//also to that the ports are set up correctly
		String urlString =  url + db;
		String timeZoneSettings = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(urlString+timeZoneSettings, this.dbusername, this.dbpassword);
		return con;
	}
	
	/**
	 * Generates all events in the database
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Event> getEventList() throws SQLException {
		ArrayList<Event> list = new ArrayList<Event>();
		Connection con = connection();
		String selectStmt = "SELECT a.*, b.username FROM event a LEFT JOIN user b ON a.created_by = b.id"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.execute();
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			Timestamp date = result.getTimestamp("date");
			String description = result.getString("description");
			int createdBy = result.getInt("created_by");
			int numTickets = result.getInt("available_num_tickets");
			String username = result.getString("username");
			list.add(new Event(id, name, date.toString(), description, createdBy, numTickets, username));
		}
		return list;
	}
	
	/**
	 * Searches for events associated with the query
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Event> searchEventList(String query) throws SQLException {
		ArrayList<Event> list = new ArrayList<Event>();
		Connection con = connection();
		String selectStmt = "SELECT a.*, username FROM event a LEFT JOIN user b ON a.created_by = b.id WHERE name LIKE ? OR description LIKE ? OR username LIKE ?"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		query = "%" + query + "%";
		stmt.setString(1, query);
		stmt.setString(2, query);
		stmt.setString(3, query);
		stmt.execute();
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			Timestamp date = result.getTimestamp("date");
			String description = result.getString("description");
			int createdBy = result.getInt("created_by");
			int numTickets = result.getInt("available_num_tickets");
			String username = result.getString("username");
			list.add(new Event(id, name, date.toString(), description, createdBy, numTickets, username));
		}
		return list;
	}
	
	/**
	 * Get all events associated with the user
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Event> getUserList(int userId) throws SQLException {
		ArrayList<Event> list = new ArrayList<Event>();
		Connection con = connection();
		String selectStmt = "SELECT a.*, b.*, c.username FROM event a RIGHT JOIN (SELECT event_id, SUM(CASE WHEN type = 'purchase' THEN num_tickets ELSE 0 END) as num_purchased, " +
		"SUM(CASE WHEN to_user_id = ? THEN num_tickets ELSE num_tickets*(-1) END) AS current_tickets FROM transaction " + 
		"WHERE from_user_id = ? OR to_user_id = ? GROUP BY event_id) b ON a.id = b.event_id LEFT JOIN user c ON a.created_by = c.id"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.setInt(1, userId);
		stmt.setInt(2, userId);
		stmt.setInt(3, userId);
		stmt.execute();
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			Timestamp date = result.getTimestamp("date");
			String description = result.getString("description");
			int createdBy = result.getInt("created_by");
			int numTickets = result.getInt("available_num_tickets");
			int purchase = result.getInt("num_purchased");
			int current = result.getInt("current_tickets");
			String username = result.getString("username");
			list.add(new Event(id, name, date.toString(), description, createdBy, numTickets, purchase, current, username));
		}
		return list;
	}
	
	/**
	 * Get full details about an event
	 * @param eventId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Event getEvent(int eventId, int userId) throws SQLException {
		Event event = null;
		Connection con = connection();
		String selectStmt = "SELECT a.*, b.*, c.username FROM event a LEFT JOIN (SELECT event_id, SUM(CASE WHEN type = 'purchase' THEN num_tickets ELSE 0 END) as num_purchased, " +
				"SUM(CASE WHEN to_user_id = ? THEN num_tickets ELSE num_tickets*(-1) END) AS current_tickets FROM transaction " + 
				"WHERE (from_user_id = ? OR to_user_id = ?) GROUP BY event_id) b ON a.id = b.event_id LEFT JOIN user c ON a.created_by = c.id WHERE a.id = ?"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.setInt(1, userId);
		stmt.setInt(2, userId);
		stmt.setInt(3, userId);
		stmt.setInt(4, eventId);
		stmt.execute();
		ResultSet result = stmt.executeQuery();
		if (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			Date date = result.getDate("date");
			String description = result.getString("description");
			int createdBy = result.getInt("created_by");
			int numTickets = result.getInt("available_num_tickets");
			int purchase = result.getInt("num_purchased");
			int current = result.getInt("current_tickets");
			String username = result.getString("username");
			event = new Event(id, name, date.toString(), description, createdBy, numTickets, purchase, current, username);
		}
		return event;
	}
	
	/**
	 * Inserts into event table with the specified variables
	 * @param name
	 * @param description
	 * @param numTickets
	 * @param date
	 * @param user
	 * @throws SQLException
	 */
	public void createEvent(String name, String description, int numTickets, Date date, int user) throws SQLException {
		Connection con = connection();
		String selectStmt = "INSERT INTO event (name, description, date, available_num_tickets, created_by) VALUES (?, ?, ?, ?, ?)"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.setString(1, name);
		stmt.setString(2, description);
		stmt.setDate(3, date);
		stmt.setInt(4, numTickets);
		stmt.setInt(5, user);
		stmt.execute();	
	}
	
	/**
	 * Purchases tickets for the user specified by creating a new purchase transaction
	 * @param userId
	 * @param eventId
	 * @param numTickets
	 * @return
	 * @throws SQLException
	 */
	public boolean purchaseTicket(int userId, int eventId, int numTickets) throws SQLException {
		Connection con = connection();
		String selectStmt = "SELECT available_num_tickets FROM event WHERE id = ?"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.setInt(1, eventId);
		stmt.execute();	
		ResultSet result = stmt.executeQuery();
		if (result.next()) {
			int numTicketsQuery = result.getInt("available_num_tickets");
			if (numTicketsQuery >= numTickets) {
				String updateStmt = "UPDATE event SET available_num_tickets = available_num_tickets - ? WHERE id = ?";
				PreparedStatement update = con.prepareStatement(updateStmt);
				update.setInt(1, numTickets);
				update.setInt(2, eventId);
				update.execute();	
				String insertStmt = "INSERT INTO transaction (type, from_user_id, to_user_id, date, num_tickets, event_id) VALUES ('purchase', -99, ?, NOW(), ?, ?)"; 
				PreparedStatement insert = con.prepareStatement(insertStmt);
				insert.setInt(1, userId);
				insert.setInt(2, numTickets);
				insert.setInt(3, eventId);
				insert.execute();	
				con.close();
				return true;
			}
			else {
				con.close();
				return false;
			}
		}
		else {
			con.close();
			return false;
		}
	}
	
	/**
	 * Transfers the ticket from the current user to a specified user for a certain event
	 * @param userId
	 * @param receivingUser
	 * @param eventId
	 * @param numTickets
	 * @return
	 * @throws SQLException
	 */
	public int transferTicket(int userId, String receivingUser, int eventId, int numTickets) throws SQLException {
		Connection con = connection();
		String selectStmt = "SELECT id FROM user WHERE username = ?"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.setString(1, receivingUser);
		stmt.execute();	
		ResultSet result = stmt.executeQuery();
		if (result.next()) {
			if (result.getInt("id") == userId) {
				return -3; //sending to yourself
			}
			String chceckStmt = "SELECT SUM(CASE WHEN to_user_id = ? THEN num_tickets ELSE num_tickets*(-1) END) AS current_tickets FROM transaction " + 
					"WHERE event_id = ? AND (from_user_id = ? OR to_user_id = ?) GROUP BY event_id";
			PreparedStatement stmt2 = con.prepareStatement(chceckStmt);
			stmt2.setInt(1, userId); 
			stmt2.setInt(2, eventId);
			stmt2.setInt(3, userId);
			stmt2.setInt(4, userId);
			stmt2.execute();	
			ResultSet result2 = stmt2.executeQuery();
			if (result2.next()) {
				int numTicketsQuery = result2.getInt("current_tickets");
				if (numTicketsQuery >= numTickets) {
					String insertStmt = "INSERT INTO transaction (type, from_user_id, to_user_id, date, num_tickets, event_id) VALUES ('transfer', ?, (SELECT id FROM user WHERE username = ?), NOW(), ?, ?)"; 
					PreparedStatement insert = con.prepareStatement(insertStmt);
					insert.setInt(1, userId);
					insert.setString(2, receivingUser);
					insert.setInt(3, numTickets);
					insert.setInt(4, eventId);
					insert.execute();	
					con.close();
					return 1; //successful
				}
				else {
					con.close();
					return -2; //you do not have enough tickets
				}
			}
			else {
				con.close();
				return -2; //you do not have enough tickets
			}
			
		}
		else {
			con.close();
			return -1; //the user does not exist
		}
		
	}
	
	/**
	 * Checks number of ticket the user current holds for the event
	 * @param userId
	 * @param eventId
	 * @return
	 * @throws SQLException
	 */
	public int checkNumTickets(int userId, int eventId) throws SQLException {
		Connection con = connection();
		String selectStmt = "SELECT SUM(CASE WHEN to_user_id = ? THEN num_tickets ELSE num_tickets*(-1) END) AS current_tickets FROM transaction WHERE event_id = ? AND (from_user_id = ? OR to_user_id = ?)"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.setInt(1, userId);
		stmt.setInt(2, eventId);
		stmt.setInt(3, userId);
		stmt.setInt(4, userId);
		stmt.execute();	
		ResultSet result = stmt.executeQuery();
		if (result.next()) {
			int numTickets = result.getInt("current_tickets");
			con.close();
			return numTickets;
		}
		else {
			con.close();
			return -1;
		}
		
	}
	
	/**
	 * Checks if the username is already taken
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public boolean checkExistingUser(String username) throws SQLException { 
		Connection con = connection();
		String selectStmt = "SELECT 1 FROM user WHERE username = ?"; 
		//create a statement object
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.setString(1, username);
		stmt.execute();
		ResultSet result = stmt.executeQuery();
		if (result.next()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Creates a new user
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 * @throws SQLException
	 */
	public int createUser(String username, String password, String salt) throws SQLException {
		Connection con = connection();
		String insertStmt = "INSERT INTO user (username, password, salt) VALUES (?, ?, ?)"; 
		PreparedStatement stmt = con.prepareStatement(insertStmt);
		stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.setString(3, salt);
		stmt.execute();
		String selectStmt = "SELECT id FROM user where username = ?"; 
		PreparedStatement stmt2 = con.prepareStatement(selectStmt);
		stmt2.setString(1, username);
		stmt2.execute();
		ResultSet result = stmt2.executeQuery();
		if (result.next()) {
			int id = result.getInt("id");
			return id;
		}
		else {
			return -1;
		}
		
	}
	
	/**
	 * Modifies an event
	 * @param event
	 * @return
	 */
	public boolean modifyEvent(Event event) {
		try {
			Connection con = connection();
			String updateStmt = "UPDATE event SET name = ?, description = ?, date = ?, available_num_tickets = ? WHERE id = ?";
			PreparedStatement update = con.prepareStatement(updateStmt);
			update.setString(1, event.getName());
			update.setString(2, event.getDescription());
			System.out.println(event.getDate());
			update.setDate(3, Date.valueOf(event.getDate()));
			update.setInt(4, event.getNumAvailable());
			update.setInt(5, event.getId());
			update.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Removes an event
	 * @param eventId
	 * @return
	 */
	public boolean removeEvent(int eventId) {
		try {
			Connection con = connection();
			String updateStmt = "DELETE FROM event WHERE id = ?";
			PreparedStatement update = con.prepareStatement(updateStmt);
			update.setInt(1, eventId);
			update.execute();
			String deleteStmt = "DELETE from transaction where event_id = ?";
			PreparedStatement delete = con.prepareStatement(deleteStmt);
			delete.setInt(1, eventId);
			delete.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Authenticates the user to see if the user has already signed up before
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public int authenticate(String username, String password) throws SQLException, UnsupportedEncodingException {
		
		Connection con = connection();
		String selectStmt = "SELECT id, password, salt FROM user WHERE username = ?"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		stmt.setString(1, username);
		stmt.execute();
		ResultSet result = stmt.executeQuery();
		
		if (result.next()) {
			String tempPassword = result.getString("password");
			String salt = result.getString("salt");
			char[] charPassword = password.toCharArray();
			byte[] byteSalt = Base64.getDecoder().decode(salt);
			byte[] bytePassword = Base64.getDecoder().decode(tempPassword);
			
			if (Password.isExpectedPassword(charPassword, byteSalt, bytePassword))
			{
				int id = result.getInt("id");
				con.close();
				return id;
			}
			con.close();
			return -1;
		}
		else {
			con.close();
			return -1;
		}		
	}
	
}
