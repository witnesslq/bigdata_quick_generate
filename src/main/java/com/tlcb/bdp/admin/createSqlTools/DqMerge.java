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

import com.tlcb.bdp.admin.createSqlTools.tools.ExcelObject;
import com.tlcb.bdp.model.ColumnMeta;


public class DqMerge {

	/****
	 * 
	 * dq 流水统计存储过程
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList,Set<ExcelObject> codeFieldSet,Set<ExcelObject> amountFieldSet) {
/*		Set<ExcelObject> codeFieldSet = null;//码值
		Set<ExcelObject> amountFieldSet = null;//金额
		try {
			 codeFieldSet = AnalyseExcel.getDQExcelAsFile("C:\\Users\\lvyueyue\\Desktop\\IndexDemo\\MAPPING_CL.xlsx");
			 amountFieldSet = AnalyseExcel.getDQExcelAsFile("C:\\Users\\lvyueyue\\Desktop\\IndexDemo\\MAPPING_AMT.xlsx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		StringBuffer sb = new StringBuffer();
				
		sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
		sb.append(schema.trim());//TODO SCHEMA CORE
		sb.append(".SP_DQ_COUNT_COMPARE_"+schema.trim()+"_");
		sb.append(tableName);
		sb.append(" (v_date IN string) is ");sb.append("\r\n");
		
		sb.append("DECLARE ");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_acctdt string;");sb.append("\r\n");
		sb.append("v_rowcnt int;");sb.append("\r\n");
		sb.append("row_nm int;");sb.append("\r\n");
		sb.append("balance_sum decimal(31,6);");sb.append("\r\n");
		sb.append("v_proc_stepnum int ;");sb.append("\r\n");
		sb.append("v_proc_stepdesc string;");sb.append("\r\n");
		sb.append("v_proc_name string;");sb.append("\r\n");
		sb.append("v_proc_step_flg int :=0;");sb.append("\r\n");
		sb.append("v_proc_exe_flg int :=0;");sb.append("\r\n");
		sb.append("v_sqlcode int;");sb.append("\r\n");
		sb.append("v_sqlerrm string;");sb.append("\r\n");
		sb.append("\r\n");
		sb.append("FLAG_OK CONSTANT  STRING :='OK';");sb.append("\r\n");
		sb.append("FLAG_ERROR CONSTANT  STRING :='ERROR';");sb.append("\r\n");
		sb.append("\r\n");
		sb.append("val_COUNT_DB2 decimal(31,6);");sb.append("\r\n");
		
		for(ExcelObject amount:amountFieldSet){
			if(amount.getDB_NM().equals(schema.trim()) && amount.getTAB_NM().equals(tableName)){
				sb.append("val_").append(amount.getTAB_FLD_NM()).append("_DB2 decimal(31,6);");sb.append("\r\n");
			}
		}

		sb.append("val_COUNT_INCEPTOR decimal(31,6);");sb.append("\r\n");
		for(ExcelObject amount:amountFieldSet){
			if(amount.getDB_NM().equals(schema.trim()) && amount.getTAB_NM().equals(tableName)){
				sb.append("val_").append(amount.getTAB_FLD_NM()).append("_INCEPTOR decimal(31,6);");sb.append("\r\n");
			}
		}
		
		sb.append("\r\n");
		
		sb.append("BEGIN");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("select cast(v_date as date) into v_acctdt from system.dual;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_name :=");
		sb.append("'"+schema.trim()+"");
		sb.append(".SP_DQ_COUNT_COMPARE_"+schema.trim()+"_");
		sb.append(tableName+"';");sb.append("\r\n");
		sb.append("v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='DQ_COUNT_DB2';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");
		
		sb.append("\r\n");
		
		sb.append("select COUNT(*) into val_COUNT_DB2 from ").append(tableName).append("@db2_edwinst_").append(schema.trim().toLowerCase()).append(" WHERE del_f!=2;");sb.append("\r\n");
		
		for(ExcelObject amount:amountFieldSet){
			if(amount.getDB_NM().equals(schema.trim()) && amount.getTAB_NM().equals(tableName)){
				sb.append("select SUM(abs(").append(amount.getTAB_FLD_NM()).append(")) into val_").append(amount.getTAB_FLD_NM()).append("_DB2 from ").append(tableName).append("@db2_edwinst_").append(schema.trim().toLowerCase()).append(" WHERE del_f!=2;");sb.append("\r\n");
			}
		}
		sb.append("\r\n");
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");sb.append("\r\n");

		sb.append("v_proc_stepnum := v_proc_stepnum+1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='DQ_COUNT_INCEPTOR';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("\r\n");
		
		sb.append("select dq_val into val_COUNT_INCEPTOR from dq.chk_cnt_msr WHERE bsn_dt= v_acctdt and lev_nm=");
		if(schema.trim().equals("SOR")){
			sb.append("'SOR'");
		}else{
			sb.append("'ODS'");
		}
		sb.append(" and  sys_nm='").append(schema.trim()).append("' and tab_nm ='").append(tableName).append("' and  dq_grp_item= 'FULL' and dq_grp_item_val='FULL' and dq_chk_dim= 'COUNT';");sb.append("\r\n");
		
		for(ExcelObject amount:amountFieldSet){
			if(amount.getDB_NM().equals(schema.trim()) && amount.getTAB_NM().equals(tableName)){
				sb.append("select dq_val into val_").append(amount.getTAB_FLD_NM()).append("_INCEPTOR from  dq.chk_cnt_msr WHERE bsn_dt= v_acctdt and lev_nm=");
				if(schema.trim().equals("SOR")){
					sb.append("'SOR'");
				}else{
					sb.append("'ODS'");
				}
				sb.append(" and  sys_nm='").append(schema.trim()).append("' and tab_nm ='").append(tableName).append("' and  dq_grp_item= 'FULL' and dq_grp_item_val='FULL' and dq_chk_dim= '").append(amount.getTAB_FLD_NM()).append("';");sb.append("\r\n");
			}
		}
		sb.append("\r\n");
		
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");sb.append("\r\n");

		sb.append("v_proc_stepnum := v_proc_stepnum+1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='DQ_COUNT_COMPARE';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("\r\n");
		
		sb.append("IF val_COUNT_DB2 = val_COUNT_INCEPTOR ");sb.append("\r\n");
		sb.append("THEN UPDATE dq.chk_cnt_msr SET chk_f = FLAG_OK where bsn_dt= v_acctdt and lev_nm=");
		if(schema.trim().equals("SOR")){
			sb.append("'SOR'");
		}else{
			sb.append("'ODS'");
		}
		sb.append(" and  sys_nm='").append(schema.trim()).append("' and tab_nm ='").append(tableName.trim()).append("' and  dq_grp_item='FULL' and dq_grp_item_val='FULL' and dq_chk_dim= 'COUNT' ;");sb.append("\r\n");
		sb.append("ELSE ");sb.append("\r\n");
		sb.append("UPDATE dq.chk_cnt_msr SET chk_f = FLAG_ERROR where bsn_dt= v_acctdt and lev_nm=");
		if(schema.trim().equals("SOR")){
			sb.append("'SOR'");
		}else{
			sb.append("'ODS'");
		}
		sb.append(" and  sys_nm='").append(schema.trim()).append("' and tab_nm ='").append(tableName.trim()).append("' and  dq_grp_item='FULL' and dq_grp_item_val='FULL' and dq_chk_dim= 'COUNT' ;");sb.append("\r\n");
		sb.append("END IF;");sb.append("\r\n");
		sb.append("\r\n");
		
		for(ExcelObject amount:amountFieldSet){
			if(amount.getDB_NM().equals(schema.trim()) && amount.getTAB_NM().equals(tableName)){
				sb.append("IF val_").append(amount.getTAB_FLD_NM()).append("_DB2 = val_").append(amount.getTAB_FLD_NM()).append("_INCEPTOR ");sb.append("\r\n");
				sb.append("THEN UPDATE dq.chk_cnt_msr SET chk_f = FLAG_OK where bsn_dt= v_acctdt and lev_nm=");
				if(schema.trim().equals("SOR")){
					sb.append("'SOR'");
				}else{
					sb.append("'ODS'");
				}
				sb.append(" and  sys_nm='").append(schema.trim()).append("' and tab_nm ='").append(tableName.trim()).append("' and  dq_grp_item='FULL' and dq_grp_item_val='FULL' and dq_chk_dim= '").append(amount.getTAB_FLD_NM()).append("' ;");sb.append("\r\n");
				sb.append("ELSE ");sb.append("\r\n");
				sb.append("UPDATE dq.chk_cnt_msr SET chk_f = FLAG_ERROR where bsn_dt= v_acctdt and lev_nm=");
				if(schema.trim().equals("SOR")){
					sb.append("'SOR'");
				}else{
					sb.append("'ODS'");
				}
				sb.append(" and  sys_nm='").append(schema.trim()).append("' and tab_nm ='").append(tableName.trim()).append("' and  dq_grp_item='FULL' and dq_grp_item_val='FULL' and dq_chk_dim= '").append(amount.getTAB_FLD_NM()).append("' ;");sb.append("\r\n");
				sb.append("END IF;");sb.append("\r\n");
				sb.append("\r\n");
			}
			
		}
		
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
		sb.append("\r\n");sb.append("\r\n");
		sb.append("v_proc_exe_flg :=1;");sb.append("\r\n");sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
		sb.append("EXCEPTION ");sb.append("\r\n");
		sb.append("WHEN OTHERS THEN");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm := SQLERRM;");sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
		sb.append("\r\n");sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");
		sb.append("\r\n");sb.append("\r\n");
		sb.append("ROLLBACK;");
		sb.append("\r\n");
		sb.append("RAISE;");
		sb.append("\r\n");
		sb.append("END;");
		sb.append("\r\n");
		sb.append("/");
		return sb.toString();
	}


}
