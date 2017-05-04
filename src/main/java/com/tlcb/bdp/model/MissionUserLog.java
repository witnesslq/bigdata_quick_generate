package com.tlcb.bdp.model;

import java.io.Serializable;
import java.util.Date;

public class MissionUserLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private java.lang.Integer id;
    private java.lang.String userId;
    private java.lang.String processType;
    private java.lang.String userName;
    private java.lang.String targetTable;
    private java.lang.String processDesc;
    private Date processTime;
    

    public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    public java.lang.String getUserId() {
        return userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    public java.lang.String getProcessType() {
        return processType;
    }

    public void setProcessType(java.lang.String processType) {
        this.processType = processType;
    }

    public java.lang.String getUserName() {
        return userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    public java.lang.String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(java.lang.String targetTable) {
        this.targetTable = targetTable;
    }

    public java.lang.String getProcessDesc() {
        return processDesc;
    }

    public void setProcessDesc(java.lang.String processDesc) {
        this.processDesc = processDesc;
    }

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

    
}
