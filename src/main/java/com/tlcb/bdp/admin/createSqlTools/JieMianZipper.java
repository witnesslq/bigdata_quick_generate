package com.tlcb.bdp.admin.createSqlTools;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tlcb.bdp.admin.createSqlTools.tools.ExcelObject;
import com.tlcb.bdp.model.ColumnMeta;


public class JieMianZipper {
	
	/****
	 * 
	 * 截面表增量加载存储过程_拉链型
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList,Set<ExcelObject> IsBucketField) {
//		Set<ExcelObject> IsBucketField = null;//分桶键
//		try {
//			IsBucketField = AnalyseExcel.getJieMianExcelAsFile("C:\\Users\\lvyueyue\\Desktop\\IndexDemo\\TabTypleAndBucketInfo.xlsx");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		List<String> keyColumnList = new ArrayList<>();
//		 for (SourceColumn sourceColumn : columnList) {
//				if(sourceColumn.getKeyseq()!=null &&  Integer.parseInt(sourceColumn.getKeyseq())>0){
//					keyColumnList.add(sourceColumn.getFieldNm());
//				}	
//		}
		  
		StringBuffer sb = new StringBuffer();
		
		sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
		sb.append(schema.trim());//TODO SCHEMA CORE
		sb.append(".SP_INC_"+schema.trim()+"_");
		sb.append(tableName);
		sb.append("(v_date IN string) is ");sb.append("\r\n");
		sb.append("DECLARE ");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_acctdt string;");sb.append("\r\n");
		sb.append("v_rowcnt int;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_stepnum int ;");sb.append("\r\n");
		sb.append("v_proc_stepdesc string;");sb.append("\r\n");
		sb.append("v_proc_name string;");sb.append("\r\n");
		sb.append("v_proc_step_flg int :=0;");sb.append("\r\n");
		sb.append("v_proc_exe_flg int :=0;");sb.append("\r\n");
		sb.append("v_sqlcode int;");sb.append("\r\n");
		sb.append("v_sqlerrm string;");sb.append("\r\n");sb.append("\r\n");
		
		
		sb.append("BEGIN");sb.append("\r\n");
		sb.append("select cast(v_date as date) into v_acctdt from system.dual;");sb.append("\r\n");
		sb.append("v_proc_name :=");
		sb.append("'"+schema.trim()+".SP_INC_CUR_"+schema.trim()+"_").append(tableName+"';");sb.append("\r\n");
		sb.append("v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='INC_MERGE';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("MERGE INTO ").append(schema.trim()+"."+tableName+" AS T");sb.append("\r\n");
		sb.append("USING (SELECT * FROM "+schema.trim()+"."+tableName+"_EXT WHERE inc_date=v_date) S");sb.append("\r\n");
		sb.append("ON ");
		/**
		 * 主键关联
		 */
//		for (String s : keyColumnList) {
//			sb.append("T.");
//			sb.append(s);
//          sb.append(" = S.").append(s).append(" and ");
//		}
		for (ColumnMeta sourceColumn : columnList) {
			if(sourceColumn.getPrimaryKeyFlag()!=null &&  sourceColumn.getPrimaryKeyFlag().intValue()>0){
				sb.append("T.");
				sb.append(sourceColumn.getFieldNm());
				if(sourceColumn.getDataTp().equals("CHARACTER")){
					sb.append(" = trim(S.").append(sourceColumn.getFieldNm()).append(") and ");
				}else{
					sb.append(" = S.").append(sourceColumn.getFieldNm()).append(" and ");
				}
			}	
		}
		int len2 = sb.toString().lastIndexOf("and");//delete and
		if(len2 != -1){
			sb.delete(len2, sb.length());
		}
		
		sb.append("\r\n");
		sb.append("WHEN MATCHED  THEN UPDATE SET");sb.append("\r\n");
	  /**
	   * 
	   * 分桶字段不显示
	   */
	  for(int i = 0; i< columnList.size(); i++){
			for(ExcelObject bucket:IsBucketField){
				if(!columnList.get(i).getFieldNm().equals(bucket.getBUCKET_FIELD()) && bucket.getDB_NM().equals(schema.trim()) && bucket.getTAB_NM().equals(tableName.trim()) ){
						if(i<columnList.size()-2){
							sb.append(columnList.get(i).getFieldNm());
							if(columnList.get(i).getDataTp().equals("CHARACTER")){
								sb.append("=trim(S.");
								sb.append(columnList.get(i).getFieldNm()).append("),");
							}else{
								sb.append("=S.");
								sb.append(columnList.get(i).getFieldNm()).append(",");
							}
						}
						if(i==columnList.size()-2){
							sb.append("LAST_ETL_ACG_DT=v_acctdt,");
						}
						if(i>columnList.size()-2){
							sb.append(columnList.get(i).getFieldNm());
							if(columnList.get(i).getDataTp().equals("CHARACTER")){
								sb.append("=trim(S.");
								sb.append(columnList.get(i).getFieldNm()).append("),");
							}else{
								sb.append("=S.");
								sb.append(columnList.get(i).getFieldNm()).append(",");
							}
						}
						sb.append("\r\n");
				}
			}
			
		}
		
		sb.deleteCharAt(sb.lastIndexOf(","));
		
		sb.append("WHEN NOT MATCHED THEN");sb.append("\r\n");
		sb.append("INSERT(");
		for (ColumnMeta sourceColumn : columnList) {
			sb.append(sourceColumn.getFieldNm()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(" )");sb.append("\r\n");
		sb.append("VALUES(");
		
//		for (SourceColumn sourceColumn : columnList) {
//			sb.append("S.");
//			sb.append(sourceColumn.getFieldNm()).append(",");
//		}
        for(int i = 0; i< columnList.size(); i++){
			if(i<columnList.size()-2){
				if(columnList.get(i).getDataTp().equals("CHARACTER")){
					sb.append("trim(S.");
					sb.append(columnList.get(i).getFieldNm()).append("),");
				}else{
					sb.append("S.");
					sb.append(columnList.get(i).getFieldNm()).append(",");
				}
			}
			if(i==columnList.size()-2){
				sb.append("v_acctdt,");
			}
			if(i>columnList.size()-2){
				if(columnList.get(i).getDataTp().equals("CHARACTER")){
					sb.append("trim(S.");
					sb.append(columnList.get(i).getFieldNm()).append("),");
				}else{
					sb.append("S.");
					sb.append(columnList.get(i).getFieldNm()).append(",");
				}
			}
		}
		
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(" )");
		sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");sb.append("\r\n");
		sb.append("v_proc_exe_flg :=1;");
		sb.append("\r\n");sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("EXCEPTION ");sb.append("\r\n");
		sb.append("WHEN OTHERS THEN");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) "
				+ "values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
		sb.append("\r\n");sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");
		sb.append("\r\n");sb.append("\r\n");

		sb.append("ROLLBACK;");sb.append("\r\n");
		sb.append("END;");sb.append("\r\n");
		sb.append("/");
		return sb.toString();
	}
	
}
