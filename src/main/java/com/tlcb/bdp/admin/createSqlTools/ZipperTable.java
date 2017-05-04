package com.tlcb.bdp.admin.createSqlTools;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tlcb.bdp.model.ColumnMeta;


public class ZipperTable {
	
	
	
	/***
	 * 
	 * 拉链表存储过程
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList) {

		List<String> keyColumnList = new ArrayList<>();
		  for (ColumnMeta sourceColumn : columnList) {
				if(sourceColumn.getPrimaryKeyFlag()!=null &&  sourceColumn.getPrimaryKeyFlag().intValue()>0){
					keyColumnList.add(sourceColumn.getFieldNm());
				}	
		}
		StringBuffer sb = new StringBuffer();
		
		//for(ExcelObject zipper:zipperFieldSet){
			//if(zipper.getHS_FLAG().trim().equals("MERGE") && zipper.getDB_NM().equals(schema.trim()) && zipper.getTAB_NM().equals(tableName.trim())){
				sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
				sb.append(schema.trim());//TODO SCHEMA CORE
				sb.append(".SP_INC_"+schema.trim()+"_");
				sb.append(tableName+"_HS");sb.append("\r\n");
				sb.append("(v_date IN string) is ");sb.append("\r\n");
				
				sb.append("DECLARE ");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("v_acctdt string;");sb.append("\r\n");
				sb.append("v_rowcnt int;");sb.append("\r\n");
				sb.append("row_nm int;");sb.append("\r\n");
				sb.append("balance_sum decimal(31,6);");sb.append("\r\n");
				sb.append("v_proc_stepnum int;");sb.append("\r\n");
				sb.append("v_proc_stepdesc string;");sb.append("\r\n");
				sb.append("v_proc_name string;");sb.append("\r\n");
				sb.append("v_proc_step_flg int :=0;");sb.append("\r\n");
				sb.append("v_proc_exe_flg int :=0;");sb.append("\r\n");
				sb.append("v_sqlcode int;");sb.append("\r\n");
				sb.append("v_sqlerrm string;");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("BEGIN ");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("select cast(v_date as date) into v_acctdt from system.dual;");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("v_proc_name :=");
				sb.append("'"+schema.trim()+".SP_INC_"+schema.trim()+"_").append(tableName+"_HS';");sb.append("\r\n");
				sb.append(" v_proc_stepnum :=1;");sb.append("\r\n");
				sb.append("v_proc_stepdesc :='delete';");sb.append("\r\n");
				sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
				sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
				sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");
				

				sb.append("delete from DQ.PROC_LOG where proc_name = v_proc_name and proc_act_dt = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
				
		        sb.append("select count(1) into v_rowcnt from ");
				sb.append(""+schema.trim()+".").append(tableName).append("_HS").append(" where begindt = v_acctdt");sb.append("\r\n");
				sb.append("if v_rowcnt <> 0 then ");sb.append("\r\n");
				sb.append("delete from ");
				sb.append(""+schema.trim()+".").append(tableName).append("_HS").append(" where begindt = v_acctdt;");sb.append("\r\n");
				sb.append("end if");sb.append("\r\n");
				sb.append("select count(1) into v_rowcnt from ");
				sb.append(""+schema.trim()+".").append(tableName).append("_HS").append(" where enddt = v_acctdt");sb.append("\r\n");
				sb.append("if v_rowcnt <> 0 then ");sb.append("\r\n");
				sb.append("update ");
				sb.append(""+schema.trim()+".").append(tableName).append("_HS").append(" set enddt = '2999-12-31' where enddt = v_acctdt");sb.append("\r\n");
				sb.append("end if");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("v_proc_step_flg :=1;");sb.append("\r\n");
				sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");sb.append("\r\n");sb.append("\r\n");  
				
				sb.append("v_proc_stepnum := v_proc_stepnum+1;");sb.append("\r\n");
				sb.append("v_proc_stepdesc :='insert';");sb.append("\r\n");
				sb.append("v_proc_step_flg :=0;");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("insert into table ");
				sb.append(""+schema.trim()+".").append(tableName).append("_HS ").append("(begindt,enddt,");
                
				/**
				 * 
				 *   加字段
				 */
				for (ColumnMeta sourceColumn : columnList) {
					sb.append(sourceColumn.getFieldNm()).append(",");
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append(" )");
				
				
				sb.append(" select v_acctdt as begindt, '2999-12-31' as enddt,");
				/**
				 * 当字段类型为VARCHAR时,加TRIM()
				 */
				for (ColumnMeta sourceColumn : columnList) {
					if(sourceColumn.getDataTp().equals("CHARACTER")){
						sb.append("trim("+sourceColumn.getFieldNm()+")").append(" as "+sourceColumn.getFieldNm()).append(",");
					}else{
					    sb.append(sourceColumn.getFieldNm()).append(",");
					}
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				
				sb.append(" from ");
				sb.append(""+schema.trim()+".").append(tableName).append("_EXT");
				sb.append(" where inc_date=v_date;");
				sb.append("\r\n");sb.append("\r\n");

				sb.append("v_proc_step_flg :=1;");sb.append("\r\n");sb.append("\r\n");
				sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");sb.append("\r\n");sb.append("\r\n");
				sb.append("\r\n");
				sb.append("v_proc_stepnum := v_proc_stepnum+1;");sb.append("\r\n");
				sb.append("v_proc_stepdesc :='merge';");sb.append("\r\n");
				sb.append("v_proc_step_flg :=0;");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("MERGE INTO ");
				sb.append(""+schema.trim()+".").append(tableName).append("_HS").append(" m USING ").append("(");
				sb.append("SELECT a.begindt as begindt,");
				for (String s : keyColumnList) {
					sb.append("a.");
					sb.append(s);
					sb.append(" as ").append(s).append(",");
				}
					int len = sb.toString().lastIndexOf(",");//delete ，
					if(len!=-1){
						sb.deleteCharAt(len);
					}
				
				sb.append(" from ");
				sb.append(" "+schema.trim()+".").append(tableName).append("_HS a").append(" INNER JOIN (SELECT ");
				/***
				 * major into 加字段,当字段类型为varchar时加trim().
				 */
				for (ColumnMeta sourceColumn : columnList) {
					if(sourceColumn.getDataTp().equals("CHARACTER")){
						sb.append("trim("+sourceColumn.getFieldNm()+")").append(" as "+sourceColumn.getFieldNm()).append(",");
					}else{
					    sb.append(sourceColumn.getFieldNm()).append(",");
					}
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append(" from ");
				
				
				sb.append(""+schema.trim()+".").append(tableName).append("_EXT ").append("WHERE inc_date = v_date) b ON ");
			
				
				for (String s : keyColumnList) {
					sb.append("a.");
					sb.append(s);
					sb.append(" = b.").append(s).append(" and ");
				}
				
				int len2 = sb.toString().lastIndexOf("and");//delete ，
				if(len2 != -1){
					sb.delete(len2, sb.length());
				}
				
				sb.append(" WHERE a.begindt <=DATE_SUB(v_acctdt,1)  AND a.enddt >DATE_SUB(v_acctdt,1)) c ");
				sb.append("ON (m.begindt=c.begindt and ");
				
				for (String s : keyColumnList) {
					sb.append("m.");
					sb.append(s);
					sb.append(" = c.").append(s).append(" and ");
				}
				
				int len3 = sb.toString().lastIndexOf("and");//delete ，
				if(len3 != -1){
					sb.delete(len3, sb.length());
				}

				sb.append(")");
				sb.append(" WHEN MATCHED THEN UPDATE SET enddt = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
				
				
				sb.append("v_proc_step_flg :=1;");sb.append("\r\n");sb.append("\r\n");
				sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("v_proc_exe_flg :=1;");sb.append("\r\n");
				sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("EXCEPTION ");sb.append("\r\n");
				sb.append("WHEN OTHERS THEN ");sb.append("\r\n");
				sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
				sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");
				sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) "
						+ "values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append("put_line(v_proc_exe_flg);");
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append("ROLLBACK;");
				sb.append("\r\n");
				sb.append("END;");
				sb.append("\r\n");
				sb.append("/");
			//}
		//}
		return sb.toString();
	}
}
