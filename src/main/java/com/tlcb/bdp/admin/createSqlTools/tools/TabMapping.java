package com.tlcb.bdp.admin.createSqlTools.tools;

import java.io.Serializable;

public class TabMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1880998959896301373L;

	
	private String SRC_SYS_NM;
	
	private String SRC_TAB_NM;
	
	private String TAB_MAPPING;

	public String getSRC_SYS_NM() {
		return SRC_SYS_NM;
	}

	public void setSRC_SYS_NM(String sRC_SYS_NM) {
		SRC_SYS_NM = sRC_SYS_NM;
	}

	public String getSRC_TAB_NM() {
		return SRC_TAB_NM;
	}

	public void setSRC_TAB_NM(String sRC_TAB_NM) {
		SRC_TAB_NM = sRC_TAB_NM;
	}

	public String getTAB_MAPPING() {
		return TAB_MAPPING;
	}

	public void setTAB_MAPPING(String tAB_MAPPING) {
		TAB_MAPPING = tAB_MAPPING;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
