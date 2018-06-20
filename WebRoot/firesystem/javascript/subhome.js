$(document).ready(function(){
	var $sys=getQueryString("sys");
	var urlRoot="";
	switch($sys){
	case "3":$('#leftCurrent').addClass('LeftMenuDivChosen');urlRoot="current/index.jsp";break;
	case "4":$('#leftDoor').addClass('LeftMenuDivChosen');urlRoot="firedoor/index.jsp";break;
	case "6":$('#leftGas').addClass('LeftMenuDivChosen');urlRoot="gas/index.jsp";break;
	case "5":$('#leftPower').addClass('LeftMenuDivChosen');urlRoot="power/index.jsp";break;
	default:$('#leftCurrent').addClass('LeftMenuDivChosen');
	}
	
	/*
	 * 跳转页面
	 */
	
	$('#subDetails').click(function(){
		window.open(urlRoot,'_blank');
	});
	
	/*
	* 实时数据websocket
	* */
	realTimeWeb(); 
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
	        console.log(simuDatasimuData);//
	        subSystem(simuDatasimuData.status);//
	        allAbnormal(simuDatasimuData.fault);//
	    };
	    indexWebsocket.onerror = function (event) {
	        console.log(event);
	    };
	    indexWebsocket.onopen = function (event) {
	        indexWebsocket.send($sys);
	        setInterval(function(){
	            indexWebsocket.send($sys);
	        },5000*60);
	    };
	    indexWebsocket.onclose = function (event) {
	        console.log(event);
	    };
	}
	//***ajax用于返回30天异常的数据变化曲线图
	$enum='faultStatisticsByThirtyDay'+$sys;
	console.log($enum);
	$.ajax({
	    type: "post",
	    url: "	servlet/DispatchServlet",
	    data: {'controller':'ChartItem','enum':$enum},
	    dataType: "json",
	    success: function (simuData) {
	    	faultStatistics(simuData.chartItem.date,simuData.chartItem.data[1]);
	    }
	});
	/*
	*首页状态数据
	* */
	function subSystem(simuData){//simuDatasimuData.status
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
	    			$timeoutunhandEvent+='<li class="Lnotice">'+faultObj[5]+' '+faultObj[1]+faultObj[6]+faultObj[4];//+'超时未处置'
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
});
