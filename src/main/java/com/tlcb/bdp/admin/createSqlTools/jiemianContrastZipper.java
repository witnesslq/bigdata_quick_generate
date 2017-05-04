package com.tlcb.bdp.admin.createSqlTools;

import java.util.List;

import com.tlcb.bdp.model.ColumnMeta;


public class jiemianContrastZipper {
	/****
	 * 
	 * 截面表与拉链表中取出截面对比存储过程--拉链型
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
		sb.append(schema.trim());//TODO SCHEMA CORE
		sb.append(".SP_EXC_"+schema.trim()+"_");
		sb.append(tableName);
		sb.append(" (v_date IN string) is ");sb.append("\r\n");
		sb.append("DECLARE ");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_acctdt string;");sb.append("\r\n");
		sb.append("v_rowcnt int;");sb.append("\r\n");sb.append("\r\n");
		sb.append("v_cur_hs_diff_cnt int;");sb.append("\r\n");
		sb.append("v_hs_cur_diff_cnt int;");sb.append("\r\n");
		sb.append("v_chk_flag boolean;");sb.append("\r\n");
		sb.append("v_table_name string :='").append(schema.trim()+"."+tableName+"'");
		sb.append("\r\n");sb.append("\r\n");
		
		
		sb.append("v_proc_stepnum int ;");sb.append("\r\n");
		sb.append("v_proc_stepdesc string;");sb.append("\r\n");
		sb.append("v_proc_name string;");sb.append("\r\n");
		sb.append("v_proc_step_flg int :=0;");sb.append("\r\n");
		sb.append("v_proc_exe_flg int :=0;");sb.append("\r\n");
		sb.append("v_sqlcode int;");sb.append("\r\n");
		sb.append("v_sqlerrm string;");sb.append("\r\n");sb.append("\r\n");
		
		
		sb.append("BEGIN");sb.append("\r\n");
		sb.append("select cast(v_date as date) into v_acctdt from system.dual;");sb.append("\r\n");
		sb.append("delete from DQ.PROC_LOG where proc_name = v_proc_name and proc_act_dt = v_acctdt;");sb.append("\r\n");
		sb.append("delete from DQ.CUR_HS_COMPARE where cur_tab_nm = v_table_name and bsn_dt = v_acctdt and chk_dt= to_char(current_date(),'yyyy-MM-dd');");
		sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_name :=");
		sb.append("'"+schema.trim()+".SP_EXC_"+schema.trim()+"_").append(tableName+"';");sb.append("\r\n");
		sb.append("v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='EXCEPT';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");
		
	    sb.append("select count(*) into v_cur_hs_diff_cnt from ("
	    		+ "(select ");
	    for (ColumnMeta sourceColumn : columnList) {
			sb.append(sourceColumn.getFieldNm()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(" from "+schema.trim()+"."+tableName+") "
				+ "except (select ");
		for(ColumnMeta sourceColumn : columnList){
			sb.append(sourceColumn.getFieldNm()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(" from "+schema.trim()+"."+tableName+"_HS where begindt<=v_acctdt AND enddt>v_acctdt));");sb.append("\r\n");
		
		sb.append("select count(*) into v_hs_cur_diff_cnt from ((select ");
		for(ColumnMeta sourceColumn : columnList){
			sb.append(sourceColumn.getFieldNm()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(" from ").append(schema.trim()+"."+tableName+"_HS where begindt<=v_acctdt AND enddt>v_acctdt)");
		sb.append(" except (select ");
		for(ColumnMeta sourceColumn : columnList){
			sb.append(sourceColumn.getFieldNm()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(" from ").append(schema.trim()+"."+tableName+"));");
		sb.append("\r\n");sb.append("\r\n");
		
		sb.append("if (v_cur_hs_diff_cnt = 0 and v_hs_cur_diff_cnt = 0) then ");sb.append("\r\n");
		sb.append("v_chk_flag := 'CORRECT';");sb.append("\r\n");
		sb.append("else");sb.append("\r\n");
		sb.append("v_chk_flag := 'ERROR';");sb.append("\r\n");
		sb.append("end if;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("insert into dq.cur_hs_compare (chk_dt,bsn_dt,sp_nm,cur_tab_nm,cur_hs_diff_cnt,hs_cur_diff_cnt,ppn_tm,chk_f) values (to_char(current_date(),'yyyy-MM-dd'),"
				+ "v_acctdt,v_proc_name,v_table_name,v_cur_hs_diff_cnt,v_hs_cur_diff_cnt,systimestamp,v_chk_flag);");
		sb.append("\r\n");
		
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
