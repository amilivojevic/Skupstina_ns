package com.tim_wro.skupstina.security;

import com.tim_wro.skupstina.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


/**
 * This class represents UserUtils
 */
@Component
public class UserUtils {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;


	public Object getLoggedUser(ServletRequest request) {
		System.out.println("pocetak funkcije");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		System.out.println("http req");
		String token = httpRequest.getHeader("X-Auth-Token");
		System.out.println("ucitan token");
		String un = tokenUtils.getUsernameFromToken(token);
		System.out.println("username = " + un);
		UserDetails details = userDetailsService.loadUserByUsername(un);
		if(details == null){
			System.out.println("Details = null");
		}
		else{
			System.out.println("detail nije null" + details.toString());
		}
		System.out.println("username : " + details.getUsername());
		return userService.findByUsername(details.getUsername());
	}

	/**
	 * This method checks if passed user name already exists
	 *
	 * @param username
	 * @return true or false
	 */
	public boolean checkUniqueUsername(String username) {

		if (userService.findByUsername(username) != null) {
			return false;
		}
		return true;
	}

}
