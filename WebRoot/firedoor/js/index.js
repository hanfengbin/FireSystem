
$(document).ready(function(){
	startTime();
    var $excelName='';//导出报表的时候传送过去的参数
	var timeinter;
    function showPwd(){
        //alert('查看'+title+'第'+name+'节点设备');
        asyncbox.open({
            title:'修改密码',
            width:800,
            height:300,
            url:'firedoor/modifypwd.html'
            //url:www.w3school.com.cn/tiy/t.asp?f=html_table_test
        });
    };
    $("#excel").css("display","none");
	$(".widget-header").css("display","none");
	$(".widget-content").css("display","none");
    //var $tree={"项目1": {"主机1": {"总线1": [{"节点1": "0101001"},{"节点2": "0202002"}]},"主机2": {"总线1": [{"节点1": "0101002"},{"节点2": "0202003"}]}},"项目2": {"主机1": {"总线1": [{"节点1": "0101001"},{"节点2": "0202002"}]},"主机2": {"总线1": [{"节点1": "0101002"},{"节点2": "0202003"}]}}};
	$("#but1").click(function(){
    	$(".functionButton").remove();
    	$(".widget-header select").remove();
    	$("#__calendarPanel").css('visibility','hidden');
    	$("#closeButton").click();
    	$("#calender").remove();
		$("#allmap").css("display","block");
		$(".widget-header").css("display","none");
		$(".widget-content").css("display","none");
    	$('.widget-header button').css("display","none");
    	if(timeinter!=null){
        	clearInterval(timeinter);
    	}
        $('#treeNode').css('display','block');
        $('#spanTable').removeClass().addClass('span10');
        $('.widget-header i').removeClass().addClass("icon-list-alt");     
        $.ajax({
            type: "post",
            url: "servlet/DispatchServlet",
            data:{'controller':'TableItem','enum':'treeList4'},
            dataType: "json",
            success: function (simudata) {
            	console.log(JSON.stringify(simudata));
            	var $tree=simudata.tableItem.tableContent;
            	
            	$('#accordion').remove();
            	$('#treeNode').append('<div class="accordion" id="accordion"></div>');
            	for(var tree in $tree){
            		var $dev=$tree[tree];
                	console.log(JSON.stringify($dev));
                	if($('#accordion-element-'+$dev[0]).length<=0){
                    	$('#accordion').append('<div class="accordion-group"><div class="accordion-heading"><a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion" href="#accordion-element-'+$dev[0]+'">'+$dev[1]+'</a></div><div id="accordion-element-'+$dev[0]+'" class="accordion-body collapse"><div class="tree"><ul id="treeParent"></ul></div></div></div>');	
                    	if($dev[2]!=null){
                    		$('#accordion-element-'+$dev[0]+' .tree #treeParent').append('<li id="host'+$dev[2]+'"><a>'+$dev[3]+'</a></li>');
                        		if($dev[4]!=null){
                        			$('#accordion-element-'+$dev[0]+' #host'+$dev[2]).append("<ul id='"+$dev[2]+"ul'></ul>");
                            		$('#accordion-element-'+$dev[0]+' #host'+$dev[2]+' ul').append('<li><a id="'+$dev[4]+'" onclick="showFloor(\''+$dev[0]+'\',\''+$dev[2]+$dev[4]+'\',\''+$dev[1]+$dev[3]+'总线'+$dev[4]+'\')">'+'总线'+$dev[4]+'</a></li>');                                    		
                        		}
                    		}
                    }else{
                		if($('#host'+$dev[2]).length<=0){
                			$('#accordion-element-'+$dev[0]+' .tree #treeParent').append('<li id="host'+$dev[2]+'"><a>'+$dev[3]+'</a></li>');
                        	$('#accordion-element-'+$dev[0]+' #host'+$dev[2]).append("<ul id='"+$dev[2]+"ul'></ul>");
                        	$('#accordion-element-'+$dev[0]+' #host'+$dev[2]+' ul').append('<li><a id="'+$dev[4]+'" onclick="showFloor(\''+$dev[0]+'\',\''+$dev[2]+$dev[4]+'\',\''+$dev[1]+$dev[3]+'总线'+$dev[4]+'\')">'+'总线'+$dev[4]+'</a></li>');
                		}else{
                			$('#accordion-element-'+$dev[0]+' #host'+$dev[2]+' ul').append('<li><a id="'+$dev[4]+'" onclick="showFloor(\''+$dev[0]+'\',\''+$dev[2]+$dev[4]+'\',\''+$dev[1]+$dev[3]+'总线'+$dev[4]+'\')">'+'总线'+$dev[4]+'</a></li>');
                		}
                	}
            	} 
                activateTree();
            },
        });
        $(".active").removeClass("active");
        $("#but1").parent().addClass("active");
        $("#excel").css("display","none");
    });

    $("#but1").click();

    $("#but2").click(function(){
    	$(".functionButton").remove();
    	$(".widget-header select").remove();
    	$("#__calendarPanel").css('visibility','hidden');
    	$("#closeButton").click();
    	$("#calender").remove();
    	$('.widget-header .functionButton').remove();
		$("#allmap").css("display","none");
		$(".widget-header").css("display","block");
		$(".widget-content").css("display","block");
    	$('.widget-header button').css("display","none");
    	if(timeinter!=null){
        	clearInterval(timeinter);
    	}
        $('#treeNode').css('display','none');
        $('#spanTable').removeClass().addClass('span12');
        $('.widget-header i').removeClass().addClass("icon-list-alt");
        $('.widget-header h3').html('防火门主机详情');
        $('.widget-header').append('<div id="pointShift3" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-50em;">异常主机</div>');
        $('.widget-header').append('<div id="pointShift2" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-50em;">正常主机</div>');
        $('.widget-header').append('<div id="pointShift1" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-50em;">所有主机</div>');
        $excelName='hostList4';
        $.ajax({
            type: "post",
            url: "servlet/DispatchServlet",
            data:{'controller':'TableItem','enum':'hostList4'},
            dataType: "json",
            success: function (simuData) {
            	//console.log(JSON.stringify(simuData));
            	var tableContent=simuData.tableItem.tableContent;
            	for(var i in tableContent){
            		var tmp=tableContent[i];
            		if(tmp[3]=="0"){
            			tmp[3]="正常";
            		}else{
            			tmp[3]="异常";
            		}
            	}
                addJsonToTableTen(simuData.tableItem, 'tableTbody');
            },
        });
        $(".active").removeClass("active");
        $("#but2").parent().addClass("active");
        $("#excel").css("display","block");
        $("#pointShift1").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'TableItem','enum':'hostList4'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                	var tableContent=simuData.tableItem.tableContent;
                	for(var i in tableContent){
                		var tmp=tableContent[i];
                		if(tmp[3]=="0"){
                			tmp[3]="正常";
                		}else{
                			tmp[3]="异常";
                		}
                	}
                    addJsonToTableTen(simuData.tableItem, 'tableTbody');
                },
            });
        });
        $("#pointShift2").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'TableItem','enum':'hostListNormal4'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                	var tableContent=simuData.tableItem.tableContent;
                	for(var i in tableContent){
                		var tmp=tableContent[i];
                		if(tmp[3]=="0"){
                			tmp[3]="正常";
                		}else{
                			tmp[3]="异常";
                		}
                	}
                    addJsonToTableTen(simuData.tableItem, 'tableTbody');
                },
            });
        });
        $("#pointShift3").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'TableItem','enum':'hostListFault4'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                	var tableContent=simuData.tableItem.tableContent;
                	for(var i in tableContent){
                		var tmp=tableContent[i];
                		if(tmp[3]=="0"){
                			tmp[3]="正常";
                		}else{
                			tmp[3]="异常";
                		}
                	}
                    addJsonToTableTen(simuData.tableItem, 'tableTbody');
                },
            });
        });
    });
    $("#but3").click(function(){
    	$(".functionButton").remove();
    	$(".widget-header select").remove();
    	$("#__calendarPanel").css('visibility','hidden');
    	$("#closeButton").click();
    	$("#calender").remove();
		$("#allmap").css("display","none");
    	$('.widget-header button').css("display","none");
        $(".widget-header").css("display","block");
        $(".widget-content").css("display","block");
    	if(timeinter!=null){
        	clearInterval(timeinter);
    	}
        $('#treeNode').css('display','none');
        $('#spanTable').removeClass().addClass('span12');
        $('.widget-header i').removeClass().addClass("icon-list-alt");
        $('.widget-header h3').html('节点设备详情');
        $('.widget-header').append('<div id="pointShift3" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-50em;">异常节点</div>');
        $('.widget-header').append('<div id="pointShift2" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-50em;">正常节点</div>');
        $('.widget-header').append('<div id="pointShift1" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-50em;">所有节点</div>');
        $excelName='nodeList4';
        $.ajax({
            type: "post",
            url: "servlet/DispatchServlet",
            data:{'controller':'TableItem','enum':'nodeList4'},
            dataType: "json",
            success: function (simuData) {
            	//console.log(JSON.stringify(simuData));
            	var tableContent=simuData.tableItem.tableContent;
            	for(var i in tableContent){
            		var tmp=tableContent[i];
            		if(tmp[4]=="0"){
            			tmp[4]="正常";
            		}else{
            			tmp[4]="异常";
            		}
            	}
                addJsonToTableTen(simuData.tableItem, 'tableTbody');
            },
        });
        
        $(".active").removeClass("active");
        $("#but3").parent().addClass("active");
        $("#excel").css("display","block");
        $("#pointShift1").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'TableItem','enum':'nodeList4'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                	var tableContent=simuData.tableItem.tableContent;
                	for(var i in tableContent){
                		var tmp=tableContent[i];
                		if(tmp[4]=="0"){
                			tmp[4]="正常";
                		}else{
                			tmp[4]="异常";
                		}
                	}
                    addJsonToTableTen(simuData, 'tableTbody');
                },
            });
        });
        $("#pointShift2").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'TableItem','enum':'nodeListNormal4'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                	var tableContent=simuData.tableItem.tableContent;
                	for(var i in tableContent){
                		var tmp=tableContent[i];
                		if(tmp[4]=="0"){
                			tmp[4]="正常";
                		}else{
                			tmp[4]="异常";
                		}
                	}
                    addJsonToTableTen(simuData.tableItem, 'tableTbody');
                },
            });
        });
        $("#pointShift3").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'TableItem','enum':'nodeListFault4'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                	var tableContent=simuData.tableItem.tableContent;
                	for(var i in tableContent){
                		var tmp=tableContent[i];
                		if(tmp[4]=="0"){
                			tmp[4]="正常";
                		}else{
                			tmp[4]="异常";
                		}
                	}
                    addJsonToTableTen(simuData.tableItem, 'tableTbody');
                },
            });
        });
        
    });
    $("#but4").click(function(){
    	$(".functionButton").remove();
    	$(".widget-header select").remove();
    	$("#__calendarPanel").css('visibility','hidden');
    	$("#closeButton").click();
    	$("#calender").remove();
		$("#allmap").css("display","none");
    	$('.widget-header button').css("display","none");
        $(".widget-header").css("display","block");
        $(".widget-content").css("display","block");
    	if(timeinter!=null){
        	clearInterval(timeinter);
    	}
        $('#treeNode').css('display','none');
        $('#spanTable').removeClass().addClass('span12');
        $('.widget-header i').removeClass().addClass("icon-list-alt");
        $('.widget-header h3').html('故障异常详情');
        $('.widget-header').append('<div id="pointShift4" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-56em;">已处置</div>');
        $('.widget-header').append('<div id="pointShift3" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-56em;">处置中</div>');
        $('.widget-header').append('<div id="pointShift2" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-56em;">未处置</div>');
        $('.widget-header').append('<div id="pointShift1" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-56em;">所有异常</div>');



        $excelName='faultScreenByAppend4';
        $("#tableTbody tr").remove();
        
        $("#pointShift1").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'FaultScreen','enum':'faultScreenByAppend','system':'4'},
                dataType: "json",
                success: function (simuData) {
                	console.log(JSON.stringify(simuData));
                	addJsonToTableFault(simuData, 'tableTbody');
                },
            });
        });
        $("#pointShift1").click();
        $("#pointShift2").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'FaultScreen','enum':'faultScreenByAppend','system':'4','mvfaultDealState':'未处置'},
                dataType: "json",
                success: function (simuData) {
                	console.log(JSON.stringify(simuData));
                	addJsonToTableFault(simuData, 'tableTbody');
                },
            });
        });
        $("#pointShift3").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'FaultScreen','enum':'faultScreenByAppend','system':'4','mvfaultDealState':'处置中'},
                dataType: "json",
                success: function (simuData) {
                	console.log(JSON.stringify(simuData));
                	addJsonToTableFault(simuData, 'tableTbody');
                },
            });
        });

        $("#pointShift4").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/DispatchServlet",
                data:{'controller':'FaultScreen','enum':'faultScreenByAppend','system':'4','mvfaultDealState':'已处置'},
                dataType: "json",
                success: function (simuData) {
                	console.log(JSON.stringify(simuData));
                	addJsonToTableFault(simuData, 'tableTbody');
                },
            });
        });
        $(".active").removeClass("active");
        $("#but4").parent().addClass("active");
        $("#excel").css("display","block");
    });
    
    


    $("#but6").click(function(){
    	$("#__calendarPanel").css('visibility','hidden');
    	$(".functionButton").remove();
    	$(".widget-header select").remove();
    	$("#closeButton").click();
    	$("#calender").remove();
		$("#allmap").css("display","none");
		$(".widget-header").css("display","block");
		$(".widget-content").css("display","block");
    	$('.widget-header button').css("display","none");
    	if(timeinter!=null){
        	clearInterval(timeinter);
    	}
        $('#treeNode').css('display','none');
        $('#spanTable').removeClass().addClass('span12');
        $('.widget-header i').removeClass().addClass("icon-list-alt");
        $('.widget-header h3').html('防火门态势分析');
        $("#tableTbody tr").remove();
        $("#tableTbody").append("<tr id='host'></tr>");
        $("#tableTbody").append("<tr id='node'></tr>");
        $("#tableTbody").append("<tr></tr>");
        $("#tableTbody").append("<tr><td colspan='20'><div id='chartLeft' style='float:left;width:49.5%;border-right:solid 1px #D3D3D3'></div><div id='chartRight' style='float:left;width:49.5%;'></div></td></tr>");
      
        $.ajax({
            type: "post",
            url: "servlet/DispatchServlet",
            data:{'controller':'BuildingController','enum':'buildingList'},
            dataType: "json",
            success: function (data) {
            	var message=data.tableContent;
            	console.log('buildCount'+JSON.stringify(message));
            	var build='';
            	build=build+'<select name="buildSelect" onchange="checkBuild(this)">';
            	build=build+'<option value=0>所有楼栋</option>';
            	for(id in message){
            		build=build+'<option value='+message[id].buildingID+'>'+message[id].buildingName+'</option>';
            	}
            	build=build+'</select>';
            	$('.widget-header').append(build);
            	
            },
        });
        var $pieChart={ 
                chart: { 
                    plotBackgroundColor: null, 
                    plotBorderWidth: null, 
                    plotShadow: false 
                    }, 
                title: { text: '今天各类异常所占比例' }, 
                tooltip: { pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' }, 
                plotOptions: { 
                    pie: { allowPointSelect: true, cursor: 'pointer', 
                        dataLabels: { enabled: false }, showInLegend: true } 
                    }, 
                series: [{ 
                    type: 'pie', name: '防火门', 
                    data: [['主机异常', 0],['预警', 0]] 
                		}],
                    }; 
        var $lineChart={chart: {
        	            type: 'column'},
        	        title: {
        	            text: '30天异常数据统计'
        	        },
        	        xAxis: {
        	            categories: [
        	                'Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct', 'Nov','Dec'
        	            ],
        	            crosshair: true,
         	        labels: {
               	          step: 5,
               	          staggerLines: 1
               	     }
        	        },
        	        yAxis: {
        	            min: 0,
        	            title: {
        	                text: '个数(个)'
        	            }
        	        },
        	        tooltip: {
        	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
        	                '<td style="padding:0"><b>{point.y}个</b></td></tr>',
        	            footerFormat: '</table>',
        	            shared: true,
        	            useHTML: true
        	        },
        	        plotOptions: {
        	            column: {
        	                pointPadding: 0,
        	                borderWidth: 0
        	            },
        	            series: {
        	  			  cursor: 'pointer',
        	  			  events: {
        	  				click: function(e) {
        	  					//alert('X轴的值：'+e.point.category+' 指标的名称:'+this.name);
        	  					if(chartSimuData==null) return;
        	  			        var $pieChartClick={ 
        	  			                chart: { 
        	  			                    plotBackgroundColor: null, 
        	  			                    plotBorderWidth: null, 
        	  			                    plotShadow: false 
        	  			                    }, 
        	  			                title: { text: '今天各类异常所占比例' }, 
        	  			                tooltip: { pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' }, 
        	  			                plotOptions: { 
        	  			                    pie: { allowPointSelect: true, cursor: 'pointer', 
        	  			                        dataLabels: { enabled: false }, showInLegend: true } 
        	  			                    }, 
        	  			                series: [{ 
        	  			                    type: 'pie', name: '防火门', 
        	  			                    data: [['主机异常', 0],['预警', 0]] 
        	  			                		}],
        	  			                    }; 
        	  					$pieChartClick.title.text=e.point.category+" 各类异常所占比例";
        	  					
        	  					var index=date30.indexOf(e.point.category);
        	  	               (( $pieChartClick.series)[0].data)[0][1]=faultData[index];
        	  	               (( $pieChartClick.series)[0].data)[1][1]=warmingData[index];
        	  	               $('#chartRight').highcharts($pieChartClick);  
        	  				}
        	  			  }
        	  	     }
        	        },
        	        series: [ {
        	            name: '总异常',
        	            data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        	        }, {
        	            name: '主机异常',
        	            data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        	        },{
        	            name: '预警',
        	            data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        	        }]
        	        };
        
       var chartSimuData=null;
       var faultData=null;
       var warmingData=null;
       var date30=null;
       $.ajax({
           type: "post",
           url: "servlet/DispatchServlet",
           data:{"controller":"ChartItem","enum":"faultStateDaysSubParas","paras":"4"},
           dataType: "json",
           success: function (simuData) {
        	   console.log(JSON.stringify(simuData));
        	   chartSimuData=simuData.chartItem.data;
        	   date30=simuData.chartItem.date;
        	   var total=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
        	   for(var key in chartSimuData){
        		   for(var count in chartSimuData[key]){
        			   total[count]+=(chartSimuData[key])[count];
        		   }
        		   if(key=="主机异常"){
        			   ($lineChart.series)[1].data=chartSimuData[key];
        			   faultData=chartSimuData[key];
        			   (( $pieChart.series)[0].data)[0][1]=(chartSimuData[key])[29];
        		   }        			   
        		   if(key=="预警"){
        			   ($lineChart.series)[2].data=chartSimuData[key];
        			   warmingData=chartSimuData[key];
        			   (( $pieChart.series)[0].data)[1][1]=(chartSimuData[key])[29];
        		   }

        	   }
        	   ($lineChart.series)[0].data=total;
        	   $lineChart['xAxis'].categories=simuData.chartItem.date;
        	   $('#chartLeft').highcharts($lineChart);
               if((( $pieChart.series)[0].data)[0][1]==0&&(( $pieChart.series)[0].data)[1][1]==0){
              	 	$pieChart.title.text="今日无异常";
               	   $('#chartRight').html("");
               	   $('#chartRight').append("<br/><br/><img class='chartRightDel' src='current/images/anquan.png'/ width=50%><br/><br/><div class='chartRightDel' style='font-size:1.2em'>今日无异常</div>");
                 }else{
                     $('#chartRight').highcharts($pieChart);  
                 }

           },
       });
     $.ajax({
            type: "post",
            url: "servlet/DispatchServlet",
            data:{'controller':'StatusController','system':'4','buildId':0},
            dataType: "json",
            success: function (simuData) {
                //console.log(JSON.stringify(message));
                $('#host').append('<td>总监测主机:'+simuData.hostAll+'个</td>'+'<td>正常主机:'+simuData.hostNormal+'个</td>'+'<td id="yczj123">异常主机:'+simuData.hostFault+'个</td>');
                $('#node').append('<td>总监测设备:'+simuData.nodeAll+'个</td>'+'<td>正常设备:'+simuData.nodeNormal+'个</td>'+'<td id="ycjd123">异常设备:'+simuData.nodeFault+'个</td>');
                
                if(simuData.mainFault!='0'){
                	$('#yczj123').css("color","red");
                }
                if(simuData.nodeFault!='0'){
                	$('#ycjd123').css('color','red');
                }
            }
     	});
     

        $(".active").removeClass("active");
        $("#but6").parent().addClass("active");
        $("#excel").css("display","none");
    });
    
    $("#but7").click(function(){
    	$(".functionButton").remove();
    	$(".widget-header select").remove();
    	$("#__calendarPanel").css('visibility','hidden');
    	$("#closeButton").click();
    	$("#calender").remove();
		$("#allmap").css("display","none");
        $(".widget-header").css("display","block");
        $(".widget-content").css("display","block");
        $('.widget-header button').css("display","block");
    	if(timeinter!=null){
        	clearInterval(timeinter);
    	}
        $('#treeNode').css('display','none');
        $('#spanTable').removeClass().addClass('span12');
        $('.widget-header i').removeClass().addClass("icon-cog");
        $('.widget-header h3').html('人员管理');
        $('.widget-header button').css("display","block");
        $('#newProject').css("display","none");
        $('#newUser').click(function(){
        	$('.widget-header h3').html('新增人员');
            $("#tableTbody tr").remove();  
            $('.widget-header button').css("display","none");
            $("#tableTbody tr").remove();
            $("#tableTbody").append("<tr><td>用户姓名</td><td><input style='width: 100%;height:2em;' type='text' id='userName123'></td></tr>");
            $("#tableTbody").append("<tr><td>联系电话</td><td><input style='width: 100%;height:2em;' type='text' id='userPhone'></td></tr>");
            $("#tableTbody").append("<tr><td>负责区域</td><td><input style='width: 100%;height:2em;' type='text' id='workArea'></td></tr>");
            $("#tableTbody").append("<tr><td>登陆密码</td><td><input style='width: 100%;height:2em;' type='password' id='userPwd1'></td></tr>");
            $("#tableTbody").append("<tr><td>确认密码</td><td><input style='width: 100%;height:2em;' type='password' id='userPwd2'></td></tr>");
            $("#tableTbody").append("<tr><td>用户权限</td><td><input type='checkbox' value='0' id='newAdmin'>管理人员    <input type='checkbox' value='0' id='newMainter'>维保人员</td></tr>");
            $("#tableTbody").append("<tr><td id='userHints' colspan='20' style='color:red;'>　</td></tr>");    	
            $("#tableTbody").append("<tr><td colspan='20'><button style='width:10%' id='submitUserButton'>提交</button><button style='width:10%' id='cacelButtonNP'>取消</button></td></tr>");
                	
            $("#cacelButtonNP").click(function(){
            	$("#but7").click();
                	});
                	
                	$("#submitUserButton").click(function(){
                		
                		var $admin;
                		if($("#newAdmin").is(':checked')){
                  			$admin=1;
                  		}else if($("#newMainter").is(':checked')){
                  			$admin=3;
                  		}else{
                  			$admin=2;
                  		}
                		var $username=$('#userName123').val();
                		var $phone=$('#userPhone').val();
                		var $workArea=$('#workArea').val();
                		var $userPwd1=$('#userPwd1').val();
                		var $userPwd2=$('#userPwd2').val();
                		if($username==''){
                			$('#userHints').html('姓名不能为空！');
                		}else if($phone==''){
                			$('#userHints').html('电话不能为空！');
                		}else if($userPwd1==''||$userPwd2==''){
                			$('#userHints').html('密码不能为空！');
                		}else if($userPwd1!=$userPwd2){
                			$('#userHints').html('两次密码不一致！');
                		}
                		
                		$.ajax({
                            type: "post",
                            url: "servlet/DispatchServlet",
                            data:{'controller':'NewStaff','name':$username,'phone':$phone,'workArea':$workArea,'userPwd1':$userPwd1,'admin':$admin},
                            success: function (message) {
                                if(message=="failure"){
                                	alert("新增失败");
                                }else{
                                	alert("新增成功");
                                }
                                $("#but7").click();
                            },
                        });
                	});
                });
        $.ajax({
            type: "post",
            url: "servlet/DispatchServlet",
            data:{'controller':'TableItem','enum':'0'},
            dataType: "json",
            success: function (simuData) {
            	var $simu=simuData.tableItem.tableContent;
            	for(var i in $simu){
            		var tmparray=$simu[i];
            		if(tmparray[4]==1){
            			tmparray[4]='管理员';
            		}else{
            			tmparray[4]='普通用户';
            		}
            		tmparray.push('<button style="margin-top:-3px;margin-bottom:-3px;" id="modifyUserButton'+tmparray[0]+'">修改</button><button style="margin-top:-3px;margin-bottom:-3px;" id="deleteUserButton'+tmparray[0]+'">删除</button>');
            	}
            	//console.log(JSON.stringify(simuData));
            	addJsonToTableTen(simuData.tableItem, 'tableTbody');
            	$('button[id^="deleteUserButton"]').click(function(){
            		var $userId=(this.id).substring(16);
            		if(confirm("是否确认删除此人员？")){
                        $.ajax({
                            type: "post",
                            url: "servlet/DispatchServlet",
                            data:{'controller':'DeleteStaff','userId':$userId},
                            success: function (simuData) {
                            	if(simuData=="success"){
                            		alert("删除成功");
                            	}else{
                            		alert("删除失败");
                            	}
                            	$('#but7').click();
                            },
                        });
            		}
            	});
            	$('button[id^="modifyUserButton"]').click(function(){
            		$('.widget-header button').css("display","none");
            		var $userId=(this.id).substring(16);
                    $("#tableTbody tr").remove();
                    $("#tableTbody").append("<tr><td>用户姓名</td><td><input style='width: 100%;height:2em;' type='text' id='userName123'></td></tr>");
                    $("#tableTbody").append("<tr><td>联系电话</td><td><input style='width: 100%;height:2em;' type='text' id='userPhone'></td></tr>");
                    $("#tableTbody").append("<tr><td>负责区域</td><td><input style='width: 100%;height:2em;' type='text' id='userworkArea'></td></tr>");
                    $("#tableTbody").append("<tr><td>登陆密码</td><td><input style='width: 100%;height:2em;' type='text' id='userPwd1'></td></tr>");
                    $("#tableTbody").append("<tr><td>用户权限</td><td><input type='checkbox' value='0' id='newAdmin'>管理人员    <input type='checkbox' value='0' id='newMainter'>维保人员</td></tr>");
                    $("#tableTbody").append("<tr><td id='userHints' colspan='20' style='color:red;'>　</td></tr>");    	
                    $("#tableTbody").append("<tr><td colspan='20'><button style='width:10%' id='submitUserButton'>提交</button><button style='width:10%' id='cacelButtonNP'>取消</button></td></tr>");
                    $.ajax({
                                type: "post",
                                url: "servlet/DispatchServlet",
                                data:{'controller':'QueryStaffById','staffId':$userId},
                                dataType: "json",
                                success: function (simuData) {
                                	console.log(JSON.stringify(simuData));
                                	$('#userName123').val(simuData.StaffName);
                                	$('#userPhone').val(simuData.StaffPhoneNumber);
                                	$('#userworkArea').val(simuData.StaffWorkArea);
                                	$('#userPwd1').val(simuData.StaffPassword);
                                	if(simuData.StaffPermissions=="1"){
                                		$('#newAdmin').prop("checked",true);
                                	}
                                }
                      });
                      $("#cacelButtonNP").click(function(){
                        		$("#but7").click();
                      });
                      
                      $("#submitUserButton").click(function(){                  		
                  		var $admin;
                  		if($("#newAdmin").is(':checked')){
                  			$admin=1;
                  		}else if($("#newMainter").is(':checked')){
                  			$admin=3;
                  		}else{
                  			$admin=2;
                  		}
                  		var $username=$('#userName123').val();
                  		var $phone=$('#userPhone').val();
                  		var $workArea=$('#userworkArea').val();
                  		var $userPwd1=$('#userPwd1').val();
                  		var $userPwd2=$('#userPwd2').val();
                  		if($username==''){
                  			$('#userHints').html('姓名不能为空！');
                  		}else if($phone==''){
                  			$('#userHints').html('电话不能为空！');
                  		}else if($userPwd1==''||$userPwd2==''){
                  			$('#userHints').html('密码不能为空！');
                  		}
                  		
                  		$.ajax({
                              type: "post",
                              url: "servlet/DispatchServlet",
                              data:{'controller':'UpdateStaffById','name':$username,'phone':$phone,'workArea':$workArea,'userPwd1':$userPwd1,'admin':$admin,"userId":$userId},
                              success: function (message) {
                                  if(message=="success"){
                                	  alert("修改成功");
                                  }else{
                                	  alert("修改失败");
                                  }
                                  $("#but7").click();
                              },
                          });
                  	
                      });
            	});
            	},
        });
        $(".active").removeClass("active");
        $("#but7").parent().addClass("active");
        $("#excel").css("display","none");
    });
    
    $("#excel").click(function(){
            window.open("servlet/ExcelServlet?excelName="+$excelName);
    });
    $('#logout').click(function(){
		$.ajax({
            type: "post",
            url: "servlet/DispatchServlet",
            data:{'controller':'Logout'},
            success: function (simuData) {
            	if((simuData=="success")){
            		alert("注销成功");
            		window.location.href="current/login.html";
            	}
            },
        });	
	});
    $("#modifyPwd").click(function(){
    	showPwd();
	});




	//树结构
    function activateTree(){
    	$( '.tree li' ).each( function() {
            if( $( this ).children( 'ul' ).length > 0 ) {
                $( this ).addClass( 'parent' );
            }
        });
        $( '.tree li.parent > a' ).click( function( ) {
            $( this ).parent().toggleClass( 'active' );
            $( this ).parent().children( 'ul' ).slideToggle( 'fast' );
        });
        $( '.tree li.parent > a' ).click();
    }
	$( '.tree li' ).each( function() {
        if( $( this ).children( 'ul' ).length > 0 ) {
            $( this ).addClass( 'parent' );
        }
    });
    $( '.tree li.parent > a' ).click( function( ) {
        $( this ).parent().toggleClass( 'active' );
        $( this ).parent().children( 'ul' ).slideToggle( 'fast' );
    });
    $( '.tree li.parent > a' ).click();







});

/*向tableId的TBODY表格添加jsonData数据，每页8行内容，包含分页代码*/
function addJsonToTableTen(jsonData,tableId){

/*        var tableHeader=jsonData["tableItem"].tableHeader;
    var tableContent=jsonData["tableItem"].tableContent;*/
	var tableHeader=jsonData.tableHeader;
    var tableContent=jsonData.tableContent;
    var colLength=tableHeader.length;

    $("#"+tableId+" tr").remove();
    var Tbody=$("#"+tableId);

    var header="<tr>";//添加表格标题
    for(var trTxt in tableHeader){
        header+='<th>'+tableHeader[trTxt]+'</th>';
    }
    header+="</tr>";
    Tbody.append(header);

    var tableContentLength=0;//计算表格行数
    for(var numRow in tableContent){
        tableContentLength++;
    }

    var pageNum=Math.ceil(tableContentLength/11);//表格页数

    if(pageNum==0){
    	pageNum=1;
    }
    var pageContent=new Array();//数组可存入每页表格内容
    var array=0;
    for(var numRow in tableContent){//存入表格数据
        var row="<tr class='forPageChange'>";
        var rowContent=tableContent[numRow];
        for(var i=0;i<colLength;i++){
            row+='<td>'+rowContent[i]+'</td>';
        }
        row+="</tr>";
        pageContent[array]+=row;
        if(numRow%11==0){array++;}
    }

    var blankNum=11*pageNum-tableContentLength;//在行数不位20整数倍时添加空白行
    if(blankNum!=0){
        for(var j=0;j<blankNum;j++){
            var rowBlank="<tr class='forPageChange'>";
            for(var k=0;k<colLength;k++){
                rowBlank+='<td>　</td>';
            }
            rowBlank+="</tr>";
            pageContent[array]+=rowBlank;
        }
    }
    var currentPage=0;
    Tbody.append(pageContent[currentPage]);
    Tbody.append('<tr id="pageChange"><td colspan="20">第 <span id="pageCurrent">'+(currentPage+1)+'</span> 页　共 <span id="pageTotal">'+pageNum+'</span> 页　<span id="pagePreview">上一页</span>　<span id="pageNext">下一页</span></td></tr>');
    $('#pagePreview').css("cursor","pointer");
    $('#pageNext').css("cursor","pointer");

    $('#pagePreview').click(function(){//上一页
     if(currentPage==0){return}
     currentPage--;
     $('.forPageChange').remove();
     $('#pageChange').before(pageContent[currentPage]);
     $('#pageCurrent').html(currentPage+1);
     });

     $('#pageNext').click(function(){//下一页
     if(currentPage==(pageNum-1)){return}
     currentPage++;
     $('.forPageChange').remove();
     $('#pageChange').before(pageContent[currentPage]);   
     $('#pageCurrent').html(currentPage+1);
     });
     
     //alert(111+JSON.stringify(tableContent));
     if(JSON.stringify(tableContent)=='{}'){
    	 //alert(222);
    	 $('.forPageChange').remove();
    	 $('#pageChange').before("<tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr>");
     }
}

function addJsonToTableFault(jsonData,tableId){

	    var tableHeader=jsonData["tableItem"].tableHeader;
	    var tableContent=jsonData["tableItem"].tableContent;
		/*var tableHeader=jsonData.tableHeader;
	    var tableContent=jsonData.tableContent;*/
	    var colLength=tableHeader.length;

	    $("#"+tableId+" tr").remove();
	    var Tbody=$("#"+tableId);

	    var header="<tr>";//添加表格标题
	    for(var trTxt in tableHeader){
	        header+='<th>'+tableHeader[trTxt]+'</th>';
	    }
	    header+="</tr>";
	    Tbody.append(header);

	    var tableContentLength=0;//计算表格行数
	    for(var numRow in tableContent){
	        tableContentLength++;
	    }

	    var pageNum=Math.ceil(tableContentLength/11);//表格页数

	    if(pageNum==0){
	    	pageNum=1;
	    }
	    var pageContent=new Array();//数组可存入每页表格内容
	    var array=0;
	    for(var numRow in tableContent){//存入表格数据
	        var row="<tr class='forPageChange'>";
	        var rowContent=tableContent[numRow];
	        var i=0;
	        for(;i<colLength-1;i++){
	        	if(rowContent[i]!=null){
	        		 row+='<td>'+rowContent[i]+'</td>';
	        	}  
	        	else{
	        		row+='<td>'+'--'+'</td>';
	        	}
	        }
	        if(rowContent[2]=='主机异常'){
	        	var hostFaultType="";
	        	if(rowContent[i]=="1"){
	        		hostFaultType="主电故障";
	        	}else if(rowContent[i]=="2"){
	        		hostFaultType="备电故障";
	        	}else if(rowContent[i]=="3"){
	        		hostFaultType="主电、备电故障";
	        	}
	        	//console.log("主机异常类型："+hostFaultType);
	        	row+='<td>'+' <div class=\"badge\" id=\"'+rowContent[i]+'\">'+hostFaultType+'</div>'+'</td>';
	        }else{
	        	row+='<td>'+' <div class=\"badge\" id=\"'+rowContent[i]+'\">'+'防火门异常'+'</div>'+'</td>';
	        }     
	        row+="</tr>";
	        pageContent[array]+=row;
	        //console.log(row);
	        if(numRow%11==0){array++;}
	    }
	   
	    var blankNum=11*pageNum-tableContentLength;//在行数不位20整数倍时添加空白行
	    if(blankNum!=0){
	        for(var j=0;j<blankNum;j++){
	            var rowBlank="<tr class='forPageChange'>";
	            for(var k=0;k<colLength;k++){
	                rowBlank+='<td>　</td>';
	            }
	            rowBlank+="</tr>";
	            pageContent[array]+=rowBlank;
	        }
	    }
	    var currentPage=0;
	    Tbody.append(pageContent[currentPage]);
	    Tbody.append('<tr id="pageChange"><td colspan="20">第 <span id="pageCurrent">'+(currentPage+1)+'</span> 页　共 <span id="pageTotal">'+pageNum+'</span> 页　<span id="pagePreview">上一页</span>　<span id="pageNext">下一页</span></td></tr>');
	    $('#pagePreview').css("cursor","pointer");
	    $('#pageNext').css("cursor","pointer");

	    $('#pagePreview').click(function(){//上一页
	     if(currentPage==0){return}
	     currentPage--;
	     $('.forPageChange').remove();
	     $('#pageChange').before(pageContent[currentPage]);
	     $('#pageCurrent').html(currentPage+1);
	     });

	     $('#pageNext').click(function(){//下一页
	     if(currentPage==(pageNum-1)){return}
	     currentPage++;
	     $('.forPageChange').remove();
	     $('#pageChange').before(pageContent[currentPage]);   
	     $('#pageCurrent').html(currentPage+1);
	     });
	     
	     //alert(111+JSON.stringify(tableContent));
	     if(JSON.stringify(tableContent)=='{}'){
	    	 //alert(222);
	    	 $('.forPageChange').remove();
	    	 $('#pageChange').before("<tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr>");
	     }
	}
/*------------------------------------*/

/*加载右上角时间*/
function startTime()
	{
		var today=new Date();
		var h=today.getHours();
		var m=today.getMinutes();
		var s=today.getSeconds();
		var w=today.getDay();
		var y=today.getFullYear();
		var mt=today.getMonth()+1;
		var d=today.getDate();
		//var mms=today.getMilliseconds();
		var weekChn = ["日","一","二","三","四","五","六"];
		
		// add a zero in front of numbers<10
		h=checkTime(h);
		m=checkTime(m);
		s=checkTime(s);

		$("#nowTime").text(h+":"+m+":"+s);
		$("#nowDate").text(y+"年"+mt+"月"+d+"日");
		$("#nowWeek").text("星期"+weekChn[w]);
		t=setTimeout('startTime()',1000);
	}
function checkTime(i)
	{
		if (i<10) {i="0" + i}
  		return i
	}
/*-------------*/

//写cookies

function setCookie(name,value)
{
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//读取cookies
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
} 
function checkBuild(obj){
	  //alert(obj.value);

	  $.ajax({
        type: "post",
        url: "servlet/DispatchServlet",
        data:{'controller':'StatusController','system':'4','buildId':obj.value},
        dataType: "json",
        success: function (message) {
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
	  
	  var $enum='faultStateDaysSubAndBuildingParas';
	  var $para=',';
	  var $obj=obj.value;
	  if($obj=='0'){
		  $enum='faultStateDaysSubParas';
		  $para='';
		  $obj="";
	  }
	  
	  var $pieChart={ 
              chart: { 
                  plotBackgroundColor: null, 
                  plotBorderWidth: null, 
                  plotShadow: false 
                  }, 
              title: { text: '今天各类异常所占比例' }, 
              tooltip: { pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' }, 
              plotOptions: { 
                  pie: { allowPointSelect: true, cursor: 'pointer', 
                      dataLabels: { enabled: false }, showInLegend: true } 
                  }, 
              series: [{ 
                  type: 'pie', name: '防火门', 
                  data: [['主机异常', 0],['预警', 0]] 
              		}],
                  }; 
      var $lineChart={chart: {
      	            type: 'column'},
      	        title: {
      	            text: '30天异常数据统计'
      	        },
      	        xAxis: {
      	            categories: [
      	                'Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct', 'Nov','Dec'
      	            ],
      	            crosshair: true,
       	        labels: {
             	          step: 5,
             	          staggerLines: 1
             	     }
      	        },
      	        yAxis: {
      	            min: 0,
      	            title: {
      	                text: '个数(个)'
      	            }
      	        },
      	        tooltip: {
      	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
      	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
      	                '<td style="padding:0"><b>{point.y}个</b></td></tr>',
      	            footerFormat: '</table>',
      	            shared: true,
      	            useHTML: true
      	        },
      	        plotOptions: {
      	            column: {
      	                pointPadding: 0,
      	                borderWidth: 0
      	            },
      	            series: {
      	  			  cursor: 'pointer',
      	  			  events: {
      	  				click: function(e) {
      	  					//alert('X轴的值：'+e.point.category+' 指标的名称:'+this.name);
      	  					if(chartSimuData==null) return;
      	  			        var $pieChartClick={ 
      	  			                chart: { 
      	  			                    plotBackgroundColor: null, 
      	  			                    plotBorderWidth: null, 
      	  			                    plotShadow: false 
      	  			                    }, 
      	  			                title: { text: '今天各类异常所占比例' }, 
      	  			                tooltip: { pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' }, 
      	  			                plotOptions: { 
      	  			                    pie: { allowPointSelect: true, cursor: 'pointer', 
      	  			                        dataLabels: { enabled: false }, showInLegend: true } 
      	  			                    }, 
      	  			                series: [{ 
      	  			                    type: 'pie', name: '防火门', 
      	  			                    data: [['主机异常', 0],['预警', 0]] 
      	  			                		}],
      	  			                    }; 
      	  					$pieChartClick.title.text=e.point.category+" 各类异常所占比例";
      	  					
      	  					var index=date30.indexOf(e.point.category);
      	  	               (( $pieChartClick.series)[0].data)[0][1]=faultData[index];
      	  	               (( $pieChartClick.series)[0].data)[1][1]=warmingData[index];
      	  	               $('#chartRight').highcharts($pieChartClick);  
      	  				}
      	  			  }
      	  	     }
      	        },
      	        series: [ {
      	            name: '总异常',
      	            data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
      	        }, {
      	            name: '主机异常',
      	            data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
      	        },{
      	            name: '预警',
      	            data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
      	        }]
      	        };
      
     var chartSimuData=null;
     var faultData=null;
     var warmingData=null;
     var date30=null;
     $.ajax({
         type: "post",
         url: "servlet/DispatchServlet",
         data:{"controller":"ChartItem","enum":$enum,"paras":"4"+$para+$obj},
         dataType: "json",
         success: function (simuData) {
      	   console.log(JSON.stringify(simuData));
      	   chartSimuData=simuData.chartItem.data;
      	   date30=simuData.chartItem.date;
      	   var total=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
      	   for(var key in chartSimuData){
      		   for(var count in chartSimuData[key]){
      			   total[count]+=(chartSimuData[key])[count];
      		   }
      		   if(key=="主机异常"){
      			   ($lineChart.series)[1].data=chartSimuData[key];
      			   faultData=chartSimuData[key];
      			   (( $pieChart.series)[0].data)[0][1]=(chartSimuData[key])[29];
      		   }        			   
      		   if(key=="预警"){
      			   ($lineChart.series)[2].data=chartSimuData[key];
      			   warmingData=chartSimuData[key];
      			   (( $pieChart.series)[0].data)[1][1]=(chartSimuData[key])[29];
      		   }

      	   }
      	   ($lineChart.series)[0].data=total;
      	   $lineChart['xAxis'].categories=simuData.chartItem.date;
      	   $('#chartLeft').highcharts($lineChart);
             if((( $pieChart.series)[0].data)[0][1]==0&&(( $pieChart.series)[0].data)[1][1]==0){
            	 	$pieChart.title.text="今日无异常";
             	   $('#chartRight').html("");
             	   $('#chartRight').append("<br/><br/><img class='chartRightDel' src='current/images/anquan.png'/ width=50%><br/><br/><div class='chartRightDel' style='font-size:1.2em'>今日无异常</div>");
               }else{
                   $('#chartRight').highcharts($pieChart);  
               }

         },
     });
};

