package com.tlcb.bdp.admin.util;

public class BUCKET_PARTITION_INFO {
	
	private String DB_DM;
	
	private String TAB_NM;
	
	private String TAB_TYPE;
	
	private  String BUCKET_FIELD;
	
	private String PARTITION_TYPE;
	
	private String PARTITION_FIELD;
	
	private String HIS_BUCKET_NUM;
	
	
	private String SECTION_PARTITION_TYPE;
	
	private String SECTION_PARTITION_FIELD;
	
	private String SECTION_BUCKET_NUM;
	
	private String HIS_TOTAL_CNT;
	
	private String INC_CNT;
	
	private String RECORD_AVG_SIZE;

	public String getDB_DM() {
		return DB_DM;
	}

	public void setDB_DM(String dB_DM) {
		DB_DM = dB_DM;
	}

	public String getTAB_NM() {
		return TAB_NM;
	}

	public void setTAB_NM(String tAB_NM) {
		TAB_NM = tAB_NM;
	}

	public String getTAB_TYPE() {
		return TAB_TYPE;
	}

	public void setTAB_TYPE(String tAB_TYPE) {
		TAB_TYPE = tAB_TYPE;
	}

	public String getBUCKET_FIELD() {
		return BUCKET_FIELD;
	}

	public void setBUCKET_FIELD(String bUCKET_FIELD) {
		BUCKET_FIELD = bUCKET_FIELD;
	}

	public String getPARTITION_TYPE() {
		return PARTITION_TYPE;
	}

	public void setPARTITION_TYPE(String pARTITION_TYPE) {
		PARTITION_TYPE = pARTITION_TYPE;
	}

	public String getPARTITION_FIELD() {
		return PARTITION_FIELD;
	}

	public void setPARTITION_FIELD(String pARTITION_FIELD) {
		PARTITION_FIELD = pARTITION_FIELD;
	}

	public String getHIS_BUCKET_NUM() {
		return HIS_BUCKET_NUM;
	}

	public void setHIS_BUCKET_NUM(String hIS_BUCKET_NUM) {
		HIS_BUCKET_NUM = hIS_BUCKET_NUM;
	}

	public String getSECTION_PARTITION_TYPE() {
		return SECTION_PARTITION_TYPE;
	}

	public void setSECTION_PARTITION_TYPE(String sECTION_PARTITION_TYPE) {
		SECTION_PARTITION_TYPE = sECTION_PARTITION_TYPE;
	}

	public String getSECTION_PARTITION_FIELD() {
		return SECTION_PARTITION_FIELD;
	}

	public void setSECTION_PARTITION_FIELD(String sECTION_PARTITION_FIELD) {
		SECTION_PARTITION_FIELD = sECTION_PARTITION_FIELD;
	}

	public String getSECTION_BUCKET_NUM() {
		return SECTION_BUCKET_NUM;
	}

	public void setSECTION_BUCKET_NUM(String sECTION_BUCKET_NUM) {
		SECTION_BUCKET_NUM = sECTION_BUCKET_NUM;
	}

	public String getHIS_TOTAL_CNT() {
		return HIS_TOTAL_CNT;
	}

	public void setHIS_TOTAL_CNT(String hIS_TOTAL_CNT) {
		HIS_TOTAL_CNT = hIS_TOTAL_CNT;
	}

	public String getINC_CNT() {
		return INC_CNT;
	}

	public void setINC_CNT(String iNC_CNT) {
		INC_CNT = iNC_CNT;
	}

	public String getRECORD_AVG_SIZE() {
		return RECORD_AVG_SIZE;
	}

	public void setRECORD_AVG_SIZE(String rECORD_AVG_SIZE) {
		RECORD_AVG_SIZE = rECORD_AVG_SIZE;
	}

	@Override
	public String toString() {
		return "BUCKET_PARTITION_INFO [DB_DM=" + DB_DM + ", TAB_NM=" + TAB_NM + ", TAB_TYPE=" + TAB_TYPE
				+ ", BUCKET_FIELD=" + BUCKET_FIELD + ", PARTITION_TYPE=" + PARTITION_TYPE + ", PARTITION_FIELD="
				+ PARTITION_FIELD + ", HIS_BUCKET_NUM=" + HIS_BUCKET_NUM + ", SECTION_PARTITION_TYPE="
				+ SECTION_PARTITION_TYPE + ", SECTION_PARTITION_FIELD=" + SECTION_PARTITION_FIELD
				+ ", SECTION_BUCKET_NUM=" + SECTION_BUCKET_NUM + ", HIS_TOTAL_CNT=" + HIS_TOTAL_CNT + ", INC_CNT="
				+ INC_CNT + ", RECORD_AVG_SIZE=" + RECORD_AVG_SIZE + "]";
	}
	
	
	

}
