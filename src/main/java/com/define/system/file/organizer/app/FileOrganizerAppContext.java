package com.define.system.file.organizer.app;

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
* The Class FileOrganizerAppContext.
* 
* @author Nitesh Apte
* @since 0.1
* @version 0.1
*/
public class FileOrganizerAppContext {

	/** The file organizer app. */
	IFileOrganizerApp iFileOrganizerApp;
	
	/**
	 * Instantiates a new file organizer app context.
	 *
	 * @param iFileOrganizerApp the i file organizer app
	 */
	public FileOrganizerAppContext(IFileOrganizerApp iFileOrganizerApp) {
		this.iFileOrganizerApp = iFileOrganizerApp;
	}
	
	/**
	 * Start organizer app.
	 */
	public void startOrganizerApp() {
		iFileOrganizerApp.init();
	}
}