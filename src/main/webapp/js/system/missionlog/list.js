var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "id",
			name : "ID",
			isSort:true,
			hide :true,
		}, {
			colkey : "userName",
			name : "用户姓名",
			isSort:true,
		}, {
			colkey : "processType",
			name : "操作类型",
			isSort:true,
		} , {
			colkey : "processDesc",
			name : "操作内容",
		},
		{
			colkey : "targetTable",
			name : "操作对象",
		},{
			colkey : "processTime",
			name : "操作时间",
		}],
		jsonUrl : rootPath + '/missionlog/findByPage.do',
		checkbox : true,
		checkValue : 'dbId',
	});
	
	
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	
	$("#testConn").click("click",function(){
		testConn();
	});
	
	$("#addDb").click("click", function() {
		addDb();
	});
	$("#editDb").click("click", function() {
		editDb();
	});
	$("#delDb").click("click", function() {
		delDb();
	});
	$("#loadTable").click("click", function() {
		loadTable();
	});
	$("#fpFun").click("click", function() {
		permissions();
	});
	$("#lyGridUp").click("click", function() {
		up();
	});
	$("#lyGridDown").click("click", function() {
		down();
	});
	$("#selectTable").click("click", function() {
		selectTable();
	});
	
	$("#addTable").click("click", function() {
		addTable();
	});
	
	$('#upload').click("click",function(){
		upload();
	})
});


function upload(){
	
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("选中一个");
		return;
	}
	pageii = layer.open({
		title : "上传",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/dbInfo/uploadUI.do?id=' + cbox
	});
}

function testConn(){
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	
		var url = rootPath + '/dbInfo/testConn.do';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "");
		if (s == "success") {
			layer.msg('数据源连接成功');
			//grid.loadData();
		} else {
			layer.msg('数据源连接失败,请检查配置信息');
		};
}

function addTable(){
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("选中一个");
		return;
	}
	
	pageii = layer.open({
		title : "新增表",
		type : 2,
		area : [ "600px", "80%" ],
		
		content : rootPath + '/dbInfo/addTableUI.do?id=' + cbox
	});
	
	
}

function editAccount() {
	
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		
		content : rootPath + '/dbInfo/editUI.do?id=' + cbox
	});
}
function addDb() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/dbInfo/addUI.do'
	});
}
function editDb() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		
		content : rootPath + '/dbInfo/editUI.do?dbId=' + cbox
	});
}
function delDb() {
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
function loadTable() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('载入是数据初始化，是否载入？', function(index) {
		var url = rootPath + '/dbInfo/loadEntity.do';
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

function selectTable() {
	
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
		if(sn[2].indexOf("/tableInfo/list.do?id=") != -1){
			url = sn[2];
			$(this).parent().addClass("active");
		}
	});
//	var nav = $(this).attr("nav-n");
//	var sn = nav.split(",");
	var html = '<li><i class="fa fa-home"></i>';
	html+='<a href="index.do">Home</a></li>';
	html+='<li><a href="javascript:void(0)">bdq管理</a></li><li><a href="javascript:void(0)">表信息</a></li>';
	$("#topli").html(html);
	var tb = $("#loadhtml");
	tb.html(CommnUtil.loadingImg());
	tb.load(rootPath+url+"&tbDbId="+cbox);
	
}
