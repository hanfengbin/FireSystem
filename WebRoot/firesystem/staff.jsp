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
			<div class="LeftMenuDiv">
				<span class="glyphicon glyphicon-exclamation-sign"></span>
				<span style="margin-left: 10px;">异常管理</span>
			</div>
			<div class="LeftMenuDiv LeftMenuDivChosen">
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
			<div class="data" id="staffList">
				<div class="data-show">
					<table class="table table-striped tablelen" id="usertable">
						<caption class="ele-caption">
							<i class="icon-group ele-random"></i>
							<span class="ele-font">人员管理</span>
							<div class="jiediandiv">
								<div class="personswitch">所有人员</div>
								<div style="display: block; height: 100%;width: 180px;float: right;">
									<button id="addStaff" class="button button-primary button-rounded button-small" style="width: 70px;padding: 0px;font-family:'微软雅黑';
									    background-color: #C62F2F; border-color: #C62F2F;line-height: 25px;height: 27px;">
										<i class="icon-plus"></i> 添 加</button>
									<button class="button button-primary button-rounded button-small" style="width: 70px;padding: 0px;font-family:'微软雅黑';
									    background-color: #C62F2F;border-color: #C62F2F;line-height: 25px;height: 27px;margin-left: 10px">
										<i class="icon-download"></i> 导 出</button>
								</div>
							</div>
						</caption>
						<thead>
							<th>数据未正常加载，请检查网络！</th>
						</thead>
						<tbody id="tableTbody">

						</tbody>
					</table>
				</div>
			</div>
			<div class="data" id="addStaffTable" style="display: none">
				<div class="page-header" style="text-align: center">
				    <h1>新建用户
				    </h1>
				</div>
				 	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">用户姓名：</label>
				    	<div class="col-sm-3">
				      		<input id='staffName' type="text" class="form-control" placeholder="请输入用户姓名">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">登录密码：</label>
				    	<div class="col-sm-3">
				      		<input id='staffPwd1' type="password" class="form-control" placeholder="请输入密码">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">确认密码：</label>
				    	<div class="col-sm-3">
				      		<input id='staffPwd2' type="password" class="form-control" placeholder="请输入密码">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">联系电话：</label>
				    	<div class="col-sm-3">
				      		<input id='staffPhone' type="text" class="form-control">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">责任区域：</label>
				    	<div class="col-sm-3">
				      		<input id='staffRsp' type="text" class="form-control">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">用户权限：</label>
				    	<div class="col-sm-3">
				      		管理员:<input id='staffThor' type='checkbox' class="form-control" value='0'>
				      		维保人员：<input id='staffThor1' type='checkbox' class="form-control" value='0'>
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<div class="col-sm-offset-5 col-sm-8">
				      		<button id='submitAddStaff' class="btn btn-default btnchangepw">提交</button>
				      		<button id='cancelAddStaff' class="btn btn-default btnchangepw">取消</button>
				    	</div>
				  	</div>
			</div>
			<div class="data" id="modifyStaffTable" style="display: none">
				<div class="page-header" style="text-align: center">
				    <h1>修改用户
				    </h1>
				</div>
					<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">用户帐号：</label>
				    	<div class="col-sm-3">
				      		<span id="staffIdMod" class="form-control loginming"></span>
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info"></span>
				    	</div>
				  	</div>
				 	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">用户姓名：</label>
				    	<div class="col-sm-3">
				      		<input id='staffNameMod' type="text" class="form-control" placeholder="请输入用户姓名">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">登录密码：</label>
				    	<div class="col-sm-3">
				      		<input id='staffPwd1Mod' type="password" class="form-control" placeholder="请输入密码">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">确认密码：</label>
				    	<div class="col-sm-3">
				      		<input id='staffPwd2Mod' type="password" class="form-control" placeholder="请输入密码">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">联系电话：</label>
				    	<div class="col-sm-3">
				      		<input id='staffPhoneMod' type="text" class="form-control">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">责任区域：</label>
				    	<div class="col-sm-3">
				      		<input id='staffRspMod' type="text" class="form-control">
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<label class="col-sm-2 col-sm-offset-3 control-label">管理员权限：</label>
				    	<div class="col-sm-3">
				      		<input id='staffThorMod' type='checkbox' class="form-control" value='0'>
				    	</div>
				    	<div class="col-sm-3">
				    		<span class="new-pw-info2"></span>
				    	</div>
				  	</div>
				  	<div class="form-group divchangepw">
				    	<div class="col-sm-offset-5 col-sm-8">
				      		<button id='submitModifyStaff' class="btn btn-default btnchangepw">提交</button>
				      		<button id='cancelModifyStaff' class="btn btn-default btnchangepw">取消</button>
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
	<script type="text/javascript" src="firesystem/javascript/time.js"></script>
	<script type="text/javascript" src="firesystem/javascript/echarts.min.js"></script>
	<script type="text/javascript" src="firesystem/javascript/datepicker.min.js"></script>
	<script type="text/javascript" src="firesystem/javascript/datepicker.en.js"></script>
	<script src="firesystem/javascript/asyncbox.js"></script>
	<script type="text/javascript" src="firesystem/javascript/default.js"></script>
	<script type="text/javascript" src="firesystem/javascript/staff.js"></script>
</body>
</html>
