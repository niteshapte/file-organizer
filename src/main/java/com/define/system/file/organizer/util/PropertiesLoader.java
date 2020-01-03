package com.define.system.file.organizer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The Class PropertiesLoader.
 */
public class PropertiesLoader {

	/** The instance. */
	private static PropertiesLoader instance = null;
	
	/** The properties. */
	private Properties properties;

	/**
	 * Gets the single instance of PropertiesLoader.
	 *
	 * @return single instance of PropertiesLoader
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public synchronized static PropertiesLoader getInstance() {
		return instance != null ? instance : new PropertiesLoader();
	}
	

	/**
	 * Load file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadFile(String path) {
		properties = new Properties();
		FileInputStream file;
		
		try {
			file = new FileInputStream(path);
			properties.load(file);
			file.close();
		} catch (IOException e) {
			System.err.println("Failed to load properties file.");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the value.
	 *
	 * @param key the key
	 * @return the value
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}
}