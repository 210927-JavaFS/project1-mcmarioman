package com.revature.repositories;
import java.util.List;
import com.revature.models.ReimbursementType;

public interface ReimbursementTypeDAO {
	List<ReimbursementType> getReimbursementTypes();
	ReimbursementType getReimbursementById(int typeId);
}
