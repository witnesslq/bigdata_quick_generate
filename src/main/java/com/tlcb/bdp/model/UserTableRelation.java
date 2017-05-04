package com.tlcb.bdp.model;

import java.io.Serializable;

public class UserTableRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    private java.lang.Integer relationId;
    private java.lang.String userId;
    private java.lang.String tableId;
    private java.lang.String userName;
    private java.lang.String status;
    private java.lang.String tableName;

    public java.lang.String getTableName() {
		return tableName;
	}

	public void setTableName(java.lang.String tableName) {
		this.tableName = tableName;
	}

	public java.lang.Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(java.lang.Integer relationId) {
        this.relationId = relationId;
    }

    public java.lang.String getUserId() {
        return userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    public java.lang.String getTableId() {
        return tableId;
    }

    public void setTableId(java.lang.String tableId) {
        this.tableId = tableId;
    }

    public java.lang.String getUserName() {
        return userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    public java.lang.String getStatus() {
        return status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }
}
