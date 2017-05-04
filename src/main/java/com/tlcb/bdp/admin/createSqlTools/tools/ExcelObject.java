package com.tlcb.bdp.admin.createSqlTools.tools;
public class ExcelObject  implements java.io.Serializable{

	/**
	 */
	private static final long serialVersionUID = 2443031485695075474L;
	
	
	private String DB_NM;
	private String TAB_NM;
	private String HS_FLAG;
	private String TAB_FLD_NM;
	private String BUCKET_FIELD;
	
	
	
	public String getTAB_FLD_NM() {
		return TAB_FLD_NM;
	}

	public void setTAB_FLD_NM(String tAB_FLD_NM) {
		TAB_FLD_NM = tAB_FLD_NM;
	}

	public String getDB_NM() {
		return DB_NM;
	}

	public void setDB_NM(String dB_NM) {
		DB_NM = dB_NM;
	}

	public String getTAB_NM() {
		return TAB_NM;
	}

	public void setTAB_NM(String tAB_NM) {
		TAB_NM = tAB_NM;
	}

	public String getHS_FLAG() {
		return HS_FLAG;
	}

	public void setHS_FLAG(String hS_FLAG) {
		HS_FLAG = hS_FLAG;
	}
	
	public String getBUCKET_FIELD() {
		return BUCKET_FIELD;
	}

	public void setBUCKET_FIELD(String bUCKET_FIELD) {
		BUCKET_FIELD = bUCKET_FIELD;
	}

	

	

	@Override
	public String toString() {
		return "ExcelObject [DB_NM=" + DB_NM + ", TAB_NM=" + TAB_NM + ", HS_FLAG=" + HS_FLAG + ", TAB_FLD_NM="
				+ TAB_FLD_NM + ", BUCKET_FIELD=" + BUCKET_FIELD + "]";
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BUCKET_FIELD == null) ? 0 : BUCKET_FIELD.hashCode());
		result = prime * result + ((DB_NM == null) ? 0 : DB_NM.hashCode());
		result = prime * result + ((HS_FLAG == null) ? 0 : HS_FLAG.hashCode());
		result = prime * result + ((TAB_FLD_NM == null) ? 0 : TAB_FLD_NM.hashCode());
		result = prime * result + ((TAB_NM == null) ? 0 : TAB_NM.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExcelObject other = (ExcelObject) obj;
		if (BUCKET_FIELD == null) {
			if (other.BUCKET_FIELD != null)
				return false;
		} else if (!BUCKET_FIELD.equals(other.BUCKET_FIELD))
			return false;
		if (DB_NM == null) {
			if (other.DB_NM != null)
				return false;
		} else if (!DB_NM.equals(other.DB_NM))
			return false;
		if (HS_FLAG == null) {
			if (other.HS_FLAG != null)
				return false;
		} else if (!HS_FLAG.equals(other.HS_FLAG))
			return false;
		if (TAB_FLD_NM == null) {
			if (other.TAB_FLD_NM != null)
				return false;
		} else if (!TAB_FLD_NM.equals(other.TAB_FLD_NM))
			return false;
		if (TAB_NM == null) {
			if (other.TAB_NM != null)
				return false;
		} else if (!TAB_NM.equals(other.TAB_NM))
			return false;
		return true;
	}
	
	

}
