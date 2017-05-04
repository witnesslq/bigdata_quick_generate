package com.tlcb.bdp.model;

import java.io.Serializable;
import java.util.Date;

public class OperationLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String userId;
	private String processType;
	private String userName;
	private String processDesc;
	private String targetTable;
	private String targetColumn;
	private Date processTime;
	private String targetDb;
	private String targetXls;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public String getTargetTable() {
		return targetTable;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public String getTargetColumn() {
		return targetColumn;
	}

	public void setTargetColumn(String targetColumn) {
		this.targetColumn = targetColumn;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public String getTargetDb() {
		return targetDb;
	}

	public void setTargetDb(String targetDb) {
		this.targetDb = targetDb;
	}

	public String getTargetXls() {
		return targetXls;
	}

	public void setTargetXls(String targetXls) {
		this.targetXls = targetXls;
	}

	
}
