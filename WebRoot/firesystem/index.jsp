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
		
		
		
			<!--首页-->
			<div class="data" id="dataTemp0" >
				<div class="data-show">
					<div style="width: 100%; height: 54px; padding-top: 28px; color: #777;">
						<span class="glyphicon glyphicon-stats ele-random"></span>
						<span class="ele-font">实时态势</span>
					</div>
					<div style="width: 670px; height: 350px; display: inline-block;">
						<div style="width: 100%; height: 100px;">
							<!-- <span>asd</span> -->
							<div style="width: 150px; height: 100%; display: inline-block; margin-left: 20px; float: left;">
								<div style="width: 110px; height: 50px; margin-top: 10px; margin-left: 20px;">
									<div style="width: 30px; height: 100%; float: left; font-size: 18px; padding-top: 8px; color: #777;">
										<i class="icon-building"></i>
									</div>
									<div  style="width: 80px; height: 100%; float: left; font-size: 13px; padding-top: 8px; color: #777;">
										<span>监测楼栋</span>
										<span>MonitorBuild</span>
									</div>
								</div>
								<div id="monitorBuild" style="width: 110px; height: 40px;margin-left: 20px; background-color: #fff; font-weight: bold; font-size: 28px; padding-left: 20px;">
									<p></p>
								</div>
							</div>
							<div style="width: 150px; height: 100%; display: inline-block; margin-left: 10px; float: left;">
								<div style="width: 110px; height: 50px; margin-top: 10px; margin-left: 20px;">
									<div style="width: 30px; height: 100%; float: left; font-size: 18px; padding-top: 8px; color: #777;">
										<i class="icon-desktop"></i>
									</div>
									<div style="width: 80px; height: 100%; float: left; font-size: 13px; padding-top: 8px; color: #777;">
										<span>监测主机</span>
										<span>MonitorHost</span>
									</div>
								</div>
								<div id="monitorHost" style="width: 110px; height: 40px;margin-left: 20px; background-color: #fff; font-weight: bold; font-size: 28px; padding-left: 20px;">
									<p></p>
								</div>
							</div>
							<div style="width: 150px; height: 100%; display: inline-block; margin-left: 10px; float: left;">
								<div style="width: 110px; height: 50px; margin-top: 10px; margin-left: 20px;">
									<div style="width: 30px; height: 100%; float: left; font-size: 18px; padding-top: 8px; color: #777;">
										<i class="icon-map-marker"></i>
									</div>
									<div style="width: 80px; height: 100%; float: left; font-size: 13px; padding-top: 8px; color: #777;">
										<span>监测点位</span>
										<span>MonitorNode</span>
									</div>
								</div>
								<div id="monitorNode" style="width: 110px; height: 40px;margin-left: 20px; background-color: #fff; font-weight: bold; font-size: 28px; padding-left: 20px;">
									<p></p>
								</div>
							</div>
							<div style="width: 150px; height: 100%; display: inline-block; margin-left: 10px; float: left;">
								<div style="width: 110px; height: 50px; margin-top: 10px; margin-left: 20px;">
									<div style="width: 30px; height: 100%; float: left; font-size: 18px; padding-top: 8px; color: #777;">
										<i class="icon-warning-sign"></i>
									</div>
									<div style="width: 80px; height: 100%; float: left; font-size: 13px; padding-top: 8px; color: #777;">
										<span>异常点位</span>
										<span>FaultNodes</span>
									</div>
								</div>
								<div id="faultNodes" style="width: 110px; height: 40px;margin-left: 20px; background-color: #fff; font-weight: bold; font-size: 28px; padding-left: 20px;">
									<p></p>
								</div>
							</div>
						</div>
						<div style="width: 100%; height: 250px; padding-left: 20px;">
							<div style="width: 100%; height: 20px; font-size: 13px; color: #777; border-bottom: 1px solid #777;">
								<span>图表统计 | This month</span>
							</div>
							<div style="width: 50%; height: 230px; float: left;">
								<div style="width: 100%; height: 30px; color: #777; font-size: 13px; padding-top: 5px; padding-left: 10px;">
									<span>点位正常率</span>
								</div>
								<div id="bingtu" style="width: 100%; height: 100%; padding-left: 0px;">

								</div>
							</div>
							<div style="width: 50%; height: 230px; float: left;">
								<div style="width: 100%; height: 30px; color: #777; font-size: 13px; padding-top: 5px; padding-left: 10px;">
									<span>异常分类统计</span>
								</div>
								<div id="tiaotu" style="width: 100%; height: 100%; padding-left: 10px;">

								</div>
							</div>
						</div>
					</div>
					<div style="width: 430px; height: 350px; display: inline-block; position: absolute; margin-left: 20px;">
						<div class="topContent" style=" border-radius: 0.2rem">
							<ul class="profile">
								<li id="nodeFault" class="nodeFault"><span>&nbsp&nbsp节点异常</span><span >-</span>　
								</li>
								<li  id="hostFault" class="hostFault"><span>&nbsp&nbsp主机异常</span><span >-</span>　
								</li>
								<li id="abnormalEventUnhandled"style="border-radius: 0.2rem  0.2rem 0 0">待处置<span>-</span>
								</li>
								<li id="abnormalEventHandling" style="border-radius:  0.2rem  0.2rem 0 0">处置中<span>-</span>
								</li>
								<li id="longtimeunhanded" style="border-radius:  0.2rem  0.2rem 0 0">积压单<span>-</span>
								</li>
							</ul>
							<div id="Information">
								<div id="abnormalDivDetail" style="overflow:scroll;overflow-x:hidden;">
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教-3楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教-3楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教-3楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教-3楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教-3楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教-3楼水位出现异常</li>
									<li class="Lnotice">2016-05-06 15:30:08  A区主教17楼水位出现异常</li> 
								</div>


							</div>
						</div>
					</div>
					<div id="xiantu" style="width: 100%; height: 181px;">

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
	<script type="text/javascript" src="firesystem/javascript/index.js"></script>
	<script type="text/javascript" src="firesystem/javascript/time.js"></script>
	<!-- <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=Vp7aCfYPrPpgP87XSh4roQqb"></script> -->
	<script type="text/javascript" src="firesystem/javascript/echarts.min.js"></script>
	<script type="text/javascript" src="firesystem/javascript/datepicker.min.js"></script>
	<script type="text/javascript" src="firesystem/javascript/datepicker.en.js"></script>
	<script src="firesystem/javascript/asyncbox.js"></script>
	<script type="text/javascript" src="firesystem/javascript/default.js"></script>
	
<script type="text/javascript">
/*
* 实时数据websocket
* */
function realTimeWeb(){
    var indexWebsocket=null;//websocket 首页的滚动数据和实时态势
    if('WebSocket' in window){
        indexWebsocket = new WebSocket(url+"index.do");
    }
    else{
        alert('Not support websocket');
    }
    indexWebsocket.onmessage = function (msg) {
        simuDatasimuData=eval('(' + msg.data + ')');
        console.log(simuDatasimuData);
        subSystem(simuDatasimuData.status);
        allAbnormal(simuDatasimuData.fault);
    };
    indexWebsocket.onerror = function (event) {
        console.log(event);
    };
    indexWebsocket.onopen = function (event) {
        indexWebsocket.send("");
        setInterval(function(){
            indexWebsocket.send("");
        },5000*60);
    };
    indexWebsocket.onclose = function (event) {
        console.log(event);
    };
}
$.ajax({
    type: "post",
    url: "	servlet/DispatchServlet",
    data: {'controller':'ChartItem','enum':'faultStatisticsByThirtyDay'},
    dataType: "json",
    success: function (simuData) {
    	faultStatistics(simuData.chartItem.date,simuData.chartItem.data[1]);
    }
});
/*
*首页状态数据 （实时态势那一栏）
* */
function subSystem(simuData){
    $('#monitorBuild').html('<p>'+simuData[0]+'</p>');
    $('#monitorHost').html('<p>'+simuData[1]+'</p>');
    $('#monitorNode').html('<p>'+simuData[2]+'</p>');
    $('#faultNodes').html('<p>'+simuData[3]+'</p>');
    nodeFaultRate(simuData[2],simuData[3]);
    faultClassStatis(simuData[4],simuData[5]);
}
/*
* 总的异常数据
* */

function allAbnormal(simuDataEvent){
    var nodeFaultCount=0;
    var hostFaultCount=0;
    var unhandcount=0;
    var handingcount=0;
    var timeoutunhandcount=0;
    var timeouthandingcount=0;
    $nodeEvent='';
    $hostEvent='';
    $unhandledEvent='';
    $handlingEvent='';
    $timeoutunhandEvent='';
    $timeouthandingEvent='';
    var fault=simuDataEvent.tableContent;
    for(var i in fault){
    	var faultObj=fault[i];
    	if(faultObj[3]=="主机异常"){
    		hostFaultCount++;
    		$hostEvent+='<li class="Lnotice">'+faultObj[5]+' '+faultObj[1]+faultObj[6]+faultObj[4];
    	}else{
    		nodeFaultCount++;
    		$nodeEvent+='<li class="Lnotice">'+faultObj[5]+' '+faultObj[1]+faultObj[6]+faultObj[4];
    	}	
    	if(faultObj[8]=="未处置"){
    		unhandcount++;
    		$unhandledEvent+='<li class="Lnotice">'+faultObj[5]+' '+faultObj[1]+faultObj[6]+faultObj[4];
    		if(compareTime(faultObj[5])){
    			timeoutunhandcount++;
    			$timeoutunhandEvent+='<li class="Lnotice">'+faultObj[5]+' '+faultObj[1]+faultObj[6]+faultObj[4];//+' '+faultObj[4]+'超时未处置'
    		}
    	}else if(faultObj[8]=="处置中"){
    		handingcount++;
    		$handlingEvent+='<li class="Lnotice">'+faultObj[5]+' '+faultObj[1]+faultObj[6]+faultObj[4];//+' '+faultObj[9]+'正在处置'
			if(compareTime(faultObj[7])){
				timeouthandingcount++;
				$timeouthandingEvent+='<li class="Lnotice">'+faultObj[5]+' '+faultObj[1]+faultObj[6]+faultObj[4];//+' '+faultObj[9]+'超时处置中'
    		}
    	}	
       
    }
    $('#abnormalDivDetail li').remove();
    $('#nodeFault span').html('<span>节点异常</span><span >'+nodeFaultCount+'</span>');
    $('#hostFault span').html('<span>主机异常</span><span >'+hostFaultCount+'</span>');
    $('#abnormalEventUnhandled span').html(unhandcount);
    $('#abnormalEventHandling span').html(handingcount);
    $('#longtimeunhanded span').html(timeoutunhandcount);
    $('#abnormalDivDetail').html($nodeEvent);
}

function compareTime(time){
	 var thisResult = (Date.parse(new Date()) - Date.parse(time)) / 3600 / 1000 / 24;
	 if(thisResult<30){
		 return false;
	 }else{
		 return true;
	 }
}

/* 滚动异常*/
$('.profile li').click(function(){
	$(this).addClass('clicking').siblings('li').removeClass('clicking');
});
$('.profile li').eq(0).click();

$("#nodeFault").click(function(){
	//更改内容
	var olddiv=document.getElementById("abnormalDivDetail");
	for(var i=olddiv.childNodes.length-1;i>=0;i--){ 
		var del=olddiv.removeChild(olddiv.childNodes[i]);
		del=null;
	}
	$('#abnormalDivDetail').html($nodeEvent);
});
$("#hostFault").click(function(){
	//更改内容
	var olddiv=document.getElementById("abnormalDivDetail");
	for(var i=olddiv.childNodes.length-1;i>=0;i--){ 
		var del=olddiv.removeChild(olddiv.childNodes[i]);
		del=null;
	}
	$('#abnormalDivDetail').html($hostEvent);
});

$("#abnormalEventUnhandled").click(function(){
	// 更改内容
	var olddiv=document.getElementById("abnormalDivDetail");
	for(var i=olddiv.childNodes.length-1;i>=0;i--){ 
		var del=olddiv.removeChild(olddiv.childNodes[i]);
		del=null;
	}
	$('#abnormalDivDetail').html($unhandledEvent);
});

$("#abnormalEventHandling").click(function(){
	//更改内容
	var olddiv=document.getElementById("abnormalDivDetail");
	for(var i=olddiv.childNodes.length-1;i>=0;i--){ 
		var del=olddiv.removeChild(olddiv.childNodes[i]);
		del=null;
	}
	$('#abnormalDivDetail').html($handlingEvent);
});

$("#longtimeunhanded").click(function(){
	var olddiv=document.getElementById("abnormalDivDetail");
	for(var i=olddiv.childNodes.length-1;i>=0;i--){ 
		var del=olddiv.removeChild(olddiv.childNodes[i]);
		del=null;
	}
	$('#abnormalDivDetail').html($timeoutunhandEvent);
});

/**
 * 图标统计
 */
 
</script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    function nodeFaultRate(nodeSum,faultNodeSum){
   	var myChart = echarts.init(document.getElementById('bingtu'));
    var labelTop = {
        normal : {
            label : {
                show : true,
                position : 'center',
                formatter : "\n{b}",
                textStyle: {
                    baseline : 'top',
					fontSize:8,
                },
            },
            labelLine : {
                show : false
            }
        }
    };
    var labelFromatter = {
        normal : {
            color:"#50a856",
            label : {
                formatter : function (params){
                    return 100 - params.value + '%'
                },
                textStyle: {
                    baseline : 'top',
                }
            }
        },
    }
    var labelBottom = {
        normal : {
            color: '#ccc',
            label : {
                show : true,
                position : 'center',
                formatter : function (params){
                    return 100-params.value + '%'
                },
                textStyle: {
                    baseline : 'bottom',
					color:"#577fb0",
                }
            },
            labelLine : {
                show : false
            }
        },
        emphasis: {
            color: 'rgba(0,0,0,0)'
        }
    };
    var radius = [40, 55];
    option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            x : 'left',
            y : '20px',
			orient:"vertical",
            data:[
                '正常率',"异常率"
            ],
			textStyle:{
              fontSize:8,
			},
        },
        title : {
            text: '节点正常率',
            subtext: 'Point normal rate',
			textStyle:{
                fontSize:10,
				//fontWeight:"normal",
			},
            x: 'center'
        },
        toolbox: {
            show : true,
            feature : {
               // dataView : {show: true, readOnly: false},//文本显示
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        series : [
            {
                name:'饼图',
                type : 'pie',
                // center : ['10%', '30%'],
                radius : radius,
                x: '0%', // for funnel
                itemStyle : labelFromatter,
                data : [
                    {name:'异常率', value:(faultNodeSum*100/nodeSum).toFixed(2), itemStyle : labelBottom},
					{name:'正常率', value:100-(faultNodeSum*100/nodeSum).toFixed(2),itemStyle : labelTop}

                ]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);	
    }
   

</script>  <!--饼图-->
<script>
		function faultClassStatis(nodeFaultSum, hostFaultSum){
			 var myChart = echarts.init(document.getElementById('tiaotu'));
	            option = {
	                title : {
	                    text: '异常分类统计',
	                    subtext: 'Abnormal classification statistics',
	                    textStyle:{
	                        fontSize:10,
	                    },
	                },
	                grid: {
	                    left: '0%',
	                    containLabel: true,
						bottom:"40",
	                },
	                tooltip : {
	                    trigger: 'axis',
	                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	                    }
	                },
	                legend: {
	                    show:true,
	                    data:['异常数'],
						x:"center",
						y:"190",
	                },
	                toolbox: {
	                    show : true,
	                    feature : {
	                       // dataView : {show: true, readOnly: false},
	                        magicType : {show: true, type: ['line', 'bar']},
	                        restore : {show: true},
	                        saveAsImage : {show: true}
	                    }
	                },
	                calculable : true,
	                xAxis : [
	                    {
	                        type : 'value'
	                    }
	                ],
	                yAxis : [
	                    {

	                        type : 'category',
	                        data : ['主机异常','节点异常']
	                    }
	                ],
	                series : [
	                    {
	                        name:'异常数',
	                        type:'bar',
							itemStyle:{
	                            normal:{
	                                color:"#61a0a8",
								},
							},
	                        data:[hostFaultSum,nodeFaultSum],
	                    },

	                ]
	            };
	        myChart.setOption(option);
		}
</script>  <!--柱状图-->
<script>
	function faultStatistics(date,data){
		var myChart = echarts.init(document.getElementById('xiantu'));
	    option={
	        title: {
	            text: '异常统计',
	            subtext: 'Exception statistics|For year',
	            textStyle:{
	                fontSize:14,
	            },
				x:"30"
	        },
	        grid: {
	            left: '20',
	            containLabel: true,
	            bottom:"10",
				right:"20"
	        },
	        tooltip: {
	            trigger: 'axis'
	        },
	        legend: {
	            show:true,
	            data:['异常总数']
	        },
	        toolbox: {
	            show: true,
	            feature: {

	              //  dataView: {readOnly: false},
	                magicType: {type: ['line', 'bar']},
	                restore: {},
	                saveAsImage: {}
	            }
	        },
	        xAxis:  {
	            type: 'category',
	            boundaryGap: false,
	            data: date
	        },
	        yAxis: {
	            type: 'value',
	            axisLabel: {
	                formatter: '{value}'
	            }
	        },
	        series: [
	            {
	                name:'异常总数',
	                type:'line',
	                data:data,
	                markPoint: {
	                    data: [
	                        {type: 'max', name: '最大值'},

	                    ]
	                },
	            },

	        ]
	    };
		myChart.setOption(option);
	}  
</script> <!--折线图-->
</body>
</html>
