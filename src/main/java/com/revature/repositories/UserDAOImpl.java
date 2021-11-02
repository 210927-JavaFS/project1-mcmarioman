package com.revature.repositories;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.ReimbursementController;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.utils.HibernateUtil;

public class UserDAOImpl implements UserDAO {
	private static Logger log = LoggerFactory.getLogger(ReimbursementController.class);
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		return session.createQuery("FROM User", User.class).list();
	}

	@Override
	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		try {
			Session session =  HibernateUtil.getSession();
			User user = session.byNaturalId(User.class)
			                   .using("userName",userName)
			                   .load();
			return user;
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
		}
		return null;
		
	}

	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		Session session =  HibernateUtil.getSession();
		return session.load(User.class, userId);
	}

}
