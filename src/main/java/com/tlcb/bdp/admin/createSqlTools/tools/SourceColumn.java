package com.tlcb.bdp.admin.createSqlTools.tools;

import java.io.Serializable;

public class SourceColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8496668892130806974L;
	
	private String tabname;
	private String tabschema;
	private String keyseq;
	private String isPrimaryKey;
	
	private String colname;
	private String typename;
	private String colno; 
	private String length;
	private String scale;
	private String nulls;
	private String remarks;
	
	
	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}
	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public String getTabname() {
		return tabname;
	}
	public void setTabname(String tabname) {
		this.tabname = tabname;
	}
	public String getTabschema() {
		return tabschema;
	}
	public void setTabschema(String tabschema) {
		this.tabschema = tabschema;
	}
	public String getColname() {
		return colname;
	}
	public void setColname(String colname) {
		this.colname = colname;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getNulls() {
		return nulls;
	}
	public void setNulls(String nulls) {
		this.nulls = nulls;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getColno() {
		return colno;
	}
	public void setColno(String colno) {
		this.colno = colno;
	}
	public String getKeyseq() {
		return keyseq;
	}
	public void setKeyseq(String keyseq) {
		this.keyseq = keyseq;
	}
	
	@Override
	public String toString() {
		return "SourceColumn [tabname=" + tabname + ", tabschema=" + tabschema + ", keyseq=" + keyseq + ", colname="
				+ colname + ", typename=" + typename + ", colno=" + colno + ", length=" + length + ", scale=" + scale
				+ ", nulls=" + nulls + ", remarks=" + remarks + "]";
	}
	
}
