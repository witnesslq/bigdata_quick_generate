package com.tlcb.bdp.admin.ddl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tlcb.bdp.model.ColumnMeta;
import com.tlcb.bdp.model.TableMeta;

public class ORCDDL {

	
	public static String generateORCFlowDDL(TableMeta sourceTable,List<ColumnMeta> sourceColumn) {
		//假设生成全部的external's DDL 和 orc's DDL
		//1.先全部查询出数据库中表,再字段表中查询出相关的字段
		
		StringBuffer sb = new StringBuffer("drop table if exists ").append(sourceTable.getSrcStmNm().trim()).append(".").append(sourceTable.getTblCode()).append("_HS ;\r\n");
		List<String> keyColumnList = new ArrayList<>();
		  for (ColumnMeta s : sourceColumn) {
			  if(null!=s.getBucketKeyFlag() && 1==s.getBucketKeyFlag()){
				  keyColumnList.add(s.getFieldCode());
			  }
			}
		  
		try {		
					sb.append(" CREATE TABLE ");
					sb.append(sourceTable.getSrcStmNm()).append(".");
					sb.append(sourceTable.getTblCode()).append("_HS").append(" ( ");
					
					for (ColumnMeta columnSchemaMeta : sourceColumn) {
						sb.append(" ").append(columnSchemaMeta.getFieldCode()).append(" ");
						sb.append(exchangeTypeColumn(columnSchemaMeta));
						if("N".equals(columnSchemaMeta.getIsNull())){
							sb.append(" NOT NULL ");
						}
						if(null!=columnSchemaMeta.getFieldDesc()){
							sb.append("  Comment '").append(columnSchemaMeta.getFieldDesc().replace(";",",")).append("' ,\r\n");					
						}else{
							sb.append("  Comment '").append("").append("' ,\r\n");					}
						}
						int len = sb.toString().lastIndexOf(",");
						if(len!=-1){
							  sb.deleteCharAt(len);
						}
						 sb.append(" )\r\n");
						 if(null!=sourceTable.getTblDesc()){
							 
							 sb.append(" comment	'").append(sourceTable.getTblDesc().replace(";",",")).append("' \r\n");
						 }else{
							 sb.append(" comment	'").append("").append("' \r\n");

						 }
					  sb.append(" clustered by (");
					  if(keyColumnList.size()>0){
						 sb.append(keyColumnList.get(0)) ;
					  }
					  
					  sb.append(") into "+new BigDecimal(sourceTable.getBucketNum()).intValue()+" buckets stored as orc tblproperties ('transactional' = 'true') ;");
					//  writeToLocal("C:\\Users\\Achilles\\Desktop\\orc\\"+sourceTable.getTabschema().trim()+"."+sourceTable.getTabname().trim()+"_ORC"+".sql",sb.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
		 
		
	}
	
	
	public static String generateORCZipperDDL(TableMeta tableMeta, List<ColumnMeta> list1) {

		List<String> keyColumnList = new ArrayList<>();
		
		  for (ColumnMeta sourceColumn : list1) {
				if(null!=sourceColumn.getBucketKeyFlag() && 1==sourceColumn.getBucketKeyFlag()){
					keyColumnList.add(sourceColumn.getFieldCode());
				}	
			}
		  
		StringBuffer sb = new StringBuffer("drop table if exists ").append(tableMeta.getSrcStmNm().trim()).append(".").append(tableMeta.getTblCode()).append("_HS ;\r\n");
		  
		  try {		
					sb.append(" CREATE TABLE ");
					sb.append(tableMeta.getSrcStmNm()).append(".");
					sb.append(tableMeta.getTblCode()).append("_HS").append(" ( ");
					sb.append("begindt string, enddt string,");
					
					  if(keyColumnList.size()==0){
						  keyColumnList.add(list1.get(0).getFieldCode());
					  }
					for (ColumnMeta columnSchemaMeta : list1) {
						sb.append(" ").append(columnSchemaMeta.getFieldCode()).append(" ");
						sb.append(exchangeTypeColumn(columnSchemaMeta));
						if("N".equals(columnSchemaMeta.getIsNull())){
							sb.append(" NOT NULL ");
						}
						if(null!=columnSchemaMeta.getFieldDesc()){
							
							sb.append("  Comment '").append(columnSchemaMeta.getFieldDesc().replace(";", ",")).append("' ,\r\n");				
						}else{
							sb.append("  Comment '").append("").append("' ,\r\n");					}

						}
						int len = sb.toString().lastIndexOf(",");
						if(len!=-1){
							  sb.deleteCharAt(len);
						}
					  sb.append(" )\r\n");
					  if(null!=tableMeta.getTblDesc()){
						  
						  sb.append(" comment	'").append(tableMeta.getTblDesc().replace(";", ",")).append("' \r\n");
					  }else{
						  sb.append(" comment	'").append("").append("' \r\n");

					  }
					  sb.append(" clustered by (");
					  
					  sb.append(keyColumnList.get(0));
					  
					  sb.append(") into "+new BigDecimal(tableMeta.getBucketNum()).intValue()+" buckets stored as orc tblproperties ('transactional' = 'true') ;");
					  //writeToLocal("C:\\Users\\Achilles\\Desktop\\orc_zipper\\"+tableMeta.getSrcStmNm().trim()+"."+tableMeta.getTblCode().trim()+"_ORC_ZIPPER"+".sql",sb.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	
	public static String generateORCJieDDL(TableMeta tableMeta, List<ColumnMeta> list1) {
		//假设生成全部的external's DDL 和 orc's DDL
				//1.先全部查询出数据库中表,再字段表中查询出相关的字段
		StringBuffer sb = new StringBuffer("drop table if exists ").append(tableMeta.getSrcStmNm().trim()).append(".").append(tableMeta.getTblCode()).append(" ;\r\n");
				
				List<String> keyColumnList = new ArrayList<>();
				  for (ColumnMeta sourceColumn : list1) {
						if(null!=sourceColumn.getBucketKeyFlag() && 1==sourceColumn.getBucketKeyFlag()){
							keyColumnList.add(sourceColumn.getFieldCode());
						}	
					}
				  
				  
				  try {		
							sb.append(" CREATE TABLE ");
							sb.append(tableMeta.getSrcStmNm().trim()).append(".");
							sb.append(tableMeta.getTblCode()).append(" ( ");
						//	sb.append("begindt string, enddt string,");
							  if(keyColumnList.size()==0){
								  keyColumnList.add(list1.get(0).getFieldCode());
							  }
							for (ColumnMeta columnSchemaMeta : list1) {
								sb.append(" ").append(columnSchemaMeta.getFieldCode()).append(" ");
								sb.append(exchangeTypeColumn(columnSchemaMeta));
								if("N".equals(columnSchemaMeta.getIsNull())){
									sb.append(" NOT NULL ");
								}
								if(null!=columnSchemaMeta.getFieldDesc()){
									sb.append("  Comment '").append(columnSchemaMeta.getFieldDesc().replace(";", ",")).append("' ,\r\n");				
								}else{
									sb.append("  Comment '").append("").append("' ,\r\n");					}

								}
								int len = sb.toString().lastIndexOf(",");
								if(len!=-1){
									  sb.deleteCharAt(len);
								}
							  sb.append(" )\r\n");
							  if(null!=tableMeta.getTblDesc()){
								  sb.append(" comment	'").append(tableMeta.getTblDesc().replace(";", ",")).append("' \r\n");
							  }else{
								  sb.append(" comment	'").append("").append("' \r\n");
							  }
							  
							  sb.append(" clustered by (");
								  sb.append(keyColumnList.get(0));
							  sb.append(") into "+new BigDecimal(tableMeta.getJieBucketNum()).intValue()+" buckets stored as orc tblproperties ('transactional' = 'true') ;");
							 // writeToLocal("C:\\Users\\Achilles\\Desktop\\orc_jie\\"+tableMeta.getSrcStmNm().trim()+"."+tableMeta.getTblCode().trim()+"_ORC_JIE"+".sql",sb.toString());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
		return sb.toString();
	}
	
	private static String exchangeTypeColumn(ColumnMeta columnSchemaMeta ) {
		String typename = columnSchemaMeta.getDataTp();
		if("DECIMAL".equals(typename)){
			typename = typename +"("+ columnSchemaMeta.getFldLength()+","+columnSchemaMeta.getFldScale()+")";
			return typename;
		}
			switch(typename){
			case "CHAR":
				typename = "string";
				break;
			case "VARCHAR":
				typename = "string";
				break;
			case "NCHAR":
				typename = "string";
				break;
			case "NVARCHAR":
				typename = "string";
				break;
			case "INTEGER":
				typename = "int";
				break;
			case "NUMERIC":
				typename = "decimal";
				break;
			case "TIMESTAMP":
				typename = "string";
				break;
			case "REAL":
				typename = "float";
				break;
			case "FLOAT":
				typename = "double";
				break;
			case "BLOB":
				typename = "binary";
				break;
			case "CLOB":
				typename = "binary";
				break;
			case "NCLOB":
				typename = "binary";
				break;
			case "DBCLOB":
				typename = "binary";
				break;
			case "XML":
				typename = "json";//TODO
				break;
			case "DATE":
				typename = "string";
				break;	
			case "SMALLINT":
				typename = "int";	
				break;
			case "BIGINTEGER":
				typename = "long";
				break;
			default:
				typename= "string";
				
			}
			
			return typename;
		}




	
}
