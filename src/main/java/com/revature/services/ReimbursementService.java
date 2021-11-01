package com.revature.services;

import java.util.List;

import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.ReimbursementDAOImpl;
import com.revature.models.Reimbursement;

public class ReimbursementService {
	private ReimbursementDAO reimbursementDAO = new ReimbursementDAOImpl();
	
	public List<Reimbursement> getReimbursementsByUserId(int userId) {
		return reimbursementDAO.getReimbursementsByUserId(userId);
	}
	
	public boolean addReimbursement(Reimbursement reimbursement) {
		return reimbursementDAO.addReimbursement(reimbursement);
	}
	
	public List<Reimbursement> getReimbursementsByStatusId(int statusId) {
		return reimbursementDAO.getReimbursementsByStatusId(statusId);
	}
	
	public Reimbursement getReimbursementById(int reimbursementId) {
		return reimbursementDAO.getReimbursementById(reimbursementId);
	}
	public boolean updateReimbursement(Reimbursement reimbursement) {
		return reimbursementDAO.updateReimbursement(reimbursement);
	}
	
}
