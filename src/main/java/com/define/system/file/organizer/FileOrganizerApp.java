package com.define.system.file.organizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.define.system.file.organizer.app.DocumentFileOrganizerApp;
import com.define.system.file.organizer.app.PhotoFileOrganizerApp;
import com.define.system.file.organizer.app.VideoFileOrganizerApp;

import lombok.extern.slf4j.Slf4j;

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
 * The Main class to call all the apps
 * 
 * @author Nitesh Apte
 * @version 0.1
 * @since 0.1
 */
@Slf4j
@SpringBootApplication
@ComponentScan("com.define.system.file.organizer*")
@PropertySource("file:./inputprop/file-organizer.properties")
public class FileOrganizerApp implements CommandLineRunner {
	
	@Autowired
	PhotoFileOrganizerApp photoFileOrganizerApp;
	
	@Autowired
	VideoFileOrganizerApp videoFileOrganizerApp;
	
	@Autowired
	DocumentFileOrganizerApp documentFileOrganizerApp;
	
	/**
	 * The main method. Calls are strategy based
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		log.info("File Organizer App Initiated.");
		SpringApplication.run(FileOrganizerApp.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		// Photo
		photoFileOrganizerApp.init();
		
		// Video
		videoFileOrganizerApp.init();
		
		// Document
		documentFileOrganizerApp.init();
	}
}