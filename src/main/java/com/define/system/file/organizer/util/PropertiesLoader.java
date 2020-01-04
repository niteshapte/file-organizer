package com.define.system.file.organizer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * The Class PropertiesLoader. Loader the user input properties
 * 
 * @author Nitesh Apte
 * @version 0.1
 * @since 0.1
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