<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="col-md-12">
		<div class="alert alert-warning alert-block">
		<table>
		<tr>
		<td align="center" colspan="2"><h1>泰隆权限开源系统</h1></td>
		</tr>
		<tr>
		<td><div class="topBtn" style="font-size: 15px;">
								关于1.0版本的说明：
								<div style="padding-left: 20px;">
								基础框架：<div style="padding-left: 20px;color: red;">springMVC4.1.4+spring4.x+Mybaits3.2.8+Ajax+html5<br>
								</div>
								<!-- spring4.x的新特性请看：
								<div style="padding-left: 20px;color: blue;">
								http://jinnianshilongnian.iteye.com/blog/1989381
								</div>
								</div>
								<div style="padding-left: 20px;">
									说明：
									<div style="padding-left: 20px;">
									这个版本主要是对原有的numberONe系统更换UI界面,功能上基本一致,
									但此还在开发当中..... 关于以前版本,不再维护,致力于新版本的开发和维护..
									</div> -->
									优化：
									<div style="padding-left: 20px;color: blue;padding-top: 10px;">
									自动化封装生成，service,serviceImpl,dao,daoImpl,Mapper 各数据层，持久层统一自动生成,大大减少代码开发时间.
									</div>
									<div style="padding-left: 20px;color: blue;padding-top: 10px;">
									spring4.x的强类型注解，泛型限定式依赖注入
									</div>
									<!-- 
									<div style="padding-left: 20px;padding-top: 10px;">
									用mapper来代替dao,由mybaits自动管理各事务的操作,大大减少代码开发时间.
									</div>
									<div style="padding-left: 20px;color: red;padding-top: 10px;">
									3.0版本不再使用spring Security3权限安全机制,采用了shiro权限机制,
									为何愿意使用Apache Shiro？请看：http://www.infoq.com/cn/articles/apache-shiro
									</div> -->
									技术要点：
									<div style="padding-left: 20px;"> 
									1：此版本采用ajax+js分页,表格lyGrid分页插件,有点模仿ligerui的分页实现 
									<br>2：列表的表头固定,兼容IE,firefox,google,360的浏览器,其他暂没有测试.<br>
									3：表格排序功能<br>
									4：弹窗采用贤心的插件，网址：http://sentsin.com/jquery/layer/<br>
									5：加入druid技术,对sql语句的监控.<br>
									6：自定义注解导出excel<br>
									<!-- 7：<font color="blue">使用了ehcache缓存机制</font><br>
									8：<font color="blue">新增支持oracle分页实现</font><br>
									9：<font color="blue">新增支持SQLserver2008分页实现</font><br> -->
									7：<font color="blue">解决分页参数没法传到后台的问题</font><br>
									8：<font color="blue">异常统一处理</font><br>
									<!-- 12：<font color="blue">使用spring Security3权限安全机制,采用了shiro权限机制</font><br>
									13：<font color="blue">封装好baseSerive,baseSeriveImpl,baseMapper..服务层，持久层统一调用</font><br> -->
									........<br>
								</div></div></div>
		</td>
		</tr>
		</table>
		</div>
	</div>

