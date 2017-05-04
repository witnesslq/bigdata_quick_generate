<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/tableInfo/edit.js"></script>

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
		action="${ctx}/tableInfo/editTableSave.do">
		<input type="hidden" class="form-control checkacc"
			value="${tableMeta.id}" name="id" id="id">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">表名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入用户名" value="${tableMeta.tblNm}"
						name="tblNm" id="tblNm" readonly="readonly">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">表字段数</label>
				<div class="col-sm-9">
					<input type="text" class="form-control "
						placeholder="请输入字段数" value="${tableMeta.totalFldNum}"
						name="totalFldNum" id="totalFldNum" readonly="readonly">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">总记录数</label>
				<div class="col-sm-9">
					<input type="text" class="form-control "
						placeholder="请输入总记录数" value="${tableMeta.totalRecordNum}"
						name="totalRecordNum" id="totalRecordNum" >
				</div>
			</div>
				<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">平均增量记录数</label>
				<div class="col-sm-9">
					<input type="text" class="form-control "
						placeholder="请输入平均增量记录" value="${tableMeta.avgIncRecordNum}"
						name="avgIncRecordNum" id="avgIncRecordNum">
				</div>
			</div>
			
				<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">平均记录长度</label>
				<div class="col-sm-9">
					<input type="text" class="form-control "
						placeholder="请输入平均记录长度" value="${tableMeta.avgRecordLength}"
						name="avgRecordLength" id="avgRecordLength" >
				</div>
			</div>
				<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">分桶数量</label>
				<div class="col-sm-9">
					<input type="text" class="form-control "
						placeholder="请输入分桶数量" value="${tableMeta.bucketNum}"
						name="bucketNum" id="bucketNum" >
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">分区类型</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label">
							<c:if test="${empty tableMeta.partitionType}">未知</c:if>
							<c:if test="${tableMeta.partitionType eq 'STATIC'}">STATIC</c:if>
							<c:if test="${tableMeta.partitionType eq 'RANGE'}">RANGE</c:if>
							</span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class="active"><a href="#"><input type="radio"
									name="partitionType" value="STATIC"
									<c:if test="${empty tableMeta.partitionType}"> checked="checked"</c:if>>无</a></li>
							<li class="active"><a href="#"><input type="radio"
									name="partitionType" value="STATIC"
									<c:if test="${tableMeta.partitionType eq STATIC}"> checked="checked"</c:if>>STATIC</a></li>
							<li class=""><a href="#"><input type="radio"
									name="partitionType" value="RANGE"
									<c:if test="${tableMeta.partitionType eq RANGE}"> checked="checked"</c:if>>RANGE</a></li>
						</ul>
					</div>
				</div>
			</div>
				<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">分桶类型</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label">
							<c:if test="${empty tableMeta.bucketType || tableMeta.bucketType eq '0'}">无分桶</c:if>
							<c:if test="${tableMeta.bucketType eq '1'}">分桶</c:if>
								<c:if test="${tableMeta.bucketType eq '2'}">分区分桶</c:if></span>
						    <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
						<li class="active"><a href="#"><input type="radio"
									name="bucketType" value="0"
									<c:if test="${tableMeta.bucketType eq '0'}"> checked="checked"</c:if>>无</a></li>
							<li class="active"><a href="#"><input type="radio"
									name="bucketType" value="1"
									<c:if test="${tableMeta.bucketType eq '1'}"> checked="checked"</c:if>>分桶</a></li>
							<li class=""><a href="#"><input type="radio"
									name="bucketType" value="2"
									<c:if test="${tableMeta.bucketType eq '2'}"> checked="checked"</c:if>>分区分桶</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="表描述"
						value="${tableMeta.tblDesc}" name="tblDesc" id="tblDesc">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">保存</button>
		</footer> </section>
	</form>
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>