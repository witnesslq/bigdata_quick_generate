package com.tlcb.bdp.admin.createSqlTools;
import java.util.List;
import java.util.Set;

import com.tlcb.bdp.admin.createSqlTools.tools.ExcelObject;
import com.tlcb.bdp.model.ColumnMeta;



public class Dq {
	
	/****
	 * 
	 * dq统计存储过程
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList,Set<ExcelObject> codeFieldSet,Set<ExcelObject> amountFieldSet) {
//		Set<ExcelObject> codeFieldSet = null;//码值
//		Set<ExcelObject> amountFieldSet = null;//金额
//		try {
//			 codeFieldSet = AnalyseExcel.getDQExcelAsFile("C:\\Users\\lvyueyue\\Desktop\\IndexDemo\\MAPPING_CL.xlsx");
//			 amountFieldSet = AnalyseExcel.getDQExcelAsFile("C:\\Users\\lvyueyue\\Desktop\\IndexDemo\\MAPPING_AMT.xlsx");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		StringBuffer sb = new StringBuffer();
				
		sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
		sb.append(schema.trim());//TODO SCHEMA CORE
		sb.append(".SP_DQ_COUNT_"+schema.trim()+"_");
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
		sb.append("v_sqlerrm string;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("BEGIN");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("select cast(v_date as date) into v_acctdt from system.dual;");sb.append("\r\n");sb.append("\r\n");
		sb.append("select to_char(current_date(),'yyyy-MM-dd') into v_chkdt from system.dual;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_name :=");
		sb.append("'"+schema.trim()+"");
		sb.append(".SP_DQ_COUNT_"+schema.trim()+"_");
		sb.append(tableName+"';");sb.append("\r\n");
		sb.append("v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='DQ_COUNT';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("delete from DQ.PROC_LOG where proc_name = v_proc_name and proc_act_dt = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
		sb.append("delete from DQ.CHK_CNT_MSR where CHK_DT = v_acctdt AND BSN_DT = v_acctdt AND SYS_NM = ");
		sb.append("'"+schema.trim()+"'").append(" AND TAB_NM = ").append("'"+tableName+"';");sb.append("\r\n");sb.append("\r\n");
		
		
		for(ExcelObject code:codeFieldSet){
			if(code.getDB_NM().equals(schema.trim()) && code.getTAB_NM().equals(tableName)){
				sb.append("INSERT INTO dq.chk_cnt_msr SELECT v_chkdt,v_acctdt,");
				if(schema.trim().equals("SOR")){
					sb.append("'SOR',");
				}else{
					sb.append("'ODS',");
				}
				sb.append("'"+schema.trim()+"',").append("'"+tableName+"',").append("'"+code.getTAB_FLD_NM()+"',").append(code.getTAB_FLD_NM()+",").append("'"+"COUNT"+"',");
				sb.append("COUNT(*),current_time(),'--' FROM "+schema.trim()+"."+tableName+" WHERE del_f!=2 GROUP BY  ");
				sb.append(code.getTAB_FLD_NM()+";");sb.append("\r\n");sb.append("\r\n");
				
			}
			for(ExcelObject amount:amountFieldSet){
				if(amount.getDB_NM().equals(schema.trim()) && amount.getTAB_NM().equals(tableName) && code.getDB_NM().equals(schema.trim()) && code.getTAB_NM().equals(tableName)){
					sb.append("INSERT INTO dq.chk_cnt_msr SELECT  v_chkdt,v_acctdt,");
					if(schema.trim().equals("SOR")){
						sb.append("'SOR',");
					}else{
						sb.append("'ODS',");
					}
					sb.append("'"+schema.trim()+"',").append("'"+tableName+"',").append("'"+code.getTAB_FLD_NM()+"',").append(code.getTAB_FLD_NM()+",");
					sb.append("'"+amount.getTAB_FLD_NM()+"',");
					sb.append("SUM(abs("+amount.getTAB_FLD_NM()+")),current_time(),'--' FROM "+schema.trim()+"."+tableName+" WHERE del_f!=2 ");
					sb.append("GROUP BY "+code.getTAB_FLD_NM()+";");sb.append("\r\n");sb.append("\r\n");
				}
			}
		}

		sb.append("INSERT INTO dq.chk_cnt_msr SELECT v_chkdt, v_acctdt ,");
		if(schema.trim().equals("SOR")){
			sb.append("'SOR',");
		}else{
			sb.append("'ODS',");
		}
		sb.append("'"+schema.trim()+"','"+tableName+"','FULL','FULL',");
		sb.append("'COUNT',COUNT(*),current_time(),'--' FROM "+schema.trim()+"."+tableName+" WHERE del_f!=2;");
		sb.append("\r\n");sb.append("\r\n");

		for(ExcelObject amount:amountFieldSet){
			if(amount.getDB_NM().equals(schema.trim()) && amount.getTAB_NM().equals(tableName) ){
				sb.append("INSERT INTO dq.chk_cnt_msr SELECT v_chkdt,v_acctdt,");
				if(schema.trim().equals("SOR")){
					sb.append("'SOR',");
				}else{
					sb.append("'ODS',");
				}
				sb.append("'"+schema.trim()+"','"+tableName+"','FULL','FULL',");
				sb.append("'"+amount.getTAB_FLD_NM()+"',").append("SUM(abs("+amount.getTAB_FLD_NM()+")),");
				sb.append("current_time(),'--' FROM "+schema.trim()+"."+tableName+" WHERE del_f!=2;");
				sb.append("\r\n");sb.append("\r\n");
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
