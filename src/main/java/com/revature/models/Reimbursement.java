package com.revature.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

@FilterDef(name = "userIdFilter", parameters = @ParamDef(name = "_userId", type = "int"), 
defaultCondition = "author_id = :_userId")
@FilterDef(name = "statusIdFilter", parameters = @ParamDef(name = "_statusId", type = "int"), 
defaultCondition = "status_id = :_statusId")
@Filters( {
@Filter(name = "userIdFilter"),
@Filter(name = "statusIdFilter")
})
@Entity
public class Reimbursement {
	
	public Reimbursement() {
		super();
	}
	public Reimbursement(int reimbursementId, double amount, Date submitted, Date resolved, String description,
			String receipt, User author, User resolver, ReimbursementStatus reimbursementStatus,
			ReimbursementType reimbursementType) {
		super();
		this.reimbursementId = reimbursementId;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.reimbursementStatus = reimbursementStatus;
		this.reimbursementType = reimbursementType;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reimbursement_id")
	private int reimbursementId;
	@Column(nullable = false, precision=10, scale=2)
	private double amount;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date submitted;
	@Temporal(TemporalType.DATE)
	private Date resolved;
	@Column(nullable = false, length= 250)
	private String description;
	@Column(length= 150)
	private String receipt;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(nullable = false, name="author_id")
	private User author;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="resolver_id")
	private User resolver;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(nullable = false, name="status_id")
	private ReimbursementStatus reimbursementStatus;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(nullable = false, name="type_id")
	private ReimbursementType reimbursementType;
	
	public int getReimbursementId() {
		return reimbursementId;
	}
	public double getAmount() {
		return amount;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public Date getResolved() {
		return resolved;
	}
	public String getDescription() {
		return description;
	}
	public String getReceipt() {
		return receipt;
	}
	public User getAuthor() {
		return author;
	}
	public User getResolver() {
		return resolver;
	}
	public ReimbursementStatus getReimbursementStatus() {
		return reimbursementStatus;
	}
	public ReimbursementType getReimbursementType() {
		return reimbursementType;
	}
	public void setReimbursementId(int reimbursementId) {
		this.reimbursementId = reimbursementId;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public void setResolver(User resolver) {
		this.resolver = resolver;
	}
	public void setReimbursementStatus(ReimbursementStatus reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}
	public void setReimbursementType(ReimbursementType reimbursementType) {
		this.reimbursementType = reimbursementType;
	}   
}
