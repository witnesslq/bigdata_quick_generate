<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/tableInfo/fenpei.js"></script>

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
		action="">
		<input type="hidden" class="form-control checkacc"
			value="${tableMeta.id}" name="tableId" id="id">
		<input type="hidden" value="${idsList }" name = "idsList"/>
		<input id="userName" type="hidden" value="" name="userName"/>
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">选择用户：</label>
				<div class="col-sm-9">
		 	   <select class="form-control" name="userId">
		 	   <c:forEach items="${userList}" var="u">
		        <option value="${u.userId}">${ u.userNm}</option>
		 	   </c:forEach>
		    </select>
			</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button onclick="submit1()" class="btn btn-success btn-s-xs">保存</button>
		</footer> </section>
	</form>
	<script type="text/javascript">
	//onloadurl();
	var checkSubmitFlag = false;
	function submit1(){
		if(checkSubmitFlag==true){
			return ;
		}
		var checkText = $("#form").find("option:selected").text();
		$('#userName').val(checkText);
		$("#form").attr("action","${ctx}/tasklist/fenpeiTableSave.do").submit();
		checkSubmitFlag= true;
	}
	
</script>
</body>
</html>