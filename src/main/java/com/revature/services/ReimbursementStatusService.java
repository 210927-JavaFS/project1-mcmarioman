package com.revature.services;

import com.revature.models.ReimbursementStatus;
import com.revature.repositories.ReimbursementStatusDAO;
import com.revature.repositories.ReimbursementStatusDAOImpl;

public class ReimbursementStatusService {
	private ReimbursementStatusDAO reimbursementStatusDAO = new ReimbursementStatusDAOImpl();
	
	public ReimbursementStatus getReimbursementStatusById(int statusId) {
		return reimbursementStatusDAO.getReimbursementStatusById(statusId);
	}
	
}
