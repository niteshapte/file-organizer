package com.define.system.file.organizer.dto;

import java.util.List;

public class UserInputDTO extends BaseDTO implements IRequestDTO {

	private String sourceLocation;
	
	private String destinationLocation;
	
	private List<String> fileExtension;
	
	private String fileNameStartWith;
	
	private String fileNameMiddleWith;
	
	private String fileNameEndWith;
	
	private Boolean createFolder;
	
	private Boolean readSubDirectories;

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public List<String> getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(List<String> fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileNameStartWith() {
		return fileNameStartWith;
	}

	public void setFileNameStartWith(String fileNameStartWith) {
		this.fileNameStartWith = fileNameStartWith;
	}

	public String getFileNameMiddleWith() {
		return fileNameMiddleWith;
	}

	public void setFileNameMiddleWith(String fileNameMiddleWith) {
		this.fileNameMiddleWith = fileNameMiddleWith;
	}

	public String getFileNameEndWith() {
		return fileNameEndWith;
	}

	public void setFileNameEndWith(String fileNameEndWith) {
		this.fileNameEndWith = fileNameEndWith;
	}

	public Boolean getCreateFolder() {
		return createFolder;
	}

	public void setCreateFolder(Boolean createFolder) {
		this.createFolder = createFolder;
	}

	public Boolean getReadSubDirectories() {
		return readSubDirectories;
	}

	public void setReadSubDirectories(Boolean readSubDirectories) {
		this.readSubDirectories = readSubDirectories;
	}	
}