var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		width:"2000px",
		tableHeight:"1000px",
//		isFixed:true,
		usePage:false,
		l_column : [ {
			colkey : "id",
			name : "ID",
			hide:true,
			isSort:true,
			width:"120px",
		}, {
			colkey : "tbId",
			name : "表ID",
			isSort:true,
			hide:true,
			width:"120px",
		},{
			colkey : "isEnable",
			name : "是否启用",
			width:"120px",
			renderData:function(rowindex,data,rowdata,colkey){
				
				if(rowdata.isEnable == 1){
					return "<input type='checkbox' class='isEnableFlagObj' value='1' checked=checked />";
				}else{
					return "<input type='checkbox' class='isEnableFlagObj' value='0' />";
				}
			}
		}, {
			colkey : "fieldCode",
			name : "字段名称",
			width:"120px",
			isSort:true,
		} , {
			colkey:"tblNm",
			width:"120px",
			name:"表名称",
		},
		{
			colkey:"srcStmNm",
			width:"120px",
			name:"schema名"
		},
		{
			colkey : "primaryKeyFlag",
			width:"120px",
			name : "字段主键",
			renderData:function(rowindex,data,rowdata,colkey){
				if(rowdata.primaryKeyFlag == 1){
					return "主键";
				}else{
					return "";
				}
			}
		},{
			colkey : "dataTp",
			width:"120px",
			name : "字段类型",
		}, {
			colkey : "fldLength",
			name : "字段长度",
			width:"120px",
			isSort:true,
		},
		
		{
			colkey : "isNull",
			name : "字段是否空",
			width:"120px",
			renderData:function(rowindex,data,rowdata,colkey){
				if(rowdata.isNull == 'Y'){
					return "Y";
				}else{
					return "N";
				}
			}
		}, {
			colkey : "fldSeqNum",
			name : "字段序列",
			isSort:true,
		},
		{
			colkey : "bucketKeyFlag",
			name : "分桶键",
			width:"120px",
			renderData:function(rowindex,data,rowdata,colkey){
				if(rowdata.bucketKeyFlag == 1){
					return "<input type='checkbox' class='isbucketKeyFlagObj' value='1' checked=checked />";
				}else{
					return "<input type='checkbox' class='isbucketKeyFlagObj' value='0' />";
				}
			}
		},
		{
			colkey : "partitionKeyFlag",
			name : "分区键",
			width:"120px",
			renderData:function(rowindex,data,rowdata,colkey){
				if(rowdata.partitionKeyFlag == 1){
					return "<input type='checkbox' class='ispartitionKeyFlagObj' value='1' checked=checked />";
				}else{
					return "<input type='checkbox' class='ispartitionKeyFlagObj' value='0' />";
				}
			}
		},
		{
			colkey : "isAmount",
			name : "是否金额",
			width:"120px",
			renderData:function(rowindex,data,rowdata,colkey){
				if(rowdata.isAmount == 1){
					return "<input type='checkbox' class='isAmountObj' value='1' checked=checked />";
				}else{
					return "<input type='checkbox' class='isAmountObj' value='0' />";
				}
			}
		},
		{
			colkey : "isCode",
			name : "是否码值",
			width:"120px",
			renderData:function(rowindex,data,rowdata,colkey){
				if(rowdata.isCode == 1){
					return "<input type='checkbox' class='isCodeObj' value='1' checked/>";
				}else{
					return "<input type='checkbox' class='isCodeObj' value='0' />";
				}
			}
		},
		{
			colkey : "fieldDesc",
			name : "字段描述",
			width:"120px",
		},
		{
			name : "操作",
			width:"120px",
			renderData:function(rowindex,data,rowdata,colkey){
				var objid = rowdata.id;
				var onclickHtml = "saveSingleObj("+objid+","+"this);";
				var buttonHtml = "<button class='btn btn-warning marR4' onclick='" +onclickHtml+ "'>保存</button>";
				return buttonHtml;
				
			}
		}],
		jsonUrl : rootPath + '/colInfo/findByPage.do',
		checkbox : true,
		checkValue : 'id',
		data : {tbName:$("#tbNameId").val(),srcStmNm:$("#srcStmNm").val()},
		pageSize : 30,
	});
	
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
		
	});
	
	$("#addAccount").click("click", function() {
		addAccount();
	});
	
});

function up(){
	grid.lyGridUp();
}
function down(){
	grid.lyGridDown();
}

function saveSingleObj(id,obj){
	var tableId = $(obj).parent().parent().children("td").eq(3).text();
	
	var isAmountObj = $(obj).parent().parent().children("td").children("input").filter(".isAmountObj");
	var isCodeObj = $(obj).parent().parent().children("td").children("input").filter(".isCodeObj");
	var isBucketFlagObj = $(obj).parent().parent().children("td").children("input").filter(".isbucketKeyFlagObj");
	var isPartionFlagObj = $(obj).parent().parent().children("td").children("input").filter(".ispartitionKeyFlagObj");
	var isEnableFlagObj = $(obj).parent().parent().children("td").children("input").filter(".isEnableFlagObj");
	var isBucketFlag = 0;
	var isPartionFlag = 0;
	var isAmount = 0;
	var isCode = 0;
	var isEnableFlag = 0;
	if(isAmountObj.is(':checked')){
		isAmount = 1;
	}
	if(isCodeObj.is(':checked')){
		isCode = 1;
	}
	
	if(isBucketFlagObj.is(':checked')){
		isBucketFlag = 1;
	}
	if(isPartionFlagObj.is(':checked')){
		isPartionFlag = 1;
	}
	
	if(isEnableFlagObj.is(':checked')){
		isEnableFlag =1;
	}
	
	
	layer.confirm('是否保存？', function(index) {
		var url = rootPath + '/colInfo/saveSingleObj.do';
		var s = CommnUtil.ajax(url, {
			id : id,
			isAmount:isAmount,
			tableId:tableId,
			isCode:isCode,
			isBucketFlagObj:isBucketFlag,
			isPartionFlagObj:isPartionFlag,
			isEnableFlagObj:isEnableFlag
		}, "");
		
		if (s == "success") {
			layer.msg('保存成功');
		}else if(s == "not_allow_bucket"){
			layer.msg('无分桶类型表不允许设置分桶键');
			grid.loadData();
		} else if(s == "not_allow_partition"){
			layer.msg('无分区类型表不允许设置分区键');
			grid.loadData();
		}
		
		else {
			layer.msg('保存失败');
			grid.loadData();
		}
	});
}

