package com.tlcb.bdp.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calanger.common.dao.OrderBy;
import com.tlcb.bdp.admin.exception.SystemException;
import com.tlcb.bdp.admin.util.Common;
import com.tlcb.bdp.admin.util.PageView;
import com.tlcb.bdp.model.ColumnMeta;
import com.tlcb.bdp.model.DbInfo;
import com.tlcb.bdp.model.TableMeta;
import com.tlcb.bdp.service.ColumnMetaService;
import com.tlcb.bdp.service.DbInfoService;
import com.tlcb.bdp.service.TableMetaService;
import com.tlcb.bdp.service.extension.ColumnMetaExtService;
import com.tlcb.bdp.vo.ColumnMetaVO;

@Controller
@RequestMapping("/colInfo/")
public class ColInfoController extends BaseController {


	@RequestMapping("list")
	public String listUI(Model model, String id) {
		
	/*	ResourcesVO vo = new ResourcesVO();
		vo.setParentId(id);

		List<Resources> res = resourcesItf.bdpButtom(vo,"level",true);*/
		
/*		model.addAttribute("res", res);*/
	/*	String colTbId = getPara("colTbId");
		model.addAttribute("colTbId", colTbId);*/
		
		String tbName = getPara("tbName");
		String srcStmNm = getPara("srcStmNm");
		model.addAttribute("tbName", tbName);
		model.addAttribute("srcStmNm", srcStmNm);
		return "/system/colInfo/list";

	}

	@RequestMapping("findByPage")
	@ResponseBody
	public PageView findByPage(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "1") String pageNow,
			@RequestParam(defaultValue = "10") String pageSize, String column,
			String sort) throws Exception {

		List<ColumnMeta> list = new ArrayList<ColumnMeta>();

		// 查询设置
		String colTbId = getPara("colTbId");
		String tbName = getPara("tbName");
		String srcStmNm = getPara("srcStmNm");

		ColumnMetaVO condition = new ColumnMetaVO();
		
		if (Common.isNotEmpty(colTbId)) {
			condition.setTbId(Integer.valueOf(colTbId));
		}
		
		if(Common.isNotEmpty(tbName)){
			condition.setTblNm(tbName);
		}
		
		if(Common.isNotEmpty(srcStmNm)){
			condition.setSrcStmNm(srcStmNm);
		}
		
		//排序
		OrderBy orderBy = new OrderBy();
		orderBy.add("FLD_SEQ_NUM", true);
	
		if(Common.isNotEmpty(tbName)){
		list = columnMetaService.find(condition, orderBy);
		}
		PageView pageView = new PageView(Integer.valueOf(pageNow));
		pageView.setRecords(list);

		return pageView;
	}


	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		return "/system/colInfo/edit";
	}
	
	@RequestMapping("editCol")
	public String editCol(HttpServletRequest request,
			HttpServletResponse response, Integer id,Model model) throws Exception {
		ColumnMeta columnMeta = columnMetaService.get(id);
		model.addAttribute("columnMeta", columnMeta);
		return "/system/colInfo/edit";
	}
	
	@RequestMapping("saveSingleObj")
	@ResponseBody
	public String saveSingleObj(String id ,String tableId,String isAmount,String isCode,String isBucketFlagObj,String isPartionFlagObj,String isEnableFlagObj){
		ColumnMeta single = new ColumnMeta();
		if(StringUtils.isNotEmpty(isAmount))
		single.setIsAmount(isAmount);
		if(StringUtils.isNotEmpty(isCode))
		single.setIsCode(isCode);
		if(StringUtils.isNotEmpty(isBucketFlagObj))
			single.setBucketKeyFlag(Integer.valueOf(isBucketFlagObj));
		if(StringUtils.isNotEmpty(isPartionFlagObj))
			single.setPartitionKeyFlag(Integer.valueOf(isPartionFlagObj));
		if(StringUtils.isNotEmpty(isEnableFlagObj)){
			single.setIsEnable(isEnableFlagObj);
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(tableId)){
			List<TableMeta> list = this.tableMetaService.findByIdList(Arrays.asList(Integer.valueOf(tableId)));
			if(list.size()>0){
				TableMeta   tableMeta= 	list.get(0);
				if("0".equals(tableMeta.getBucketType()) || null==tableMeta.getBucketType()){//不分桶
					//1:流水   2:拉链
					if(StringUtils.isNotBlank(isBucketFlagObj)&"1".equals(isBucketFlagObj)){
						return "not_allow_bucket";
					}
					
				}
				
				if("0".equals(tableMeta.getPartitionType())|| null==tableMeta.getPartitionType()){
					if(org.apache.commons.lang3.StringUtils.isNotBlank(isPartionFlagObj)&"1".equals(isPartionFlagObj)){
						return "not_allow_partition";
					}
					
				}
			}
		}
		
		
		if(StringUtils.isNotEmpty(isAmount)||StringUtils.isNotEmpty(isCode)||StringUtils.isNotEmpty(isBucketFlagObj)||StringUtils.isNotEmpty(isPartionFlagObj)||StringUtils.isNotEmpty(isEnableFlagObj)){
			
			columnMetaService.update(single, Integer.valueOf(id));
		}

		return "success";
	}
	
	
	@RequestMapping("editColToSave")
	@ResponseBody
	public String editColToSave(HttpServletRequest request,
			HttpServletResponse response,ColumnMeta columnMeta,Model model) throws Exception {
		ColumnMeta single = new ColumnMeta();
		single.setBucketKeyFlag(columnMeta.getBucketKeyFlag());
		single.setPartitionKeyFlag(columnMeta.getPartitionKeyFlag());
		single.setIsNull(columnMeta.getIsNull());
		single.setFieldDesc(columnMeta.getFieldDesc());
		single.setIsAmount(columnMeta.getIsAmount());
		single.setIsCode(columnMeta.getIsCode());
		columnMetaService.update(single, columnMeta.getId());
		
		return "success";
	}

	@RequestMapping(value = "/addEntity")
	@ResponseBody
	public String addEntity(String txtGroupsSelect, String userName,
			String userId) {
		try {

			DbInfo entity = new DbInfo();
			dbInfoService.insert(entity);

			if (!Common.isEmpty(txtGroupsSelect)) {
				String[] txt = txtGroupsSelect.split(",");
				for (String roleId : txt) {
					DbInfo single = new DbInfo();
					dbInfoService.insert(single);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("添加账号异常");
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

	@ResponseBody
	@RequestMapping("deleteEntity")
	public String deleteEntity(String[] ids) throws Exception {
		for (String id : ids) {
			dbInfoService.remove(Integer.valueOf(id));
		}
		return "success";
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
		}*/

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

	/**
	 * 验证账号是否存在
	 * 
	 * @author numberONe date：2014-2-19
	 * @param name
	 * @return
	 */
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist(String colId) {
		DbInfo single = dbInfoService.get(Integer.valueOf(colId));
		if (single == null) {
			return true;
		} else {
			return false;
		}
	}

	@ResponseBody
	@RequestMapping("creatDLL")
	public String creatDLL(Integer[] ids,HttpServletResponse response) throws Exception {
		List<Integer> idList = new ArrayList<Integer>();
		for (Integer id : ids) {
			idList.add(id);
		}
		OrderBy orderBy = new OrderBy();
		orderBy.add("ID", true);
		List<ColumnMeta> res = columnMetaService.findByIdList(idList,orderBy);
		ColumnMeta firset = res.get(0);
		
		String sql = "存量外表,增量外表,orc表已生成;位于目录C:\\Users\\Achilles\\Desktop\\external\\下";
		
		//TableInfo tableInfo = tableInfoService.get(firset.getColTbId());
		TableMeta tableMeta = tableMetaService.get(firset.getTbId());
//		DbInfo dbInfo = dbInfoService.get(tableMeta.getTbDbId());
		createDDLExistExternalTable(tableMeta,res,tableMeta.getSchemaCode());
		createDDLIncExternalTable(tableMeta,res,tableMeta.getSchemaCode());
		createDDLORCTable(tableMeta, res, tableMeta.getSchemaCode());
		response.setContentType("text/html;charset=UTF-8");
		
		return sql;
		
	}
	
	@RequestMapping(value="creatSP",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String creatSP(Integer[] ids,HttpServletResponse response) throws Exception {
		List<Integer> idList = new ArrayList<Integer>();
		for (Integer id : ids) {
			idList.add(id);
		}
		OrderBy orderBy = new OrderBy();
		orderBy.add("ID", true);
		List<ColumnMeta> res = columnMetaService.findByIdList(idList,orderBy);
		ColumnMeta firset = res.get(0);
		
		String sql = "生成拉链表,流水表,DQ表,增量数据;位于目录C:\\Users\\Achilles\\Desktop\\SP\\下";
		
		TableMeta tableMeta = tableMetaService.get(firset.getTbId());
		
		writeToLocal("C:\\Users\\Achilles\\Desktop\\SP\\"+tableMeta.getSrcStmNm()+".SP_DQ_COUNT_"+tableMeta.getSrcStmNm()+"_"+tableMeta.getTblCode()+".sql", generateDQIncPro(tableMeta.getSrcStmNm(),tableMeta.getTblCode(),res));
		writeToLocal("C:\\Users\\Achilles\\Desktop\\SP\\"+tableMeta.getSrcStmNm()+".SP_CNT_"+tableMeta.getSrcStmNm()+"_"+tableMeta.getTblCode()+".sql",generateIncSumVerifyPro(tableMeta.getSrcStmNm(),tableMeta.getTblCode(), res));
		writeToLocal("C:\\Users\\Achilles\\Desktop\\SP\\"+tableMeta.getSrcStmNm()+".SP_INC_FLOW_"+tableMeta.getSrcStmNm()+"_"+tableMeta.getTblCode()+".sql",generateLiushuiIncPro(tableMeta.getSrcStmNm(),tableMeta.getTblCode(), res));
		writeToLocal("C:\\Users\\Achilles\\Desktop\\SP\\"+tableMeta.getSrcStmNm()+".SP_INC_ZIPPER_"+tableMeta.getSrcStmNm()+"_"+tableMeta.getTblCode()+".sql",generateZipperIncPro(tableMeta.getSrcStmNm(),tableMeta.getTblCode(),res));
		
		
		response.setContentType("text/html;charset=UTF-8");
		
		return sql;
		
	}
	
	
	/****
	 * 
	 * 生成DQ统计存储过程
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	private String  generateDQIncPro(String schema,String tableName,List<ColumnMeta> columnList) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE OR REPLACE PROCEDURE ");
		sb.append(schema);//TODO SCHEMA DCDP
		sb.append(".SP_DQ_COUNT_").append(schema).append("_");
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
		
		sb.append("v_proc_name :=");
		sb.append("'").append(schema);
		sb.append(".SP_DQ_COUNT_").append(schema).append("_");
		sb.append(tableName+"';");sb.append("\r\n");
		sb.append("v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='DQ_COUNT';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("delete from DQ.PROC_LOG where proc_name = v_proc_name and proc_act_dt = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
		sb.append("delete from DQ.CHK_CNT_MSR where CHK_DT = to_char(current_date(),'yyyy-MM-dd') AND BSN_DT = v_acctdt AND SYS_NM = ");
		sb.append("'"+schema+"'").append(" AND TAB_NM = ").append("'"+tableName+"';");sb.append("\r\n");sb.append("\r\n");
		
		//码值List
		List<String> codevaluelist= new ArrayList<String>();
		List <String> Ballist = new ArrayList<String>();
		for (ColumnMeta columnMeta : columnList) {
			if("1".equals(columnMeta.getIsCode())){
				codevaluelist.add(columnMeta.getFieldCode());
			}
			if("1".equals(columnMeta.getIsAmount())){
				Ballist.add(columnMeta.getFieldCode());
			}
		}
		
		for(String attribute:codevaluelist){
			
			sb.append("INSERT INTO dq.chk_cnt_msr SELECT to_char(current_date(),'yyyy-MM-dd'),v_acctdt,'ODS',");
			sb.append("'"+schema+"',").append("'"+tableName+"',").append("'"+attribute+"',").append(attribute+",").append("'"+"COUNT"+"',");
			sb.append("COUNT(*),current_time(),NULL FROM "+schema+"."+tableName+"_HS WHERE begindt<=v_acctdt AND enddt>v_acctdt AND del_f!=2 GROUP BY  ");
			sb.append(attribute).append(";");sb.append("\r\n");sb.append("\r\n");
			
			for(String strbal:Ballist){
				sb.append("INSERT INTO dq.chk_cnt_msr SELECT  to_char(current_date(),'yyyy-MM-dd'),v_acctdt,");
				sb.append("'ODS',").append("'"+schema+"',").append("'"+tableName+"',").append("'"+attribute+"',").append(attribute+",");
				sb.append("'"+strbal+"',");
				sb.append("SUM(abs("+strbal+")),current_time(),NULL FROM "+schema+"."+tableName+"_HS WHERE begindt<=v_acctdt AND enddt>v_acctdt  AND del_f!=2 ");
				sb.append("GROUP BY "+attribute+";");sb.append("\r\n");sb.append("\r\n");
			}
		}
		//----COUNT(*) FULL
		sb.append("INSERT INTO dq.chk_cnt_msr SELECT to_char(current_date(),'yyyy-MM-dd'), v_acctdt ,'ODS',");
		sb.append("'"+schema+"','"+tableName+"','FULL','FULL',");
		sb.append("'COUNT',COUNT(*),current_time(),NULL FROM "+schema+"."+tableName+"_HS WHERE begindt<=v_acctdt AND enddt>v_acctdt AND del_f!=2;");
		sb.append("\r\n");sb.append("\r\n");
		//----SUM(abs(AA10AMR1)) FULL
		//----SUM(abs(AA10BALY)) FULL
		for(String balfull:Ballist){
			sb.append("INSERT INTO dq.chk_cnt_msr SELECT to_char(current_date(),'yyyy-MM-dd'),v_acctdt,'ODS','"+schema+"','"+tableName+"','FULL','FULL',");
			sb.append("'"+balfull+"',").append("SUM(abs("+balfull+")),");
			sb.append("current_time(),NULL FROM "+schema+"."+tableName+"_HS WHERE begindt<=v_acctdt AND enddt>v_acctdt AND del_f!=2;");
			sb.append("\r\n");sb.append("\r\n");
		}
		
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");sb.append("\r\n");
		sb.append("begin DQ.SP_REC_LOG (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg) end;");
		sb.append("\r\n");sb.append("\r\n");
		sb.append("v_proc_exe_flg :=1;");sb.append("\r\n");sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
		sb.append("EXCEPTION ");sb.append("\r\n");
		sb.append("WHEN OTHERS THEN");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm := SQLERRM;");sb.append("\r\n");
		sb.append("begin DQ.SP_REC_LOG (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg) end;");
		sb.append("\r\n");sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");
		sb.append("\r\n");sb.append("\r\n");
		sb.append("ROLLBACK;");
		sb.append("\r\n");
		sb.append("END;");
		sb.append("\r\n");
		sb.append("/");
		
		return sb.toString();
	}
	
	
	/***
	 * 
	 * 拉链表存储过程
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	private static String  generateZipperIncPro(String schema,String tableName,List<ColumnMeta> columnList) {
		
		List<String> keyColumnList = new ArrayList<>();
		  for (ColumnMeta sourceColumn : columnList) {
				if(sourceColumn.getPrimaryKeyFlag()!=null &&  sourceColumn.getPrimaryKeyFlag()>0){
					keyColumnList.add(sourceColumn.getFieldCode());
				}	
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
		sb.append(schema);//TODO SCHEMA DCDP
		sb.append(".SP_INC_ZIPPER_"+schema+"_");
		sb.append(tableName);sb.append("\r\n");
		sb.append("(v_date IN string) is ");sb.append("\r\n");
		
		sb.append("DECLARE ");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_acctdt string;");sb.append("\r\n");
		sb.append("v_rowcnt int;");sb.append("\r\n");
		sb.append("row_nm int;");sb.append("\r\n");
		sb.append("balance_sum decimal(31,6);");sb.append("\r\n");
		sb.append("v_proc_stepnum int;");sb.append("\r\n");
		sb.append("v_proc_stepdesc string;");sb.append("\r\n");
		sb.append("v_proc_name string;");sb.append("\r\n");
		sb.append("v_proc_step_flg int :=0;");sb.append("\r\n");
		sb.append("v_proc_exe_flg int :=0;");sb.append("\r\n");
		sb.append("v_sqlcode int;");sb.append("\r\n");
		sb.append("v_sqlerrm string;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("BEGIN ");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("select cast(v_date as date) into v_acctdt from system.dual;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_name :=");
		sb.append("'"+schema+".SP_INC_ZIPPER_"+schema+"_").append(tableName+"';");sb.append("\r\n");
		sb.append(" v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='delete';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");
		

		sb.append("delete from DQ.PROC_LOG where proc_name = v_proc_name and proc_act_dt = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
		
        sb.append("select count(1) into v_rowcnt from ");
		sb.append(""+schema+".").append(tableName).append("_HS_ZIPPER").append(" where begindt = v_acctdt");sb.append("\r\n");
		sb.append("if v_rowcnt <> 0 then ");sb.append("\r\n");
		sb.append("delete from ");
		sb.append(""+schema+".").append(tableName).append("_HS_ZIPPER").append(" where enddt = v_acctdt;");sb.append("\r\n");
		sb.append("end if");sb.append("\r\n");
		sb.append("select count(1) into v_rowcnt from ");
		sb.append(""+schema+".").append(tableName).append("_HS_ZIPPER").append(" where begindt = v_acctdt");sb.append("\r\n");
		sb.append("if v_rowcnt <> 0 then ");sb.append("\r\n");
		sb.append("update ");
		sb.append(""+schema+".").append(tableName).append("_HS_ZIPPER").append(" set enddt = '2999-12-31' where enddt = v_acctdt");sb.append("\r\n");
		sb.append("end if");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");
		sb.append("begin DQ.SP_REC_LOG (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg) end;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_stepnum := v_proc_stepnum+1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='insert';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("insert into table ");
		sb.append(""+schema+".").append(tableName).append("_HS_ZIPPER ");

		
		sb.append("select v_acctdt as begindt, '2999-12-31' as enddt,");
		for (ColumnMeta sourceColumn : columnList) {
			sb.append(sourceColumn.getFieldCode()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		
		sb.append(" from ");
		sb.append(""+schema+".").append(tableName).append("_EXT");
		sb.append(" where inc_date=v_date;");
		sb.append("\r\n");sb.append("\r\n");

		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");sb.append("\r\n");
		sb.append("begin DQ.SP_REC_LOG (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg) end;");sb.append("\r\n");sb.append("\r\n");
		sb.append("\r\n");
		
		sb.append("v_proc_stepnum := v_proc_stepnum+1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='merge';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("MERGE INTO ");
		sb.append(""+schema+".").append(tableName).append("_HS_ZIPPER").append(" m USING ").append("(");
		sb.append("SELECT a.begindt as begindt,");
		for (String s : keyColumnList) {
			sb.append("a.");
			sb.append(s);
			sb.append(" as ").append(s).append(",");
		}
			int len = sb.toString().lastIndexOf(",");//delete ，
			if(len!=-1){
				sb.deleteCharAt(len);
			}
			
		sb.append(" from ");
		sb.append(" "+schema+".").append(tableName).append("_HS_ZIPPER a").append(" INNER JOIN (SELECT * FROM ");
		sb.append(""+schema+".").append(tableName).append("_EXT ").append("WHERE inc_date = v_date) b ON ");
		
		for (String s : keyColumnList) {
			sb.append("a.");
			sb.append(s);
			sb.append(" = b.").append(s).append(" and ");
		}
		
		int len2 = sb.toString().lastIndexOf("and");//delete ，
		if(len2 != -1){
			sb.delete(len2, sb.length());
		}
		
		sb.append(" WHERE a.begindt <=DATE_SUB(v_acctdt,1)  AND a.enddt >DATE_SUB(v_acctdt,1)) c ");
		sb.append("ON (m.begindt=c.begindt and ");

		for (String s : keyColumnList) {
			sb.append("m.");
			sb.append(s);
			sb.append(" = c.").append(s).append(" and ");
		}
		
		int len3 = sb.toString().lastIndexOf("and");//delete ，
		if(len3 != -1){
			sb.delete(len3, sb.length());
		}
		
		sb.append(")");
		sb.append(" WHEN MATCHED THEN UPDATE SET enddt = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
		
		
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");sb.append("\r\n");
		sb.append("begin DQ.SP_REC_LOG (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg) end;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_exe_flg :=1;");sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("EXCEPTION ");sb.append("\r\n");
		sb.append("WHEN OTHERS THEN ");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");
		sb.append("insert into table DQ.PROC_LOG (proc_name,proc_act_dt,proc_stepnum,proc_stepdesc,proc_sqlcode,proc_sqlerrm,proc_step_flg,cur_ts) "
				+ "values (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg,systimestamp);");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("ROLLBACK;");
		sb.append("\r\n");
		sb.append("END;");
		sb.append("\r\n");
		sb.append("/");
		
		return sb.toString();
	}
	
	/****
	 * 
	 * 增量数据文件记录条数校验存储过程
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	private  String  generateIncSumVerifyPro(String schema,String tableName,List<ColumnMeta> columnList) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
		sb.append(schema);//TODO SCHEMA DCDP
		sb.append(".SP_INC_"+schema+"_");
		sb.append(tableName);sb.append("\r\n");
		sb.append("(v_date IN string) is ");sb.append("\r\n");
		sb.append("DECLARE ");sb.append("\r\n");sb.append("\r\n");
		sb.append("ext_count_num int;");sb.append("\r\n");
		sb.append("v_sql string;");sb.append("\r\n");
		sb.append("v_table string;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("BEGIN");sb.append("\r\n");
		sb.append("v_table := ");
		sb.append("'"+schema+".").append(tableName+"_EXT';");sb.append("\r\n");sb.append("\r\n");
		sb.append("execute immediate ' begin select count(*) into :ext_count_num from ' || v_table || ' where inc_date = ' || v_date || ' end' using out ext_count_num;");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("DBMS_OUTPUT.PUT_LINE(ext_count_num);");sb.append("\r\n");
		sb.append("END;");
		sb.append("\r\n");
		sb.append("/");
		return sb.toString();
	}
	
	//流水表增量
private static String  generateLiushuiIncPro(String schema,String tableName,List<ColumnMeta> columnList) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("!set plsqlUseSlash true"+"\r\n\r\n"+"CREATE OR REPLACE PROCEDURE ");
		sb.append(schema);//TODO SCHEMA DCDP
		sb.append(".SP_INC_FLOW_"+schema+"_");
		sb.append(tableName);sb.append("\r\n");
		sb.append("(v_date IN string) is ");sb.append("\r\n");
		sb.append("DECLARE ");sb.append("\r\n");sb.append("\r\n");
		sb.append("v_acctdt string;");sb.append("\r\n");
		sb.append("v_rowcnt int;");sb.append("\r\n");
		sb.append("row_nm int;");sb.append("\r\n");
		sb.append("balance_sum decimal(31,6);");sb.append("\r\n");
		sb.append("v_proc_stepnum int;");sb.append("\r\n");
		sb.append("v_proc_stepdesc string;");sb.append("\r\n");
		sb.append("v_proc_name string;");sb.append("\r\n");
		sb.append("v_proc_step_flg int :=0;");sb.append("\r\n");
		sb.append("v_proc_exe_flg int :=0;");sb.append("\r\n");
		sb.append("v_sqlcode int;");sb.append("\r\n");
		sb.append("v_sqlerrm string; ");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("BEGIN ");sb.append("\r\n");sb.append("\r\n");
		sb.append("select cast(v_date as date) into v_acctdt from system.dual;");sb.append("\r\n");sb.append("\r\n");
		sb.append("v_proc_name :=");
		sb.append("'"+schema+".SP_INC_FLOW_"+schema+"_").append(tableName+"';");sb.append("\r\n");
		sb.append(" v_proc_stepnum :=1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='delete';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");sb.append("\r\n");
		

		sb.append("delete from DQ.PROC_LOG where proc_name = v_proc_name and proc_act_dt = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
        sb.append("delete from ");
		sb.append(""+schema+".").append(tableName).append("_HS").append(" where LAST_ETL_ACG_DT = v_acctdt;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");sb.append("\r\n");
		sb.append("begin DQ.SP_REC_LOG (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg) end;");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("v_proc_stepnum := v_proc_stepnum+1;");sb.append("\r\n");
		sb.append("v_proc_stepdesc :='insert';");sb.append("\r\n");
		sb.append("v_proc_step_flg :=0;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("insert into table ");
		sb.append(""+schema+".").append(tableName).append("_HS").append(" (");
		for (ColumnMeta sourceColumn : columnList) {
			sb.append(sourceColumn.getFieldCode()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(" )");
		sb.append("\r\n");
		sb.append("select ");
		for (ColumnMeta sourceColumn : columnList) {
			sb.append(sourceColumn.getFieldCode()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("\r\n");
		sb.append("from ");
		sb.append(""+schema+".").append(tableName).append("_EXT");
		sb.append(" where inc_date=v_date;");sb.append("\r\n");sb.append("\r\n");
		
		sb.append("v_proc_step_flg :=1;");sb.append("\r\n");
		sb.append("begin DQ.SP_REC_LOG (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg) end;");sb.append("\r\n");sb.append("\r\n");
		sb.append("v_proc_exe_flg :=1;");sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
		sb.append("EXCEPTION ");sb.append("\r\n");
		sb.append("WHEN OTHERS THEN");sb.append("\r\n");
		sb.append("v_sqlcode := SQLCODE;");sb.append("\r\n");
		sb.append("v_sqlerrm :=SQLERRM;");sb.append("\r\n");
		sb.append("begin DQ.SP_REC_LOG (v_proc_name,v_acctdt,v_proc_stepnum,v_proc_stepdesc,v_sqlcode,v_sqlerrm,v_proc_step_flg) end;");
		sb.append("\r\n");
		sb.append("put_line(v_proc_exe_flg);");sb.append("\r\n");sb.append("\r\n");
		sb.append("END;");
		sb.append("\r\n");
		sb.append("/");
		return sb.toString();
	}
	
	//存量外表
	public void createDDLExistExternalTable(TableMeta tableMeta ,List<ColumnMeta> res,String schemaName){
		
		StringBuffer sb = new StringBuffer("CREATE EXTERNAL TABLE ");
		sb.append(schemaName.trim()).append(".");
		sb.append(tableMeta.getTblNm()).append("_EXT_HS").append(" ( ");
		
		for (ColumnMeta columnSchemaMeta : res) {
			sb.append(" ").append(columnSchemaMeta.getFieldCode()).append(" ").append(exchangeTypeColumnExternal(columnSchemaMeta)).append(" ,");
		}
		  sb.deleteCharAt(sb.toString().lastIndexOf(","));
		  sb.append(" )");
		  //外表
		  sb.append(" row format delimited FIELDS TERMINATED BY '\\27' ; ");
		
		  writeToLocal("C:\\Users\\Achilles\\Desktop\\external\\"+tableMeta.getTblCode()+"_EXT_HS"+".sql",sb.toString());
	}
	
	//增量外表
	public void createDDLIncExternalTable(TableMeta tableMeta ,List<ColumnMeta> res,String schemaName){
		
		StringBuffer sb = new StringBuffer("CREATE EXTERNAL TABLE ");
		sb.append(schemaName.trim()).append(".");
		sb.append(tableMeta.getTblNm()).append("_EXT").append(" ( ");
		
		for (ColumnMeta columnSchemaMeta : res) {
			sb.append(" ").append(columnSchemaMeta.getFieldCode()).append(" ").append(exchangeTypeColumnExternal(columnSchemaMeta)).append(" ,");
		}
		  sb.deleteCharAt(sb.toString().lastIndexOf(","));
		  sb.append(" )");
		  //外表
		  sb.append(" PARTITIONED BY(INC_DATE string) row format delimited FIELDS TERMINATED BY '\\27';");
		  sb.append(" ALTER TABLE").append(schemaName.trim()).append(".").append(tableMeta.getTblNm()+"_EXT").append(" set serdeproperties('serialization.encoding'='gbk')");
		  writeToLocal("C:\\Users\\Achilles\\Desktop\\external\\"+tableMeta.getTblCode()+"_EXT"+".sql",sb.toString());
	}
	
	//增量外表
		public void createDDLORCTable(TableMeta tableMeta ,List<ColumnMeta> res,String schemaName){
				
			List<String> keyColumnList = new ArrayList<>();
			  for (ColumnMeta columnMeta : res) {
					if(columnMeta.getPrimaryKeyFlag()!=null &&  columnMeta.getPrimaryKeyFlag()>0){
						keyColumnList.add(columnMeta.getFieldCode());
					}	
				}
			  
			  if(keyColumnList.size()==0){
				  keyColumnList.add(res.get(0).getFieldCode());
			  }
			StringBuffer sb = new StringBuffer("CREATE  TABLE ");
			sb.append(schemaName.trim()).append(".");
			sb.append(tableMeta.getTblNm()).append("_HS").append(" ( ");
			
			for (ColumnMeta columnSchemaMeta : res) {
				sb.append(" ").append(columnSchemaMeta.getFieldCode()).append(" ").append(exchangeTypeColumnOrc(columnSchemaMeta)).append(" ,");
			}
			  sb.deleteCharAt(sb.toString().lastIndexOf(","));
			  sb.append(" )");
			  //外表
			  for (ColumnMeta columnSchemaMeta : res) {
				  	if(null!=columnSchemaMeta.getPrimaryKeyFlag()&&columnSchemaMeta.getPrimaryKeyFlag() ==1)
					sb.append(" clustered by (").append(keyColumnList.get(keyColumnList.size()-1)).append(") into 53 buckets stored as orc tblproperties ('transactional' = 'true');");
			  }
			  writeToLocal("C:\\Users\\Achilles\\Desktop\\external\\"+tableMeta.getTblCode()+"_ORC_HS"+".sql",sb.toString());
		}
	
	public  void writeToLocal(String fileName,String content){
		
		
		
		File file = new File(fileName);
		if(file.exists()){
			
			file.delete();
		}
		
		try {
			OutputStream out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static String exchangeTypeColumnExternal(ColumnMeta columnSchemaMeta ) {
		String typename = columnSchemaMeta.getDataTp();
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
			case "BIGINTEGER":
				typename = "long";
				
			default:
				typename= "string";
				
			}
			
			if("decimal".equals(typename)){
				typename = typename +"("+ columnSchemaMeta.getFldLength()+",0)";
			}
			
			return typename;
		}
		

		private static String exchangeTypeColumnOrc(ColumnMeta columnSchemaMeta ) {
		String typename = columnSchemaMeta.getDataTp();
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
				typename = "date";
				break;
			case "BIGINTEGER":
				typename = "long";
				
			default:
				typename= "string";
				
			}
			
			if("decimal".equals(typename)){
				typename = typename +"("+ columnSchemaMeta.getFldLength()+",0)";
			}
			
			return typename;
		}

	
		@Autowired
		private DbInfoService dbInfoService;
		
		@Resource
		private ColumnMetaExtService columnMetaExtService;
		
		@Autowired
		private TableMetaService tableMetaService;
		
		@Autowired
		private ColumnMetaService columnMetaService;

		/*@Autowired
		private ResourcesItf resourcesItf;*/

}