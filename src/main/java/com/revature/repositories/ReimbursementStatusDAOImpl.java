package com.revature.repositories;

import java.util.List;

import com.revature.models.ReimbursementStatus;
import com.revature.utils.HibernateUtil;

import org.hibernate.Session;

public class ReimbursementStatusDAOImpl implements ReimbursementStatusDAO {

	@Override
	public List<ReimbursementStatus> getReimbursementStatus() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		return session.createQuery("FROM ReimbursementStatus", ReimbursementStatus.class).list();
	}

	@Override
	public ReimbursementStatus getReimbursementStatusById(int statusId) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		return session.load(ReimbursementStatus.class, statusId);
	}

}
