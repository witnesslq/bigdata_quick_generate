var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		pageSize : 30,
		dymCol:true,
		l_column : [{
			 colkey:"id",
			 name:"id",
			 hide:true,
			 isSort: false,
		}, 
		 {
			colkey : "taskUserName",
			name : "用户名",
			isSort:false,
		},
		{
			colkey : "srcStmNm",
			name : "schema",
			isSort:false,
		},{
			colkey : "tblNm",
			name : "表名称",
			isSort:false,
		},{
			colkey : "taskUserStatus",
			name : "状态",
			isSort:false,
			renderData:function(rowindex,data,rowdata,colkey){
				if("00"==data){
					return "未分配"
				}else if("01"==data) {
					return "未完成(已分配)"
				}else if("02"==data){
					return "未完成(已生成)"
				}else if("03"==data){
					return "已完成(已确认)";
				}
				else{
					return "未分配";
				}
				
			}
			
		} ],
		jsonUrl : rootPath + '/tasklist/findByPage.do',
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
	
	
	$('#upload').click("click",function(){
		upload();
	})
	$("#finishFun").click("click", function() {
		finished();
	});
	
	
	$("#revokeFun").click("click", function() {
		revokeFun();
	});
	
	
	
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

function revokeFun(){
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length < 1) {
		layer.msg("至少选中一个");
		return;
	}
	
	var ck = grid.selectRow();
	try{
	$.each(ck,function(index,obj){
		console.info(obj.taskUserStatus)
		if(obj.taskUserStatus == '02'){
			layer.msg("已执行的记录不能收回");
			//return ;
			grid.loadData();
			throw('')
		}
	})
	}catch(err){
		return ;
	}
	
	layer.confirm('是否回收权限？', function(index) {
		var url = rootPath + '/tasklist/revokeSuccess.do';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "");
		if (s == "success") {
			layer.msg('设置成功');
			grid.loadData();
		} else {
			layer.msg('设置失败');
		}
	});
}

function finished(){
	
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length < 1) {
		layer.msg("至少选中一个");
		return;
	}
	
	var ck = grid.selectRow();
	try{
	$.each(ck,function(index,obj){
		
		if(obj.taskUserStatus == '0'){
			layer.msg("未分配的任务不能设为完成");
			//return ;
			grid.loadData();
			throw('')
		}
		
		if(obj.taskUserStatus == '01'){
			layer.msg("未生成数据包不能设为完成");
			//return ;
			grid.loadData();
			throw('')
		}
		
		if(obj.taskUserStatus == '03'){
			layer.msg("已完成不需设为完成");
			//return ;
			grid.loadData();
			throw('')
		}
	})
	}catch(err){
		return ;
	}
	
	
	layer.confirm('是否设为完成？', function(index) {
		var url = rootPath + '/tasklist/finishTask.do';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "");
		if (s == "success") {
			layer.msg('设置成功');
			grid.loadData();
		} else {
			layer.msg('设置失败');
		}
	});
	
	
}

function fpFun(){
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length < 1) {
		layer.msg("至少选中一个");
		return;
	}
	var ck = grid.selectRow();
	try{
	$.each(ck,function(index,obj){
		console.info(obj.taskUserStatus)
		if(obj.taskUserStatus == '01'||obj.taskUserStatus == '02'){
			layer.msg("已分配的记录不能重复分配");
			//return ;
			grid.loadData();
			throw('')
		}
	})
	}catch(err){
		return ;
	}
	
	pageii = layer.open({
		title : "分配",
		type : 2,
		area : [ "600px", "80%" ],
		
		content : rootPath + '/tasklist/fenpeiUI.do?ids=' + cbox
	});
}


function upload(){
	
	pageii = layer.open({
		title : "上传",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/tasklist/uploadUI.do'
	});
	
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
	
	var html = '<li><i class="fa fa-home"></i>';
	html+='<a href="index.do">Home</a></li>';
	html+='<li><a href="javascript:void(0)">bdq管理</a></li><li><a href="javascript:void(0)">字段信息</a></li>';
	$("#topli").html(html);
	var tb = $("#loadhtml");
	tb.html(CommnUtil.loadingImg());
	tb.load(rootPath+url+"&colTbId="+cbox);
	
}


function saveSingleObj(id,obj){
	
	var totalRecordNumObj=$(obj).parent().parent().children("td").children("input").filter(".totalRecordNum").val();
	var avgIncRecordNumObj = $(obj).parent().parent().children("td").children("input").filter(".avgIncRecordNum").val();
	var bucketNumObj = $(obj).parent().parent().children("td").children("input").filter(".bucketNum").val();
	
	var partionObj = $(obj).parent().parent().children("td").children("select").filter(".partitionType").val();
	var bucketTypeObj = $(obj).parent().parent().children("td").children("select").filter(".bucketType").val();

	layer.confirm('是否保存？', function(index) {
		var url = rootPath + '/tableInfo/saveSingleObj.do';
		var s = CommnUtil.ajax(url, {
			id : id,
			totalRecordNumObj:totalRecordNumObj,
			avgIncRecordNumObj:avgIncRecordNumObj,
			bucketNumObj:bucketNumObj,
			partionObj:partionObj,
			bucketTypeObj:bucketTypeObj
		}, "");
		if (s == "success") {
			layer.msg('保存成功');
			//grid.loadData();
		} else {
			layer.msg('保存失败');
		}
	});
}
