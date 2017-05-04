package com.tlcb.bdp.admin.createSqlTools.tools;

import java.io.Serializable;

public class SouceTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5470405437578048597L;
	
	private String tabschema;
	private String tabname;
	private String remarks;
	
	public String getTabschema() {
		return tabschema;
	}
	public void setTabschema(String tabschema) {
		this.tabschema = tabschema;
	}
	public String getTabname() {
		return tabname;
	}
	public void setTabname(String tabname) {
		this.tabname = tabname;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "SouceTable [tabschema=" + tabschema + ", tabname=" + tabname + ", remarks=" + remarks + "]";
	}
	
	
	
	
}
