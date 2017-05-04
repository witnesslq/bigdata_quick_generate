package com.tlcb.bdp.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calanger.common.dao.OrderBy;
import com.tlcb.bdp.admin.createSqlTools.Dq;
import com.tlcb.bdp.admin.createSqlTools.FlowWater;
import com.tlcb.bdp.admin.createSqlTools.JieMianFWater;
import com.tlcb.bdp.admin.createSqlTools.JieMianZipper;
import com.tlcb.bdp.admin.createSqlTools.OdsSorDq;
import com.tlcb.bdp.admin.createSqlTools.ZipperTable;
import com.tlcb.bdp.admin.createSqlTools.jiemianContrastFlow;
import com.tlcb.bdp.admin.createSqlTools.jiemianContrastZipper;
import com.tlcb.bdp.admin.createSqlTools.tools.ExcelObject;
import com.tlcb.bdp.admin.ddl.ExternalDDL;
import com.tlcb.bdp.admin.ddl.ORCDDL;
import com.tlcb.bdp.admin.util.Common;
import com.tlcb.bdp.admin.util.DbUtil;
import com.tlcb.bdp.admin.util.PageView;
import com.tlcb.bdp.model.ColumnMeta;
import com.tlcb.bdp.model.DbInfo;
import com.tlcb.bdp.model.MissionUserLog;
import com.tlcb.bdp.model.TableMeta;
import com.tlcb.bdp.model.UserTableRelation;
import com.tlcb.bdp.service.ColumnMetaService;
import com.tlcb.bdp.service.DbInfoService;
import com.tlcb.bdp.service.MissionUserLogService;
import com.tlcb.bdp.service.TableMetaService;
import com.tlcb.bdp.service.UserTableRelationService;
import com.tlcb.bdp.service.extension.TableMetaExtService;
import com.tlcb.bdp.vo.ColumnMetaVO;
import com.tlcb.bdp.vo.TableMetaVO;
import com.tlcb.bdp.vo.UserTableRelationVO;


@Controller
@RequestMapping("/tableInfo/")
public class TableInfoController extends BaseController {
	 private static final int BUFFEREDSIZE = 1024; 
	
/*	@Autowired
	UserItf userItf;
	*/
	@Autowired
	private UserTableRelationService userTableRelationService;
	
	@Autowired
	private DbInfoService dbInfoService;
	
	@Resource
	private TableMetaExtService tableMetaExtService;
	
	@Autowired
	private TableMetaService tableMetaService;
	
	@Autowired
	private ColumnMetaService columnMetaService;
	
	@Autowired
	private MissionUserLogService missionUserLogService ;

	/*@Autowired
	private ResourcesItf resourcesItf;*/

	@RequestMapping("list")
	public String listUI(Model model, String id ,String tbDbId) throws Exception {

	/*	ResourcesVO vo = new ResourcesVO();
		vo.setParentId(id);

		List<Resources> res = resourcesItf.bdpButtom(vo,"level",true);
		model.addAttribute("res", res);*/
		
		model.addAttribute("tbDbId", tbDbId);
		
		return "/system/tableInfo/list";

	}
	
	
	@ResponseBody
	@RequestMapping("creatDLL")
	public String creatDLL(Integer[] ids,HttpServletResponse response) throws Exception {
		//ids只有一条
				List<Integer> idList = new ArrayList<Integer>();
				for (Integer id : ids) {
					idList.add(id);
				}
				TableMetaVO tableMetaVO = new TableMetaVO();
				tableMetaVO.setTaskUserStatus("02");//表示已经生成,未确认
				this.tableMetaService.updateByIdList(tableMetaVO, Arrays.asList(ids));
				
				String folder = System.getProperty("java.io.tmpdir");
				String ddlFolder = folder + "/etl/ddl/";
				
				/*File file1 = new File(ddlFolder);
				if(!file1.exists() && !file1.isDirectory()){
					System.out.println("不存在次目录:"+file1.getPath());
					//file1.mkdir();
				}else{
					file1.delete();
					file1.mkdir();//删除之后又新建
					System.out.println(file1.getPath()+"目录已经存在");
				}*/
				
				deleteFile(folder + "/etl");
				
				List<TableMeta> list = this.tableMetaService.findByIdList(Arrays.asList(ids));//查询表集合
				for (TableMeta tableMeta : list) {
					
						MissionUserLog missionUserLog = new  MissionUserLog();
						missionUserLog.setProcessDesc("生成数据包,用户名:"+tableMeta.getTaskUserName()+",表:"+tableMeta.getTblCode());
						missionUserLog.setProcessType("生成数据包");
						missionUserLog.setTargetTable(tableMeta.getTblCode());
						missionUserLog.setUserId(tableMeta.getTaskUserId());
						missionUserLog.setUserName(tableMeta.getTaskUserName());
						long startTimeMillis = System.currentTimeMillis();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String processTime = sdf.format(startTimeMillis);
						missionUserLog.setProcessTime(sdf.parse(processTime));
						missionUserLogService.insert(missionUserLog);
					
					ColumnMetaVO columnMetaVO = new ColumnMetaVO();
					columnMetaVO.setSrcStmNm(tableMeta.getSchemaCode());
					columnMetaVO.setTblNm(tableMeta.getTblCode());
					columnMetaVO.setIsEnable("1");
					List<ColumnMeta> COLUMNLIST = this.columnMetaService.find(columnMetaVO);//查询表相关的字段
					
					String tablenname = tableMeta.getTblCode();//tableName
					String schemaName = tableMeta.getSrcStmNm();//schema
					
					Set<ExcelObject> codeFieldSet = new HashSet<>();//码值
					Set<ExcelObject> amountFieldSet = new HashSet<>();//金额
					
					for (ColumnMeta col : COLUMNLIST) {
						ExcelObject e = new ExcelObject();
						e.setTAB_NM(tablenname.trim());
						if ("1".equals(col.getIsCode())) {
							e.setTAB_FLD_NM(col.getFieldCode());
						}
						e.setDB_NM(col.getSrcStmNm());
						codeFieldSet.add(e);//码值列表字段集合
						
						ExcelObject ea = new ExcelObject();
						ea.setTAB_NM(tablenname.trim());
						if("1".equals(col.getIsAmount())){
							ea.setTAB_FLD_NM(col.getFieldCode());
						}
						ea.setDB_NM(col.getSrcStmNm());//金额列表字段集合
						amountFieldSet.add(ea);
						
					}
					
					System.out.println("java临时的文件夹:"+folder);
					
					ColumnMetaVO columnMetaVO2 = new ColumnMetaVO();
					columnMetaVO2.setTblNm(tableMeta.getTblCode());
					columnMetaVO2.setSrcStmNm(tableMeta.getSrcStmNm());
					
					List<ColumnMeta> list1 = this.columnMetaService.find(columnMetaVO2);
					
					String generateZengDDL = ExternalDDL.generateZengDDL(tableMeta,list1);//生成增量DDL
					writeToLocal(ddlFolder+"/zeng/",schemaName+"."+tablenname+".sql",generateZengDDL);
					String gengrateCunDDL = ExternalDDL.generateCunDDL(tableMeta, list1);
					writeToLocal(ddlFolder+"/cun/",schemaName+"."+tablenname+".sql",gengrateCunDDL);
					String generateOrcFlow = ORCDDL.generateORCFlowDDL(tableMeta, list1);
					writeToLocal(ddlFolder+"/orc_flow/",schemaName+"."+tablenname+".sql",generateOrcFlow);
					String gengrateOrcZipper = ORCDDL.generateORCZipperDDL(tableMeta, list1);
					writeToLocal(ddlFolder+"/orc_zipper/",schemaName+"."+tablenname+".sql",gengrateOrcZipper);
					String genearateOrcJie = ORCDDL.generateORCJieDDL(tableMeta, list1);
					writeToLocal(ddlFolder+"/orc_jie/",schemaName+"."+tablenname+".sql",genearateOrcJie);

					
					String spFolder = folder + "/etl/sp/" ;
					File file = new File(spFolder);
					
					if(!file.exists() && !file.isDirectory()){
						System.out.println("不存在次目录:"+file.getPath());
						file.mkdir();
					}else{
						System.out.println(file.getPath()+"目录已经存在");
					}
					
				
					writeToLocal(spFolder+"/SP_DQ_COUNT_","SP_DQ_COUNT_"+schemaName.trim()+"_"+tablenname+".sql",
								 Dq.generateIncPro(schemaName,tablenname,COLUMNLIST,codeFieldSet,amountFieldSet));
					
				/*	List<TabMapping> tabMappings=null;//tabMapping  暂时不能自己生成
					
					writeToLocal(spFolder+"/SP_ETL_","SP_ETL_"+schemaName.trim()+"_"+tablenname+".sql",
								EtlDispatch.generateIncPro(schemaName,tablenname,COLUMNLIST,tabMappings));*/
					
					writeToLocal(spFolder+"/Flow","SP_FLOW_"+schemaName.trim()+"_"+tablenname+"_HS.sql",
								 FlowWater.generateIncPro(schemaName,tablenname,COLUMNLIST));
					
					writeToLocal(spFolder+"/SP_EXC","SP_EXC_"+schemaName.trim()+"_"+tablenname+".sql",
								 jiemianContrastFlow.generateIncPro(schemaName,tablenname,COLUMNLIST));
					
					writeToLocal(spFolder+"/SP_EXC_jiemianContrastZipper_","SP_EXC_jiemianContrastZipper_"+schemaName.trim()+"_"+tablenname+".sql",
								  jiemianContrastZipper.generateIncPro(schemaName,tablenname,COLUMNLIST));
					
					writeToLocal(spFolder+"/SP_INC_","SP_INC_"+schemaName.trim()+"_"+tablenname+".sql",
								 JieMianFWater.generateIncPro(schemaName,tablenname,COLUMNLIST));
					
					Set<ExcelObject> IsBucketField=new HashSet<>();//分桶键
					
					for (ColumnMeta col : COLUMNLIST) {
						if(null!=col.getBucketKeyFlag()&&col.getBucketKeyFlag()==1){
							ExcelObject excelObject = new ExcelObject();
							excelObject.setBUCKET_FIELD(col.getFieldCode());
							excelObject.setTAB_NM(col.getTblNm());
							excelObject.setDB_NM(col.getSrcStmNm());
							
							IsBucketField.add(excelObject);
						}
						
					}
					
					
					writeToLocal(spFolder+"/SP_INC_/jiemianzipper","SP_INC_"+schemaName.trim()+"_"+tablenname+".sql",
							 	 JieMianZipper.generateIncPro(schemaName,tablenname,COLUMNLIST,IsBucketField));
					
					writeToLocal(spFolder+"/SP_DQ_CHK_/SOR",schemaName.trim()+"_"+tablenname+".sql",
								 OdsSorDq.generateIncPro(schemaName,tablenname,COLUMNLIST));
					
					writeToLocal(spFolder+"/SP_INC_/zip","SP_INC_"+schemaName.trim()+"_"+tablenname+"_HS.sql",
								 ZipperTable.generateIncPro(schemaName,tablenname,COLUMNLIST));
				
		}
		
		
	/*	String sql = "存量外表,增量外表,orc表已生成;位于目录C:\\Users\\Achilles\\Desktop\\external\\下";
		
		response.setContentType("text/html;charset=UTF-8");
		
		return sql;*/
		
		return "success";
	}
	
	public static void deleteFile(String targetPath){
		File targetFile = new File(targetPath);
		if(targetFile.isDirectory()){
			try {
				FileUtils.deleteDirectory(targetFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			targetFile.delete();
		}
	}
	
	@RequestMapping(value="/zipDownload")
	public void downLoadZipFile(HttpServletResponse response) throws IOException{
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition","attachement;filename = "+this.getZipFileName());
	/*	ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		String path = System.getProperty("java.io.tmpdir");
		List<File> list = traverseFolder(path+"/etl/ddl/");
		
		File[] f1 = new File[list.size()];
		for (int i = 0; i < list.size(); i++) {
			f1[i] = list.get(i);
		}
		zipFile(f1,"",zos);
		zos.flush();
		zos.close();
		fs.clear();*/
		
		
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		out.setEncoding("GBK");  
		String inputFile = System.getProperty("java.io.tmpdir")+"/etl";
		File f2 = new File(inputFile);
		zip(f2, out, "");
		out.flush();
  		out.close();
		
	}
	
	 private static void zip(File inputFile, ZipOutputStream out, String base)  
	            throws IOException {  
	        if (inputFile.isDirectory()) {  //文件夹
	            if (inputFile.listFiles().length == 0) {//没有文件了  
	                ZipEntry zipEntry = new ZipEntry(base + inputFile.getName()  
	                        + "/");  
	                out.putNextEntry(zipEntry);  
	                out.closeEntry();  
	            } else {  
	                base += inputFile.getName() + File.separator;  
	                for (File f : inputFile.listFiles()) {  
	                    zip(f, out, base);  
	                }  
	            }  
	        } else {  
	            ZipEntry zipEntry = new ZipEntry(base + inputFile.getName());  
	            out.putNextEntry(zipEntry);  
	            FileInputStream in = new FileInputStream(inputFile);  
	            try {  
	                int c;  
	                byte[] by = new byte[BUFFEREDSIZE];  
	                while ((c = in.read(by)) != -1) {  
	                    out.write(by, 0, c);  
	                }  
	            } catch (IOException e) {  
	                throw e;  
	            } finally {  
	                in.close(); 
	            }  
	        }  
	    }  
	
	List<File> fs = new ArrayList<>();

	public List<File> traverseFolder(String path){
		
		File file = new  File(path);
		if(file.exists()){
			File[] files= file.listFiles();
			if(files.length == 0){
				System.out.println("文件夹是空的");
				return fs;
			}else{
				for (File file2 : files) {
					if(file2.isDirectory()){
						System.out.println("文件夹:"+file2.getAbsolutePath());
						traverseFolder(file2.getAbsolutePath());
					}else{
						System.out.println("文件:"+file2.getAbsolutePath());
						fs.add(new File(file2.getAbsolutePath()));
					}
				}
				
			}
		}else{
			System.out.println(path+"不存在!");
		}
		return fs;
	}
	
	private String getZipFileName() {
		Date date = new Date();
		String s = date.getTime()+".zip";
		return s;
	}


	private void zipFile(File[] subs, String baseName, ZipOutputStream zos)  {
		// TODO Auto-generated method stub
		BufferedInputStream bis = null ;
		FileInputStream fis = null;
		try {
		byte[] buf = new byte[1024*10];
		for(int i=0; i< subs.length;i++){
			File f = subs[i];
				zos.putNextEntry(new ZipEntry(baseName+f.getName()));
			
			 fis = new FileInputStream(f);
			bis = new BufferedInputStream(fis,1024*10);
			int read = 0 ;
			while((read = bis.read(buf, 0, 1024*10)) != -1){
				zos.write(buf,0,read);
			}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bis.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


	@RequestMapping("findByPage")
	@ResponseBody
	public PageView findByPage(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "1") String pageNow,
			@RequestParam(defaultValue = "30") String pageSize, String column,
			String sort) throws Exception {

		/*User userSession = (User) getWebUserAttribute("userSession");*/
		
		List<TableMeta> list = new ArrayList<TableMeta>();

		// 查询设置
		String tbDbId = getPara("tbDbId");
		String schemaName = getPara("schemaName");
		String tbName = getPara("tbName");
		String taskUserStatus = getPara("taskUserStatus");
		
		TableMetaVO condition = new TableMetaVO();
		if (Common.isNotEmpty(tbDbId)) {
			condition.setTbDbId(Integer.valueOf(tbDbId));
		}
		
		if (Common.isNotEmpty(schemaName)) {
			condition.setSchemaCode(schemaName);
		}
		if (Common.isNotEmpty(tbName)) {
			condition.setTblCode(tbName);
		}
		if(Common.isNotEmpty(taskUserStatus)){
			condition.setTaskUserStatus(taskUserStatus);
		}
		//排序
		OrderBy orderBy = new OrderBy();
		if (Common.isNotEmpty(sort)&&Common.isNotEmpty(column)) {
			String colName = "";
			if(column.equalsIgnoreCase("tbDbId")){
				colName = "TB_DB_ID";
			}
			if(column.equalsIgnoreCase("taskUserStatus")){
				colName = "TASK_USER_STATUS";
			}
			boolean order = true;
			if(sort.equalsIgnoreCase("DESC")){
				order = false;
			}
			orderBy.add(colName, order);
		}
		else{
			orderBy.add("TASK_USER_STATUS", true);
		}
		
	//	if(!"0000001".equals(userSession.getUserId())){//如果不是根用户,查询出分配给这个用户的列表信息
			/*condition.setTaskUserId(userSession.getUserId());*/
	//	}
		
			
		list = tableMetaService.find(condition, orderBy, Integer.valueOf(pageSize), Integer.valueOf(pageNow));
		
		PageView pageView = null;
		pageView = new PageView(Integer.valueOf(pageNow));
		pageView.setRecords(list);
		pageView.setPageSize(Integer.valueOf(pageSize));
		int count = tableMetaService.count(condition);
		pageView.setRowCount(count);
		
		return pageView;
	}
	
	@RequestMapping("saveSingleObj")
	@ResponseBody
	public String saveSingleObj(String id ,String totalRecordNumObj,String avgIncRecordNumObj,String bucketNumObj,String partionObj,String bucketTypeObj,String avgRecordLengthObj,String tblTypeObj ) {
		TableMeta single = new TableMeta();
		if(StringUtils.isNotBlank(totalRecordNumObj))
			single.setTotalRecordNum(Long.valueOf(totalRecordNumObj));
		if(StringUtils.isNotBlank(avgIncRecordNumObj))
			single.setAvgIncRecordNum(Long.valueOf(avgIncRecordNumObj));
		if(StringUtils.isNotBlank(bucketNumObj)){
			single.setBucketNum(Integer.valueOf(bucketNumObj));
		}
		if(StringUtils.isNotBlank(partionObj)){
			single.setPartitionType(partionObj);
		}
		if(StringUtils.isNotBlank(bucketTypeObj)){
			single.setBucketType(Integer.valueOf(bucketTypeObj));
		}
		
		if(StringUtils.isNotBlank(avgRecordLengthObj)){
			single.setAvgRecordLength(Integer.valueOf(avgRecordLengthObj));
		}
		
		if(StringUtils.isNotBlank(tblTypeObj)){
			single.setTblType(tblTypeObj);//1:流水   2:拉链
		}
		
		if(StringUtils.isNotEmpty(totalRecordNumObj) || StringUtils.isNotEmpty(avgIncRecordNumObj) || StringUtils.isNotEmpty(bucketNumObj) || StringUtils.isNotEmpty(partionObj) || StringUtils.isNotEmpty(bucketTypeObj) || StringUtils.isNotEmpty(avgRecordLengthObj) || StringUtils.isNotEmpty(tblTypeObj))
		
			tableMetaService.update(single, Integer.valueOf(id));
		
		return "success";
	}
	

	@RequestMapping("editUI")
	public String editUI(Model model, Integer id) throws Exception {
		if (Common.isNotEmpty(id.toString())) {
			TableMeta single = tableMetaService.get(id);
			model.addAttribute("tableMeta", single);
		}
		return "/system/tableInfo/edit";
	}
	
	
	@RequestMapping("editTableSave")
	@ResponseBody
	public String editTableSave(TableMeta tableMeta,Model model, Integer id) throws Exception {
		
		TableMeta single = new TableMeta();
		single.setAvgIncRecordNum(tableMeta.getAvgIncRecordNum());
		single.setAvgRecordLength(tableMeta.getAvgRecordLength());
		single.setBucketType(tableMeta.getBucketType());
		single.setTotalRecordNum(tableMeta.getTotalRecordNum());
		single.setPartitionType(tableMeta.getPartitionType());
		single.setTblDesc(tableMeta.getTblDesc());
		single.setBucketNum(tableMeta.getBucketNum());
		single.setTotalRecordNum(tableMeta.getTotalRecordNum());
		
		tableMetaService.update(single, tableMeta.getId());
		return "success";
	}
	
	@RequestMapping("fenpeiTableSave")
	@ResponseBody
	public String fenpeiTableSave(String userId, String idsList,String userName) throws Exception {
		
		System.out.println(userId+"---------"+idsList);
		
		List<String> list2 = Arrays.asList(idsList.split(","));
		
		
		for (String tableId : list2) {
			
			TableMetaVO tableMetaVO= new TableMetaVO();
			tableMetaVO.setId(Integer.valueOf(tableId));
			TableMeta tableMeta = this.tableMetaService.get(Integer.valueOf(tableId));
			UserTableRelationVO userTableRelationVO = new UserTableRelationVO();
			userTableRelationVO.setTableName(tableMeta.getTblNm());
			userTableRelationVO.setStatus("00");
			userTableRelationVO.setTableId(tableId);//TODO 是否一个用户只能分配一个表的权限,需要待定
			
			UserTableRelation userTableRelation = new UserTableRelation();
			
			userTableRelation.setStatus("01");//00表示未完成, 01表示已完成
			userTableRelation.setTableId(tableId);
			userTableRelation.setUserId(userId);
			userTableRelation.setUserName(userName);
			userTableRelation.setTableName(tableMeta.getTblNm());

			userTableRelationService.insert(userTableRelation);//保存
		}
		
		return "success";
	}
	
	
	@RequestMapping("editUIExt")
	public String editUIExt(Model model, String ids) throws Exception {
		if (Common.isNotEmpty(ids)) {
			model.addAttribute("ids", ids);
		}
		return "/system/position/edit_ext";
	}
	
	@RequestMapping("fenpeiUI")
	public String fenpeiUI(Model model,String ids){
		Object md5  = getWebUserAttribute("_current_userIdMD5");
		
		try{
			/*List<User> userList = userItf.getUserList(md5+"");
			System.out.println(userList.get(0).getUserId());
			model.addAttribute("userList", userList);*/
			model.addAttribute("idsList", ids);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return "/system/tableInfo/fenpei";
	}


	@ResponseBody
	@RequestMapping("editEntity")
	public String editEntity(String txtGroupsSelect, String orgId, String posiId)
			throws Exception {

	/*	PosiRoleVO condition = new PosiRoleVO();
		if(Common.isNotEmpty(orgId)){
			condition.setOrgId(orgId);
		}
		if(Common.isNotEmpty(posiId)){
			condition.setPosiId(posiId);
		}
*/
		return "success";
	}

	@ResponseBody
	@RequestMapping("editEntityExt")
	public String editEntityExt(String txtGroupsSelect, String ids)
			throws Exception {

		String[] idArray = ids.split(",");

		for (int i = 0; i < idArray.length; i++) {

		}

		return "success";
	}

	@ResponseBody
	@RequestMapping("loadEntity")
	public String loadEntity(Integer[] ids) throws Exception {
		List<Integer> idList = new ArrayList<Integer>();
		for (Integer id : ids) {
			idList.add(id);
		}
		
		List<TableMeta> res = tableMetaService.findByIdList(idList);
		
		// 配置文件信息
		Map<String, HashMap<String, String>> dbMap;
		DbUtil dbutil = new DbUtil();
		dbMap = dbutil.getDbConfigMap();
		HashMap<String,String> dbInfoMap;
		for(TableMeta single : res){
			
			ColumnMetaVO condition = new ColumnMetaVO();
			condition.setTbId(single.getId());
			
			List<ColumnMeta> list2 = columnMetaService.find(condition);
			columnMetaService.remove(condition);
			
			DbInfo dbInfo = dbInfoService.get(single.getTbDbId());
			
			if(dbInfo.getDbType().equalsIgnoreCase("DB2")){
				String dbType = "DB2";
				dbInfoMap = new HashMap<String,String>();
				dbInfoMap = dbMap.get(dbType);
				dbInfoMap.put("userName", dbInfo.getDbUser());
				dbInfoMap.put("userpwd", dbInfo.getDbPassword());
				dbInfoMap.put("jdbc", dbMap.get(dbType).get("JdbcURL") + dbInfo.getDbIp()
						+ dbMap.get(dbType).get("dbStr") + dbInfo.getDbName());
				dbInfoMap.put("driver", dbMap.get(dbType).get("driverClassName"));
				dbInfoMap.put("dbType", dbType);
				dbInfoMap.put("database", dbInfo.getDbName());
				dbInfoMap.put("schamaName", single.getSrcStmNm());
				dbInfoMap.put("ip", dbInfo.getDbIp());
				// 获取表名
				List<Map<String, String>> columnList = dbutil.getColumnNames(dbInfoMap, single.getTblCode());
				if(null!=columnList || columnList.size()>0)
				for(Map map : columnList){
					ColumnMeta entity = new ColumnMeta();
					
					entity.setFieldCode(map.get("COLNAME").toString());
					entity.setFieldNm(map.get("COLNAME").toString());
					entity.setFldSeqNum(Integer.valueOf(map.get("COLNO").toString()));
					entity.setDataTp(map.get("TYPENAME").toString());
					entity.setFldLength(Integer.valueOf(map.get("LENGTH").toString()));
					entity.setIsNull(map.get("NULLS").toString());
					entity.setTblNm(single.getTblNm());
					entity.setSrcStmNm(single.getSrcStmNm());
					entity.setTbId(single.getId());
					if(map.get("KEYSEQ")!=null){
						entity.setPrimaryKeyFlag(Integer.valueOf(map.get("KEYSEQ").toString()));
					}
					if(map.get("REMARKS")!=null){
						entity.setFieldDesc(map.get("REMARKS").toString());
					}
					
					ColumnMetaVO  columnMetaVO = new ColumnMetaVO();
					
					columnMetaVO.setFieldCode(map.get("COLNAME").toString());
					columnMetaVO.setTblNm(single.getTblNm());
					columnMetaVO.setSrcStmNm(single.getSrcStmNm());
					
					for (ColumnMeta col : list2) {
						if(col.getFieldCode().equals(entity.getFieldCode())&& col.getTblNm().equals(entity.getTblNm())&& col.getSrcStmNm().equals(entity.getSrcStmNm())){
							entity.setBucketKeyFlag(col.getBucketKeyFlag());
							entity.setPartitionKeyFlag(col.getPartitionKeyFlag());
							entity.setIsAmount(col.getIsAmount());
							entity.setIsCode(col.getIsCode());
							entity.setIsNull(col.getIsNull());
						}
					}
						columnMetaService.insert(entity);
					}
				
			}
		}
		
		return "success";
	}
	
	/**
	 * 验证账号是否存在
	 * 
	 * @author numberONe date：2014-2-19
	 * @param name
	 * @return
	 */
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist(Integer id) {
		TableMeta single = tableMetaService.get(id);
		if (single == null) {
			return true;
		} else {
			return false;
		}
	}


}