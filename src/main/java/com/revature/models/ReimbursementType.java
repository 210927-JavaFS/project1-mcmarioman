package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "reimbursement_type")
public class ReimbursementType {
	@Id
	@Column(name = "type_id")
	private int typeId;
	@Column(nullable = false, name = "type_name", length = 25)
	private String typeName;
	
	
	public ReimbursementType() {
		super();
	}
	public ReimbursementType(int typeId, String typeName) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
	}
	public int getTypeId() {
		return typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
