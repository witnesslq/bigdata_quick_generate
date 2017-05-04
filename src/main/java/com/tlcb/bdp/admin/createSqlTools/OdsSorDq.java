package com.tlcb.bdp.admin.createSqlTools;
import java.util.List;

import com.tlcb.bdp.model.ColumnMeta;


public class OdsSorDq {
	
	/****
	 * 
	 * ODS_SOR_CHK_DQ_模板
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
		sb.append(schema.trim());//TODO SCHEMA CORE
		sb.append(".SP_DQ_CHK_"+schema.trim()+"_");
		sb.append(tableName);sb.append("\r\n");
		sb.append("(v_date IN string) is ");sb.append("\r\n");
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
		sb.append("v_proc_name :=");
		sb.append("'"+schema.trim()+".").append("SP_DQ_CHK_").append(schema.trim()+"_");
		sb.append(tableName+"';");sb.append("\r\n");
		sb.append("v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='DQ_CHK';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("delete from DQ.PROC_LOG where proc_name = v_proc_name and proc_act_dt = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("delete from DQ.ODS_SOR_DQ_TB where BSN_DT = v_acctdt  AND TAB_NM = ");
		sb.append("'"+tableName+"' ;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("insert into DQ.ODS_SOR_DQ_TB ");sb.append("\r\n");
		sb.append("SELECT t1.*,t2.dq_val,t1.ods_value-t2.dq_val,current_time() FROM (");sb.append("\r\n");
		sb.append("select ");sb.append("\r\n");
		sb.append("to_char(current_date(),'yyyy-MM-dd') AS chk_dt ");sb.append("\r\n");
		sb.append(",t_ods.bsn_dt");sb.append("\r\n");
		sb.append(",").append("'"+tableName+"'").append(" AS tab_nm");sb.append("\r\n");
		sb.append(",M3.fld_mapping AS dq_grp_item");sb.append("\r\n");
        sb.append(", M2.cl_fld_val AS dq_grp_item_val");sb.append("\r\n");
		sb.append(",M1.fld_mapping AS dq_chk_dim");sb.append("\r\n");
		sb.append(",sum(t_ods.dq_val) as ods_value ");sb.append("\r\n");
		sb.append("from dq.chk_cnt_msr t_ods");sb.append("\r\n");
		sb.append("join  dq.tab_mapping M1 on t_ods.tab_nm = M1.src_tab_nm and t_ods.dq_chk_dim = M1.src_fld_nm AND  M1.tab_mapping=");
		sb.append("'"+tableName+"'");sb.append("\r\n");
		sb.append("join  dq.cl_mapping M2 on (t_ods.tab_nm = M2.src_tab_nm and t_ods.dq_grp_item = M2.src_fld_nm and t_ods.dq_grp_item_val = M2.src_fld_val) ");
		sb.append("\r\n");
		sb.append("join dq.tab_mapping M3 on t_ods.tab_nm = M3.src_tab_nm and t_ods.dq_grp_item = M3.src_fld_nm AND  M3.tab_mapping=");
		sb.append("'"+tableName+"'");sb.append("\r\n");
		sb.append("where t_ods.bsn_dt=v_acctdt");
		sb.append(" and t_ods.chk_dt= to_char(current_date(),'yyyy-MM-dd')");
		sb.append("\r\n");
		sb.append("group by");sb.append("\r\n");
		sb.append("t_ods.bsn_dt");sb.append("\r\n");
		sb.append(",M1.tab_mapping ");sb.append("\r\n");
		sb.append(",M3.fld_mapping ");sb.append("\r\n");
		sb.append(",M2.cl_fld_val ");sb.append("\r\n");
		sb.append(",M1.fld_mapping ");sb.append("\r\n");
		sb.append(") t1");sb.append("\r\n");
		sb.append("JOIN");sb.append("\r\n");
		sb.append("(SELECT BSN_DT,TAB_NM,dq_grp_item,dq_grp_item_val,dq_chk_dim,DQ_VAL FROM DQ.CHK_CNT_MSR WHERE TAB_NM=");
		sb.append("'"+tableName+"'").append(" AND bsn_dt=v_acctdt ) t2");sb.append("\r\n");
		sb.append("ON t1.bsn_dt=t2.bsn_dt AND t1.tab_nm=t2.tab_nm AND t1.dq_grp_item=t2.dq_grp_item AND t1.dq_grp_item_val=t2.dq_grp_item_val AND t1.dq_chk_dim=t2.dq_chk_dim;");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("v_proc_step_flg :=1;");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("v_proc_exe_flg :=1;");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("EXCEPTION ");sb.append("\r\n");
		sb.append("WHEN OTHERS THEN");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
		sb.append("ROLLBACK;");sb.append("\r\n");
		sb.append("END;");sb.append("\r\n");
		sb.append("/");
		
		return sb.toString();
	}
}
