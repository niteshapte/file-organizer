package com.define.system.file.organizer.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.define.system.file.organizer.dto.UserInputDTO;

public class VideoOrganizer implements IFileOrganizer {
	
	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(PhotoOrganizer.class);
	
	/**
	 * Initialize process.
	 *
	 * @param userInputDTO the user input DTO
	 */
	public void initializeProcess(UserInputDTO userInputDTO) {
		try {
			List<Path> imagePaths = Files.walk(Paths.get(userInputDTO.getSourceLocation())).filter(Files::isRegularFile).filter(path -> 
			userInputDTO.getFileExtension().contains(path.getFileName().getFileName().toString().substring(path.getFileName().getFileName().toString().lastIndexOf(".") + 1, path.getFileName().getFileName().toString().length()))).collect(Collectors.toList());
			
			logger.info("Source Location: " + userInputDTO.getSourceLocation());
			logger.info("Total photos found: " + imagePaths.size());
			logger.info("Processing files one by one now.");

			imagePaths.forEach(imageFile -> process(imageFile, userInputDTO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Process single file
	 *
	 * @param imageFile the image file
	 * @param userInputDTO the user input DTO
	 */
	private void process(Path imageFile, UserInputDTO userInputDTO) {
		
	}
}