package com.entry.webapp.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.entry.wepapp.entity.User;
import com.entry.wepapp.service.UserService;

public class AppHelper {
	
	@Autowired
    private UserService userService;
	
	
	public Map<String, Boolean> checkUserExist(String email, String nickname) {
		Map<String, Boolean> res = new HashMap<>();
 
		if (!StringUtils.isEmpty(email)) {
			User user4Email = this.userService.findUserByEmail(email);
			if (!ObjectUtils.isEmpty(user4Email)) {
				res.put("status", false);
				res.put("email", false); 
			}
		}
		
		try {
			if (!StringUtils.isEmpty(nickname)) {
				User user4Nickname = this.userService.findUserByNickName(nickname);
				if (!ObjectUtils.isEmpty(user4Nickname)) {
					res.put("status", false);
					res.put("nickname", false);
				}
			}
		} catch (UsernameNotFoundException e) {
			return res;
		}
		return res;
	}

}
