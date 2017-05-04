var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		pageSize : 30,
		dymCol:true,
		l_column : [ {
			colkey : "id",
			name : "ID",
			hide: "true",
			isSort:false,
		},{
			colkey : "tblNm",
			name : "表名称",
			isSort:false,
		} , 
		{
			colkey:"schemaCode",
			name:"源系统名",
			isSort:false,
		}
		,
		 {
			colkey : "totalFldNum",
			name : "表字段数",
			isSort:false,
		}, {
			colkey : "tblDesc",
			name : "表描述",
			isSort:false,
		},{
			colkey:"tblType",
			name:"表类型",
			renderData:function(rowindex,data,rowdata,colkey){
				if(data == 'MERGE'){
				var tblTypeHtml ="<select class='tblType' style='width:80px' ><option value='NORMAL' selected='selected'>MERGE</option><option value='NORMAL'>NORMAL</option><option value='FLOW' >FLOW</option></select>";
				}
				if(data == 'NORMAL'){
					 tblTypeHtml ="<select class='tblType' style='width:80px' ><option value='MERGE' >MERGE</option><option value='NORMAL' selected='selected'>NORMAL</option><option value='FLOW' >FLOW</option></select>";
				}
				if(data == 'FLOW'){
					 tblTypeHtml ="<select class='tblType' style='width:80px' ><option value='MERGE' >MERGE</option><option value='NORMAL' >NORMAL</option><option value='FLOW' selected='selected'>FLOW</option></select>";
				}
				return tblTypeHtml;
			}
			
		},{
			colkey:"totalRecordNum",
			name:"总记录数",
			renderData:function(rowindex,data,rowdata,colkey){
				return "<input type='text' size='9' class='totalRecordNum' value='"+data+"' />";
			}
		},
		{
			colkey:"avgRecordLength",
			name:"平均增长度数",
			renderData:function(rowindex,data,rowdata,colkey){
				return "<input type='text' size='9' class='avgRecordLength' value='"+data+"' />";

			}
		},
		{
			colkey:"avgIncRecordNum",
			name:"平均增量记录数",
			renderData:function(rowindex,data,rowdata,colkey){
				return "<input type='text' size='9' class='avgIncRecordNum' value='"+data+"' />";

			}
		},{
			colkey:"jieBucketNum",
			name:"截面分桶数量",
			renderData:function(rowindex,data,rowdata,colkey){
				return "<input type='text' size='9' class='avgIncRecordNum' value='"+data+"' />";

			}
		},
		{
			colkey:"partitionType",
			name:"分区类型",
	
			renderData:function(rowindex,data,rowdata,colkey){
				var a = "0";
				var partitionHtml ="<select class='partitionType' ><option value='"+a+"' selected='selected'>无</option><option value='YEAR'>YEAR</option><option value='MONTH' >MONTH</option><option value='DAY'>DAY<option/></select>";
				if(data=='YEAR')
				partitionHtml = "<select class='partitionType' ><option value='"+a+"' >无</option><option value='YEAR' selected='selected'>YEAR</option><option value='MONTH' >MONTH</option><option value='DAY'>DAY<option/></select>";
				if(data=='MONTH')
				partitionHtml = "<select class='partitionType' ><option value='"+a+"' >无</option><option value='YEAR' selected='selected'>YEAR</option><option value='MONTH' selected='selected'>MONTH</option><option value='DAY'>DAY<option/></select>";
				if(data=='DAY')
				partitionHtml = "<select class='partitionType' ><option value='"+a+"' >无</option><option value='YEAR'>YEAR</option><option value='MONTH' selected='selected'>MONTH</option><option value='DAY'  selected='selected'>DAY<option/></select>";
				
				return partitionHtml;
			}
		},
		{
			colkey:"bucketType",
			name:"历史分桶类型",
			renderData:function(rowindex,data,rowdata,colkey){
				
				var a = "0";
				var bucketTypeHtml ="<select class='bucketType' ><option value='"+a+"' selected='selected'>无</option><option value='1'>分桶</option><option value='2' >分桶分区</option></select>";
				if(data=='1')
					bucketTypeHtml = "<select class='bucketType' ><option value='"+a+"' >无</option><option value='1' selected='selected'>分桶</option><option value='2' >分桶分区</option></select>";
				if(data=='2')
					bucketTypeHtml = "<select class='bucketType' ><option value='"+a+"' >无</option><option value='1' >分桶</option><option value='2' selected='selected'>分桶分区</option></select>";
				return bucketTypeHtml;
				
			}
		},{
			colkey:"bucketNum",
			name:"分桶数量",
			renderData:function(rowindex,data,rowdata,colkey){
				return "<input type='text' size='9' class='bucketNum' value='"+data+"' />";
			}
		},
		{
			name : "操作",
			renderData:function(rowindex,data,rowdata,colkey){
				var objid = rowdata.id;
				var onclickHtml = "saveSingleObj("+objid+","+"this);";
				var buttonHtml = "<button class='btn btn-warning marR4' onclick='" +onclickHtml+ "'>保存</button>";
				return buttonHtml;
				
			}
		}],
		jsonUrl : rootPath + '/tableInfo/findByPage.do',
		checkbox : true,
		checkValue : 'id',
		data : {tbDbId:$("#tbDbId").val()},
	});
	
	
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	
	$("#creatDLL").click("click", function() {
		creatDLL();
	});
	
	$('#fenpeiTask').click("click",function(){
		fenpei();
	})
	
	$("#addAccount").click("click", function() {
		addAccount();
	});
	$("#editTable").click("click", function() {
		editTable();
	});
	$("#delAccount").click("click", function() {
		delAccount();
	});
	$("#loadCol").click("click", function() {
		loadCol();
	});
	$("#fpFun").click("click", function() {
		fpFun();
	});
	$("#lyGridUp").click("click", function() {
		up();
	});
	$("#lyGridDown").click("click", function() {
		down();
	});
	$("#selectCol").click("click", function() {
		selectCol();
	});
});

function fpFun(){
	var cbox = grid.getSelectedCheckbox();
	
	pageii = layer.open({
		title : "分配",
		type : 2,
		area : [ "600px", "80%" ],
		
		content : rootPath + '/tableInfo/fenpeiUI.do?ids=' + cbox
	});
}

function creatDLL() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择表项！！");
		return;
	}
	var url = rootPath + '/tableInfo/creatDLL.do';
	var s = CommnUtil.ajax(url, {
		ids : cbox.join(",")
	}, "");
	
	if (s == "success") {
		 window.location.href=rootPath + "/tableInfo/zipDownload.do";
	}
}


function editTable() {
	
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		
		content : rootPath + '/tableInfo/editUI.do?id=' + cbox
	});
}
function addAccount() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/dbInfo/addUI.do'
	});
}
function delAccount() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/dbInfo/deleteEntity.do';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "");
		if (s == "success") {
			layer.msg('删除成功');
			grid.loadData();
		} else {
			layer.msg('删除失败');
		}
	});
}

function loadCol() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择载入项！！");
		return;
	}
	layer.confirm('载入是数据初始化，是否载入？', function(index) {
		var url = rootPath + '/tableInfo/loadEntity.do';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "");
		if (s == "success") {
			layer.msg('载入成功');
			//grid.loadData();
		} else {
			layer.msg('载入失败');
		}
	});
}
function permissions() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/resources/permissions.do?userId='+cbox;
	pageii = layer.open({
		title : "分配权限",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}


function up(){
	grid.lyGridUp();
}
function down(){
	grid.lyGridDown();
}

function selectCol() {
	
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	
	var row = grid.selectRow();
	
	var url = "";
	$("[nav-n]").each(function () {
		$(this).parent().removeClass("active");
		var nav = $(this).attr("nav-n");
		var sn = nav.split(",");
		if(sn[2].indexOf("/colInfo/list.do?id=") != -1){
			url = sn[2];
			$(this).parent().addClass("active");
		}
	});
//	var nav = $(this).attr("nav-n");
//	var sn = nav.split(",");
	var html = '<li><i class="fa fa-home"></i>';
	html+='<a href="index.do">Home</a></li>';
	html+='<li><a href="javascript:void(0)">bdq管理</a></li><li><a href="javascript:void(0)">字段信息</a></li>';
	$("#topli").html(html);
	var tb = $("#loadhtml");
	tb.html(CommnUtil.loadingImg());
	console.info(rootPath+url+"&tbName="+row[0].tblNm+"&srcStmNm="+row[0].schemaCode)
	//bdp-admin/colInfo/list.do?id=5&tbName=INT_TXN_DTL&srcStmNm=SOR
	//bdp-admin/colInfo/list.do?id=5&tbName=T_DB_INFO&srcStmNm=ETL   
	tb.load(rootPath+url+"&tbName="+row[0].tblNm+"&srcStmNm="+row[0].schemaCode);
	
}


function saveSingleObj(id,obj){
	
	var totalRecordNumObj=$(obj).parent().parent().children("td").children("input").filter(".totalRecordNum").val();
	var avgIncRecordNumObj = $(obj).parent().parent().children("td").children("input").filter(".avgIncRecordNum").val();
	var bucketNumObj = $(obj).parent().parent().children("td").children("input").filter(".bucketNum").val();
	var avgRecordLengthObj = $(obj).parent().parent().children("td").children("input").filter(".avgRecordLength").val();
	
	var partionObj = $(obj).parent().parent().children("td").children("select").filter(".partitionType").val();
	var bucketTypeObj = $(obj).parent().parent().children("td").children("select").filter(".bucketType").val();
	var tblTypeObj = $(obj).parent().parent().children("td").children("select").filter(".tblType").val();
	
	
	layer.confirm('是否保存？', function(index) {
		var url = rootPath + '/tableInfo/saveSingleObj.do';
		var s = CommnUtil.ajax(url, {
			id : id,
			totalRecordNumObj:totalRecordNumObj,
			avgIncRecordNumObj:avgIncRecordNumObj,
			bucketNumObj:bucketNumObj,
			partionObj:partionObj,
			bucketTypeObj:bucketTypeObj,
			avgRecordLengthObj:avgRecordLengthObj,
			tblTypeObj:tblTypeObj
		}, "");
		if (s == "success") {
			layer.msg('保存成功');
			//grid.loadData();
		} else {
			layer.msg('保存失败');
		}
	});
}
