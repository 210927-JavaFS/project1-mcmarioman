package com.revature.repositories;
import java.util.List;

import com.revature.models.ReimbursementStatus;

public interface ReimbursementStatusDAO {
	List<ReimbursementStatus> getReimbursementStatus();
	ReimbursementStatus getReimbursementStatusById(int statusId);
}
