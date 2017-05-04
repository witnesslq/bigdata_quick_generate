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