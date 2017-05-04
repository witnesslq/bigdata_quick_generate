<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%
	String importMsg = "";
	if (request.getSession().getAttribute("msg") != null) {
		importMsg = request.getSession().getAttribute("msg").toString();
	}

	request.getSession().setAttribute("msg", "");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/dbInfo/upload.js"></script>

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
		enctype="multipart/form-data" action="${ctx}/dbInfo/upload.do">
		<input type="hidden" value="${dataId }" name ="dataId" />
		<section class="panel panel-default">
		<div class="panel-body">

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">文档上传路径</label>
				<div class="col-sm-9">
					<input type="file" class="form-control " 
						name="uploadfile" id="uploadfile">
				</div>
			</div>
		</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button onclick="submit1()" class="btn btn-success btn-s-xs">提交</button>
		</footer> </section>
	</form>
	<script type="text/javascript">
		onloadurl();

		function submit1() {

			var flag = "1";
			var excel_file = $('#uploadfile').val();
			if (excel_file == "" || excel_file.length == 0) {
				alert('请输入文件路径')
				return;
			}

		}
	</script>
</body>
</html>