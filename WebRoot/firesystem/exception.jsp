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
			<div class="LeftMenuDiv">
				<span class="glyphicon glyphicon-stats"></span>
				<span style="margin-left: 10px;">实时态势</span>
			</div>
			<div class="LeftMenuDiv">
				<span class="glyphicon glyphicon-globe"></span>
				<span style="margin-left: 10px;">地图展示</span>
			</div>
			<div class="LeftMenuDiv LeftMenuDivChosen">
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
			<div class="LeftMenuDiv">
				<i class="icon-desktop"></i>
				<span style="margin-left: 10px;">电气火灾</span>
			</div>
			<div class="LeftMenuDiv">
				<i class="icon-sitemap"></i>
				<span style="margin-left: 10px;">防火门</span>
			</div>
			
			<div class="LeftMenuDiv">
				<i class="icon-desktop"></i>
				<span style="margin-left: 10px;">气体监控</span>
			</div>

			<div class="LeftMenuDiv">
				<i class="icon-sitemap"></i>
				<span style="margin-left: 10px;">消防电源</span>
			</div>
		</div>
		<div class="dat">
			<div class="data" id="dataTemp2">
				<div class="data-show">
					<div class="data-1">
						<div style="width: 100%; height: 54px; padding-top: 28px; color: #777;">
							<span class="glyphicon glyphicon-exclamation-sign ele-random"></span>
							<span class="ele-font">异常管理</span>
						</div>
						<div class="xuanxiang"  style="padding-top: 8px">
							<span>异常种类 Exception Type</span>
							<select id="exceptionSelect" class="zi" style="height: 22px;width: 200px;">
								<option value="0"></option>
								<option value="1">主机异常</option>
								<option value="2">预警</option>
							</select>
						</div>
						<div class="xuanxiang" style="margin-left: 50px;padding-top: 8px">
							<span>状态 State</span>
							<select id="stateSelect" class="zi" style="height: 22px;width: 200px;">
								<option value="0"></option>
								<option value="1">未处置</option>
								<option value="2">处置中</option>
								<option value="3">已处置</option>
							</select>
						</div>
						<div class="xuanxiang" style="margin-left: 130px; width: 300px; padding-top: 8px">
							<span style="display: block;">时间范围 Time range</span>
							<input id="startingTime" class="datepicker-here"  data-language='en' data-date-format="yyyy-mm-dd"
								   type="text" style="width: 100px;margin-top: 5px;"  />
							&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;&nbsp;
							<input id="endingTime" class="datepicker-here"   data-language='en' data-date-format="yyyy-mm-dd" type="text" style="width: 100px;margin-top: 5px;"  />

							<!--<input class="zi" style="width: 60px; margin-left: 30px;" type="button" value="查 询" />-->
							<!-- <button type="button" class="btn btn-default">查询</button> -->
						</div>
						<div style="float: left;width: 100px;margin-top: 28px;">
							<button id="exceptionQueryButton" style="    color: white;height: 27px;width: 70px;
							line-height: 25px;margin-top: 5px;font-size: 10px;background-color:
							 #C62F2F;border-color: #C62F2F; padding: 0px;font-family: '微软雅黑';" class="button button-primary button-rounded button-small">查 &nbsp; 询</button>
						</div>
					</div>
					<div class="data-2">
						<table class="table table-striped">
							<thead>
							<th>数据未正常加载，请检查网络！</th>
							</thead>
							<tbody id="tableTbody">

							</tbody>
						</table>
					</div>
				</div>
			</div>
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
	<script type="text/javascript" src="public/default.js"></script>
	<script type="text/javascript" src="firesystem/javascript/time.js"></script>
	<script type="text/javascript" src="firesystem/javascript/echarts.min.js"></script>
	<script type="text/javascript" src="firesystem/javascript/datepicker.min.js"></script>
	<script type="text/javascript" src="firesystem/javascript/datepicker.en.js"></script>
	<script src="firesystem/javascript/asyncbox.js"></script>
	<script type="text/javascript" src="firesystem/javascript/exception.js"></script>
	<script type="text/javascript" src="firesystem/javascript/default.js"></script>
</body>
</html>
