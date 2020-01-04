package com.define.system.file.organizer.dto;

import java.util.List;

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
 * The Class UserInputDTO.
 * 
 * @author Nitesh Apte
 * @version 0.1
 * @since 0.1
 */
public class UserInputDTO extends BaseDTO implements IRequestDTO {

	/** The source location. */
	private String sourceLocation;
	
	/** The destination location. */
	private String destinationLocation;
	
	/** The file extension. */
	private List<String> fileExtension;
	
	/** The create folder. */
	private Boolean createFolder;
	
	/**
	 * Gets the source location.
	 *
	 * @return the source location
	 */
	public String getSourceLocation() {
		return sourceLocation;
	}

	/**
	 * Sets the source location.
	 *
	 * @param sourceLocation the new source location
	 */
	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	/**
	 * Gets the destination location.
	 *
	 * @return the destination location
	 */
	public String getDestinationLocation() {
		return destinationLocation;
	}

	/**
	 * Sets the destination location.
	 *
	 * @param destinationLocation the new destination location
	 */
	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	public List<String> getFileExtension() {
		return fileExtension;
	}

	/**
	 * Sets the file extension.
	 *
	 * @param fileExtension the new file extension
	 */
	public void setFileExtension(List<String> fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	/**
	 * Gets the creates the folder.
	 *
	 * @return the creates the folder
	 */
	public Boolean getCreateFolder() {
		return createFolder;
	}

	/**
	 * Sets the creates the folder.
	 *
	 * @param createFolder the new creates the folder
	 */
	public void setCreateFolder(Boolean createFolder) {
		this.createFolder = createFolder;
	}
}