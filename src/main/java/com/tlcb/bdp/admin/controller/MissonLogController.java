package com.tlcb.bdp.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calanger.common.dao.OrderBy;
import com.tlcb.bdp.admin.util.Common;
import com.tlcb.bdp.admin.util.PageView;
import com.tlcb.bdp.model.MissionUserLog;
import com.tlcb.bdp.service.MissionUserLogService;
import com.tlcb.bdp.vo.MissionUserLogVO;

@Controller
@RequestMapping("/missionlog/")
public class MissonLogController extends BaseController{
	
	@Resource
	private MissionUserLogService missionUserLogService;
	
	
	@RequestMapping("fenyelist")
	public String listUI(Model model, String id) throws Exception {
		return "/system/missionlog/list";
	}
	
	@RequestMapping("findByPage")
	@ResponseBody
	public PageView findByPage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "1") String pageNow, @RequestParam(defaultValue = "10") String pageSize,
			String column, String sort) throws Exception {

		List<MissionUserLog> list = new ArrayList<MissionUserLog>();

		// 查询条件 待定???
		MissionUserLogVO condition = new MissionUserLogVO();

		OrderBy orderBy = new OrderBy();
		if (Common.isNotEmpty(sort) && Common.isNotEmpty(column)) {
			String colName = "";
			if (column.equalsIgnoreCase("id")) {
				colName = "ID";
			}
			boolean order = true;
			if (sort.equalsIgnoreCase("DESC")) {
				order = false;
			}
			orderBy.add(colName, order);
		} else {
			orderBy.add("ID", true);
		}

	/*	User userSession = (User) getWebUserAttribute("userSession");
		if (!"000001".equals(userSession.getUserId())) {
			// 表示获取全部信息
			condition.setUserId(userSession.getUserId());// 当前用户的session userid
		}*/
		list = missionUserLogService.find(condition, orderBy, Integer.valueOf(pageSize), Integer.valueOf(pageNow));
		PageView pageView = null;
		pageView = new PageView(Integer.valueOf(pageNow));
		pageView.setRecords(list);
		int count = missionUserLogService.count(condition);
		pageView.setRowCount(count);

		return pageView;
	}
	
}
