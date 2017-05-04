<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- table size drag -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dist/jquery.resizableColumns.js"></script>
<style type="text/css">
table{border-collapse:collapse;border-spacing:0;}
.listext th{background:#eee;color:#3366cc;}
.listext th,.listext td{border:solid 1px #ddd;text-align:left;padding:10px;font-size:14px;}
.rc-handle-container{position:relative;}
.rc-handle{position:absolute;width:7px;cursor:ew-resize;*cursor:pointer;margin-left:-3px;}
</style>
<script type="text/javascript">
$(function(){
	$("#mytable").resizableColumns({});
});
</script>
