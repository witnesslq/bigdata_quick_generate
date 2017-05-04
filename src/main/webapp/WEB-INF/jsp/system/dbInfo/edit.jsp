<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/dbInfo/edit.js"></script>

<style type="text/css">
.col-sm-3 {
	width: 15%;
	float: left;
}

.col-sm-9 {
	width: 85%;
	float: left;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
			<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/dbInfo/editEntity.do">
		<input type="hidden" class="form-control checkacc"
			value="${single.dbId}" name="dbId" id="dbId">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">数据库类型</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label">DB2</span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class=""><a href="#"><input type="radio"
									name="dbType" value="DB2" checked="checked">DB2</a></li>
							<li class="active"><a href="#"><input type="radio"
									name="dbType" value="ORACLE">ORACLE</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<div class="col-sm-3">
					<label class="control-label">数据库名称</label>
				</div>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入名称" name="dbName" value="${single.dbName}" id="dbName">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">数据库地址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" value="${single.dbIp}"
						placeholder="请输入地址+端口" name="dbIp" id="dbIp">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">数据库用户</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入用户" name="dbUser" value="${single.dbUser}" id="dbUser">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">数据库密码</label>
				<div class="col-sm-9">
					<input type="password" class="form-control"
						placeholder="请输入密码"  name="dbPassword" value="${single.dbPassword}" id="dbPassword">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入描述"
						name="dbDesc" value="${single.dbDesc}" id="remark">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> </section>
	</form>
			
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>