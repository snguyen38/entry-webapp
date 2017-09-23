package com.entry.webapp.util;

import java.io.File;

import com.entry.wepapp.entity.User;

public class AvatarUtils {
	
	public static String getValidAvatarDirectory(String fileContext, String dir) {
		File file = new File(fileContext + dir);
		if (!file.isDirectory() && file.exists()) {
			return dir;
		} else {
			return "images/if_icon-person.png";
		}
	}
	
	public static User getValidAvatarDirectory(String fileContext, User user) {
		String validDir = getValidAvatarDirectory(fileContext, user.getAvatar());
		user.setAvatar(validDir);
		
		return user;
	}

}
