package com.revature.repositories;

import java.util.List;

import org.hibernate.Session;

import com.revature.models.UserRol;
import com.revature.utils.HibernateUtil;

public class UserRolDAOImpl implements UserRolDAO {

	@Override
	public List<UserRol> getUserRoles() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		return session.createQuery("FROM UserRol", UserRol.class).list();
	}

}
