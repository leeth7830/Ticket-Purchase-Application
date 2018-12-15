package cs601.project4;

/**
 * Config object for holding variables from config.json file
 * @author Tae Hyon Lee
 *
 */
public class Config {
	private int serverPort;
	private String username;
	private String password;
	private String db;
	private String url;
	
	/**
	 * Constructor for Config object
	 * @param serverPort server port for server
	 * @param username username for database
	 * @param password password for database
	 * @param db database name
	 * @param url url link for SQL database
	 */
	public Config(int serverPort, String username, String password, String db, String url){
		this.setServerPort(serverPort);
		this.username = username;
		this.password = password;
		this.db = db;
		this.url = url;
	}
	
	//Getter and setters for the variables above
	
	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
