package com.tlcb.bdp.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calanger.common.dao.OrderBy;
import com.tlcb.bdp.admin.util.Common;
import com.tlcb.bdp.admin.util.DbUtil;
import com.tlcb.bdp.admin.util.PageView;
import com.tlcb.bdp.model.ColumnMeta;
import com.tlcb.bdp.model.DbInfo;
import com.tlcb.bdp.model.MissionUserLog;
import com.tlcb.bdp.model.TableMeta;
import com.tlcb.bdp.service.ColumnMetaService;
import com.tlcb.bdp.service.DbInfoService;
import com.tlcb.bdp.service.MissionUserLogService;
import com.tlcb.bdp.service.TableMetaService;
import com.tlcb.bdp.service.extension.TableMetaExtService;
import com.tlcb.bdp.vo.ColumnMetaVO;
import com.tlcb.bdp.vo.TableMetaVO;

@Controller
@RequestMapping("/tasklist/")
public class TaskListController extends BaseController {

	/*@Autowired
	UserItf userItf;*/

	@Autowired
	private MissionUserLogService missionUserLogService;
	
	@Autowired
	private DbInfoService dbInfoService;

	@Resource
	private TableMetaExtService tableMetaExtService;

	@Autowired
	private TableMetaService tableMetaService;

	@Autowired
	private ColumnMetaService columnMetaService;

/*	@Autowired
	private ResourcesItf resourcesItf;*/

	@RequestMapping("fenyelist")
	public String listUI(Model model, String id, String tbDbId) throws Exception {

		/*ResourcesVO vo = new ResourcesVO();
		vo.setParentId(id);

		List<Resources> res = resourcesItf.bdpButtom(vo, "level", true);
		model.addAttribute("res", res);*/

		model.addAttribute("tbDbId", tbDbId);

		return "/system/tasklist/list";
	}
	
	
	@RequestMapping("findByPage")
	@ResponseBody
	public PageView findByPage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "1") String pageNow, @RequestParam(defaultValue = "10") String pageSize,
			String column, String sort) throws Exception {

		pageSize = "30";
	/*	
		User userSession = (User) getWebUserAttribute("userSession");
*/
		OrderBy orderBy = new OrderBy();
		if (Common.isNotEmpty(sort) && Common.isNotEmpty(column)) {
			String colName = "";
			if (column.equalsIgnoreCase("relationId")) {
				colName = "RELATION_ID";
			}
			boolean order = true;
			if (sort.equalsIgnoreCase("DESC")) {
				order = false;
			}
			orderBy.add(colName, order);
		} else {
			orderBy.add("ID", true);
		}

		TableMetaVO condition = new TableMetaVO();

		// 查询设置
		String userName = getPara("userName");
		if (Common.isNotEmpty(userName)) {
			condition.setTaskUserName(userName);
		}
		String tableName = getPara("tableName");
		if (Common.isNotEmpty(tableName)) {
			condition.setTblNm(tableName);
		}

		/*if (!"000001".equals(userSession.getUserId())) {
			condition.setTaskUserId(userSession.getUserId());
		}*/

		List<TableMeta> list = this.tableMetaService.find(condition, orderBy, Integer.valueOf(pageSize),
				Integer.valueOf(pageNow));

		PageView pageView = null;
		pageView = new PageView(Integer.valueOf(pageSize),Integer.valueOf(pageNow));
		pageView.setRecords(list);
		int count = this.tableMetaService.count(condition);
		pageView.setRowCount(count);
		return pageView;
	}

	@RequestMapping("saveSingleObj")
	@ResponseBody
	public String saveSingleObj(String id, String totalRecordNumObj, String avgIncRecordNumObj, String bucketNumObj,
			String partionObj, String bucketTypeObj) {
		TableMeta single = new TableMeta();
		if (StringUtils.isNotEmpty(totalRecordNumObj))
			single.setTotalRecordNum(Long.valueOf(totalRecordNumObj));
		if (StringUtils.isNotEmpty(avgIncRecordNumObj))
			single.setAvgIncRecordNum(Long.valueOf(avgIncRecordNumObj));
		if (StringUtils.isNotEmpty(bucketNumObj)) {
			single.setBucketNum(Integer.valueOf(bucketNumObj));
		}
		if (StringUtils.isNotEmpty(partionObj)) {
			single.setPartitionType(partionObj);
		}
		if (StringUtils.isNotEmpty(bucketTypeObj)) {
			single.setBucketType(Integer.valueOf(bucketTypeObj));
		}
		
		
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
	public String editTableSave(TableMeta tableMeta, Model model, Integer id) throws Exception {

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
	
	
	@RequestMapping("revokeSuccess")
	@ResponseBody
	public String revokeUI(Model model, String ids) throws Exception {
		
		String[] splits = ids.split(",");
		for (String id : splits) {
			TableMetaVO tableMeta = new TableMetaVO();
			tableMeta.setTaskUserStatus("0");
			tableMeta.setTaskUserName("-");
			tableMeta.setTaskUserId("-");
			
			List<TableMeta> tableMetaList = this.tableMetaService.findByIdList(Arrays.asList(Integer.valueOf(id)));
			
			MissionUserLog missionUserLog = new  MissionUserLog();
			if(tableMetaList.size()>0){
				TableMeta tableMeta1 = tableMetaList.get(0);
				missionUserLog.setProcessDesc("回收权限,用户名:"+tableMeta1.getTaskUserName()+",表:"+tableMeta1.getTblCode());
				missionUserLog.setProcessType("回收权限");
				missionUserLog.setTargetTable(tableMeta1.getTblCode());
				missionUserLog.setUserId(tableMeta1.getTaskUserId());
				missionUserLog.setUserName(tableMeta1.getTaskUserName());
				long startTimeMillis = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String processTime = sdf.format(startTimeMillis);
				missionUserLog.setProcessTime(sdf.parse(processTime));
				missionUserLogService.insert(missionUserLog);
			}
			
			this.tableMetaService.updateByIdList(tableMeta, Arrays.asList(Integer.valueOf(id)));
		}
		
		return "success";
	}

	
	@RequestMapping("fenpeiTableSave")
	@ResponseBody
	public String fenpeiTableSave(String userId, String idsList,String userName) throws Exception {


		List<String> list2 = Arrays.asList(idsList.split(","));
		for (String tableId : list2) {
			TableMetaVO tableMetaVO = new TableMetaVO();
			tableMetaVO.setTaskUserStatus("01");
			tableMetaVO.setTaskUserId(userId);
			tableMetaVO.setTaskUserName(userName);
			this.tableMetaService.update(tableMetaVO, Integer.valueOf(tableId));
			
			List<TableMeta> tableMetaList = this.tableMetaService.findByIdList(Arrays.asList(Integer.valueOf(tableId)));
			
			MissionUserLog missionUserLog = new  MissionUserLog();
			if(tableMetaList.size()>0){
				TableMeta tableMeta1 = tableMetaList.get(0);
				missionUserLog.setProcessDesc("分配权限,用户名:"+tableMeta1.getTaskUserName()+",表:"+tableMeta1.getTblCode());
				missionUserLog.setProcessType("分配权限");
				missionUserLog.setTargetTable(tableMeta1.getTblCode());
				missionUserLog.setUserId(tableMeta1.getTaskUserId());
				missionUserLog.setUserName(tableMeta1.getTaskUserName());
				long startTimeMillis = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String processTime = sdf.format(startTimeMillis);
				missionUserLog.setProcessTime(sdf.parse(processTime));
				missionUserLogService.insert(missionUserLog);
			}
			
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
	public String fenpeiUI(Model model, String ids) {
		Object md5 = getWebUserAttribute("_current_userIdMD5");

		try {
		/*	List<User> userList = userItf.getUserList(md5 + "");*/
			/*model.addAttribute("userList", userList);*/
			model.addAttribute("idsList", ids);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/tableInfo/fenpei";
	}

	@RequestMapping("finishTask")
	@ResponseBody
	public String finishedTask(String ids) {

		String[] splits = ids.split(",");
		for (String id : splits) {
			
			TableMetaVO tableMetaVO = new TableMetaVO();
			tableMetaVO.setTaskUserStatus("03");
			this.tableMetaService.update(tableMetaVO, Integer.valueOf(id));
		}

		return "success";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	public String editEntity(String txtGroupsSelect, String orgId, String posiId) throws Exception {

	/*	PosiRoleVO condition = new PosiRoleVO();
		if (Common.isNotEmpty(orgId)) {
			condition.setOrgId(orgId);
		}
		if (Common.isNotEmpty(posiId)) {
			condition.setPosiId(posiId);
		}*/

		return "success";
	}

	@ResponseBody
	@RequestMapping("editEntityExt")
	public String editEntityExt(String txtGroupsSelect, String ids) throws Exception {

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
		HashMap<String, String> dbInfoMap;
		for (TableMeta single : res) {

			ColumnMetaVO condition = new ColumnMetaVO();
			// condition.setTblNm(single.getTblNm());
			// condition.setSrcStmNm(single.getSrcStmNm());
			condition.setTbId(single.getId());

			List<ColumnMeta> list2 = columnMetaService.find(condition);
			columnMetaService.remove(condition);

			DbInfo dbInfo = dbInfoService.get(single.getTbDbId());

			if (dbInfo.getDbType().equalsIgnoreCase("DB2")) {
				String dbType = "DB2";
				dbInfoMap = new HashMap<String, String>();
				dbInfoMap = dbMap.get(dbType);
				dbInfoMap.put("userName", dbInfo.getDbUser());
				dbInfoMap.put("userpwd", dbInfo.getDbPassword());
				dbInfoMap.put("jdbc", dbMap.get(dbType).get("JdbcURL") + dbInfo.getDbIp()
						+ dbMap.get(dbType).get("dbStr") + dbInfo.getDbName());
				dbInfoMap.put("driver", dbMap.get(dbType).get("driverClassName"));
				dbInfoMap.put("dbType", dbType);
				dbInfoMap.put("database", dbInfo.getDbName());
				dbInfoMap.put("schamaName", dbInfo.getDbSchema());
				dbInfoMap.put("ip", dbInfo.getDbIp());
				// 获取表名
				List<Map<String, String>> columnList = dbutil.getColumnNames(dbInfoMap, single.getTblCode());
				if (null != columnList || columnList.size() > 0)
					for (Map map : columnList) {
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
						if (map.get("KEYSEQ") != null) {
							entity.setPrimaryKeyFlag(Integer.valueOf(map.get("KEYSEQ").toString()));
						}
						if (map.get("REMARKS") != null) {
							entity.setFieldDesc(map.get("REMARKS").toString());
						}

						ColumnMetaVO columnMetaVO = new ColumnMetaVO();

						columnMetaVO.setFieldCode(map.get("COLNAME").toString());
						columnMetaVO.setTblNm(single.getTblNm());
						columnMetaVO.setSrcStmNm(single.getSrcStmNm());

						for (ColumnMeta col : list2) {
							if (col.getFieldCode().equals(entity.getFieldCode())
									&& col.getTblNm().equals(entity.getTblNm())
									&& col.getSrcStmNm().equals(entity.getSrcStmNm())) {
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