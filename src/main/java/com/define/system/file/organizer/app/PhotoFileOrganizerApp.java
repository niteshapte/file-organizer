package com.define.system.file.organizer.app;

import java.util.Arrays;

import com.define.system.file.organizer.constants.FileOrganizerConstant;
import com.define.system.file.organizer.dto.UserInputDTO;
import com.define.system.file.organizer.processor.PhotoOrganizer;
import com.define.system.file.organizer.util.PropertiesLoader;

public class PhotoFileOrganizerApp implements IFileOrganizerApp {
	
	@Override
	public void init() {
		UserInputDTO userInputDTO = collectInput();
		new PhotoOrganizer().initializeProcess(userInputDTO);
	}
	
	private UserInputDTO collectInput() {
		PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
		propertiesLoader.loadFile(FileOrganizerConstant.PHOTO_PROP_FILE_PATH);
		
		UserInputDTO userInputDTO = new UserInputDTO();
		userInputDTO.setSourceLocation(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_STRING_SOURCE_LOCATION));
		userInputDTO.setDestinationLocation(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_STRING_DESTINATION_LOCATION));
		userInputDTO.setCreateFolder(Boolean.parseBoolean(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_BOOLEAN_CREATE_FOLDER)));
		userInputDTO.setFileExtension(Arrays.asList(propertiesLoader.getValue(FileOrganizerConstant.PHOTO_LIST_FILE_EXTENSION).split(",")));	
		
		return userInputDTO;
	}	
}