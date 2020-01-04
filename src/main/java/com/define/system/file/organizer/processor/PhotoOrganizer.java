package com.define.system.file.organizer.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.define.system.file.organizer.dto.UserInputDTO;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

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
 * The Class PhotoOrganizer - Photo strategy class
 * Organize photos / pictures by creation year, month and day
 *
 * @author Nitesh Apte
 * @version 0.1
 * @since 0.1
 */
public class PhotoOrganizer implements IFileOrganizer {
	
	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(PhotoOrganizer.class);
	
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
			
			logger.info("Source Location: " + userInputDTO.getSourceLocation());
			logger.info("Total photos found: " + imagePaths.size());
			logger.info("Processing files one by one now.");

			imagePaths.forEach(imageFile -> process(imageFile, userInputDTO));
		} catch (IOException e) {
			logger.error("Failed to process the file.");
		}
	}
	
	/**
	 * Process single file
	 *
	 * @param imageFile the image file
	 * @param userInputDTO the user input DTO
	 */
	private void process(Path imageFile, UserInputDTO userInputDTO) {
		logger.info("Inside PhotoOrganizer.process");
		
		String filePath = imageFile.toString();
		
		logger.info("Processing file = " + filePath);
		
		String fileName = imageFile.getFileName().toString();
		String dateTime = "00000000";
		String year = "0000";
		String month = "00";
		String day = "00";
		try {
			dateTime = getDateFromImgEXIF(new File(filePath));
			logger.info("Date Time detected for file " + filePath + " = " + dateTime);
		} catch (IOException e) {
			logger.error("Failed to read date time of image " + fileName + ". Error: " + e.getMessage());
		}
		year = dateTime != null ? dateTime.substring(0, 4) : "0000";
		month = dateTime != null ? dateTime.substring(5, 7) : "00";
		day = dateTime != null ? dateTime.substring(8, 10) : "00";
		
		logger.info("Original Year - Month - Day : " + year + " - " + month + " - " + day + " for file " + filePath);
		
		String formattedMonth = formatMonth(month);

		logger.info("Formatted Year - Month - Day : " + year + " - " + formattedMonth + " - " + day);
		
		if(userInputDTO.getCreateFolder()) {
			logger.info("Relocation to new location is enabled.");
			
			String destinationFilePath = userInputDTO.getDestinationLocation() + "/" + year + "/" + formattedMonth + "/" + day + " " + formattedMonth + " " + year;
			
			createDestinationFolderStructure(destinationFilePath);
			
			moveFileToDestination(imageFile, Paths.get(destinationFilePath + "/" + fileName));
		}
		logger.info("Finished processing file = " + filePath);
		logger.info("Leaving PhotoOrganizer.process");
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
			logger.info("File " + originalPath.toString() + " moved to " + destinationPath.toString());
		} catch (Exception e) {
			logger.error("Failed to move file " + originalPath.toString() + " to destination");
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
                logger.info("Created nested folders = " + destinationFilePath);
            } catch (IOException ioExceptionObj) {
            	logger.error("Problem occured while creating the directory structure = " + ioExceptionObj.getMessage());
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
			logger.error("Failed to convert Integer form of month to String. Error: " + e.getMessage());
		}
	    return formattedMonth;
	}

	/**
	 * Gets the date from img EXIF.
	 *
	 * @param file the file
	 * @return the date from img EXIF
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getDateFromImgEXIF(final File file) throws IOException {
		String date = null;
		if (file.isFile()) {
			try {
				final Metadata metadata = ImageMetadataReader.readMetadata(file);
				// obtain the Exif directory
				final Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
				if (null != directory) {
					final Date tagDate = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
					if (null != tagDate) {
						date = this.sdf.format(tagDate);
					}
				}
			} catch (final ImageProcessingException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
}