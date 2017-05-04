<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<jsp:include page="../../common/bottom.jsp" flush="true "/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/dbInfo/list.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm"
			name="searchForm">
				<div class="form-group">
				<label class="control-label"> <span
					class="h4 font-thin v-middle">数据库名称:</span></label> <input
					class="input-medium ui-autocomplete-input" id="dbName"
					name="dbName">
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<button type="button" id="addTable" class="btn btn btn-grey marR10">新增表</button>
		<button type="button" id="testConn" class="btn btn-success">数据源测试</button>
		<button type="button" id="addDb" class="btn btn-primary marR10">新增</button>
	    <button type="button" id="delDb" class="btn btn-danger">删除</button>
	    <button type="button" id="editDb" class="btn btn-info marR10">编辑</button>
	    <button type="button" id="upload" class="btn btn-warning marR10">文档上传</button>
	</div> 
	</header>
	
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
