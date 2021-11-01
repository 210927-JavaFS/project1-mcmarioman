package com.revature.repositories;

import java.util.List;

import org.hibernate.Session;

import com.revature.models.ReimbursementType;
import com.revature.utils.HibernateUtil;

public class ReimbursementTypeDAOImpl implements ReimbursementTypeDAO {

	@Override
	public List<ReimbursementType> getReimbursementTypes() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		
		return session.createQuery("FROM ReimbursementType", ReimbursementType.class).list();
	}

	@Override
	public ReimbursementType getReimbursementById(int typeId) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		return session.load(ReimbursementType.class, typeId);
	}

}
