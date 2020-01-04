package com.define.system.file.organizer.processor;

import com.define.system.file.organizer.dto.UserInputDTO;

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
 * File Organizer context for different strategies.
 *
 * @author Nitesh Apte
 * @version 0.1
 * @since 0.1
 */
public class FileOrganizerContext {

	/** The i file organizer. */
	IFileOrganizer iFileOrganizer;
	
	/**
	 * Instantiates a new file organizer context.
	 *
	 * @param iFileOrganizer the i file organizer
	 */
	public FileOrganizerContext(IFileOrganizer iFileOrganizer) {
		this.iFileOrganizer = iFileOrganizer; 
	}
	
	/**
	 * Start the processing
	 *
	 * @param userInputDTO the user input DTO
	 */
	public void start(UserInputDTO userInputDTO) {
		iFileOrganizer.initializeProcess(userInputDTO);
	}
}