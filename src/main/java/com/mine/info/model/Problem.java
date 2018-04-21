package com.mine.info.model;

import java.util.Date;


public class Problem {
	int id; 
	
	String problem; 
	
	String reasonForProblem; 
	
	String solution; 
	
	Date submitDate; 
	
	Date modifiedDate;
	
	Technology technology;
	
	public Problem() {
	}
	
	public Problem(int id, String problem, String reasonForProblem, String solution, Date submitDate, Date modifiedDate, Technology technology) {
		super();
		this.id = id;
		this.problem = problem;
		this.reasonForProblem = reasonForProblem;
		this.solution = solution; 
		this.submitDate = submitDate;
		this.modifiedDate = modifiedDate;
		this.technology = technology;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getReasonForProblem() {
		return reasonForProblem;
	}
	
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setReasonForProblem(String reasonForProblem) {
		this.reasonForProblem = reasonForProblem;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}
	
}
