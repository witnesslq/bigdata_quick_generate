package com.tlcb.bdp.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlcb.bdp.admin.util.TreeObject;
import com.tlcb.bdp.admin.util.TreeUtil;

@Controller
public class IndexController extends BaseController{

	/*@Autowired
	ResourcesItf resourcesItf;*/
	
	/*@Autowired
	UserItf userItf;*/
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,HttpServletResponse response, Model model,String roleId,String userIdMD5){

		
	/*	List<Resources> resList = new ArrayList<Resources>();
		
		User userSessionPre = (User) getWebUserAttribute("userSession");
		User userSession = new User();
		
		if(Common.isNotEmpty(userIdMD5)){
			
			//获取用户登入状态
			UserLogin userLogin = userItf.getUserLogin(userIdMD5);
			//测试获取用户列表接口
			try{
			
				System.out.println("userId:"+userLogin.getUserId());
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//本系统登入状态写入---服务于登入拦截
			userSession.setUserId(userLogin.getUserId());
			List<User> userList = userItf.getUserList(userIdMD5+"");
			if(userList.size()>0){
				for (User user : userList) {
					if(user.getUserId().equals(userSession.getUserId())){
						userSession.setUserId(user.getUserId());
						userSession.setUserNm(user.getUserNm());
					}
				}
			}

			addSessionWebUser("userSession",userSession);
			addSessionWebUser("_current_roleId",roleId);
			addSessionWebUser("_current_userIdMD5", userIdMD5);
			//获取权限资源列表
			resList = resourcesItf.bdp("1",roleId,userLogin.getUserId());
			
		}
		else if(userSessionPre!=null){
			
			userIdMD5 = (String) getWebUserAttribute("_current_userIdMD5");
			roleId = (String) getWebUserAttribute("_current_roleId");
			
			userSession.setUserId(userSessionPre.getUserId());
			
			//获取用户登入状态
			UserLogin userLogin = userItf.getUserLogin(userIdMD5);
			//测试获取用户列表接口
			try{
				List<User> userList = userItf.getUserList(userIdMD5);
				System.out.println(userList.get(userList.size()-1).getUserId());
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//获取权限资源列表
			resList = resourcesItf.bdp("1",roleId,userLogin.getUserId());
//			List<Resources> resList = resourcesItf.bdp("1","1","0000001");
		}
		else{
			return "redirect:/login_redirect.do";
		}
		*/
		
		
		List<TreeObject> list = new ArrayList<TreeObject>();
	/*	for (Resources single : resList) {
			TreeObject ts = new TreeObject();
			ts.setResId(single.getResId());
			ts.setResNm(single.getResNm());
			ts.setParentId(single.getParentId());
			ts.setResurl(single.getResurl());
			list.add(ts);
		}*/
		
		for(int i=0;i<7;i++){
			TreeObject ts = new TreeObject();
			if(i==0){
				ts.setResId("0");
				ts.setResNm("测试");
				ts.setParentId("000000");
				ts.setResurl("");
			}else if(i==1 || i==2){
				ts.setResId(i+"");
				ts.setResNm("一级菜单"+i);
				ts.setParentId("0");
				ts.setResurl("/generate/sourceTable.do");
			}else if(i==3 ){//元表信息
				ts.setResId(i+"");
				ts.setResNm("数据库信息"+(i-2));
				ts.setParentId("1");
				ts.setResurl("/dbInfo/list.do");
			}else if(i == 4){//元表信息
				ts.setResId(i+"");
				ts.setResNm("表信息"+(i-2));
				ts.setParentId("1");
				ts.setResurl("/tableInfo/list.do");
			}else if(i == 5){//元字段信息
				ts.setResId(i+"");
				ts.setResNm("字段"+(i-2));
				ts.setParentId("1");
				ts.setIshide(0);
				ts.setResurl("/colInfo/list.do");
			}else if(i == 6){//信息
				ts.setResId(i+"");
				ts.setResNm("新增表"+(i-2));
				ts.setParentId("3");
				ts.setResurl("/dbInfo/addTableUI.do");
				StringBuffer sbBuffer = new StringBuffer("");
				sbBuffer.append("<button type=\"button\" id=\"addTable\" class=\"btn btn btn-grey marR10\">新增表</button>");
				ts.setDescription(sbBuffer.toString());
			}
			list.add(ts);
		}
		
		TreeUtil treeUtil = new TreeUtil();
		List<TreeObject> ns = treeUtil.getChildTreeObjects(list, "0");

		model.addAttribute("list", ns);
	/*	model.addAttribute("userSession", userSession);*/

		return "index";
	}
	
	@RequestMapping("/logout")
	public String Logout(Model model, String username, String password,
			HttpServletRequest request, HttpServletResponse response){
		
		removeWebUserAttribute("userSession");
		return "redirect:/login_redirect.do";
		
	}
	

}
