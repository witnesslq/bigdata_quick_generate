package com.tlcb.bdp.admin.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tlcb.bdp.service.OperationLogService;


public class ArchivesLogAspect{
	@Autowired
	private OperationLogService operationLogService;
	
	private long startTimeMillis = 0;// 开始时间
	private long endTimeMillis = 0;// 结束时间
	//private User user = null;
	private HttpServletRequest request = null;

	/**
	 * 获取request
	 * 
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}

	/**
	 * @description 方法调用前触发,记录开始时间
	 * @param joinPoint
	 */
	public void before(JoinPoint joinPoint) {
		// 被拦截方法调用之后调用此方法,输出此语句
		request = getHttpServletRequest();
	//	user = (User) request.getSession().getAttribute("userSession");
		startTimeMillis = System.currentTimeMillis();
	}

	/**
	 * 方法调用后触发 记录结束时间
	 * 
	 * @param joinPoint
	 */
	public void after(JoinPoint joinPoint) throws Exception {
		request = getHttpServletRequest();
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = null;

		targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String operationName = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] classes = method.getParameterTypes();
				if (classes != null && classes.length == arguments.length
						&& method.getAnnotation(ArchiveLog.class) != null) {
					operationName = method.getAnnotation(ArchiveLog.class).operationName();
					break;
				}
			}
		}

		endTimeMillis = System.currentTimeMillis();

		// 格式化开始时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = sdf.format(startTimeMillis);
		String endTime = sdf.format(endTimeMillis);
		
		if(!methodName.equals("index")){
		/*	System.out.println(	"操作人:" + user.getUserNm() + ",操作方法:" + methodName + ",操作开始时间:" + startTime + "操作结束时间:" + endTime);
			
			OperationLog operationLog = new OperationLog();
			operationLog.setProcessTime(sdf.parse(startTime));
			operationLog.setUserId(user.getUserId());
			operationLog.setUserName(user.getUserNm());
			operationLog.setProcessDesc(methodName);
			
			operationLogService.insert(operationLog);
			*/
		}
	}

}
