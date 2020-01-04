package com.define.system.file.organizer;

import com.define.system.file.organizer.app.DocumentFileOrganizerApp;
import com.define.system.file.organizer.app.FileOrganizerAppContext;
import com.define.system.file.organizer.app.PhotoFileOrganizerApp;
import com.define.system.file.organizer.app.VideoFileOrganizerApp;

public class FileFinderApp {
	
	public static void main(String[] args) {
		
		new FileOrganizerAppContext(new PhotoFileOrganizerApp()).startOrganizerApp();
		
		new FileOrganizerAppContext(new VideoFileOrganizerApp()).startOrganizerApp();
		
		new FileOrganizerAppContext(new DocumentFileOrganizerApp()).startOrganizerApp();
	}
}