<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/colInfo/edit.js"></script>

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
		action="${ctx}/colInfo/editColToSave.do">
		<input type="hidden" class="form-control checkacc"
			value="${columnMeta.id}" name="id" id="id">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">字段名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="字段名" value="${columnMeta.fieldCode}"
						name="fieldCode" id="fieldCode"  readonly="readonly">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">表名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入账号" value="${columnMeta.tblNm}"
						name="tblNm" id="tblNm" readonly="readonly">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">分桶键</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label"><c:if
									test="${columnMeta.bucketKeyFlag eq 1}">是</c:if>
								<c:if test="${empty columnMeta.bucketKeyFlag }">否</c:if></span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class="active"><a href="#"><input type="radio"
									name=bucketKeyFlag value="1"
									<c:if test="${columnMeta.bucketKeyFlag eq 1}"> checked="checked"</c:if>>是</a></li>
							<li class=""><a href="#"><input type="radio"
									name="bucketKeyFlag" value="0"
									<c:if test="${empty columnMeta.bucketKeyFlag}"> checked="checked"</c:if>>否</a></li>
						</ul>
					</div>
				</div>
			</div>
						<div class="line line-dashed line-lg pull-in"></div>
			
				<div class="form-group">
				<label class="col-sm-3 control-label">分区键</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label"><c:if
									test="${columnMeta.partitionKeyFlag eq 1}">是</c:if>
								<c:if test="${empty columnMeta.partitionKeyFlag }">否</c:if></span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class="active"><a href="#"><input type="radio"
									name=partitionKeyFlag value="1"
									<c:if test="${columnMeta.partitionKeyFlag eq 1}"> checked="checked"</c:if>>是</a></li>
							<li class=""><a href="#"><input type="radio"
									name="partitionKeyFlag" value="0"
									<c:if test="${empty columnMeta.partitionKeyFlag}"> checked="checked"</c:if>>否</a></li>
						</ul>
					</div>
				</div>
			</div>
				<div class="line line-dashed line-lg pull-in"></div>
			
				<div class="form-group">
				<label class="col-sm-3 control-label">可空</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label"><c:if
									test="${columnMeta.isNull eq 'Y'}">是</c:if>
								<c:if test="${columnMeta.isNull eq 'N' }">否</c:if></span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class="active"><a href="#"><input type="radio"
									name=isNull value="1"
									<c:if test="${columnMeta.isNull eq 'Y'}"> checked="checked"</c:if>>是</a></li>
							<li class=""><a href="#"><input type="radio"
									name="isNull" value="0"
									<c:if test="${columnMeta.isNull eq 'N'}"> checked="checked"</c:if>>否</a></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			
				<div class="form-group">
				<label class="col-sm-3 control-label">是否码值</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label"><c:if
									test="${columnMeta.isCode eq '1'}">是</c:if>
								<c:if test="${columnMeta.isCode eq '0' || empty columnMeta.isCode  }">否</c:if></span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class="active"><a href="#"><input type="radio"
									name=isCode value="1"
									<c:if test="${columnMeta.isCode eq '1'}"> checked="checked"</c:if>>是</a></li>
							<li class=""><a href="#"><input type="radio"
									name="isCode" value="0"
									<c:if test="${columnMeta.isCode eq '0'}"> checked="checked"</c:if>>否</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
				<div class="form-group">
				<label class="col-sm-3 control-label">是否金额</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label"><c:if
									test="${columnMeta.isAmount eq '1'}">是</c:if>
								<c:if test="${columnMeta.isAmount eq '0' || empty columnMeta.isAmount}">否</c:if></span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class="active"><a href="#"><input type="radio"
									name=isAmount value="1"
									<c:if test="${columnMeta.isAmount eq '1'}"> checked="checked"</c:if>>是</a></li>
							<li class=""><a href="#"><input type="radio"
									name="isAmount" value="0"
									<c:if test="${columnMeta.isAmount eq '0'}"> checked="checked"</c:if>>否</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入字段备注"
						value="${columnMeta.fieldDesc}" name="fieldDesc" id="remark">
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