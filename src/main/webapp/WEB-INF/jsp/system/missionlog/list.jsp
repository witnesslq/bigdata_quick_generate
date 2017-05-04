<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<jsp:include page="../../common/bottom.jsp" flush="true "/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/missionlog/list.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm"
			name="searchForm">
<!-- 			<div class="form-group"> -->
<!-- 				<label class="control-label"> <span -->
<!-- 					class="h4 font-thin v-middle">日志数据库名称:</span></label> <input -->
<!-- 					class="input-medium ui-autocomplete-input" id="dbName" -->
<!-- 					name="dbName"> -->
<!-- 			</div> -->
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<c:forEach items="${res}" var="key">
			${key.description}
		</c:forEach>
	</div> 
	</header>
	
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	