package com.tlcb.bdp.model;

import java.io.Serializable;

public class DbInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private java.lang.String dbAlias;
    private java.lang.String dbDesc;
    private java.lang.Integer dbId;
    private java.lang.String dbIp;
    private java.lang.String dbName;
    private java.lang.String dbPassword;
    private java.lang.String dbSchema;
    private java.lang.String dbStatus;
    private java.lang.String dbType;
    private java.lang.String dbUser;

    public java.lang.String getDbAlias() {
        return dbAlias;
    }

    public void setDbAlias(java.lang.String dbAlias) {
        this.dbAlias = dbAlias;
    }

    public java.lang.String getDbDesc() {
        return dbDesc;
    }

    public void setDbDesc(java.lang.String dbDesc) {
        this.dbDesc = dbDesc;
    }

    public java.lang.Integer getDbId() {
        return dbId;
    }

    public void setDbId(java.lang.Integer dbId) {
        this.dbId = dbId;
    }

    public java.lang.String getDbIp() {
        return dbIp;
    }

    public void setDbIp(java.lang.String dbIp) {
        this.dbIp = dbIp;
    }

    public java.lang.String getDbName() {
        return dbName;
    }

    public void setDbName(java.lang.String dbName) {
        this.dbName = dbName;
    }

    public java.lang.String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(java.lang.String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public java.lang.String getDbSchema() {
        return dbSchema;
    }

    public void setDbSchema(java.lang.String dbSchema) {
        this.dbSchema = dbSchema;
    }

    public java.lang.String getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(java.lang.String dbStatus) {
        this.dbStatus = dbStatus;
    }

    public java.lang.String getDbType() {
        return dbType;
    }

    public void setDbType(java.lang.String dbType) {
        this.dbType = dbType;
    }

    public java.lang.String getDbUser() {
        return dbUser;
    }

    public void setDbUser(java.lang.String dbUser) {
        this.dbUser = dbUser;
    }
}
