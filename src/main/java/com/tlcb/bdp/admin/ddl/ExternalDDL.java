package com.tlcb.bdp.admin.ddl;

import java.util.List;

import com.tlcb.bdp.admin.createSqlTools.tools.FileUtils;
import com.tlcb.bdp.model.ColumnMeta;
import com.tlcb.bdp.model.TableMeta;


public class ExternalDDL {
	
	
	/**
	 * 生成增量DDL
	 * @param tableMeta
	 * @param columnList
	 */
	public static String generateZengDDL( TableMeta tableMeta,List<ColumnMeta> columnList) {
		//假设生成全部的external's DDL 和 orc's DDL
		//1.先全部查询出数据库中表,再字段表中查询出相关的字段
		StringBuffer sb = new StringBuffer(" drop table if exists ").append(tableMeta.getSrcStmNm().trim()).append(".").append(tableMeta.getTblCode()).append("_EXT ;\r\n");
		try {
					sb.append("CREATE EXTERNAL TABLE ");
					sb.append(tableMeta.getSrcStmNm()).append(".");
					sb.append(tableMeta.getTblCode()).append("_EXT").append(" ( ");
					
					for (ColumnMeta columnSchemaMeta : columnList) {
						
						sb.append(" ").append(columnSchemaMeta.getFieldCode()).append(" ");
						
						if("DEL_F".equals(columnSchemaMeta.getFieldCode())){
							sb.append("varchar(1)");
						}else{
							sb.append("string");
						}
						
						if("N".equals(columnSchemaMeta.getIsNull())){
							sb.append(" NOT NULL ");
						}
						if(null!=columnSchemaMeta.getFieldDesc()){
							
							sb.append("  Comment '").append(columnSchemaMeta.getFieldDesc().replace(";", ",")).append("' ,\r\n");
						}else{
							sb.append("  Comment '").append("").append("' ,\r\n");

						}
					}
					int len = sb.toString().lastIndexOf(",");
					if(len!=-1){
						  sb.deleteCharAt(len);
					}
				
					  sb.append(" ) \r\n");
					  
					  if(null!=tableMeta.getTblDesc()){
						  
						  sb.append(" comment	'").append(tableMeta.getTblDesc().replace(";", ",")).append("' \r\n");
					  }else{
						  sb.append(" comment	'").append("").append("' \r\n");
					  }
					  //外表
					  sb.append(" PARTITIONED BY(INC_DATE string) row format delimited FIELDS TERMINATED BY '\\27' ;");
					  sb.append("\r\n");
					//  sb.append(" LOG ERRORS INTO  ").append(sourceTable.getTabschema().trim()+"_"+sourceTable.getTabname()+"_EXT_ERROR").append(" ;\r\n");
					  sb.append("ALTER TABLE ");
					  sb.append(tableMeta.getSrcStmNm().trim()).append(".");
					  sb.append(tableMeta.getTblNm()).append("_EXT");
					  sb.append(" set serdeproperties('serialization.encoding'='gbk');");
					
					 // FileUtils.writeToLocal("C:\\Users\\Achilles\\Desktop\\zeng\\"+tableMeta.getSrcStmNm().trim()+"."+tableMeta.getTblNm().trim()+"_EXT"+".sql",sb.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
		 
	}
	
	
	
	/**
	 * 生成存量DDL
	 * @param tableMeta
	 * @param columnList
	 * @return
	 */
	public static String  generateCunDDL(TableMeta tableMeta,List<ColumnMeta> columnList) {
		//假设生成全部的external's DDL 和 orc's DDL
		//1.先全部查询出数据库中表,再字段表中查询出相关的字段
		
		StringBuffer sb = new StringBuffer("drop table if exists ").append(tableMeta.getSrcStmNm().trim()).append(".").append(tableMeta.getTblCode()).append("_EXT_INIT ;\r\n");
		try {
					sb.append("CREATE EXTERNAL TABLE ");
					sb.append(tableMeta.getSrcStmNm().trim()).append(".");
					sb.append(tableMeta.getTblCode()).append("_EXT_INIT").append(" ( ");
					
					for (ColumnMeta columnSchemaMeta : columnList) {
						sb.append(" ").append(columnSchemaMeta.getFieldCode()).append(" ");
						
						if("DEL_F".equals(columnSchemaMeta.getFieldCode())){
							sb.append("varchar(1)");
						}else{
							sb.append("string");
						}
						
						if("N".equals(columnSchemaMeta.getIsNull())){
							sb.append(" NOT NULL ");
						}
						String cc;
						if(null!=columnSchemaMeta.getFieldDesc()){
							cc = columnSchemaMeta.getFieldDesc();
						}else{
							cc ="";
						}
						
						sb.append("  Comment '").append(cc.replace(";", ",")).append("' ,\r\n");
						
					}
					int len = sb.toString().lastIndexOf(",");
					if(len!=-1){
						  sb.deleteCharAt(len);
					}
					  sb.append(" )\r\n");
					  String aa  ;
					  if(null!=tableMeta.getTblDesc()){
						  aa = tableMeta.getTblDesc();
					  }else{
						  aa = "";
					  }
					  //外表
					  sb.append(" comment	'").append(aa.replace(";", ",")).append("' \r\n");

					  sb.append(" row format delimited FIELDS TERMINATED BY '\\27' ; ");
					  System.out.println(sb);
					//  writeToLocal("C:\\Users\\Achilles\\Desktop\\cun\\"+sourceTable.getTabschema().trim()+"."+sourceTable.getTabname().trim()+"_EXT_INIT"+".sql",sb.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
}
