package com.define.system.file.organizer.app;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.define.system.file.organizer.constants.FileOrganizerConstant;
import com.define.system.file.organizer.dto.UserInputDTO;
import com.define.system.file.organizer.processor.FileOrganizerContext;
import com.define.system.file.organizer.processor.VideoOrganizer;
import com.define.system.file.organizer.util.PropertiesLoader;

public class VideoFileOrganizerApp implements IFileOrganizerApp {

	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(VideoFileOrganizerApp.class);
	
	@Override
	public void init() {
		UserInputDTO userInputDTO = collectInput();
		if(null != userInputDTO) {
			logger.info("Photo organizer is enabled. Initializing process...");
			new FileOrganizerContext(new VideoOrganizer()).start(userInputDTO);
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
		
		if(Boolean.parseBoolean(propertiesLoader.getValue(FileOrganizerConstant.VIDEO_ORGANIZER_ENABLED))) {
			logger.info("Collecting input for video organizer");
			
			userInputDTO = new UserInputDTO();
			userInputDTO.setSourceLocation(propertiesLoader.getValue(FileOrganizerConstant.VIDEO_STRING_SOURCE_LOCATION));
			userInputDTO.setDestinationLocation(propertiesLoader.getValue(FileOrganizerConstant.VIDEO_STRING_DESTINATION_LOCATION));
			userInputDTO.setCreateFolder(Boolean.parseBoolean(propertiesLoader.getValue(FileOrganizerConstant.VIDEO_BOOLEAN_CREATE_FOLDER)));
			userInputDTO.setFileExtension(Arrays.asList(propertiesLoader.getValue(FileOrganizerConstant.VIDEO_LIST_FILE_EXTENSION).split(",")));
			
			logger.info("Collected inputs:");
			logger.info("Source Location: " + userInputDTO.getSourceLocation());
			logger.info("Destination Location: " + userInputDTO.getDestinationLocation());
			logger.info("Folder creation / File Relocation allowed: " + userInputDTO.getCreateFolder());
			logger.info("Allowed file extensions: " + userInputDTO.getFileExtension());
		}
		return userInputDTO;
	}
}
