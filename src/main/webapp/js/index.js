$.messager.defaults = { ok: "确定", cancel: "取消" ,width:"300px",modal:true,minimizable:false,maximizable:false,collapsible:false};
$(document).ready(function(){
	$(".sysa").hover(function(){
		console.info("click sysa");
		$(".imgshow").children("img").hide();
		$(".imga").show();
		$(".sysimg").removeClass("sysimghover");
		$(this).addClass("sysimghover");
		
	});
	
	$(".sysb").hover(function(){
		console.info("click sysb");
		$(".imgshow").children("img").hide();
		$(".imgb").show();
		$(".sysimg").removeClass("sysimghover");
		$(this).addClass("sysimghover");
	});
	
	$(".sysc").hover(function(){
		console.info("click sysc");
		$(".imgshow").children("img").hide();
		$(".imgc").show();
		$(".sysimg").removeClass("sysimghover");
		$(this).addClass("sysimghover");
	});
	
	$(".sysd").hover(function(){
		console.info("click sysd");
		$(".imgshow").children("img").hide();
		$(".imgd").show();
		$(".sysimg").removeClass("sysimghover");
		$(this).addClass("sysimghover");
	});
	
	$(".syse").hover(function(){
		console.info("click syse");
		$(".imgshow").children("img").hide();
		$(".imge").show();
		$(".sysimg").removeClass("sysimghover");
		$(this).addClass("sysimghover");
	});
});

function manage(){
	//$.messager.alert("提示","testes","info");
	alert("testet");
	window.location.href="/dcdp/sysInfo/index.do";
//	$.ajax( {
//		url : "SysInfo/index.do",
//		cache : false,
//		async : false,
//		type : 'POST',
//		data:{},
//		success : function(data) {
//		},
//		error: function(){
//		}
//	});	
}