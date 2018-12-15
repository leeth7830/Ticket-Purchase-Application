package cs601.project4;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

/**
 * Configuration Manager that reads the config.json file and create a Config object
 * @author Taehyon
 *
 */
public class ConfigurationManager {
	private Config data;
	
	/**
	 * constructor for configuration manager that calls the readConfig method
	 */
	public ConfigurationManager(){
		readConfig();
	}
	
	/**
	 * reads config file
	 */
	public void readConfig() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("config.json")));
			Gson gson = new Gson();
			String currentLine = bufferedReader.readLine();
			data = gson.fromJson(currentLine, Config.class);
		} 
		catch (FileNotFoundException e) {
			System.err.println("Config file is not found");
		} catch (IOException e) {
			System.err.println("COULD NOT READ CONFIGURATION LINE");
		}
	}
	
	
	/**
	 * returns the config object
	 * @return config object
	 */
	public Config getConfig() {
		return data;
	}
	
}
