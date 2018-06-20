<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String username=(String)session.getAttribute("StaffName");
String admin=(String)session.getAttribute("StaffPermissions");
 	if(username==null){
		response.sendRedirect(basePath+"firesystem/login.html");  
	} 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<title>智能消防安全云服务平台</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="firesystem/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="firesystem/css/bootstrap-theme.min.css" />
	<link rel="stylesheet" type="text/css" href="firesystem/css/font-awesome.min.css" />
	<link href="firesystem/css/asyncbox.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="firesystem/css/style.css" />
	<link rel="stylesheet" type="text/css" href="firesystem/css/show_data.css" />
	<link rel="stylesheet" type="text/css" href="firesystem/css/buttons.css">
	<link rel="stylesheet" type="text/css" href="firesystem/css/datepicker.min.css"  >
  </head>
  
<body>
<div class="pagepage col-md-*12">
	<div class="page">
		<nav class="navbar navbar-default" role="navigation"> 
		    <div class="container-fluid container-fluid-zi"> 
		        <div class="navbar-header"> 
		            <a class="navbar-brand timu" href="#">智能消防安全云服务平台</a>
		        </div>
		        <ul class="nav navbar-nav navbar-right"> 
		            <li>
		                <div class="dropdown">
							<button type="button" class="btn dropdown-toggle btn-login" id="dropdownMenu1" data-toggle="dropdown">
								<div style="width: 40px;height: 33px;float: left">
									<img style="width: 100%;height: 100%" src="firesystem/images/User.png">
								</div>
								<span class="loginren" style="line-height: 30px"> </span>
								<b class="caret"><%=username %></b>
							</button>
							<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
								<li id="modifyPassword"><a class="changepw"><span class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;&nbsp;修改密码</a></li>
		                    	<li id="logout"><a class="loginout"><span class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;&nbsp;注销登录</a></li>
							</ul>
						</div>
	                </li>
	<!--                 <li>
						<div  id="maillog" style="width: 40px;height: 50px;line-height: 52px;color: white;text-align: center;">
							<span class="glyphicon glyphicon-envelope"></span>
						</div>
	                </li> -->
<!--					<li>
						<div class="time">
	                		<div class="hour"></div>
						</div>
					</li>-->
		        </ul> 
		    </div> 
		</nav>

		<div class="title">
			<div class="subtitle">
				<span>监测</span>
			</div>
			<div class="LeftMenuDiv" >
				<span class="glyphicon glyphicon-stats"></span>
				<span style="margin-left: 10px;">实时态势</span>
			</div>
			<div class="LeftMenuDiv LeftMenuDivChosen">
				<span class="glyphicon glyphicon-globe"></span>
				<span style="margin-left: 10px;">地图展示</span>
			</div>
			<div class="LeftMenuDiv">
				<span class="glyphicon glyphicon-exclamation-sign"></span>
				<span style="margin-left: 10px;">异常管理</span>
			</div>
			<div class="LeftMenuDiv">
				<span class="icon-group"></span>
				<span style="margin-left: 10px;">人员管理</span>
			</div>
			<div class="subtitle">
				<span>子系统管理</span>
			</div>
			<div class="LeftMenuDiv" id="leftCurrent">
				<i class="icon-desktop"></i>
				<span style="margin-left: 10px;">电气火灾</span>
			</div>
			<div class="LeftMenuDiv" id="leftDoor">
				<i class="icon-sitemap"></i>
				<span style="margin-left: 10px;">防火门</span>
			</div>
			
			<div class="LeftMenuDiv"  id="leftGas">
				<i class="icon-desktop"></i>
				<span style="margin-left: 10px;">气体监控</span>
			</div>

			<div class="LeftMenuDiv"  id="leftPower">
				<i class="icon-sitemap"></i>
				<span style="margin-left: 10px;">消防电源</span>
			</div>
		</div>
					<!--地图展示-->
		<div class="data" id="allmap" style="display: inline-block"> > <!--地理信息-->
        </div>
			
		



	</div>
	<div class="foot">
		<div class="foot2">
			<p>Copyright @ By College of Automation of CQU, 2016</p>
		</div>
	</div>
</div>
	<script type="text/javascript" src="firesystem/javascript/jquery.js"></script>
	<script type="text/javascript" src="firesystem/javascript/bootstrap.js"></script>
	<script type="text/javascript" src="firesystem/javascript/echarts.min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=Vp7aCfYPrPpgP87XSh4roQqb"></script>
	<script type="text/javascript" src="firesystem/javascript/datepicker.min.js"></script>
	<script type="text/javascript" src="firesystem/javascript/datepicker.en.js"></script>
	<script src="firesystem/javascript/asyncbox.js"></script>
	<script type="text/javascript" src="firesystem/javascript/default.js"></script>
	<script type="text/javascript" src="firesystem/javascript/map.js"></script>
	


</body>
</html>
