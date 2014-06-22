package uy.edu.ort.arqliv.obligatorio.client.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;


public class MainSingleton {
	private static MainSingleton instance = new MainSingleton();
	
	private final Logger log = LoggerFactory.getLogger(MainSingleton.class);

	private Properties prop;

	private MainSingleton(){
		
		prop = new Properties();
		InputStream input = null;
	
		try {

			String filename = "config.properties";
			input = ContextSingleton.class.getClassLoader()
					.getResourceAsStream(filename);
			prop.load(input);

		} catch (IOException ex) {
			log.error("IOException", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error("IOException finally", e);
				}
			}
		}
		
		
	}
	
	private String user = "dummy";
	
	public static MainSingleton getInstance(){
		return instance;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getProperty(String key, String defaultValue){
		return prop.getProperty(key, defaultValue);
	}
	public String getProperty(String key){
		return prop.getProperty(key);
	}
	
	
}
