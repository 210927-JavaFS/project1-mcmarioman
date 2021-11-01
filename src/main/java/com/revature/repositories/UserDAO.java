package com.revature.repositories;

import java.util.List;

import com.revature.models.User;
import com.revature.models.UserDTO;

public interface UserDAO {
	List<User> getUsers();
	User getUserByUserName(String userName);
	User getUserById(int userId);
}
