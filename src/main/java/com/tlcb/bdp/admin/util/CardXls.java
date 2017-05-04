package com.tlcb.bdp.admin.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardXls {
	
	
	private static Logger logger = LoggerFactory.getLogger(CardXls.class);
	
	public static Map<String,List<Object>> readXls(String xlsPath) throws IOException{
		Map<String, List<Object>> map = new  HashMap<String,List<Object>>();
		
		List<Object> list1 = new ArrayList<Object>();
		
		List<Object> list2 = new ArrayList<Object>();

		List<Object> list3 = new ArrayList<Object>();

	/*	if(xlsPath == null || !(WDWUtil.isExcel2003(xlsPath) || WDWUtil.isExcel2007(xlsPath))){
			map.put("msg", "文件名不是excel");
			return map;
		}
		
		File file = new File(xlsPath);
		if(null==file || !file.exists()){
			map.put("msg", "文件不存在");
			return map;
		}*/
		
		InputStream is = new FileInputStream(xlsPath);
		Workbook wb = null;
		
		if(WDWUtil.isExcel2003(xlsPath)){
			wb = new HSSFWorkbook(is);
		}else{
			wb = new XSSFWorkbook(is);
		}
		
		for(int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++){
			Sheet sheet = wb.getSheetAt(numSheet);
			if(sheet == null){
				continue;
			}
			
			if("BUCKET_PARTITION_INFO".equals(sheet.getSheetName())){
				
				for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
					Row row = sheet.getRow(rowNum);
					if(row==null){
						continue;
					}
				
					BUCKET_PARTITION_INFO bucket_partition_info = new BUCKET_PARTITION_INFO();
					bucket_partition_info.setDB_DM(getValue(row.getCell(0)));
					bucket_partition_info.setTAB_NM(getValue(row.getCell(1)));
					bucket_partition_info.setTAB_TYPE(getValue(row.getCell(2)));
					bucket_partition_info.setBUCKET_FIELD(getValue(row.getCell(3)));
					bucket_partition_info.setPARTITION_TYPE(getValue(row.getCell(4)));
					bucket_partition_info.setPARTITION_FIELD(getValue(row.getCell(5)));
					bucket_partition_info.setHIS_BUCKET_NUM(getValue(row.getCell(6)));
					bucket_partition_info.setSECTION_PARTITION_TYPE(getValue(row.getCell(7)));
					bucket_partition_info.setSECTION_PARTITION_FIELD(getValue(row.getCell(8)));
					bucket_partition_info.setSECTION_BUCKET_NUM(getValue(row.getCell(9)));
					bucket_partition_info.setHIS_TOTAL_CNT(getValue(row.getCell(10)));
					bucket_partition_info.setINC_CNT(getValue(row.getCell(11)));
					bucket_partition_info.setRECORD_AVG_SIZE(getValue(row.getCell(12)));
					
					list1.add(bucket_partition_info);
				}
				
			}
			
				if("MAPPING_CL_KEY".equals(sheet.getSheetName())){
				
				for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
					Row row = sheet.getRow(rowNum);
					if(row==null){
						continue;
					}
				
					MAPPING_CL_KEY mapping_cl_key = new MAPPING_CL_KEY();
					mapping_cl_key.setDB_NM(getValue(row.getCell(0)));
					mapping_cl_key.setTAB_NM(getValue(row.getCell(1)));
					mapping_cl_key.setCL_KEY(getValue(row.getCell(2)));
					
					list2.add(mapping_cl_key);
				}
				
			}
			
				if("MAPPING_AMT_KEY".equals(sheet.getSheetName())){
					
					for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
						Row row = sheet.getRow(rowNum);
						if(row==null){
							continue;
						}
					
						MAPPING_AMT_KEY mapping_amt_key = new MAPPING_AMT_KEY();
						mapping_amt_key.setDB_NM(getValue(row.getCell(0)));
						mapping_amt_key.setTAB_NM(getValue(row.getCell(1)));
						mapping_amt_key.setAMT_KEY(getValue(row.getCell(2)));
						
						list3.add(mapping_amt_key);
					}
					
				}
			
		}
		
			map.put("bucket_partition_info", list1);
			map.put("mapping_cl_key", list2);
			map.put("mapping_amt_key", list3);
		
		return map;
	}

	private static String getValue(Cell cell) {
		
		String cellValue = "";
		if(null != cell){
			switch(cell.getCellType()){
			case HSSFCell.CELL_TYPE_NUMERIC:
				DecimalFormat df = new DecimalFormat("0");
				cellValue = df.format(cell.getNumericCellValue());
				break;
				
			case HSSFCell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			
			case HSSFCell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			}	
		}
		return cellValue;
	}
}
