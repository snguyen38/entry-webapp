package com.entry.webapp.util;

import java.io.File;

import com.entry.webapp.entity.User;

public class AppUtils {
	
	public static String getValidAvatarDirectory(String fileContext, String dir) {
		File file = new File(fileContext + dir);
		if (!file.isDirectory() && file.exists()) {
			return dir;
		} else {
			return "images/avatar/default.png";
		}
	}
	
	public static User getValidAvatarDirectory(String fileContext, User user) {
		String validDir = getValidAvatarDirectory(fileContext, user.getAvatar());
		user.setAvatar(validDir);
		
		return user;
	}

}
