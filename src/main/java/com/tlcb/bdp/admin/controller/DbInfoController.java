package com.tlcb.bdp.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.calanger.common.dao.OrderBy;
import com.tlcb.bdp.admin.exception.SystemException;
import com.tlcb.bdp.admin.util.BUCKET_PARTITION_INFO;
import com.tlcb.bdp.admin.util.CardXls;
import com.tlcb.bdp.admin.util.Common;
import com.tlcb.bdp.admin.util.DbUtil;
import com.tlcb.bdp.admin.util.JsonUtils;
import com.tlcb.bdp.admin.util.MAPPING_AMT_KEY;
import com.tlcb.bdp.admin.util.MAPPING_CL_KEY;
import com.tlcb.bdp.admin.util.MapUtils;
import com.tlcb.bdp.admin.util.POIUtils;
import com.tlcb.bdp.admin.util.PageView;
import com.tlcb.bdp.admin.util.TreeObject;
import com.tlcb.bdp.model.ColumnMeta;
import com.tlcb.bdp.model.DbInfo;
import com.tlcb.bdp.model.TableMeta;
import com.tlcb.bdp.service.ColumnMetaService;
import com.tlcb.bdp.service.DbInfoService;
import com.tlcb.bdp.service.TableMetaService;
import com.tlcb.bdp.vo.ColumnMetaVO;
import com.tlcb.bdp.vo.DbInfoVO;
import com.tlcb.bdp.vo.TableMetaVO;

@Controller
@RequestMapping("/dbInfo/")
public class DbInfoController extends BaseController {

	@Autowired
	private DbInfoService dbInfoService;

	// @Autowired
	// private TableInfoService tableInfoService;
	@Autowired
	private TableMetaService tableMetaService;

	@Autowired
	private ColumnMetaService columnMetaService;

	/*
	 * @Autowired private ResourcesItf resourcesItf;
	 */

	@RequestMapping("list")
	public String listUI(Model model, String id) throws Exception {

		/*
		 * ResourcesVO vo = new ResourcesVO(); vo.setParentId(id);
		 * 
		 * List<Resources> res = resourcesItf.bdpButtom(vo,"level",true);
		 */
		List<TreeObject> list = new ArrayList<TreeObject>();

		for (int i = 0; i < 4; i++) {

			TreeObject ts = new TreeObject();
			if (i == 0) {

				ts.setResId(i + "");
				ts.setResNm("新增表" + (i - 2));
				ts.setParentId("3");
				ts.setType("2");

				ts.setResurl("/dbInfo/addTableUI.do");
				StringBuffer sbBuffer = new StringBuffer("");
				sbBuffer.append(
						"<button type=\"button\" id=\"addTable\" class=\"'btn btn btn-grey marR10'\">新增表</button>");
				ts.setDescription(sbBuffer.toString());
				list.add(ts);
			}
			if (i == 1) {

				ts.setResId(i + "");
				ts.setResNm("数据源测试" + (i - 2));
				ts.setParentId("3");
				ts.setType("2");

				ts.setResurl("/dbInfo/testConn.do");
				StringBuffer sbBuffer = new StringBuffer("");
				sbBuffer.append(
						"<button type=\"button\" id=\"testConn\" class=\"'btn btn btn-green marR10'\">数据源测试</button>");
				ts.setDescription(sbBuffer.toString());
				list.add(ts);
			}
			

		//	list.add(ts);
		}

		model.addAttribute("res", list);
		return "/system/dbInfo/list";

	}

	@RequestMapping("findByPage")
	@ResponseBody
	public PageView findByPage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "1") String pageNow, @RequestParam(defaultValue = "10") String pageSize,
			String column, String sort) throws Exception {

		List<DbInfo> list = new ArrayList<DbInfo>();

		// 查询设置
		String dbType = getPara("dbType");
		String dbName = getPara("dbName");

		DbInfoVO condition = new DbInfoVO();
		if (Common.isNotEmpty(dbType)) {
			condition.setDbType(dbType);
		}
		if (Common.isNotEmpty(dbName)) {
			condition.setDbName(dbName);
			;
		}

		// 排序
		OrderBy orderBy = new OrderBy();
		if (Common.isNotEmpty(sort) && Common.isNotEmpty(column)) {
			String colName = "";
			if (column.equalsIgnoreCase("dbId")) {
				colName = "DB_ID";
			} else if (column.equalsIgnoreCase("dbType")) {
				colName = "DB_TYPE";
			}
			boolean order = true;
			if (sort.equalsIgnoreCase("DESC")) {
				order = false;
			}
			orderBy.add(colName, order);
		} else {
			orderBy.add("DB_ID", true);
		}

		list = dbInfoService.find(condition, orderBy, Integer.valueOf(pageSize), Integer.valueOf(pageNow));

		PageView pageView = null;
		pageView = new PageView(Integer.valueOf(pageNow));
		pageView.setRecords(list);
		int count = dbInfoService.count(condition);
		pageView.setRowCount(count);

		return pageView;
	}

	@RequestMapping("testConn")
	@ResponseBody
	public String testConn(String ids) {

		List<Integer> idList = new ArrayList<Integer>();
		idList.add(Integer.valueOf(ids));
		List<DbInfo> res = dbInfoService.findByIdList(idList);
		DbInfo single = null;
		if (res.size() > 0) {
			single = res.get(0);
		}

		String dbType = "DB2";
		Map<String, String> dbInfoMap = new HashMap<String, String>();
		DbUtil dbutil = new DbUtil();
		Map<String, HashMap<String, String>> dbMap = dbutil.getDbConfigMap();
		dbInfoMap = dbMap.get(dbType);
		dbInfoMap.put("userName", single.getDbUser());
		dbInfoMap.put("userpwd", single.getDbPassword());
		dbInfoMap.put("jdbc", dbMap.get(dbType).get("JdbcURL") + single.getDbIp() + dbMap.get(dbType).get("dbStr")
				+ single.getDbName());
		dbInfoMap.put("driver", dbMap.get(dbType).get("driverClassName"));
		dbInfoMap.put("dbType", dbType);
		dbInfoMap.put("database", single.getDbName());
		dbInfoMap.put("schamaName", single.getDbSchema());
		dbInfoMap.put("ip", single.getDbIp());

		Object obj = dbutil.getStatement(dbInfoMap);
		if (null != obj) {
			return "success";
		} else {
			return "error";
		}

	}

	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		return "/system/dbInfo/add";
	}

	@RequestMapping("addTableUI")
	public String addTableUI(Model model, String id) throws Exception {

		if (Common.isNotEmpty(id.toString())) {
			DbInfo single = dbInfoService.get(Integer.valueOf(id));
			model.addAttribute("single", single);
		}
		return "/system/dbInfo/addTableUI";
	}

	@RequestMapping("uploadUI")
	public String uploadUI(String id, Model model) {
		model.addAttribute("dataId", id);
		return "/system/dbInfo/upload";
	}

	@RequestMapping("upload")
	@ResponseBody
	public String uploadToSuccess(@RequestParam("uploadfile") MultipartFile uploadfile, HttpServletRequest request,
			String dataId) throws IOException {

		String fileType = uploadfile.getContentType();

		List<Integer> idList = new ArrayList<Integer>();
		idList.add(Integer.valueOf(dataId));
		List<DbInfo> res = dbInfoService.findByIdList(idList);
		DbInfo single = null;
		if (res.size() > 0) {
			single = res.get(0);
		}
		String dbType = "DB2";
		Map<String, String> dbInfoMap = new HashMap<String, String>();
		DbUtil dbutil = new DbUtil();
		Map<String, HashMap<String, String>> dbMap = dbutil.getDbConfigMap();
		dbInfoMap = dbMap.get(dbType);
		dbInfoMap.put("userName", single.getDbUser());
		dbInfoMap.put("userpwd", single.getDbPassword());
		dbInfoMap.put("jdbc", dbMap.get(dbType).get("JdbcURL") + single.getDbIp() + dbMap.get(dbType).get("dbStr")
				+ single.getDbName());
		dbInfoMap.put("driver", dbMap.get(dbType).get("driverClassName"));
		dbInfoMap.put("dbType", dbType);
		dbInfoMap.put("database", single.getDbName());
		dbInfoMap.put("schamaName", single.getDbSchema());
		dbInfoMap.put("ip", single.getDbIp());
		if (!uploadfile.isEmpty()) {

			if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(fileType)) {
				return "upload_error_type"; // 上传文件类型错误
			} else {
				String basePath = "c:/upload";
				File dir = new File(basePath);
				if (!dir.exists()) {
					dir.mkdir();
				}

				String fileName = basePath + "/" + uploadfile.getOriginalFilename();
				File file = new File(fileName);
				try {
					FileUtils.writeByteArrayToFile(file, uploadfile.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (file.exists()) {

					Map<String, List<Object>> map = null;
					try {
						map = CardXls.readXls(fileName);
						List<Object> list = map.get("bucket_partition_info");
						List<Object> isCLList = map.get("mapping_cl_key");
						List<Object> isAmountList = map.get("mapping_amt_key");

						for (Object object : list) {
							if (object instanceof BUCKET_PARTITION_INFO) {
								BUCKET_PARTITION_INFO bucket_partition_info = (BUCKET_PARTITION_INFO) object;

								Map<String, String> schemaNameTableName = new HashMap<String, String>();
								schemaNameTableName.put("schema", bucket_partition_info.getDB_DM().trim());
								schemaNameTableName.put("table", bucket_partition_info.getTAB_NM().trim());
								// dbInfoMap.put("schemaName",
								// bucket_partition_info.getDB_DM().trim());
								List<Map<String, String>> tableList = dbutil.getTableNames(dbInfoMap,
										schemaNameTableName);
								try {
									for (Map map2 : tableList) {
										TableMeta entity = new TableMeta();

										entity.setSchemaCode(map2.get("TABSCHEMA").toString().trim());
										entity.setSrcStmNm(map2.get("TABSCHEMA").toString().trim());
										entity.setTbDbId(single.getDbId());
										entity.setTblCode(map2.get("TABNAME").toString());
										entity.setTblNm(map2.get("TABNAME").toString());
										entity.setTotalFldNum(Integer.valueOf(map2.get("COLCOUNT").toString()));
										entity.setLstUpdtDt(new Date());
										entity.setBucketType(1);
										entity.setTblType(bucket_partition_info.getTAB_TYPE());
										entity.setBucketNum(Integer.valueOf(bucket_partition_info.getHIS_BUCKET_NUM()));
										entity.setTbDbId(Integer.valueOf(dataId));
										entity.setPartitionType(bucket_partition_info.getPARTITION_TYPE());
										entity.setTblDesc(map2.get("REMARKS") + "");
										entity.setJieBucketNum(bucket_partition_info.getSECTION_BUCKET_NUM());

										TableMetaVO condition = new TableMetaVO();
										condition.setSrcStmNm(map2.get("TABSCHEMA").toString());
										condition.setTblNm(map2.get("TABNAME").toString());

										this.tableMetaService.remove(condition);

										this.tableMetaService.insert(entity);

										List<Map<String, String>> columnList = dbutil.getColumnNames(dbInfoMap,
												bucket_partition_info.getTAB_NM().trim(),
												bucket_partition_info.getDB_DM().trim());
										if (null != columnList || columnList.size() > 0)
											for (Map map1 : columnList) {
												ColumnMeta entity1 = new ColumnMeta();

												entity1.setFieldCode(map1.get("COLNAME").toString());
												entity1.setFieldNm(map1.get("COLNAME").toString());
												entity1.setFldSeqNum(Integer.valueOf(map1.get("COLNO").toString()));
												entity1.setDataTp(map1.get("TYPENAME").toString());
												entity1.setFldLength(Integer.valueOf(map1.get("LENGTH").toString()));
												entity1.setIsNull(map1.get("NULLS").toString());
												entity1.setTblNm(bucket_partition_info.getTAB_NM());
												entity1.setSrcStmNm(bucket_partition_info.getDB_DM());
												entity1.setTbId(Integer.valueOf(dataId));
												if (map1.get("KEYSEQ") != null) {
													entity1.setPrimaryKeyFlag(
															Integer.valueOf(map1.get("KEYSEQ").toString()));
												}
												if (map1.get("REMARKS") != null) {
													entity1.setFieldDesc(map1.get("REMARKS").toString());
												}

												if (bucket_partition_info.getBUCKET_FIELD()
														.equals(map1.get("COLNAME").toString().trim())) {
													entity1.setBucketKeyFlag(1);
												}

												entity1.setPartionWay(bucket_partition_info.getPARTITION_TYPE());

												for (Object amount : isAmountList) {
													if (amount instanceof MAPPING_AMT_KEY) {

														if (((MAPPING_AMT_KEY) amount).getAMT_KEY()
																.equals(map1.get("COLNAME").toString())
																&& ((MAPPING_AMT_KEY) amount).getTAB_NM()
																		.equals(bucket_partition_info.getTAB_NM())
																&& ((MAPPING_AMT_KEY) amount).getDB_NM()
																		.equals(bucket_partition_info.getDB_DM())) {
															entity1.setIsAmount("1");
														}
													}
												}

												for (Object cl : isCLList) {
													if (cl instanceof MAPPING_CL_KEY) {
														if (((MAPPING_CL_KEY) cl).getDB_NM()
																.equals(bucket_partition_info.getDB_DM())
																&& ((MAPPING_CL_KEY) cl).getCL_KEY()
																		.equals(map1.get("COLNAME").toString())
																&& ((MAPPING_CL_KEY) cl).getTAB_NM()
																		.equals(map2.get("TABNAME").toString())) {
															entity1.setIsCode("1");
														}
													}
												}

												ColumnMetaVO columnMetaVO = new ColumnMetaVO();

												columnMetaVO.setFieldCode(map1.get("COLNAME").toString());
												columnMetaVO.setTblNm(bucket_partition_info.getTAB_NM());
												columnMetaVO.setSrcStmNm(bucket_partition_info.getDB_DM());

												columnMetaService.remove(columnMetaVO);
												/*
												 * List<ColumnMeta> list2 =
												 * columnMetaService.find(
												 * condition);
												 * 
												 * for (ColumnMeta col : list2)
												 * {
												 * if(col.getFieldCode().equals(
												 * entity1.getFieldCode())&&
												 * col.getTblNm().equals(entity1
												 * .getTblNm())&&
												 * col.getSrcStmNm().equals(
												 * entity1.getSrcStmNm())){
												 * entity1.setBucketKeyFlag(col.
												 * getBucketKeyFlag());
												 * entity1.setPartitionKeyFlag(
												 * col.getPartitionKeyFlag());
												 * entity1.setIsAmount(col.
												 * getIsAmount());
												 * entity1.setIsCode(col.
												 * getIsCode());
												 * entity1.setIsNull(col.
												 * getIsNull()); } }
												 */
												
												System.out.println(entity1);
												columnMetaService.insert(entity1);
											}

									}

									// 插入至META表
								} catch (Exception e) {
									e.printStackTrace();
									// throw new SystemException("添加账号异常");
								}
							}

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					return "success";

				}

			}

		} else {
			return "upload_error_empty";// 上传的文件不能为空
		}

		return "success";
	}

	@RequestMapping(value = "/addTableEntity")
	@ResponseBody
	public String addTableEntity(String dbId, String tableNames) {

		String[] tableSchema = tableNames.trim().split(",");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (String s : Arrays.asList(tableSchema)) {
			Map<String, String> map = new HashMap<String, String>();
			String[] split = s.split("\\.");
			System.out.println(split.length);
			map.put("schema", split[0]);
			map.put("table", split[1]);
			list.add(map);
		}

		List<Integer> idList = new ArrayList<Integer>();
		idList.add(Integer.valueOf(dbId));
		List<DbInfo> res = dbInfoService.findByIdList(idList);
		DbInfo single = null;
		if (res.size() > 0) {
			single = res.get(0);
		}

		String dbType = "DB2";
		Map<String, String> dbInfoMap = new HashMap<String, String>();
		DbUtil dbutil = new DbUtil();
		Map<String, HashMap<String, String>> dbMap = dbutil.getDbConfigMap();
		dbInfoMap = dbMap.get(dbType);
		dbInfoMap.put("userName", single.getDbUser());
		dbInfoMap.put("userpwd", single.getDbPassword());
		dbInfoMap.put("jdbc", dbMap.get(dbType).get("JdbcURL") + single.getDbIp() + dbMap.get(dbType).get("dbStr")
				+ single.getDbName());
		dbInfoMap.put("driver", dbMap.get(dbType).get("driverClassName"));
		dbInfoMap.put("dbType", dbType);
		dbInfoMap.put("database", single.getDbName());
		dbInfoMap.put("schamaName", single.getDbSchema());
		dbInfoMap.put("ip", single.getDbIp());
		// 获取表名

		for (Map<String, String> map : list) {
			List<Map> tableList = dbutil.getTableNames(dbInfoMap, map);
			try {
				for (Map map2 : tableList) {
					TableMeta entity = new TableMeta();

					entity.setSchemaCode(map2.get("TABSCHEMA").toString().trim());
					entity.setSrcStmNm(map2.get("TABSCHEMA").toString());
					entity.setTbDbId(single.getDbId());
					entity.setTblCode(map2.get("TABNAME").toString());
					entity.setTblNm(map2.get("TABNAME").toString());
					entity.setTotalFldNum(Integer.valueOf(map2.get("COLCOUNT").toString()));
					entity.setLstUpdtDt(new Date());

					TableMetaVO condition = new TableMetaVO();
					condition.setSrcStmNm(map2.get("TABSCHEMA").toString());
					condition.setTblNm(map2.get("TABNAME").toString());
					List<TableMeta> list2 = tableMetaService.find(condition);

					for (TableMeta oldTableMeta : list2) {
						if (entity.equals(oldTableMeta)) {
							entity.setAvgIncRecordNum(oldTableMeta.getAvgIncRecordNum());
							entity.setAvgRecordLength(oldTableMeta.getAvgRecordLength());
							entity.setTotalRecordNum(oldTableMeta.getTotalRecordNum());
							entity.setBucketType(oldTableMeta.getBucketType());
							entity.setBucketNum(oldTableMeta.getBucketNum());
							entity.setTblDesc(oldTableMeta.getTblDesc());
						}
					}
					
					TableMetaVO tableMetaVO = new TableMetaVO();
						
					tableMetaVO.setTblCode(map2.get("TABNAME").toString());
					tableMetaVO.setSrcStmNm(map2.get("TABSCHEMA").toString().trim());
					tableMetaService.remove(tableMetaVO);
					entity.setTblType("NORMAL");
					this.tableMetaService.insert(entity);
					dbInfoMap.put("schamaName", map2.get("TABSCHEMA").toString().trim());
					List<Map<String, String>> columnList = dbutil.getColumnNames(dbInfoMap,
							map2.get("TABNAME").toString().trim());
					List list1 = new ArrayList<>();
					if (null != columnList || columnList.size() > 0)
						
						for (Map map1 : columnList) {
							if (map1.get("KEYSEQ") != null) {
								list1.add(map1.get("COLNAME"));
							}
						}
						for (Map map1 : columnList) {
							
							ColumnMeta entity1 = new ColumnMeta();

							entity1.setFieldCode(map1.get("COLNAME").toString());
							entity1.setFieldNm(map1.get("COLNAME").toString());
							entity1.setFldSeqNum(Integer.valueOf(map1.get("COLNO").toString()));
							entity1.setDataTp(map1.get("TYPENAME").toString());
							entity1.setFldLength(Integer.valueOf(map1.get("LENGTH").toString()));
							entity1.setIsNull(map1.get("NULLS").toString());
							entity1.setTblNm(map2.get("TABNAME")+"");
							entity1.setSrcStmNm(map2.get("TABSCHEMA").toString().trim());
							entity1.setTbId(Integer.valueOf(dbId));
							if (map1.get("KEYSEQ") != null) {
								entity1.setPrimaryKeyFlag(
										Integer.valueOf(map1.get("KEYSEQ").toString()));
							}
							if (map1.get("REMARKS") != null) {
								entity1.setFieldDesc(map1.get("REMARKS").toString());
							}

							if (list1.size()>0) {
								if(map1.get("COLNAME").toString().equals(list1.get(list1.size()-1))){
									entity1.setBucketKeyFlag(1);
								}
							}

							entity1.setPartionWay("NONE");
							
							ColumnMetaVO columnMetaVO = new ColumnMetaVO();

							columnMetaVO.setFieldCode(map1.get("COLNAME").toString());
							columnMetaVO.setTblNm(map1.get("TABNAME").toString());
							columnMetaVO.setSrcStmNm(map1.get("TABSCHEMA").toString().trim());

							columnMetaService.remove(columnMetaVO);
							columnMetaService.insert(entity1);
						}
					
					
				}

				// 插入至META表
			} catch (Exception e) {
				e.printStackTrace();
				// throw new SystemException("添加账号异常");
			}
		}
		return "success";
	}

	@RequestMapping(value = "/addEntity")
	@ResponseBody
	public String addEntity(String dbAlias, String dbType, String dbDesc, String dbIp, String dbName, String dbPassword,
			String dbSchema, String dbStatus, String dbUser) {
		try {
			DbInfo entity = new DbInfo();

			if (StringUtils.isNotBlank(dbAlias))
				entity.setDbAlias(dbAlias);
			if (StringUtils.isNotBlank(dbType))
				entity.setDbType(dbType);
			if (StringUtils.isNotBlank(dbName))
				entity.setDbName(dbName);
			if (StringUtils.isNotBlank(dbSchema))
				entity.setDbSchema(dbSchema);
			if (StringUtils.isNotBlank(dbIp))
				entity.setDbIp(dbIp);
			if (StringUtils.isNotBlank(dbUser))
				entity.setDbUser(dbUser);
			if (StringUtils.isNotBlank(dbPassword))
				entity.setDbPassword(dbPassword);
			if (StringUtils.isNotBlank(dbStatus))
				entity.setDbStatus(dbStatus);
			if (StringUtils.isNotBlank(dbDesc))
				entity.setDbDesc(dbDesc);
			dbInfoService.insert(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("添加账号异常");
		}
		return "success";
	}

	@RequestMapping("editUI")
	public String editUI(Model model, String dbId) throws Exception {
		if (Common.isNotEmpty(dbId.toString())) {
			DbInfo single = dbInfoService.get(Integer.valueOf(dbId));
			model.addAttribute("single", single);
		}
		return "/system/dbInfo/edit";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	public String editEntity(DbInfo single) throws Exception {
		DbInfo dbInfoVO = new DbInfo();
		dbInfoVO.setDbAlias(single.getDbAlias());
		dbInfoVO.setDbDesc(single.getDbDesc());
		dbInfoVO.setDbIp(single.getDbIp());
		dbInfoVO.setDbUser(single.getDbUser());
		dbInfoVO.setDbType(single.getDbType());
		dbInfoVO.setDbPassword(single.getDbPassword());
		dbInfoVO.setDbSchema(single.getDbSchema());
		dbInfoVO.setDbStatus(single.getDbStatus());
		dbInfoVO.setDbName(single.getDbName());

		dbInfoService.update(dbInfoVO, single.getDbId());

		return "success";
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	public String deleteEntity(Integer[] ids) throws Exception {
		for (Integer id : ids) {
			dbInfoService.remove(id);
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping("loadEntity")
	public String loadEntity(Integer[] ids) throws Exception {
		/*
		 * List<Integer> idList = new ArrayList<Integer>(); for (Integer id :
		 * ids) { idList.add(id); }
		 * 
		 * List<DbInfo> res = dbInfoService.findByIdList(idList);
		 * 
		 * // 配置文件信息 Map<String, HashMap<String, String>> dbMap; DbUtil dbutil =
		 * new DbUtil(); dbMap = dbutil.getDbConfigMap(); HashMap<String,String>
		 * dbInfoMap; for(DbInfo single : res){
		 * 
		 * TableMetaVO condition = new TableMetaVO();
		 * condition.setTbDbId(Integer.valueOf(single.getDbId()));
		 * List<TableMeta> list2 = tableMetaService.find(condition);
		 * tableMetaService.remove(condition);
		 * 
		 * if(single.getDbType().equalsIgnoreCase("DB2")){ String dbType =
		 * "DB2"; dbInfoMap = new HashMap<String,String>(); dbInfoMap =
		 * dbMap.get(dbType); dbInfoMap.put("userName", single.getDbUser());
		 * dbInfoMap.put("userpwd", single.getDbPassword());
		 * dbInfoMap.put("jdbc", dbMap.get(dbType).get("JdbcURL") +
		 * single.getDbIp() + dbMap.get(dbType).get("dbStr") +
		 * single.getDbName()); dbInfoMap.put("driver",
		 * dbMap.get(dbType).get("driverClassName")); dbInfoMap.put("dbType",
		 * dbType); dbInfoMap.put("database", single.getDbName());
		 * dbInfoMap.put("schamaName", single.getDbSchema());
		 * dbInfoMap.put("ip", single.getDbIp()); // 获取表名 List<Map> tableList =
		 * dbutil.getTableNames(dbInfoMap,single.getDbSchema());
		 * if(tableList!=null && tableList.size()>0) for(Map map : tableList){
		 * TableMeta entity = new TableMeta();
		 * entity.setTbDbId(Integer.valueOf(single.getDbId()));
		 * entity.setSchemaCode(single.getDbSchema());
		 * entity.setSrcStmNm(single.getDbSchema());
		 * entity.setTblType(map.get("TYPE").toString());
		 * entity.setTblCode(map.get("TABNAME").toString());
		 * entity.setTblNm(map.get("TABNAME").toString());
		 * entity.setTotalFldNum(Integer.valueOf(map.get("COLCOUNT").toString())
		 * ); entity.setLstUpdtDt(new Date()); if(map.get("REMARKS")!=null){
		 * entity.setTblDesc(map.get("REMARKS").toString()); }
		 * 
		 * TableMetaVO tableMetaVO = new TableMetaVO();
		 * tableMetaVO.setSrcStmNm(single.getDbSchema());
		 * tableMetaVO.setTblCode(map.get("TABNAME").toString());
		 * List<TableMeta> list = tableMetaService.find(tableMetaVO); for
		 * (TableMeta oldTableMeta : list2) { if(entity.equals(oldTableMeta)){
		 * entity.setAvgIncRecordNum(oldTableMeta.getAvgIncRecordNum());
		 * entity.setAvgRecordLength(oldTableMeta.getAvgRecordLength());
		 * entity.setTotalRecordNum(oldTableMeta.getTotalRecordNum());
		 * entity.setBucketType(oldTableMeta.getBucketType());
		 * entity.setBucketNum(oldTableMeta.getBucketNum());
		 * entity.setTblDesc(oldTableMeta.getTblDesc()); } }
		 * //tableMetaService.remove(tableMetaVO);
		 * tableMetaService.insert(entity);
		 * 
		 * }
		 * 
		 * } }
		 */

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
	public boolean isExist(String dbAlias) {
		DbInfoVO condition = new DbInfoVO();
		condition.setDbAlias(dbAlias);
		DbInfo single = dbInfoService.get(condition);
		if (single == null) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String pageNow, String pageSize)
			throws IOException {
		String fileName = "用户列表";
		String userId = getPara("userId");
		String exportData = getPara("exportData");

		List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

		DbInfoVO condition = new DbInfoVO();
		if (Common.isNotEmpty(userId)) {
			// condition.setUserId(userId);
		}
		OrderBy orderBy = new OrderBy();
		orderBy.add("USER_ID", true);

		List<DbInfo> list = new ArrayList<DbInfo>();

		if (Common.isNotEmpty(pageNow) && Common.isNotEmpty(pageSize)) {
			list = dbInfoService.find(condition, orderBy, Integer.valueOf(pageSize), Integer.valueOf(pageNow));
		} else {
			list = dbInfoService.find(condition, orderBy, 10, 1);
		}

		List<Map> lis = new ArrayList<Map>();
		for (DbInfo single : list) {
			lis.add(MapUtils.java2Map(single));
		}

		POIUtils.exportToExcel(response, listMap, lis, fileName);
	}

}