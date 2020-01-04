package com.define.system.file.organizer.app;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.define.system.file.organizer.constants.FileOrganizerConstant;
import com.define.system.file.organizer.dto.UserInputDTO;
import com.define.system.file.organizer.processor.PhotoOrganizer;
import com.define.system.file.organizer.util.PropertiesLoader;

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
public class PhotoFileOrganizerApp implements IFileOrganizerApp {
	
	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(PhotoFileOrganizerApp.class);
	
	/* (non-Javadoc)
	 * @see com.define.system.file.organizer.app.IFileOrganizerApp#init()
	 */
	@Override
	public void init() {
		UserInputDTO userInputDTO = collectInput();
		if(null != userInputDTO) {
			logger.info("Photo organizer is enabled. Initializing process...");
			new PhotoOrganizer().initializeProcess(userInputDTO);
		} else {
			logger.info("Photo organizer is not enabled.");
		}
	}
	
	/**
	 * Collect input from properties file.
	 *
	 * @return the user input DTO
	 */
	private UserInputDTO collectInput() {
		PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
		propertiesLoader.loadFile(FileOrganizerConstant.INPUT_PROP_FILE_PATH);
		
		UserInputDTO userInputDTO = null;
		
		if(Boolean.parseBoolean(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_ORGANIZER_ENABLED))) {
			logger.info("Collecting input for photo organizer");
			
			userInputDTO = new UserInputDTO();
			userInputDTO.setSourceLocation(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_STRING_SOURCE_LOCATION));
			userInputDTO.setDestinationLocation(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_STRING_DESTINATION_LOCATION));
			userInputDTO.setCreateFolder(Boolean.parseBoolean(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_BOOLEAN_CREATE_FOLDER)));
			userInputDTO.setFileExtension(Arrays.asList(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_LIST_FILE_EXTENSION).split(",")));
			
			logger.info("Collected inputs:");
			logger.info("Source Location: " + userInputDTO.getSourceLocation());
			logger.info("Destination Location: " + userInputDTO.getDestinationLocation());
			logger.info("Folder creation / File Relocation allowed: " + userInputDTO.getCreateFolder());
			logger.info("Allowed file extensions: " + userInputDTO.getFileExtension());
		}
		return userInputDTO;
	}	
}