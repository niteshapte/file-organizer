package com.define.system.file.organizer.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.define.system.file.organizer.dto.UserInputDTO;

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
 * The Class VideoOrganizer - Video strategy class
 * Organize video files by creation year, month and day
 *
 * @author Nitesh Apte
 * @version 0.1
 * @since 0.1
 */
@Slf4j
@Component
public class VideoOrganizer implements IFileOrganizer {
	
	/** The sdf. */
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Initialize process.
	 *
	 * @param userInputDTO the user input DTO
	 */
	public void initializeProcess(UserInputDTO userInputDTO) {
		try {
			List<Path> imagePaths = Files.walk(Paths.get(userInputDTO.getSourceLocation())).filter(Files::isRegularFile).filter(path -> 
			userInputDTO.getFileExtension().contains(path.getFileName().getFileName().toString().substring(path.getFileName().getFileName().toString().lastIndexOf(".") + 1, path.getFileName().getFileName().toString().length()))).collect(Collectors.toList());
			
			log.info("Source Location: " + userInputDTO.getSourceLocation());
			log.info("Total photos found: " + imagePaths.size());
			log.info("Processing files one by one now.");

			imagePaths.forEach(imageFile -> process(imageFile, userInputDTO));
		} catch (IOException e) {
			log.error("Failed to process the file.");
		}
	}
	
	/**
	 * Process single file.
	 *
	 * @param imageFile the image file
	 * @param userInputDTO the user input DTO
	 */
	private void process(Path imageFile, UserInputDTO userInputDTO) {
		log.info("Inside VideoOrganizer.process");
		
		String filePath = imageFile.toString();
		
		log.info("Processing file = " + filePath);
		
		String fileName = imageFile.getFileName().toString();
		String dateTime = "00000000";
		String year = "0000";
		String month = "00";
		String day = "00";
		
		try {
			dateTime = getLastModifiedDateOfFile(imageFile);
			log.info("Date Time detected for file " + filePath + " = " + dateTime);
		} catch (IOException e) {
			log.error("Failed to read date time of image " + fileName + ". Error: " + e.getMessage());
		}
		year = dateTime != null ? dateTime.substring(0, 4) : "0000";
		month = dateTime != null ? dateTime.substring(5, 7) : "00";
		day = dateTime != null ? dateTime.substring(8, 10) : "00";
		
		log.info("Original Year - Month - Day : " + year + " - " + month + " - " + day + " for file " + filePath);
		
		String formattedMonth = formatMonth(month);

		log.info("Formatted Year - Month - Day : " + year + " - " + formattedMonth + " - " + day);
		
		if(userInputDTO.getCreateFolder()) {
			log.info("Relocation to new location is enabled.");
			
			String destinationFilePath = userInputDTO.getDestinationLocation() + "/" + year + "/" + formattedMonth + "/" + day + " " + formattedMonth + " " + year;
			
			createDestinationFolderStructure(destinationFilePath);
			
			moveFileToDestination(imageFile, Paths.get(destinationFilePath + "/" + fileName));
		}
		log.info("Finished processing file = " + filePath);
		log.info("Leaving VideoOrganizer.process");
	}
	
	/**
	 * Move file to destination.
	 *
	 * @param originalPath the original path
	 * @param destinationPath the destination path
	 */
	private void moveFileToDestination(Path originalPath, Path destinationPath) {
		try {
			Files.move(originalPath, destinationPath, StandardCopyOption.ATOMIC_MOVE);
			log.info("File " + originalPath.toString() + " moved to " + destinationPath.toString());
		} catch (Exception e) {
			log.error("Failed to move file " + originalPath.toString() + " to destination");
		}
	}
	
	/**
	 * Creates the destination folder structure.
	 *
	 * @param destinationFilePath the destination file path
	 */
	private void createDestinationFolderStructure(String destinationFilePath) {
		Path dirPathObj = Paths.get(destinationFilePath);
		boolean dirExists = Files.exists(dirPathObj);
        if(!dirExists) {
        	try {
                Files.createDirectories(dirPathObj);
                log.info("Created nested folders = " + destinationFilePath);
            } catch (IOException ioExceptionObj) {
            	log.error("Problem occured while creating the directory structure = " + ioExceptionObj.getMessage());
            }
        }
	}
	
	/**
	 * Format month.
	 *
	 * @param month the month
	 * @return the string
	 */
	private String formatMonth(String month) {
		String formattedMonth = "00";
	    SimpleDateFormat monthParse = new SimpleDateFormat("MM");
	    SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM");
	    try {
	    	formattedMonth = monthDisplay.format(monthParse.parse(month));
		} catch (ParseException e) {
			log.error("Failed to convert Integer form of month to String. Error: " + e.getMessage());
		}
	    return formattedMonth;
	}
	
	/**
	 * Gets the last modified date of file.
	 *
	 * @param path the path
	 * @return the last modified date of file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getLastModifiedDateOfFile(final Path path) throws IOException {
		FileTime fileTime = Files.getLastModifiedTime(path);
		return this.sdf.format(fileTime.toMillis());
	}
}