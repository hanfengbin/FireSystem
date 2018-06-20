<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username=(String)session.getAttribute("CquLoginUser");
String admin=(String)session.getAttribute("CquUserAdmin");
	if(username==null){
		response.sendRedirect(basePath+"login.html");  
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>消防电气火灾监控</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		body, html {width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#allmap{width:100%;height:500px;}
		p{margin-left:5px; font-size:14px;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=joePNdMbgAS1mmPvBhUmeYNt"></script>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
	
	<link href="css/font-awesome.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/asyncbox.css" rel="stylesheet">
	<script src="js/jquery-1.7.2.min.js"></script>

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
  
<body>

<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
		<a class="brand" href="index.html">消防电气火灾监控</a>
		<div class="nav-collapse">
				<ul class="nav pull-right">
			
					<li class="dropdown open">						
						<a href="#" class="dropdown-toggle" style="font-size:1.2em;" data-toggle="dropdown">
							<i class="icon-user"></i> 
							<span id="userName"><%=username %></span>
							<b class="caret"></b>
						</a>
						
						<ul class="dropdown-menu">
							<li id="modifyPwd"><a>修改密码</a></li>
							<li id="logout"><a>注销</a></li>
						</ul>						
					</li>
					<li>
					<div id="headDivTime">
					<span id="nowDate"></span></br><span id="nowWeek"></span>  <span id="nowTime" style="font-size:1.1em;"></span>
					</div>
				</li>
				</ul>
			</div>

<!-- 		<div style="float:right;padding-right:1em;text-align:center;width:2em;" id="logout">
			<a><i class="icon-user" style="font-size:1.5em;color:black;"></i>
			<span style="color:white;font-size:0.9em;">修改密码</span></a>
		</div> -->

	</div>
    <!-- /container --> 
  </div>
  <!-- /navbar-inner --> 
</div>
<!-- /navbar -->

<div class="subnavbar">
  <div class="subnavbar-inner">
    <div class="container">
      <ul class="mainnav">
        <li class="active"><a id="but4"><i class="icon-globe"></i><span>首页</span> </a> </li>
        <script>
        	var $admin='<%=admin %>';
        	if($admin=='1'){
        		$('#isAdmin').css('display','block');
        	}else{
        		$('#isAdmin').css('display','none');
        	}
        	
        </script>
      </ul>
    </div>
    <!-- /container --> 
  </div>
  <!-- /subnavbar-inner --> 
</div>

<!-- /subnavbar -->
<div class="main">
  <div class="main-inner">
    <div class="container">
      <div class="row">
		<div id='treeNode' class="span2">
			<div class="accordion" id="accordion-947454">
				<div class="accordion-group">
					<div class="accordion-heading">
						 <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion-947454" href="#accordion-element-235550">选项卡 #1</a>
					</div>
					<div id="accordion-element-235550" class="accordion-body collapse">
						<div class="tree">                           
							<ul>
								<li><a>第一级目录</a>
									<ul>
										<li><a>第二级目录</a>
											<ul>
												<li><a>第三级目录</a></li>
												<li><a>第三级目录</a></li>
												<li><a>第三级目录</a></li>
											</ul>
										</li>
									</ul>
								</li>
								<li><a>第一级目录</a>
									<ul>
										<li><a>第二级目录</a></li>
										<li><a>第二级目录</a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="accordion-group">
					<div class="accordion-heading">
						 <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion-947454" href="#accordion-element-267991">选项卡 #2</a>
					</div>
					<div id="accordion-element-267991" class="accordion-body collapse">
						<div class="accordion-inner">
							功能块...
						</div>
					</div>
				</div>
			</div>
		</div>
        <div id='spanTable' class="span10">
          <div class="widget">
            <div class="widget-header" style="position:relative;"> <i class="icon-list-alt"></i>
              <h3> Content</h3>
              
              <div id="excel" style="float:right;padding-right:2em;color:green;cursor:pointer">导出Excel</div>
              <button id="newProject" style="float: right; width:9em;height:3em;cursor: pointer; display: none;text-align:center;">新建</button>
              <button id="newUser" style="float: right; width:9em;height:3em;cursor: pointer; display: none;text-align:center;">新建</button>
             
            </div>
            <!-- /widget-header -->
            <div class="widget-content">
              <table class="table table-bordered">
                <tbody id="tableTbody">
                  <tr>
                    <th>
                   </th>
                  </tr>
                  <tr>
                  <td>
                  </td>
                  </tr>
                  <tr>
                    <td>
                    </td>
                  </tr>
                  <tr>
                    <td>
                    </td>
                  </tr>
                  <tr>
                    <td>
                    </td>
                  </tr>
                  <tr>
                    <td>
                    </td>
                  </tr>
                  <tr>
                    <td>
                    </td>
                  </tr>
                  <tr>
                    <td>
                    </td>
                  </tr>
                  <tr>
                    <td>
                    </td>
                  </tr>
                  <tr><td></td></tr>
                </tbody>
              </table>
            </div>
            <!-- /widget-content --> 
  		<div id="allmap"></div>
          </div>
          <!-- /widget --> 
        </div>
        <!-- /span6 -->
      </div>
      <!-- /row --> 
    </div>
    <!-- /container --> 
  </div>
  <!-- /main-inner --> 
</div>
<!-- /main -->

<!-- /extra -->
<div class="footer">
  <div class="footer-inner">
    <div class="container">
      <div class="row">
        <div class="span12" style="text-align:center;">Copyright © By
						College of Automation of CQU, 2016</div>
        <!-- /span12 --> 
      </div>
      <!-- /row --> 
    </div>
    <!-- /container --> 
  </div>
  <!-- /footer-inner --> 
</div>
<!-- /footer --> 
<!-- Le javascript
================================================== --> 
<!-- Placed at the end of the document so the pages load faster --> 

<script type="text/javascript">
var $username='<%=username %>';
function updatefaultczzsate(id){
     if (confirm("确认操作")) { 
           /*  alert(obj.value);
            alert(exuserID); */
            $.ajax({
					 type: "post",
					 url: "servlet/WeibaoServlet",
					 data:{'faultID':id,'state':'czz','username':$username},
					 dataType: "json",
				success: function (simuData) {
				$("#pointShift3").trigger("click");
					if (simuData){
						alert("操作成功!");
					}
					else
						{
						alert("操作失败！");
						}				
					},
					});
        }
  };
function updatefaultwczsate(id){
    //alert(id+title+name);
      if (confirm("确认操作")) { 
           /*  alert(obj.value);
            alert(exuserID); */
            $.ajax({
					 type: "post",
					 url: "servlet/WeibaoServlet",
					 data:{'faultID':id,'state':'wcz','username':$username},
					 dataType: "json",
				success: function (simuData) {
				$("#pointShift2").trigger("click");
					if (simuData){
						alert("操作成功!");
					}
					else
						{
						alert("操作失败！");
						}				
					},
					});
        }
  };
  </script>
<script src="js/bootstrap.js"></script>
<script src="js/jiedan.js"></script>
<script src="js/Calendar3.js"></script>

</body>
</html>

