package com.revature.repositories;

import java.util.List;

import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimbursement;
import com.revature.utils.HibernateUtil;

public class ReimbursementDAOImpl implements ReimbursementDAO {

	@Override
	public List<Reimbursement> getReimbursementsByUserId(int UserId) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		session.disableFilter("statusIdFilter");
		Filter filter = session.enableFilter("userIdFilter");
		filter.setParameter("_userId", UserId);
		List<Reimbursement> list = session.createQuery("FROM Reimbursement", Reimbursement.class).list();
		return list;
	}

	@Override
	public boolean addReimbursement(Reimbursement reimbursement) {
		try {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.save(reimbursement);
			tx.commit();
			HibernateUtil.closeSession();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Reimbursement> getReimbursementsByStatusId(int statusId) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		session.disableFilter("userIdFilter");
		Filter filter = session.enableFilter("statusIdFilter");
		filter.setParameter("_statusId", statusId);
		List<Reimbursement> list = session.createQuery("FROM Reimbursement", Reimbursement.class).list();
		return list;
	}

	@Override
	public Reimbursement getReimbursementById(int reimbursementId) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		return session.get(Reimbursement.class, reimbursementId);
	}

	@Override
	public boolean updateReimbursement(Reimbursement reimbursement) {
		// TODO Auto-generated method stub
		try {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.merge(reimbursement);
			tx.commit();
			HibernateUtil.closeSession();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

}
