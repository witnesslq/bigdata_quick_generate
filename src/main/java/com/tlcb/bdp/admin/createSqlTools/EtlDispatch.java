package com.tlcb.bdp.admin.createSqlTools;
import java.util.List;

import com.tlcb.bdp.admin.createSqlTools.tools.TabMapping;
import com.tlcb.bdp.model.ColumnMeta;

public class EtlDispatch {
	/****
	 * 
	 * ETL调度配置存储过程
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public static String  generateIncPro(String schema,String tableName,List<ColumnMeta> columnList,List<TabMapping> tabMappings) {
		StringBuffer sb = new StringBuffer();
		
		if(schema.equals("SOR")){
			sb.append("---SOR_MEATADATA");
			sb.append("\r\n");
			sb.append("INSERT INTO ETL.JOB_METADATA(JOB_NM,SCHD_PERIOD,JOB_TP,LOCATION,JOBCMD,PARAMS,PRIORITY,EST_WRKLD,"
					+ "MTX_GRP,INIT_FLAG,INIT_BATCH_NO,MAX_BATCH_NO,SRC_SYS_ID,JOB_DESC,SCHD_ENGIN_IP)");
			sb.append("\r\n");
			sb.append("VALUES");
			sb.append("\r\n");
			
//			sb.append("('PRE_JOB','DAY','CMD','L_DSEXT','pre_kinit.sh',null,5,1,'PRE_JOB','N',1,1,");
//			sb.append("'"+schema.trim()+"','PRE_JOB任务','10.4.145.45'),");
//			sb.append("\r\n");
			
			sb.append("('HANDFILE_CHK_"+schema.trim()+"_"+tableName+"','DAY','FILE','FILE','dw_");
			sb.append(schema.trim().toLowerCase()+"_"+tableName.toLowerCase()+"_$dateid.dat.flg HANDFILE','/home/etladm/data/nfs/"+schema.trim().toLowerCase()+"/$dateid',5,1,");
			sb.append("'HANDFILE_CHK_"+schema.trim()+"_"+tableName+"','N',1,1,"+"'"+schema.trim()+"',");
			sb.append("'HANDFILE_CHK_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_PUT_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_PUT','hdfs_put.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'HDFS_PUT_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'HDFS_PUT_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_CHECK_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_CHK','hdfs_check.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'HDFS_CHECK_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'HDFS_CHECK_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_INC','inc_dat.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'INC_DAT_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'INC_DAT_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"_HS','DAY','CMD','L_INC','inc_dat.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"_hs','$dateid',5,1,'INC_DAT_"+schema.trim()+"_"+tableName+"_HS','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'INC_DAT_"+schema.trim()+"_"+tableName+"_HS任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('DQ_COUNT_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_DQCNT','dq_count.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'DQ_COUNT_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'DQ_COUNT_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			/***
			 * 
			 * 当Schema为SOR时,才有这条语句
			 */
			if(schema.trim().equals("SOR")){
				sb.append("('DQ_CHECK_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_DQCHK','dq_check.sh ");
				sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'DQ_CHECK_"+schema.trim()+"_"+tableName+"','N',1,1,");
				sb.append("'"+schema.trim()+"'").append(",'DQ_CHECK_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
				sb.append("\r\n");
			}
			sb.append("('CH_CMPR_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_CMPR','cur_hs_compare.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'CH_CPMR_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'CH_CPMR_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");

			sb.append("('FILEBK_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_BK','file_backup.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'FILEBK_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'FILEBK_"+schema.trim()+"_"+tableName+"任务','10.4.145.45');");
			sb.append("\r\n");
			sb.append("\r\n");
			
			
			sb.append("---SOR SEQ");
			sb.append("\r\n");
			sb.append("INSERT INTO ETL.JOB_SEQ(PRE_JOB,JOB_NM)");
			sb.append("\r\n");
			sb.append("VALUES");
			
			sb.append("('PRE_JOB','HDFS_PUT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");

			sb.append("('HANDFILE_CHK_"+schema.trim()+"_"+tableName+"','HDFS_PUT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_PUT_"+schema.trim()+"_"+tableName+"','HDFS_CHECK_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_CHECK_"+schema.trim()+"_"+tableName+"','INC_DAT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_CHECK_"+schema.trim()+"_"+tableName+"','INC_DAT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"','DQ_COUNT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"_HS','CH_CMPR_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"','CH_CMPR_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_PUT_"+schema.trim()+"_"+tableName+"','FILEBK_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
//			sb.append("('DQ_COUNT_"+schema.trim()+"_"+tableName+"','DQ_CHECK_"+schema.trim()+"_"+tableName+"'),");
//			sb.append("\r\n");
//			
//			sb.append("('DQ_COUNT_"+schema.trim()+"_"+tableName+"','DQ_CHECK_"+schema.trim()+"_"+tableName+"'),");
//			sb.append("\r\n");
			for(TabMapping tabMapping:tabMappings){
				sb.append("('DQ_COUNT_"+tabMapping.getSRC_SYS_NM().trim()+"_"+tabMapping.getSRC_TAB_NM().trim()+"','DQ_CHECK_"+schema.trim()+"_"+tableName+"'),");
				sb.append("\r\n");
			}
			
			
			sb.append("('DQ_COUNT_"+schema.trim()+"_"+tableName+"','DQ_CHECK_"+schema.trim()+"_"+tableName+"');");
			sb.append("\r\n");
		}else{
			sb.append("---ODS METADATA");
			sb.append("\r\n");
			sb.append("INSERT INTO ETL.JOB_METADATA(JOB_NM,SCHD_PERIOD,JOB_TP,LOCATION,JOBCMD,PARAMS,PRIORITY,EST_WRKLD,"
					+ "MTX_GRP,INIT_FLAG,INIT_BATCH_NO,MAX_BATCH_NO,SRC_SYS_ID,JOB_DESC,SCHD_ENGIN_IP)");
			sb.append("\r\n");
			sb.append("VALUES");
			sb.append("\r\n");
//			sb.append("('PRE_JOB','DAY','CMD','L_DSEXT','pre_kinit.sh',null,5,1,'PRE_JOB','N',1,1,'"+schema.trim()+"','PRE_JOB任务','10.4.145.45'),");
//			sb.append("\r\n");
			
			sb.append("('HANDFILE_CHK_"+schema.trim()+"_"+tableName+"','DAY','FILE','FILE',");
			sb.append("'dw_"+schema.trim().toLowerCase()+"_"+tableName.toLowerCase()+"_$dateid.dat.flg HANDFILE','/home/etladm/data/nfs/"+schema.trim().toLowerCase()+"/$dateid',5,1,");
			sb.append("'HANDFILE_CHK_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"',").append("'HANDFILE_CHK_"+schema.trim()+"_"+tableName+"任务',"+"'10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_PUT_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_PUT','hdfs_put.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'HDFS_PUT_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'HDFS_PUT_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_CHECK_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_CHK','hdfs_check.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'HDFS_CHECK_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'HDFS_CHECK_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_INC','inc_dat.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'INC_DAT_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'INC_DAT_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"_HS','DAY','CMD','L_INC','inc_dat.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"_hs','$dateid',5,1,'INC_DAT_"+schema.trim()+"_"+tableName+"_HS','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'INC_DAT_"+schema.trim()+"_"+tableName+"_HS任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('DQ_COUNT_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_DQCNT','dq_count.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'DQ_COUNT_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'DQ_COUNT_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('CH_CMPR_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_CMPR','cur_hs_compare.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'CH_CPMR_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'CH_CPMR_"+schema.trim()+"_"+tableName+"任务','10.4.145.45'),");
			sb.append("\r\n");
			
			sb.append("('FILEBK_"+schema.trim()+"_"+tableName+"','DAY','CMD','L_BK','file_backup.sh ");
			sb.append(schema.trim().toLowerCase()+" "+tableName.toLowerCase()+"','$dateid',5,1,'FILEBK_"+schema.trim()+"_"+tableName+"','N',1,1,");
			sb.append("'"+schema.trim()+"'").append(",'FILEBK_"+schema.trim()+"_"+tableName+"任务','10.4.145.45');");
			sb.append("\r\n");
			sb.append("\r\n");
			
			
			sb.append("---ODS SEQ ");
			sb.append("\r\n");
			sb.append("INSERT INTO ETL.JOB_SEQ(PRE_JOB,JOB_NM)");
			sb.append("\r\n");
			sb.append("VALUES");
			sb.append("\r\n");
			
			sb.append("('PRE_JOB','HDFS_PUT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HANDFILE_CHK_"+schema.trim()+"_"+tableName+"','HDFS_PUT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_PUT_"+schema.trim()+"_"+tableName+"','HDFS_CHECK_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_CHECK_"+schema.trim()+"_"+tableName+"','INC_DAT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_CHECK_"+schema.trim()+"_"+tableName+"','INC_DAT_"+schema.trim()+"_"+tableName+"_HS'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"','DQ_COUNT_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"_HS','CH_CMPR_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('INC_DAT_"+schema.trim()+"_"+tableName+"','CH_CMPR_"+schema.trim()+"_"+tableName+"'),");
			sb.append("\r\n");
			
			sb.append("('HDFS_PUT_"+schema.trim()+"_"+tableName+"','FILEBK_CORE_"+schema.trim()+"_"+tableName+"');");
			sb.append("\r\n");
			sb.append("\r\n");
			
		}
		
		
		

		return sb.toString();
	}
   
	   
}
