package com.tlcb.bdp.admin.util;

public class WDWUtil {

	public static boolean isExcel2003(String xlsPath) {
		// TODO Auto-generated method stub
		return xlsPath.matches("^.+\\.(?i)(xls)$");
	}

	public static boolean isExcel2007(String xlsPath) {
		// TODO Auto-generated method stub
		return xlsPath.matches("^.+\\.(?i)(xlsx)$");
	}

}
