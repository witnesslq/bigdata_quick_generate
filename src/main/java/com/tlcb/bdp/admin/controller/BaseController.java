package com.tlcb.bdp.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
//import com.tlcb.dcdp.model.UserInfo;
import com.tlcb.bdp.admin.util.Common;
import com.tlcb.bdp.admin.util.FormMap;

/**
 * 基础controller,其他controller继承此controller来获得writeJson和ActionSupport的功能
 * 
 * @author 
 * 
 */
public class BaseController {
	
	
	protected Integer page = 1;
	protected Integer rows = 20;
	protected Integer startRow;
	
	private static Logger log = LoggerFactory.getLogger(BaseController.class);
	
	
	protected ServletContext servletContext;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected HttpSession httpSession;

	protected Map<String, Object> session;
	
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.httpSession = request.getSession();
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * @param response
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(HttpServletResponse response,Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			exceptionLog(log, Thread.currentThread().getStackTrace()[1].getMethodName(), e, "此处用户不记录");
		}
	}
	
	/**
	 * Exception log
	 * 
	 * 
	 */
	protected void exceptionLog(Logger log, String method, Exception e, String userName){
		log.error("方法名："+method+" 用户:"+userName);
		log.error("信息:"+e.getMessage());
		log.error("本地化信息："+e.getLocalizedMessage());
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw,true));
		log.error("错误原因："+sw.toString());
	}
	
	
	/**
	 * 获取传递的所有参数,
	 * 反射实例化对象，再设置属性值
	 * 通过泛型回传对象.
	 * <br/>
	 *<b>author：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspnumberONe</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
	 *<b>version：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp3.0v</b>
	 * @return Class<T>
	 * @throws Exception
	 */
	public <T> T getFormMap(Class<T> clazz){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			@SuppressWarnings("unchecked")
			FormMap<String, Object> map = (FormMap<String, Object>) t;
			String order = "",sort="";
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if(nms.endsWith("[]")){
					String[] as = request.getParameterValues(nms);
					if(as!=null&&as.length!=0&&as.toString()!="[]"){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(nms.toUpperCase().indexOf(mname)+1);
							map.put( nms,as);
						}
					}
				}else{
					String as = request.getParameter(nms);
					if(!Common.isEmpty(as)){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(mname.length()+1);
							map.put( nms, as);
						}
						if(nms.toLowerCase().equals("column"))order = as;
						if(nms.toLowerCase().equals("sort"))sort = as;
					}
				}
			}
			if(!Common.isEmpty(order) && !Common.isEmpty(sort))
				map.put("orderby", " order by " + order + " " + sort);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return  t;
	}
	
	/**
	 * 获取页面传递的某一个参数值,
	 * <br/>
	 *<b>author：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspnumberONe</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
	 *<b>version：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp3.0v</b>
	 */
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	
	/**
	 * 获取页面传递的某一个数组值,
	 * <br/>
	 *<b>author：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspnumberONe</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
	 *<b>version：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp3.0v</b>
	 * @return Class<T>
	 * @throws Exception
	 */
	public String[] getParaValues(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameterValues(key);
	}
	
	
//	/**
//	 * 获取返回某一页面的按扭组,
//	 * <br/>
//	 *<b>author：</b><br/> 
//	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspnumberONe</b><br/> 
//	 *<b>date：</b><br/> 
//	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
//	 *<b>mod by：</b><br/> 
//	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspEkko</b><br/> 
//	 *<b>date：</b><br/> 
//	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-09-07</b><br/> 
//	 *<b>version：</b><br/> 
//	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp3.0v</b>
//	 * @return Class<T>
//	 * @throws Exception
//	 */
//	public List<Resources> findByRes(){
//		// 资源ID
//		String id = getPara("id");
//		// 
//		// 获取request user id
////		int userId = userFormMap.getInt("id");
//		
//		//根据用户ID，角色ID，父级ID。还有操作用户ID。获取资源list。现在简化处理。全部列出。
//		//使用在多处资源获取请求
//		
//		List<Resources> rse = null;
//		
//		return rse;
//	}
//	
//	
	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getParameterMap(HttpServletRequest request) {
	    // 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map<String,Object> returnMap = new HashMap<String,Object>();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    return returnMap;
	}
	
	
	public void addSessionWebUser(String name, Object webuser) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();
		request.getSession().setAttribute(name, webuser);

	}

	public Object getWebUserAttribute(String name) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();
		Object obj = null;
		if (!name.trim().equals("")) {
			obj = request.getSession().getAttribute(name);
		}

		return obj;
	}
	
	public void removeWebUserAttribute(String name) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();
		if (!name.trim().equals("")) {
			request.getSession().removeAttribute(name);
		}
	}
    public void writeToLocal(String folder ,String fileName,String content){
    	
    	File file1 = new File(folder);
    	if(!file1.exists()){
    		file1.mkdirs();
    	}
    	
		File file = new File(folder+"/"+fileName);
		
		if(file.exists()){
			file.delete();
		}else{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			OutputStream out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    
   
}
