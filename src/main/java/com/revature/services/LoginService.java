package com.revature.services;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.repositories.UserDAO;
import com.revature.repositories.UserDAOImpl;

public class LoginService {
	
	private UserDAO userDao = new UserDAOImpl();
	
	public boolean login(UserDTO userDto) {
		
		User user = userDao.getUserByUserName(userDto.username);
		
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		
		
		if(user!=null &&  (passwordEncryptor.checkPassword(userDto.password, user.getUserPassword()))) {
	
			return true;
		}
		
		return false;
	}
	
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}
	
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	
}
