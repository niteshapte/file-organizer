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
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.define.system.file.organizer.dto.UserInputDTO;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class PhotoOrganizer implements IFileOrganizer {
	
	final static Logger logger = Logger.getLogger(PhotoOrganizer.class);
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void initializeProcess(UserInputDTO userInputDTO) {
		try {
			Files.walk(Paths.get(userInputDTO.getSourceLocation())).filter(Files::isRegularFile).filter(path -> 
			userInputDTO.getFileExtension().contains(path.getFileName().getFileName().toString().substring(path.getFileName().getFileName().toString().lastIndexOf(".") + 1, path.getFileName().getFileName().toString().length()))).collect(Collectors.toList())
			.forEach(imageFile -> {
				process(imageFile, userInputDTO);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void process(Path imageFile, UserInputDTO userInputDTO) {
		logger.info("Inside PhotoFinderProcessor.process");
		
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
			logger.info("Relocation of new location is enabled.");
			
			String destinationFilePath = userInputDTO.getDestinationLocation() + year + "/" + formattedMonth + "/" + day + " " + formattedMonth + " " + year;
			
			createDestinationFolderStructure(destinationFilePath);
			
			moveFileToDestination(imageFile, Paths.get(destinationFilePath + "/" + fileName));
		}
		logger.info("Finished processing file = " + filePath);
		logger.info("Leaving PhotoFinderProcessor.process");
	}
	
	private void moveFileToDestination(Path originalPath, Path destinationPath) {
		try {
			Files.move(originalPath, destinationPath, StandardCopyOption.ATOMIC_MOVE);
			logger.info("File " + originalPath.toString() + " moved.");
		} catch (Exception e) {
			logger.error("Failed to move file " + originalPath.toString() + " to destination");
		}
	}
	
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