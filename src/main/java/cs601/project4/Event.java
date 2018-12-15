package cs601.project4;

/**
 * Event object that stores all of the details about an event
 * @author Tae Hyon Lee
 *
 */
public class Event {
	private int id;
	private String name;
	private String date;
	private String description;
	private int createdBy;
	private int numAvailable;
	private int numOwned;
	private int numPurchased;
	private String username;
	
	/**
	 * Constructor for Event object that does not take in number owned or purchased
	 * @param id
	 * @param name
	 * @param date
	 * @param description
	 * @param createdBy
	 * @param numAvailable
	 * @param username
	 */
	public Event(int id, String name, String date, String description, int createdBy, int numAvailable, String username) {
		this.setId(id);
		this.setName(name);
		this.setDate(date);
		this.setDescription(description);
		this.setCreatedBy(createdBy);
		this.setNumAvailable(numAvailable);
		this.setUsername(username);
	}
	
	/**
	 * Constructor for event object that does not take in number purchased
	 * @param id
	 * @param name
	 * @param date
	 * @param description
	 * @param createdBy
	 * @param numAvailable
	 * @param numOwned
	 * @param username
	 */
	public Event(int id, String name, String date, String description, int createdBy, int numAvailable, int numOwned, String username) {
		this.setId(id);
		this.setName(name);
		this.setDate(date);
		this.setDescription(description);
		this.setCreatedBy(createdBy);
		this.setNumAvailable(numAvailable);
		this.setNumOwned(numOwned);
		this.setUsername(username);
	}
	
	/**
	 * Constructor for the full object that takes in all of the information related to the event and the user
	 * @param id
	 * @param name
	 * @param date
	 * @param description
	 * @param createdBy
	 * @param numAvailable
	 * @param numPurchased
	 * @param numOwned
	 * @param username
	 */
	public Event(int id, String name, String date, String description, int createdBy, int numAvailable, int numPurchased, int numOwned, String username) {
		this.setId(id);
		this.setName(name);
		this.setDate(date);
		this.setDescription(description);
		this.setCreatedBy(createdBy);
		this.setNumAvailable(numAvailable);
		this.setNumOwned(numOwned);
		this.setNumPurchased(numPurchased);
		this.setUsername(username);
	}

	//Getter and setters for above variables
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumAvailable() {
		return numAvailable;
	}

	public void setNumAvailable(int numAvailable) {
		this.numAvailable = numAvailable;
	}

	public int getNumOwned() {
		return numOwned;
	}

	public void setNumOwned(int numOwned) {
		this.numOwned = numOwned;
	}

	public int getNumPurchased() {
		return numPurchased;
	}

	public void setNumPurchased(int numPurchased) {
		this.numPurchased = numPurchased;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
