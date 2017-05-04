<%@ page language="java" pageEncoding="UTF-8"%>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/redirect/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/redirect/css/tl_init.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/redirect/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/redirect/css/index.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/redirect/css/style.min.css"/>
		<script type="text/javascript" src="${pageContext.servletContext.contextPath }/redirect/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${pageContext.servletContext.contextPath }/redirect/js/bootstrap.min.js"></script>
		<script src="${pageContext.servletContext.contextPath }/redirect/js/index.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		
		
		<div id="indexA">

  <div id="logo"><a href="#"><img src="${pageContext.servletContext.contextPath }/img/weblogo.jpg" width="397" height="100" border="0"></a></div>
  <div id="topBanner">
<script language="javascript">
	if (AC_FL_RunContent == 0) {
		alert("This page requires AC_RunActiveContent.js.");
	} else {
		AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0','name','topBanner','width','560','height','100','align','middle','id','topBanner','src','newhomepage/topBanner','quality','high','wmode','transparent','bgcolor','#ffffff','allowscriptaccess','sameDomain','allowfullscreen','false','pluginspage','http://www.macromedia.com/go/getflashplayer','movie','newhomepage/topBanner' ); //end AC code
	}
</script><embed name="topBanner" width="560" height="100" align="middle" src="resources/topBanner.swf" quality="high" wmode="transparent" bgcolor="#ffffff" allowscriptaccess="sameDomain" allowfullscreen="false" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"> 
<noscript>
	&lt;object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0" name="topBanner" width="560" height="100" align="middle" id="topBanner"&gt;
	&lt;param name="allowScriptAccess" value="sameDomain" /&gt;
	&lt;param name="allowFullScreen" value="false" /&gt;
	&lt;param name="movie" value="newhomepage/topBanner.swf" /&gt;&lt;param name="quality" value="high" /&gt;&lt;param name="wmode" value="transparent" /&gt;&lt;param name="bgcolor" value="#ffffff" /&gt;	&lt;embed src="newhomepage/topBanner.swf" quality="high" wmode="transparent" bgcolor="#ffffff" width="560" height="100" name="topBanner" align="middle" allowScriptAccess="sameDomain" allowFullScreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" /&gt;
	&lt;/object&gt;
</noscript>
  </div>
</div>
	<button onclick="window.open('${pageContext.servletContext.contextPath }/auth/index.do');" class="btn btn-primary  dim btn-large-dim" type="button" style="position: fixed;right: 19px;top: 168px;width: 145px;height: 45px;font-size: 20px;">权限管理入口</button>	
		<div class="container"  style="margin:105px auto;">
	<div class="row clearfix">
		<div class="col-md-8 column imgshow">
			<img alt="140x140" src="${pageContext.servletContext.contextPath }/img/bg01.jpg" class="imga" />
			<img alt="140x140" src="${pageContext.servletContext.contextPath }/img/bg02.jpg" class="imgb" style="display: none;"/>
			<img alt="140x140" src="${pageContext.servletContext.contextPath }/img/bg03.jpg" class="imgc" style="display: none;"/>
			<img alt="140x140" src="${pageContext.servletContext.contextPath }/img/bg04.jpg" class="imgd" style="display: none;"/>
			<img alt="140x140" src="${pageContext.servletContext.contextPath }/img/bg05.jpg" class="imge" style="display: none;"/>
		</div>
		<div class="col-md-4 column">
			 <div class="tabbable" id="tabs-247986">
				<ul class="nav nav-tabs">
					<li class="active" >
						 <a href="#panel-131228" data-toggle="tab">高级管理员</a>
					</li>
					<li>
						 <a href="#panel-65543" data-toggle="tab">中级管理员</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-131228">
						<ul class="sysul">
							<li class="fl">
								<!--<span class="glyphicon glyphicon-ok" aria-hidden="true" style="color: #1BBC9B;"></span>-->
								<img src="${pageContext.servletContext.contextPath }/img/icon1.jpg" class="sysimg sysa sysimghover" />
							<p class="text-center">
								A系统
							</p>
							</li>
							<li class="fl">
								<img src="${pageContext.servletContext.contextPath }/img/icon2.jpg" class="sysimg sysb"/>
							<p class="text-center">
								B系统
							</p>
							</li>
							<li class="fl">
								<img src="${pageContext.servletContext.contextPath }/img/icon3.jpg" class="sysimg sysc"/>
							<p class="text-center">
								C系统
							</p>
							</li>
							<li class="fl">
								<img src="${pageContext.servletContext.contextPath }/img/icon4.jpg" class="sysimg sysd"/>
							<p class="text-center">
								D系统
							</p>
							</li>
							<li class="fl">
								<img src="${pageContext.servletContext.contextPath }/img/icon5.jpg" class="sysimg syse"/>
							<p class="text-center">
								E系统
							</p>
							</li>
						</ul>
						
					</div>
					<div class="tab-pane" id="panel-65543">
						<ul class="sysul">
							<li class="fl">
								<img src="${pageContext.servletContext.contextPath }/img/icon1.jpg" class="sysimg sysa sysimghover"/>
							<p class="text-center">
								A系统
							</p>
							</li>
							<li class="fl">
								<img src="${pageContext.servletContext.contextPath }/img/icon2.jpg" class="sysimg sysb"/>
							<p class="text-center">
								B系统
							</p>
							</li>
							<li class="fl">
								<img src="${pageContext.servletContext.contextPath }/img/icon3.jpg" class="sysimg sysc"/>
							<p class="text-center">
								C系统
							</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="indexH" style="margin: 0 auto;" >

  <p>浙江省台州市路桥区南官大道188号&nbsp;&nbsp;客户服务热线：0576-96575&nbsp;&nbsp;全国服务热线：400-88-96575&nbsp;&nbsp;邮政编码：318050</p>

  <p>COPYRIGHT ? 2003-2009 TaiLong ALL RIGHTS RESERVED 浙ICP备11018676号 <script src="https://ss.knet.cn/seallogo.dll?sn=2011050400100008363&amp;ct=df"> </script><span style="display:inline-block;position:relative;width:auto;"><a href="https://ss.knet.cn/verifyseal.dll?sn=2011050400100008363&amp;ct=df&amp;a=1&amp;pa=0.7404909026809037" id="kx_verify" tabindex="-1" target="_blank" kx_type="图标式" style="display:inline-block;"><img src="https://ss.knet.cn/static/images/logo/cnnic.png" style="border:none;" oncontextmenu="return false;" alt="可信网站"></a></span> 
<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1253033555'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1253033555%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));</script><span id="cnzz_stat_icon_1253033555"><a href="http://www.cnzz.com/stat/website.php?web_id=1253033555" target="_blank" title="站长统计"><img border="0" hspace="0" vspace="0" src="http://icon.cnzz.com/img/pic1.gif"></a></span><script src=" http://s11.cnzz.com/z_stat.php?id=1253033555&amp;show=pic1" type="text/javascript"></script><script src="http://c.cnzz.com/core.php?web_id=1253033555&amp;show=pic1&amp;t=z" charset="utf-8" type="text/javascript"></script>
<script language="javaScript" src="http://zjnet.zjaic.gov.cn/bsjs/330000/33000000008216.js"></script><a target="_blank" href="http://idinfo.zjaic.gov.cn/bscx.do?method=hddoc&amp;id=33000000008216"><img src="http://idinfo.zjaic.gov.cn/images/i_lo2.gif" border="0"></a></p>

</div>



	</body>

</html>