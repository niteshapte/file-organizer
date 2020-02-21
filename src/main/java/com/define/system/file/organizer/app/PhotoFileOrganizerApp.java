package com.define.system.file.organizer.app;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.define.system.file.organizer.constants.FileOrganizerConstant;
import com.define.system.file.organizer.dto.UserInputDTO;
import com.define.system.file.organizer.processor.PhotoOrganizer;

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
 * Photo organizer strategy
 * 
 * @author Nitesh Apte
 * @version 0.1
 * @since 0.1
 */
@Slf4j
@Component
public class PhotoFileOrganizerApp implements IFileOrganizerApp {
	
	@Autowired
	Environment env;
	
	@Autowired
	PhotoOrganizer photoOrganizer;
	
	/* (non-Javadoc)
	 * @see com.define.system.file.organizer.app.IFileOrganizerApp#init()
	 */
	@Override
	public void init() {
		UserInputDTO userInputDTO = collectInput();
		if(null != userInputDTO) {
			log.info("Photo organizer is enabled. Initializing process...");
			photoOrganizer.initializeProcess(userInputDTO);
		} else {
			log.info("Photo organizer is not enabled.");
		} 
	} 
	
	/**
	 * Collect input from properties file.
	 *
	 * @return the user input DTO
	 */ 
	private UserInputDTO collectInput() {
		UserInputDTO userInputDTO = null;
		
		if(Boolean.parseBoolean(env.getProperty(FileOrganizerConstant.PHOTO_ORGANIZER_ENABLED))) {
			log.info("Collecting input for photo organizer");
			
			userInputDTO = new UserInputDTO();
			userInputDTO.setSourceLocation(env.getProperty(FileOrganizerConstant.PHOTO_STRING_SOURCE_LOCATION));
			userInputDTO.setDestinationLocation(env.getProperty(FileOrganizerConstant.PHOTO_STRING_DESTINATION_LOCATION));
			userInputDTO.setCreateFolder(Boolean.parseBoolean(env.getProperty(FileOrganizerConstant.PHOTO_BOOLEAN_CREATE_FOLDER)));
			userInputDTO.setFileExtension(Arrays.asList(env.getProperty(FileOrganizerConstant.PHOTO_LIST_FILE_EXTENSION).split(",")));

			log.info("Collected inputs:");
			log.info("Source Location: " + userInputDTO.getSourceLocation());
			log.info("Destination Location: " + userInputDTO.getDestinationLocation());
			log.info("Folder creation / File Relocation allowed: " + userInputDTO.getCreateFolder());
			log.info("Allowed file extensions: " + userInputDTO.getFileExtension());
		}
		return userInputDTO;
	}	
}