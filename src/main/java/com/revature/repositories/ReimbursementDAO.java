package com.revature.repositories;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDAO {
	List<Reimbursement> getReimbursementsByUserId(int userId);
	boolean addReimbursement(Reimbursement reimbursement);
	List<Reimbursement> getReimbursementsByStatusId(int statusId);
	Reimbursement getReimbursementById(int reimbursementId);
	boolean updateReimbursement(Reimbursement reimbursement);
}
