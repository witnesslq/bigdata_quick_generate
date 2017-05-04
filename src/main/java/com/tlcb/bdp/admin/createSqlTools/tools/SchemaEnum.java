package com.tlcb.bdp.admin.createSqlTools.tools;

public enum SchemaEnum {
	
//	ADMS("ADMS"),BCRD("BCRD"),BILL("BILL"),CDC("CDC"),CIPS("CIPS"),CORE("CORE"),CPSP("CPSP"),CQCS("CQCS")
//	,CRDS("CRDS"),CSMS("CSMS"),CSTCAL("CSTCAL"),CZCR("CZCR"),CZDX("CZDX"),CZLN("CZLN"),CZLXJT("CZLXJT"),
//	DXPT("DXPT"),EBNK("EBNK"),ECIF("ECIF"),FINC("FINC"),FRHD("FRHD"),GAPS("GAPS"),HRMS("HRMS"),
//	IBIZ("IBIZ"),ICRD("ICRD"),IFMP("IFMP"),IFTP("IFTP"),LOAN("LOAN"),LXJT("LXJT"),MBBK("MBBK"),
//	NACE("NACE"),NCMS("NCMS"),OCRM("OCRM"),OPIC("OPIC"),PTMS("PTMS"),PYMT("PYMT"),SEAL("SEAL"),
//	SPMT("SPMT"),WIND("WIND"),WYHL("WYHL"),XTCR("XTCR"),XWDT("XWDT"),SOR("SOR"),SMY("SMY");
	CORE("CORE"),EBNK("EBNK"),LOAN("LOAN"),SOR("SOR");
	private String dataLayer;

	SchemaEnum(String dataLayer){
		this.dataLayer = dataLayer;
	}

	public String getDataLayer(){
		return this.dataLayer;
	}

	@Override
	public String toString(){
		return dataLayer;
	}
	
}