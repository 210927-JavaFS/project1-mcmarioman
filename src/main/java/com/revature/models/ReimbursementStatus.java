package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reimbursement_status")
public class ReimbursementStatus {
	@Id
	@Column(name = "status_id")
	private int statusId;
	@Column(nullable = false, name = "status_name", length = 25)
	private String statusName;
	public int getStatusId() {
		return statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public ReimbursementStatus() {
		super();
	}
	public ReimbursementStatus(int statusId, String statusName) {
		super();
		this.statusId = statusId;
		this.statusName = statusName;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
