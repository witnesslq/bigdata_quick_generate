package com.tlcb.bdp.admin.createSqlTools;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.Pipe.SourceChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tlcb.bdp.model.ColumnMeta;


public class FlowWater {
	
	/****
	 * 
	 * 流水表增量导入存储过程
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList) {
		
		     StringBuffer sb = new StringBuffer();
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
				sb.append("v_sqlerrm string; ");sb.append("\r\n");sb.append("\r\n");
				
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
			    sb.append("delete from ");
				sb.append(""+schema.trim()+".").append(tableName).append("_HS").append(" where LAST_ETL_ACG_DT = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("v_proc_step_flg :=1;");sb.append("\r\n");sb.append("\r\n");
				sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append("v_proc_stepnum := v_proc_stepnum+1;");sb.append("\r\n");
				sb.append("v_proc_stepdesc :='insert';");sb.append("\r\n");
				sb.append("v_proc_step_flg :=0;");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("insert into table ");
				sb.append(""+schema.trim()+".").append(tableName).append("_HS").append(" (");
				for (ColumnMeta sourceColumn : columnList) {
					sb.append(sourceColumn.getFieldNm()).append(",");
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append(" )");
				sb.append("\r\n");
				sb.append("select ");
				for (ColumnMeta sourceColumn : columnList) {
					if(sourceColumn.getDataTp().equals("CHARACTER")){
					   sb.append("trim("+sourceColumn.getFieldNm()+")").append(",");
					}else{
					   sb.append(sourceColumn.getFieldNm()).append(",");
					}
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append("\r\n");
				sb.append("from ");
				sb.append(""+schema.trim()+".").append(tableName).append("_EXT");
				sb.append(" where inc_date=v_date;");sb.append("\r\n");sb.append("\r\n");
				
				sb.append("v_proc_step_flg :=1;");sb.append("\r\n");
				sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");sb.append("\r\n");sb.append("\r\n");
				sb.append("v_proc_exe_flg :=1;");sb.append("\r\n");
				sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
				sb.append("EXCEPTION ");sb.append("\r\n");
				sb.append("WHEN OTHERS THEN");sb.append("\r\n");
				sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
				sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");
				sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
				sb.append("\r\n");
				sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
				sb.append("END;");
				sb.append("\r\n");
				sb.append("/");
		return sb.toString();
	}


	public static void writeToLocal(String fileName,String content){
		File file = new File(fileName);
		if(file.exists()){
			file.delete();
		}
		
		try {
			OutputStream out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
