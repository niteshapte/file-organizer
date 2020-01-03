package com.define.system.file.organizer.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.define.system.file.organizer.constants.FileFinderConstant;
import com.define.system.file.organizer.dto.UserInputDTO;
import com.define.system.file.organizer.processor.PhotoFinderProcessor;
import com.define.system.file.organizer.util.PropertiesLoader;

public class PhotoFinder implements IFileFinder {
	
	public void initiateProcess(UserInputDTO userInputDTO) {
		/*
		try {
			Files.list(Paths.get("/root/Pictures/Camera")).filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith("png") || path.toString().endsWith("jpeg")).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void init() {
		UserInputDTO userInputDTO = collectInput();
		new PhotoFinderProcessor().initializeProcess(userInputDTO);
	}
	
	
	private UserInputDTO collectInput() {
		PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
		propertiesLoader.loadFile(FileFinderConstant.PHOTO_PROP_FILE_PATH);
		
		UserInputDTO userInputDTO = new UserInputDTO();
		userInputDTO.setSourceLocation(propertiesLoader.getValue(FileFinderConstant.PHOTO_STRING_SOURCE_LOCATION));
		userInputDTO.setDestinationLocation(propertiesLoader.getValue(FileFinderConstant.PHOTO_STRING_DESTINATION_LOCATION));
		userInputDTO.setCreateFolder(Boolean.parseBoolean(propertiesLoader.getValue(FileFinderConstant.PHOTO_BOOLEAN_CREATE_FOLDER)));
		userInputDTO.setFileExtension(Arrays.asList(propertiesLoader.getValue(FileFinderConstant.PHOTO_LIST_FILE_EXTENSION).split(",")));	
		userInputDTO.setFileNameStartWith(propertiesLoader.getValue(FileFinderConstant.PHOTO_STRING_FILENAME_START_WITH));
		userInputDTO.setFileNameMiddleWith(propertiesLoader.getValue(FileFinderConstant.PHOTO_STRING_FILENAME_MIDDLE_WITH));
		userInputDTO.setFileNameEndWith(propertiesLoader.getValue(FileFinderConstant.PHOTO_STRING_FILENAME_END_WITH));
		userInputDTO.setReadSubDirectories(Boolean.parseBoolean(propertiesLoader.getValue(FileFinderConstant.PHOTO_BOOLEAN_READ_SUB_DIRECTORIES)));
		
		return userInputDTO;
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("20140527_225402.jpg".substring(1,"20140527_225402.jpg".lastIndexOf(".")));
		
		new PhotoFinder().init();
	}
	
}