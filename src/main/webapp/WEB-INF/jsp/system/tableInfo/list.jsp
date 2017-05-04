<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	

<link href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/tableInfo/list.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm"
			name="searchForm">
			<div class="form-group">
				<label class="control-label"> <span
					class="h4 font-thin v-middle">数据库ID:</span></label> <input
					class="input-medium ui-autocomplete-input" id="tbDbId" value="${tbDbId}"
					name="tbDbId">
			</div>
			<div class="form-group">
				<label class="control-label"> <span
					class="h4 font-thin v-middle">源系统名:</span></label> <input
					class="input-medium ui-autocomplete-input" id="schemaNameId" 
					name="schemaName">
			</div>
			<div class="form-group">
				<label class="control-label"> <span
					class="h4 font-thin v-middle">表名:</span></label> <input
					class="input-medium ui-autocomplete-input" id="tbName" 
					name="tbName">
			</div>
			<div class="form-group">
				<label class="control-label"> <span
					class="h4 font-thin v-middle">状态:</span></label>
					<select id="taskUserStatus" class="input-large" 
						name="taskUserStatus" style="width: 150px">
						<option value="">全部</option>
						<option value="00">未分配</option>
						<option value="01">未完成(已分配)</option>
						<option value="02">未完成(已生成)</option>
						<option value="03">已完成(已确认)</option>
					</select>
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<button type="button" id="selectCol" class="btn btn-warning marR10">查看字段</button>
		<button type="button" id="creatDLL" class="btn btn-success marR10">生成数据包</button>
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
<%-- <jsp:include page="../../common/bottom.jsp" flush="true "/> --%>