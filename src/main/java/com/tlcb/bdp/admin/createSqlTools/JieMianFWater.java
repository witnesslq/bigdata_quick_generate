package com.tlcb.bdp.admin.createSqlTools;

import java.util.List;

import com.tlcb.bdp.model.ColumnMeta;


public class JieMianFWater {
	/****
	 * 
	 * 截面表增量加载存储过程_流水型
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList) {
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
		sb.append("'"+schema.trim()+".SP_INC_"+schema.trim()+"_").append(tableName+"';");sb.append("\r\n");
		sb.append("v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='INC_MERGE';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("DELETE FROM ").append(schema.trim()+"."+tableName+";");sb.append("\r\n");sb.append("\r\n");
		sb.append("INSERT INTO "+schema.trim()+"."+tableName+"(");
		for (ColumnMeta sourceColumn : columnList) {
			sb.append(sourceColumn.getFieldNm()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(" )");
		sb.append("\r\n");
		
		sb.append("SELECT ");

		for(int i=0;i<columnList.size();i++){
			if(i<columnList.size()-2){
				if(columnList.get(i).getDataTp().equals("CHARACTER")){
					sb.append("trim(S."+columnList.get(i).getFieldNm()+")").append(",");;
				}else{
					sb.append("S.");
					sb.append(columnList.get(i).getFieldNm()).append(",");
				}
			}
			if(i == columnList.size()-2){
				sb.append("v_acctdt,");
			}
			if(i>columnList.size()-2){
				if(columnList.get(i).getDataTp().equals("CHARACTER")){
					sb.append("trim(S."+columnList.get(i).getFieldNm()+")").append(",");;
				}else{
					sb.append("S.");
					sb.append(columnList.get(i).getFieldNm()).append(",");
				}
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		
		sb.append(" FROM ");
		sb.append(schema.trim()+"."+tableName+"_EXT S WHERE S.inc_date=v_date ;");
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
