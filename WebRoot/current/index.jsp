<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";   //http://localhost:8080:FireSystem
String username=(String)session.getAttribute("StaffName");
String admin=(String)session.getAttribute("StaffPermissions");
	if(username==null){
		response.sendRedirect(basePath+"current/login.html");  
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
		#allmap{width:100%;height:528px;margin-top:-1em;}
		p{margin-left:5px; font-size:14px;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=joePNdMbgAS1mmPvBhUmeYNt"></script>
	<link href="current/css/bootstrap.min.css" rel="stylesheet">
	<link href="current/css/bootstrap-responsive.min.css" rel="stylesheet">
	
	<link href="current/css/font-awesome.css" rel="stylesheet">
	<link href="current/css/style.css" rel="stylesheet">
	<link href="current/css/asyncbox.css" rel="stylesheet">
	<script src="current/js/jquery-1.7.2.min.js"></script>

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
        <li class="active"><a id="but1"><i class="icon-globe"></i><span>首页</span> </a> </li>
        <li><a id="but2"><i class="icon-bolt"></i><span>电流主机</span> </a> </li>
        <li><a id="but3"><i class="icon-sitemap"></i><span>节点设备</span> </a></li>
        <li><a id="but4"><i class="icon-exclamation-sign"></i><span>故障异常</span> </a> </li>
         <li><a id="but6"><i class="icon-sitemap"></i><span>态势分析</span> </a> </li>
      <!--   <li><a id="but5"><i class="icon-cog"></i><span>楼栋管理</span> </a> </li> -->
       
        
        <li id="isAdmin"><a id="but7"><i class="icon-user"></i><span>人员管理</span> </a> </li>
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
		<div id='treeNode' class="span2" style="height:526px;overflow-x:auto;border:1px solid #b2afaa;margin-left:28px;">
			<div class="accordion" id="accordion">
				<div class="accordion-group">
					<div class="accordion-heading">
						 <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion-947454" href="#accordion-element-235550"></a>
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
						 <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion-947454" href="#accordion-element-267991"></a>
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
             <!--  <div id="selectBuilding"></div> -->
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
  		
  			
          </div>
          <!-- /widget --> 
          <!-- <div class="row"> --><div id="allmap"></div><!--</div> -->
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
function showFloor(id, title, name){
    //alert(id+title+name);
    asyncbox.open({
      title:'查看'+title+id+'节点实时数据',
      width:1000,
      height:595,
      url:'http://localhost:8080/DemoP/data.html?nodeId='+id
    });
  };
  function changeColor(id){
     document.getElementById(id).style.backgroundColor = '#FFEB3B';
    
  };
  
  function resetColor1(id){
    document.getElementById(id).style.backgroundColor = '#FFC107';
     
  }
  
  function resetColor2(id){
    document.getElementById(id).style.backgroundColor = '#E62C0C';
  
  }
  function updateFualtType(data){
      //alert('查看'+title+'第'+name+'节点设备');
      asyncbox.open({
          title:'查看异常类型',
          width:800,
          height:300,
          url:'current/faultType.html?type='+data
          //url:www.w3school.com.cn/tiy/t.asp?f=html_table_test
      });
  };
  function showFloor(buildid,canNo,name){
      //alert('查看'+title+'第'+name+'节点设备');
      asyncbox.open({
          title:'查看'+name+'实时数据',
          width:1000,
          height:500,
          url:'current/data.html?canNo='+canNo+'&buildId='+buildid
          //url:www.w3school.com.cn/tiy/t.asp?f=html_table_test
      });
  };
  /* function checkBuild(obj){
	  alert(obj.value);  
	  $.ajax({
          type: "post",
          url: "servlet/DispatchServlet",
          data:{'controller':'StatusController','system':'3','buildId':obj.value},
          dataType: "json",
          success: function (message) {
              //console.log(JSON.stringify(message));
              $('#host td').remove();
              $('#node td').remove();
              $('#host').append('<td>总监测主机:'+message.hostAll+'个</td>'+'<td>正常主机:'+message.hostNormal+'个</td>'+'<td id="yczj123">异常主机:'+message.hostFault+'个</td>');
              $('#node').append('<td>总监测设备:'+message.nodeAll+'个</td>'+'<td>正常设备:'+message.nodeNormal+'个</td>'+'<td id="ycjd123">异常设备:'+message.nodeFault+'个</td>');          
              if(message.mainFault!='0'){
              	$('#yczj123').css("color","red");
              }
              if(message.nodeFault!='0'){
              	$('#ycjd123').css('color','red');
              }

          },
      });
  }; */
  </script>

<script src="current/js/bootstrap.js"></script>

<script src="current/js/highcharts.js"></script>
 
<script src="current/js/index.js"></script>
<script src="current/js/asyncbox.js"></script>
<!-- <script src="js/Calendar3.js"></script> -->
<script src="current/js/map.js"></script>

</body>
</html>

