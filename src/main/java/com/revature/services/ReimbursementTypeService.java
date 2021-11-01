package com.revature.services;

import com.revature.models.ReimbursementType;
import com.revature.repositories.ReimbursementTypeDAO;
import com.revature.repositories.ReimbursementTypeDAOImpl;

public class ReimbursementTypeService {
	
	
	private ReimbursementTypeDAO reimbursementTypeDAO = new ReimbursementTypeDAOImpl();
	
	public ReimbursementType getReimbursementById(int typeId) {
		return reimbursementTypeDAO.getReimbursementById(typeId);
	}
}
